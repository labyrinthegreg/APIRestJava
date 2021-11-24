package group5.APIRest.controller;

import group5.APIRest.Services.ProductsDao;
import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class CrudProductController {

    @Autowired
    private ProductsDao productsDao;

    @GetMapping("")
    public List<Products> readAllProducts(){
        return productsDao.readAll();
    }

    @GetMapping("/{id}")
    public Products readProductById(@PathVariable("id") int id){
        return productsDao.readOneById(id);
    }

    @PostMapping("")
    public String createProduct(){
        return "";
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable("id") int id){
        return "";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        return "";
    }

}
