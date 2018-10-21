package com.bt.base.session;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.bt.common.util.BaseConst;
import com.bt.common.util.CommonUtil;
import com.bt.sys.model.Account;
import com.bt.sys.util.SysUtil;

/** 
 * @ClassName: SessionFilter
 * @Description: TODO(会话过滤器)
 * @author Ian.Huang
 * @date 2017年4月27日 下午3:21:16 
 * 
 */
public class SessionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 请求的uri
		String[] notFilter = new String[] { "login.jsp" };
		
		String uri = request.getRequestURI();
		
		if (!BaseConst.filterTestMark){
		    if(uri!=null&&uri.startsWith("/BT-OP")){
	            if (uri.equals("/BT-OP")){
	                response.sendRedirect("/");
	                return;
	            }
	            uri = uri.substring("/BT-OP".length());
	            response.sendRedirect(uri);
	            return;
	        }
        }
		if (uri.indexOf("control") != -1) {
			boolean doFilter = true;
			for (String s : notFilter) {
				if (uri.indexOf(s) != -1) {
					// 如果uri中包含不过滤的uri，则不进行过滤
					doFilter = false;
					break;
				}
			}
			if (doFilter&&!uri.contains("sfintest")) {
				// 从session中读取当前登录的用户
				Account account = SysUtil.getAccountInSession(request);
				// 如果session中不存在登录者实体，则弹出框提示重新登录
				// 设置request和response的字符集，防止乱码
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				if (CommonUtil.isNull(account)) {
					StringBuilder builder = new StringBuilder();
					builder.append("<script type=\"text/javascript\">");
                    // builder.append("alert('网页过期，请重新登录！');");
					builder.append("window.top.location.href='");
					builder.append(request.getScheme());
					builder.append("://");
					builder.append(request.getServerName());
					builder.append(":");
					builder.append(request.getServerPort());
					builder.append(request.getContextPath());
					builder.append("';");
					builder.append("</script>");
					response.getWriter().print(builder.toString());
					
				} else {
					filterChain.doFilter(request, response);
					
				}
				
			} else {
				filterChain.doFilter(request, response);
				
			}
			
		} else {
			filterChain.doFilter(request, response);
			
		}
		
	}

}
