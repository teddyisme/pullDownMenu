package lixs.com.pulldownmenulib.listener;

import java.util.List;

import lixs.com.pulldownmenulib.MenuType;

/**
 * description
 * author  XinSheng
 * date 2017/8/1
 */
public interface PullDownSelectDataListner {
    void onPullDownSelect(int tab,List<Integer> data,  MenuType menuType);//true 点击消失 false点击不消失

}
