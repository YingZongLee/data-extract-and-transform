package com.wcc.datatransform;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

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
    private String company;
    private String department;
    private String jobTitle;
    private Instant createTime;
}
