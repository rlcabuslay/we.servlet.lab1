package com.webshoppe.ecommerce.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.webshoppe.ecommerce.entity.Book;
import com.webshoppe.ecommerce.entity.Flower;
import com.webshoppe.ecommerce.entity.Item;
import com.webshoppe.ecommerce.entity.Toy;
import com.webshoppe.ecommerce.exception.DataAccessException;
import com.webshoppe.ecommerce.jdbc.JdbcConnectionManager;

public class Repository {
    private final static String TOY_COLUMNS = " tid, tname, tdesc, tprice ";
    private final static String TOY_PRICE = " tprice ";
    private final static String TOY_TABLE = " ToysDetails ";
    private final static String BOOK_COLUMNS = " bid, title, bookdesc, bookprice ";
    private final static String BOOK_PRICE = " bookprice ";
    private final static String BOOK_TABLE = " BooksDetails ";
    private final static String FLOWER_COLUMNS = " fid, fname, fdesc, fprice ";
    private final static String FLOWER_PRICE = " fprice ";
    private final static String FLOWER_TABLE = " FlowersDetails ";
    
    private JdbcConnectionManager jdbcConnectionManager;
    
    private String itemType;

    public Repository(JdbcConnectionManager jdbcConnectionManager) {
        this.jdbcConnectionManager = jdbcConnectionManager;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public List<Item> findAll() {
        try {
            final Connection connection = jdbcConnectionManager.getConnection();
            
            String query = queryDatabase(itemType, false);

            final PreparedStatement findAllQuery = connection.prepareStatement(query);

            final ResultSet resultSet = findAllQuery.executeQuery();
            final List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                Item item = toItem(itemType, resultSet);
                items.add(item);
            }

            return items;
        } catch (Exception e) {
            throw DataAccessException.instance("failed_to_retrieve_toys");
        }
    }

    public List<Item> findByPrice(BigDecimal minimumPrice, BigDecimal maximumPrice) {
        try {
            final Connection connection = jdbcConnectionManager.getConnection();
            
            String query = queryDatabase(itemType, true);

            final PreparedStatement findAllQuery = connection.prepareStatement(query);
            findAllQuery.setBigDecimal(1, minimumPrice);
            findAllQuery.setBigDecimal(2, maximumPrice);

            final ResultSet resultSet = findAllQuery.executeQuery();
            final List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                Item item = toItem(itemType, resultSet);
                items.add(item);
            }

            return items;
        } catch (Exception e) {
            throw DataAccessException.instance("failed_to_retrieve_toys_by_price");
        }
    }
    
    private Item toItem(String itemType, ResultSet resultSet) throws SQLException {
        Item item = null;
        
        if(itemType.equals("toy")){
            item = new Toy(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBigDecimal(4));
        } else if (itemType.equals("book")) {
            item = new Book(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBigDecimal(4));
        } else if (itemType.equals("flower")) {
            item = new Flower(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBigDecimal(4));
        }
        
        return item;
    }
    
    private String queryDatabase(String itemType, Boolean isByPrice) {
        final StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("SELECT");
        if(itemType.equals("toy")){
            stringBuilder.append(TOY_COLUMNS);
        } else if (itemType.equals("book")) {
            stringBuilder.append(BOOK_COLUMNS);
        } else if (itemType.equals("flower")) {
            stringBuilder.append(FLOWER_COLUMNS);
        }
        stringBuilder.append("FROM");
        if(itemType.equals("toy")){
            stringBuilder.append(TOY_TABLE);
        } else if (itemType.equals("book")) {
            stringBuilder.append(BOOK_TABLE);
        } else if (itemType.equals("flower")) {
            stringBuilder.append(FLOWER_TABLE);
        }
        
        if(isByPrice) {
            stringBuilder.append("WHERE");
            if(itemType.equals("toy")){
                stringBuilder.append(TOY_PRICE);
            } else if (itemType.equals("book")) {
                stringBuilder.append(BOOK_PRICE);
            } else if (itemType.equals("flower")) {
                stringBuilder.append(FLOWER_PRICE);
            }
            stringBuilder.append("BETWEEN ? AND ?");
        }

        return stringBuilder.toString();
    }
    
}
