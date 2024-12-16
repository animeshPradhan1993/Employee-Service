package com.animesh.employee.service.mapper;


import com.animesh.employee.service.resource.Project;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface ProjectResourceToApiModelMapper {
    ProjectResourceToApiModelMapper INSTANCE = Mappers.getMapper(ProjectResourceToApiModelMapper.class);

    @Mapping(source = "resource.id", target = "id")
    @Mapping(source = "resource.name", target = "name")
    com.animesh.generated.employee.database.model.Project map(Project resource);
}
