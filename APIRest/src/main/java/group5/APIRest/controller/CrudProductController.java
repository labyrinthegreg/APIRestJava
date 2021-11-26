package group5.APIRest.controller;

import group5.APIRest.Services.HandleSortingInputService;
import group5.APIRest.Services.ProductsDao;
import group5.APIRest.models.Products;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


/***
 * Controller for the products
 * With route "/products"
 */
@RestController
@RequestMapping("/products")
public class CrudProductController {
    @Autowired
    private ProductsDao productsDao;
    
    // Creating a default return JsonObject 
    private JSONObject msgReturn = new JSONObject();

    /***
     * Function to get all the products from the database
     * @param asc
     * @param desc
     * @param type
     * @param rating
     * @return a list of products 
     */
    @GetMapping("")
    public List<Products> readAllProducts(@RequestParam(required = false) String asc,
                                          @RequestParam(required = false) String desc,
                                          @RequestParam(required = false) String type,
                                          @RequestParam(required = false) String rating){
        // Creating a string for all the parameters includes in the request
        String addToRequest = "";
        // Adding the parameters for the filters and the sorts
        Map<String, String> whereClose = HandleSortingInputService.makeFilters(type,rating);
        addToRequest += productsDao.reqWhere(whereClose);

        Map<String, String> orders = HandleSortingInputService.makeOrderMap(asc, desc);
        addToRequest += productsDao.reqOrder(orders);

        return productsDao.readAll(addToRequest);
    }

    /***
     * Function to get a specific products with his id
     * @param id
     * @return a Product 
     */
    @GetMapping("/{id}")
    public Products readProductById(@PathVariable("id") int id){
        return productsDao.readOneById(id);
    }

    /***
     * Function to create a new product in the database
     * @param newProducts
     * @return a JsonObject with a message of success
     */
    @PostMapping("")
    public JSONObject createProduct(@RequestBody Products newProducts){
        productsDao.add(newProducts);
        String message = "New product " + newProducts.getName() +" created.";
        return this.msgReturn.appendField("message", message);
    }

    /***
     * Function to update a product with a specific id in the database
     * @param id
     * @param products
     * @return a JsonObject with a message of success
     */
    @PutMapping("/{id}")
    public JSONObject updateProduct(@PathVariable("id") int id, @RequestBody Products products){
        productsDao.updateProduct(id, products);
        String message = "Product number " + id + " has been successfully updated";
        return this.msgReturn.appendField("message", message);
    }

     /***
     * Function to delete a product with a specific id in the database
     * @param id
     * @param products
     * @return a JsonObject with a message of success
     */
    @DeleteMapping("/{id}")
    public JSONObject deleteProduct(@PathVariable("id") int id){
        productsDao.delete(id);
        String message = "Product number " + id + " has been successfully deleted";
        return this.msgReturn.appendField("message", message);
    }

}
