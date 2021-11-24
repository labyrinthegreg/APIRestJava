package group5.APIRest.Services;

import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Repository
public class ProductsDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(Products products){
        String sql = "INSERT INTO products (name , created_at , type, rating) VALUES (?, ?, ?, ?);";
        return jdbcTemplate.update(sql, products.getName(), products.getCreated_at(), products.getType(), products.getRating());
    }
    public List<Products> readAll(){
        String sql = "SELECT P.name, P.type, P.rating, P.created_at" +
                "FROM Products as P;";
        List<Products> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class));
        return list;
    }
}
