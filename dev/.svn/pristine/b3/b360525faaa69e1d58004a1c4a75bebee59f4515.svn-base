package com.bt.exception;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

/**
 * 错误监控机制
 * @author Administrator
 *
 */
public class AnnotationHandlerMethodExceptionResolver extends ExceptionHandlerExceptionResolver{

	private static final Logger logger = Logger.getLogger(AnnotationHandlerMethodExceptionResolver.class);
	
	private String defaultErrorView;
	private ModelAndView resultModel;
	
	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,HandlerMethod handlerMethod, Exception exception) {
		// TODO Auto-generated method stub
		markException(handlerMethod,exception,request);
		resultModel=new ModelAndView();
		resultModel.setViewName(defaultErrorView);
		return resultModel;
	}	
	
	void markException(HandlerMethod handlerMethod,Exception exception,HttpServletRequest request){
		HashMap<String,String>map=new HashMap<String,String>();	
		try {

			//记录方法名
//			map.put("method", handlerMethod.getMethod().getName());
			//记录错误信息
			logger.error("**********************"+exception.toString()+"**********************");
			map.put("exceptionInfo",exception.toString());
			//记录错误的映射信息
			map.put("mapperInfo",request.getRequestURI());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public String getDefaultErrorView() {
		return defaultErrorView;
	}
	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}	
}
