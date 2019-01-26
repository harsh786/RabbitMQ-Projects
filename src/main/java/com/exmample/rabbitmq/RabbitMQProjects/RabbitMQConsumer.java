package com.exmample.rabbitmq.RabbitMQProjects;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
	@RabbitListener(queues = "${test.rabbitmq.queue1}")
    public void receiveMessage1(final Employee Obj) {
		
		System.out.println("consumer:"+Obj);
	
	}
	
	@RabbitListener(queues = "${test.rabbitmq.queue2}")
    public void receiveMessage2(final Employee Obj) {
		
		System.out.println("consumer2:"+Obj);
	
	}
}
