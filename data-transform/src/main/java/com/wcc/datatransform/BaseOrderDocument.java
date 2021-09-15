package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.enums.RecognizeSourceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseOrderDocument extends BaseIdDocument {
    private RecognizeSourceType recognizeSource;
    private Integer fieldOrder;
}
