package api.stenden.data.jpa;

import api.stenden.data.model.ImageAnnotated;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<ImageAnnotated, Long> {
}
