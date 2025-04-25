package fh.bswe.statusserver.repository;

import fh.bswe.statusserver.entity.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StatusRepository extends CrudRepository<Status, Long> {
    Optional<Status> findStatusByName(String name);
}
