package com.example.demo.service;

public class OrderPlacedEvent {
	private String orderId;
	private String product;
	private int quantity;

	public OrderPlacedEvent() {
	}

	public OrderPlacedEvent(String orderId, String product, int quantity) {
		this.orderId = orderId;
		this.product = product;
		this.quantity = quantity;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
