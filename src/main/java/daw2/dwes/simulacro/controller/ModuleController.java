package daw2.dwes.simulacro.controller;

import daw2.dwes.simulacro.dto.ModuleDTO;
import daw2.dwes.simulacro.dto.StudentDTO;
import daw2.dwes.simulacro.model.Student;
import daw2.dwes.simulacro.service.ModuleService;
import daw2.dwes.simulacro.service.StudentService;
import daw2.dwes.simulacro.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import daw2.dwes.simulacro.model.Module;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/modules")
public class ModuleController {

    private ModuleService moduleService;
    private StudentService studentService;
    private TeacherService teacherService;

    public ModuleController(ModuleService moduleService, StudentService studentService, TeacherService teacherService) {
        this.moduleService = moduleService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("modules", moduleService.findAll());
        return "modules/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Module module = moduleService.findById(id);
        model.addAttribute("module", module);
        return "modules/detail";
    }

    @GetMapping("/new")
    public String newModule(Model model) {
        model.addAttribute("module", new ModuleDTO());
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("teachers", teacherService.findAll());
        return "modules/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Module module = moduleService.findById(id);
        ModuleDTO moduleDTO = new ModuleDTO();

        moduleDTO.setId(module.getId());
        moduleDTO.setName(module.getName());
        moduleDTO.setTeacherId(module.getTeacher().getId());
        moduleDTO.setStudentIds(module.getStudents().stream().map(Student::getId).toList());

        model.addAttribute("module", moduleDTO);
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("teachers", teacherService.findAll());
        return "modules/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {

        Module module = moduleService.findById(id);

        if (module != null) {
            moduleService.delete(module.getId());
        }

        return "redirect:/modules";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute ModuleDTO moduleDTO,
                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("module", new ModuleDTO());
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("teachers", teacherService.findAll());
            return "modules/form";
        }

        Module module;

        if (moduleDTO.getId() != null) {
            module = moduleService.findById(moduleDTO.getId());
        }else{
            module = new Module();
        }

        module.setName(moduleDTO.getName());
        module.setCredits(moduleDTO.getCredits());

        module.setTeacher(teacherService.findById(moduleDTO.getTeacherId()));

        List<Student> students = studentService.findAllById(moduleDTO.getStudentIds());

        // Inicializar la lista si es null
        if (module.getStudents() == null) {
            module.setStudents(new ArrayList<>());
        }

        // Añadir los nuevos
        for (Student student : students) {

            Student managedStudent=studentService.findById(student.getId());

            if (!module.getStudents().contains(managedStudent)) {
                module.getStudents().add(managedStudent);
            }
            if (!student.getModules().contains(module)) {
                student.getModules().add(module);
            }
        }

        // Quitar los alumnos que ya no están
        module.getStudents().removeIf(student -> !students.contains(student));


        moduleService.save(module);

        return "redirect:/modules";
    }

}
