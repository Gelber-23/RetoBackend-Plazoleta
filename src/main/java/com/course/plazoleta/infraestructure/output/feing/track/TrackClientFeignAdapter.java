package com.course.plazoleta.infraestructure.output.feing.track;

import com.course.plazoleta.domain.exception.feing.TrackNotCreateException;
import com.course.plazoleta.domain.model.feign.Track;
import com.course.plazoleta.domain.spi.feign.ITrackClientPort;
import com.course.plazoleta.infraestructure.output.feing.track.mapper.ITrackMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class TrackClientFeignAdapter implements ITrackClientPort {
    private final ITrackClientFeign trackClientFeign;
    private final ITrackMapper trackMapper;
    @Override
    public void createTrack(Track track) {
        try{
            trackClientFeign.createTrack(trackMapper.toRequest(track));

        }catch(Exception exception){
            throw new TrackNotCreateException(exception.getMessage());
        }
    }
}
