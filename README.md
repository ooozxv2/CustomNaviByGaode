# Android高德SDK 导航篇:自定义导航

- **[github地址](https://github.com/jikun2008/CustomNaviByGaode)**

- **扫描下面二维码下载DEMO**

<img width="200px" src="https://user-gold-cdn.xitu.io/2018/4/10/162af0981e141889?w=280&h=280&f=png&s=1925"/>


## 主要功能
- **可以使用Fragment进行导航(已经实现)**
- **多路径规划(已经实现)**
- **多路径规划和导航都是在一个Fragment中实现的**

> 多路规划和导航可以放在同一个Activity中实现



<img width="320px" src="http://p6wrbso1g.bkt.clouddn.com/%E6%95%88%E6%9E%9C%E5%9B%BE.png"/>
<img width="320px" src="https://user-gold-cdn.xitu.io/2018/4/10/162af0981ba24a51?w=1080&h=1920&f=png&s=456801"/>





### 如何使用


#### 添加库到app的build.gradle

```
  dependencies {
    compile 'com.yisingle.amap.lib:AmapLibrary:0.0.8'
}
```
#### 添加高德SDK库到libs

0.0.8-----------AMap3DMap_6.1.0_AMapNavi_6.0.1_AMapSearch_6.1.0_AMapLocation_3.8.0_20180330

高德SDK 2018年3月30 更新的版本 下载地址如下

[0.0.8对应的高德SDK下载地址](http://p6wqjwt3q.bkt.clouddn.com/0.0.6%E5%AF%B9%E5%BA%94%E9%AB%98%E5%BE%B7SDK.zip)

#### app的AndroidManifest.xml添加<meta-data>     




这个key是跟app包名和签名相关的。
请在application标签下添加
```xml
<meta-data
            android:name="com.amap.api.v2.apikey"
            android:value="{请填写在高德开发平台上申请的key}" />
            
```


[如何申请Android高德开放平台的ApiKey请点我](https://github.com/jikun2008/CustomNaviByGaode/blob/master/%E7%94%B3%E8%AF%B7%E9%AB%98%E5%BE%B7sdk%E7%9A%84ApiKey%E7%9A%84%E6%AD%A5%E9%AA%A4.md)


例子:

``` java
NaviActionData naviActionData = new NaviActionData.Builder()
        //路径规划成功后立即导航
        .setNaviRightNow(true)
        //设置模拟导航
        .setEmulatorNavi(true)
        .buildEnd(null, new NaviLatLng(30.661825, 104.071228));
NaviFragment naviFragment = NaviFragment.newInstance(naviActionData);

getSupportFragmentManager().beginTransaction()
.replace(R.id.rlNaviContent, naviFragment).commitAllowingStateLoss();
```



















```

```








