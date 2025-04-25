package fh.bswe.statusserver.service;

import fh.bswe.statusserver.entity.Status;
import fh.bswe.statusserver.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status setStatus(Status status) {
        return statusRepository.save(status);
    }

    public Status getStatus(String name) throws Exception {
        final Optional<Status> status = statusRepository.findStatusByName(name);

        if (status.isPresent()) {
            return status.get();
        } else {
            throw new Exception("not available");
        }
    }

    public List<Status> getAllStatus() throws Exception {
        final Iterable<Status> allStatus = statusRepository.findAll();
        final List<Status> statusList = new ArrayList<>();
        allStatus.forEach(statusList::add);

        return statusList;
    }

    public void setAllStatus(List<Status> statusList) throws Exception {
        statusRepository.saveAll(statusList);
    }
}
