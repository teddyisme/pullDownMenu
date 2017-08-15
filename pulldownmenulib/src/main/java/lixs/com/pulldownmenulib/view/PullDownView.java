package lixs.com.pulldownmenulib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lixs.com.pulldownmenulib.BasePopView;
import lixs.com.pulldownmenulib.MenuType;
import lixs.com.pulldownmenulib.R;
import lixs.com.pulldownmenulib.listener.OnSimpleSingleClickListener;
import lixs.com.pulldownmenulib.listener.PullDownSelectDataListner;
import lixs.com.pulldownmenulib.listener.TabClickListener;


/**
 * description
 * author  XinSheng
 * date 2017/7/25
 */
public class PullDownView extends LinearLayout implements OnSimpleSingleClickListener {
    private Context mContext;
    private TabClickListener tabClickListener;
    private List<BasePopView> popupWindows = new ArrayList<>();
    private List<View> tabs = new ArrayList<>();
    private LinearLayout mL;
    private PullDownView mSelf;


    private PullDownSelectDataListner selectDataListner;

    private int selectColor;
    private int normalColor;
    private int normalArrow;
    private int selectArrow;

    private int selectTabIndex;//选中的tab


    public PullDownView setSelectListner(PullDownSelectDataListner a) {
        this.selectDataListner = a;
        return this;
    }

    public PullDownView addItem(String title, List<String> list, MenuType menuType) {
        if (list != null) {
            final View tabView = inflate(getContext(), R.layout.item_tab, null);
            final TextView labButton = (TextView) tabView.findViewById(R.id.tv_travel_type);
            labButton.setText(title);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            tabView.setLayoutParams(params);

            BasePopView popView = null;
            switch (menuType) {
                case PL_SIMPLE_SINGLE:
                    popView = new SimplePopView(mContext, list, menuType);
                    ((SimplePopView) popView).setPopListner(this);
                    break;
                case PL_SIMPLE_MULTIPLE:
                    popView = new SimplePopView(mContext, list, menuType);
                    ((SimplePopView) popView).setPopListner(this);
                    break;
            }

            popView.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    for (int j = 0; j < popupWindows.size(); j++) {
                        setShowStatus(popupWindows.get(j).isShowing(), j);
                    }
                }
            });

            final int finalI = popupWindows.size();

            tabView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tabClickListener != null) {
                        tabClickListener.OnClickListener(finalI);
                    }
                    selectTabIndex = finalI;

                    for (int j = 0; j < popupWindows.size(); j++) {
                        if (finalI != j) {
                            setHiddenTag(j);
                        }
                    }
                    setShowStatus(!popupWindows.get(finalI).isShowing(), finalI);
                }
            });
            if (popupWindows.size() != 0) {
                mL.addView(generateDivisionLine());
            }
            mL.addView(tabView);
            tabs.add(tabView);
            popupWindows.add(popView);
        }
        return this;
    }

    private void setShowStatus(boolean isshow, int i) {
        if (isshow) {
            setShowingTag(i);
        } else {
            setHiddenTag(i);
        }
    }

    private void setShowingTag(int i) {
        popupWindows.get(i).show(mSelf);
        ((TextView) tabs.get(i).findViewById(R.id.tv_travel_type)).setTextColor(selectColor);
        ((ImageView) tabs.get(i).findViewById(R.id.pull_down_arrow)).setImageResource(selectArrow);
    }

    private void setHiddenTag(int i) {
        popupWindows.get(i).dismiss();
        ((TextView) tabs.get(i).findViewById(R.id.tv_travel_type)).setTextColor(normalColor);
        ((ImageView) tabs.get(i).findViewById(R.id.pull_down_arrow)).setImageResource(normalArrow);
    }

    private View generateDivisionLine() {
        View view = new View(mContext);
        LayoutParams l = new LayoutParams(dp2px(1), ViewGroup.LayoutParams.MATCH_PARENT);
        l.setMargins(0, dp2px(8), 0, dp2px(8));
        view.setLayoutParams(l);
        view.setBackgroundColor(Color.parseColor("#FFACACAC"));
        return view;
    }

    public PullDownView setTabClickListener(TabClickListener listener) {
        this.tabClickListener = listener;
        return this;
    }


    public PullDownView(Context context) {
        super(context);
        init(context, null);
    }

    public PullDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PullDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public PullDownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PullDownView);
            selectColor = ta.getColor(R.styleable.PullDownView_pullDownSelectTextColor,
                    getResources().getColor(R.color.colorPrimaryDark));

            normalColor = ta.getColor(R.styleable.PullDownView_pullDownTextColor,
                    getResources().getColor(R.color.color333));

            normalArrow = ta.getResourceId(R.styleable.PullDownView_pullDownArrow, 0);
            selectArrow = ta.getResourceId(R.styleable.PullDownView_pullDownSelectArrow, 0);
            ta.recycle();
        }

        mContext = context;
        mSelf = this;
        setOrientation(LinearLayout.VERTICAL);
        mL = new LinearLayout(mContext);
        mL.setOrientation(HORIZONTAL);
        mL.setBackgroundColor(Color.WHITE);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, dp2px(1));
        mL.setLayoutParams(params);
        addView(mL);
    }

    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }

    @Override
    public void onItemClick(List<Integer> positions, MenuType type) {
        selectDataListner.onPullDownSelect(selectTabIndex, positions, type);
    }
}
