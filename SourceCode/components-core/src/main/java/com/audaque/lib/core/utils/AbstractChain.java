package com.audaque.lib.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任链实现
 * @author lindeshu <deshu.lin@audaque.com>
 */
public abstract  class AbstractChain<T, N> implements Chain<T, N> {

    private final Map<String, Entry<T, N>> name2entry = new ConcurrentHashMap<String, Entry<T, N>>();
    /** The chain head */
    protected final EntryImpl head;
    /** The chain tail */
    protected final EntryImpl tail;

    /**
     * Create a new default chain, associated with a session. It will only contain a
     * HeadHandler and a TailHandler.
     *
     */
    public AbstractChain() {
        head = new EntryImpl(null, null, "head", newHeadHandler());
        tail = new EntryImpl(head, null, "tail", newTailHandler());
        head.nextEntry = tail;
    }

    @Override
    public Entry<T, N> getEntry(String name) {
        Entry<T, N> e = name2entry.get(name);
        if (e == null) {
            return null;
        }
        return e;
    }

    @Override
    public Entry<T, N> getEntry(T handler) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (e.getHandler() == handler) {
                return e;
            }
            e = e.nextEntry;
        }
        return null;
    }

    @Override
    public T get(String name) {
        Entry<T, N> e = getEntry(name);
        if (e == null) {
            return null;
        }

        return e.getHandler();
    }

    @Override
    public N getNextHandler(String name) {
        Entry<T, N> e = getEntry(name);
        if (e == null) {
            return null;
        }

        return e.getNextHandler();
    }

    @Override
    public N getNextHandler(T handler) {
        Entry<T, N> e = getEntry(handler);
        if (e == null) {
            return null;
        }

        return e.getNextHandler();
    }

    @Override
    public synchronized void addFirst(String name, T handler) {
        checkAddable(name);
        register(head, name, handler);
    }

    @Override
    public synchronized void addLast(String name, T handler) {
        checkAddable(name);
        register(tail.prevEntry, name, handler);
    }

    @Override
    public synchronized void addBefore(String baseName, String name,
            T handler) {
        EntryImpl baseEntry = checkOldName(baseName);
        checkAddable(name);
        register(baseEntry.prevEntry, name, handler);
    }

    @Override
    public synchronized void addAfter(String baseName, String name,
            T handler) {
        EntryImpl baseEntry = checkOldName(baseName);
        checkAddable(name);
        register(baseEntry, name, handler);
    }

    @Override
    public synchronized T remove(String name) {
        EntryImpl entry = checkOldName(name);
        deregister(entry);
        return entry.getHandler();
    }

    @Override
    public synchronized void remove(T handler) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (e.getHandler() == handler) {
                deregister(e);
                return;
            }
            e = e.nextEntry;
        }
        throw new IllegalArgumentException("Handler not found: "
                + handler.getClass().getName());
    }

    @Override
    public synchronized T remove(Class<? extends T> handlerType) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (handlerType.isAssignableFrom(e.getHandler().getClass())) {
                T oldHandler = e.getHandler();
                deregister(e);
                return oldHandler;
            }
            e = e.nextEntry;
        }
        throw new IllegalArgumentException("Handler not found: "
                + handlerType.getName());
    }

    @Override
    public synchronized T replace(String name, T newHandler) {
        EntryImpl entry = checkOldName(name);
        T oldHandler = entry.getHandler();
        entry.setHandler(newHandler);
        return oldHandler;
    }

    @Override
    public synchronized void replace(T oldHandler, T newHandler) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (e.getHandler() == oldHandler) {
                e.setHandler(newHandler);
                return;
            }
            e = e.nextEntry;
        }
        throw new IllegalArgumentException("Handler not found: "
                + oldHandler.getClass().getName());
    }

    @Override
    public synchronized T replace(
            Class<? extends T> oldHandlerType, T newHandler) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (oldHandlerType.isAssignableFrom(e.getHandler().getClass())) {
                T oldHandler = e.getHandler();
                e.setHandler(newHandler);
                return oldHandler;
            }
            e = e.nextEntry;
        }
        throw new IllegalArgumentException("Handler not found: "
                + oldHandlerType.getName());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public synchronized void clear() {
        List<Chain.Entry> l = new ArrayList<Chain.Entry>(
                name2entry.values());
        for (Chain.Entry entry : l) {
            deregister((EntryImpl) entry);
        }
    }

    private void register(EntryImpl prevEntry, String name, T handler) {
        EntryImpl newEntry = new EntryImpl(prevEntry, prevEntry.nextEntry,
                name, handler);
        prevEntry.nextEntry.prevEntry = newEntry;
        prevEntry.nextEntry = newEntry;
        name2entry.put(name, newEntry);
    }

    private void deregister(EntryImpl entry) {
        EntryImpl prevEntry = entry.prevEntry;
        EntryImpl nextEntry = entry.nextEntry;
        prevEntry.nextEntry = nextEntry;
        nextEntry.prevEntry = prevEntry;

        name2entry.remove(entry.name);
    }

    /**
     * Throws an exception when the specified handler name is not registered in this chain.
     *
     * @return An handler entry with the specified name.
     */
    private EntryImpl checkOldName(String baseName) {
        EntryImpl e = (EntryImpl) name2entry.get(baseName);
        if (e == null) {
            throw new IllegalArgumentException("Handler not found:" + baseName);
        }
        return e;
    }

    /**
     * Checks the specified handler name is already taken and throws an exception if already taken.
     */
    private void checkAddable(String name) {
        if (name2entry.containsKey(name)) {
            throw new IllegalArgumentException(
                    "Other handler is using the same name '" + name + "'");

        }
    }

    @Override
    public List<Entry<T, N>> getAll() {
        List<Entry<T, N>> list = new ArrayList<Entry<T, N>>();
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            list.add(e);
            e = e.nextEntry;
        }

        return list;
    }

    @Override
    public List<Entry<T, N>> getAllReversed() {
        List<Entry<T, N>> list = new ArrayList<Entry<T, N>>();
        EntryImpl e = tail.prevEntry;
        while (e != head) {
            list.add(e);
            e = e.prevEntry;
        }
        return list;
    }

    @Override
    public boolean contains(String name) {
        return getEntry(name) != null;
    }

    @Override
    public boolean contains(T handler) {
        return getEntry(handler) != null;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("{ ");

        boolean empty = true;

        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (!empty) {
                buf.append(", ");
            } else {
                empty = false;
            }

            buf.append('(');
            buf.append(e.getName());
            buf.append(':');
            buf.append(e.getHandler());
            buf.append(')');

            e = e.nextEntry;
        }

        if (empty) {
            buf.append("empty");
        }

        buf.append(" }");

        return buf.toString();
    }

    protected abstract  T newHeadHandler();
    
    protected abstract  T newTailHandler();

    protected abstract N newNextHandler(EntryImpl entryImpl);
    
    protected class EntryImpl implements Entry<T, N> {

        protected EntryImpl prevEntry;
        protected EntryImpl nextEntry;
        protected final String name;
        protected T handler;
        protected final N nextHandler;

        private EntryImpl(EntryImpl prevEntry, EntryImpl nextEntry,
                String name, T handler) {
            if (handler == null) {
                throw new IllegalArgumentException("handler");
            }
            if (name == null) {
                throw new IllegalArgumentException("name");
            }

            this.prevEntry = prevEntry;
            this.nextEntry = nextEntry;
            this.name = name;
            this.handler = handler;
            this.nextHandler = AbstractChain.this.newNextHandler(this);
        }

        public EntryImpl getNextEntry() {
            return nextEntry;
        }

        public EntryImpl getPrevEntry() {
            return prevEntry;
        }
        
        @Override
        public String getName() {
            return name;
        }

        @Override
        public T getHandler() {
            return handler;
        }

        private void setHandler(T handler) {
            if (handler == null) {
                throw new IllegalArgumentException("handler");
            }

            this.handler = handler;
        }

        @Override
        public N getNextHandler() {
            return nextHandler;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            // Add the current handler
            sb.append("('").append(getName()).append('\'');

            // Add the previous handler
            sb.append(", prev: '");

            if (prevEntry != null) {
                sb.append(prevEntry.name);
                sb.append(':');
                sb.append(prevEntry.getHandler().getClass().getSimpleName());
            } else {
                sb.append("null");
            }

            // Add the next handler
            sb.append("', next: '");

            if (nextEntry != null) {
                sb.append(nextEntry.name);
                sb.append(':');
                sb.append(nextEntry.getHandler().getClass().getSimpleName());
            } else {
                sb.append("null");
            }

            sb.append("')");
            return sb.toString();
        }

        @Override
        public void addAfter(String name, T handler) {
            AbstractChain.this.addAfter(getName(), name, handler);
        }

        @Override
        public void addBefore(String name, T handler) {
            AbstractChain.this.addBefore(getName(), name, handler);
        }

        @Override
        public void remove() {
            AbstractChain.this.remove(getName());
        }

        @Override
        public void replace(T newHandler) {
            AbstractChain.this.replace(getName(), newHandler);
        }
    }
}
