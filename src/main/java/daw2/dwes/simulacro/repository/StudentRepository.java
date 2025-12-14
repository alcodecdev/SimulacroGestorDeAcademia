package daw2.dwes.simulacro.repository;
import daw2.dwes.simulacro.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {}
