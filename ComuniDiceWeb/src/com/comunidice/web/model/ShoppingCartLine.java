package com.comunidice.web.model;

public class ShoppingCartLine<T> {
	
	private T product = null;
	private Integer quantity = null;
	private Boolean game = null;
	
	public ShoppingCartLine() {
		
	}

	public T getProduct() {
		return product;
	}

	public void setProduct(T product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Boolean getGame() {
		return game;
	}

	public void setGame(Boolean game) {
		this.game = game;
	}
}
