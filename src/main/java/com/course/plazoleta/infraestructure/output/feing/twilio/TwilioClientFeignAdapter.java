package com.course.plazoleta.infraestructure.output.feing.twilio;

import com.course.plazoleta.domain.exception.SmsNotSendException;
import com.course.plazoleta.domain.model.MessageSms;
import com.course.plazoleta.domain.spi.ITwilioClientPort;
import com.course.plazoleta.infraestructure.output.feing.twilio.mapper.ITwilioMapper;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TwilioClientFeignAdapter implements ITwilioClientPort {
    private final ITwilioClientFeign twilioClientFeign;
    private final ITwilioMapper twilioMapper;

    @Override
    public void sendMessageSms(MessageSms messageSms) {
        try{
            twilioClientFeign.sendSmsMessage(twilioMapper.toMessageRequest(messageSms));

        }catch(Exception exception){
            throw new SmsNotSendException(exception.getMessage());
        }

    }
}