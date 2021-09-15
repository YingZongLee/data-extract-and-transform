package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.enums.CustomFieldContactAttribute;
import lombok.*;
import org.joda.time.DateTime;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "custom")
@TypeAlias("custom")
@Data
public class ContactCustom extends BaseIdDocument {
    private CustomFieldContactAttribute fieldType;
    private String textValue;
    private Long intValue;
    private Double floatValue;
    private DateTime dateTimeValue;
    private String fieldSettingGuid;
}
