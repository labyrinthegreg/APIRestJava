package group5.APIRest.Services;

import java.util.List;

import group5.APIRest.models.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import group5.APIRest.models.Categories;

@Repository
public class CategoryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String[] value = null;

    private List<Categories> executeAndClose(String sql){
        return jdbcTemplate.query(sql, this.value, BeanPropertyRowMapper.newInstance(Categories.class));
    }

    public List<Categories> listAll() {
        String sql = "SELECT * FROM categories;";
        return this.executeAndClose(sql);
    }

    public Categories listOneById(int id) {
        String sql = "SELECT C.name, C.description FROM Categories as C WHERE C.id = ?";
        this.value = new String[]{String.valueOf(id)};
        return this.executeAndClose(sql).get(0);

    public int addCategory(Categories category){
        String sql = "INSERT INTO categories (name) VALUES (?)";
        return jdbcTemplate.update(sql, category.getName(););
    }

    public int updateCategory(Integer id, Categories category){
        String sql = "update categories set name = ? where id = ?";
        return jdbcTemplate.update(sql, new Object[] {category.getName(), id});
    }

    public int deleteCategory(Integer id){
        String sql = "delete from categories where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}


