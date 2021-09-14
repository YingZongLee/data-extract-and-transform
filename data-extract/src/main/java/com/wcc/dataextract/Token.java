package com.wcc.dataextract;

import lombok.Data;

@Data
public class Token {
    private String token;
    private String userguid;
    private String createdtime;
    private String expiredtime;
    private String invalidtime;
    private String ip;
    private String deviceid;
    private String platform;
    private String platformcategory;
    private String hostname;
}
