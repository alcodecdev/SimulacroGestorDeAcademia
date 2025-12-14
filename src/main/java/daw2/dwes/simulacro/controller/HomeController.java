package daw2.dwes.simulacro.controller;

import daw2.dwes.simulacro.model.Teacher;
import daw2.dwes.simulacro.model.Module;
import daw2.dwes.simulacro.model.Student;
import daw2.dwes.simulacro.repository.ModuleRepository;
import daw2.dwes.simulacro.repository.StudentRepository;
import daw2.dwes.simulacro.repository.TeacherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private ModuleRepository moduleRepository;
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;

    public HomeController(ModuleRepository moduleRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.moduleRepository = moduleRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Academic Management System");
        return "home";
    }

    @GetMapping("/reset")
    public String resetAndSeed() {
        /*
        moduleRepository.deleteAll();
        studentRepository.deleteAll();
        teacherRepository.deleteAll();

        Teacher t1 = new Teacher();
        t1.setName("Juan Martínez");
        t1.setSpecialty("Matemáticas");

        Teacher t2 = new Teacher();
        t2.setName("María Gómez");
        t2.setSpecialty("Informática");

        Teacher t3 = new Teacher();
        t3.setName("Pedro Sánchez");
        t3.setSpecialty("Economía");

        teacherRepository.saveAll(List.of(t1, t2, t3));

        Module m1 = new Module();
        m1.setName("Álgebra");
        m1.setCredits(6);
        m1.setTeacher(t1);

        Module m2 = new Module();
        m2.setName("Bases de Datos");
        m2.setCredits(5);
        m2.setTeacher(t2);

        Module m3 = new Module();
        m3.setName("Programación en Java");
        m3.setCredits(8);
        m3.setTeacher(t2);

        Module m4 = new Module();
        m4.setName("Microeconomía");
        m4.setCredits(4);
        m4.setTeacher(t3);

        moduleRepository.saveAll(List.of(m1, m2, m3, m4));

        Student s1 = new Student();
        s1.setName("Carlos Ruiz");
        s1.setEmail("carlos.ruiz@example.com");
        s1.setModules(new ArrayList<>(List.of(m1, m2, m3)));

        Student s2 = new Student();
        s2.setName("Lucía Fernández");
        s2.setEmail("lucia.fernandez@example.com");
        s2.setModules(new ArrayList<>(List.of(m1, m4)));

        Student s3 = new Student();
        s3.setName("Miguel Torres");
        s3.setEmail("miguel.torres@example.com");
        s3.setModules(new ArrayList<>(List.of(m2, m3)));

        Student s4 = new Student();
        s4.setName("Ana López");
        s4.setEmail("ana.lopez@example.com");
        s4.setModules(new ArrayList<>(List.of(m1, m2, m4)));

        m1.setStudents(new ArrayList<>(List.of(s1, s2, s4)));
        m2.setStudents(new ArrayList<>(List.of(s1, s3, s4)));
        m3.setStudents(new ArrayList<>(List.of(s1, s3)));
        m4.setStudents(new ArrayList<>(List.of(s2, s4)));

        studentRepository.saveAll(List.of(s1, s2, s3, s4));
        moduleRepository.saveAll(List.of(m1, m2, m3, m4));*/

        return "redirect:/";
    }

}
