package com.penpower.worldcard.team.mongo.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "fulltext")
@TypeAlias("fulltext")
@Data
public class ContactFullText extends BaseIdDocument {
    private String textContent;
}
