package com.cqut.genhoo.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;

public class ClassUtil {

	// 获取类加载器
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    // 获取类路径
    public static String getClassPath() {
        String classpath = "";
        URL resource = getClassLoader().getResource("");
        if (resource != null) {
            classpath = resource.getPath();
        }
        return classpath;
    }
    
    private static File[] getClassFiles(String packagePath) {
        return new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
    }
    
    private static String getClassName(String packageName, String fileName) {
        String className = fileName.substring(0, fileName.lastIndexOf("."));
        if (StringUtils.isNotEmpty(packageName)) {
            className = packageName + "." + className;
        }
        return className;
    }
    
    
    // 加载类
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            //logger.error("加载类出错！", e);
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
        return cls;
    }
    
    private static String getSubPackagePath(String packagePath, String filePath) {
        String subPackagePath = filePath;
        if (StringUtils.isNotEmpty(packagePath)) {
            subPackagePath = packagePath + "/" + subPackagePath;
        }
        return subPackagePath;
    }

    private static String getSubPackageName(String packageName, String filePath) {
        String subPackageName = filePath;
        if (StringUtils.isNotEmpty(packageName)) {
            subPackageName = packageName + "." + subPackageName;
        }
        return subPackageName;
    }
    
	public static List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass){
		List<Class<?>> classList = new ArrayList<Class<?>>();
		try {
			Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
			
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				//System.out.println("File:"+url.getFile());
				if (url!=null) {
					String protocol = url.getProtocol();
					if (protocol.equals("file")) {
						//System.out.println("this is file");
						String packagePath = url.getPath();
						//System.out.println(packagePath);
						addClassByAnnotation(classList, packagePath, packageName, annotationClass);
					}else if (protocol.equals("jar")) {
						JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> jarEntries = jarFile.entries();
                        while (jarEntries.hasMoreElements()) {
                            JarEntry jarEntry = jarEntries.nextElement();
                            String jarEntryName = jarEntry.getName();
                            if (jarEntryName.endsWith(".class")) {
                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                Class<?> cls = loadClass(className, false);
                                if (cls.isAnnotationPresent(annotationClass)) {
                                    classList.add(cls);
                                }
                            }
                        }
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		// 打印所有已经扫描到的 class
		//System.err.println(Arrays.deepToString(classList.toArray()));
		//
		return classList;
	}
	
	private static void addClassByAnnotation(List<Class<?>> classList, String packagePath, String packageName, Class<? extends Annotation> annotationClass) {
        try {
            File[] files = getClassFiles(packagePath);
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    //System.out.println("fileName:"+fileName);
                    if (file.isFile()) {
                        String className = getClassName(packageName, fileName);
                        Class<?> cls = loadClass(className, false);
                        if (cls.isAnnotationPresent(annotationClass)) {
                            classList.add(cls);
                            //System.out.println("adding");
                        }
                    } else {
                        String subPackagePath = getSubPackagePath(packagePath, fileName);
                        String subPackageName = getSubPackageName(packageName, fileName);
                        addClassByAnnotation(classList, subPackagePath, subPackageName, annotationClass);
                    }
                }
            }
        } catch (Exception e) {
            //logger.error("添加类出错！", e);
            throw new RuntimeException(e);
        }
    }
}
