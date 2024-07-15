package top.fanxfan.design.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义异常
 *
 * @author fanxfan
 */
@Setter
@Getter
@Slf4j
public final class CustomException extends RuntimeException {

    private final String message;

    public CustomException(String message) {
        this.message = message;
        logMessage();
    }

    void logMessage() {
        log.error(this.message);
    }
}