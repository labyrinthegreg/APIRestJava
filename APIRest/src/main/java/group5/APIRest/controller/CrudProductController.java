package group5.APIRest.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class CrudProductController {

    @GetMapping("")
    public String readAllProducts(){
        return "";
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
