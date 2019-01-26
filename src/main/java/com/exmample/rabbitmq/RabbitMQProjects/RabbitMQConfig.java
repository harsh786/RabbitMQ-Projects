package com.exmample.rabbitmq.RabbitMQProjects;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration ;

@Configuration
public class RabbitMQConfig {
	@Value("${test.rabbitmq.queue1}")
	String queueName1;
	@Value("${test.rabbitmq.queue2}")
	String queueName2;

	@Value("${test.rabbitmq.exchange}")
	String exchange;

	@Value("${test.rabbitmq.routingkey}")
	private String routingkey;

	@Bean
	Queue queue1() {
		//return new Queue(queueName1);
		return new Queue(queueName1,true,false,false);
	}

	@Bean
	Queue queue2() {	
		return new Queue(queueName2, false);
	}

	@Bean
	DirectExchange exchange() {

		return new DirectExchange(exchange,true,false);
	}

	@Bean
	Binding binding1(@Qualifier("queue1")Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}

	@Bean
	Binding bindin2(@Qualifier("queue2")Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	
	
	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		CachingConnectionFactory objCachingConnectionFactory=new CachingConnectionFactory();
		objCachingConnectionFactory.setPublisherConfirms(true);
		rabbitTemplate.setConfirmCallback(new ConfirmCallback()
				{

					@Override
					public void confirm(CorrelationData correlationData, boolean ack, String cause) {
						// TODO Auto-generated method stub
						
					}
			
			
				
				});
		return rabbitTemplate;
	}	
}
