package com.audaque.lib.core.utils;

import java.util.List;

/**
 * 责任链
 * @author lindeshu <deshu.lin@audaque.com>
 */
public interface Chain<T, N> {

    /**
     * Returns the {@link Entry} with the specified <tt>name</tt> in this chain.
     * 
     * @param name The handler's name we are looking for
     * @return <tt>null</tt> if there's no such name in this chain
     */
    Entry<T, N> getEntry(String name);

    /**
     * Returns the {@link Entry} with the specified <tt>handler</tt> in this chain.
     * 
     * @param handler  The Handler we are looking for
     * @return <tt>null</tt> if there's no such handler in this chain
     */
    Entry<T, N> getEntry(T handler);

    /**
     * Returns the {@link T} with the specified <tt>name</tt> in this chain.
     * 
     * @param name the handler's name
     * @return <tt>null</tt> if there's no such name in this chain
     */
    T get(String name);

    /**
     * Returns the {@link NextHandler} of the {@link T} with the
     * specified <tt>name</tt> in this chain.
     * 
     * @param name The handler's name we want the next handler
     * @return <tt>null</tt> if there's no such name in this chain
     */
    N getNextHandler(String name);

    /**
     * Returns the {@link NextHandler} of the specified {@link T}
     * in this chain.
     * 
     * @param handler The handler for which we want the next handler
     * @return <tt>null</tt> if there's no such name in this chain
     */
    N getNextHandler(T handler);

    /**
     * @return The list of all {@link Entry}s this chain contains.
     */
    List<Entry<T, N>> getAll();

    /**
     * @return The reversed list of all {@link Entry}s this chain contains.
     */
    List<Entry<T, N>> getAllReversed();

    /**
     * @param name The handler's name we are looking for
     * 
     * @return <tt>true</tt> if this chain contains an {@link T} with the
     * specified <tt>name</tt>.
     */
    boolean contains(String name);

    /**
     * @param handler The handler we are looking for
     * 
     * @return <tt>true</tt> if this chain contains the specified <tt>handler</tt>.
     */
    boolean contains(T handler);

    /**
     * Adds the specified handler with the specified name at the beginning of this chain.
     * 
     * @param name The handler's name
     * @param handler The handler to add
     */
    void addFirst(String name, T handler);

    /**
     * Adds the specified handler with the specified name at the end of this chain.
     * 
     * @param name The handler's name
     * @param handler The handler to add
     */
    void addLast(String name, T handler);

    /**
     * Adds the specified handler with the specified name just before the handler whose name is
     * <code>baseName</code> in this chain.
     * 
     * @param baseName The targeted Handler's name
     * @param name The handler's name
     * @param handler The handler to add
     */
    void addBefore(String baseName, String name, T handler);

    /**
     * Adds the specified handler with the specified name just after the handler whose name is
     * <code>baseName</code> in this chain.
     * 
     * @param baseName The targeted Handler's name
     * @param name The handler's name
     * @param handler The handler to add
     */
    void addAfter(String baseName, String name, T handler);

    /**
     * Replace the handler with the specified name with the specified new
     * handler.
     *
     * @param name The name of the handler we want to replace
     * @param newHandler The new handler
     * @return the old handler
     */
    T replace(String name, T newHandler);

    /**
     * Replace the handler with the specified name with the specified new
     * handler.
     *
     * @param oldHandler The handler we want to replace
     * @param newHandler The new handler
     */
    void replace(T oldHandler, T newHandler);

    /**
     * Replace the handler of the specified type with the specified new
     * handler.  If there's more than one handler with the specified type,
     * the first match will be replaced.
     *
     * @param oldHandlerType The handler class we want to replace
     * @param newHandler The new handler
     */
    T replace(Class<? extends T> oldHandlerType, T newHandler);

    /**
     * Removes the handler with the specified name from this chain.
     * 
     * @param name The name of the handler to remove
     * @return The removed handler
     */
    T remove(String name);

    /**
     * Replace the handler with the specified name with the specified new
     * handler.
     *
     * @param name The handler to remove
     */
    void remove(T handler);

    /**
     * Replace the handler of the specified type with the specified new
     * handler.  If there's more than one handler with the specified type,
     * the first match will be replaced.
     *
     * @param name The handler class to remove
     * @return The removed handler
     */
    T remove(Class<? extends T> handlerType);

    /**
     * Removes all handlers added to this chain.
     */
    void clear();

    /**
     * Represents a name-handler pair that an {@link Chain} contains.
     */
    public interface Entry<T, N> {

        /**
         * Returns the name of the handler.
         */
        String getName();

        T getHandler();

        /**
         * @return The {@link NextHandler} of the handler.
         */
        N getNextHandler();

        /**
         * Adds the specified handler with the specified name just before this entry.
         */
        void addBefore(String name, T handler);

        /**
         * Adds the specified handler with the specified name just after this entry.
         */
        void addAfter(String name, T handler);

        /**
         * Replace the handler of this entry with the specified new handler.
         */
        void replace(T newHandler);

        /**
         * Removes this entry from the chain it belongs to.
         */
        void remove();
    }
}
