package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.enums.ContactDateType;
import lombok.*;
import org.joda.time.DateTime;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "date")
@TypeAlias("date")
@Data
public class ContactDate extends BaseOrderDocument {
    private ContactDateType dateType;
    private DateTime dateValue;
}
