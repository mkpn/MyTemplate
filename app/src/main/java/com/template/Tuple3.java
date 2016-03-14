package com.template;

/**
 * Created by makoto on 2016/03/15.
 */
public class Tuple3<T1, T2, T3> {
    public T1 first; public T2 second; public T3 third;
    public Tuple3(T1 first, T2 second, T3 third){
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public static <T1, T2, T3> Tuple3<T1, T2, T3> create(T1 t1, T2 t2, T3 t3){
        return new Tuple3<>(t1, t2, t3);
    }
}