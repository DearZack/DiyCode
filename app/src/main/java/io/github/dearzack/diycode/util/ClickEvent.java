package io.github.dearzack.diycode.util;

/**
 * Created by Zack on 2017/6/27.
 */

public class ClickEvent<T> {

    private T message;

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
