package com.wcc.datatransform;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

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
    private Instant modifyTimeForCrmSync;
    private String salesforceAccount;
    private String exportToSFContactId;
    private Instant previousModifiedTimeForSFContact;
    private String exportToSFLeadId;
    private Instant previousModifiedTimeForSFLead;
    private String sugarCrmAccount;
    private String exportToSugarCrmId;
    private Instant modifyTimeForContactServerSync;
    private String exchangeAccount;
    private String exportToExchangeId;
    private Instant previousModifiedTimeForExchange;
    private String office365Account;
    private String exportToOffice365Id;
    private Instant previousModifiedTimeForOffice365;
    private Instant statusUpdateTime;
    private String crmExportFailedReason;
}