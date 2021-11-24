package group5.APIRest.controller;

import org.springframework.web.bind.annotation.*;

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
    public String createCategory(){
        return "";
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
