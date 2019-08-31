package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    // 注入 SuccessKilledDao 实体
    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() {
        int insertResult = successKilledDao.insertSuccessKilled(1001L, 17694816966L);
        System.out.println("Insert Result: " + insertResult);
    }

    @Test
    public void queryByIdWithSeckill() {
        long userPhone = 17694816966L;
        long seckillId = 1001L;
        SuccessKilled queryResult = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
        System.out.println(queryResult);
        System.out.println(queryResult.getSeckill());
    }
}