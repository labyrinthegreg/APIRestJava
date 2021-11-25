package group5.APIRest.controller;

import group5.APIRest.Services.ProductsDao;
import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/products")
public class SortProductController {
    @Autowired
    private ProductsDao productsDao;

    @GetMapping("/orders") 
    public List<Products> readWithRange(@RequestParam String range, HttpServletResponse response){
        List<Products> erreurProducts = new ArrayList<>();
        Products erreurProduct = new Products();
        String links = "";
        String roadLink = "http://127.0.0.1:8080/products/orders?range=";
        int limitRange = 25;
        String[] rangeArray = range.split("-");
        int startNb;
        int rowsCounted;
        int lengthProducts = productsDao.readAll(" ", new Object[] {}).size();
        if (rangeArray[0] != ""){
            startNb = Integer.parseInt(rangeArray[0]);
            rowsCounted = Integer.parseInt(rangeArray[1])+1 - startNb;
        } else {
            startNb = Integer.parseInt(rangeArray[1]);
            rowsCounted = Integer.parseInt(rangeArray[2])+1 - startNb;
        }
        if (rangeArray[0] != "" ){
            if (limitRange >= rowsCounted) {
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
        else {
            erreurProduct.setName("erreur: Ton premier produit ne peut pas être négatif ");
            erreurProducts.add(0, erreurProduct);
            return erreurProducts;
        }
        
    }

    // @GetMapping("/search")
    // public List<Products> researchProduct(@RequestParam(required = false) String type,
    //                                       @RequestParam(required = false) String name){
    //     return productsDao.readOneByType(type, name);
    // }
}
