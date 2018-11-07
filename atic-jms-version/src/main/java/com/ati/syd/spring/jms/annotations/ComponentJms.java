package com.ati.syd.spring.jms.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.jms.annotation.JmsListener;

/**
 * this annotation for inject class contain {@link JmsListener} into bean Factory Spring {@link BeanFactory}
 *
 * @author taufik.muliahadi (&copy;Oct 11, 2018) 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentJms {

	String value() default "";
}
