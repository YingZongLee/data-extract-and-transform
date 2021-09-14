package com.wcc.dataextract;

import lombok.Data;

@Data
public class Customfield {
    private String guid;
    private String customfieldcategory;
    private String customfieldname;
    private String customfieldcontactattribute;
    private int sortorder;
    private String companyguid;
}
