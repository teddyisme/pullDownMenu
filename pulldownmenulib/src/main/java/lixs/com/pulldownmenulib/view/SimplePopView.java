package lixs.com.pulldownmenulib.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lixs.com.pulldownmenulib.BasePopView;
import lixs.com.pulldownmenulib.MenuType;
import lixs.com.pulldownmenulib.R;
import lixs.com.pulldownmenulib.adapter.SimpleFilterAdapter;
import lixs.com.pulldownmenulib.listener.OnSimpleSingleClickListener;


public class SimplePopView extends BasePopView implements SimpleFilterAdapter.SimpleClick {

    private SimpleFilterAdapter adpater;
    private List<Map<String, Object>> simpleData;
    private MenuType menuType;

    public SimplePopView(Context context, List<String> data, MenuType menuType) {
        super(context, data, menuType);
        mData = data;
        mContext = context;
        this.menuType = menuType;
        simpleData = new ArrayList<>();
    }

    private void resetData() {
        simpleData = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", mData.get(i));
            map.put("select", false);
            simpleData.add(map);
        }
    }

    protected void initView(final MenuType menuType) {
        resetData();
        mRootView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.view_simple_filter, null);

        RecyclerView list = (RecyclerView) mRootView.findViewById(R.id.list);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);

        adpater = new SimpleFilterAdapter(mContext, simpleData, this);
        list.setAdapter(adpater);

        if (menuType == MenuType.PL_SIMPLE_MULTIPLE) {
            View foot = LayoutInflater.from(mContext).inflate(R.layout.view_foot, null);
            mRootView.addView(foot, 1);
            foot.findViewById(R.id.reset_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectData.clear();
                    resetData();
                    adpater.setData(simpleData);
                }
            });

            foot.findViewById(R.id.confirm_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(selectData, menuType);
                    dismiss();
                }
            });
        }

        setContentView(mRootView);
    }

    private List<Integer> selectData = new ArrayList<>();

    private List<Integer> getPositions(int po) {
        //单选
        if (menuType == MenuType.PL_SIMPLE_SINGLE) {
            selectData.clear();
            selectData.add(po);
            return selectData;
        }

        //多选
        if (menuType == MenuType.PL_SIMPLE_MULTIPLE && !selectData.contains(po)) {
            selectData.add(po);
            return selectData;
        }

        if (selectData.size() != 0 && selectData.contains(po)) {
            selectData.remove(Integer.valueOf(po));
        }
        return selectData;
    }

    @Override
    public void onItemClick(int position) {
        if (clickListener != null) {
            getPositions(position);

            if (menuType == MenuType.PL_SIMPLE_SINGLE) {
                clickListener.onItemClick(selectData, menuType);
                dismiss();
            }

            if (menuType == MenuType.PL_SIMPLE_SINGLE || simpleData.size() == 0) {
                resetData();
            }
            Map<String, Object> d = simpleData.get(position);
            d.put("select", !(boolean) d.get("select"));
            simpleData.set(position, d);
            adpater.setData(simpleData);
        }
    }


    private OnSimpleSingleClickListener clickListener;

    public void setPopListner(OnSimpleSingleClickListener clickListener) {
        this.clickListener = clickListener;
    }


}

