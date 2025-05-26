package com.course.plazoleta.domain.spi.feign;

import com.course.plazoleta.domain.model.feign.Track;

public interface ITrackClientPort {
    void createTrack(Track track);
}
