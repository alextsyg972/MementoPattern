package org.example;


public class FilterImpl<T> implements Filter<T> {
    T one;
    T two;

    public FilterImpl(T one, T two) {
        this.one = one;
        this.two = two;
    }

    @Override
    public T apply(T o) {
        return o.equals(one) ? one : two;
    }
}
