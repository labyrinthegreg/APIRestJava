package group5.APIRest.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import group5.APIRest.models.Categories;

@Repository
public class CategoryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
