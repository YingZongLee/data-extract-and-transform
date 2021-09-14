package com.wcc.dataextract;

import lombok.Data;

@Data
public class Loginfailedinfo {
    private String guid;
    private String ipaddress;
    private String lastfailuretime;
    private String useraccount;
    private int failurecount;
    private String failuretype;
}
