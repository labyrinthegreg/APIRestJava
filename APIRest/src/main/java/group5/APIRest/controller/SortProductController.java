package group5.APIRest.controller;

import group5.APIRest.Services.ProductsDao;
import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class SortProductController {
    @Autowired
    private ProductsDao productsDao;

    @GetMapping("/orders")
    public String sortByCol(@RequestParam String range){
        return "Asc: "+range;
    }

    @GetMapping("/search")
    public List<Products> researchProduct(@RequestParam(required = false) String type,
                                          @RequestParam(required = false) String name){
        return productsDao.readOneByType(type, name);
    }
}
