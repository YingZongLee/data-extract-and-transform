package com.penpower.worldcard.team.mongo.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "simpleInfo")
@TypeAlias("simpleInfo")
@Data
public class ContactSimpleInfo extends BaseIdDocument {
    private String fullNameEastFirstWestFirst;
    private String fullNameEastFirstWestLast;
    private String fullNameEastLastWestFirst;
    private String fullNameEastLastWestLast;
    @Indexed()
    private String company;
    private String department;
    private String jobTitle;
    private DateTime createTime;
}
