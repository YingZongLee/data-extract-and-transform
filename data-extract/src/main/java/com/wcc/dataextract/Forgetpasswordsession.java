package com.wcc.dataextract;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Forgetpasswordsession {
    private String guid;
    private String email;
    private String token;
    private OffsetDateTime createdate;
}
