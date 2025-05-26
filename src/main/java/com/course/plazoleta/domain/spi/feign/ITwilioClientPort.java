package com.course.plazoleta.domain.spi.feign;

import com.course.plazoleta.domain.model.feign.MessageSms;

public interface ITwilioClientPort {
    void sendMessageSms(MessageSms messageSms);
}
