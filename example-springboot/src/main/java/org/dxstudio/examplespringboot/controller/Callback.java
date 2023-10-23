package org.dxstudio.examplespringboot.controller;

import com.alibaba.fastjson.JSONObject;
import org.dxstudio.cwtsdk.CallbackUtils;
import org.dxstudio.cwtsdk.MerchantSDK;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Callback {

    @Value("${cwtSdk.gateway}")
    String gateway;

    @Value("${cwtSdk.key}")
    String key;

    @Value("${cwtSdk.secret}")
    String secret;


    @RequestMapping("callback")
    public String Response(@RequestBody String param){

        MerchantSDK sdk = new MerchantSDK(gateway, key, secret);
        //这里传入您收到的 json 字符串
        CallbackUtils callbackUtils = sdk.getCallbackUtils( param );

        //验证签名
        if( !callbackUtils.verifySign() ) return "验证签名失败";

        //获取回调类型，1-7，具体见文档
        //当创建订单时，不设置allCall\needConfirm，您将只会收到4、5、6、7，，一般情况下，响应6、7即可，分别对应订单取消、订单完成。这是最简单的接入。
        //如果需要同步我们的订单取消、订单完成后被申诉扣减，则可响应4、5、6。
        //当创建订单时，设置needConfirm=1，则会多收到1回调，您还需要调用确认接口以确认收款
        //当创建订单时，设置allCall=1，则会多收到2回调。点付系统内，订单可能会被拆分，2回调可收到子订单完成通知
        int type = callbackUtils.getType();


        //下面使用最简单的接入，响应6、7----------------------------

        //可以获取通用参数
        String orderId = callbackUtils.getOrderId(); //获取点付的订单号
        String localOrderId = callbackUtils.getLocalOrderId(); //获取您本地的订单号

        //如果您还需要使用其他参数,
        JSONObject params = callbackUtils.getParams(); //一般情况下不需要使用其他参数，如果您有特殊需求则可使用，具体见文档

        if( type == 7 ){ //订单完成
            //使用 localOrderId 同步更改您的订单状态为完成
            //---------您的其他业务代码------------

            //如果您更改自己的订单状态成功，给我们响应 成功
            return callbackUtils.success();
            //如果您的业务失败，不用担心，return xxxx 返回任意内容即可。如果您希望我们能为您提供调试信息，您可以直接返回您的错误信息，我们的工作人员会提供给您
            //如果没有显示返回success,我们的系统会多次请求，直到成功，因此不必担心您系统偶尔的失败
        }

        if( type == 6 ){ //订单取消
            //使用 localOrderId 同步更改您的订单状态为取消
            //我们一般不会取消您的订单，如果您有特殊需求，建议您接入此类型回调

            //如果您更改自己的订单状态成功，给我们响应 成功
            return callbackUtils.success();
            //如果您的业务失败，不用担心，return xxxx 返回任意内容即可
            //如果没有显示返回success,我们的系统会多次请求，因此不必担心您系统偶尔的失败
        }


        //其他不处理的状态，建议您 为我们直接显示返回 success
        //如果您不显示返回成功，您可能会在您不处理的类型，多次收到我们的回调
        //因此为避免给您服务器带来不必要的性能开销，建议您显示返回成功
        return callbackUtils.success();

    }

}
