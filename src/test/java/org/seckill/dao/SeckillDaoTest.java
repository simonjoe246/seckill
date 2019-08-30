package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置 spring 与 junit 整合，junit 启动时加载 springIOC 容器
 * srping-test, junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉 junit spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})

public class SeckillDaoTest {
    // 注入 DAO 实现类依赖
    // 使用 resource 会自动去 spring 容器中查找对应的实现类
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() {
        Date killTime = new Date();
        int updateResult = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("Update Result :" + updateResult);
    }

    @Test
    public void queryById() {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);

    }

    @Test
    public void queryAll() {
        List<Seckill> seckills =  seckillDao.queryAll(0 ,100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
    }
}