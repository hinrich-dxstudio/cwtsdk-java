package org.dxstudio.examplespringboot.controller;

import org.dxstudio.cwtsdk.MerchantSDK;
import org.dxstudio.cwtsdk.request.CancelOrderParam;
import org.dxstudio.cwtsdk.request.CreateOrderParam;
import org.dxstudio.cwtsdk.request.QueryOrderParam;
import org.dxstudio.cwtsdk.response.CWTResult;
import org.dxstudio.cwtsdk.response.CancelOrder;
import org.dxstudio.cwtsdk.response.CreateOrder;
import org.dxstudio.cwtsdk.response.QueryItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
public class RequestAPI {

    @Value("${cwtSdk.gateway}")
    String gateway;

    @Value("${cwtSdk.key}")
    String key;

    @Value("${cwtSdk.secret}")
    String secret;


    @RequestMapping("/api/create")
    public CreateOrder Create( @RequestParam(name = "amount") BigDecimal amount ) throws IOException {

        MerchantSDK sdk = new MerchantSDK(gateway, key, secret);
        CreateOrderParam param = CreateOrderParam.builder()
                .localOrderId("my2343209234889454")
                .notifyUrl("http://192.168.222.187:8080/callback")
                .amount(amount)
                .waitTime( 15 * 60 )
                .timeoutProcess(1)  //超时处理方式，如果为1，则超时后我们将进行人工处理，能保证您的提款100%成功
                .setPayment("hinrich","1111222233334444","CSCB")
                .build();

        CWTResult<CreateOrder> result = sdk.createOrder( param );

        CreateOrder createOrder = null;
        if( result.isSuccess() ){

            createOrder = result.getResult();

            //订单创建时间戳
            Long createAt = createOrder.getOrderAt();

            //点付订单号
            String orderId = createOrder.getOrderId();

            /*
             * 之后是你的业务
             * ...................
             * ................
             */

        }else{
            System.out.println( result.getMessage() );
        }

        return createOrder;

    }


    @RequestMapping("/api/query")
    public QueryItem Query(@RequestParam(name = "order_id") String orderId) throws IOException {

        MerchantSDK sdk = new MerchantSDK(gateway, key, secret);
        QueryOrderParam param = QueryOrderParam.builder().orderId(orderId).build();
        QueryItem item = null;


        CWTResult<QueryItem> result = sdk.queryOrder(param);
        //判断是否成功
        if (result.isSuccess()) {
            //成功则获取到查询结果
            item = result.getResult();
            /*
              之后是你的业务
              ...................
              ................
            */
        }else{

            //如果不成功，可以获取错误信息
            String msg = result.getMessage();
            System.out.println(msg);

        }

        return item;

    }

    @RequestMapping("/api/cancel")
    public CancelOrder Cancel(@RequestParam(name = "order_id") String orderId) throws IOException {

        MerchantSDK sdk = new MerchantSDK( gateway, key, secret );
        CancelOrderParam param = CancelOrderParam.builder().orderId( orderId ).build();
        CWTResult<CancelOrder> cwtResult = sdk.cancelOrder( param );

        CancelOrder cancelOrder = null;
        if (cwtResult.isSuccess()) {
            cancelOrder = cwtResult.getResult();
            /*
             * 之后是你的业务
             * ...................
             * ...................
             */
        }else{
            //如果不成功，可以获取错误信息
            String msg = cwtResult.getMessage();
            System.out.println(msg);
        }

        return cancelOrder;
    }

}
