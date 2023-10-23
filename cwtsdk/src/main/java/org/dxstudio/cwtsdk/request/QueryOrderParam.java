package org.dxstudio.cwtsdk.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryOrderParam {

    @JSONField( name = "serial")
    private String orderId = "";

}
