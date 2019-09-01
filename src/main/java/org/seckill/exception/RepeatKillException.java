package org.seckill.exception;

/**
 * 重复秒杀异常(运行期异常)
 * spring 声明式事务只接受运行期异常回滚策略
 */
public class RepeatKillException extends SeckillException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
