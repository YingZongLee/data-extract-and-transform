package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Contact {
    private String guid;
    private String note;
    private String birthday;
    private String uniformnumber;
    private String nickname;
    private String recoglanguagefront;
    private String recoglanguageback;
    private String createtime;
    private String modifytime;
    private int iscorrected;
    private int isdeleted;
    private String textsha1;
    private String ownerguid;
    private String creatorguid;
    private String modifytimefordisplay;
    private String accountscanviewsha1;
    private String editorguid;
    private String modifytimeforcrmsync;
    private int version;
    private long modifytimeforsearch;
    private long modifytimeinsearch;
    private String companyguid;
    private String modifytimeforcontactserversync;
    private int isvertify;
    private String fulltext;
    private Contactsimpleinfo simpleInfo;

    @Builder.Default private List<Contactimage> contactimages = new LinkedList<>();
    @Builder.Default private List<Contactname> contactnames = new LinkedList<>();
    @Builder.Default private List<Contactjobinfo> contactjobinfos = new LinkedList<>();
    @Builder.Default private List<Contactphone> contactphones = new LinkedList<>();
    @Builder.Default private List<Contactaddress> contactaddress = new LinkedList<>();
    @Builder.Default private List<Contactemail> contactemails = new LinkedList<>();
    @Builder.Default private List<Contacturl> contacturls = new LinkedList<>();
    @Builder.Default private List<Contactim> contactims = new LinkedList<>();
    @Builder.Default private List<Contactsocial> contactsocials = new LinkedList<>();
    @Builder.Default private List<Contactdate> contactdates = new LinkedList<>();
    @Builder.Default private List<Contactcustomdata> contactcustomdata = new LinkedList<>();
    @Builder.Default private List<String> viewers = new LinkedList<>();
    @Builder.Default private List<String> categories = new LinkedList<>();
    @Builder.Default private List<String> status = new LinkedList<>();
    @Builder.Default private List<Contactstatusforaccount> statusDetail = new ArrayList<>();
}
