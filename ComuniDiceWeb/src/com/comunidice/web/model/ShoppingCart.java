package com.comunidice.web.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
	
	private List<ShoppingCartLine> line = null;
	private Double total = null;
	private Double shippingCosts = null;
	
	public ShoppingCart() {
	}

	public List<ShoppingCartLine> getLine() {
		return line;
	}

	public void setLine(List<ShoppingCartLine> line) {
		this.line = line;
	}

	public Double getShippingCosts() {
		return shippingCosts;
	}

	public void setShippingCosts(Double shippingCosts) {
		this.shippingCosts = shippingCosts;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
}
