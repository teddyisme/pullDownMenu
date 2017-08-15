package lixs.com.pulldownmenulib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePopView extends PopupWindow implements PopupWindow.OnDismissListener {

    protected LinearLayout mRootView;
    protected Context mContext;
    protected List<String> mData = new ArrayList<>();

    public BasePopView(Context context, List<String> data,MenuType menuType) {
        mContext = context;
        this.mData = data;
        initView(menuType);
        initCommonContentView();
    }

    protected void initView(MenuType menuType) {
    }

    private void initCommonContentView() {
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setTouchable(true);
//        this.setFocusable(true);
//        this.setBackgroundDrawable(new PaintDrawable());
//        this.setAnimationStyle(R.style.PopupWindowAnimation);
//        this.setOutsideTouchable(true);
        setOnDismissListener(this);

        mRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dismiss();
                return false;
            }
        });
    }

    @Override
    public void onDismiss() {
        this.setBackgroundAlpha(0);
    }

    private void setBackgroundAlpha(int bgAlpha) {
        mRootView.setBackgroundColor(Color.argb(bgAlpha, 0, 0, 0));
    }


    public void show(View anchor) {
        showAsDropDown(anchor);
        this.setBackgroundAlpha(100);//设置屏幕透明度


    }


    /**
     * 适配Android7.0
     *
     * @param anchor
     */
    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

}
