package com.template;

/**
 * Created by makoto on 2016/03/15.
 */
public class Tuple4<T1, T2, T3, T4> {
    public T1 first;
    public T2 second;
    public T3 third;
    public T4 forth;

    public Tuple4(T1 first, T2 second, T3 third, T4 forth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.forth = forth;
    }

    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> create(T1 t1, T2 t2, T3 t3, T4 t4) {
        return new Tuple4<>(t1, t2, t3, t4);
    }
}