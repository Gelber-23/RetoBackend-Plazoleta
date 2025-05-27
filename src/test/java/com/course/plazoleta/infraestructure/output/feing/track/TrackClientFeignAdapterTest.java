package com.course.plazoleta.infraestructure.output.feing.track;

import com.course.plazoleta.application.dto.request.feign.TrackRequest;
import com.course.plazoleta.domain.exception.feing.TrackNotCreateException;
import com.course.plazoleta.domain.model.feign.Track;
import com.course.plazoleta.infraestructure.output.feing.track.mapper.ITrackMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackClientFeignAdapterTest {

    @Mock
    private ITrackClientFeign trackClientFeign;

    @Mock
    private ITrackMapper trackMapper;

    @InjectMocks
    private TrackClientFeignAdapter adapter;

    @Test
    void createTrack_shouldCallFeign_whenNoException() {
        Track track = new Track();
        TrackRequest trackRequest = new TrackRequest();

        when(trackMapper.toRequest(track)).thenReturn(trackRequest);

        assertDoesNotThrow(() -> adapter.createTrack(track));

        verify(trackMapper).toRequest(track);
        verify(trackClientFeign).createTrack(trackRequest);
    }

    @Test
    void createTrack_shouldThrowTrackNotCreateException_whenFeignThrows() {
        Track track = new Track();
        TrackRequest trackRequest = new TrackRequest();

        when(trackMapper.toRequest(track)).thenReturn(trackRequest);
        doThrow(new RuntimeException("Feign error")).when(trackClientFeign).createTrack(trackRequest);

        TrackNotCreateException exception = assertThrows(TrackNotCreateException.class,
                () -> adapter.createTrack(track));

        assertEquals("Feign error", exception.getMessage());
        verify(trackMapper).toRequest(track);
        verify(trackClientFeign).createTrack(trackRequest);
    }

}