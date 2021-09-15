package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.enums.CategoryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "category")
@TypeAlias("category")
@Data
@CompoundIndex(name = "ownerGuid_categoryType", def = "{ ownerGuid : 1, categoryType : 1 }")
public class Category extends BaseIdDocument {
    private String name;
    private CategoryType categoryType;
    private Integer displayOrder;
    private Integer contactCount;
    private String ownerGuid;
    private DateTime updateTime;
    private Boolean isDeleted;
    private String secretaryAccountGuid;
    private String inheritCategoryGuid;
    @Indexed(sparse = true)
    private String parentCategoryGuid;
    private Boolean isUsual;

    public Category() {
        isDeleted = false;
    }
}


//ownerGuid_categoryType
//{
//"ownerGuid" : 1,
//"categoryType" : 1
//}
//
//parentCategoryGuid
//{
//"parentCategoryGuid" : 1
//}