package iuh.fit.se.service;

import iuh.fit.se.dto.CohortsRequest;
import iuh.fit.se.entity.Cohorts;

public interface CohortsService {
    Cohorts create(CohortsRequest cohortsRequest);
    Cohorts findById(long id);
    Iterable<Cohorts> findAll();
}
