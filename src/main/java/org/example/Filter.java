package org.example;

public interface Filter<T> {
    T apply(T o);
}
