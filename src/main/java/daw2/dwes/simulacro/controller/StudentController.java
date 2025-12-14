package daw2.dwes.simulacro.controller;

import daw2.dwes.simulacro.dto.StudentDTO;
import daw2.dwes.simulacro.model.Module;
import daw2.dwes.simulacro.model.Student;
import daw2.dwes.simulacro.service.ModuleService;
import daw2.dwes.simulacro.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    StudentService studentService;
    ModuleService moduleService;

    public StudentController(StudentService studentService,ModuleService moduleService) {
        this.studentService = studentService;
        this.moduleService = moduleService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students/list";

    }

    @GetMapping("/new")
    public String newStudent(Model model) {
        model.addAttribute("student", new StudentDTO());
        model.addAttribute("modules", moduleService.findAll());
        return "students/form";

    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "students/detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id);
        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setModuleIds(student.getModules().stream().map(Module::getId).toList());

        model.addAttribute("student", studentDTO);
        model.addAttribute("modules", moduleService.findAll());
        return "students/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {

        Student student = studentService.findById(id);

        if (student != null) {
            studentService.delete(student.getId());
        }

        return "redirect:/students";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute StudentDTO studentDTO,
                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("student", new StudentDTO());
            model.addAttribute("modules", moduleService.findAll());
            return "students/form";
        }

        Student student;

        if (studentDTO.getId() != null) {
            student = studentService.findById(studentDTO.getId());
        }else{
            student = new Student();
        }

        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());

        List<Module> modules = moduleService.findAllById(studentDTO.getModuleIds());

        // Inicializar la lista si es null
        if (student.getModules() == null) {
            student.setModules(new ArrayList<>());
        }

        // Añadir nuevos módulos
        for (Module module : modules) {
            if (!student.getModules().contains(module)) {
                student.getModules().add(module);
            }
            if (!module.getStudents().contains(student)) {
                module.getStudents().add(student);
            }
        }

        // Quitar los módulos que el alumno ya no tiene
        student.getModules().removeIf(module -> !modules.contains(module));

        studentService.save(student);

        return "redirect:/students";
    }

}
