package org.seckill.dao;

import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {

    /**
     * 插入成功购买记录,联合主键可过滤重复
     * @param seckillId
     * @param userPhone
     * @return 插入的行数，返回0表示插入失败
     */
    int insertSuccessKilled(long seckillId, long userPhone);

    /**
     * 根据商品id返回秒杀明细并携带秒杀产品对象
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(long seckillId);
}
