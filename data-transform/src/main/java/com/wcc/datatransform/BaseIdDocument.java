package com.wcc.datatransform;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class BaseIdDocument {
    @Id
    private String guid;
}
