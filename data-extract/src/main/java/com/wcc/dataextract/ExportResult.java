package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportResult {
    @Builder.Default private List<Device> device = new LinkedList<>();
    private Company company;
    @Builder.Default private List<Account> accounts = new LinkedList<>();
    @Builder.Default private List<Accountbindingdeviceinfo> accountbindingdeviceinfos = new LinkedList<>();
    @Builder.Default private List<Accountloginrecord> accountloginrecords = new LinkedList<>();
    @Builder.Default private List<Accountmycardsetting> accountmycardsettings = new LinkedList<>();
    @Builder.Default private List<Accountsecuritysetting> accountsecuritysettings = new LinkedList<>();
    @Builder.Default private List<Accountsharetargetsetting> accountsharetargetsettings = new LinkedList<>();
    @Builder.Default private List<Accountprivatesetting> accountprivatesettings = new LinkedList<>();
    @Builder.Default private List<Accountsharetoaccountitem> accountsharetoaccountitems = new LinkedList<>();
    @Builder.Default private List<Customfield> customfields = new LinkedList<>();
    @Builder.Default private List<Forgetpasswordsession> forgetpasswordsessions = new LinkedList<>();
    @Builder.Default private List<Globalinfocompany> globalinfocompanies = new LinkedList<>();
    @Builder.Default private List<Loginfailedinfo> loginfailedinfos = new LinkedList<>();
    @Builder.Default private List<Notice> notices = new LinkedList<>();
    @Builder.Default private List<Picklistcontent> picklistcontents = new LinkedList<>();
    @Builder.Default private List<Token> tokens = new LinkedList<>();
    @Builder.Default private List<Category> categories = new LinkedList<>();
    private int contactCount;
}
