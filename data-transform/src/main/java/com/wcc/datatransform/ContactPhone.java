package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.enums.ContactPhoneType;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "phone")
@TypeAlias("phone")
@Data
public class ContactPhone extends BaseOrderDocument {
    private ContactPhoneType phoneType;
    private String phoneValue;
}
