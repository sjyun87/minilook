package com.minilook.minilook.ui.base;

import java.util.List;

public interface BaseAdapterDataModel<T> {

    void add(T $item);

    void add(int $index, T $item);

    void addAll(List<T> $items);

    void set(int $index, T $item);

    void set(List<T> $items);

    T get(int $index);

    List<T> get();

    void remove(int $index);

    void remove(T $item);

    void removeAll();

    void clear();

    int getSize();
}
