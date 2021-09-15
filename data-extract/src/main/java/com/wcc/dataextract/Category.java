package com.wcc.dataextract;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Category {
    private String guid;
    private String name;
    private String categorytype;
    private int displayorder;
    private int contactcount;
    private String ownerguid;
    private OffsetDateTime updatetime;
    private int isdeleted;
    private String secretary_account_guid;
    private String inheritecategoryguid;
    private String parentcategoryguid;
    private int isusual;
}
