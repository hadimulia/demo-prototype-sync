package com.ati.syd.spring.jms.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.jms.annotation.JmsListener;

import com.ati.syd.spring.jms.ConfigJmsVersion;

/**
 * for Versioning queue name for Consumer Listener 
 *
 * @author taufik.muliahadi (&copy;Oct 11, 2018) 
 * @see {@link JmsListener}
 * @see {@link ConfigJmsVersion}
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AnabaticBEVerison {


	String value() default "";
}
