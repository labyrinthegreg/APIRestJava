package group5.APIRest.Services;

import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ProductsDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Object[] value = null;

    /**
     * Execute research with the sql request using jdbcTemplate
     * @param sql the sql request
     * @return the List of all product we search for
     */
    private List<Products> executeAndClose(String sql){
        return jdbcTemplate.query(sql, this.value, BeanPropertyRowMapper.newInstance(Products.class));
    }

    /**
     * Update the sql database with the sql request using jdbcTemplate
     * @param sql, the sql request
     * @return the number of row updated
     */
    private int updateAndClose(String sql){
        return jdbcTemplate.update(sql, this.value);
    }

    /**
     * Make a string to add to another SELECT request where we want a WHERE clause
     * @param orders a map with all the data we want to add, which has the form 'key = value' in the DB
     * @return a part to add to the sql request
     */
    public String reqWhere(Map<String, String> orders){
        //String request = "type = Boisson OR type = pate  ";
        StringBuilder toReturn = new StringBuilder();
        List<String> newValue = new ArrayList<>();
        boolean firstEntry = true;
        for (var entry : orders.entrySet()) {

            toReturn.append(firstEntry ? " WHERE " : " AND ");
            boolean isRange = entry.getValue().charAt(0) == '(';
            String value_ = (isRange ?
                    entry.getValue().replace("(", "").replace(")", "")
                    : entry.getValue());
            String key = entry.getKey();
            String[] result = value_.split(",");
            String comparator = (isRange ? " >= " : " = ");
            switch (key) {
                case "type":
                    for (int i = 0; i < result.length; i++) {
                        if (i > 0) {
                            toReturn.append(" OR ");
                        }
                        toReturn.append(key).append(" = ?");
                        newValue.add(result[i]);
                    }
                    break;
                case "createdat":
                    //Before doing the build of the sql request, we need to translate the date value
                    key = "created_at";
                    if (!isRange) {
                        comparator = " LIKE ";
                    }
                    for(int i = 0; i<result.length; i++){
                        result[i] = "'"+ result[i] + (!isRange? "%" : "") +"'";
                    }

                case "rating":
                    boolean firstEntry_ = true;
                    for (int i = 0; i < result.length; i++) {
                        if (isRange && i > 0) {
                            comparator = " <= ";
                        }
                        if (!firstEntry_) {
                            toReturn.append(isRange ? " AND " : " OR ");
                        }
                        if (!result[i].equals("")) {
                            toReturn.append(key).append(comparator).append("?");
                            newValue.add(result[i]);
                            firstEntry_ = false;
                        }
                    }
                    break;
            }
            firstEntry = false;
        }
        this.value = new Object[]{newValue};
        return toReturn.toString();
    }

    /**
     * Make a string to add to another SELECT request where we want an ORDER BY clause
     * @param orders a map with all the data we want to add, which has the form 'value KEY'(key can be ASC or DESC) in the DB
     * @return a part to add to the sql request
     */
    public String reqOrder(Map<String, String> orders){
        StringBuilder toReturn = new StringBuilder();
        boolean firstEntry = true;
        for (var entry : orders.entrySet()) {
            toReturn.append(firstEntry ? " ORDER BY " : ", ").append(entry.getValue()).append(" ").append(entry.getKey());
            firstEntry = false;
        }
        return toReturn.toString();
    }

    /**
     * Insert into the db a Product
     * @param products to add, it's given in a json form the request of the user
     * @return the row affected by the request
     */
    public int add(Products products){
        String sql = "INSERT INTO products (name , type, rating, category_id) VALUES (?, ?, ?, ?);";
        this.value = new Object[]{products.getName(), products.getType(), products.getRating(), products.getCategory_id()};
        return this.updateAndClose(sql);
    }

    /**
     * See all the product saved in the DB
     * @param sql_bis a part to add to the request if their some filters in the url
     * @return the list of products from the DB
     */
    public List<Products> readAll(String sql_bis){
        String sql = "SELECT * FROM Products" + sql_bis;
        if (sql_bis.equals("")){
            this.value = null;
        }
        System.out.println(sql_bis);
        System.out.println(this.value.getClass());
        return this.executeAndClose(sql);
    }

    /**
     * Get 1 Product from the DB, select by its id,
     * If the wanted ID does not match with a Product in the DB, we send a Blank Product with no data
     * @param id of the product wanted
     * @return the data of the product
     */
    public Products readOneById(int id){
        String sql = "SELECT * FROM Products as P WHERE P.id = ?";
        this.value = new Object[]{id};
        try {
            return this.executeAndClose(sql).get(0);
        }
        catch (IndexOutOfBoundsException e){
            return new Products(){};
        }
    }

    /**
     * Give a List of a given number of product from the DB
     * @param startNb the offset value where we want to start to display product
     * @param rowsCounted the number of row to display
     * @return the list of product
     */
    public List<Products> readByRangeProducts(int startNb, int rowsCounted){
        String sql = "SELECT * FROM products LIMIT ? , ? ";
        this.value = new Object[]{startNb, rowsCounted};
        return this.executeAndClose(sql);
    }

    /**
     * Delete a product by ID
     * @param id the id of the product
     * @return the number of row affected
     */
    public int delete(Integer id){
        String sql = "DELETE FROM products WHERE id = ?";
        this.value = new Object[]{id};
        return this.updateAndClose(sql);
    }

    /**
     * Update the data of a specific product
     * @param id the id of the product
     * @param product the new data of the product
     * @return the number of row affected
     */
    public int updateProduct(Integer id, Products product){
        String sql = "update products set name = ?, type = ?, rating = ?, category_id = ? where id = ?";
        this.value = new Object[] {product.getName(), product.getType(), product.getRating(), product.getCategory_id() , id};
        return this.updateAndClose(sql);
    }

    /**
     * Search for all the Product with the match data given in the map Object
     * @param researchProducts the map with all the data wanted from the url
     * @return the list of product
     */
    public List<Products> genericResearch(Map<String, String> researchProducts){
        StringBuilder addToRequest = new StringBuilder();
        StringBuilder addToRequestOrder = new StringBuilder();

        List<String> newValue = new ArrayList<>();
        boolean firstEntry = true;

        for(var entry : researchProducts.entrySet()){
            if(!Objects.equals(entry.getKey(), "sort")){
                addToRequest.append(firstEntry ? " WHERE " : " AND ");
                firstEntry = false;
            }
            switch (entry.getKey()) {
                case "name" -> {
                    addToRequest.append(entry.getKey()).append(" LIKE ?");
                    newValue.add(entry.getValue());
                }
                case "type" -> {
                    String[] result = entry.getValue().split(",");
                    for (int i = 0; i < result.length; i++) {
                        if (i > 0) {
                            addToRequest.append(" OR ");
                        }
                        addToRequest.append(entry.getKey()).append(" = ?");
                        newValue.add(result[i]);
                    }
                }
                case "sort" -> {
                    String[] resultSort = entry.getValue().split(",");
                    addToRequestOrder.append(" ORDER BY ");
                    for (int i = 0; i < resultSort.length; i++) {
                        if (i > 0) {
                            addToRequestOrder.append(" , ");
                        }
                        addToRequestOrder.append("? ").append("ASC");
                        newValue.add(entry.getValue());
                    }
                }
            }
        }
        this.value = new Object[]{newValue};
        return this.readAll(addToRequest + addToRequestOrder.toString());
    }
}
