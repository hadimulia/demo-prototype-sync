package com.ati.syd.spring.jms;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;

import com.ati.syd.spring.jms.annotations.AnabaticBEVerison;
import com.ati.syd.spring.jms.annotations.ComponentJms;
import com.ati.syd.spring.jms.reflection.ScannClassByPackage;
import com.google.common.base.CaseFormat;

import xdean.jex.util.reflect.AnnotationUtil;

/**
 * Handle Versioning Queue name with concate value from {@link AnabaticBEVerison}
 *
 * @author taufik.muliahadi (&copy;Oct 11, 2018) 
 */
public class ConfigJmsVersion {

	private Class<?> mainClass;
	private String[] packages = null;
	private String verison;

	public ConfigJmsVersion() {

	}

	public ConfigJmsVersion(Class<?> mainClass) {
		this.mainClass = mainClass;
		this.mainClass.getDeclaredAnnotations();
		this.verison = (String) AnnotationUtils.getValue(this.mainClass.getDeclaredAnnotation(AnabaticBEVerison.class));
		this.packages = (String[]) ArrayUtils.addAll(packages,
				((String[]) AnnotationUtils.getValue(this.mainClass.getAnnotation(ComponentScan.class), "value")));
		this.packages = (String[]) ArrayUtils.addAll(packages, ((String[]) AnnotationUtils
				.getValue(this.mainClass.getAnnotation(ComponentScan.class), "basePackages")));

	}

	
	/**
	 * Inject Class Contain {@link ComponentJms} to bean factory spring
	 * and change value of "destination" from {@link JmsListener}
	 *
	 * @author taufik.muliahadi (&copy;Oct 11, 2018)
	 * @return
	 */
	public BeanDefinitionRegistryPostProcessor doOverideJmsListener() {
		BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor = new BeanDefinitionRegistryPostProcessor() {

			@Override
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
				List<Class> listClass = null;
				try {
					listClass = ScannClassByPackage.getClasses(packages);
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
				try {
					for (Class clazz : listClass) {
						if (clazz.getDeclaredAnnotation(ComponentJms.class) != null) {
							Object obj = clazz.newInstance();
							for (Method method : obj.getClass().getDeclaredMethods()) {
								method.setAccessible(true);
								if(method.getAnnotation(JmsListener.class)!=null)
									changeJmsListener(method);
								else if(method.getAnnotation(KafkaListener.class)!=null)
									changeKafkasListener(method);
							}
							GenericBeanDefinition gbd = new GenericBeanDefinition();
							gbd.setBeanClass(obj.getClass());

							registry.registerBeanDefinition(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, obj.getClass().getSimpleName()), gbd);
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		};
		return beanDefinitionRegistryPostProcessor;
	}
	
	private void changeJmsListener(Method method) {
		JmsListener jmsListener = method.getAnnotation(JmsListener.class);
		if (jmsListener!=null) {
			try {
				String destination = jmsListener.destination() + "." + getVerison();
				AnnotationUtil.changeAnnotationValue(method.getAnnotation(JmsListener.class),"destination", destination);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		SendTo sendTo = method.getAnnotation(SendTo.class);
		if (sendTo != null) {
			try {
				String destination [] = sendTo.value();
				for (int i = 0; i < destination.length; i++) {
					destination[i] = destination[i]+"."+getVerison();
				}
				AnnotationUtil.changeAnnotationValue(method.getAnnotation(SendTo.class),"value", destination);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void changeKafkasListener(Method method) {
		KafkaListener kafkaListener = method.getAnnotation(KafkaListener.class);
		if (kafkaListener!=null) {
			try {
				String [] destination = kafkaListener.topics() ;
				for (int i = 0; i < destination.length; i++) {
					destination[i]=destination[i]+"."+getVerison();
				}
				AnnotationUtil.changeAnnotationValue(method.getAnnotation(KafkaListener.class),"topics", destination);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		SendTo sendTo = method.getAnnotation(SendTo.class);
		if (sendTo != null) {
			try {
				String destination [] = sendTo.value();
				for (int i = 0; i < destination.length; i++) {
					destination[i] = destination[i]+"."+getVerison();
				}
				AnnotationUtil.changeAnnotationValue(method.getAnnotation(SendTo.class),"value", destination);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public Class getMainClass() {
		return mainClass;
	}

	@SuppressWarnings("rawtypes")
	public void setMainClass(Class mainClass) {
		this.mainClass = mainClass;
	}

	public String[] getPackages() {
		return packages;
	}

	public void setPackages(String[] packages) {
		this.packages = packages;
	}

	public String getVerison() {
		return verison;
	}

	public void setVerison(String verison) {
		this.verison = verison;
	}

}
