package org.dxstudio.cwtsdk.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class CreateOrder {

    @JSONField(name = "serial")
    private String orderId;

    @JSONField(name = "timeCreate")
    private Long orderAt;

}
