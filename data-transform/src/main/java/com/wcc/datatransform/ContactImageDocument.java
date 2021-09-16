package com.wcc.datatransform;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "image")
@TypeAlias("image")
@Data
public class ContactImageDocument extends BaseIdDocument {
    private String sha1;
    private String imageType;
    private String filePath;
    private byte[] contentData;
    private Boolean isDeleted;
    private Instant createTime;
    private Instant updateTime;
}
