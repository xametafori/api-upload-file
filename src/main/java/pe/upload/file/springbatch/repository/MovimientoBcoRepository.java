package pe.upload.file.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.upload.file.springbatch.model.MovimientoBco;

import java.util.Optional;

@Repository
public interface MovimientoBcoRepository extends JpaRepository<MovimientoBco, Integer> {

    Optional<MovimientoBco> findByNroOperacion(String nroOperacion);
}
