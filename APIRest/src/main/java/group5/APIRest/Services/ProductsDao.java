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

    private Object[] value = null;

    private List<Products> executeAndClose(String sql){
        return jdbcTemplate.query(sql, this.value, BeanPropertyRowMapper.newInstance(Products.class));
    }
    private  int updateAndClose(String sql){
        return jdbcTemplate.update(sql, this.value);
    }

    public int add(Products products){
        String sql = "INSERT INTO products (name , type, rating, category_id) VALUES (?, ?, ?, ?);";
        this value = new Object[]{products.getName(), products.getType(), products.getRating(), products.getCategory_id()};
        return this.updateAndClose(sql);
    }
    public List<Products> readAll(){
        String sql = "SELECT * FROM Products";
        this.value = null;
        return this.executeAndClose(sql);
    }
    public Products readOneById(int id){
        String sql = "SELECT * FROM Products as P WHERE P.id = ?";
        this.value = new Object[]{String.valueOf(id)};
        return this.executeAndClose(sql).get(0);
    }

    public List<Products> readByRangeProducts(int startNb, int rowsCounted){
        String sql = "SELECT * FROM products LIMIT ? , ? ";
        this.value = new Object[]{startNb, rowsCounted};
        return this.executeAndClose(sql);
    }

    public int delete(Integer id){
        String sql = "DELETE FROM products WHERE id = ?";
        this.value = new Object[]{id};
        return  this.updateAndClose(sql);
    }

    public int updateProduct(Integer id, Products product){
        String sql = "update products set name = ?, type = ?, rating = ?, category_id = ? where id = ?";
        this.value = new Object[] {product.getName(), product.getType(), product.getRating(), product.getCategory_id() , id};
        return this.updateAndClose(sql);
    }
}
