package com.wcc.dataextract;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Token {
    private String token;
    private String userguid;
    private OffsetDateTime createdtime;
    private OffsetDateTime expiredtime;
    private OffsetDateTime invalidtime;
    private String ip;
    private String deviceid;
    private String platform;
    private String platformcategory;
    private String hostname;
}
