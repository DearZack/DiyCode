package io.github.dearzack.diycode.base;

/**
 * Created by Zack on 2017/6/29.
 */

public class BaesEvent<T> {
    private T message;

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
