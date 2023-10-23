package org.dxstudio.cwtsdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dxstudio.cwtsdk.request.CancelOrderParam;
import org.dxstudio.cwtsdk.request.CreateOrderParam;
import org.dxstudio.cwtsdk.request.QueryOrderParam;
import org.dxstudio.cwtsdk.response.CancelOrder;
import org.dxstudio.cwtsdk.response.CreateOrder;
import org.dxstudio.cwtsdk.response.QueryItem;
import org.dxstudio.cwtsdk.response.CWTResult;

import java.io.IOException;

public class MerchantSDK {

    private final String gateway;

    private final String key;

    private final String secret;

    private final String version = "v1";

    public MerchantSDK(String gateway, String key, String secret){

        this.gateway = gateway.endsWith("/")?gateway:gateway+"/";
        this.key = key;
        this.secret = secret;

    }

    public CWTResult<CreateOrder> createOrder(CreateOrderParam param) throws IOException {
        String result = postAPI("collect/create", getJsonObject(param) );
        return JSON.parseObject( result, new TypeReference<CWTResult<CreateOrder>>(){} );
    }

    public CWTResult<CancelOrder> cancelOrder(CancelOrderParam param) throws IOException {

        String result = postAPI("collect/cancel", getJsonObject(param) );
        return JSON.parseObject( result, new TypeReference<CWTResult<CancelOrder>>(){} );

    }

    public CWTResult<QueryItem> queryOrder( QueryOrderParam param ) throws IOException {

        String result = postAPI("collect/query", getJsonObject(param) );
        CWTResult<JSONObject> query = JSON.parseObject( result, new TypeReference<CWTResult<JSONObject>>(){} );

        CWTResult<QueryItem> r = new CWTResult<>();

        if(query.getResult().getInteger("total") > 0){

            r.setSuccess(true);
            r.setMessage("");
            r.setCode(0);
            JSONObject item = (JSONObject) query.getResult().getJSONArray("records").get(0);
            r.setResult( JSONObject.toJavaObject(item, QueryItem.class) );

        }else{
            r.setSuccess(false);
            r.setMessage("没有找到订单");
            r.setCode(1);
            r.setResult(null);
        }

        return r;

    }


    /**
     * 获取回调工具类
     */
    public CallbackUtils getCallbackUtils( String callJsonStr ){

        return new CallbackUtils( this.secret, callJsonStr );

    }

    private JSONObject getJsonObject( Object param ){

        JSONObject p = (JSONObject) JSONObject.toJSON( param );
        p.put("key", this.key);
        String sign = SignUtils.getSign( p, this.secret );
        p.put("sign",sign);
        return p;

    }

    private String getFullPath( String method ){

        return this.gateway+this.version+"/"+method;

    }

    private String postAPI( String method, JSONObject param ) throws IOException {

        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( getFullPath(method) );
        httpPost.addHeader("Content-Type","application/json;charset=UTF-8");

        StringEntity stringEntity = new StringEntity( param.toString(), "UTF-8" );
        stringEntity.setContentEncoding("UTF-8");
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = null;
        response = httpClient.execute( httpPost );

        if( response.getStatusLine().getStatusCode() == 200 ){
            HttpEntity respEntity = response.getEntity();
            result = EntityUtils.toString( respEntity, "UTF-8" );
        }

        response.close();
        httpClient.close();

        return result;

    }

}
