package com.asiainfo.festivalsms.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月08日16点55分 描述:
 */
public class FestivalLib {

    private List<Festival> mFestivals = new ArrayList<>();

    private static FestivalLib ourInstance = new FestivalLib();

    public static FestivalLib getInstance() {
        return ourInstance;
    }

    private FestivalLib() {

        mFestivals.add(new Festival(1,"国庆节"));
        mFestivals.add(new Festival(2,"中秋节"));
        mFestivals.add(new Festival(3,"情人节"));
        mFestivals.add(new Festival(4,"元旦节"));
        mFestivals.add(new Festival(5,"中秋节"));
        mFestivals.add(new Festival(6,"端午节"));
        mFestivals.add(new Festival(7,"儿童节"));
        mFestivals.add(new Festival(8,"圣诞节"));

    }

    public  List<Festival> getFestivals(){

        return new ArrayList<Festival>(mFestivals);

    }

    public  Festival getFestivalById(int fesId){

        for (Festival festival : mFestivals) {

            if (festival.getId()==fesId){
                return festival;
            }
        }

        return null;

    }
}
