package com.lmis.framework.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lmis.common.dataFormat.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseInfo.LmisUserRedis;

/**
 * @author daikaihua
 * @date 2017年11月15日
 * @todo TODO
 */
public class LmisInterceptor implements HandlerInterceptor {
	
private static Log logger = LogFactory.getLog(LmisInterceptor.class);
	
	@Resource
    private RedisTemplate<?, ?> redisTemplate;
	
	@Autowired
	private HttpSession session;
	
	//用户登录信息在redis中命名规则
	@Value("${redis.key.user}")
	String redisKeyUser;
	
	@Value("${redis.key.user.fb}")
	String redisKeyUserFb;

	@Value("${redis.key.user.timeout}")
	int redisKeyUserTimeOut;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	logger.info("~~~LMIS_Interceptor1~~~在请求处理之前进行调用（Controller方法调用之前）");
    	String lmisUserId = request.getParameter("lmisUserId");
    	String lmisTokenId = request.getParameter("lmisTokenId");
    	String lmisFbFunction = request.getParameter("lmisFbFunction");
    	String lmisFbButton = request.getParameter("lmisFbButton");
    	
    	logger.info("~~~request user~~~"+lmisUserId+"~~~~~token id~~~~~"+lmisTokenId+"~~~~~~lmisFbFunction~~~~~~"+lmisFbFunction+"~~~~lmisFbButton~~~~~"+lmisFbButton);
    	if(!"".equals(lmisUserId) && !"".equals(lmisTokenId)){
    		//通过tokenid获取redis中的对应用户信息
    		@SuppressWarnings("unchecked")
    		ValueOperations<String, LmisUserRedis> operations = (ValueOperations<String, LmisUserRedis>)redisTemplate.opsForValue();
    		String redisKey = redisKeyUser + lmisUserId + ":" +lmisTokenId;
    		LmisUserRedis lud = operations.get(redisKey);
    		//判断用户是否已经登陆
    		if(lud == null){
    			writerPrintForUserMiss(response, LmisConstant.RESULT_CODE_LOGIN_ERROR);
    			return false;
    		}
    		if(!lmisUserId.equals(lud.getLmisUserId())){
    			writerPrintForUserMiss(response, LmisConstant.RESULT_CODE_LOGIN_ERROR);
    			return false;
    		}
    		//判断用户的功能菜单与按钮是否有权限
    		if(lmisFbFunction!=null && !"".equals(lmisFbFunction)){
        		@SuppressWarnings("unchecked")
    			ValueOperations<String, List<String>> operations1 = (ValueOperations<String, List<String>>)redisTemplate.opsForValue();
        		String redisKey1 = redisKeyUserFb+lmisUserId;
        		List<String> fbList = new ArrayList<String>();
        		fbList = operations1.get(redisKey1);
        		if(!fbList.contains(lmisFbFunction)){
        			writerPrintForUserMiss(response, LmisConstant.RESULT_CODE_NO_FB_ERROR);
        			return false;
        		}
        		if(!ObjectUtils.isNull(lmisFbButton)){
					if(!fbList.contains(lmisFbButton)){
						writerPrintForUserMiss(response, LmisConstant.RESULT_CODE_NO_FB_ERROR);
						return false;
					}
				}
    		}
    		
        	//刷新redis中用户最后操作时间
    		lud.setLastTime(new Date());
    		operations.set(redisKeyUser + lud.getLmisUserId() + ":" + lud.getLmisTokenId(), lud, redisKeyUserTimeOut, TimeUnit.SECONDS);
    		//把用户信息写入session中去
    		session.setAttribute("lmisUserId", lud.getLmisUserId());
    		session.setAttribute("lmisTokenId", lud.getLmisTokenId());
    		session.setAttribute("lmisUserName", lud.getLmisUserName());
    		session.setAttribute("lmisUserOrg", lud.getLmisUserOrg());
    		session.setAttribute("lastTime", lud.getLastTime());
    		session.setAttribute("lmisFbFunction", lmisFbFunction);
    		session.setAttribute("lmisFbButton", lmisFbButton);
    		return true;
    	}else{
    		// 只有返回true才会继续向下执行，返回false取消当前请求
			writerPrintForUserMiss(response, LmisConstant.RESULT_CODE_LOGIN_ERROR);
    		return false;
    	}
    }
    
    public void writerPrintForUserMiss(HttpServletResponse response, String errorMessage) throws IOException{
    	PrintWriter out = response.getWriter();
    	LmisResult<String> lmisResult = new LmisResult<String>();
    	lmisResult.setCode(errorMessage);
		out.print(JSON.toJSONString(lmisResult));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    	logger.info(">>>LMIS_Interceptor1>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    	logger.info(">>>LMIS_Interceptor1>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }

}