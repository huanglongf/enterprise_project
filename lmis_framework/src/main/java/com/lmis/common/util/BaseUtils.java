package com.lmis.common.util;


import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lmis.sys.codeRule.model.SysCoderuleInfo;
import com.lmis.sys.codeRule.service.SysCoderuleInfoServiceInterface;
import com.lmis.sys.message.dao.SysMessageMapper;
import com.lmis.sys.message.model.SysMessage;
import com.lmis.sys.message.service.SysMessageServiceInterface;

/**
 * 消息内容工具类
 *
 * @author xuyisu
 * @date 2018-05-25 17:34
 */
@Component
public class BaseUtils {
   
  
    @Autowired
    private SysMessageServiceInterface<SysMessage> sysMessageServiceInterface;
    @Autowired
    private SysCoderuleInfoServiceInterface<SysCoderuleInfo> sysCoderuleInfoServiceInterface;
    @Autowired
    private SysMessageMapper<SysMessage> sysMessageMapper;
    
    /**
     * 根据输入的编码获取编码对应的内容
     * @author xuyisu
     * @date 2018/05/28 17:33
     * @param code
     * @return
     */
    public String  GetMessage(String code)
    {
       return  sysMessageServiceInterface.getSysMessageByCode(code);
    }
    
    
    /**
     * 根据输入多个code返回相应的消息内容
     * @author xuyisu
     * @date 2018/05/28 17:33
     * @param code
     * @return
     */
    public List<Map<String, String>>  GetMessageWithList(List<String> code)
    {
       return  sysMessageMapper.getSysMessages(code);
    }
    /**
     * 根据传入的编码code和参数值，拼装消息，传参为List<String>集合
     * @author xuyisu
     * @date  2018年6月12日
     * 
     * @param code
     * @param params
     * @return
     */
    public String  GetMessageWithParams(String code,List<String> params)
    {
         String sysMessageByCode = sysMessageServiceInterface.getSysMessageByCode(code);
         Object[] array = params.toArray();
         String format = MessageFormat.format(sysMessageByCode, array); 
         return format;
    }
    /**
     * 根据传入的编码code和可变参数，拼装消息
     * @author xuyisu
     * @date  2018年6月27日
     * 
     * @param code
     * @param params
     * @return
     */
    public String  GetMessageWithParamsTwo(String code,String... params)
    {
         String sysMessageByCode = sysMessageServiceInterface.getSysMessageByCode(code);
         Object[] array = params;
         String format = MessageFormat.format(sysMessageByCode, array); 
         return format;
    }
    
    /**
     * 根据输入的配置编码获取编号
     * @author xuyisu
     * @date  2018年5月29日
     * 
     * @param configCode
     * @return
     */
    public String  GetCodeRule(String configCode)
    {
       return  sysCoderuleInfoServiceInterface.GetCodeRule(configCode);
    }
    
    
    
   
}
