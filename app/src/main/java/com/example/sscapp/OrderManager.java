package com.example.sscapp;

import com.example.sscapp.models.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private static OrderManager instance;
    private List<Order> orders;
    private int lastOrderId;

    private OrderManager() {
        orders = new ArrayList<>();
        lastOrderId = 0;
    }

    public static synchronized OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public int getNextOrderId() {
        return ++lastOrderId;
    }
}
