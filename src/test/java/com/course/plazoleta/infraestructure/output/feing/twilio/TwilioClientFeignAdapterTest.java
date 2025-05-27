package com.course.plazoleta.infraestructure.output.feing.twilio;

import com.course.plazoleta.application.dto.request.feign.MessageSmsRequest;
import com.course.plazoleta.domain.exception.feing.SmsNotSendException;
import com.course.plazoleta.domain.model.feign.MessageSms;
import com.course.plazoleta.infraestructure.output.feing.twilio.mapper.ITwilioMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TwilioClientFeignAdapterTest {
    @Mock
    private ITwilioClientFeign twilioClientFeign;

    @Mock
    private ITwilioMapper twilioMapper;

    @InjectMocks
    private TwilioClientFeignAdapter adapter;

    @Test
    void sendMessageSms_shouldCallFeign_whenNoException() {
        MessageSms messageSms = new MessageSms("1234567890", "Test message");
        MessageSmsRequest request = new MessageSmsRequest();

        when(twilioMapper.toMessageRequest(messageSms)).thenReturn(request);

        assertDoesNotThrow(() -> adapter.sendMessageSms(messageSms));

        verify(twilioMapper).toMessageRequest(messageSms);
        verify(twilioClientFeign).sendSmsMessage(request);
    }

    @Test
    void sendMessageSms_shouldThrowSmsNotSendException_whenFeignThrows() {
        MessageSms messageSms = new MessageSms("1234567890", "Test message");
        MessageSmsRequest request = new MessageSmsRequest();

        when(twilioMapper.toMessageRequest(messageSms)).thenReturn(request);
        doThrow(new RuntimeException("Failed")).when(twilioClientFeign).sendSmsMessage(request);

        SmsNotSendException exception = assertThrows(SmsNotSendException.class,
                () -> adapter.sendMessageSms(messageSms));

        assertEquals("Failed", exception.getMessage());
        verify(twilioMapper).toMessageRequest(messageSms);
        verify(twilioClientFeign).sendSmsMessage(request);
    }

}