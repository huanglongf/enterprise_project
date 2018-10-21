package com.bt.interf.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** 
	* @Description: 
	* @author  Hanery:
 	* @date 2017年7月20日 下午2:13:48  
*/
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mvc.xml","classpath:spring-mybatis.xml"}) 
public class TestTest {

	@Test
    @Transactional   //标明此方法需使用事务  
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void create(){
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; // 用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        try {
            String str = "";
            fis = new FileInputStream("e:\\ftp\\ftp.TXT");// FileInputStream
            // 从文件系统中的某个文件中获取字节
            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new
                                            // InputStreamReader的对象
            while ((str = br.readLine()) != null) {
                //截取得到的一行数据
                String[] parms = str.split("\t");
                for (String string : parms) {
					String[] master = string.split("\",\"");
					System.out.println(master[0].replaceAll("\"", "")+"::"+master[1].trim()+"::"+master[2].trim()
							+"::"+master[3].trim()
							+"::"+master[4].trim()
							+"::"+master[5].trim()
							+"::"+master[6].trim()
							+"::"+master[7].trim()
							+"::"+master[8].trim()
							+"::"+master[9].trim()
							+"::"+master[10].trim()
							+"::"+master[11].trim()
							+"::"+master[12].trim()
							+"::"+master[13].trim()
							+"::"+master[14].trim()
							+"::"+master[15].trim()
							+"::"+master[16].trim()
							+"::"+master[17].trim()
							+"::"+master[18].trim()
							+"::"+master[19].trim()
							+"::"+master[21].trim()
							+"::"+master[22].trim()
							+"::"+master[23].trim()
							+"::"+master[24].trim()
							+"::"+master[25].trim()
							+"::"+master[26].trim()
							+"::"+master[27].trim()
							+"::"+master[28].replaceAll("\",", "").trim());
				}
            }
            //记录下读了/写了多少条数据
            //System.out.println("read="+read+"; write="+write);
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
	}
	 
}
