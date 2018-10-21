package com.lmis.common.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lmis.sys.errLog.dao.SysErrLogMapper;
import com.lmis.sys.errLog.model.SysErrLog;

/**
 * 本工具类用于记录项目中需要记录日志地方的封装（如操作日志，异常日志，用户日志等）
 * 
 * @author xsh11040
 * @date 2018年05月09日14时41分
 */
@Component
public class LimsLogUtil{

    private static Logger logger = Logger.getLogger(LimsLogUtil.class);

    @Autowired
    private SysErrLogMapper<SysErrLog> sysErrLogMapper;

    @Autowired
    private OauthUtil oauthUtil;

    /**
     * 异常日志记录
     * 
     * @author xuyisu
     * @param ex
     *            异常详细信息
     */
    public void SaveErrLog(Exception ex){
        try{
           
                StackTraceElement[] st = ex.getStackTrace();
                StackTraceElement stackTraceElement = st[0];
                String exclass = stackTraceElement.getClassName();
                String method = stackTraceElement.getMethodName();
                String errLog = "[类:" + exclass + "]调用" + method + "时在第" + stackTraceElement.getLineNumber() + "行代码处发生异常!异常类型:" + ex.getClass().getName() + ",异常信息是:" + ex.getMessage();

                //输出一遍到控制台显示
                logger.info("=========>>>>异常原因如下<<<<========:" + errLog);

                SysErrLog sysErrLog = new SysErrLog();
                sysErrLog.setByFunction(method);
                sysErrLog.setErrData(ex.getMessage());
                sysErrLog.setErrLog(errLog);
                sysErrLog.setCreateBy(oauthUtil.GetCurentUserId());
                sysErrLog.setUpdateBy(oauthUtil.GetCurentUserId());
                //sysErrLog.setPwrOrg(null);
                sysErrLogMapper.create(sysErrLog);
            
        }catch (Exception e){
            // TODO: handle exception
            logger.info("异常日志记录失败，原因：" + e.getMessage());
        }
    }
}
