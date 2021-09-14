package com.wcc.dataextract;

import lombok.Data;

@Data
public class Accountloginrecord {
    private String guid;
    private String logintime;
    private String device_guid;
    private String account_guid;
    private String logintimeforbackuprestore;
}
