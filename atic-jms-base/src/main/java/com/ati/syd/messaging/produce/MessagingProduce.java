package com.ati.syd.messaging.produce;

import org.springframework.beans.factory.annotation.Autowired;

import com.ati.syd.messaging.template.RequestMessage;
import com.ati.syd.messaging.template.ResponseMessage;

/**
 * This interface for Produce or Send Message to Message Broker <br>
 * Dependend Messaging Provider and Active Profile Spring <br>
 * For now supported Provider 
 * <ol>
 * 		<li> <a href="http://activemq.apache.org/">ActiveMQ</a> </li>
 * 		<li style="display:none"> <a href="https://www.rabbitmq.com/">RabbitMQ</a> </li>
 * 		<li> <a href="https://kafka.apache.org/">Kafka</a> </li>
 * </ol>
 * 
 * using @Qualifier <br>
 * Example : <br>
 * <pre>
 * {@literal }@Autowired
 * private MessagingProduce&ltObject&gt messagingProduce;
 * </pre>
 * 
 * @author taufik.muliahadi (&copy;Nov 1, 2018) 
 * @see Autowired
 * @param <T> 
 */
public interface  MessagingProduce<T> {

	/**
	 * Send message to message broker without group
	 * 
	 * @author taufik.muliahadi (&copy;Nov 1, 2018)
	 * @param topic
	 * @param obj -> will be converted to json format
	 * @return
	 */
	public T produce(String topic,T obj);
	
	/**
	 * Send message to message broker with group
	 *
	 * @author taufik.muliahadi (&copy;Nov 1, 2018)
	 * @param topic
	 * @param group
	 * @param obj -> will be converted to json format
	 * @return
	 */
	public T produce(String topic,String group,T obj);
	
	/**
	 * Send message to message broker, with template class {@link RequestMessage}
	 *
	 * @author taufik.muliahadi (&copy;Nov 1, 2018)
	 * @param request
	 * @return
	 */
	public ResponseMessage<T> produce(RequestMessage<T> request);
	
	/**
	 * Send message to message broker, with template class {@link RequestMessage}
	 * and process will be executed by another thread
	 * @author taufik.muliahadi (&copy;Nov 1, 2018)
	 * @param request
	 */
	public void produceAsynchronous(RequestMessage<T> request);
	
	/**
	 * Send message to message broker without group
	 * and process will be executed by another thread
	 * @author taufik.muliahadi (&copy;Nov 1, 2018)
	 * @param topic
	 * @param obj -> will be converted to json format
	 */
	public void produceAsynhcronous(String topic,T obj);
	
	/**
	 * Send message to message broker without group
	 * and process will be executed by another thread
	 * 
	 * @author taufik.muliahadi (&copy;Nov 1, 2018)
	 * @param topic
	 * @param group
	 * @param obj -> will be converted to json format
	 */
	public void produceAsynhcronous(String topic,String group,T obj);
	
}
