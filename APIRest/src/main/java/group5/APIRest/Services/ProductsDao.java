package group5.APIRest.Services;

import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public String reqWhere(Map<String, String> orders){
        //String request = "type = Boisson OR type = pate  ";
        StringBuilder toReturn = new StringBuilder();
        boolean firstEntry = true;
        for (var entry : orders.entrySet()) {
            toReturn.append(firstEntry ? " WHERE " : " AND ");
            switch (entry.getKey()) {
                case "type" -> {
                    String[] result = entry.getValue().split(",");
                    for (int i = 0; i < result.length; i++) {
                        if (i > 0) {
                            toReturn.append(" OR ");
                        }
                        toReturn.append(entry.getKey()).append(" = '").append(result[i]).append("'");
                    }
                }
                case "rating" -> {
                    boolean isRange = entry.getValue().charAt(0) == '(';
                    String value_ = (isRange ?
                            entry.getValue().replace("(", "").replace(")", "")
                            : entry.getValue());
                    String[] resultRating = value_.split(",");
                    String comparator = (isRange ? " >= " : " = ");
                    boolean firstEntry_ = true;
                    for (int i = 0; i < resultRating.length; i++) {
                        if (isRange && i > 0) {
                            comparator = " <= ";
                        }
                        if (!firstEntry_) {
                            toReturn.append(isRange ? " AND " : " OR ");
                        }
                        if (!resultRating[i].equals("")) {
                            toReturn.append(entry.getKey()).append(comparator).append(resultRating[i]);
                            firstEntry_ = false;
                        }
                    }
                }
            }
            firstEntry = false;
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
        this.value = new Object[]{products.getName(), products.getType(), products.getRating(), products.getCategory_id()};
        return this.updateAndClose(sql);
    }
    public List<Products> readAll(String sql_bis){
        String sql = "SELECT * FROM Products" + sql_bis;
        this.value = null;
        return this.executeAndClose(sql);
    }
    public Products readOneById(int id){
        String sql = "SELECT * FROM Products as P WHERE P.id = ?";
        this.value = new Object[]{id};
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

    public List<Products> genericResearch(Map<String, String> researchProducts){
        StringBuilder addToRequest = new StringBuilder();
        StringBuilder addToRequestOrder = new StringBuilder();
        boolean firstEntry = true;

        for(var entry : researchProducts.entrySet()){
            if(!Objects.equals(entry.getKey(), "sort")){
                addToRequest.append(firstEntry ? " WHERE " : " AND ");
                firstEntry = false;
            }
            switch (entry.getKey()) {
                case "name" -> addToRequest.append(entry.getKey()).append(" LIKE '").append(entry.getValue()).append("'");
                case "type" -> {
                    String[] result = entry.getValue().split(",");
                    for (int i = 0; i < result.length; i++) {
                        if (i > 0) {
                            addToRequest.append(" OR ");
                        }
                        addToRequest.append(entry.getKey()).append(" = '").append(result[i]).append("'");
                    }
                }
                case "sort" -> {
                    String[] resultSort = entry.getValue().split(",");
                    addToRequestOrder.append(" ORDER BY ");
                    for (int i = 0; i < resultSort.length; i++) {
                        if (i > 0) {
                            addToRequestOrder.append(" , ");
                        }
                        addToRequestOrder.append(entry.getValue()).append(" ").append("ASC");
                    }
                }
            }
        }
        return this.readAll(addToRequest + addToRequestOrder.toString());
    }
}
