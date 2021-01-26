package com.example.smaboy.layouthelper.application;

import android.app.Application;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

/**
 * <ur>
 * <li> 类名: MyApp</li>
 * <li> 类作用描述: java类作用描述 </li>
 * <li> 页面名称: 页面描述 </li>
 * <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 * <li> 创建时间: 2019/9/3 15:19</li>
 * </ur>
 */
public class MyApp extends Application {

    private FlutterEngine flutterEngine;

    @Override
    public void onCreate() {
        super.onCreate();


        //初始化flutter引擎，用于提高flutter页面的启动速度
        initFlutterEngine();

    }

    private void initFlutterEngine() {
        // Instantiate a FlutterEngine.
        flutterEngine = new FlutterEngine(this);

        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        );

        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
                .getInstance()
                .put("my_engine_id", flutterEngine);
    }


}
