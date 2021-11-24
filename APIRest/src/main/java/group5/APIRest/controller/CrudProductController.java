package group5.APIRest.controller;

import group5.APIRest.Services.ProductsDao;
import group5.APIRest.models.Categories;
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
    public List<Products> readAllProducts(@RequestParam(required = false) String asc,
                                          @RequestParam(required = false) String desc){
        System.out.println("Asc: "+asc+" - Desc: "+desc);
        return productsDao.readAll();
    }

    @GetMapping("/{id}")
    public Products readProductById(@PathVariable("id") int id){
        return productsDao.readOneById(id);
    }

    @PostMapping("")
    public void createProduct(@RequestBody Products newProducts){
        productsDao.add(newProducts);
        return;
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable("id") int id, @RequestBody Products products){
        productsDao.updateProduct(id, products);
        return "Product number " + id + " has been successfully updated";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productsDao.delete(id);
        return "";
    }

}
