package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contactdate {
    private String guid;
    private String datetype;
    private OffsetDateTime datevalue;
    private Integer fieldorder;
}
