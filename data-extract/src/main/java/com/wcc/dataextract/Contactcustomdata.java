package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contactcustomdata {
    private String guid;
    private String fieldtype;
    private String fieldsettingguid;
    private String textvalue;
    private Long intvalue;
    private Double floatvalue;
    private OffsetDateTime datetimevalue;
}

enum CustomFieldContactAttribute {
    TEXT,EMAIL,PICKLIST,NUMBER,FLOAT,DATE,DATE_TIME,URL
}
