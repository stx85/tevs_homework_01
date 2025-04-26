package fh.bswe.statusserver.service;

import fh.bswe.statusserver.dto.StatusDto;
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

    public StatusDto setStatus(StatusDto status) {
        final Optional<Status> foundStatus = statusRepository.findStatusByName(status.getName());

        if(foundStatus.isPresent()) {
            foundStatus.get().setDate(status.getDate());
            foundStatus.get().setInfo(status.getInfo());
            return mapToDto(statusRepository.save(foundStatus.get()));
        } else {
            return mapToDto(statusRepository.save(mapToEntity(status)));
        }
    }

    public StatusDto getStatus(String name) throws Exception {
        final Optional<Status> status = statusRepository.findStatusByName(name);

        if (status.isPresent()) {
            return mapToDto(status.get());
        } else {
            throw new Exception("not available");
        }
    }

    public List<StatusDto> getAllStatus() throws Exception {
        final Iterable<Status> allStatus = statusRepository.findAll();
        final List<StatusDto> statusList = new ArrayList<>();
        allStatus.forEach(status -> statusList.add(mapToDto(status)));

        return statusList;
    }

    public void setAllStatus(List<StatusDto> statusList) throws Exception {
        final List<Status> statusListToStore = new ArrayList<>();
        statusList.forEach(status -> statusListToStore.add(mapToEntity(status)));

        statusRepository.saveAll(statusListToStore);
    }

    private StatusDto mapToDto(Status status) {
        StatusDto dto = new StatusDto();
        dto.setDate(status.getDate());
        dto.setName(status.getName());
        dto.setInfo(status.getInfo());
        return dto;
    }

    private Status mapToEntity(StatusDto dto) {
        Status status = new Status();
        status.setName(dto.getName());
        status.setInfo(dto.getInfo());
        status.setDate(dto.getDate());
        return status;
    }
}
