package com.minilook.minilook.ui.base;

public interface BaseAdapterDataView<T> {

    void refresh();

    void refresh(int $position);

    void refresh(int $start, int $row);
}
