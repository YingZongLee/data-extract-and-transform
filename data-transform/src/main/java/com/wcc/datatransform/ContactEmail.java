package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.enums.ContactFieldType;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "email")
@TypeAlias("email")
@Data
public class ContactEmail extends BaseOrderDocument {
    private ContactFieldType emailType;
    private String emailValue;
}
