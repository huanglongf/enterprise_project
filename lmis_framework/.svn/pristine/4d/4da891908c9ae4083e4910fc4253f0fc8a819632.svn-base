package com.lmis.common.util;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户权限相关工具类
 * 
 * @author xsh11040
 *
 */
@Component
public class OauthUtil{

    @Autowired
    private HttpSession session;

    /**
     * 获取当前登录用户的id
     * 
     * @return
     */
    public String GetCurentUserId(){
        String CurentUser = null;
        try{
            CurentUser = session.getAttribute("lmisUserId").toString();
        }catch (Exception e){
            // TODO: handle exception
            CurentUser = null;
        }
        return CurentUser;

    }
    /**
     * 获取当前登录用户TokenId
     * @author xuyisu
     * @date  2018年6月13日
     * 
     * @return
     */
    public String GetCurentTokenId(){
        String CurentUser = null;
        try{
            CurentUser = session.getAttribute("lmisTokenId").toString();
        }catch (Exception e){
            // TODO: handle exception
            CurentUser = null;
        }
        return CurentUser;

    }
    /**
     * 获取当前登录用户UserName
     * @author xuyisu
     * @date  2018年6月13日
     * 
     * @return
     */
    public String GetCurentUserName(){
        String CurentUser = null;
        try{
            CurentUser = session.getAttribute("lmisUserName").toString();
        }catch (Exception e){
            // TODO: handle exception
            CurentUser = null;
        }
        return CurentUser;

    }
    /**
     * 获取当前登录用户UserOrg
     * @author xuyisu
     * @date  2018年6月13日
     * 
     * @return
     */
    public String GetCurentUserOrg(){
        String CurentUser = null;
        try{
            CurentUser = session.getAttribute("lmisUserOrg").toString();
        }catch (Exception e){
            // TODO: handle exception
            CurentUser = null;
        }
        return CurentUser;

    }
    /**
     * 获取当前登录用户lastTime
     * @author xuyisu
     * @date  2018年6月13日
     * 
     * @return
     */
    public String GetCurentLastTime(){
        String CurentUser = null;
        try{
            CurentUser = session.getAttribute("lastTime").toString();
        }catch (Exception e){
            // TODO: handle exception
            CurentUser = null;
        }
        return CurentUser;

    }
    /**
     * 获取当前登录用户FbFunction
     * @author xuyisu
     * @date  2018年6月13日
     * 
     * @return
     */
    public String GetCurentFbFunction(){
        String CurentUser = null;
        try{
            CurentUser = session.getAttribute("lmisFbFunction").toString();
        }catch (Exception e){
            // TODO: handle exception
            CurentUser = null;
        }
        return CurentUser;

    }
    /**
     * 获取当前登录用户FbButton
     * @author xuyisu
     * @date  2018年6月13日
     * 
     * @return
     */
    public String GetCurentFbButton(){
        String CurentUser = null;
        try{
            CurentUser = session.getAttribute("lmisFbButton").toString();
        }catch (Exception e){
            // TODO: handle exception
            CurentUser = null;
        }
        return CurentUser;

    }
    
    
}
