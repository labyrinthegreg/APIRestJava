package group5.APIRest.controller;
import org.springframework.ui.Model;
import group5.APIRest.models.Categories;
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
