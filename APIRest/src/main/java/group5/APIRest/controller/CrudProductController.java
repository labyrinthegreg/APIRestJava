package group5.APIRest.controller;

import group5.APIRest.Services.ProductsDao;
import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class CrudProductController {
    @Autowired
    private ProductsDao productsDao;

    @GetMapping("")
    public String readAllProducts(){
        return "";
    }

    @GetMapping("/{id}")
    public String readProductById(@PathVariable("id") int id){
        return "";
    }

    @PostMapping("")
    public void createProduct(@RequestBody Products newProducts){
        productsDao.add(newProducts);
        return;
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable("id") int id){
        return "";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productsDao.delete(id);
        return "";
    }



}
