package com.ati.syd.spring.jms.rabbitmq;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.springframework.jms.annotation.JmsListener;

import com.ati.syd.spring.jms.Application;
import com.ati.syd.spring.jms.ConfigJmsVersion;
import com.ati.syd.spring.jms.annotations.MessageListener;
import com.ati.syd.spring.jms.reflection.AddAnnotation;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import sample.SayHelloBean;
import xdean.jex.util.reflect.AnnotationUtil;

/**
 * Unit test for simple App.
 */
public class ApplicationTest {
	
	private AddAnnotation aa = new AddAnnotation();
//	@Test
	public void testApp() {
		try {
			Class<?> clazz = Class.forName("com.ati.syd.spring.jms.rabbitmq.ListenerAMQP");
			Object obj = clazz.newInstance();
			for (Method method : obj.getClass().getDeclaredMethods()) {
				method.setAccessible(true);
				JmsListener jmsListener = method.getAnnotation(JmsListener.class);
				if (jmsListener != null) {
					try {
						System.out.println("old ddestination : "+jmsListener.destination());
						String destination  = jmsListener.destination()+".v2";
						AnnotationUtil.changeAnnotationValue(method.getAnnotation(JmsListener.class), "destination", destination);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			for (Method method : obj.getClass().getDeclaredMethods()) {
				method.setAccessible(true);
				JmsListener jmsListener = method.getAnnotation(JmsListener.class);
				if (jmsListener != null) {
					try {
						String destination  = jmsListener.destination();
						System.out.println("new ddestination :"+destination);
					} catch ( SecurityException e) {
						e.printStackTrace();
					}

				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test	
	public void concatArray() {
		String [] array1 = new String[] {"1","2"};
		String [] array2 = new String[] {"3","4"};
		String [] array3 = null;
		array3 =  (String[]) ArrayUtils.addAll(array3, array1);
		array3 =  (String[]) ArrayUtils.addAll(array3, array2);
		System.out.println(array3);
	}
	
//	@Test
	public void getAttributeMainClass() {
		ConfigJmsVersion configJmsVersion = new ConfigJmsVersion(Application.class);
		System.out.println(configJmsVersion.getVerison());
	}
	
//	@Test
	public void testAddAnnotattionMethod() throws Exception {
		Map<String, String> attr = new HashMap<>();
		attr.put("topic", "test.topic");
		attr.put("group", "test.group");
//		attr.put("name", "yeahhh");
		
//		Object clazz = aa.addAnnotationMethod(SayHelloBean.class, "sayHelloTo", MessageListener.class,  attr);
		
		Class<?> clazz = aa.addAnnotationMethod("sample.SayHelloBean", "sayHelloTo", MessageListener.class, attr);
		
		
		for (Method method : clazz.getDeclaredMethods()) {
			method.setAccessible(true);
			for (Annotation annotation : method.getAnnotations()) {
				System.out.println(annotation.annotationType());
				if(annotation instanceof MessageListener) {
					System.out.println(((MessageListener) annotation).topic());
					System.out.println(((MessageListener) annotation).group());
					
				}
			}
			method.invoke(clazz,"test invoke debug");
		}
	}
}
