package daw2.dwes.simulacro.controller;

import daw2.dwes.simulacro.dto.TeacherDTO;
import daw2.dwes.simulacro.model.Module;
import daw2.dwes.simulacro.model.Student;
import daw2.dwes.simulacro.model.Teacher;
import daw2.dwes.simulacro.service.ModuleService;
import daw2.dwes.simulacro.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private TeacherService teacherService;
    private ModuleService  moduleService;

    public TeacherController(TeacherService teacherService, ModuleService moduleService) {
        this.teacherService = teacherService;
        this.moduleService = moduleService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "teachers/list";
    }

    @GetMapping("/new")
    public String newTeacher(Model model) {
        model.addAttribute("teacher", new TeacherDTO());
        model.addAttribute("modules", moduleService.findAll());
        return "teachers/form";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.findById(id);
        model.addAttribute("teacher", teacher);
        return "teachers/detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.findById(id);
        TeacherDTO teacherDTO = new TeacherDTO();

        teacherDTO.setId(teacher.getId());
        teacherDTO.setName(teacher.getName());
        teacherDTO.setSpecialty(teacher.getSpecialty());
        teacherDTO.setModulesIds(teacher.getModules().stream().map(Module::getId).toList());

        model.addAttribute("teacher", teacherDTO);
        model.addAttribute("modules", moduleService.findAll());
        return "teachers/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {

        Teacher teacher = teacherService.findById(id);

        if (teacher != null) {
            // Desvincular todos los módulos del profesor
            if (teacher.getModules() != null) {
                for (Module module : teacher.getModules()) {
                    module.setTeacher(null);
                    moduleService.save(module); // guardar el cambio en la base
                }
            }
            // Ahora sí eliminar al profesor
            teacherService.delete(id);
        }

        return "redirect:/teachers";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute TeacherDTO teacherDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("teacher", new TeacherDTO());
            model.addAttribute("modules", moduleService.findAll());
            return "teachers/form";
        }

        Teacher teacher;

        if (teacherDTO.getId() != null) {
            teacher = teacherService.findById(teacherDTO.getId());
        }else {
            teacher = new Teacher();
        }

        teacher.setName(teacherDTO.getName());
        teacher.setSpecialty(teacherDTO.getSpecialty());
        List<Module> modules = moduleService.findAllById(teacherDTO.getModulesIds());

        if (teacher.getModules() == null) {
            teacher.setModules(new ArrayList<>());
        }

        // Limpiamos los módulos antiguos si es edición
        teacher.getModules().clear();

        // Asignamos módulos al teacher y teacher a cada módulo
        for (Module module : modules) {
            teacher.getModules().add(module); // añadimos módulo al teacher
            module.setTeacher(teacher);       // asignamos teacher al módulo
        }

        teacherService.save(teacher);

        return "redirect:/teachers";
    }



}
