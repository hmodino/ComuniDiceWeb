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
	
	@Override
	public boolean equals(Object o) {
		Boolean b = true;
		
		if(o==null) {
			b=false;
		}
		if(!(o instanceof ShoppingCartLine)) {
			b=false;
		}
		if(this.getProduct()== null) {
			b=false;
		}
		ShoppingCartLine scl = (ShoppingCartLine) o;
		if(!this.getProduct().equals(scl.getProduct())) {
			b=false;
		}
		return b;
	}
}
