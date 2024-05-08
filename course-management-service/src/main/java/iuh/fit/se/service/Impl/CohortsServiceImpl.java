package iuh.fit.se.service.Impl;

import iuh.fit.se.dto.CohortsRequest;
import iuh.fit.se.entity.Cohorts;
import iuh.fit.se.exception.ResourceNotFoundException;
import iuh.fit.se.mapper.CohortsAutoMapper;
import iuh.fit.se.repository.CohortsRepository;
import iuh.fit.se.service.CohortsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CohortsServiceImpl implements CohortsService {
    private final CohortsRepository cohortsRepository;
    @Override
    public Cohorts create(CohortsRequest cohortsRequest) {
        Cohorts cohorts = CohortsAutoMapper.MAPPER.mapToCohorts(cohortsRequest);
        Cohorts savedCohorts = cohortsRepository.save(cohorts);
        return savedCohorts;
    }

    @Override
    public Cohorts findById(long id) {
        Cohorts cohorts = cohortsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("COHORTS","ID", id));
        return cohorts;
    }

    @Override
    public Iterable<Cohorts> findAll() {
        List<Cohorts> responses = cohortsRepository.findAll();
        return responses;
    }
}
