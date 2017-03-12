package com.asiainfo.CMCHN.net.callback;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.asiainfo.CMCHN.ConstantsAPI;
import com.asiainfo.hun.lib.net.JSONCallBack;
import com.asiainfo.hun.lib.utils.JsonUtils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类名
 *
 * @Author 温维远｛wenwy@asiainfo.com｝<br/>
 * @Date 16/9/11 下午3:22
 * 功能介绍：
 */
public class WapUIDGetCallBack extends JSONCallBack {
    public WapUIDGetCallBack(Handler handler, Context context) {
        super(handler, context);
    }

    public WapUIDGetCallBack(Handler handler, Context context, boolean isShow) {
        super(handler, context, isShow);
    }

    @Override
    public void response(int code, String msg, String data) {
        if(isSuc(code)){
            Logger.e("--请求结果--", "" + msg);
            if(!TextUtils.isEmpty(data)){
                try {
                    String wapsUid = JsonUtils.getString(new JSONObject(data),"urlParams");
                    sendSuccessMsg(ConstantsAPI.WAPS_UID_GET_SUC, wapsUid);
                } catch (JSONException e) {
                    sendFailureMsg(ConstantsAPI.WAPS_UID_GET_FAILURE, "数据解析异常");
                    e.printStackTrace();
                }

            }else {
                sendFailureMsg(ConstantsAPI.WAPS_UID_GET_FAILURE, "数据解析异常");
            }
        }else{
            sendFailureMsg(ConstantsAPI.WAPS_UID_GET_FAILURE,msg);
        }
    }
}
