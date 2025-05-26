package com.course.plazoleta.infraestructure.output.feing.track.mapper;

import com.course.plazoleta.application.dto.request.feign.TrackRequest;

import com.course.plazoleta.domain.model.feign.Track;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ITrackMapper {
    TrackRequest toRequest(Track track);
}
