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
public class SortProductController {
    @Autowired
    private ProductsDao productsDao;

    @GetMapping("/orders")
    public String sortByCol(@RequestParam String range){
        return "Asc: "+range;
    }

    @GetMapping("/search")
    public List<Products> researchProduct(@RequestParam(required = false) String type,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false) String sort){
        Map<String, String> whereClose = HandleSortingInputService.genericResearch(type, name, sort);


        return productsDao.genericResearch(whereClose);
    }
}
