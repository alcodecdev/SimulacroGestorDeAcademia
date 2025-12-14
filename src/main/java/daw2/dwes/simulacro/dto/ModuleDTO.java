package daw2.dwes.simulacro.dto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDTO {
    private Long id;

    @NotBlank(message = "Introduce un nombre")
    private String name;

    @NotNull(message = "Introduce el numero de creditos")
    private int credits;

    @NotNull(message = "Debe contener un professor")
    private Long teacherId;

    @Size(min=1,message = "Debes seleccionar un alumno")
    private List<Long> studentIds;

}
