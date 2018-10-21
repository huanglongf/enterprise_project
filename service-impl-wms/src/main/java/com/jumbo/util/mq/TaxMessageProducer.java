package com.jumbo.util.mq;

import org.springframework.jms.core.JmsTemplate;

/**
 * Date: 2008-8-28 Time: 17:14:23
 */
public class TaxMessageProducer {

    private JmsTemplate template;

    private String destination;

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void send(TaxMessage message) {
        template.convertAndSend(this.destination, message);
    }

}
