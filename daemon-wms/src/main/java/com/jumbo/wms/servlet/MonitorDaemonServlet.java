package com.jumbo.wms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.baozun.monitor.bean.MonitorService;
import com.baozun.monitor.command.ServiceNodeCommand;
import com.jumbo.util.JsonUtil;



/**
 * 系统初始化时的servlet
 * @author Justin Hu
 *
 */
public class MonitorDaemonServlet extends HttpServlet {
	
    private static final long serialVersionUID = 6110015112023864794L;


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
	
		httpProcess(req,resp);
    }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
	
		httpProcess(req,resp);
    }
	
	
	private void httpProcess(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
	
        resp.setContentType("application/json;charset=utf-8");

		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext()); 
		
		MonitorService monitorService=(MonitorService)context.getBean("monitorService");
		
		ServiceNodeCommand snc= monitorService.monitor();
		
        String str = JsonUtil.obj2jsonStrIncludeAll(snc);
		
		
		resp.getWriter().write(str);
		
		
		
    }
	
	
	
}
