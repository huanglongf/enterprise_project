package com.lmis.framework.filter;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lmis.common.util.LimsLogUtil;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.constant.BaseConstant;
import com.lmis.sys.message.model.SysMessage;
import com.lmis.sys.message.service.SysMessageServiceInterface;

/**
 * @author daikaihua
 * @date 2017年11月21日
 * @todo controller 增强器
 */
@ControllerAdvice
public class LmisControllerAdvice{

    @Autowired
    private LimsLogUtil limsLogUtil;

    @Autowired
    private SysMessageServiceInterface<SysMessage> sysMessageServiceInterface;

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * 
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder){
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * 
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model){
        //        model.addAttribute("author", "Magical Sam");
    }

    /**
     * 全局异常捕捉处理
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public LmisResult<T> errorHandler(Exception ex){
        
        //数据规则校验(包含字母和数字)
        Pattern pattern = Pattern.compile(BaseConstant.SYS_MESSAGE_REGULAR_EXPRESSION);
        Matcher matcher = pattern.matcher(ex.getMessage().trim());
        if ((ex.getMessage().length() == BaseConstant.SYS_MESSAGE_CODE_LENGTH)&&matcher.matches()){
            
            return new LmisResult<T>(LmisConstant.RESULT_CODE_ERROR, sysMessageServiceInterface.GetMessageByCodeFormat(ex.getMessage()));
        }
        limsLogUtil.SaveErrLog(ex);
        //return new LmisResult<T>(LmisConstant.RESULT_CODE_ERROR, (Object)ex.getMessage());//通过data传出去
        return new LmisResult<T>(LmisConstant.RESULT_CODE_ERROR, ex.getMessage());
    }
    
    

    /**
     * SQL异常捕捉处理
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = SQLException.class)
    public LmisResult<T> sqlExceptionHandler(SQLException slqEx){
        return new LmisResult<T>(LmisConstant.RESULT_CODE_ERROR, slqEx.getMessage());
    }

}