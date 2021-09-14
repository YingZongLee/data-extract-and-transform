package com.wcc.dataextract;

import lombok.Data;

@Data
public class Forgetpasswordsession {
    private String guid;
    private String email;
    private String token;
    private String createdate;
}
