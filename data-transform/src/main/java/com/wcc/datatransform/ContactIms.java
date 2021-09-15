package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.enums.ContactIMType;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "ims")
@TypeAlias("ims")
@Data
public class ContactIms extends BaseOrderDocument {
    private ContactIMType imType;
    private String imValue;
}
