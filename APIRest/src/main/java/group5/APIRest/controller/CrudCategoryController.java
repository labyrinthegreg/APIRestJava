package group5.APIRest.controller;

import com.fasterxml.jackson.core.sym.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import group5.APIRest.models.Categories;
import org.springframework.web.bind.annotation.*;

import group5.APIRest.Services.CategoryDao;
import group5.APIRest.models.Categories;
import net.minidev.json.JSONObject;


@RestController
@RequestMapping("/categories")
public class CrudCategoryController {
    @Autowired
    private CategoryDao categoryDao;

    @GetMapping("")
    public String readAllCategories(){
        return "";
    }

    @GetMapping("/{id}")
    public String readCategoryById(@PathVariable("id") int id){
        return "";
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
