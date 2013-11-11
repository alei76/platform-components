package com.audaque.lib.core.utils;

/**
 *
 * @author Administrator
 */
public class Triple<F, S, T> {

    public F first;
    public S second;
    public T third;

    public F getFirst() {
        return first;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public T getThird() {
        return third;
    }

    public void setThird(T third) {
        this.third = third;
    }
}
