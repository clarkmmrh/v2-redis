package com.study.mmrh;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.study.mmrh.cache.utils.JedisUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.runners.MethodSorters;

/**
 * Redis业务测试用例
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRedis {



    @Resource
    private JedisUtils jedis;

    @Test
    public void test() {

        jedis.set("aaaaaa1","xxxxxx");
        System.out.println(jedis.get("aaaaaa1"));
    }



    @Test
    public void test1(){
        for (int i = 1; i <= 3; i++) {
            String orderId = "order:"+i;
            Map<String,String> map = new HashMap<>();
            map.put("orderId",i+"");
            map.put("money","99");
            map.put("time","2020-01-01");
            jedis.hmset(orderId,map);
        }
    }

    @Test
    public void test2(){

        String[] ordersArr = {"order:1","order:2","order:3"};

        String user1 = "user:1:order";
        jedis.lpush(user1,ordersArr);
    }

    @Test
    public void test3(){
        String orderId = "order:"+4;
        Map<String,String> map = new HashMap<>();
        map.put("orderId",4+"");
        map.put("money","199");
        map.put("time","2020-01-01");
        jedis.hmset(orderId,map);
    }

    @Test
    public void test4(){
        String user1 = "user:1:order";

        String orderId = "order:"+4;
        jedis.lpush(user1,orderId);
    }

    @Test
    public void test5(){
        List<String> orders = new ArrayList<>();

        String user1 = "user:1:order";

        orders = jedis.lrange(user1,0,-1);

        System.out.println("所有订单："+orders);
        System.out.println();

        for (String order:orders) {
            String[] fileds = {"orderId","money","time"};
            List<String> orderId = jedis.hmget(order,fileds);
            System.out.println("订单："+order +" ，订单详情："+orderId.toString());
        }
    }

}