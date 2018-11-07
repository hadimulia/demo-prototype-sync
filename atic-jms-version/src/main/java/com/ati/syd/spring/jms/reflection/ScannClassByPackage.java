package com.ati.syd.spring.jms.reflection;

	
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;


public class ScannClassByPackage {

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 *
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList();
		while (resources.hasMoreElements()) {
			URL resource = (URL) resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList classes = new ArrayList();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return (Class[]) classes.toArray(new Class[classes.size()]);
	}
	
	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 *
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> getClasses(String[] packageName) throws ClassNotFoundException, IOException {
		List<Class> classes =  new ArrayList<Class>();
		for(String pck : packageName){
			classes.addAll(Arrays.asList(getClasses(pck)));
		}
		
		return getClassOrAbstract(classes);
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 *
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List findClasses(File directory, String packageName) throws ClassNotFoundException {
		List classes = new ArrayList();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(
						Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}
	
	/**
	 * get native Class and abstract Class
	 *
	 * @author taufik.muliahadi (&copy;Oct 11, 2018)
	 * @param classes
	 * @return List of Class
	 */
	@SuppressWarnings("rawtypes")
	private static List<Class> getClassOrAbstract(List<Class> classes){
		List<Class> clazz = new ArrayList<Class>();
		for (Class class1 : classes) {
			if (class1.isInterface()) {
				System.out.println(class1.getSimpleName() + " : is Interface");
			} else if (class1.isAnnotation()) {
				System.out.println(class1.getSimpleName() + " : is Annotation");
			} else if (class1.isEnum()) {
				System.out.println(class1.getSimpleName() + " : is Enum");
			} else if (class1.isSynthetic()) {
				System.out.println(class1.getSimpleName() + " : is Syntetic");
			} else {
				clazz.add(class1);
			}
		}
		return clazz;
	}

}
