package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.enums.ContactSocialType;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "social")
@TypeAlias("social")
@Data
public class ContactSocial extends BaseOrderDocument {
    private ContactSocialType socialType;
    private String socialValue;
}
