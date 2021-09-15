package com.wcc.datatransform;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "address")
@TypeAlias("address")
@Data
public class ContactAddress extends BaseOrderDocument {
    private String countryCode;
    private String countryName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private Integer addressFormat;
    private String addressType;
}
