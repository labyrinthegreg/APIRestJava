package group5.APIRest.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import group5.APIRest.models.Categories;


@RestController
@RequestMapping("/categories")
public class CrudCategoryController {

    @GetMapping("")
    public String readAllCategories(){
        return "";
    }

    @GetMapping("/{id}")
    public String readCategoryById(@PathVariable("id") int id){
        return "";
    }

    @PostMapping("")
    public String createCategory(@ModelAttribute Categories category, Model model){
        model.addAttribute("user", new User());
        
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
