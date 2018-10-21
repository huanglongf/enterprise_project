package com.jumbo.webservice.etam.client;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityHeaderImpl implements SOAPHandler<SOAPMessageContext> {
    protected static final Logger log = LoggerFactory.getLogger(SecurityHeaderImpl.class);
    public Set<QName> getHeaders() {
        return null;
    }

    public void close(MessageContext context) {

    }

    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    public boolean handleMessage(SOAPMessageContext context) {
        boolean request_p = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (request_p) {
            try {
                SOAPMessage msg = context.getMessage();
                SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
                SOAPHeader hdr = env.getHeader();
                if (hdr == null) hdr = env.addHeader();

                // 添加认证信息
                QName qname_user = new QName("http://www.bpl.com.cn/", "SecurityHeader");
                SOAPHeaderElement helem_user = hdr.addHeaderElement(qname_user);
                // helem_user.setActor(SOAPConstants.URI_SOAP_1_2_ROLE_NEXT);

                // hdr.add
                QName x = new QName("http://www.bpl.com.cn/", "SecurityKey");
                SOAPHeaderElement xxu = hdr.addHeaderElement(x);
                xxu.addTextNode(" www.bpl.com.cn ");
                helem_user.addChildElement(xxu);


                // helem_user.addTextNode("SecurityKey&www.bpl.com.cn");
                msg.saveChanges();
                // 把SOAP消息输出到System.out，即控制台
                return true;
            } catch (Exception e) {
                log.error("",e);
            }
        }
        return false;
    }

}
