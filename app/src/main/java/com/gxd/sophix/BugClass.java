package com.gxd.sophix;

import android.util.Log;

/**
 * 测试bug类
 *
 * @author gaoxiaoiduo
 * @version 1.0
 * @date 18/12/21下午3:04
 */
public class BugClass
{
    public String bug ()
    {
        // 这段代码会报空指针异常
        String str = null;
        Log.e("BugClass", "get String length:" + str.length());
        // return "This is a fixed - 6 - bug class";
        return "This is a  bug class";
    }
}
