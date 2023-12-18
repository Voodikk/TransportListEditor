package dev.voodik.editortransportlist.repository;

import dev.voodik.editortransportlist.model.TransportVid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportVidRepository extends JpaRepository<TransportVid, Integer> {


}
