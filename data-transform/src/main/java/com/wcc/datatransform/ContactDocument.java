package com.wcc.datatransform;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "contact")
@TypeAlias("contact")
@Data
public class ContactDocument extends BaseIdDocument {
    @Version
    private Long version;
    private Instant birthday;
    private String nickname;
    private String note;
    private String uniformNumber;
    private String textSha1;
    private String creatorGuid;
    private String companyGuid;
    private String editorGuid;
    private String ownerGuid;
    private Integer recogLanguageFront;
    private Integer recogLanguageBack;
    private Boolean isCorrected;
    private Boolean isDeleted;
    private Boolean isVertify;
    private Boolean isUnCategory;
    private Instant createTime;
    private Instant modifyTime;
    private Instant modifyTimeForDisplay;
    private Instant modifyTimeForCrmSync;
    private Instant modifyTimeForContactServerSync;
    private Double modifyTimeForSearch;
    private Double modifyTimeInSearch;
    private String accountsCanViewSha1;
    private List<String> accountsCanViews;
    private List<ContactJobInfo> jobInfos;
    private String fullText;
    private ContactSimpleInfo simpleInfo;
    private List<String> categories;
    private List<ContactName> names;
    private List<ContactAddress> addresses;
    private List<ContactDate> dates;
    private List<ContactSimpleImage> simpleImages;
    private List<ContactPhone> phones;
    private List<ContactEmail> emails;
    private List<ContactIms> ims;
    private List<ContactUrl> urls;
    private List<ContactSocial> socials;
    private List<ContactCustom> custom;
    private List<String> status;
}