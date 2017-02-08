package com.asiainfo.festivalsms.bean;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月08日16点55分 描述:
 */
public class FestivalLib {
    private static FestivalLib ourInstance = new FestivalLib();

    public static FestivalLib getInstance() {
        return ourInstance;
    }

    private FestivalLib() {
    }
}
