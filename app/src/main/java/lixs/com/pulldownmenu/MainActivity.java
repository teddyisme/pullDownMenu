package lixs.com.pulldownmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import lixs.com.pulldownmenulib.MenuType;
import lixs.com.pulldownmenulib.listener.PullDownSelectDataListner;
import lixs.com.pulldownmenulib.listener.TabClickListener;
import lixs.com.pulldownmenulib.view.PullDownView;

public class MainActivity extends AppCompatActivity implements PullDownSelectDataListner, TabClickListener {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PullDownView downView = (PullDownView) findViewById(R.id.pullDownView);

        downView.setSelectListner(this)
                .addItem("分类1", Arrays.asList("项目6", "项目7", "项目8", "项目4"), MenuType.PL_SIMPLE_SINGLE)
                .setTabClickListener(this);

        downView.setSelectListner(this)
                .addItem("分类2", Arrays.asList("项目1", "项目7", "项目3", "项目4"), MenuType.PL_SIMPLE_MULTIPLE)
                .setTabClickListener(this);

        downView.setSelectListner(this)
                .addItem("分类3", Arrays.asList("项目1", "项目7", "项目3", "项目4"), MenuType.PL_SIMPLE_SINGLE)
                .setTabClickListener(this);
    }


    @Override
    public void OnClickListener(int i) {
        Log.d(TAG, "tab:" + i);
    }

    @Override
    public void onPullDownSelect(int position, List data, MenuType menuType) {
        Log.d(TAG, "position:" + position +" data："+data);
    }
}
