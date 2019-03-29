package io.fighter.pantheon.util;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author xiasx
 * @create 2019-03-20 17:42
 **/
public final class ClassUtil {

    private ClassUtil() {
    }

    public static List<Class> getAllClassByInterface(Class c) {
        List<Class> returnClassList = null;
        if (c.isInterface()) {
            String packageName = c.getPackage().getName();
            List<Class<?>> allClass = getClasses(packageName);
            if (allClass != null) {
                returnClassList = Lists.newArrayList();
                Iterator i$ = allClass.iterator();

                while(i$.hasNext()) {
                    Class classes = (Class)i$.next();
                    if (c.isAssignableFrom(classes) && !c.equals(classes)) {
                        returnClassList.add(classes);
                    }
                }
            }
        }

        return returnClassList;
    }

    public static String[] getPackageAllClassName(String classLocation, String packageName) {
        String[] packagePathSplit = packageName.split("[.]");
        String realClassLocation = classLocation;
        String[] arr$ = packagePathSplit;
        int len$ = packagePathSplit.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String aPackagePathSplit = arr$[i$];
            realClassLocation = realClassLocation + File.separator + aPackagePathSplit;
        }

        File packeageDir = new File(realClassLocation);
        return packeageDir.isDirectory() ? packeageDir.list() : null;
    }

    public static List<Class<?>> getClasses(String packageName) {
        List<Class<?>> classes = Lists.newArrayList();
        boolean recursive = true;
        String packageDirName = packageName.replace('.', '/');

        try {
            Enumeration dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

            while(true) {
                label67:
                while(dirs.hasMoreElements()) {
                    URL url = (URL)dirs.nextElement();
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                        findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                    } else if ("jar".equals(protocol)) {
                        try {
                            JarFile jar = ((JarURLConnection)url.openConnection()).getJarFile();
                            Enumeration entries = jar.entries();

                            while(true) {
                                JarEntry entry;
                                String name;
                                int idx;
                                do {
                                    do {
                                        if (!entries.hasMoreElements()) {
                                            continue label67;
                                        }

                                        entry = (JarEntry)entries.nextElement();
                                        name = entry.getName();
                                        if (name.charAt(0) == '/') {
                                            name = name.substring(1);
                                        }
                                    } while(!name.startsWith(packageDirName));

                                    idx = name.lastIndexOf(47);
                                    if (idx != -1) {
                                        packageName = name.substring(0, idx).replace('/', '.');
                                    }
                                } while(idx == -1 && !recursive);

                                if (name.endsWith(".class") && !entry.isDirectory()) {
                                    String className = name.substring(packageName.length() + 1, name.length() - 6);

                                    try {
                                        classes.add(Class.forName(packageName + '.' + className));
                                    } catch (ClassNotFoundException var14) {
                                        var14.printStackTrace();
                                    }
                                }
                            }
                        } catch (IOException var15) {
                            var15.printStackTrace();
                        }
                    }
                }

                return classes;
            }
        } catch (IOException var16) {
            var16.printStackTrace();
            return classes;
        }
    }

    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {
        File dir = new File(packagePath);
        if (dir.exists() && dir.isDirectory()) {
            File[] dirfiles = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return recursive && file.isDirectory() || file.getName().endsWith(".class");
                }
            });
            File[] arr$ = dirfiles;
            int len$ = dirfiles.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                File file = arr$[i$];
                if (file.isDirectory()) {
                    findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
                } else {
                    String className = file.getName().substring(0, file.getName().length() - 6);

                    try {
                        classes.add(Class.forName(packageName + '.' + className));
                    } catch (ClassNotFoundException var12) {
                        var12.printStackTrace();
                    }
                }
            }

        }
    }
}
