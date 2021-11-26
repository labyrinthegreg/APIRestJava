package group5.APIRest.controller;

/***
 * All imports
 */
import group5.APIRest.Services.HandleSortingInputService;
import group5.APIRest.Services.ProductsDao;
import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/***
 * Controller for the products with specific requests
 * With route "/products"
 */
@RestController
@RequestMapping("/products")
public class SortProductController {
    @Autowired
    private ProductsDao productsDao;


    /***
     * Function to get all products on a specific range
     * @param range
     * @param response
     * @return a list with all the products included in the range
     */
    @GetMapping("/orders") 
    public List<Products> readWithRange(@RequestParam String range, HttpServletResponse response){
        // Creating a a list for the bad requested and a product for bad request 
        List<Products> erreurProducts = new ArrayList<>();
        Products erreurProduct = new Products();
        // Creating base for the links in the response headers
        String links = "";
        String roadLink = "http://127.0.0.1:8080/products/orders?range=";
        // Adding a default limit range
        int limitRange = 25;
        // Getting range parameter from the request 
        String[] rangeArray = range.split("-");
        int startNb;
        int rowsCounted;
        int lengthProducts = productsDao.readAll(" ").size();
        // Check if the range is not empty or start with a negative number else return null product with an error name
        if (rangeArray[0] != ""){
            startNb = Integer.parseInt(rangeArray[0]);
            rowsCounted = Integer.parseInt(rangeArray[1])+1 - startNb;
        } else {
            erreurProduct.setName("erreur: Ton premier produit ne peut pas être négatif ");
            erreurProducts.add(0, erreurProduct);
            return erreurProducts;
        }
        // Check if the range is included in our limit else return null product with an error name
        if (limitRange >= rowsCounted) {
            // Check which link need to be add in the reponse headers 
            if (startNb > 0) {
                links += roadLink+"0-"+String.valueOf(rowsCounted-1)+";rel='first',";
            }
            if (startNb > rowsCounted){
                links += roadLink+String.valueOf(startNb-rowsCounted)+"-"+String.valueOf(startNb-1)+";rel='prev',";
            }
            if (lengthProducts-1 < startNb+rowsCounted){
                links += roadLink+String.valueOf(startNb+rowsCounted)+"-"+String.valueOf(startNb+(2*rowsCounted)-1)+";rel='next',";
            }
            if (startNb+rowsCounted <= lengthProducts){
                links += roadLink+String.valueOf(lengthProducts-rowsCounted)+"-"+String.valueOf(lengthProducts-1)+";rel='last',";
            }
            // Add the response headers and return the list of products reques
            response.addHeader("Accept-Ranges", "Products " + String.valueOf(limitRange));
            response.addHeader("Current-Range", range + "/" + String.valueOf(lengthProducts));
            response.addHeader("Links", links);
            return productsDao.readByRangeProducts(startNb, rowsCounted);
        } 
        else{
            erreurProduct.setName("erreur: Ta range maximal est de "+String.valueOf(limitRange)+" tu es à "+String.valueOf(rowsCounted));
            erreurProducts.add(0, erreurProduct);
            return erreurProducts;
        }
        
    }


    /***
     * Function to get all products for search type, name, and sort them
     * @param type
     * @param name
     * @param sort
     * @return a list of products with the expected parameters
     */
    @GetMapping("/search")
    public List<Products> researchProduct(@RequestParam(required = false) String type,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false) String sort){
        Map<String, String> whereClose = HandleSortingInputService.genericResearch(type, name, sort);


        return productsDao.genericResearch(whereClose);
    }
}
