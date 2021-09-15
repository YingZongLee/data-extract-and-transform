package com.penpower.worldcard.team.mongo.document;

import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "name")
@TypeAlias("name")
@Data
public class ContactName extends BaseOrderDocument {
    private String firstName;
    private String firstNamePronounce;
    private String lastName;
    private String lastNamePronounce;
    private String middleName;
    private String prefix;
    private String suffix;
}
