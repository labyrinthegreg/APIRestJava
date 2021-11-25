package group5.APIRest.controller;

import group5.APIRest.Services.ProductsDao;
import group5.APIRest.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class SortProductController {
    @Autowired
    private ProductsDao productsDao;

    @GetMapping("/orders")
    public List<Products> readWithRange(@RequestParam String range, @RequestHeader Map<String, String> headers){
        // HttpHeaders responHeaders = HttpHeaders();
        // responHeaders.set("Accept-ranges", range);
        // headers.forEach((key, value) -> {
        //     System.out.println(String.format("Header '%s' = %s", key, value));
        // });
        String[] rangeArray = range.split("-");
        int startNb = Integer.parseInt(rangeArray[0]);
        int rowsCounted = Integer.parseInt(rangeArray[1])+1 - startNb;
        return productsDao.readByRangeProducts(startNb, rowsCounted);
    }
}
