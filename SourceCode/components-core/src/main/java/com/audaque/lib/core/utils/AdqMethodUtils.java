package com.audaque.lib.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.log4j.Logger;

/**
 * @author deshu.lin (deshu.lin@audaque.com)
 * 
 */
public class AdqMethodUtils extends MethodUtils {

	private final static Logger logger = Logger.getLogger(AdqMethodUtils.class);
	
	private static final Pattern CGLIB_RENAMED_METHOD_PATTERN = Pattern.compile("CGLIB\\$(.+)\\$\\d+");

	/**
	 * Attempt to find a {@link Method} on the supplied class with the supplied
	 * name and no parameters. Searches all superclasses up to
	 * <code>Object</code>.
	 * <p>
	 * Returns <code>null</code> if no {@link Method} can be found.
	 * 
	 * @param clazz
	 *            the class to introspect
	 * @param name
	 *            the name of the method
	 * @return the Method object, or <code>null</code> if none found
	 */
	public static Method findMethod(Class<?> clazz, String name) {
		return findMethod(clazz, name, new Class[0]);
	}

	/**
	 * Attempt to find a {@link Method} on the supplied class with the supplied
	 * name and parameter types. Searches all superclasses up to
	 * <code>Object</code>.
	 * <p>
	 * Returns <code>null</code> if no {@link Method} can be found.
	 * 
	 * @param clazz
	 *            the class to introspect
	 * @param name
	 *            the name of the method
	 * @param paramTypes
	 *            the parameter types of the method (may be <code>null</code> to
	 *            indicate any signature)
	 * @return the Method object, or <code>null</code> if none found
	 */
	public static Method findMethod(Class<?> clazz, String name, Class<?>[] paramTypes) {
		AdqAssert.notNull(clazz, "Class must not be null");
		AdqAssert.notNull(name, "Method name must not be null");
		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (Method method : methods) {
				if (name.equals(method.getName()) && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	/**
	 * Determine whether the given method is an "equals" method.
	 * 
	 * @see java.lang.Object#equals(Object)
	 */
	public static boolean isEqualsMethod(Method method) {
		if (method == null || !method.getName().equals("equals")) {
			return false;
		}
		Class<?>[] paramTypes = method.getParameterTypes();
		return (paramTypes.length == 1 && paramTypes[0] == Object.class);
	}

	/**
	 * Determine whether the given method is a "hashCode" method.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public static boolean isHashCodeMethod(Method method) {
		return (method != null && method.getName().equals("hashCode") && method.getParameterTypes().length == 0);
	}

	/**
	 * Determine whether the given method is a "toString" method.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public static boolean isToStringMethod(Method method) {
		return (method != null && method.getName().equals("toString") && method.getParameterTypes().length == 0);
	}

	/**
	 * Determine whether the given method is originally declared by
	 * {@link java.lang.Object}.
	 */
	public static boolean isObjectMethod(Method method) {
		try {
			Object.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
			return true;
		} catch (SecurityException ex) {
			return false;
		} catch (NoSuchMethodException ex) {
			return false;
		}
	}

	/**
	 * Determine whether the given method is a CGLIB 'renamed' method, following
	 * the pattern "CGLIB$methodName$0".
	 * 
	 * @param renamedMethod
	 *            the method to check
	 * @see net.sf.cglib.proxy.Enhancer#rename
	 */
	public static boolean isCglibRenamedMethod(Method renamedMethod) {
		return CGLIB_RENAMED_METHOD_PATTERN.matcher(renamedMethod.getName()).matches();
	}

	/**
	 * Invoke the specified {@link Method} against the supplied target object
	 * with the supplied arguments. The target object can be <code>null</code>
	 * when invoking a static {@link Method}.
	 * <p>
	 * Thrown exceptions are handled via a call to
	 * {@link #handleReflectionException}.
	 * 
	 * @param method
	 *            the method to invoke
	 * @param target
	 *            the target object to invoke the method on
	 * @param args
	 *            the invocation arguments (may be <code>null</code>)
	 * @return the invocation result, if any
	 */
	public static Object invokeMethod(Method method, Object target, Object... args) {
		try {
			return method.invoke(target, args);
		} catch (Exception ex) {
			AdqExceptionUtils.handleReflectionException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}

	/**
	 * Invoke the specified JDBC API {@link Method} against the supplied target
	 * object with no arguments.
	 * 
	 * @param method
	 *            the method to invoke
	 * @param target
	 *            the target object to invoke the method on
	 * @return the invocation result, if any
	 * @throws SQLException
	 *             the JDBC API SQLException to rethrow (if any)
	 * @see #invokeJdbcMethod(java.lang.reflect.Method, Object, Object[])
	 */
	public static Object invokeJdbcMethod(Method method, Object target) throws SQLException {
		return invokeJdbcMethod(method, target, new Object[0]);
	}

	/**
	 * Invoke the specified JDBC API {@link Method} against the supplied target
	 * object with the supplied arguments.
	 * 
	 * @param method
	 *            the method to invoke
	 * @param target
	 *            the target object to invoke the method on
	 * @param args
	 *            the invocation arguments (may be <code>null</code>)
	 * @return the invocation result, if any
	 * @throws SQLException
	 *             the JDBC API SQLException to rethrow (if any)
	 * @see #invokeMethod(java.lang.reflect.Method, Object, Object[])
	 */
	public static Object invokeJdbcMethod(Method method, Object target, Object... args) throws SQLException {
		try {
			return method.invoke(target, args);
		} catch (IllegalAccessException ex) {
			AdqExceptionUtils.handleReflectionException(ex);
		} catch (InvocationTargetException ex) {
			if (ex.getTargetException() instanceof SQLException) {
				throw (SQLException) ex.getTargetException();
			}
			AdqExceptionUtils.handleInvocationTargetException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}

	/**
	 * Make the given method accessible, explicitly setting it accessible if
	 * necessary. The <code>setAccessible(true)</code> method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * 
	 * @param method
	 *            the method to make accessible
	 * @see java.lang.reflect.Method#setAccessible
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * Perform the given callback operation on all matching methods of the given
	 * class and superclasses (or given interface and super-interfaces).
	 * <p>
	 * The same named method occurring on subclass and superclass will appear
	 * twice, unless excluded by the specified {@link MethodFilter}.
	 * 
	 * @param clazz
	 *            class to start looking at
	 * @param mc
	 *            the callback to invoke for each method
	 * @param mf
	 *            the filter that determines the methods to apply the callback
	 *            to
	 */
	public static void doWithMethods(Class<?> clazz, MethodCallback mc, MethodFilter mf) throws IllegalArgumentException {

		// Keep backing up the inheritance hierarchy.
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (mf != null && !mf.matches(method)) {
				continue;
			}
			try {
				mc.doWith(method);
			} catch (IllegalAccessException ex) {
				throw new IllegalStateException("Shouldn't be illegal to access method '" + method.getName() + "': " + ex);
			}
		}
		if (clazz.getSuperclass() != null) {
			doWithMethods(clazz.getSuperclass(), mc, mf);
		} else if (clazz.isInterface()) {
			for (Class<?> superIfc : clazz.getInterfaces()) {
				doWithMethods(superIfc, mc, mf);
			}
		}
	}

	/**
	 * Perform the given callback operation on all matching methods of the given
	 * class and superclasses.
	 * <p>
	 * The same named method occurring on subclass and superclass will appear
	 * twice, unless excluded by a {@link MethodFilter}.
	 * 
	 * @param clazz
	 *            class to start looking at
	 * @param mc
	 *            the callback to invoke for each method
	 * @see #doWithMethods(Class, MethodCallback, MethodFilter)
	 */
	public static void doWithMethods(Class<?> clazz, MethodCallback mc) throws IllegalArgumentException {
		doWithMethods(clazz, mc, null);
	}

	/**
	 * Get all declared methods on the leaf class and all superclasses. Leaf
	 * class methods are included first.
	 */
	public static Method[] getAllDeclaredMethods(Class<?> leafClass) throws IllegalArgumentException {
		final List<Method> methods = new ArrayList<Method>(32);
		doWithMethods(leafClass, new MethodCallback() {

			public void doWith(Method method) {
				methods.add(method);
			}
		});
		return methods.toArray(new Method[methods.size()]);
	}

	/**
	 * Get the unique set of declared methods on the leaf class and all
	 * superclasses. Leaf class methods are included first and while traversing
	 * the superclass hierarchy any methods found with signatures matching a
	 * method already included are filtered out.
	 */
	public static Method[] getUniqueDeclaredMethods(Class<?> leafClass) throws IllegalArgumentException {
		final List<Method> methods = new ArrayList<Method>(32);
		doWithMethods(leafClass, new MethodCallback() {

			public void doWith(Method method) {
				boolean knownSignature = false;
				Method methodBeingOverriddenWithCovariantReturnType = null;

				for (Method existingMethod : methods) {
					if (method.getName().equals(existingMethod.getName())
							&& Arrays.equals(method.getParameterTypes(), existingMethod.getParameterTypes())) {
						// is this a covariant return type situation?
						if (existingMethod.getReturnType() != method.getReturnType()
								&& existingMethod.getReturnType().isAssignableFrom(method.getReturnType())) {
							methodBeingOverriddenWithCovariantReturnType = existingMethod;
						} else {
							knownSignature = true;
						}
						break;
					}
				}
				if (methodBeingOverriddenWithCovariantReturnType != null) {
					methods.remove(methodBeingOverriddenWithCovariantReturnType);
				}
				if (!knownSignature && !isCglibRenamedMethod(method)) {
					methods.add(method);
				}
			}
		});
		return methods.toArray(new Method[methods.size()]);
	}

	/**
	 * Invoke the given callback on all fields in the target class, going up the
	 * class hierarchy to get all declared fields.
	 * 
	 * @param clazz
	 *            the target class to analyze
	 * @param fc
	 *            the callback to invoke for each field
	 */
	public static void doWithFields(Class<?> clazz, FieldCallback fc) throws IllegalArgumentException {
		doWithFields(clazz, fc, null);
	}

	/**
	 * Invoke the given callback on all fields in the target class, going up the
	 * class hierarchy to get all declared fields.
	 * 
	 * @param clazz
	 *            the target class to analyze
	 * @param fc
	 *            the callback to invoke for each field
	 * @param ff
	 *            the filter that determines the fields to apply the callback to
	 */
	public static void doWithFields(Class<?> clazz, FieldCallback fc, FieldFilter ff) throws IllegalArgumentException {

		// Keep backing up the inheritance hierarchy.
		Class<?> targetClass = clazz;
		do {
			Field[] fields = targetClass.getDeclaredFields();
			for (Field field : fields) {
				// Skip static and final fields.
				if (ff != null && !ff.matches(field)) {
					continue;
				}
				try {
					fc.doWith(field);
				} catch (IllegalAccessException ex) {
					throw new IllegalStateException("Shouldn't be illegal to access field '" + field.getName() + "': " + ex);
				}
			}
			targetClass = targetClass.getSuperclass();
		} while (targetClass != null && targetClass != Object.class);
	}

	/**
	 * Given the source object and the destination, which must be the same class
	 * or a subclass, copy all fields, including inherited fields. Designed to
	 * work on objects with public no-arg constructors.
	 * 
	 * @throws IllegalArgumentException
	 *             if the arguments are incompatible
	 */
	public static void shallowCopyFieldState(final Object src, final Object dest) throws IllegalArgumentException {
		if (src == null) {
			throw new IllegalArgumentException("Source for field copy cannot be null");
		}
		if (dest == null) {
			throw new IllegalArgumentException("Destination for field copy cannot be null");
		}
		if (!src.getClass().isAssignableFrom(dest.getClass())) {
			throw new IllegalArgumentException("Destination class [" + dest.getClass().getName() + "] must be same or subclass as source class ["
					+ src.getClass().getName() + "]");
		}
		doWithFields(src.getClass(), new FieldCallback() {

			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				AdqFieldUtils.makeAccessible(field);
				Object srcValue = field.get(src);
				field.set(dest, srcValue);
			}
		}, COPYABLE_FIELDS);
	}

	/**
	 * Action to take on each method.
	 */
	public interface MethodCallback {

		/**
		 * Perform an operation using the given method.
		 * 
		 * @param method
		 *            the method to operate on
		 */
		void doWith(Method method) throws IllegalArgumentException, IllegalAccessException;
	}

	/**
	 * Callback optionally used to filter methods to be operated on by a method
	 * callback.
	 */
	public interface MethodFilter {

		/**
		 * Determine whether the given method matches.
		 * 
		 * @param method
		 *            the method to check
		 */
		boolean matches(Method method);
	}

	/**
	 * Callback interface invoked on each field in the hierarchy.
	 */
	public interface FieldCallback {

		/**
		 * Perform an operation using the given field.
		 * 
		 * @param field
		 *            the field to operate on
		 */
		void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
	}

	/**
	 * Callback optionally used to filter fields to be operated on by a field
	 * callback.
	 */
	public interface FieldFilter {

		/**
		 * Determine whether the given field matches.
		 * 
		 * @param field
		 *            the field to check
		 */
		boolean matches(Field field);
	}

	/**
	 * Pre-built FieldFilter that matches all non-static, non-final fields.
	 */
	public static FieldFilter COPYABLE_FIELDS = new FieldFilter() {

		public boolean matches(Field field) {
			return !(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()));
		}
	};
	/**
	 * Pre-built MethodFilter that matches all non-bridge methods.
	 */
	public static MethodFilter NON_BRIDGED_METHODS = new MethodFilter() {

		public boolean matches(Method method) {
			return !method.isBridge();
		}
	};
	/**
	 * Pre-built MethodFilter that matches all non-bridge methods which are not
	 * declared on <code>java.lang.Object</code>.
	 */
	public static MethodFilter USER_DECLARED_METHODS = new MethodFilter() {

		public boolean matches(Method method) {
			return (!method.isBridge() && method.getDeclaringClass() != Object.class);
		}
	};
	
	/**
     * 暴力调用对象函数,忽略private,protected修饰符的限制.
     * @throws Exception 
     */
    public static Object invokePrivateMethod(Object object, String methodName, Class<?>[] types, Object[] params)
            throws Exception {
        AdqAssert.notNull(object);
        AdqAssert.notNull(methodName);
        types = types != null ? types : new Class<?>[0];
        params = params != null ? params : new Object[0];

        AdqAssert.isTrue(types.length == params.length, "Types's length must equals the Params's length.");

        Class<?> clazz = object.getClass();
        Method method = null;
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                method = superClass.getDeclaredMethod(methodName, types);
                break;
            } catch (NoSuchMethodException e) {
                // 方法不在当前类定继续向上转型
            }
        }

        if (method == null) {
            StringBuilder message = new StringBuilder("No Such Method:" + clazz.getName() + "." + methodName + "(");
            if (types != null) {
                for (int index = 0; index < types.length; index++) {
                    message.append(types[index]).append(" ").append(params[index]);
                    if (index < types.length - 1) {
                        message.append(", ");
                    }
                }
            }
            throw new NoSuchMethodException(message.toString());
        }
        boolean accessible = method.isAccessible();
        method.setAccessible(true);
        Object result = null;
        try {
            result = method.invoke(object, params);
        } catch (Exception e) {
            throw e;
        }
        method.setAccessible(accessible);
        return result;
    }
    
    /**
     * 获得field的getter函数,如果找不到该方法,返回null.
     */
    public static Method getGetterMethod(Class<?> type, String fieldName) {
        try {
            return type.getMethod(getGetterName(type, fieldName));
        } catch (NoSuchMethodException e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }
    
    /**
     * 获得field的getter函数名称.
     */
    public static String getGetterName(Class<?> type, String fieldName) {
        AdqAssert.notNull(type, "Type required");
        AdqAssert.notNull(fieldName, "FieldName required");

        if (type.getName().equals("boolean")) {
            return "is" + StringUtils.capitalize(fieldName);
        } else {
            return "get" + StringUtils.capitalize(fieldName);
        }
    }

    public static String getSetterName(Class<?> type, String fieldName) {
        AdqAssert.notNull(type, "Type required");
        AdqAssert.notNull(fieldName, "FieldName required");
        return "set" + StringUtils.capitalize(fieldName);
    }

    public static Method getSetterMethod(Class<?> type, String fieldName) throws SecurityException, NoSuchFieldException {
        try {
            return type.getMethod(getSetterName(type, fieldName), AdqFieldUtils.getNestedFieldType(type, fieldName));
        } catch (NoSuchMethodException e) {
            logger.warn("Error when execute getSetterMethod:[" + type + ", " + fieldName + "].", e);
        }
        return null;
    }
    
    /**
	 * Return the qualified name of the given method, consisting of fully
	 * qualified interface/class name + "." + method name.
	 * 
	 * @param method
	 *            the method
	 * @return the qualified name of the method
	 */
	public static String getQualifiedMethodName(Method method) {
		AdqAssert.notNull(method, "Method must not be null");
		return method.getDeclaringClass().getName() + "." + method.getName();
	}
    
}
