package lixs.com.pulldownmenulib.listener;

import java.util.List;

import lixs.com.pulldownmenulib.MenuType;

public interface OnSimpleSingleClickListener {
    void onItemClick(List<Integer> position, MenuType type);
}