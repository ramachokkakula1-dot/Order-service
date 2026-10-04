package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String orderId; // a business id, separate from the DB's own auto-increment id

	private String customerName;
	private String product;
	private int quantity;
	private String status; // PENDING, CONFIRMED, REJECTED

	public Order() {
	}

	public Order(String orderId, String customerName, String product, int quantity, String status) {
		this.orderId = orderId;
		this.customerName = customerName;
		this.product = product;
		this.quantity = quantity;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Order{orderId='" + orderId + "', product='" + product + "', quantity=" + quantity + ", status='"
				+ status + "'}";
	}
}
