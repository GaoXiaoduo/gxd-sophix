package com.gxd.sophix;

import android.app.Application;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * @author gaoxiaoiduo
 * @version 1.0
 * @date 18/12/28下午6:58
 */
public class GxdApplication extends Application
{
    @Override
    public void onCreate ()
    {

        super.onCreate();
        //initHotfix();
    }

    /**
     * 快速接入方法，稳健接入方法不需要调用本初始化方法
     */
    private void initHotfix ()
    {

        String appVersion;
        try
        {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e)
        {
            appVersion = "1.0.0";
        }
        // initialize必须放在attachBaseContext最前面，初始化代码直接写在Application类里面，切勿封装到其他类。
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener()
                {
                    @Override
                    public void onLoad (
                            final int mode, final int code, final String info, final
                    int handlePatchVersion)
                    {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS)
                        {
                            // 表明补丁加载成功
                        }
                        else if (code == PatchStatus.CODE_LOAD_RELAUNCH)
                        {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                        }
                        else
                        {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                    }
                }).initialize();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        // 查询拉取补丁
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
