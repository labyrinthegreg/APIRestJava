package group5.APIRest.controller;

import group5.APIRest.Services.ProductsDao;
import group5.APIRest.models.Products;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class CrudProductController {

    @GetMapping("")
    public List<Products> readAllProducts(){
        return new ProductsDao().readAll();
    }

    @GetMapping("/{id}")
    public String readProductById(@PathVariable("id") int id){
        return "";
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
