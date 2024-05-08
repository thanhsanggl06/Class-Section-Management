package iuh.fit.se.service.Impl;

import iuh.fit.se.dto.ClassRoomRequest;
import iuh.fit.se.dto.ClassRoomResponse;
import iuh.fit.se.entity.ClassRoom;
import iuh.fit.se.exception.ResourceNotFoundException;
import iuh.fit.se.mapper.ClassRoomAutoMapper;
import iuh.fit.se.repository.ClassRoomRepository;
import iuh.fit.se.service.ClassRoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassRoomServiceImpl implements ClassRoomService {
    private final ClassRoomRepository classRoomRepository;
    @Override
    public ClassRoomResponse add(ClassRoomRequest classRoomRequest) {
        ClassRoom classRoom = ClassRoomAutoMapper.MAPPER.mapToClassRoom(classRoomRequest);
        ClassRoom savedClassRoom = classRoomRepository.save(classRoom);

        ClassRoomResponse classRoomResponse = ClassRoomAutoMapper.MAPPER.mapToClassRoomResponse(savedClassRoom);
        return classRoomResponse;
    }

    @Override
    public Iterable<ClassRoomResponse> findAll() {
        List<ClassRoomResponse> classRoomResponses = classRoomRepository.findAll()
                .stream().map(ClassRoomAutoMapper.MAPPER::mapToClassRoomResponse).collect(Collectors.toList());
        return classRoomResponses;
    }

    @Override
    public ClassRoomResponse findById(long id) {
        ClassRoom  classRoom = classRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("CLASSROOM", "ID", id));
        ClassRoomResponse classRoomResponse = ClassRoomAutoMapper.MAPPER.mapToClassRoomResponse(classRoom);
        return classRoomResponse;
    }
}
