
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
    void testIndexShouldReturnIndexViewWithTodos() throws Exception {
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
    void testAddTodoShouldCallServiceAndRedirect() throws Exception {
        mockMvc.perform(post("/webapp/add")
                        .param("description", "Test Jenkins Pipeline"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/webapp/index-data"));

        verify(todoService).addTodo("Test Jenkins Pipeline");
    }

    @Test
    void testUpdateTodoShouldCallServiceAndRedirect() throws Exception {
        mockMvc.perform(post("/webapp/update")
                        .param("id", "todo-123")
                        .param("description", "Updated Jenkins Pipeline"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/webapp/index-data"));

        verify(todoService).updateTodo(
                "todo-123",
                "Updated Jenkins Pipeline"
        );
    }

    @Test
    void testToggleTodoShouldCallServiceAndRedirect() throws Exception {
        mockMvc.perform(post("/webapp/toggle")
                        .param("id", "todo-123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/webapp/index-data"));

        verify(todoService).toggleTodo("todo-123");
    }

    @Test
    void testDeleteTodoShouldCallServiceAndRedirect() throws Exception {
        mockMvc.perform(post("/webapp/delete")
                        .param("id", "todo-123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/webapp/index-data"));

        verify(todoService).deleteTodo("todo-123");
    }

    @Test
    void testAddTodoWithoutDescriptionShouldReturnBadRequest()
            throws Exception {

        mockMvc.perform(post("/webapp/add"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(todoService);
    }

    @Test
    void testUpdateTodoWithoutIdShouldReturnBadRequest()
            throws Exception {

        mockMvc.perform(post("/webapp/update")
                        .param("description", "Updated task"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(todoService);
    }

    @Test
    void testToggleTodoWithoutIdShouldReturnBadRequest()
            throws Exception {

        mockMvc.perform(post("/webapp/toggle"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(todoService);
    }
}
