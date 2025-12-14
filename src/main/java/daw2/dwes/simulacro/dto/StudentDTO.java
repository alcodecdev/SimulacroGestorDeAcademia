package daw2.dwes.simulacro.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Long id;

    @NotBlank(message = "Introduce un nombre")
    private String name;

    @NotBlank(message = "Introduce un email")
    private String email;

    @Size(min=1,message = "Debes seleccionar un modulo")
    private List<Long> moduleIds;
}
