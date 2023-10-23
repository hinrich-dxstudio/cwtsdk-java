package org.dxstudio.cwtsdk.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentItem {

    private int type;

    private String accountName;

    private String bankerCode;

    private String account;

}
