package com.penpower.worldcard.team.mongo.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "contact")
@TypeAlias("contact")
@Data
@CompoundIndexes({
        @CompoundIndex(name = "ownerGuid_isDeleted_createTime_accountsCanView", def = "{ ownerGuid : 1, isDeleted : 1, createTime : 1, accountsCanViews : 1 }"),
        @CompoundIndex(name = "ownerGuid_isDeleted_createTime_categories", def = "{ ownerGuid : 1, isDeleted : 1, createTime : 1, categories : 1 }"),
        @CompoundIndex(name = "categories_isDeleted_isCorrected", def = "{ categories : 1, isDeleted : 1, isCorrected : 1 }"),
        @CompoundIndex(name = "categories_modifyTime_isDeleted", def = "{ categories : 1, modifyTime : 1, isDeleted : 1 }"),
        @CompoundIndex(name = "ownerGuid_isDeleted_isUnCategory_createTime_accountsCanViews", def = "{ ownerGuid : 1, isDeleted : 1, isUnCategory : 1, createTime : 1, accountsCanViews : 1 }"),
        @CompoundIndex(name = "ownerGuid_isDeleted_isUnCategory", def = "{ ownerGuid : 1, isDeleted : 1, isUnCategory : 1 }"),
})
public class Contact extends BaseIdDocument {
    @Version
    private Long version;
    private DateTime birthday;
    private String nickname;
    private String note;
    private String uniformNumber;
    private String textSha1;
    private String creatorGuid;
    @Indexed()
    private String companyGuid;
    private String editorGuid;
    @Indexed()
    private String ownerGuid;
    private Integer recogLanguageFront;
    private Integer recogLanguageBack;
    private Boolean isCorrected;
    @Indexed()
    private Boolean isDeleted;
    private Boolean isVertify;
    private Boolean isUnCategory;
    private DateTime createTime;
    private DateTime modifyTime;
    private DateTime modifyTimeForDisplay;
    private DateTime modifyTimeForCrmSync;
    private DateTime modifyTimeForContactServerSync;
    private Double modifyTimeForSearch;
    private Double modifyTimeInSearch;
    private String accountsCanViewSha1;
    private List<String> accountsCanViews;
    private List<ContactJobInfo> jobInfos;
    private String fullText;
    private ContactSimpleInfo simpleInfo;
    private List<String> activities;
    @Indexed()
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



//companyGuid
//{
//    "companyGuid" : 1
//}
//
//simpleInfo.company
//{
//        "simpleInfo.company" : 1
//}
//
//isDeleted
//{
//        "isDeleted" : 1
//}
//
//categories
//{
//"categories" : 1
//}
//
//ownerGuid
//{
//"ownerGuid" : 1
//}
//
//ownerGuid_isDeleted_createTime_accountsCanView
//{
//"ownerGuid" : 1,
//"isDeleted" : 1,
//"createTime" : 1,
//"accountsCanViews" : 1
//}
//
//ownerGuid_isDeleted_createTime_categories
//{
//"ownerGuid" : 1,
//"isDeleted" : 1,
//"createTime" : 1,
//"categories" : 1
//}
//
//categories_isDeleted_isCorrected
//{
//"categories" : 1,
//"isDeleted" : 1,
//"isCorrected" : 1
//}
//
//categoires_modifyTime_isDeleted
//{
//"categories" : 1,
//"modifyTime" : 1,
//"isDeleted" : 1
//}