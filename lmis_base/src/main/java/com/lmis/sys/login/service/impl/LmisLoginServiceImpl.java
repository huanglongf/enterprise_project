package com.lmis.sys.login.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.constant.LBASEConstant;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseInfo.LmisUserRedis;
import com.lmis.sys.functionButton.dao.SysFunctionButtonMapper;
import com.lmis.sys.functionButton.model.SysFunctionButton;
import com.lmis.sys.login.service.LmisLoginServiceInterface;
import com.lmis.sys.org.dao.SysOrgMapper;
import com.lmis.sys.org.model.SysOrg;
import com.lmis.sys.user.dao.SysUserMapper;
import com.lmis.sys.user.model.SysUser;

/**
 * @author daikaihua
 * @date 2018年1月11日
 * @todo TODO
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class LmisLoginServiceImpl<T extends SysUser> implements LmisLoginServiceInterface<T>{

    private static Logger logger = Logger.getLogger(LmisLoginServiceImpl.class);

    @Autowired
    private SysUserMapper<SysUser> sysUserMapper;

    @Autowired
    private SysOrgMapper<SysOrg> sysOrgMapper;

    @Autowired
    private SysFunctionButtonMapper<SysFunctionButton> sysFunctionButtonMapper;

    @Autowired
    private RedisTemplate<?, ?> redisTemplate;


    //用户登录信息在redis中命名规则
    @Value("${redis.key.user}")
    String redisKeyUser;

    @Value("${redis.key.user.timeout}")
    int redisKeyUserTimeOut;

    @Value("${redis.key.user.org}")
    String redisKeyUserOrg;

    @Value("${redis.key.user.fb}")
    String redisKeyUserFb;

    //功能菜单数据中类型为菜单
    public static final String FB_TYPE_FUNCTION = "fb_function";

    //功能菜单树根节点，为0
    public static final String FB_FIRST_NODE = "0";

    @Override
    public LmisResult<T> lmisLogin(T t) throws Exception{
        //校验用户密码
        LmisResult<T> lmisResult = new LmisResult<T>();
        //数据权限，需要加的时候要加载PageHelper.startPage(0, 1)上面
        //SqlCombineHelper.startSqlCombine(true); 
        
        Page<T> page = PageHelper.startPage(0, 1);     
        sysUserMapper.retrieve(t);
        List<T> list = page.toPageInfo().getList();
        if (list.size() == 0){
            lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
            throw new Exception(LBASEConstant.EBASE000106);
        }else{
            SysUser sysUser = list.get(0);
            //封装redis中的用户对象
            LmisUserRedis lur = setRedisUser(sysUser);
            //获取对应的功能权限
            setRedisUserFb(sysUser.getUserCode());
            //获取对应的数据权限
            setRedisUserOrg(sysUser.getUserCode());
            lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
            lmisResult.setData(lur);
        }
        return lmisResult;
    }

    //封装redis中的用户对象
    public LmisUserRedis setRedisUser(SysUser sysUser){
        LmisUserRedis lud = new LmisUserRedis();
        lud.setLmisUserId(sysUser.getUserCode());
        lud.setLmisUserName(sysUser.getUserName());
        lud.setLmisUserOrg(sysUser.getUserOrgCode());
        //生成token
        String tokenId = UUID.randomUUID().toString();
        lud.setLmisTokenId(tokenId);
        lud.setLastTime(new Date());
        //同步redis中数据，同时设置redis中登陆用户信息的失效时间
        @SuppressWarnings("unchecked")
        ValueOperations<String, LmisUserRedis> operations = (ValueOperations<String, LmisUserRedis>) redisTemplate.opsForValue();
        operations.set(redisKeyUser + lud.getLmisUserId() + ":" + tokenId, lud, redisKeyUserTimeOut, TimeUnit.SECONDS);
        //		operations.set(redisKeyUser + "test1:10000001", lud);
        return lud;
    }

    //获取对应的菜单功能权限
    public void setRedisUserFb(String userCode){
        SysFunctionButton sfb = new SysFunctionButton();
        sfb.setIsDeleted(false);
        sfb.setIsDisabled(false);
        sfb.setUserId(userCode);
        List<SysFunctionButton> list = sysFunctionButtonMapper.retrieve(sfb);
        if (CollectionUtils.isNotEmpty(list)){
            List<String> userFb = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++){
                SysFunctionButton temp = list.get(i);
                if (!ObjectUtils.isNull(temp.getFbType()) && FB_TYPE_FUNCTION.equals(temp.getFbType())){
                    userFb.add(temp.getFbId());
                }else{
                    userFb.add(temp.getFbId());
                }
            }
            //用户功能菜单数据同步到redis
            if (userFb.size() > 0){
                @SuppressWarnings("unchecked")
                ValueOperations<String, List<String>> operations = (ValueOperations<String, List<String>>) redisTemplate.opsForValue();
                operations.set(redisKeyUserFb + userCode, userFb);
            }
        }
    }

    //获取对应的数据权限
    public void setRedisUserOrg(String userCode){
        SysOrg so = new SysOrg();
        so.setIsDeleted(false);
        so.setIsDisabled(false);
        so.setUserId(userCode);
        List<SysOrg> list = sysOrgMapper.retrieve(so);
        if (CollectionUtils.isNotEmpty(list)){
            List<String> userOrg = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++){
                SysOrg temp = list.get(i);
                userOrg.add(temp.getOrgId());
            }
            //用户组织机构数据同步到redis
            if (userOrg.size() > 0){
                @SuppressWarnings("unchecked")
                ValueOperations<String, List<String>> operations = (ValueOperations<String, List<String>>) redisTemplate.opsForValue();
                operations.set(redisKeyUserOrg + userCode, userOrg);
            }
        }
    }

    @Override
    public LmisResult<T> lmisLogout(String lmisUserId,String lmisTokenId){
        LmisResult<T> lmisResult = new LmisResult<T>();
        lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return lmisResult;
    }

    @Override
    public LmisResult<T> getFbTree(String lmisUserId){
        LmisResult<T> lmisResult = new LmisResult<T>();
        if (lmisUserId == null || "".equals(lmisUserId)){
            lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
            return lmisResult;
        }
        //赋值查询条件
        SysFunctionButton sfb = new SysFunctionButton();
        sfb.setIsDeleted(false);
        sfb.setIsDisabled(false);
        sfb.setFbType(FB_TYPE_FUNCTION);
        sfb.setUserId(lmisUserId);
        List<SysFunctionButton> list = sysFunctionButtonMapper.retrieve(sfb);

        List<SysFunctionButton> treeList = new ArrayList<SysFunctionButton>();
        for (int i = 0; i < list.size(); i++){
            SysFunctionButton temp = list.get(i);
            if (FB_FIRST_NODE.equals(temp.getFbPid())){
                treeList.add(getUserFbData(temp, list));
            }
        }

        lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        lmisResult.setData(treeList);
        return lmisResult;
    }

    //获取用户对应功能菜单树
    public SysFunctionButton getUserFbData(SysFunctionButton temp,List<SysFunctionButton> list){
        for (int i = 0; i < list.size(); i++){
            SysFunctionButton child = list.get(i);
            if (temp.getFbId().equals(child.getFbPid())){
                logger.info("~~~~~~fbid~~~~~" + temp.getFbId() + temp.getFbName() + "~~~~~~fbpid~~~~~" + child.getFbId() + child.getFbName());
                if (temp.getChildList() == null){
                    temp.setChildList(new ArrayList<SysFunctionButton>());
                }
                temp.getChildList().add(getUserFbData(child, list));
            }
        }
        return temp;
    }

}