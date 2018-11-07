package com.ati.syd.spring.jms.reflection;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

/**
 * !place your description here!
 *
 * @author taufik.muliahadi (&copy;Nov 5, 2018)
 */
public class AddAnnotation {

	public  Object addAnnotationMethod(Class<?> className, String methodName, Class<?> annotation,
			Map<String, String> attribute) throws Exception {
		
		
		// pool creation
		ClassPool pool = ClassPool.getDefault();
		// extracting the class
		CtClass cc = pool.getAndRename(className.getName(),className.getName()+"Added");
		// looking for the method to apply the annotation on
		CtMethod sayHelloMethodDescriptor = cc.getDeclaredMethod(methodName);
		// create the annotation
		ClassFile ccFile = cc.getClassFile();
		ConstPool constpool = ccFile.getConstPool();
		AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
		Annotation annot = new Annotation(annotation.getName(), constpool);

		for (Entry<String, String> entry : attribute.entrySet()) {
			annot.addMemberValue(entry.getKey(), new StringMemberValue(entry.getValue(), ccFile.getConstPool()));
		}
		attr.addAnnotation(annot);
		sayHelloMethodDescriptor.getMethodInfo().addAttribute(attr);
		Class<?> dynamiqueBeanClass = cc.toClass();
		
		
		return dynamiqueBeanClass.newInstance();
	}
	
	public  Class<?> addAnnotationMethod(String className, String methodName, Class<?> annotation,
			Map<String, String> attribute) throws Exception {
		
		ClassLoader loader = ClassLoader.getSystemClassLoader();
//		Class<?> c = Class.forName(className);
		// pool creation
		ClassPool pool = ClassPool.getDefault();
		// extracting the class
//		CtClass cc = pool.getAndRename(className,className+"Added");
		CtClass cc = pool.get(className);
		// looking for the method to apply the annotation on
		CtMethod sayHelloMethodDescriptor = cc.getDeclaredMethod(methodName);
		// create the annotation
		ClassFile ccFile = cc.getClassFile();
		ConstPool constpool = ccFile.getConstPool();
		AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
		Annotation annot = new Annotation(annotation.getName(), constpool);

		for (Entry<String, String> entry : attribute.entrySet()) {
			annot.addMemberValue(entry.getKey(), new StringMemberValue(entry.getValue(), ccFile.getConstPool()));
		}
		attr.addAnnotation(annot);
		sayHelloMethodDescriptor.getMethodInfo().addAttribute(attr);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
        
		
		Class<?> dynamiqueBeanClass = cc.toClass();
		

		return dynamiqueBeanClass;
	}

	public static Class<?> addAnnotation(String className, String methodName,Map<String, String> attribute) throws NotFoundException, CannotCompileException {
		// pool creation
		ClassPool pool = ClassPool.getDefault();
		// extracting the class
		CtClass cc = pool.getCtClass(className);
		// looking for the method to apply the annotation on
		CtMethod sayHelloMethodDescriptor = cc.getDeclaredMethod(methodName);
		// create the annotation
		ClassFile ccFile = cc.getClassFile();
		ConstPool constpool = ccFile.getConstPool();
		AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
		Annotation annot = new Annotation("com.ati.syd.spring.jms.annotations.MessageListener", constpool);
		for (Entry<String, String> entry : attribute.entrySet()) {
			annot.addMemberValue(entry.getKey(), new StringMemberValue(entry.getValue(), ccFile.getConstPool()));
		}
		attr.addAnnotation(annot);
		sayHelloMethodDescriptor.getMethodInfo().addAttribute(attr);

		// transform the ctClass to java class
		Class dynamiqueBeanClass = cc.toClass();
		return dynamiqueBeanClass;
	}

}
