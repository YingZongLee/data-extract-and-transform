package com.wcc.datatransform;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "status_for_account")
@TypeAlias("statusForAccount")
@Data
public class ContactStatusForAccountDocument extends BaseIdDocument {
    private String accountGuid;
    private String contactGuid;
    private Boolean isDeletedFromAccount;
    private String categories;
    private String categorySha1;
    private OffsetDateTime modifyTimeForCrmSync;
    private String salesforceAccount;
    private String exportToSFContactId;
    private OffsetDateTime previousModifiedTimeForSFContact;
    private String exportToSFLeadId;
    private OffsetDateTime previousModifiedTimeForSFLead;
    private String sugarCrmAccount;
    private String exportToSugarCrmId;
    private OffsetDateTime modifyTimeForContactServerSync;
    private String exchangeAccount;
    private String exportToExchangeId;
    private OffsetDateTime previousModifiedTimeForExchange;
    private String office365Account;
    private String exportToOffice365Id;
    private OffsetDateTime previousModifiedTimeForOffice365;
    private OffsetDateTime statusUpdateTime;
    private String crmExportFailedReason;
}