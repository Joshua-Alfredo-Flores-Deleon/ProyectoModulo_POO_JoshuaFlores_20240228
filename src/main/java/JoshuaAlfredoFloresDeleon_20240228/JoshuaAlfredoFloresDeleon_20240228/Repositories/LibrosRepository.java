package JoshuaAlfredoFloresDeleon_20240228.JoshuaAlfredoFloresDeleon_20240228.Repositories;

import JoshuaAlfredoFloresDeleon_20240228.JoshuaAlfredoFloresDeleon_20240228.Entities.LibrosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrosRepository extends JpaRepository<LibrosEntity, Long> {
}
