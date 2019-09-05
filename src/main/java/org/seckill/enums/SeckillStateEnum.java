package org.seckill.enums;

/**
 * 使用枚举表述常量数据字典
 */
public enum SeckillStateEnum {
    /**
     * 以下是根据构造方法定义的几个枚举常量
     */
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATA_REWRITE(-3, "数据篡改");

    private int state;

    private String stateInfo;

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    /**
     * 根据传入的 state 状态值返回枚举类型
     * @param state
     * @return
     */
    public static SeckillStateEnum stateOf(int state) {
        for (SeckillStateEnum stateInstance: values()) {
            if (stateInstance.getState() == state) {
                return stateInstance;
            }
        }
        return null;
    }
}
