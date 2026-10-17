package com.webapp.webcontroller;

import com.webapp.model.TodoItem;
import com.webapp.servicepack.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    void testIndex_shouldReturnIndexViewWithTodos() throws Exception {
        List<TodoItem> mockTodos = List.of(
                new TodoItem("1", "Learn Jenkins"),
                new TodoItem("2", "Build Spring Boot App")
        );
        when(todoService.getAllTodos()).thenReturn(mockTodos);

        mockMvc.perform(get("/webapp/index-data"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("todos"))
                .andExpect(model().attribute("todos", mockTodos));

        verify(todoService, times(1)).getAllTodos();
    }

    @Test
    void testAddTodo_shouldCallServiceAndRedirect() throws Exception {
        mockMvc.perform(post("/webapp/add")
                        .param("description", "Test Jenkins Pipeline"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/webapp/index-data"));

        verify(todoService, times(1)).addTodo("Test Jenkins Pipeline");
    }

    @Test
    void testDeleteTodo_shouldCallServiceAndRedirect() throws Exception {
        mockMvc.perform(post("/webapp/delete")
                        .param("id", "sample-uuid-123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/webapp/index-data"));

        verify(todoService, times(1)).deleteTodo("sample-uuid-123");
    }
}
