package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contactimage {
    private String guid;
    private String imagetype;
    private String createtime;
    private String updatetime;
    private String sha1;
    private String contact_guid;
    private String contentdatapath;
    private Boolean isdeleted;
}

enum ContactImageType {
    FRONT, REAR, LOGO
}
