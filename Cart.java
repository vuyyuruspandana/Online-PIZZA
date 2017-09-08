package com.csueb.bean;

import java.util.HashMap;

public class Cart {
	HashMap<String, Pizza> cartItems;	
	public Cart(){
	cartItems = new HashMap<String, Pizza>();
}
public HashMap<String, Pizza> getCartItems()	{
	return cartItems;
}
 public void addtocart(String name, Integer price ){
	 Pizza pizza = cartItems.get(name);
	if (pizza == null) {
		pizza = new Pizza(name, price, 1);
		cartItems.put(name, pizza);
	} else {
		pizza.quantity++;
	}
 } 
 public void deleteitem(String name)
 {
	 cartItems.remove(name);	 
 } 
}

