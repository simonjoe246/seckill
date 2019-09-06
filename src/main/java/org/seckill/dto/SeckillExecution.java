package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;

/**
 * 封装执行秒杀后的结果
 */
public class SeckillExecution {

    private long seckillId;

    private long userPhone;

    private int state;

    private String stateInfo;

    private SuccessKilled successKilled;

    public SeckillExecution(long seckillId, long userPhone, SeckillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.userPhone = userPhone;
        this.state = stateEnum.getState();
    }

    public SeckillExecution(long seckillId, long userPhone, SeckillStateEnum stateEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.userPhone = userPhone;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
