package dev.voodik.editortransportlist.repository;

import dev.voodik.editortransportlist.dto.TransportTypeDTO;
import dev.voodik.editortransportlist.model.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportTypeRepository extends JpaRepository<TransportType, Integer> {

    @Query("SELECT new dev.voodik.editortransportlist.dto.TransportTypeDTO(t.id, t.name, t.vidId, t.attribute) " +
            "FROM TransportType t WHERE t.vidId = :vidId")
    List<TransportTypeDTO> findDTOWithVidId(@Param("vidId") int vidId);

    Optional<TransportType> findById (Integer id);
}
