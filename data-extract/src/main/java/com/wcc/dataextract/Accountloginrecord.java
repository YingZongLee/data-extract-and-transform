package com.wcc.dataextract;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Accountloginrecord {
    private String guid;
    private OffsetDateTime logintime;
    private String device_guid;
    private String account_guid;
    private OffsetDateTime logintimeforbackuprestore;
}
