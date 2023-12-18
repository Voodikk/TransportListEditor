package dev.voodik.editortransportlist.repository;

import dev.voodik.editortransportlist.dto.TransportPassportDTO;
import dev.voodik.editortransportlist.model.TransportPassport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportPassportRepository extends JpaRepository<TransportPassport, Integer> {


    Optional<TransportPassport> findByUniqueNumber(Integer uniqueNumber);


    @Query("SELECT new dev.voodik.editortransportlist.dto.TransportPassportDTO(p.uniqueNumber, p.referenceNumber, p.typeId, p.firmId, p.loadCapacity, p.normFuelConsumption, p.dateOfDebit) " +
            "FROM TransportPassport p " +
            "WHERE p.firmId = :firmID AND p.typeId = :typeID")
    List<TransportPassportDTO> findTransportPassportByFirmIdAndVidAndType(@Param("firmID") int firmID,@Param("typeID") int typeID);
}
