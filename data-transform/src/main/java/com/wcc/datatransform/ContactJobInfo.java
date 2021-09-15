package com.penpower.worldcard.team.mongo.document;

import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "jobInfo")
@TypeAlias("jobInfo")
@Data
public class ContactJobInfo extends BaseOrderDocument {
    private String companyName;
    private String companyPronounce;
    private String department;
    private String jobTitle;
}
