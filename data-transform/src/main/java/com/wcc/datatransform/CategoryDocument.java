package com.wcc.datatransform;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "category")
@TypeAlias("category")
@Data
public class CategoryDocument extends BaseIdDocument {
    private String name;
    private String categoryType;
    private Integer displayOrder;
    private Integer contactCount;
    private String ownerGuid;
    private Instant updateTime;
    private Boolean isDeleted;
    private String secretaryAccountGuid;
    private String inheritCategoryGuid;
    private String parentCategoryGuid;
    private Boolean isUsual;
}