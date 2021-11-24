package group5.APIRest.Services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import group5.APIRest.models.Categories;
import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Categories> listAll() {
        String sql = "SELECT * FROM categories;";
        List<Categories> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Categories.class));
        return list;
    }

}
