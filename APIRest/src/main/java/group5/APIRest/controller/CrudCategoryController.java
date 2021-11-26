package group5.APIRest.controller;

/***
 * All imports
 */
import com.fasterxml.jackson.core.sym.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import group5.APIRest.models.Categories;
import java.util.List;
import group5.APIRest.Services.CategoryDao;
import group5.APIRest.models.Categories;
import net.minidev.json.JSONObject;


/***
 * Controller for the categories
 * With route "/categories"
 */
@RestController
@RequestMapping("/categories")
public class CrudCategoryController {
    @Autowired
    private CategoryDao categoryDao;

    // Creating a default return JsonObject 
    private JSONObject msgReturn = new JSONObject();

    /***
     * Function to get all the categories from the database
     * @return a list with all categories from the database
     */
    @GetMapping("")
    public List<Categories> readAllCategories(){
        return categoryDao.listAll();
    }

    /***
     * Function to get a category with a specific id from the database
     * @param id
     * @return a category from the database
     */
    @GetMapping("/{id}")
    public Categories readCategoryById(@PathVariable("id") int id){
        return categoryDao.listOneById(id);
    }

    /***
     * Function to create a new category in the database 
     * @param category
     * @return a JsonObject with a message of success
     */
    @PostMapping("")
    public JSONObject createCategory(@RequestBody Categories category){
        categoryDao.addCategory(category); 
        String message = "New product " + category.getName() +" created.";
        return this.msgReturn.appendField("message", message);
    }

    /***
     * Function to update a category with a specific id in the database
     * @param id
     * @param category
     * @return a JsonObject with a message of success
     */
    @PutMapping("/{id}")
    public JSONObject updateCategory(@PathVariable("id") int id, @RequestBody Categories category){
        categoryDao.updateCategory(id, category);
        String message = "Category number " + id + " has been successfully updated";
        return this.msgReturn.appendField("message", message);
    }

    /***
     * Function to delete a category with a specific id in the database
     * @param id
     * @return a JsonObject with a message of success
     */
    @DeleteMapping("/{id}")
    public JSONObject deleteCategory(@PathVariable("id") int id){
        categoryDao.deleteCategory(id);
        String message = "Category number " + id + " has been successfully deleted";
        return this.msgReturn.appendField("message", message);
    }
}
