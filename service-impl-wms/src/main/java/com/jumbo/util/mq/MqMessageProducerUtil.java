package com.jumbo.util.mq;

import java.io.Serializable;

import org.springframework.jms.core.JmsTemplate;

/**
 * Message Producer
 * 
 * @author sjk
 * 
 */
public class MqMessageProducerUtil {

    private JmsTemplate template;

    private String destination;

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void send(Serializable message) {
        template.convertAndSend(this.destination, message);
    }

}
