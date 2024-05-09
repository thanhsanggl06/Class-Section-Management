package iuh.fit.se.service;

import iuh.fit.se.dto.UnitRequest;
import iuh.fit.se.dto.UnitResponse;

public interface UnitService {
    UnitResponse create(UnitRequest unitRequest);
}
