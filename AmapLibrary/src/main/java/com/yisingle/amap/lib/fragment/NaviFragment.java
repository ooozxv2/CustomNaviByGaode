package com.yisingle.amap.lib.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.TextureMapView;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.NextTurnTipView;
import com.amap.api.navi.view.ZoomInIntersectionView;
import com.yisingle.amap.lib.GaoDeErrorUtils;
import com.yisingle.amap.lib.R;
import com.yisingle.amap.lib.adapter.RecyclerAdapter;
import com.yisingle.amap.lib.adapter.RecyclerSpace;
import com.yisingle.amap.lib.adapter.RecyclerViewHolder;
import com.yisingle.amap.lib.base.navi.BaseNaviFragment;
import com.yisingle.amap.lib.data.NaviActionData;
import com.yisingle.amap.lib.data.StrategyType;
import com.yisingle.amap.lib.location.AMapLocationHelper;
import com.yisingle.amap.lib.utils.ContextUtils;
import com.yisingle.amap.lib.utils.DistanceUtils;
import com.yisingle.amap.lib.utils.DpSpPxUtils;
import com.yisingle.amap.lib.utils.StrategyUtils;
import com.yisingle.amap.lib.utils.TimeUtils;
import com.yisingle.amap.lib.widget.LoadingView;
import com.yisingle.amap.lib.widget.MultipleRouteView;
import com.yisingle.amap.lib.widget.NaviStrategyPopouWindow;
import com.yisingle.amap.lib.widget.SimpleRouteView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/3/29.
 */

public class NaviFragment extends BaseNaviFragment {

    public static final String BUNDLE_KEY = "ATION_DATA";

    private NaviActionData currentNaviActionData;

    boolean isDisplayOverview = false;


    /**
     * 是否重新绘制路线  重新走路径规划的流程。onCalculateRouteSuccess 或 onCalculateRouteFailure
     * 这种情况有三种
     * 1.切换主路或辅路的重新计算路线  主动的 我们可以调用 AMapNavi.switchParallelRoad(); 主路辅路UI显示回调 notifyParallelRoad
     * 2.非常拥堵重新计算路线(这种情况很少见) 被动的 这个是由高德SDK内部实现的  当准备开始拥堵重新计算路线的回调方法： onReCalculateRouteForTrafficJam
     * 3.偏航的重新计算路线 被动的  这个是由高德SDK内部实现的 当准备开始偏航重新计算路线的回调方法：onReCalculateRouteForYaw
     */
    private boolean isReCalculateRoute = false;


    /**
     * ----------------------------------------------地图相关控件 START-----------------------------
     * 包含MapView和其他控件容器
     */
    private RelativeLayout rlMapViewContent;

    /**
     * 高德地图控件 TextureMapView
     */
    private TextureMapView textureMapView;

    /**
     * 列表控件 RecyclerView
     */
    private RecyclerView recyclerView;


    /**
     * 开始导航按钮
     */
    private Button btBeginNavi;


    /**
     * 策略选择按钮
     */
    private Button btStrategyChoose;


    private NaviStrategyPopouWindow strategyPopouWindow;


    private RecyclerAdapter<SimpleRouteView> adapter;

    /**
     * ----------------------------------------------地图相关控件 END-----------------------------
     */


    /**
     * ----------------------------------------------导航相关控件 START-----------------------------
     * 包含AMapNaviView和导航所需要的一些控件的容器。
     */
    private RelativeLayout rlNavViewContent;

    /**
     * 导航控件
     */
    private AMapNaviView naviView;


    private RelativeLayout rlAllTitleNaviInfo;


    /**
     * ----------------------------------------------模型图不需要显示的导航信息UI效果 START-----------------------------
     * 包含导航信息控件的容器(没有模型显示的情况下大多数情况都是这样效果)
     */
    private RelativeLayout rlBigTitleontent;

    /**
     * 显示距离下一个路口距离的控件
     */
    private TextView tvBigDistance;

    /**
     * 显示下一个路口道路名称的控件
     */
    private TextView tvBigRoadName;

    /**
     * 显示下一个路口转向的控件
     */
    private NextTurnTipView nvBigTurnView;

    /**
     * 显示剩余距离的控件
     */
    private TextView tvBigRemainDistance;

    /**
     * 显示剩余时间的控件
     */
    private TextView tvBigRemainTime;


    /**
     * 显示到达时间的控件
     */
    private TextView tvBigArriveTime;
    /**
     * ----------------------------------------------模型图不需要显示的导航信息UI效果 END-----------------------------
     */


    /**
     * ----------------------------------------------模型图需要显示的导航信息UI效果 START-----------------------------
     * 包含导航信息控件的容器(模型图需要展示时的效果)
     */
    private LinearLayout llLittleTitleContent;

    /**
     * 显示距离下一个路口距离的控件
     */
    private TextView tvLittleDistance;

    /**
     * 显示下一个路口道路名称的控件
     */
    private TextView tvLittleRoadName;

    /**
     * 显示下一个路口转向的控件
     */
    private NextTurnTipView nvLittleTurnView;

    /**
     * 实景图显示效果
     */
    private ZoomInIntersectionView zmLittleInIntersectionView;

    /**
     * ----------------------------------------------模型图需要显示的导航信息UI效果 END-----------------------------
     *
     */

    /**
     * 主路辅路切换按钮
     */
    private Button tvSwichRoad;

    /**
     * 全览按钮
     */
    private Button overviewButtonView;

    /**
     * 定位点图片
     */
    private Button btn_Location;


    /**
     * 加载控件 自己实现的
     */
    private LoadingView loadingView;

    /**
     * ----------------------------------------------导航相关控件 END-----------------------------
     */


    @Override
    protected void afterNaviViewLoad() {
        //导航NaviView加载完成后

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ContextUtils.init(getContext());
        return inflater.inflate(R.layout.fragment_navi, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //地图View ID加载
        rlMapViewContent = view.findViewById(R.id.rlMapViewContent);
        textureMapView = view.findViewById(R.id.textureMapView);
        recyclerView = view.findViewById(R.id.recyclerView);
        btBeginNavi = view.findViewById(R.id.btBeginNavi);
        btBeginNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在导航地图上绘制路线
                rlMapViewContent.setVisibility(View.GONE);
                rlNavViewContent.setVisibility(View.VISIBLE);
                drawRouteViewOnNaviView(multipleRouteView.getSelectRouteId());
                //开始导航
                startNavi();
            }
        });

        btStrategyChoose = view.findViewById(R.id.btStrategyChoose);
        btStrategyChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != getActivity() && null != getActivity().getWindow()) {
                    //将屏幕变暗
                    strategyPopouWindow.changeDarken(getActivity().getWindow(), true);
                }

                strategyPopouWindow.showPopuWindows(recyclerView, currentNaviActionData.getStrategy());


            }
        });


        //导航界面的 控件 ID加载
        rlNavViewContent = view.findViewById(R.id.rlNavViewContent);
        //地图控件
        naviView = view.findViewById(R.id.naviView);
        //
        rlAllTitleNaviInfo = view.findViewById(R.id.rlAllTitleNaviInfo);
        //没有显示模型图的导航信息界面UI 大多数情况都是这个界面效果
        rlBigTitleontent = view.findViewById(R.id.rlBigTitleontent);
        tvBigDistance = view.findViewById(R.id.tvBigDistance);
        tvBigRoadName = view.findViewById(R.id.tvBigRoadName);
        nvBigTurnView = view.findViewById(R.id.nvBigTurnView);
        tvBigRemainDistance = view.findViewById(R.id.tvBigRemainDistance);
        tvBigRemainTime = view.findViewById(R.id.tvBigRemainTime);
        tvBigArriveTime = view.findViewById(R.id.tvBigArriveTime);

        //显示了模型图的导航信息界面UI
        llLittleTitleContent = view.findViewById(R.id.llLittleTitleContent);
        tvLittleDistance = view.findViewById(R.id.tvLittleDistance);
        tvLittleRoadName = view.findViewById(R.id.tvLittleRoadName);
        zmLittleInIntersectionView = view.findViewById(R.id.zmLittleInIntersectionView);
        nvLittleTurnView = view.findViewById(R.id.nvLittleTurnView);
        tvSwichRoad = view.findViewById(R.id.tvSwichRoad);
        tvSwichRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchParallelRoad();
            }
        });

        overviewButtonView = view.findViewById(R.id.overviewButtonView);
        overviewButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDisPlayViewOrRecoverLockMode(!isDisplayOverview);
            }
        });
        btn_Location = view.findViewById(R.id.btn_Location);
        btn_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //锁车模式
                showDisPlayViewOrRecoverLockMode(false);

            }
        });
        loadingView = view.findViewById(R.id.loadingView);
        loadingView.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction(currentNaviActionData);
            }
        });

        initStrategyPopouWindow();
        initRecyclerView();
        //初始化AMapNavi导航对象
        initAMapNavi();
        //初始化NaviView导航控件
        initNaviView(savedInstanceState, naviView);
        //初始化MapView地图控件
        initMapView(savedInstanceState, textureMapView);
        //初始化转向控件的图片参数
        initTurnView(nvBigTurnView, nvLittleTurnView);

        //从Bundle获取数据
        //获取NaviActionData来进行一些操作
        Bundle bundle = getArguments();
        if (null != bundle && null != bundle.getParcelable(BUNDLE_KEY)) {
            NaviActionData data = bundle.getParcelable(BUNDLE_KEY);
            doAction(data);

        }

        multipleRouteView.setOnItemClickListener(new MultipleRouteView.OnItemClickListener() {
            @Override
            public void onItemClick(int index) {
                multipleRouteView.selectRouteOverLayByIndex(index);
                adapter.refreshWithNewData(multipleRouteView.getSimpleRouteViewList());
            }
        });
    }


    public static NaviFragment newInstance(NaviActionData data) {
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_KEY, data);
        NaviFragment fragment = new NaviFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void doAction(NaviActionData data) {
        currentNaviActionData = data;
        rlMapViewContent.setVisibility(currentNaviActionData.isNaviRightNow() ? View.GONE : View.VISIBLE);
        rlNavViewContent.setVisibility(currentNaviActionData.isNaviRightNow() ? View.VISIBLE : View.GONE);
        btBeginNavi.setVisibility(View.GONE);
        btStrategyChoose.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        cleanMultipleRouteViewOnMapView();

        if (null != currentNaviActionData) {
            switch (currentNaviActionData.getType()) {
                case NaviActionData.Type.DoNoThing:
                default:
                    break;

                case NaviActionData.Type.NaviToStart:
                    naviPathPlanUi(currentNaviActionData.getCarLatlng(),
                            currentNaviActionData.getStartLatlng(), currentNaviActionData.getStrategy());

                    break;
                case NaviActionData.Type.NaviToEnd:
                    naviPathPlanUi(currentNaviActionData.getCarLatlng(),
                            currentNaviActionData.getEndLatlng(), currentNaviActionData.getStrategy());

                    break;
            }
        }

    }

    private void initStrategyPopouWindow() {
        strategyPopouWindow = new NaviStrategyPopouWindow(getContext());
        strategyPopouWindow.setListener(new NaviStrategyPopouWindow.OnChooseStrategyListener() {
            @Override
            public void onChooseStrategy(int nowConfimChossStrategy) {
                //将屏幕变亮
                if (null != getActivity() && null != getActivity().getWindow()) {
                    strategyPopouWindow.changeDarken(getActivity().getWindow(), false);
                }

                //只有策略改变的时候才重新进行路径规划
                if (nowConfimChossStrategy != currentNaviActionData.getStrategy()) {
                    //设置策略
                    currentNaviActionData.setStrategy(nowConfimChossStrategy);
                    doAction(currentNaviActionData);

                    btStrategyChoose.setText(StrategyUtils.getStrategyName(nowConfimChossStrategy));


                } else {
                    //donoThing
                }
            }
        });
    }

    private void initRecyclerView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3) {
            @Override
            public boolean canScrollVertically() {
                //设置RecyclerView不能垂直滚动
                return false;
            }
        };
        RecyclerSpace recyclerSpace = new RecyclerSpace(DpSpPxUtils.dip2px(1), getResources().getColor(R.color.navi_color_grey));
        recyclerView.addItemDecoration(recyclerSpace);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new RecyclerAdapter<SimpleRouteView>(null, R.layout.adapter_multi_path) {
            @Override
            protected void onBindData(RecyclerViewHolder holder, int position, SimpleRouteView item) {
                AMapNaviPath path = item.getMapNaviPath();
                if (null != path) {
                    holder.setText(R.id.tvTag, item.getMapNaviPath().getLabels());
                    holder.setSelected(R.id.tvTag, item.isSelect());

                    holder.setText(R.id.tvTime, TimeUtils.secToTime(path.getAllTime()));
                    holder.setSelected(R.id.tvTime, item.isSelect());


                    holder.setText(R.id.tvDistance, DistanceUtils.getDistance(path.getAllLength()));
                    holder.setSelected(R.id.tvDistance, item.isSelect());
                }


            }
        };
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                multipleRouteView.selectRouteOverLayByIndex(position);
                adapter.refreshWithNewData(multipleRouteView.getSimpleRouteViewList());


            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void showLoading(String info) {
        loadingView.showLoading(info);

    }

    @Override
    public void showSuccess() {
        loadingView.showSuccess();

    }

    @Override
    public void showError(String info) {
        loadingView.showErrorInfo(info);

    }


    /**
     * 进行导航路径规划和路径规划的UI显示
     *
     * @param from     起点
     * @param to       终点
     * @param strategy 策略
     */
    private void naviPathPlanUi(@Nullable final NaviLatLng from, @NonNull final NaviLatLng to, @StrategyType final int strategy) {
        showLoading("导航规划路径中");
        //设置不是导航重新绘制
        isReCalculateRoute = false;
        if (null != from) {
            boolean isCanCallBack = calculateDriveRoute(from, to, strategy);
            if (!isCanCallBack) {
                //显示失败效果
                showError("calculateDriveRoute方法未执行");
            }

        } else {
            AMapLocationHelper.startSingleLocate(getContext(), new AMapLocationHelper.OnLocationGetListener() {
                @Override
                public void onLocationGetSuccess(AMapLocation loc) {
                    NaviLatLng latLng = new NaviLatLng(loc.getLatitude(), loc.getLongitude());
                    boolean isCanCallBack = calculateDriveRoute(latLng, to, strategy);
                    if (!isCanCallBack) {
                        //显示失败效果
                        showError("calculateDriveRoute方法未执行");
                    }
                }

                @Override
                public void onLocationGetFail(AMapLocation loc) {
                    showError("获取定位失败:" + loc.getErrorInfo());
                }
            });

        }

    }


    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        if (isReCalculateRoute) {
            //如果是重新路径那么就直接在NaviView上画路线
            showSuccess();
            if (ints.length > 0) {
                //在导航地图上绘制路线
                drawRouteViewOnNaviView(ints[0]);
                //开始导航
                startNavi();
            }
            isReCalculateRoute = false;

        } else {
            showSuccess();
            //如果是立即导航 根据currentNaviActionData.isNaviRightNow()
            // 判断是在MapView上画路线 还是在NaviView上画路线
            if (currentNaviActionData.isNaviRightNow()) {
                if (ints.length > 0) {
                    //在导航地图上绘制路线
                    drawRouteViewOnNaviView(ints[0]);
                    //开始导航
                    startNavi();
                }
            } else {
                //如果不是立即导航  currentNaviActionData.isNaviRightNow()==false
                drawMultipleRouteViewOnMapView(ints);
                adapter.refreshWithNewData(multipleRouteView.getSimpleRouteViewList());
                recyclerView.setVisibility(View.VISIBLE);
                btBeginNavi.setVisibility(View.VISIBLE);
                btStrategyChoose.setVisibility(View.VISIBLE);
                if (ints.length > 0 && null != mAMapNavi) {
                    AMapNaviPath path = mAMapNavi.getNaviPaths().get(ints[0]);

                    moveCameraMapView(path.getStartPoint(), path.getEndPoint());
                }
            }
        }

    }

    @Override
    public void onCalculateRouteFailure(int code) {
        String errorInfo = GaoDeErrorUtils.getErrorInfo(code);
        if (isReCalculateRoute) {
            showError("偏航或主辅路或拥堵路径规划失败:" + errorInfo);
            isReCalculateRoute = false;
        } else {
            showError("路径规划失败:" + errorInfo);
        }
    }

    @Override
    public void onReCalculateRouteForYaw() {
        //偏航后准备重新规划的通知回调。
        isReCalculateRoute = true;
        showLoading("偏航导航重新路径规划中");


    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //驾车导航时，如果前方遇到拥堵时需要重新计算路径的回调。这种情况很少见。但是还是要实现
        isReCalculateRoute = true;
        showLoading("拥堵导航重新路径规划中");
    }

    /**
     * 切换主路辅路
     */
    public void switchParallelRoad() {
        if (null != mAMapNavi) {
            isReCalculateRoute = true;
            showLoading("主路辅路切换导航路径规划中");
            mAMapNavi.stopNavi();
            mAMapNavi.switchParallelRoad();

        }
    }


    @Override
    public void notifyParallelRoad(int parallelRoadType) {
        // parallelRoadType - 0表示隐藏 1 表示显示主路 2 表示显示辅路
        //parallelRoadType - 0表示隐藏 1 表示导航在主路 2 表示导航在辅路
        switch (parallelRoadType) {
            case 0:
            default:
                //隐藏切换主路辅路按钮
                tvSwichRoad.setVisibility(View.GONE);
                break;
            case 1:
                //导航在主路,UI显示为在辅路。
                tvSwichRoad.setSelected(false);
                break;
            case 2:
                //导航在辅路,UI显示为在主路。
                tvSwichRoad.setSelected(true);
                break;
        }

    }


    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        if (null != naviInfo) {
            String distance = DistanceUtils.getDistance(naviInfo.getCurStepRetainDistance());
            String roadName = naviInfo.getNextRoadName();
            String allditance = "剩余约" + DistanceUtils.getDistance(naviInfo.getPathRetainDistance());
            String allTime = "预计" + TimeUtils.secToTime(naviInfo.getPathRetainTime());
            long nowTime = System.currentTimeMillis() + naviInfo.getPathRetainTime() * 1000;
            String arriverTime = "预计" + TimeUtils.millis2String(nowTime) + "到达";


            tvBigDistance.setText(distance);
            tvBigRoadName.setText(roadName);
            nvBigTurnView.setIconType(naviInfo.getIconType());
            tvBigRemainDistance.setText(allditance);
            tvBigRemainTime.setText(allTime);
            tvBigArriveTime.setText(arriverTime);


            tvLittleDistance.setText(distance);
            tvLittleRoadName.setText(roadName);
            nvLittleTurnView.setIconType(naviInfo.getIconType());


            //画导航路线上的箭头
            drawArrow(naviInfo);

        }
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        Log.e("ShowCross", "test-showCross-----");
        //显示路口放大图回调(实景图)。
        showModeLittleNaviInfo(true);
        //展示实景图
        zmLittleInIntersectionView.setImageBitmap(aMapNaviCross.getBitmap());


    }

    @Override
    public void hideCross() {
        Log.e("hideCross", "test-hideCross-----");
        //关闭路口放大图回调(实景图)。
        showModeLittleNaviInfo(false);
    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {
        Log.e("showModeCross", "test-showModeCross-----");
        //显示路口放大图回调(模型图)。 模型图需要在
        showModeLittleNaviInfo(true);
        // zmLittleInIntersectionView.setImageResource(android.R.color.transparent);

    }

    @Override
    public void hideModeCross() {
        Log.e("hideModeCross", "test-hideModeCross-----");
        //关闭路口放大图回调(模型图)。
        showModeLittleNaviInfo(false);

    }

    /**
     * 开始导航 在路径规划之后 也就是onCalculateRouteSuccess成功之后才可以调用这个方法
     */
    public void startNavi() {
        if (null != mAMapNavi) {
            mAMapNavi.stopNavi();
            mAMapNavi.setEmulatorNaviSpeed(90);
            if (currentNaviActionData.isEmulatorNavi()) {
                mAMapNavi.startNavi(NaviType.EMULATOR);
            } else {
                mAMapNavi.startNavi(NaviType.GPS);
            }

        }

    }

    /**
     * 是否需要展示实景图或者模型图
     *
     * @param isShowMode true展示
     */
    private void showModeLittleNaviInfo(boolean isShowMode) {
        rlBigTitleontent.setVisibility(isShowMode ? View.GONE : View.VISIBLE);
        llLittleTitleContent.setVisibility(isShowMode ? View.VISIBLE : View.GONE);

    }


    /**
     * 计算驾车路径
     * 说明：返回true，只表示路径计算方法执行，但是否返回规划的路线，请参见AMapNaviListener的回调。
     *
     * @param from 导航起点坐标
     * @param to   导航终点坐标
     */
    private boolean calculateDriveRoute(@NonNull NaviLatLng from, @NonNull NaviLatLng to, @StrategyType int strategy) {
        List<NaviLatLng> fromList = new ArrayList<>();
        List<NaviLatLng> toList = new ArrayList<>();
        mAMapNavi.stopNavi();
        fromList.add(from);
        toList.add(to);
        /*
         * 再次强调，最后一个参数为true时代表多路径，否则代表单路径
         * congestion：躲避拥堵 true fasle
         * avoidhightspeed：不走高速 true fasle
         * cost：避免收费 true fasle
         * hightspeed：高速优先 true fasle
         * multiple：多路径 true fasle
         * 不走高速与高速优先不能同时为true。  高速优先与避免收费不能同时为true。
         * --strategy = AMapNavi--strategyConvert(congestion, avoidhightspeed, cost, hightspeed, isMultipath);
         * --AMapNavi--strategyConvert(false, false, false, false, isMultipath);
         * --int strategy = PathPlanningStrategy.DRIVING_SHORTEST_DISTANCE;//重新写参数策略 不使用高德自己的策略
         */

        // 算路是否成功，true-成功；false-失败。
        // 说明：返回true，只表示路径计算方法执行，但是否返回规划的路线，请参见AMapNaviListener的回调。
        return mAMapNavi.calculateDriveRoute(fromList, toList, null, strategy);
    }


    /**
     * 显示全览或者锁车模式
     *
     * @param isdiplay true全览 false锁车
     */
    private void showDisPlayViewOrRecoverLockMode(boolean isdiplay) {
        isDisplayOverview = isdiplay;
        if (null != getNaviView() && null != mAMapNavi) {
            overviewButtonView.setSelected(isdiplay);
            if (isdiplay) {
                //这个地方有BUG暂时找不到解决的问题 BUG全览路线会被导航信息的UI遮盖
                getNaviView().displayOverview();
//                if (null != naviRouteView && null != naviRouteView.getRouteOverLay() && null != naviRouteView.getRouteOverLay().getAMapNaviPath()) {
//
//                    AMapNaviPath path = naviRouteView.getRouteOverLay().getAMapNaviPath();
//                    moveCameraOnNaviView(path.getStartPoint(), path.getEndPoint(), rlAllTitleNaviInfo.getHeight());
//                }
            } else {

                getNaviView().recoverLockMode();
                mAMapNavi.resumeNavi();
            }

        }
    }
}
