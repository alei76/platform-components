package com.audaque.lib.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author deshu.lin (deshu.lin@audaque.com)
 *
 */
public abstract class AdqCollectionUtils extends CollectionUtils {

    public static <E> Collection<E> removeIfNullExists(Collection<E> source) {
        if(source == null) {
            return null;
        }
        if(source.isEmpty()) {
            return new ArrayList<E>(0);
        }
        Collection<E> newCollection = new ArrayList<E>(source);
        for(Iterator<E> iterator = newCollection.iterator(); iterator.hasNext();) {
            E entry = iterator.next();
            if(entry == null) {
                iterator.remove();
            }
        }
        return newCollection;
    }

    /**
     * map是否为空
     */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}
	
    /**
     * Convert the supplied array into a List. A primitive array gets
     * converted into a List of the appropriate wrapper type.
     * <p>A <code>null</code> source value will be converted to an
     * empty List.
     * @param source the (potentially primitive) array
     * @return the converted List result
     * @see AdqObjectUtils#toObjectArray(Object)
     */
    public static List<?> arrayToList(Object source) {
        return Arrays.asList(AdqArrayUtils.toObjectArray(source));
    }

    /**
     * Merge the given array into the given Collection.
     * @param array the array to merge (may be <code>null</code>)
     * @param collection the target Collection<?> to merge the array into
     */
    @SuppressWarnings("unchecked")
    public static void mergeArrayIntoCollection(Object array, @SuppressWarnings("rawtypes") Collection collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection<?> must not be null");
        }
        Object[] arr = AdqArrayUtils.toObjectArray(array);
        for (Object elem : arr) {
            collection.add(elem);
        }
    }

    /**
     * Merge the given Properties instance into the given Map,
     * copying all properties (key-value pairs) over.
     * <p>Uses <code>Properties.propertyNames()</code> to even catch
     * default properties linked into the original Properties instance.
     * @param props the Properties instance to merge (may be <code>null</code>)
     * @param map the target Map<?, ?> to merge the properties into
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void mergePropertiesIntoMap(Properties props, Map map) {
        if (map == null) {
            throw new IllegalArgumentException("Map must not be null");
        }
        if (props != null) {
            for (Enumeration en = props.propertyNames(); en.hasMoreElements();) {
                String key = (String) en.nextElement();
                Object value = props.getProperty(key);
                if (value == null) {
                    // Potentially a non-String value...
                    value = props.get(key);
                }
                map.put(key, value);
            }
        }
    }

    /**
     * Check whether the given Iterator contains the given element.
     * @param iterator the Iterator to check
     * @param element the element to look for
     * @return <code>true</code> if found, <code>false</code> else
     */
    public static boolean contains(Iterator<?> iterator, Object element) {
        if (iterator != null) {
            while (iterator.hasNext()) {
                Object candidate = iterator.next();
                if (AdqObjectUtils.nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check whether the given Enumeration contains the given element.
     * @param enumeration the Enumeration to check
     * @param element the element to look for
     * @return <code>true</code> if found, <code>false</code> else
     */
    public static boolean contains(Enumeration<?> enumeration, Object element) {
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                Object candidate = enumeration.nextElement();
                if (AdqObjectUtils.nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check whether the given Collection<?> contains the given element instance.
     * <p>Enforces the given instance to be present, rather than returning
     * <code>true</code> for an equal element as well.
     * @param collection the Collection<?> to check
     * @param element the element to look for
     * @return <code>true</code> if found, <code>false</code> else
     */
    public static boolean containsInstance(Collection<?> collection, Object element) {
        if (collection != null) {
            for (Object candidate : collection) {
                if (candidate == element) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return the first element in '<code>candidates</code>' that is contained in
     * '<code>source</code>'. If no element in '<code>candidates</code>' is present in
     * '<code>source</code>' returns <code>null</code>. Iteration order is
     * {@link Collection} implementation specific.
     * @param source the source Collection
     * @param candidates the candidates to search for
     * @return the first present object, or <code>null</code> if not found
     */
    public static Object findFirstMatch(Collection<?> source, Collection<?> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) {
            return null;
        }
        for (Object candidate : candidates) {
            if (source.contains(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    /**
     * Find a single value of the given type in the given Collection.
     * @param collection the Collection<?> to search
     * @param type the type to look for
     * @return a value of the given type found if there is a clear match,
     * or <code>null</code> if none or more than one such value found
     */
    @SuppressWarnings("unchecked")
    public static <T> T findValueOfType(Collection<?> collection, Class<T> type) {
        if (isEmpty(collection)) {
            return null;
        }
        T value = null;
        for (Object element : collection) {
            if (type == null || type.isInstance(element)) {
                if (value != null) {
                    // More than one value found... no clear single value.
                    return null;
                }
                value = (T) element;
            }
        }
        return value;
    }

    /**
     * Find a single value of one of the given types in the given Collection:
     * searching the Collection<?> for a value of the first type, then
     * searching for a value of the second type, etc.
     * @param collection the collection to search
     * @param types the types to look for, in prioritized order
     * @return a value of one of the given types found if there is a clear match,
     * or <code>null</code> if none or more than one such value found
     */
    public static Object findValueOfType(Collection<?> collection, Class<?>[] types) {
        if (isEmpty(collection) || AdqArrayUtils.isEmpty(types)) {
            return null;
        }
        for (Class<?> type : types) {
            Object value = findValueOfType(collection, type);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
    
    /**
     * Check whether the given array of enum constants contains a constant with the given name,
     * ignoring case when determining a match.
     * @param enumValues the enum values to check, typically the product of a call to MyEnum.values()
     * @param constant the constant name to find (must not be null or empty string)
     * @return whether the constant has been found in the given array
     */
    public static boolean containsConstant(Enum<?>[] enumValues, String constant) {
        return containsConstant(enumValues, constant, false);
    }

    /**
     * Check whether the given array of enum constants contains a constant with the given name.
     * @param enumValues the enum values to check, typically the product of a call to MyEnum.values()
     * @param constant the constant name to find (must not be null or empty string)
     * @param caseSensitive whether case is significant in determining a match
     * @return whether the constant has been found in the given array
     */
    public static boolean containsConstant(Enum<?>[] enumValues, String constant, boolean caseSensitive) {
        for (Enum<?> candidate : enumValues) {
            if (caseSensitive
                    ? candidate.toString().equals(constant)
                    : candidate.toString().equalsIgnoreCase(constant)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Case insensitive alternative to {@link Enum#valueOf(Class, String)}.
     * @param <E> the concrete Enum type
     * @param enumValues the array of all Enum constants in question, usually per Enum.values()
     * @param constant the constant to get the enum value of
     * @throws IllegalArgumentException if the given constant is not found in the given array
     * of enum values. Use {@link #containsConstant(Enum[], String)} as a guard to avoid this exception.
     */
    public static <E extends Enum<?>> E caseInsensitiveValueOf(E[] enumValues, String constant) {
        for (E candidate : enumValues) {
            if (candidate.toString().equalsIgnoreCase(constant)) {
                return candidate;
            }
        }
        throw new IllegalArgumentException(
                String.format("constant [%s] does not exist in enum type %s",
                constant, enumValues.getClass().getComponentType().getName()));
    }


    /**
     * Determine whether the given Collection<?> only contains a single unique object.
     * @param collection the Collection<?> to check
     * @return <code>true</code> if the collection contains a single reference or
     * multiple references to the same instance, <code>false</code> else
     */
    public static boolean hasUniqueObject(Collection<?> collection) {
        if (isEmpty(collection)) {
            return false;
        }
        boolean hasCandidate = false;
        Object candidate = null;
        for (Object elem : collection) {
            if (!hasCandidate) {
                hasCandidate = true;
                candidate = elem;
            } else if (candidate != elem) {
                return false;
            }
        }
        return true;
    }

    /**
     * Find the common element type of the given Collection, if any.
     * @param collection the Collection<?> to check
     * @return the common element type, or <code>null</code> if no clear
     * common type has been found (or the collection was empty)
     */
    public static Class<?> findCommonElementType(Collection<?> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        Class<?> candidate = null;
        for (Object val : collection) {
            if (val != null) {
                if (candidate == null) {
                    candidate = val.getClass();
                } else if (candidate != val.getClass()) {
                    return null;
                }
            }
        }
        return candidate;
    }

    /**
     * Marshal the elements from the given enumeration into an array of the given type.
     * Enumeration elements must be assignable to the type of the given array. The array
     * returned will be a different instance than the array given.
     */
    public static <A, E extends A> A[] toArray(Enumeration<E> enumeration, A[] array) {
        ArrayList<A> elements = new ArrayList<A>();
        while (enumeration.hasMoreElements()) {
            elements.add(enumeration.nextElement());
        }
        return elements.toArray(array);
    }

    /**
     * Adapt an enumeration to an iterator.
     * @param enumeration the enumeration
     * @return the iterator
     */
    public static <E> Iterator<E> toIterator(Enumeration<E> enumeration) {
        return new EnumerationIterator<E>(enumeration);
    }

    /**
     * Iterator wrapping an Enumeration.
     */
    private static class EnumerationIterator<E> implements Iterator<E> {

        private Enumeration<E> enumeration;

        public EnumerationIterator(Enumeration<E> enumeration) {
            this.enumeration = enumeration;
        }

        public boolean hasNext() {
            return this.enumeration.hasMoreElements();
        }

        public E next() {
            return this.enumeration.nextElement();
        }

        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Not supported");
        }
    }

}
