package com.audaque.lib.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.StringTokenizer;

import org.apache.commons.lang.reflect.FieldUtils;

/**
 * @author deshu.lin (deshu.lin@audaque.com)
 *
 */
public class AdqFieldUtils extends FieldUtils {

    /**
     * Attempt to find a {@link Field field} on the supplied {@link Class} with the
     * supplied <code>name</code>. Searches all superclasses up to {@link Object}.
     * @param clazz the class to introspect
     * @param name the name of the field
     * @return the corresponding Field object, or <code>null</code> if not found
     */
    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, null);
    }
    
    /**
     * Determine whether the given field is a "public static final" constant.
     * @param field the field to check
     */
    public static boolean isPublicStaticFinal(Field field) {
        int modifiers = field.getModifiers();
        return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
    }
    
    /**
     * Make the given field accessible, explicitly setting it accessible if
     * necessary. The <code>setAccessible(true)</code> method is only called
     * when actually necessary, to avoid unnecessary conflicts with a JVM
     * SecurityManager (if active).
     * @param field the field to make accessible
     * @see java.lang.reflect.Field#setAccessible
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * Attempt to find a {@link Field field} on the supplied {@link Class} with the
     * supplied <code>name</code> and/or {@link Class type}. Searches all superclasses
     * up to {@link Object}.
     * @param clazz the class to introspect
     * @param name the name of the field (may be <code>null</code> if type is specified)
     * @param type the type of the field (may be <code>null</code> if name is specified)
     * @return the corresponding Field object, or <code>null</code> if not found
     */
    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        AdqAssert.notNull(clazz, "Class must not be null");
        AdqAssert.isTrue(name != null || type != null, "Either name or type of the field must be specified");
        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }
    
    /**
     * 按FiledName获得Field的类
     */
    public static Class<?> getFieldType(Class<?> type, String name) throws NoSuchFieldException {
        return AdqFieldUtils.findField(type, name).getType();
    }

    public static Class<?> getNestedFieldType(Class<?> type, String nestedName) throws NoSuchFieldException {
        String subName = nestedName;
        for (StringTokenizer nameToken = new StringTokenizer(nestedName, ".");
                nameToken.hasMoreTokens();) {
            String name = nameToken.nextToken();
            if (nameToken.hasMoreTokens()) {
                subName = nestedName.substring(name.length() + 1);
                return getFieldType(getFieldType(type, name), subName);
            } else {
                return getFieldType(type, name);
            }
        }
        throw new NoSuchFieldException("No such field: " + type.getName() + '.' + nestedName);
    }

	
}
