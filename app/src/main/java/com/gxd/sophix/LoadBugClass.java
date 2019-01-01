package com.gxd.sophix;

/**
 * 加载Bug的类
 *
 * @author gaoxiaoiduo
 * @version 1.0
 * @date 18/12/21下午3:08
 */
public class LoadBugClass
{
    /**
     * 获取bug字符串.
     *
     * @return 返回bug字符串
     */
    public static String getBugString ()
    {

        BugClass bugClass = new BugClass();
        return bugClass.bug();
    }
}
