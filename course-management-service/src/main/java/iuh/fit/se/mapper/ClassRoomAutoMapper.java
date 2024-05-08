package iuh.fit.se.mapper;

import iuh.fit.se.dto.ClassRoomRequest;
import iuh.fit.se.dto.ClassRoomResponse;
import iuh.fit.se.entity.ClassRoom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClassRoomAutoMapper {
    ClassRoomAutoMapper MAPPER = Mappers.getMapper(ClassRoomAutoMapper.class);

    ClassRoom mapToClassRoom(ClassRoomRequest classRoomRequest);
    ClassRoomResponse mapToClassRoomResponse(ClassRoom classRoom);
}
