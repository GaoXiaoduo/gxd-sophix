package com.gxd.sophix;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication
{
    private final String TAG = "SophixStubApplication";

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(GxdApplication.class)
    static class RealApplicationStub
    {
    }

    @Override
    protected void attachBaseContext (Context base)
    {

        super.attachBaseContext(base);
        // 如果需要使用MultiDex，需要在此处调用。
        // MultiDex.install(this);
        initSophix();
    }

    private void initSophix ()
    {

        String appVersion = "0.0.0";
        try
        {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e)
        {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                // 分别对应AndroidManifest里面的三个
                .setSecretMetaData(null, null, null)
                // 线下调试此参数可以设置为true, 查看日志过滤TAG:Sophix, 同时强制不对补丁进行签名校验,
                // 所有就算补丁未签名或者签名失败也发现可以加载成功.
                // 但是正式发布该参数必须为false, false会对补丁做签名校验, 否则就可能存在安全漏洞风险
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener()
                {
                    @Override
                    public void onLoad (
                            final int mode, final int code, final String info, final
                    int handlePatchVersion)
                    {

                        if (code == PatchStatus.CODE_LOAD_SUCCESS)
                        {
                            Log.i(TAG, "sophix load patch success!");
                        }
                        else if (code == PatchStatus.CODE_LOAD_RELAUNCH)
                        {
                            // 调用此方法杀死进程
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }
}