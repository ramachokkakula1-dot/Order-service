package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.Order;
import com.example.demo.repo.OrderRepository;
import com.example.demo.service.OrderPlacedEvent;

class OrderController {
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
	private final OrderRepository orderRepository;

	public OrderController(KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate, OrderRepository orderRepository) {
		this.kafkaTemplate = kafkaTemplate;
		this.orderRepository = orderRepository;
	}

	@PostMapping
	public Order placeOrder(@RequestBody OrderRequest request) {
		//@RequestBody OrderRequest request tells Spring: take the incoming JSON body and convert it
		//into an OrderRequest object automatically.
		
		String orderId = UUID.randomUUID().toString();//generating random uuid here like a1b2c3..

		Order order = new Order(orderId, request.getCustomerName(), request.getProduct(), request.getQuantity(),
				"PENDING");
		orderRepository.save(order);
		log.info("Order saved as PENDING: {}", order);

		OrderPlacedEvent event = new OrderPlacedEvent(orderId, request.getProduct(), request.getQuantity());
		kafkaTemplate.send("order-placed", orderId, event);
		log.info("Published order-placed event for orderId={}", orderId);

		return order;
	}

	@GetMapping
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	// small inner class just to shape the incoming JSON body
	public static class OrderRequest {
		private String customerName;
		private String product;
		private int quantity;

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public String getProduct() {
			return product;
		}

		public void setProduct(String product) {
			this.product = product;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
	}
}
