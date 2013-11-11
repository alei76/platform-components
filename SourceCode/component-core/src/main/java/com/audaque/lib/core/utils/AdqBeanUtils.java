package com.audaque.lib.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author lindeshu
 *
 */
public abstract class AdqBeanUtils extends org.apache.commons.beanutils.BeanUtils {

    private final static Log logger = LogFactory.getLog(AdqBeanUtils.class);

    /**
     * 暴力获取对象变量，忽略private,protected修饰符的限制.
     *
     * @throws NoSuchFieldException 如果没有该Field时抛
     * @throws InvocationTargetException when abend
     * @throws IllegalArgumentException     when abend
     */
    public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException, IllegalArgumentException, InvocationTargetException {
        AdqAssert.notNull(object);
        AdqAssert.notNull(propertyName);

        String[] cells = propertyName.split("\\.");
        Object result = object;
        for (String cell : cells) {
            result = doForceGetProperty(result, cell);
        }
        return result;
    }

    private static Object doForceGetProperty(Object object, String propertyName) throws NoSuchFieldException, IllegalArgumentException, InvocationTargetException {
        AdqAssert.notNull(object);
        AdqAssert.notNull(propertyName);

        int index = -1;
        Matcher matcher = Pattern.compile("^(.*)\\[(\\d+)\\]").matcher(propertyName);
        if (matcher.matches()) {
            matcher.toMatchResult();
            propertyName = matcher.group(1);
            index = Integer.parseInt(matcher.group(2));
        }
        Object entity = object;
        if (StringUtils.isNotEmpty(propertyName)) {
            Method method = AdqMethodUtils.getGetterMethod(object.getClass(), propertyName);
            boolean accessible = method.isAccessible();
            method.setAccessible(true);
            try {
                entity = method.invoke(object);
            } catch (IllegalAccessException e) {
                logger.warn("Should not be happened.", e);
            } finally {
                method.setAccessible(accessible);
            }
        }
        if (index >= 0) {
            return indexOf(entity, propertyName, index);
        }
        return entity;
    }

    @SuppressWarnings("rawtypes")
    private static Object indexOf(Object entity, String propertyName, int index) {
        if (entity == null) {
            String error = "The property[" + propertyName + "] must not null.";
            logger.warn(error);
            throw new IllegalStateException(error);
        }
        Object result = entity;
        if (entity.getClass().isArray()) {
            result = ((Object[]) result)[index];
        } else if (entity instanceof List) {
            result = ((List) entity).get(index);
        } else if (entity instanceof Set) {
            Set set = (Set) entity;
            if (index >= set.size()) {
                throw new IllegalStateException("Index[" + index + " is out of bounds " + set.size() + ".");
            }
            int temp = 0;
            for (Object element : set) {
                if (temp == index) {
                    result = element;
                    break;
                }
                temp++;
            }
        } else {
            throw new IllegalStateException("Unspported type[" + entity.getClass() + "].");
        }
        return result;
    }

    /**
     * 暴力设置对象变量，忽略private,protected修饰符的限制.
     *
     * @throws NoSuchFieldException 如果没有该Field时抛
     * @throws InvocationTargetException when abend
     * @throws IllegalArgumentException     when abend
     */
    public static void forceSetProperty(Object object, String propertyName, Object value) 
                throws NoSuchFieldException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        AdqAssert.notNull(object);
        AdqAssert.notNull(propertyName);

        String[] cells = propertyName.split("\\.");
        Object result = object;
        for (int i = 0; i < cells.length - 1; i++) {
            result = doForceGetProperty(result, cells[i]);
        }
        doForceSetProperty(result, cells[cells.length - 1], value);
    }

    /**
     * 暴力设置对象变量忽略private,protected修饰符的限制.
     *
     * @throws NoSuchFieldException 如果没有该Field时抛
     * @throws InvocationTargetException when abend
     * @throws IllegalArgumentException     when abend
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void doForceSetProperty(Object object, String propertyName, Object newValue)
            throws NoSuchFieldException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        AdqAssert.notNull(object);
        AdqAssert.notNull(propertyName);

        int index = -1;
        Matcher matcher = Pattern.compile("^(.*)\\[(\\d+)\\]").matcher(propertyName);
        if (matcher.matches()) {
            matcher.toMatchResult();
            propertyName = matcher.group(1);
            index = Integer.parseInt(matcher.group(2));
        }

        Object entity = object;
        if (StringUtils.isEmpty(propertyName) && index >= 0) {
            if (entity == null) {
                throw new IllegalStateException("Property[" + propertyName + "] must not be null.");
            }
            if (entity.getClass().isArray()) {
                ((Object[]) entity)[index] = newValue;
            } else if (entity instanceof List) {
                ((List) entity).set(index, newValue);
            } else if (entity instanceof Set) {
                Set set = (Set) entity;
                List temp = new ArrayList(set);
                if (index >= set.size()) {
                    throw new IllegalStateException("Index[" + index + " is out of bounds " + set.size() + ".");
                }
                for (int i = 0; i < set.size(); i++) {
                    if (i == index) {
                        set.add(newValue);
                    } else {
                        set.add(temp.get(i));
                    }
                }
            } else {
                throw new IllegalStateException("Unspported type[" + entity.getClass() + "].");
            }
        } else {
            Method method = null;
            if(newValue != null) {
                try {
                    method = object.getClass().getMethod(AdqMethodUtils.getSetterName(newValue.getClass(), propertyName), newValue.getClass());
                } catch (Exception ignore) {
                }
            }
            if(method == null) {
                method = AdqMethodUtils.getSetterMethod(object.getClass(), propertyName);
            }
            if(method == null) {
                throw new NoSuchMethodException("No setter method for property[" + propertyName + " in[ " + object.getClass() + "]." );
            }
            boolean accessible = method.isAccessible();
            method.setAccessible(true);
            try {
                method.invoke(object, newValue);
            } catch (IllegalAccessException e) {
                logger.warn("error wont' happen", e);
            } finally {
                method.setAccessible(accessible);
            }
        }

    }

    /**
     * 按Filed的类型取得Field列表.
     */
    public static List<Field> getFieldsByType(Object object, Class<?> type) {
        List<Field> list = new ArrayList<Field>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().isAssignableFrom(type)) {
                list.add(field);
            }
        }
        return list;
    }
}
