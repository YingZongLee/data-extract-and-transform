package com.wcc.dataextract;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Loginfailedinfo {
    private String guid;
    private String ipaddress;
    private OffsetDateTime lastfailuretime;
    private String useraccount;
    private int failurecount;
    private String failuretype;
}
