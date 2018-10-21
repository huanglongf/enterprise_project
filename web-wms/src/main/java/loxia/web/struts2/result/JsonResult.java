package loxia.web.struts2.result;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import loxia.support.json.AbstractJSONObject;

import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;

public class JsonResult extends StrutsResultSupport {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4328612208758924273L;

    private static final Logger logger = LoggerFactory.getLogger(JsonResult.class);

    public static final String DEFAULT_CONTENT_TYPE = "application/json";
    public static final String DEFAULT_ENCODING = "UTF-8";

    private String charSet = DEFAULT_ENCODING;

    @Override
    protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
        logger.debug("Handling JSON Result...");
        Charset charset = null;
        if (Charset.isSupported(charSet)) {
            charset = Charset.forName(charSet);
        } else {
            logger.warn("charset [" + charSet + "] is not recognized ");
            charset = null;
        }
        HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(HTTP_RESPONSE);

        if (charset != null) {
            response.setContentType(DEFAULT_CONTENT_TYPE + "; charset=" + charSet);
        } else {
            response.setContentType(DEFAULT_CONTENT_TYPE);
        }
        response.setHeader("Content-Disposition", "inline");


        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IllegalStateException e) {
            logger.error("Response.getWriter exception:{}", e.getMessage());
        }
        if (writer == null) return;
        AbstractJSONObject jo = null;

        @SuppressWarnings("rawtypes")
        Map request = (Map) invocation.getInvocationContext().get("request");

        Object obj = request.get("json");
        if(obj instanceof AbstractJSONObject){
        	jo = (AbstractJSONObject) obj;
        }
        if (jo != null) {
            logger.debug(jo.toString());
            writer.write(jo.toString());
        }

        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

}
