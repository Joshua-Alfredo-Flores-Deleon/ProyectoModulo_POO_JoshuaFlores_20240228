package JoshuaAlfredoFloresDeleon_20240228.JoshuaAlfredoFloresDeleon_20240228.Models.DTO;

import jakarta.validation.MessageInterpolator;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.message.Message;

@ToString @EqualsAndHashCode
@Getter @Setter
public class LibrosDTO {

    private Long id;

    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    @NotBlank(message = "El isbn es obligatoio")
    private String  isbn;

    @Getter @Setter
    private Long añoPublicacion;

    @Getter @Setter
    private String genero;

    private Long idAutor;
}
