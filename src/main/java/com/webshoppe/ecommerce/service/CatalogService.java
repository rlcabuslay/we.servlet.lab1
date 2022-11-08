package com.webshoppe.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;

import com.webshoppe.ecommerce.entity.Item;
import com.webshoppe.ecommerce.exception.DataAccessException;
import com.webshoppe.ecommerce.exception.ServiceException;
import com.webshoppe.ecommerce.repository.Repository;

public class CatalogService {
    private Repository repository;

    public CatalogService(Repository repository) {
        this.repository = repository;
    }

    public List<Item> getCatalog() {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw ServiceException.instance(e.getMessage());
        }

    }

    public List<Item> getCatalog(BigDecimal minimumPrice, BigDecimal maximumPrice) {
        try {
            return repository.findByPrice(minimumPrice, maximumPrice);
        } catch (DataAccessException e) {
            throw ServiceException.instance(e.getMessage());
        }

    }
}
