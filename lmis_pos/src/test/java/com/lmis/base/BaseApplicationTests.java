package com.lmis.base;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author daikaihua
 * @date 2017年11月16日
 * @todo TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseApplicationTests {

	@Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("test:111:gogo", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("test:111:gogo"));
    }
    
//    @Test
//    public void testObj() throws Exception {
//        User user=new User("aa@126.com", "aa", "aa123456", "aa","123");
//        ValueOperations<String, User> operations=redisTemplate.opsForValue();
//        operations.set("com.neox", user);
//        operations.set("com.neo.f", user,1,TimeUnit.SECONDS);
//        Thread.sleep(1000);
//        //redisTemplate.delete("com.neo.f");
//        boolean exists=redisTemplate.hasKey("com.neo.f");
//        if(exists){
//            System.out.println("exists is true");
//        }else{
//            System.out.println("exists is false");
//        }
//       // Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
//    }
	


}
