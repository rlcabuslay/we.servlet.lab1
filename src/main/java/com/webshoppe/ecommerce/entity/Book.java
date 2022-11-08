package com.webshoppe.ecommerce.entity;

import java.math.BigDecimal;

public class Book extends Item {
    
    public Book(String id, String name, String description, BigDecimal price) {
        super(id, name, description, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Book)) {
            return false;
        }
        Book otherBook = (Book)obj;
        return this.id.equals(otherBook.getId()) && this.name.equals(otherBook.getName())
               && this.description.equals(otherBook.getDescription())
               && this.price.equals(otherBook.getPrice());
    }
}
