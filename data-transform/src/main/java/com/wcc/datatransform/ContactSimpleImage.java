package com.wcc.datatransform;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "simpleImage")
@TypeAlias("simpleImage")
@Data
@NoArgsConstructor
public class ContactSimpleImage extends BaseIdDocument {
    private String imageType;
    private Integer recognizeLang;
}
