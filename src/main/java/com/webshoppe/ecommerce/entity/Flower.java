package com.webshoppe.ecommerce.entity;

import java.math.BigDecimal;

public class Flower extends Item {
    public Flower(String id, String name, String description, BigDecimal price) {
        super(id, name, description, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Flower)) {
            return false;
        }
        Flower otherFlower = (Flower)obj;
        return this.id.equals(otherFlower.getId()) && this.name.equals(otherFlower.getName())
               && this.description.equals(otherFlower.getDescription())
               && this.price.equals(otherFlower.getPrice());
    }
}
