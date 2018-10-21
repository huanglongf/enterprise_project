package com.jumbo.zookeeper;


import java.util.Date;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baozun.task.bean.ZkSerializerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class ZkClientTest extends AbstractJUnit4SpringContextTests {

    @Autowired(required = false)
    private ZkClient zkClient;

    private static void updateNode(String fullPath, String value, ZkClient z) {
        boolean isExist = z.exists(fullPath);
        if (isExist) {
            // 更新
            z.writeData(fullPath, value);
        } else {
            // 添加
            z.createPersistent(fullPath, value);
//            z.create(fullPath, value, CreateMode.PERSISTENT);
//            z.writeData(fullPath, value);
        }
    }

    public static void copyToPath(String path, String toPath, ZkClient z) {
        List<String> list = z.getChildren(path);
        for (String s : list) {
            Object val = z.readData(path + "/" + s);
            String fullPath = toPath + "/" + s;
            updateNode(fullPath, val.toString(), z);
        }
    }


    @Test
    public static void main(String[] args) {
        String path = "/sysconfig/wms/daemon/dev";
        String toPath = "/sysconfig/wms/check";
        String hosts = "10.8.4.48:2181";
        ZkClient z = new ZkClient(hosts);
        z.setZkSerializer(new ZkSerializerImpl());
        copyToPath(path, toPath, z);
//        List<String> list = z.getChildren(path);
//        for (String s : list) {
//            // z.readData(path + "/" + s);
//            Object val = z.readData(path + "/" + s);
//
//            System.out.println(s + "\t" + val.toString());
//        }
    }


    @BeforeClass
    public static void init() {
        // ProfileConfigUtil.setMode("dev");
    }



    @Test
    public void testInsert() {

        // zkClient.createEphemeralSequential("/test2/","2");
        // zkClient.createEphemeralSequential("/test2/","3");
        // /zktask/wms/com.jumbo.wms.daemon.TtkOrderTask-ttkTransNo
        Long t = new Date().getTime();
        zkClient.createEphemeral("/mytest.cc", t);
        zkClient.createEphemeral("/mytest/haha/b", t);
        zkClient.createEphemeral("/mytest/haha/c", t);
        zkClient.createEphemeral("/mytest/haha/d", t);
        zkClient.createEphemeral("/mytest/haha/e", t);
        zkClient.createEphemeral("/mytest/haha/f", t);

        List<String> list = zkClient.getChildren("/mytest/haha");

        System.out.println(list.size());

        zkClient.delete("/mytest/haha/b");
        zkClient.delete("/mytest/haha/c");
        zkClient.delete("/mytest/haha/d");
        zkClient.delete("/mytest/haha/e");
        zkClient.delete("/mytest/haha/f");
        // zkClient.delete("/mytest/haha/");
        System.out.println("22");

        // zkClient.close();

    }

    @Test
    public void testGet() {

        List<String> list = zkClient.getChildren("/test");

        System.out.println(list.size());
    }



}
