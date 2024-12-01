package com.example.sscapp;

import com.example.sscapp.models.CartItem;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(CartItem newItem) {
        for (CartItem item : cartItems) {
            if (item.getName().equals(newItem.getName()) && item.getSize().equals(newItem.getSize())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        cartItems.add(newItem);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void removeFromCart(CartItem item) {
        cartItems.remove(item);
    }

    public void clearCart() {
        cartItems.clear();
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}