package com.jumbo.web.result;


import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.util.ValueStack;

public class StreamResult extends StrutsResultSupport {

    private static final long serialVersionUID = -1468409635999059850L;

    protected static final Logger LOG = LoggerFactory.getLogger(StreamResult.class);

    public static final String DEFAULT_PARAM = "inputName";

    protected String contentType = "text/plain";
    protected String contentLength;
    protected String contentDisposition = "inline";
    protected String contentCharSet;
    protected String inputName = "inputStream";
    protected InputStream inputStream;
    protected int bufferSize = 1024;
    protected boolean allowCaching = true;

    public StreamResult() {
        super();
    }

    public StreamResult(InputStream in) {
        this.inputStream = in;
    }

    /**
     * @return Returns the whether or not the client should be requested to allow caching of the
     *         data stream.
     */
    public boolean getAllowCaching() {
        return allowCaching;
    }

    /**
     * Set allowCaching to <tt>false</tt> to indicate that the client should be requested not to
     * cache the data stream. This is set to <tt>false</tt> by default
     *
     * @param allowCaching Enable caching.
     */
    public void setAllowCaching(boolean allowCaching) {
        this.allowCaching = allowCaching;
    }


    /**
     * @return Returns the bufferSize.
     */
    public int getBufferSize() {
        return (bufferSize);
    }

    /**
     * @param bufferSize The bufferSize to set.
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * @return Returns the contentType.
     */
    public String getContentType() {
        return (contentType);
    }

    /**
     * @param contentType The contentType to set.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return Returns the contentLength.
     */
    public String getContentLength() {
        return contentLength;
    }

    /**
     * @param contentLength The contentLength to set.
     */
    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    /**
     * @return Returns the Content-disposition header value.
     */
    public String getContentDisposition() {
        return contentDisposition;
    }

    /**
     * @param contentDisposition the Content-disposition header value to use.
     */
    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    /**
     * @return Returns the charset specified by the user
     */
    public String getContentCharSet() {
        return contentCharSet;
    }

    /**
     * @param contentCharSet the charset to use on the header when sending the stream
     */
    public void setContentCharSet(String contentCharSet) {
        this.contentCharSet = contentCharSet;
    }

    /**
     * @return Returns the inputName.
     */
    public String getInputName() {
        return (inputName);
    }

    /**
     * @param inputName The inputName to set.
     */
    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    /**
     * @see org.apache.struts2.dispatcher.StrutsResultSupport#doExecute(java.lang.String,
     *      com.opensymphony.xwork2.ActionInvocation)
     */
    protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {

        // Override any parameters using values on the stack
        resolveParamsFromStack(invocation.getStack(), invocation);

        OutputStream oOutput = null;

        try {
            if (inputStream == null) {
                // Find the inputstream from the invocation variable stack
                inputStream = (InputStream) invocation.getStack().findValue(conditionalParse(inputName, invocation));
            }

            if (inputStream == null) {
                String msg = ("Can not find a java.io.InputStream with the name [" + inputName + "] in the invocation stack. " + "Check the <param name=\"inputName\"> tag specified for this action.");
                LOG.error(msg);
                throw new IllegalArgumentException(msg);
            }

            // Find the Response in context
            HttpServletResponse oResponse = (HttpServletResponse) invocation.getInvocationContext().get(HTTP_RESPONSE);

            // Set the content type
            if (contentCharSet != null && !contentCharSet.equals("")) {
                oResponse.setContentType(conditionalParse(contentType, invocation) + ";charset=" + contentCharSet);
            } else {
                oResponse.setContentType(conditionalParse(contentType, invocation));
            }

            // Set the content length
            if (contentLength != null) {
                String _contentLength = conditionalParse(contentLength, invocation);
                int _contentLengthAsInt = -1;
                try {
                    _contentLengthAsInt = Integer.parseInt(_contentLength);
                    if (_contentLengthAsInt >= 0) {
                        oResponse.setContentLength(_contentLengthAsInt);
                    }
                } catch (NumberFormatException e) {
                    if (LOG.isWarnEnabled()) {
                        LOG.warn("failed to recongnize " + _contentLength + " as a number, contentLength header will not be set", e);
                    }
                }
            }

            // Set the content-disposition
            if (contentDisposition != null) {
                oResponse.addHeader("Content-Disposition", conditionalParse(contentDisposition, invocation));
            }

            // Set the cache control headers if neccessary
            if (!allowCaching) {
                oResponse.addHeader("Pragma", "no-cache");
                oResponse.addHeader("Cache-Control", "no-cache");
            }

            // Get the outputstream
            oOutput = oResponse.getOutputStream();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Streaming result [" + inputName + "] type=[" + contentType + "] length=[" + contentLength + "] content-disposition=[" + contentDisposition + "] charset=[" + contentCharSet + "]");
            }
            try {
                // Copy input to output
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Streaming to output buffer +++ START +++");
                }
                byte[] oBuff = new byte[bufferSize];
                int iSize;
                while (-1 != (iSize = inputStream.read(oBuff))) {
                    oOutput.write(oBuff, 0, iSize);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Streaming to output buffer +++ END +++");
                }
                // Flush
                oOutput.flush();
            } catch (Exception e) {
                LOG.error("stream result error", e);
                if (oOutput != null) {
                    try {
                        oOutput.close();
                    } catch (Exception e1) {
                        oOutput = null;
                    }
                }
            } finally {
                if (inputStream != null) inputStream.close();
                if (oOutput != null) oOutput.close();
            }
        } finally {
            if (inputStream != null) inputStream.close();
            if (oOutput != null) oOutput.close();
        }
    }

    /**
     * Tries to lookup the parameters on the stack. Will override any existing parameters
     *
     * @param stack The current value stack
     */
    protected void resolveParamsFromStack(ValueStack stack, ActionInvocation invocation) {
        String disposition = stack.findString("contentDisposition");
        if (disposition != null) {
            setContentDisposition(disposition);
        }

        String contentType = stack.findString("contentType");
        if (contentType != null) {
            setContentType(contentType);
        }

        String inputName = stack.findString("inputName");
        if (inputName != null) {
            setInputName(inputName);
        }

        String contentLength = stack.findString("contentLength");
        if (contentLength != null) {
            setContentLength(contentLength);
        }

        Integer bufferSize = (Integer) stack.findValue("bufferSize", Integer.class);
        if (bufferSize != null) {
            setBufferSize(bufferSize.intValue());
        }

        if (contentCharSet != null) {
            contentCharSet = conditionalParse(contentCharSet, invocation);
        } else {
            contentCharSet = stack.findString("contentCharSet");
        }
    }

}
