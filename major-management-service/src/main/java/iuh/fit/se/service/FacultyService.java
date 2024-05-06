package iuh.fit.se.service;

import iuh.fit.se.dto.FacultyRequest;
import iuh.fit.se.entity.Faculty;

public interface FacultyService {
    Faculty findById(long  id);
    Iterable<Faculty> getAll();
    Faculty create(FacultyRequest request);
    Faculty update(long id, FacultyRequest request);

}
