package com.webapp.webcontroller;

import com.webapp.servicepack.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/webapp")
public class HomeController {


    @Autowired
    TodoService todoService;
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

    @PostMapping("/delete")
    public String delete(@RequestParam String id) {
        todoService.deleteTodo(id);
        return "redirect:/webapp/index-data";
    }
}