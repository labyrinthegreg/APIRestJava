package group5.APIRest.Services;

import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@Repository
public class ProductsDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Object[] value = null;

    private List<Products> executeAndClose(String sql){
        return jdbcTemplate.query(sql, this.value, BeanPropertyRowMapper.newInstance(Products.class));
    }
    private  int updateAndClose(String sql, Object[] value){
        return jdbcTemplate.update(sql, value);
    }

    public String reqWhere(Map<String, String> orders){
        //String request = "type = Boisson OR type = pate  ";
        StringBuilder toReturn = new StringBuilder();
        boolean firstEntry = true;
        for (var entry : orders.entrySet()) {
            toReturn.append(firstEntry ? " WHERE " : " AND ");
            switch (entry.getKey()){
                case "type" :
                    String[] result = entry.getValue().split(",");
                    for(int i=0 ; i <result.length ; i++ ){
                        if (i>0){
                            toReturn.append(" OR ");
                        }
                        toReturn.append(entry.getKey()+" = '"+ result[i]+"'");
                    }
            }
        }
        return toReturn.toString();
    }

    public String reqOrder(Map<String, String> orders){
        StringBuilder toReturn = new StringBuilder();
        boolean firstEntry = true;
        for (var entry : orders.entrySet()) {
            toReturn.append(firstEntry ? " ORDER BY " : ", ").append(entry.getValue()).append(" ").append(entry.getKey());
            firstEntry = false;
        }
        return toReturn.toString();
    }

    public int add(Products products){
        String sql = "INSERT INTO products (name , type, rating, category_id) VALUES (?, ?, ?, ?);";
        Object[] value = new Object[]{products.getName(), products.getType(), products.getRating(), products.getCategory_id()};
        return this.updateAndClose(sql, value);
    }
    public List<Products> readAll(String sql_bis, Object[] value){
        String sql = "SELECT * FROM Products" + sql_bis;
        this.value = value;
        return this.executeAndClose(sql);
    }
    public Products readOneById(int id){
        String sql = "SELECT * FROM Products as P WHERE P.id = ?";
        this.value = new Object[]{id};
        return this.executeAndClose(sql).get(0);
    }

    public int delete(Integer id){
        String sql = "DELETE FROM products WHERE id = ?";
        return  this.updateAndClose(sql, new Object[]{id});
    }

    public int updateProduct(Integer id, Products product){
        String sql = "update products set name = ?, type = ?, rating = ?, category_id = ? where id = ?";
        Object[] value = new Object[]{product.getName(), product.getType(), product.getRating(), product.getCategory_id(), id};
        return this.updateAndClose(sql, value);
    }

    public List<Products> readOneByType(String type, String name){
        String sql = "SELECT * FROM PRODUCTS WHERE type = ? AND name = ?";
        this.value = new String[]{String.valueOf(type), String.valueOf(name)};
        for (int i = 0; i <= value.length; i++) {
            return jdbcTemplate.query(sql, this.value, BeanPropertyRowMapper.newInstance(Products.class));
        }
        return null;
    }
}
