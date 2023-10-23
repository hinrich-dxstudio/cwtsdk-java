package org.dxstudio.cwtsdk.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateOrderParam {

    private String localOrderId = "";

    private int type = 1;

    private BigDecimal cash = BigDecimal.ZERO;

    private int minRemain = 0;

    private int maxOrderNum = 1;

    private int waitTime = 900;

    private String notifyUrl = "";

    private int needConfirm = 0;

    private int allCall = 0;

    private int timeoutProcess = 0;

    private List<PaymentItem> payments = new ArrayList<PaymentItem>();

    public CreateOrderParam(){

    }

    public CreateOrderParam(CreateOrderParam createOrderParamBuilder) {
        localOrderId = createOrderParamBuilder.getLocalOrderId();
        type = createOrderParamBuilder.getType();
        cash = createOrderParamBuilder.getCash();
        minRemain = createOrderParamBuilder.getMinRemain();
        maxOrderNum = createOrderParamBuilder.getMaxOrderNum();
        waitTime = createOrderParamBuilder.getWaitTime();
        notifyUrl = createOrderParamBuilder.getNotifyUrl();
        needConfirm = createOrderParamBuilder.getNeedConfirm();
        allCall = createOrderParamBuilder.getAllCall();
        timeoutProcess = createOrderParamBuilder.getTimeoutProcess();
        payments = createOrderParamBuilder.getPayments();
    }

    public static CreateOrderParamBuilder builder() {
        return new CreateOrderParamBuilder( new CreateOrderParam() );
    }

    public static class CreateOrderParamBuilder
    {
        CreateOrderParam target;
        public CreateOrderParamBuilder( CreateOrderParam target ){
            this.target = target;
        }
        public CreateOrderParam build(){
            return new CreateOrderParam(target);
        }


        public CreateOrderParamBuilder localOrderId( String localOrderId ){
            target.localOrderId = localOrderId;
            return this;
        }

        public CreateOrderParamBuilder amount( BigDecimal amount ){
            target.cash = amount;
            return this;
        }

        public CreateOrderParamBuilder waitTime( int waitTime ){
            target.waitTime = waitTime;
            return this;
        }

        public CreateOrderParamBuilder notifyUrl( String notifyUrl ){
            target.notifyUrl = notifyUrl;
            return this;
        }

        public CreateOrderParamBuilder needConfirm( int needConfirm ){
            target.needConfirm = needConfirm;
            return this;
        }

        public CreateOrderParamBuilder allCall( int allCall ){
            target.allCall = allCall;
            return this;
        }

        public CreateOrderParamBuilder timeoutProcess( int timeoutProcess ){

            target.timeoutProcess = timeoutProcess;
            return this;

        }

        public CreateOrderParamBuilder setPayment( String name, String cardNumber, String bankCode ){

            target.payments.add(0, PaymentItem.builder()
                    .accountName(name)
                    .account(cardNumber)
                    .bankerCode(bankCode)
                    .build() );
            return this;

        }


    }



}
