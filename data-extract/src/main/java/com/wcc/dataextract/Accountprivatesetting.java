package com.wcc.dataextract;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Accountprivatesetting {
    private String guid;
    private String mapengine;
    private String westernsurnameorder;
    private String asiansurnameorder;
    private int showownerinfo;
    private int autoshare;
    private OffsetDateTime lastnoticecountview;
    private String sortingorder;
    private String identificationresult;
    private String account_guid;
    private String userstoredcrmtype;
    private String crmexportmode;
    private String usercrmaccount;
    private String crmlogintoken;
    private OffsetDateTime modifytime;
    private OffsetDateTime crmsynclastcompletedtimeforwct;
    private OffsetDateTime crmsynclastcompletedtimeforcrm;
    private OffsetDateTime lastdeeplydeletedcontacttime;
    private String contactservertype;
    private String contactserverexportmode;
    private String exchangeauthinfo;
    private String office365authinfo;
    private OffsetDateTime contactserversynclastcompletedtimeforwct;
    private OffsetDateTime contactserversynclastcompletedtimeforcontactserver;
    private String office365deltalinkforsync;
    private int isaddnoteinfoafterrecog;
    private int iseditafterrecog;
    private String crmcompanyassignmentoption;
    private OffsetDateTime leadsynclastcompletedtimeforwct;
    private OffsetDateTime leadsynclastcompletedtimeforlead;
}
