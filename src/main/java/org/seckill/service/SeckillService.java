package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在“使用者”角度设计接口
 * 三个方面：方法定义的粒度，参数（简单直接），返回类型（友好，return 类型/异常）
 *
 */
public interface SeckillService {

    /**
     * 查询所有秒杀产品列表
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 根据商品 id 查询商品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启输出秒杀地址
     * 秒杀未开始输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exposeSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException;
}
