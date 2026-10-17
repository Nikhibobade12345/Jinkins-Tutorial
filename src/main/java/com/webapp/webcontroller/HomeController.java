package com.webapp.webcontroller;

import com.webapp.servicepack.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/webapp")
public class HomeController {

    private final TodoService todoService;

    public HomeController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/index-data")
    public String index(Model model) {
        model.addAttribute("todos", todoService.getAllTodos());
        return "index";
    }

    @PostMapping("/add")
    public String add(@RequestParam String description) {
        todoService.addTodo(description);
        return "redirect:/webapp/index-data";
    }

    @PostMapping("/update")
    public String update(
            @RequestParam String id,
            @RequestParam String description) {

        todoService.updateTodo(id, description);
        return "redirect:/webapp/index-data";
    }

    @PostMapping("/toggle")
    public String toggle(@RequestParam String id) {
        todoService.toggleTodo(id);
        return "redirect:/webapp/index-data";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String id) {
        todoService.deleteTodo(id);
        return "redirect:/webapp/index-data";
    }
}