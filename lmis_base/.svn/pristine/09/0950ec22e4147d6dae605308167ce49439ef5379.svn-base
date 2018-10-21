package com.lmis.sys.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.util.BaseUtils;
import com.lmis.sys.message.dao.SysMessageMapper;
import com.lmis.sys.message.model.SysMessage;
import com.lmis.sys.message.service.SysMessageServiceInterface;

import cn.hutool.json.JSONUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageTest{

    @Autowired
    private SysMessageMapper<SysMessage> sysMessageMapper;

    @Autowired
    private SysMessageServiceInterface<SysMessage> sysMessageServiceInterface;

    @Autowired
    private BaseUtils baseUtils;

    /**
     * TEST A01
     * 
     * @throws Exception
     */
    @Test
    public void test() throws Exception{
        SysMessage t = new SysMessage();
        List<SysMessage> retrieve = sysMessageMapper.retrieve(t);
        System.out.println(JSONUtil.toJsonStr(retrieve));
    }

    /**
     * 保存系统消息
     * 
     * @throws Exception
     */
    @Transactional
    @Rollback(true)
    @Test
    public void Save() throws Exception{
        /*
         * SysMessage sysMessage=new SysMessage();
         * sysMessage.setCode("SLMI00002");
         * sysMessage.setMessage("你好搞笑");
         * LmisResult<?> retrieve = sysMessageServiceInterface.executeInsert(sysMessage);
         * System.out.println(JSONUtil.toJsonStr(retrieve));
         */
    }

    /**
     * 更新系统消息
     * 
     * @throws Exception
     */
    @Transactional
    @Rollback(true)
    @Test
    public void Update() throws Exception{
        //        SysMessage sysMessage=new SysMessage();
        //        sysMessage.setId("60b0a6c4-5f29-11e8-9d0d-005056954f8e");
        //        sysMessage.setCode("123");
        //        sysMessage.setMessage("你傻啊");
        //        LmisResult<?> retrieve = sysMessageServiceInterface.executeUpdate(sysMessage);
        //        System.out.println(JSONUtil.toJsonStr(retrieve));
    }

    /**
     * 获取系统消息
     * 
     * @throws Exception
     */
    @Test
    public void Get() throws Exception{
        //        long start = System.currentTimeMillis();
        //        for (int i = 0; i < 1; i++){
        //            String sysMessageByCode = sysMessageServiceInterface.getSysMessageByCode("SLMIS00001");
        //            System.out.println(sysMessageByCode);
        //            
        //        }
        //        long end = System.currentTimeMillis();
        //        System.out.println("用时："+(end-start)/1000);
    }

    /**
     * 获取系统消息
     * 
     * @throws Exception
     */
    @Test
    public void getSysMessageByCode() throws Exception{
        //        SysMessage sysMessageByCode = sysMessageMapper.getSysMessageByCode("SLMI00001");
        //        System.out.println(JSONUtil.toJsonStr(sysMessageByCode));
    }

    /**
     * 根据传入的多个消息code获取多个消息值
     * 
     * @author xuyisu
     * @date 2018年6月12日
     *
     */
    @Test
    public void getSysMessages(){
        List<String> list = new ArrayList<>();
        list.add("EBASE000001");
        list.add("EBASE000002");
        List<Map<String, String>> sysMessages = baseUtils.GetMessageWithList(list);
        System.out.println(JSONUtil.toJsonStr(sysMessages));
    }

    /**
     * 根据输入的配置编码获取编号
     * 
     * @author xuyisu
     * @date 2018年6月12日
     *
     */
    @Test
    public void GetMessageWithParams(){
        List<String> params = new ArrayList<>();
        //建议定义好参数的顺序
        params.add(0, "姓名:张无忌");
        params.add(1, "技能:九阳神功");
        params.add(2, "大招:乾坤大挪移");
  
        //假设EBASE000001  对应的消息值为：查无此编码,{0},{1},{2}
        String getMessageWithParams = baseUtils.GetMessageWithParams("EBASE000001", params);
        System.out.println(getMessageWithParams);
    }
    
    /**
     * 根据输入的配置编码获取编号
     * 
     * @author xuyisu
     * @date 2018年6月12日
     *
     */
    @Test
    public void GetMessageWithParamsTwo(){
        //假设EBASE000001  对应的消息值为：查无此编码,{0},{1},{2}
        String getMessageWithParams = baseUtils.GetMessageWithParamsTwo("EBASE000001", "张无忌","九阳神功","乾坤大挪移");
        System.out.println(getMessageWithParams);
    }

}
