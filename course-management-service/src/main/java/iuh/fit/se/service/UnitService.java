package iuh.fit.se.service;

import iuh.fit.se.dto.UnitRequest;
import iuh.fit.se.dto.UnitResponse;
import iuh.fit.se.entity.Unit;

public interface UnitService {
    UnitResponse create(UnitRequest unitRequest);
    Iterable<UnitResponse> findUnitsBySubjectId(long subjectId);
    Unit findById(long id);
}
