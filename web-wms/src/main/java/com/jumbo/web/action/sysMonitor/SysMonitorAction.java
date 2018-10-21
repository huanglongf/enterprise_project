package com.jumbo.web.action.sysMonitor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.monitor.bean.MonitorService;
import com.baozun.monitor.command.ServiceNodeCommand;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;



/**
 * @author lihui
 *
 * @createDate 2016年4月8日 上午10:02:11
 */
public class SysMonitorAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -7934160450973122421L;

    @Autowired
    private MonitorService monitorService;
    
    public void monitorWmsWeb() {
        ServiceNodeCommand snc = monitorService.monitor();

        String str = JsonUtil.writeValue(snc);

        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().print(str);
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
