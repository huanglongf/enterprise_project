package com.lmis.pos.whsUser.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.pos.common.controller.BaseController;
import com.lmis.pos.whsUser.model.PosWhsUser;
import com.lmis.pos.whsUser.service.PosWhsUserServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosWhsUserController
 * @Description: TODO(仓库用户控制层类)
 * @author codeGenerator
 * @date 2018-05-29 16:31:04
 * 
 */
@Api(value = "仓库用户")
@RestController
@RequestMapping(value="/pos")
public class PosWhsUserController extends BaseController{
	
	@Resource(name="posWhsUserServiceImpl")
	PosWhsUserServiceInterface<PosWhsUser> posWhsUserService;
	
	@ApiOperation(value="仓库用户查询")
    @RequestMapping(value = "/whsUserSearch", method = RequestMethod.POST)
    public String whsUserSearch(@ModelAttribute PosWhsUser poWhsUser) throws Exception {
        return JSON.toJSONString(posWhsUserService.whsUserSearch(poWhsUser));
    }
    @ApiOperation(value="新增用户")
    @RequestMapping(value = "/whsBindingUser", method = RequestMethod.POST)
    public String whsBindingUser(String whsCode,String userList) throws Exception {
        return JSON.toJSONString(posWhsUserService.whsBindingUser(whsCode,userList));
    }
    @ApiOperation(value="删除用户")
    @RequestMapping(value = "/deleteWhsUser", method = RequestMethod.POST)
    public String deleteWhsUser(@ModelAttribute PosWhsUser poWhsUser) throws Exception {
        return JSON.toJSONString(posWhsUserService.deleteWhsUser(poWhsUser));
    }
    @ApiOperation(value="查询用户")
    @RequestMapping(value = "/getAllUser", method = RequestMethod.POST)
    public String getAllUser() throws Exception {
        return JSON.toJSONString(posWhsUserService.getAllUser());
    }
}
