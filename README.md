# 高德SDK自定导航效果


使用Fragment进行导航
* 效果如下:
<img width="320px" src="http://ohsx1h37z.bkt.clouddn.com/%E9%AB%98%E5%BE%B7%E5%AF%BC%E8%88%AA%E6%95%88%E6%9E%9C%E5%9B%BE1.png"/>



### 如何使用


* 添加库到app的build.gradle
```
  dependencies {
    compile 'com.yisingle.amap.lib:AmapLibrary:0.0.6'
}
```
* 添加高德SDK库到libs

0.0.6-----------AMap3DMap_6.1.0_AMapNavi_6.0.1_AMapSearch_6.1.0_AMapLocation_3.8.0_20180330

[0.0.6对应的高德SDK下载地址](http://p6wqjwt3q.bkt.clouddn.com/0.0.6%E5%AF%B9%E5%BA%94%E9%AB%98%E5%BE%B7SDK.zip)

* app的AndroidManifest.xml application标签下添加      
 
请注意这个key是跟app包名和签名相关的。
```xml
<meta-data
            android:name="com.amap.api.v2.apikey"
            android:value="{请填写在高德开发平台上申请的key}" />
            
```







