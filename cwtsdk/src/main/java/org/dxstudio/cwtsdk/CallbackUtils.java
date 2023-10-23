package org.dxstudio.cwtsdk;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

public class CallbackUtils {

    private final String secret;

    @Getter
    private final JSONObject params;

    public CallbackUtils( String secret, String callParamStr ){

        this.secret = secret;
        this.params = JSONObject.parseObject( callParamStr );

    }

    /**
     * 验证签名
     */
    public boolean verifySign(){

        String sign = params.getString("sign");
        JSONObject p = (JSONObject) this.params.clone();
        p.remove("sign");
        String verify = SignUtils.getSign( p, secret );
        return sign.equals(verify);

    }

    public int getType(){
        return params.getInteger("type");
    }

    public String getOrderId(){
        return params.getString("serial");
    }

    public String getLocalOrderId(){
        return params.getString("localOrderId");
    }

    public String success(){
        return "success";
    }

}
