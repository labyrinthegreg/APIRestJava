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
        String name = category.getName();
        String sql = "INSERT INTO categories (name) VALUES (?)";
        return jdbcTemplate.update(sql, new Object[] {name});
    }
}
