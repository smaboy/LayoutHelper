package com.example.smaboy.layouthelper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smaboy.layouthelper.Entity.City;
import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.base.BaseActivity;
import com.example.smaboy.layouthelper.util.CityUtil;
import com.gavin.com.library.PowerfulStickyDecoration;
import com.gavin.com.library.listener.PowerGroupListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * <ur>
 * <li> 类名: StickyActivity</li>
 * <li> 类作用描述: java类作用描述 </li>
 * <li> 页面名称: 页面描述 </li>
 * <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 * <li> 创建时间: 2019/8/29 13:55</li>
 * </ur>
 */
public class StickyActivity extends BaseActivity {
    @BindView(R.id.cl_title)
    ConstraintLayout clTitle;
    @BindView(R.id.rv_recycler)
    RecyclerView rvRecycler;
    private List<City> dataList = new ArrayList<>();

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_sticky;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {


    }

    @Override
    public void setData() {

        //对recycler进行设置
        dataList.addAll(CityUtil.getRandomCityList());//模拟数据
        PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder
                .init(new PowerGroupListener() {
                    @Override
                    public View getGroupView(int position) {
                        //获取自定定义的组View
                        if (dataList.size() > position) {
                            View view = getLayoutInflater().inflate(R.layout.item_group, null, false);
                            ((TextView) view.findViewById(R.id.tv_name)).setText(dataList.get(position).getProvince());
                            return view;
                        } else {
                            return null;
                        }
                    }

                    @Override
                    public String getGroupName(int position) {
                        //获取组名，用于判断是否是同一组
                        if (dataList.size() > position) {
                            return dataList.get(position).getProvince();
                        }
                        return null;
                    }
                })
                .build();
        rvRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvRecycler.setAdapter(new CommonAdapter<City>(this,R.layout.item_list,dataList) {


            @Override
            protected void convert(ViewHolder holder, City city, int position) {
                holder.setText(R.id.tv_info,city.getName());
            }
        });

        rvRecycler.addItemDecoration(decoration);

    }


    @NotNull
    @Override
    public Class initViewModel() {

        return getClass();
    }
}
