package daw2.dwes.simulacro.controller;

import daw2.dwes.simulacro.model.Teacher;
import daw2.dwes.simulacro.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "teachers/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.findById(id);
        model.addAttribute("teacher", teacher);
        return "teachers/detail";
    }
}
