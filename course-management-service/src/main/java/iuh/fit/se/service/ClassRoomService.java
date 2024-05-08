package iuh.fit.se.service;

import iuh.fit.se.dto.ClassRoomRequest;
import iuh.fit.se.dto.ClassRoomResponse;

public interface ClassRoomService {
    ClassRoomResponse add(ClassRoomRequest classRoomRequest);
    Iterable<ClassRoomResponse> findAll();
    ClassRoomResponse findById(long id);
}
