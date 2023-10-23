package org.dxstudio.cwtsdk.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class QueryItem {

    @JSONField( name = "serial")
    private String orderId;

    private String localOrderId;

    @JSONField( name = "timeCreate")
    private Long orderAt;

    private int status;

    @JSONField( name = "originCash")
    private BigDecimal amount;

    @JSONField( name = "cash")
    private BigDecimal amountReceived;

}
