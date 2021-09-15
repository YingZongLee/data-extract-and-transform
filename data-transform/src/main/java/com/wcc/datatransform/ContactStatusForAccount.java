package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.enums.CrmExportFailedReason;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "status_for_account")
@TypeAlias("statusForAccount")
@CompoundIndex(name = "accountGuid_contactGuid", def = "{ 'accountGuid': 1, 'contactGuid': 1 }")
@Data
public class ContactStatusForAccount extends BaseIdDocument {

    @Indexed(sparse = true)
    private String accountGuid;
    @Indexed(sparse = true)
    private String contactGuid;
    private Boolean isDeletedFromAccount;

    private String categories;
    private String categorySha1;

    private DateTime modifyTimeForCrmSync;
    private String salesforceAccount;
    private String exportToSFContactId;
    private DateTime previousModifiedTimeForSFContact;
    private String exportToSFLeadId;
    private DateTime previousModifiedTimeForSFLead;
    private String sugarCrmAccount;
    private String exportToSugarCrmId;

    private DateTime modifyTimeForContactServerSync;
    private String exchangeAccount;
    private String exportToExchangeId;
    private DateTime previousModifiedTimeForExchange;
    private String office365Account;
    private String exportToOffice365Id;
    private DateTime previousModifiedTimeForOffice365;

    private DateTime statusUpdateTime;
    private CrmExportFailedReason crmExportFailedReason;
}


//accountGuid_contactGuid
//{
//    "accountGuid" : 1,
//        "contactGuid" : 1
//}
//
//contactGuid
//{
//"contactGuid" : 1
//}
//
//accountGuid
//{
//    "accountGuid" : 1
//}