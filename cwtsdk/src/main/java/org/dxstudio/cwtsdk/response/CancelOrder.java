package org.dxstudio.cwtsdk.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class CancelOrder {

    @JSONField( name = "serial")
    private String orderId;

}
