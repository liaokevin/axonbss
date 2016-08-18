package com.ai.bss.api.shoppingcart.command;

import java.util.LinkedHashSet;
import java.util.Set;

import com.ai.bss.api.shoppingcart.ShoppingCartItemCharacter;
import com.ai.bss.api.shoppingcart.ShoppingCartItemProductCharacter;

public class AddShoppingCartItemCommand extends ShoppingCartItemCommand {
	private String offerId;
	private int quantity;
	private long price;  
	private Set<ShoppingCartItemCharacter> offerCharacters = new LinkedHashSet<ShoppingCartItemCharacter>();
	private Set<ShoppingCartItemProductCharacter> productCharacters = new LinkedHashSet<ShoppingCartItemProductCharacter>();
	public AddShoppingCartItemCommand() {
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	
	public void addOfferCharacter(ShoppingCartItemCharacter offerCharacter){
		this.offerCharacters.add(offerCharacter);
	}
	
	public Set<ShoppingCartItemCharacter> getOfferCharacters(){
		return this.offerCharacters;
	}
	
	public void addProductCharacter(ShoppingCartItemProductCharacter productCharacter){
		this.productCharacters.add(productCharacter);
	}
	
	public Set<ShoppingCartItemProductCharacter> getProductCharacters(){
		return this.productCharacters;
	}

}
