package com.wcc.dataextract;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Device {
    private String guid;
    private String name;
    private String platform;
}
