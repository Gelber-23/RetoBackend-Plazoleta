package com.course.plazoleta.domain.spi;

import com.course.plazoleta.domain.model.MessageSms;

public interface ITwilioClientPort {
    void sendMessageSms(MessageSms messageSms);
}
