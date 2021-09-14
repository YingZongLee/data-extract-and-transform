package com.wcc.dataextract;

import lombok.Data;

@Data
public class Notice {
    private String guid;
    private String notifytype;
    private String notifytime;
    private int isviewed;
    private String receiverguid;
    private String notifycontent;
    private String notifycategory;
    private String senderguid;
    private String companyguid;
}
