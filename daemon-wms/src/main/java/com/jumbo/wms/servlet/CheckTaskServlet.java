package com.jumbo.wms.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.manager.checklist.CheckListManager;

public class CheckTaskServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -6659702384945988682L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=GBK");
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        CheckListManager checkListManager = (CheckListManager) webContext.getBean("checkListManager");
        // 检查定时任务是否OK
        boolean isOk = checkListManager.checkTaskIsOk(new Date());
        if (isOk) {
            resp.getOutputStream().write((isOk + " wms dubbo service check task : " + isOk).getBytes());
        } else {
            resp.sendError(500);
        }

    }
}
