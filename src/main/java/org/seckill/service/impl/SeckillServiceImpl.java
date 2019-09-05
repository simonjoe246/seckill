package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SeckillDao seckillDao;

    private SuccessKilledDao successKilledDao;

    private String salt = "fhjwerghwuigwei#$%#$%ij4ui43i495";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exposeSeckillUrl(long seckillId) {
        Seckill seckill = getById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date nowTime = new Date();
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        // 还未开启秒杀或秒杀结束，显示当前时间和秒杀时间
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        // 转化特定字符串的过程，不可逆
        String md5 = getMD5(seckillId); //TODO
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        try {
            Date nowTime = new Date();
            int updateCounts = seckillDao.reduceNumber(seckillId, nowTime);
            // 没有更新记录，秒杀结束
            if (updateCounts <= 0) {
                throw new SeckillCloseException("seckill is closed");
            }
            else {
                // 记录购买行为
                int insertCounts = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCounts <= 0) {
                    // 重复秒杀
                    throw new RepeatKillException("seckill repeated");
                }
                else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, userPhone, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        }
        catch (SeckillCloseException e1) {
            throw e1;
        }
        catch (RepeatKillException e2) {
            throw e2;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 将所有编译期异常，转化为运行期异常(spring 遇到运行期异常会自动回滚)
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }
}
