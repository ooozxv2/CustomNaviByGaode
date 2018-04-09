# 申请高德SDK的ApiKey

### 申请高德SDK的apikey 需要两个关键的东西。

- 一个是应用的包名
- 一个是应用的打包签名文件的sh1值


### 应用的包名:

在这个工程中 请注意我们拿app的包名

app的包名是:com.yisingle.study.map.one

如下图:

![image](http://p6wrbso1g.bkt.clouddn.com/%E5%9B%BE%E7%89%87%E5%8C%85%E5%90%8D.png)

### 应用的打包签名文件的sh1值:

大家可以看我的签名文件是这个

![image](http://p6wrbso1g.bkt.clouddn.com/%E7%AD%BE%E5%90%8D%E6%96%87%E4%BB%B6%E5%AD%98%E5%82%A8%E4%BD%8D%E7%BD%AE.png)

通过命令获取签名文件的SH1值命令在window下也可以用

``` 
keytool -v -list -keystore  keystore文件路径

```


![image](http://p6wrbso1g.bkt.clouddn.com/%E8%8E%B7%E5%8F%96SH1%E5%80%BC%E7%9A%84%E5%91%BD%E4%BB%A4.png)

![image](http://p6wrbso1g.bkt.clouddn.com/%E5%A4%8D%E5%88%B6SH1%E7%9A%84%E5%91%BD%E4%BB%A4.png)

然后到高德开放平台上填写应用得到apiKey 

[高德开放平台](https://note.youdao.com/)

如下图：

![image](http://p6wrbso1g.bkt.clouddn.com/%E9%AB%98%E5%BE%B7%E5%BC%80%E6%94%BE%E5%B9%B3%E5%8F%B0%E4%B8%8A%E7%94%B3%E8%AF%B7apikey.png)


点击提交

![image](http://p6wrbso1g.bkt.clouddn.com/%E5%BE%97%E5%88%B0%E9%AB%98%E5%BE%B7%E7%9A%84api%20key.png)

然后填写 

``` xml
<meta-data
    android:name="com.amap.api.v2.apikey"
    android:value="7a91a3b1ccdf395223bcf4f511d5697c" />
``` 

### 注意事项:

**一定要在app中的build.gradle中 配置打包使用生成的签名。**

在app.的build.gradle文件下 android标签下 添加

```
signingConfigs {
    signinfo {
        keyAlias 'key0'
        keyPassword '123456789'
        storeFile file('../key/studyone.jks')
        storePassword '123456789'
    }
}

```


在buildTypes标签下修改代码如下：

```
buildTypes {
    release {
        minifyEnabled false
        signingConfig signingConfigs.signinfo
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    }
    debug {
        minifyEnabled false
        signingConfig signingConfigs.signinfo
      
    }
}

```
**注意请把signingConfigs 放在 buildTypes的前面。**

app工程下的build.gradle的文件如下:
```
apply plugin: 'com.android.application'

android {
    compileSdkVersion 26
    defaultConfig {
        applicationId "com.yisingle.study.map.one"
        minSdkVersion 18
        targetSdkVersion 26
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
        ndk {
            abiFilters 'armeabi'
        }
    }

    signingConfigs {
        signinfo {
            keyAlias 'key0'
            keyPassword '123456789'
            storeFile file('../key/studyone.jks')
            storePassword '123456789'
        }
    }
    buildTypes {
        release {
            minifyEnabled false
            signingConfig signingConfigs.signinfo
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
        debug {
            minifyEnabled false
            signingConfig signingConfigs.signinfo

        }
    }
    sourceSets {
        main {
            jniLibs.srcDirs = ['libs']
        }
    }

}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:26.1.0'
    implementation 'com.android.support.constraint:constraint-layout:1.0.2'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.1'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.1'
}


```











