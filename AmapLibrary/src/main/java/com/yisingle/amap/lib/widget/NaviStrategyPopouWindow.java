package com.yisingle.amap.lib.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yisingle.amap.lib.R;
import com.yisingle.amap.lib.adapter.RecyclerAdapter;
import com.yisingle.amap.lib.adapter.RecyclerSpace;
import com.yisingle.amap.lib.adapter.RecyclerViewHolder;
import com.yisingle.amap.lib.data.StrategyData;
import com.yisingle.amap.lib.data.StrategyType;
import com.yisingle.amap.lib.utils.DpSpPxUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/3/6.
 */

public class NaviStrategyPopouWindow extends PopupWindow {


    private View parentView;
    private RecyclerView recyclerView;

    private List<StrategyData> strategyList;

    protected RecyclerAdapter<StrategyData> adapter;

    private Context context;


    private OnChooseStrategyListener listener;


    public List<StrategyData> createDataList() {
        List<StrategyData> list = new ArrayList<>();
        //"智能推荐"
        list.add(new StrategyData(StrategyType.DRIVING_MULTIPLE_ROUTES_DEFAULT));
        //"躲避拥堵"
        list.add(new StrategyData(StrategyType.DRIVING_MULTIPLE_ROUTES_AVOID_CONGESTION));
        //"不走高速"
        list.add(new StrategyData(StrategyType.DRIVING_MULTIPLE_ROUTES_AVOID_HIGHSPEED));
        //"高速优先"
        list.add(new StrategyData(StrategyType.DRIVING_MULTIPLE_ROUTES_PRIORITY_HIGHSPEED));
        return list;
    }


    public NaviStrategyPopouWindow(Context context) {
        super(context);
        this.context = context;
        this.listener = null;
        initView(context);
        setPopWindowConfig();
        initRecyclerView(context);
    }



    public NaviStrategyPopouWindow(Context context, OnChooseStrategyListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        initView(context);
        setPopWindowConfig();
        initRecyclerView(context);
    }


    private void initView(Context context) {

        parentView = LayoutInflater.from(context).inflate(R.layout.popup_window_navi_strategy, null);

        setContentView(parentView);

        recyclerView = parentView.findViewById(R.id.recyclerView);


    }

    private void initRecyclerView(Context context) {


        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4) {
            @Override
            public boolean canScrollVertically() {
                //设置RecyclerView不能垂直滚动
                return false;
            }
        };
        recyclerView.setLayoutManager(gridLayoutManager);
        RecyclerSpace recyclerSpace = new RecyclerSpace(DpSpPxUtils.dip2px(1), context.getResources().getColor(R.color.navi_color_grey));
        recyclerView.addItemDecoration(recyclerSpace);
        strategyList = new ArrayList<>();
        strategyList = createDataList();

        adapter = new RecyclerAdapter<StrategyData>(strategyList, R.layout.adapter_navi_strategy_choose) {
            @Override
            protected void onBindData(RecyclerViewHolder holder, int position, StrategyData item) {
                TextView tvStrategyInfo = holder.get(R.id.tvStrategyInfo);
                tvStrategyInfo.setText(item.getStrateName());
                tvStrategyInfo.setSelected(item.isSelected());

                holder.setBackgroundResource(R.id.iv_icon, item.getDrawableId());
                holder.setSelected(R.id.iv_icon, item.isSelected());
            }
        };


        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                StrategyData data = strategyList.get(position);
                selectedStrategy(data.getStrate());

            }
        });

        recyclerView.setAdapter(adapter);

    }


    private void selectedStrategy(@StrategyType int strategyType) {
        for (StrategyData data : strategyList) {
            if (data.getStrate() == strategyType) {
                data.setSelected(true);
            } else {
                data.setSelected(false);
            }
        }
        adapter.notifyDataSetChanged();

    }

    private void setPopWindowConfig() {

        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);
        //设置白色
        ColorDrawable dw = new ColorDrawable(0x00ffffff);
        this.setBackgroundDrawable(dw);
        // 设置外部触摸会关闭窗口
        this.setOutsideTouchable(true);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                for (StrategyData data : strategyList) {
                    if (data.isSelected()) {
                        if (null != listener) {
                            listener.onChooseStrategy(data.getStrate());
                        }
                        break;
                    }
                }
            }
        });


    }

    /**
     * 设置显示在v上方(以v的左边距为开始位置)
     *
     * @param v
     */
    public void showPopuWindows(View v, @StrategyType int chooseStrategyType) {
        //获取需要在其上方显示的控件的位置信息
        selectedStrategy(chooseStrategyType);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        setAnimationStyle(R.style.popupwindow_left_to_right_anim_style);


        int offset = DpSpPxUtils.dip2px(86);
        showAtLocation(v, Gravity.LEFT | Gravity.TOP, location[0], location[1] - offset);
    }


    /**
     * 设置屏幕变暗变亮
     *
     * @param window         window
     * @param isChangeDarken isChangeDarken
     */
    public void changeDarken(Window window, boolean isChangeDarken) {
        if (null != window) {
            WindowManager.LayoutParams lp = window.getAttributes();
            if (isChangeDarken) {
                lp.alpha = 0.4F;

                window.setAttributes(lp);
            } else {
                lp.alpha = 1F;
                window.setAttributes(lp);
            }
        }
    }

    public void setListener(OnChooseStrategyListener listener) {
        this.listener = listener;
    }

    public interface OnChooseStrategyListener {
        void onChooseStrategy(@StrategyType int nowConfimChossStrategy);
    }

}
