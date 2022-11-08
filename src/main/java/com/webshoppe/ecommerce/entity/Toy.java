package com.webshoppe.ecommerce.entity;

import java.math.BigDecimal;

public class Toy extends Item {

    public Toy(String id, String name, String description, BigDecimal price) {
        super(id, name, description, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Toy)) {
            return false;
        }
        Toy otherToy = (Toy)obj;
        return this.id.equals(otherToy.getId()) && this.name.equals(otherToy.getName())
               && this.description.equals(otherToy.getDescription())
               && this.price.equals(otherToy.getPrice());
    }

}
