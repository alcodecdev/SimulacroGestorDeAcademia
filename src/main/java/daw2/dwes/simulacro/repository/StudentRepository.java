package daw2.dwes.simulacro.repository;
import daw2.dwes.simulacro.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("""
           SELECT DISTINCT s 
           FROM Student s 
           LEFT JOIN s.modules m 
           WHERE (:studentName IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT(:studentName, '%')))
           AND (:moduleName IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT(:moduleName, '%')))
           """)
    List<Student> findByStudentNameAndModuleName(
            @Param("studentName") String studentName,
            @Param("moduleName") String moduleName);
}
