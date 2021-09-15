package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.enums.ContactImageType;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "simpleImage")
@TypeAlias("simpleImage")
@Data
@NoArgsConstructor
public class ContactSimpleImage extends BaseIdDocument {
    private ContactImageType imageType;
    private Integer recognizeLang;

    public ContactSimpleImage(ContactImage image, Integer recognizeLang) {
        super.setGuid(image.getGuid());
        this.imageType = image.getImageType();
        this.recognizeLang = recognizeLang;
    }
}
