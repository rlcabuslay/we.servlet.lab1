package com.webshoppe.ecommerce.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webshoppe.ecommerce.entity.Item;
import com.webshoppe.ecommerce.jdbc.JdbcConnectionManager;
import com.webshoppe.ecommerce.repository.Repository;
import com.webshoppe.ecommerce.service.CatalogService;

public class CatalogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CatalogService catalogService;
    private Repository repository;

    @Override
    public void init() throws ServletException {
        final JdbcConnectionManager jdbcConnectionManager = new JdbcConnectionManager();
        repository = new Repository(jdbcConnectionManager);
        catalogService = new CatalogService(repository);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        
        if(request.getParameter("category").equals("toys")) {
            repository.setItemType("toy");

        } else if(request.getParameter("category").equals("books")) {
            repository.setItemType("book");

        } else if(request.getParameter("category").equals("flowers")) {
            repository.setItemType("flower");

        }
        
        final List<Item> items = catalogService.getCatalog();
        show(items, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        
        final String categoryParam = request.getParameter("select-category");

        final String minimumPriceParam = request.getParameter("minimum-price");
        final BigDecimal minimumPrice = new BigDecimal(minimumPriceParam);
        
        final String maximumPriceParam = request.getParameter("maximum-price");
        final BigDecimal maximumPrice = new BigDecimal(maximumPriceParam);
        
        if(categoryParam.equals("toy")) {
            repository.setItemType("toy");

        } else if(categoryParam.equals("book")) {
            repository.setItemType("book");

        } else if(categoryParam.equals("flower")) {
            repository.setItemType("flower");

        }
        
        final List<Item> items = catalogService.getCatalog(minimumPrice, maximumPrice);
        show(items, response);
    }
    
    private void show(List<Item> item, HttpServletResponse response) throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        if (item.isEmpty()) {
            stringBuilder.append("<b>Cannot find items that met the price range.</b>");
        } else {
            stringBuilder.append("<table class='table'>");
            stringBuilder.append("<thead>");
            stringBuilder.append("<th scope='col'>ID</th>");
            stringBuilder.append("<th scope='col'>Name</th>");
            stringBuilder.append("<th scope='col'>Description</th>");
            stringBuilder.append("<th scope='col'>Price</th>");
            stringBuilder.append("</thead>");
            item.forEach(e -> {
                stringBuilder.append("<tr scope='row'>");
                stringBuilder.append("<td>").append((e).getId()).append("</td>");
                stringBuilder.append("<td>").append((e).getName()).append("</td>");
                stringBuilder.append("<td>").append((e).getDescription()).append("</td>");
                stringBuilder.append("<td>").append((e).getPrice()).append("</td>");
                stringBuilder.append("</tr>");
            });
            stringBuilder.append("</table>");
        }

        PrintWriter out = response.getWriter();
        out.println(stringBuilder.toString());
        out.flush();
        out.close();
    }

}
