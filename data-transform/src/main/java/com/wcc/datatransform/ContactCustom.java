package com.wcc.datatransform;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "custom")
@TypeAlias("custom")
@Data
public class ContactCustom extends BaseIdDocument {
    private String fieldType;
    private String textValue;
    private Long intValue;
    private Double floatValue;
    private Instant dateTimeValue;
    private String fieldSettingGuid;
}
