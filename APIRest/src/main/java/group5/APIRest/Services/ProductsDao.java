package group5.APIRest.Services;

import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class ProductsDao {
    @Autowired

    private JdbcTemplate jdbcTemplate;

    public int add(Products products){
        String sql = "INSERT INTO products (name , type, rating) VALUES (?, ?, ?);";
        return jdbcTemplate.update(sql, products.getName(), products.getType(), products.getRating());
    }
}
