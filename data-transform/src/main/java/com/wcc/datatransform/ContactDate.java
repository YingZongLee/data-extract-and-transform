package com.wcc.datatransform;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "date")
@TypeAlias("date")
@Data
public class ContactDate extends BaseOrderDocument {
    private String dateType;
    private OffsetDateTime dateValue;
}
