package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.enums.ContactImageType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "image")
@TypeAlias("image")
@Data
public class ContactImage extends BaseIdDocument {
    private String sha1;
    private ContactImageType imageType;
    private String filePath;
    private byte[] contentData;
    private Boolean isDeleted;
    private DateTime createTime;
    private DateTime updateTime;
}
