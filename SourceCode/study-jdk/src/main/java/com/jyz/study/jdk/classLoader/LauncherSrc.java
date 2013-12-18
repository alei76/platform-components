//package com.jyz.study.jdk.classLoader;
//
///**
// * 自定义ClassLoader
// * 
// * @author JoyoungZhang@gmail.com
// * 
// */
//public class LauncherSrc {
//    private static URLStreamHandlerFactory factory = new Factory();
//    private static Launcher launcher = new Launcher();
//
//    public static Launcher getLauncher() {
//        return launcher;
//    }
//
//    private ClassLoader loader;
//    
//    //ClassLoader.getSystemClassLoader会调用此方法
//    public ClassLoader getClassLoader() {
//    	return loader;
//    }
//
//    public Launcher() {
//        // 1. 创建ExtClassLoader 
//        ClassLoader extcl;
//        try {
//            extcl = ExtClassLoader.getExtClassLoader();
//        } catch (IOException e) {
//            throw new InternalError(
//                "Could not create extension class loader");
//        }
//
//        // 2. 用ExtClassLoader作为parent去创建AppClassLoader 
//        try {
//            loader = AppClassLoader.getAppClassLoader(extcl);
//        } catch (IOException e) {
//            throw new InternalError(
//                "Could not create application class loader");
//        }
//
//        // 3. 设置ContextClassLoader为AppClassLoader
//        Thread.currentThread().setContextClassLoader(loader);
//        //...
//    }
//
//    static class ExtClassLoader extends URLClassLoader {
//        private File[] dirs;
//
//        public static ExtClassLoader getExtClassLoader() throws IOException
//        {
//            final File[] dirs = getExtDirs();
//            return new ExtClassLoader(dirs);
//        }
//
//        public ExtClassLoader(File[] dirs) throws IOException {
//            super(getExtURLs(dirs), null, factory);
//            this.dirs = dirs;
//        }
//
//        private static File[] getExtDirs() {
//            String s = System.getProperty("java.ext.dirs");
//            File[] dirs;
//            //...
//            return dirs;
//        }
//    }
//
//    /**
//     * The class loader used for loading from java.class.path.
//     * runs in a restricted security context.
//     */
//    static class AppClassLoader extends URLClassLoader {
//
//        public static ClassLoader getAppClassLoader(final ClassLoader extcl)
//            throws IOException
//        {
//            final String s = System.getProperty("java.class.path");
//            final File[] path = (s == null) ? new File[0] : getClassPath(s);
//
//            URL[] urls = (s == null) ? new URL[0] : pathToURLs(path);
//            return new AppClassLoader(urls, extcl);
//        }
//
//        AppClassLoader(URL[] urls, ClassLoader parent) {
//            super(urls, parent, factory);
//        }
//        
//        /**
//         * Override loadClass so we can checkPackageAccess.
//         * 这个方法似乎没什么必要，因为super.loadClass(name, resolve)时也会checkPackageAccess
//         */
//        public synchronized Class loadClass(String name, boolean resolve)
//            throws ClassNotFoundException
//        {
//            int i = name.lastIndexOf('.');
//            if (i != -1) {
//                SecurityManager sm = System.getSecurityManager();
//                if (sm != null) {
//                	//
//                    sm.checkPackageAccess(name.substring(0, i));
//                }
//            }
//            return (super.loadClass(name, resolve));
//        }
//
//    }
//}
