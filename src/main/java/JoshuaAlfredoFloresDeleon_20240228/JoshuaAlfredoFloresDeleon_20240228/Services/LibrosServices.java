package JoshuaAlfredoFloresDeleon_20240228.JoshuaAlfredoFloresDeleon_20240228.Services;

import JoshuaAlfredoFloresDeleon_20240228.JoshuaAlfredoFloresDeleon_20240228.Entities.LibrosEntity;
import JoshuaAlfredoFloresDeleon_20240228.JoshuaAlfredoFloresDeleon_20240228.Models.DTO.LibrosDTO;
import JoshuaAlfredoFloresDeleon_20240228.JoshuaAlfredoFloresDeleon_20240228.Repositories.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibrosServices {

    @Autowired
    private LibrosRepository repo;

    public List<LibrosDTO> getAllLibros(){
        List<LibrosEntity> libros = repo.findAll();
        return libros.stream()
                .map(this::convertirALibroDTO)
                .collect(Collectors.toList());

    }

    private LibrosDTO convertirALibroDTO(LibrosEntity libro){
        //Se crea un objeto de tipo dto
        LibrosDTO dto = new LibrosDTO();
        //Se guardan todos lso valores Entity en los atributos DTO
        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setIsbn(libro.getIsbn());
        dto.setAñoPublicacion(libro.getAñoPublicacion());
        dto.setGenero(libro.getGenero());

        //Si el autor es diferente de null se procede a guardar el autor en el DTO
        //En caso el autor no sea diferente de null pues en el idAutor se guardara null
        if (libro.getAutor() != null){
            dto.setIdAutor(libro.getAutorId());
        }else{
            dto.setIdAutor(null);
        }

        //Se retorna los valores en formato dto
        return dto;
    }

    private LibrosEntity convertirALibroEntity(LibrosDTO dto){
        //Se crea el libro de tipo Entity, es decir se crea como una caja de tipo ENTITY que guardaa los datos de tipo DTO
        LibrosEntity libros = new LibrosEntity();
        libros.setTitulo(dto.getTitulo());
        libros.setIsbn(dto.getIsbn());
        libros.setAñoPublicacion(dto.getAñoPublicacion());
        libros.setGenero(dto.getGenero());
        //Si el idAutor del DTO no posee ningun vloir quiere decir qur es null de lo contrario sera diferernte de null
        if (dto.getIdAutor() != null){
            AutorEntity autor = repoAutor.findById(dto.getIdAutor())
                    .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado con ID: " + dto.getIdAutor()));
            libros.setAutor(autor);
        }
        return libros;
    }

    public LibrosDTO insertLibro(LibrosDTO libroDto){
        if (libroDto == null || libroDto.getTitulo() == null || libroDto.getTitulo().isEmpty()){
            throw new IllegalArgumentException("Titulo no puede ser nulo");
        }
        try{
            LibrosEntity librosEntity = convertirALibroEntity(libroDto);
            LibrosEntity libroGuardado = repoLibro.save(librosEntity);
            return convertirALibroDTO(libroGuardado);
        } catch (Exception e) {
            log.error("Error al registrar el libro: " + e.getMessage());
            throw new ExceptionLibroNoEncontrado("Error al registrar el libro: " + e.getMessage());
        }

    }

    public LibrosDTO actualizarLibro(Long id, LibrosDTO librosDto){
        LibrosEntity libroExistente = repoLibro.findById(id).orElseThrow(() -> new
                    ExceptionsLibroNoEncontrado("Libro no encontrado"));

        libroExistente.setTitulo(librosDto.getTitulo());
        libroExistente.setIsbn(librosDto.getIsbn());
        libroExistente.setAñoPublicacion(librosDto.getAñoPublicacion());
        libroExistente.setGenero(librosDto.getGenero());

        if (librosDto.getIdAutor() != null){
            AutorEntity autor = repoAutor.findById(librosDto.getIdAutor())
                    .orElseThrow(() -> new IllegalArgumentException("Cargo no encontrado con ID proporcionados"));
            libroExistente.setAutor(autor);
        }else{
            libroExistente.setAutor0(null);
        }

        LibrosEntity libroActualizado = repoLibro.save(libroExistente);
        return convertirALibroDTO(libroActualizado);
    }

    public boolean removerLibro(Long id) {
        try {
            LibrosEntity objLibro = repoLibro.findById(id).orElse(null);
            if (objLibro != null) {
                repoLibro.deleteById(id);
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("No se encontro el usuario con ID: " + id + "para eliminar.", 1);
        }
    }
}
