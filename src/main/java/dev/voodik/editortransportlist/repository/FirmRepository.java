package dev.voodik.editortransportlist.repository;

import dev.voodik.editortransportlist.model.Firm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FirmRepository extends JpaRepository<Firm, Integer> {

    Optional<Firm> findById(Integer id);
}
