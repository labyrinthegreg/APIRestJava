package group5.APIRest;

import group5.APIRest.Services.CategoryDao;
import group5.APIRest.controller.CrudCategoryController;
import group5.APIRest.models.Categories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = CrudCategoryController.class)
public class CrudCategoryControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryDao categoryDao;

    @Test
    public void testReadAllCategories() throws Exception{
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadCategoryById() throws Exception{
        mockMvc.perform(get("/categories/8"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Boisson")));
    }

    @Test
    public void testCreateCategory() throws Exception{
        Categories category = new Categories();
        category.setName("Coucou");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
