package com.jumbo.webservice.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IP验证过滤器
 * 
 * @author sjk
 * 
 */
public class IpInteceptor extends AbstractPhaseInterceptor<Message> {

    protected static final Logger log = LoggerFactory.getLogger(IpInteceptor.class);
    /**
     * 外部编码
     */
    private String sourceCode;

    public IpInteceptor(String phase) {
        super(phase);
    }

    public IpInteceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        String clienIp = getClientIp(request);
        log.debug("ip : {}",clienIp);
    }

    private String getClientIp(HttpServletRequest request) {
        if (request != null) {
            String ipAddress = request.getRemoteAddr();
            return ipAddress;
        } else {
            return null;
        }
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }
}
