package com.example.demo.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Order;
import com.example.demo.repo.OrderRepository;

@Component
public class OrderStatusConsumer {
	private static final Logger log = LoggerFactory.getLogger(OrderStatusConsumer.class);

	@Autowired
	private OrderRepository orderRepository;

	@KafkaListener(topics = "order-status-updated", groupId = "order-service-group")
	public void listen(OrderStatusEvent event) {
		Optional<Order> found = orderRepository.findByOrderId(event.getOrderId());

		if (found.isEmpty()) {
			log.warn("Received status update for unknown orderId={}", event.getOrderId());
			return;
		}

		Order order = found.get();
		order.setStatus(event.getStatus());
		orderRepository.save(order);
		log.info("Order {} updated to status {}", order.getOrderId(), order.getStatus());
	} 	
}
