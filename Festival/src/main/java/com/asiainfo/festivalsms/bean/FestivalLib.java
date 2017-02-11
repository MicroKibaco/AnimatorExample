package com.asiainfo.festivalsms.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月08日16点55分 描述:模拟数据源
 */
public class FestivalLib {

    private List<Festival> mFestivals = new ArrayList<>();

    private static FestivalLib ourInstance = new FestivalLib();

    private List<MsgBean> mMsgList = new ArrayList<>();

    public static FestivalLib getInstance() {
        return ourInstance;
    }

    private FestivalLib() {

        mFestivals.add(new Festival(1, "国庆节"));
        mFestivals.add(new Festival(2, "中秋节"));
        mFestivals.add(new Festival(3, "情人节"));
        mFestivals.add(new Festival(4, "元旦节"));
        mFestivals.add(new Festival(5, "中秋节"));
        mFestivals.add(new Festival(6, "端午节"));
        mFestivals.add(new Festival(7, "儿童节"));
        mFestivals.add(new Festival(8, "圣诞节"));

        mMsgList.add(new MsgBean(1, 1, "2017新年送福：清晨早起精神抖，晚上早睡做美梦，饮食合理防节症，健康为主享万年，春节聚会酒肉多，祝福关心早送到，祝君节制体强健，来年还要为君送。祝：开心健康！快乐平安！如意吉祥！"));
        mMsgList.add(new MsgBean(2, 1, "恰逢新年春节忙，送走猴年迎鸡年，吉祥话要趁早，祝福语提前念。祝愿你新年新气象，快乐依然，幸福绵绵，平安一生，健康到永远！"));
        mMsgList.add(new MsgBean(3, 1, "春节啦！有伙人到处打听你，还说逮住你决不轻饶，他们一个叫财神，一个叫顺心，领头的叫幸福，你就别躲了认命吧！2017春节快乐！"));
        mMsgList.add(new MsgBean(4, 1, "团团圆圆新年到，欢欢喜喜迎新年，平平安安身体健，开开心心好运来，顺顺利利万事顺，幸幸福福吉祥到，快快乐乐过大年，舒舒服服过春节，祝君鸡年万事顺意，平安幸福！"));
        mMsgList.add(new MsgBean(5, 1, "敲锣打鼓新年到，快乐雪花满天飞，福星照耀祝福来，真诚问候对你笑，愿你事业红火钞票多，一路唱响健康歌，好运旺旺幸福至，快乐吉祥又安康！鸡年万事顺意，平安幸福！"));
        mMsgList.add(new MsgBean(6, 1, "在新的一年里，祝您：事业正当午，身体壮如虎，金钱不胜数，干活不辛苦，悠闲像老鼠，浪漫似乐谱，快乐莫你属。鸡年新春快乐！"));
        mMsgList.add(new MsgBean(7, 1, "抢在新春前头，抢在爆竹前头，抢在欢腾的前头，抢在陶醉的前头，抢在将要到来的铺天盖地的短信前头，为的是您能听清我真挚的祝福！祝鸡年春节快乐！"));
        mMsgList.add(new MsgBean(8, 1, "春风洋溢着你，家人关心你，爱滋润着你，财神系着你，朋友忠于你，我这祝福你，幸运之星永远照着你。"));
        mMsgList.add(new MsgBean(9, 1, "春风洋溢着你，家人关心你，爱滋润着你，财神系着你，朋友忠于你，我这祝福你，幸运之星永远照着你。"));

    }

    public List<Festival> getFestivals() {

        return new ArrayList<Festival>(mFestivals);

    }

    public List<MsgBean> getMsgsByFestivalById(int fesId) {

        List<MsgBean> msgs = new ArrayList<>();

        for (MsgBean msg : mMsgList) {

            if (msg.getFestivalId() == fesId) {

                msgs.add(msg);
            }

       }

        return msgs;
    }

    public MsgBean getMsgBeanById(int id){

        for (MsgBean msgBean : mMsgList) {

            if (msgBean.getId() == id){

                return msgBean;

            }
        }
        return null;
    }

    public Festival getFestivalById(int fesId) {

        for (Festival festival : mFestivals) {

            if (festival.getId() == fesId) {
                return festival;
            }
        }

        return null;

    }
}
