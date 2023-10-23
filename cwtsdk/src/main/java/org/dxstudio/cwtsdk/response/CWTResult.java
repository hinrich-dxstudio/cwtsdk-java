package org.dxstudio.cwtsdk.response;

import lombok.Data;

@Data
public class CWTResult<T>{

    private boolean success;

    private String message;

    private int code;

    private T result;

}
