package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.enums.ContactUrlType;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "url")
@TypeAlias("url")
@Data
public class ContactUrl extends BaseOrderDocument {
    private ContactUrlType urlType;
    private String urlValue;
}
