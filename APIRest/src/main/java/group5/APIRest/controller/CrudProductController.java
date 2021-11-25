package group5.APIRest.controller;

import group5.APIRest.Services.HandleSortingInputService;
import group5.APIRest.Services.ProductsDao;
import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class CrudProductController {
    @Autowired
    private ProductsDao productsDao;

    @GetMapping("")
    public List<Products> readAllProducts(@RequestParam(required = false) String asc,
                                          @RequestParam(required = false) String desc,
                                          @RequestParam(required = false) String type,
                                          @RequestParam(required = false) String rating){
        System.out.println("Asc: "+asc+" - Desc: "+desc);
        String addToRequest = "";
        Object[] value = null;

        Map<String, String> whereClose = HandleSortingInputService.makeFilters(type,rating);
        addToRequest += productsDao.reqWhere(whereClose);

        Map<String, String> orders = HandleSortingInputService.makeOrderMap(asc, desc);
        addToRequest += productsDao.reqOrder(orders);

        return productsDao.readAll(addToRequest, value);
    }

    @GetMapping("/{id}")
    public Products readProductById(@PathVariable("id") int id){
        return productsDao.readOneById(id);
    }

    @PostMapping("")
    public String createProduct(@RequestBody Products newProducts){
        productsDao.add(newProducts);
        return "The product has been successfully added";
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable("id") int id, @RequestBody Products products){
        productsDao.updateProduct(id, products);
        return "Product number " + id + " has been successfully updated";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productsDao.delete(id);
        return "The product has been successfully deleted";
    }

}
