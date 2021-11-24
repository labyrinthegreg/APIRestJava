package group5.APIRest.controller;

import group5.APIRest.Services.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import group5.APIRest.models.Categories;

import java.util.List;


@RestController
@RequestMapping("/categories")
public class CrudCategoryController {

    @Autowired CategoryDao categoryDao;

    @GetMapping("")
    public List<Categories> readAllCategories(){
        return categoryDao.listAll();
    }

    @GetMapping("/{id}")
    public String readCategoryById(@PathVariable("id") int id){
        return "";
    }

    @PostMapping("")
    public String createCategory(@ModelAttribute Categories category, Model model){
        return "New category added";
    }

    @PutMapping("/{id}")
    public String updateCategory(@PathVariable("id") int id){
        return "";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable("id") int id){
        return "";
    }
}
