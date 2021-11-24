package group5.APIRest.Services;

import group5.APIRest.models.Categories;
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

    private String[] value = null;

    private List<Products> executeAndClose(String sql){
        return jdbcTemplate.query(sql, this.value, BeanPropertyRowMapper.newInstance(Products.class));
    }

    public int add(Products products){
        String sql = "INSERT INTO products (name , type, rating, category_id) VALUES (?, ?, ?, ?);";
        return jdbcTemplate.update(sql, products.getName(), products.getType(), products.getRating(), products.getCategory_id());
    }
    public List<Products> readAll(){
        String sql = "SELECT P.name, P.type, P.rating, P.created_at FROM Products as P";
        return this.executeAndClose(sql);
    }
    public Products readOneById(int id){
        String sql = "SELECT P.name, P.type, P.rating, P.created_at FROM Products as P WHERE P.id = ?";
        this.value = new String[]{String.valueOf(id)};
        return this.executeAndClose(sql).get(0);
    }

    public int delete(Integer id){
        String sql = "DELETE FROM products WHERE id = ?";
        return  jdbcTemplate.update(sql, id);
    }

    public int updateProduct(Integer id, Products product){
        String sql = "update products set name = ?, type = ?, rating = ?, category_id = ? where id = ?";
        return jdbcTemplate.update(sql, new Object[] {product.getName(), product.getType(), product.getRating(), product.getCategory_id() , id});
    }
}
