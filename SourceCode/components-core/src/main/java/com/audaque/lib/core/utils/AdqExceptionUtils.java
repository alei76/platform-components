package com.audaque.lib.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 
 * @author lindeshu <deshu.lin@audaque.com>
 */
public class AdqExceptionUtils extends org.apache.commons.lang.exception.ExceptionUtils {

	/**
     * Return whether the given throwable is a checked exception:
     * that is, neither a RuntimeException nor an Error.
     * @param ex the throwable to check
     * @return whether the throwable is a checked exception
     * @see java.lang.Exception
     * @see java.lang.RuntimeException
     * @see java.lang.Error
     */
    public static boolean isCheckedException(Throwable ex) {
        return !(ex instanceof RuntimeException || ex instanceof Error);
    }
    
    /**
     * Check whether the given exception is compatible with the exceptions
     * declared in a throws clause.
     * @param ex the exception to checked
     * @param declaredExceptions the exceptions declared in the throws clause
     * @return whether the given exception is compatible
     */
	public static boolean isCompatibleWithThrowsClause(Throwable ex, Class<?>[] declaredExceptions) {
        if (!isCheckedException(ex)) {
            return true;
        }
        if (declaredExceptions != null) {
            int i = 0;
            while (i < declaredExceptions.length) {
                if (declaredExceptions[i].isAssignableFrom(ex.getClass())) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }
	
	/**
	 * Determine whether the given method explicitly declares the given
	 * exception or one of its superclasses, which means that an exception of
	 * that type can be propagated as-is within a reflective invocation.
	 * 
	 * @param method
	 *            the declaring method
	 * @param exceptionType
	 *            the exception to throw
	 * @return <code>true</code> if the exception can be thrown as-is;
	 *         <code>false</code> if it needs to be wrapped
	 */
	public static boolean declaresException(Method method, Class<?> exceptionType) {
		AdqAssert.notNull(method, "Method must not be null");
		Class<?>[] declaredExceptions = method.getExceptionTypes();
		for (Class<?> declaredException : declaredExceptions) {
			if (declaredException.isAssignableFrom(exceptionType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Handle the given reflection exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>
	 * Throws the underlying RuntimeException or Error in case of an
	 * InvocationTargetException with such a root cause. Throws an
	 * IllegalStateException with an appropriate message else.
	 * 
	 * @param ex
	 *            the reflection exception to handle
	 */
	public static void handleReflectionException(Exception ex) {
		if (ex instanceof NoSuchMethodException) {
			throw new IllegalStateException("Method not found: " + ex.getMessage());
		}
		if (ex instanceof IllegalAccessException) {
			throw new IllegalStateException("Could not access method: " + ex.getMessage());
		}
		if (ex instanceof InvocationTargetException) {
			handleInvocationTargetException((InvocationTargetException) ex);
		}
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}

	/**
	 * Handle the given invocation target exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>
	 * Throws the underlying RuntimeException or Error in case of such a root
	 * cause. Throws an IllegalStateException else.
	 * 
	 * @param ex
	 *            the invocation target exception to handle
	 */
	public static void handleInvocationTargetException(InvocationTargetException ex) {
		rethrowRuntimeException(ex.getTargetException());
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}. Should
	 * only be called if no checked exception is expected to be thrown by the
	 * target method.
	 * <p>
	 * Rethrows the underlying exception cast to an {@link RuntimeException} or
	 * {@link Error} if appropriate; otherwise, throws an
	 * {@link IllegalStateException}.
	 * 
	 * @param ex
	 *            the exception to rethrow
	 * @throws RuntimeException
	 *             the rethrown exception
	 */
	public static void rethrowRuntimeException(Throwable ex) {
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}. Should
	 * only be called if no checked exception is expected to be thrown by the
	 * target method.
	 * <p>
	 * Rethrows the underlying exception cast to an {@link Exception} or
	 * {@link Error} if appropriate; otherwise, throws an
	 * {@link IllegalStateException}.
	 * 
	 * @param ex
	 *            the exception to rethrow
	 * @throws Exception
	 *             the rethrown exception (in case of a checked exception)
	 */
	public static void rethrowException(Throwable ex) throws Exception {
		if (ex instanceof Exception) {
			throw (Exception) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}
	
	/**
     * Build a message for the given base message and root cause.
     * @param message the base message
     * @param cause the root cause
     * @return the full exception message
     */
    public static String getNestedMessage(String message, Throwable cause) {
        if (cause != null) {
            StringBuilder sb = new StringBuilder();
            if (message != null) {
                sb.append(message).append("; ");
            }
            sb.append("nested exception is ").append(cause);
            return sb.toString();
        } else {
            return message;
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getCausedException(Throwable e, Class<T> targetType) {
        if (targetType == null) {
            return null;
        }
        Throwable caused = e;
        while (caused != null) {
            while(caused != null && caused instanceof InvocationTargetException) {
                caused = ((InvocationTargetException)caused).getTargetException();
            }
            if (targetType.isAssignableFrom(caused.getClass())) {
                return (T) caused;
            }
            caused = caused.getCause();
        }
        return null;
    }
    
    public static <T> T getException(Throwable source, Class<? extends T> targetType, T defaultException) {
        T caused = (T)getCausedException(source, targetType);
        if(caused != null) {
            return caused;
        }
        return defaultException;
    }
    
    public static <T> T getException(Throwable source, Class<T> targetType, DefaultExceptionCreator<? extends T> creator) {
        T caused = (T)getCausedException(source, targetType);
        if(caused != null) {
            return caused;
        }
        return creator.create(source);
    }

    public static interface DefaultExceptionCreator<T> {
        
        T create(Throwable source);
        
    }

}
