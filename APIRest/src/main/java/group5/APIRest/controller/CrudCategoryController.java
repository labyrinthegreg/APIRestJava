package group5.APIRest.controller;

import com.fasterxml.jackson.core.sym.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import group5.APIRest.models.Categories;
import java.util.List;
import group5.APIRest.Services.CategoryDao;
import group5.APIRest.models.Categories;
import net.minidev.json.JSONObject;



@RestController
@RequestMapping("/categories")
public class CrudCategoryController {
    @Autowired
    private CategoryDao categoryDao;

    @Autowired CategoryDao categoryDao;

    @GetMapping("")
    public List<Categories> readAllCategories(){
        return categoryDao.listAll();
    }

    @GetMapping("/{id}")
    public Categories readCategoryById(@PathVariable("id") int id){
        return categoryDao.listOneById(id);
    }

    @PostMapping("")
    public String createCategory(@RequestBody Categories category){
        categoryDao.addCategory(category); 
        return "New category created with name " + category.getName() ;
    }

    @PutMapping("/{id}")
    public String updateCategory(@PathVariable("id") int id, @RequestBody Categories category){
        categoryDao.updateCategory(id, category);
        return "Category number " + id + " have been updated and is now named " + category.getName();
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable("id") int id){
        categoryDao.deleteCategory(id);
        return "Category number " + id + " have been deleted";
    }
}
