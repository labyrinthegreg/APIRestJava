package group5.APIRest.controller;

import group5.APIRest.Services.ProductsDao;
import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
