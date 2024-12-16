package com.animesh.employee.service.mapper;

import com.animesh.employee.service.resource.Project;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface ProjectApiModelToResourceMapper {
    ProjectApiModelToResourceMapper INSTANCE = Mappers.getMapper(ProjectApiModelToResourceMapper.class);

    @Mapping(source = "project.id", target = "id")
    @Mapping(source = "project.name", target = "name")
    Project map(com.animesh.generated.employee.database.model.Project project);


}
