package com.wcc.dataextract;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
public class DataExtractApplication implements CommandLineRunner {
	private static Path workingDir = null;
	private Gson gson;
	@Value("${s:null}")
	private String serviceId;
	@Value("${c:null}")
	private String companyId;
	@Value("${extDir}")
	private String extDir;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	public static void main(String[] args) {
		SpringApplication.run(DataExtractApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("companyGuid: " + companyId + " serviceId: " + serviceId);
		System.out.println("dataSource: " + jdbcTemplate.getDataSource().getConnection().getMetaData().getURL());
		String configCompanyGuid = configure();
		String path = Arrays.stream(args).findFirst().orElse(extDir);
		workingDir = Paths.get(path).resolve(configCompanyGuid);
		FileUtils.deleteDirectory(workingDir.toFile());
		Files.createDirectories(workingDir);
		Path exportJsonFile = workingDir.resolve("export.json");

		ExportResult extResult = new ExportResult();
		jdbcTemplate.query("SELECT * FROM DEVICE", rs -> {
			extResult.getDevice().add(
					Device.builder()
							.guid(rs.getString(1))
							.name(rs.getString(2))
							.platform(rs.getString(3))
							.build()
			);
		});
		jdbcTemplate.query("SELECT * FROM COMPANY WHERE GUID=?", rs -> {
			extResult.setCompany(
					Company.builder()
							.guid(rs.getString(1))
							.name(rs.getString(2))
							.telphone(rs.getString(3))
							.build()
			);
		}, configCompanyGuid);
		jdbcTemplate.query("SELECT * FROM ACCOUNT WHERE COMPANY_GUID=?", rs -> {
			extResult.getAccounts().add(
					Account.builder()
							.guid(rs.getString("guid"))
							.email(rs.getString("email"))
							.password(rs.getString("password"))
							.displayname(rs.getString("displayname"))
							.createtime(rs.getObject("createtime", OffsetDateTime.class))
							.status(rs.getString("status"))
							.role(rs.getString("role"))
							.exportability(rs.getInt("exportability"))
							.boss(rs.getString("boss"))
							.company_guid(rs.getString("company_guid"))
							.categoryorderupdatetime(rs.getObject("categoryorderupdatetime", OffsetDateTime.class))
							.resigndate(rs.getObject("resigndate", OffsetDateTime.class))
							.inheritdate(rs.getObject("inheritdate", OffsetDateTime.class))
							.inheritedaccount(rs.getString("inheritedaccount"))
							.secretary(rs.getInt("secretary"))
							.printability(rs.getInt("printability"))
							.inheritancestatus(rs.getString("inheritancestatus"))
							.contactcountinprivate(0)
							.accounttype(rs.getString("accounttype"))
							.accountsubscriptionstatus(rs.getString("accountsubscriptionstatus"))
							.lastpasswordchangetime(rs.getObject("lastpasswordchangetime", OffsetDateTime.class))
							.lockedissue(rs.getString("lockedissue"))
							.lockcase(rs.getString("lockcase"))
							.assistantguid(rs.getString("assistantguid"))
							.build()
			);
		}, configCompanyGuid);
		String nOfAccountGuid = extResult.getAccounts().stream().map(a -> String.format("'%s'", a.getGuid())).collect(Collectors.joining(","));
		String nOfAccountEmail = extResult.getAccounts().stream().map(a -> String.format("'%s'", a.getEmail())).collect(Collectors.joining(","));
		jdbcTemplate.query(String.format("SELECT * FROM ACCOUNTBINDINGDEVICEINFO WHERE ACCOUNT_GUID IN (%s)", nOfAccountGuid), rs -> {
			Accountbindingdeviceinfo accountbindingdeviceinfo = new Accountbindingdeviceinfo();
			accountbindingdeviceinfo.setGuid(rs.getString("guid"));
			accountbindingdeviceinfo.setIpconstraintenable(0);
			accountbindingdeviceinfo.setAccount_guid(rs.getString("account_guid"));
			accountbindingdeviceinfo.setDevice_guid(rs.getString("device_guid"));
			extResult.getAccountbindingdeviceinfos().add(accountbindingdeviceinfo);
		});
		jdbcTemplate.query(String.format("SELECT * FROM ACCOUNTLOGINRECORD WHERE ACCOUNT_GUID IN (%s)", nOfAccountGuid), rs -> {
			Accountloginrecord accountloginrecord = new Accountloginrecord();
			accountloginrecord.setGuid(rs.getString("guid"));
			accountloginrecord.setLogintime(rs.getObject("logintime", OffsetDateTime.class));
			accountloginrecord.setDevice_guid(rs.getString("device_guid"));
			accountloginrecord.setAccount_guid(rs.getString("account_guid"));
			accountloginrecord.setLogintimeforbackuprestore(rs.getObject("logintimeforbackuprestore", OffsetDateTime.class));
			extResult.getAccountloginrecords().add(accountloginrecord);
		});
		jdbcTemplate.query(String.format("SELECT * FROM ACCOUNTMYCARDSETTING WHERE ACCOUNT_GUID IN (%s)", nOfAccountGuid), rs -> {
			Accountmycardsetting accountmycardsetting = new Accountmycardsetting();
			String guid = rs.getString("guid");
			accountmycardsetting.setGuid(guid);
			accountmycardsetting.setIsenable(rs.getInt("isenable"));
			accountmycardsetting.setMycardinfo(rs.getString("mycardinfo"));
			accountmycardsetting.setAccount_guid(rs.getString("account_guid"));

			byte[] logoImageData = rs.getBytes("logoimagedata");
			if(logoImageData != null){
				String logoImageName = String.format("%s-logo.jpg", guid);
				FileWrite(workingDir.resolve(logoImageName), logoImageData);
				accountmycardsetting.setLogoimagedatapath(logoImageName);
			}

			byte[] frontImageData = rs.getBytes("frontimagedata");
			if(frontImageData != null){
				String frontImageName = String.format("%s-front.jpg", guid);
				FileWrite(workingDir.resolve(frontImageName), frontImageData);
				accountmycardsetting.setFrontimagedatapath(frontImageName);
			}
			extResult.getAccountmycardsettings().add(accountmycardsetting);
		});
		jdbcTemplate.query(String.format("SELECT * FROM ACCOUNTSECURITYSETTING WHERE ACCOUNT_GUID IN (%s)", nOfAccountGuid), rs -> {
			Accountsecuritysetting accountsecuritysetting = new Accountsecuritysetting();
			accountsecuritysetting.setGuid(rs.getString("guid"));
			accountsecuritysetting.setIosenable(rs.getInt("iosenable"));
			accountsecuritysetting.setAndroidenable(rs.getInt("androidenable"));
			accountsecuritysetting.setWindowsenable(rs.getInt("windowsenable"));
			accountsecuritysetting.setMacenable(rs.getInt("macenable"));
			accountsecuritysetting.setWebenable(rs.getInt("webenable"));
			accountsecuritysetting.setAccount_guid(rs.getString("account_guid"));
			accountsecuritysetting.setIosbindingenable(rs.getInt("iosbindingenable"));
			accountsecuritysetting.setAndroidbindingenable(rs.getInt("androidbindingenable"));
			accountsecuritysetting.setWindowsbindingenable(rs.getInt("windowsbindingenable"));
			accountsecuritysetting.setMacbindingenable(rs.getInt("macbindingenable"));
			extResult.getAccountsecuritysettings().add(accountsecuritysetting);
		});
		jdbcTemplate.query(String.format("SELECT * FROM ACCOUNTSHARETARGETSETTING WHERE ACCOUNT_GUID IN (%s)", nOfAccountGuid), rs -> {
			Accountsharetargetsetting accountsharetargetsetting = new Accountsharetargetsetting();
			accountsharetargetsetting.setGuid(rs.getString("guid"));
			accountsharetargetsetting.setShareitem(rs.getString("shareitem"));
			accountsharetargetsetting.setSharedaccountguid(rs.getString("sharedaccountguid"));
			accountsharetargetsetting.setAccount_guid(rs.getString("account_guid"));
			extResult.getAccountsharetargetsettings().add(accountsharetargetsetting);
		});
		jdbcTemplate.query(String.format("SELECT * FROM ACCOUNT_PRIVATE_SETTINGS WHERE ACCOUNT_GUID IN (%s)", nOfAccountGuid), rs -> {
			Accountprivatesetting accountprivatesetting = new Accountprivatesetting();
			accountprivatesetting.setGuid(rs.getString("guid"));
			accountprivatesetting.setMapengine(rs.getString("mapengine"));
			accountprivatesetting.setWesternsurnameorder(rs.getString("westernsurnameorder"));
			accountprivatesetting.setAsiansurnameorder(rs.getString("asiansurnameorder"));
			accountprivatesetting.setShowownerinfo(rs.getInt("showownerinfo"));
			accountprivatesetting.setAutoshare(rs.getInt("autoshare"));
			accountprivatesetting.setLastnoticecountview(rs.getObject("lastnoticecountview", OffsetDateTime.class));
			accountprivatesetting.setSortingorder(rs.getString("sortingorder"));
			accountprivatesetting.setIdentificationresult(rs.getString("identificationresult"));
			accountprivatesetting.setAccount_guid(rs.getString("account_guid"));
			accountprivatesetting.setUserstoredcrmtype(rs.getString("userstoredcrmtype"));
			accountprivatesetting.setCrmexportmode(rs.getString("crmexportmode"));
			accountprivatesetting.setUsercrmaccount(rs.getString("usercrmaccount"));
			accountprivatesetting.setCrmlogintoken(rs.getString("crmlogintoken"));
			accountprivatesetting.setModifytime(rs.getObject("modifytime", OffsetDateTime.class));
			accountprivatesetting.setCrmsynclastcompletedtimeforwct(rs.getObject("crmsynclastcompletedtimeforwct", OffsetDateTime.class));
			accountprivatesetting.setCrmsynclastcompletedtimeforcrm(rs.getObject("crmsynclastcompletedtimeforcrm", OffsetDateTime.class));
			accountprivatesetting.setLastdeeplydeletedcontacttime(rs.getObject("lastdeeplydeletedcontacttime", OffsetDateTime.class));
			accountprivatesetting.setContactservertype(rs.getString("contactservertype"));
			accountprivatesetting.setContactserverexportmode(rs.getString("contactserverexportmode"));
			accountprivatesetting.setExchangeauthinfo(rs.getString("exchangeauthinfo"));
			accountprivatesetting.setOffice365authinfo(rs.getString("office365authinfo"));
			accountprivatesetting.setContactserversynclastcompletedtimeforwct(rs.getObject("contactserversynclastcompletedtimeforwct", OffsetDateTime.class));
			accountprivatesetting.setContactserversynclastcompletedtimeforcontactserver(rs.getObject("contactserversynclastcompletedtimeforcontactserver", OffsetDateTime.class));
			accountprivatesetting.setOffice365deltalinkforsync(rs.getString("office365deltalinkforsync"));
			accountprivatesetting.setIsaddnoteinfoafterrecog(rs.getInt("isaddnoteinfoafterrecog"));
			accountprivatesetting.setIseditafterrecog(rs.getInt("iseditafterrecog"));
			accountprivatesetting.setCrmcompanyassignmentoption(rs.getString("crmcompanyassignmentoption"));
			accountprivatesetting.setLeadsynclastcompletedtimeforwct(rs.getObject("leadsynclastcompletedtimeforwct", OffsetDateTime.class));
			accountprivatesetting.setLeadsynclastcompletedtimeforlead(rs.getObject("leadsynclastcompletedtimeforlead", OffsetDateTime.class));
			extResult.getAccountprivatesettings().add(accountprivatesetting);
		});
		jdbcTemplate.query(String.format("SELECT * FROM ACCOUNT_SHARE_TO_ACCOUNT_ITEMS WHERE SHARE_ACCOUNT_GUID IN (%s) OR BESHARED_ACCOUNT_GUID IN (%s)", nOfAccountGuid, nOfAccountGuid), rs -> {
			Accountsharetoaccountitem accountsharetoaccountitem = new Accountsharetoaccountitem();
			accountsharetoaccountitem.setGuid(rs.getString("guid"));
			accountsharetoaccountitem.setShare_account_guid(rs.getString("share_account_guid"));
			accountsharetoaccountitem.setBeshared_account_guid(rs.getString("beshared_account_guid"));
			accountsharetoaccountitem.setItem_guid(rs.getString("item_guid"));
			accountsharetoaccountitem.setItem_type(rs.getString("item_type"));
			extResult.getAccountsharetoaccountitems().add(accountsharetoaccountitem);
		});
		jdbcTemplate.query("SELECT * FROM CUSTOMFIELD WHERE COMPANYGUID=?", rs -> {
			Customfield customfield = new Customfield();
			customfield.setGuid(rs.getString("guid"));
			customfield.setCustomfieldcategory(rs.getString("customfieldcategory"));
			customfield.setCustomfieldname(rs.getString("customfieldname"));
			customfield.setCustomfieldcontactattribute(rs.getString("customfieldcontactattribute"));
			customfield.setSortorder(rs.getInt("sortorder"));
			customfield.setCompanyguid(rs.getString("companyguid"));
			extResult.getCustomfields().add(customfield);
		}, configCompanyGuid);
		String nOfCustomFieldGuid = extResult.getCustomfields().stream().map(a -> String.format("'%s'", a.getGuid())).collect(Collectors.joining(","));
		jdbcTemplate.query(String.format("SELECT * FROM FORGET_PASSWORD_SESSION WHERE EMAIL IN (%s)", nOfAccountEmail), rs -> {
			Forgetpasswordsession forgetpasswordsession = new Forgetpasswordsession();
			forgetpasswordsession.setGuid(rs.getString("guid"));
			forgetpasswordsession.setEmail(rs.getString("email"));
			forgetpasswordsession.setToken(rs.getString("token"));
			forgetpasswordsession.setCreatedate(rs.getObject("createdate", OffsetDateTime.class));
			extResult.getForgetpasswordsessions().add(forgetpasswordsession);
		});
		jdbcTemplate.query("SELECT * FROM GLOBALINFOCOMPANY WHERE COMPANYGUID=?", rs -> {
			Globalinfocompany globalinfocompany = new Globalinfocompany();
			globalinfocompany.setGuid(rs.getString("guid"));
			globalinfocompany.setConfigkey(rs.getString("configkey"));
			globalinfocompany.setConfigvalue(rs.getString("configvalue"));
			globalinfocompany.setCompanyguid(rs.getString("companyguid"));
			extResult.getGlobalinfocompanies().add(globalinfocompany);
		}, configCompanyGuid);
		jdbcTemplate.query(String.format("SELECT * FROM LOGINFAILEDLINFO WHERE USERACCOUNT IN (%s)", nOfAccountEmail), rs -> {
			Loginfailedinfo loginfailedinfo = new Loginfailedinfo();
			loginfailedinfo.setGuid(rs.getString("guid"));
			loginfailedinfo.setIpaddress(rs.getString("ipaddress"));
			loginfailedinfo.setLastfailuretime(rs.getObject("lastfailuretime", OffsetDateTime.class));
			loginfailedinfo.setUseraccount(rs.getString("useraccount"));
			loginfailedinfo.setFailurecount(rs.getInt("failurecount"));
			loginfailedinfo.setFailuretype(rs.getString("failuretype"));
			extResult.getLoginfailedinfos().add(loginfailedinfo);
		});
		jdbcTemplate.query("SELECT * FROM NOTICE WHERE COMPANYGUID=?", rs -> {
			Notice notice = new Notice();
			notice.setGuid(rs.getString("guid"));
			notice.setNotifytype(rs.getString("notifytype"));
			notice.setNotifytime(rs.getObject("notifytime", OffsetDateTime.class));
			notice.setIsviewed(rs.getInt("isviewed"));
			notice.setReceiverguid(rs.getString("receiverguid"));
			notice.setNotifycontent(rs.getString("notifycontent"));
			notice.setNotifycategory(rs.getString("notifycategory"));
			notice.setSenderguid(rs.getString("senderguid"));
			notice.setCompanyguid(rs.getString("companyguid"));
			extResult.getNotices().add(notice);
		}, configCompanyGuid);
		if(StringUtils.hasText(nOfCustomFieldGuid)) {
			jdbcTemplate.query(String.format("SELECT * FROM PICKLIST_CONTENT WHERE CUSTOMFIELD_GUID IN (%s)", nOfCustomFieldGuid), rs -> {
				Picklistcontent picklistcontent = new Picklistcontent();
				picklistcontent.setGuid(rs.getString("guid"));
				picklistcontent.setContent(rs.getString("content"));
				picklistcontent.setCustomfield_guid(rs.getString("customfield_guid"));
				picklistcontent.setSortorder(rs.getInt("sortorder"));
				extResult.getPicklistcontents().add(picklistcontent);
			});
		}
		jdbcTemplate.query(String.format("SELECT * FROM TOKEN WHERE USERGUID IN (%s)", nOfAccountGuid), rs -> {
			Token token = new Token();
			token.setToken(rs.getString("token"));
			token.setUserguid(rs.getString("userguid"));
			token.setCreatedtime(rs.getObject("createdtime", OffsetDateTime.class));
			token.setExpiredtime(rs.getObject("expiredtime", OffsetDateTime.class));
			token.setInvalidtime(rs.getObject("invalidtime", OffsetDateTime.class));
			token.setIp(rs.getString("ip"));
			token.setDeviceid(rs.getString("deviceid"));
			token.setPlatform(rs.getString("platform"));
			token.setPlatformcategory(rs.getString("platformcategory"));
			token.setHostname(rs.getString("hostname"));
			extResult.getTokens().add(token);
		});
		jdbcTemplate.query(String.format("SELECT * FROM CATEGORY WHERE OWNERGUID IN (%s) AND ISDELETED=0", nOfAccountGuid), rs -> {
			Category category = new Category();
			category.setGuid(rs.getString("guid"));
			category.setName(rs.getString("name"));
			category.setCategorytype(rs.getString("categorytype"));
			category.setDisplayorder(rs.getInt("displayorder"));
			category.setContactcount(rs.getInt("contactcount"));
			category.setOwnerguid(rs.getString("ownerguid"));
			category.setUpdatetime(rs.getObject("updatetime", OffsetDateTime.class));
			category.setIsdeleted(rs.getInt("isdeleted"));
			category.setSecretary_account_guid(rs.getString("secretary_account_guid"));
			category.setInheritecategoryguid(rs.getString("inheritecategoryguid"));
			category.setParentcategoryguid(rs.getString("parentcategoryguid"));
			category.setIsusual(rs.getInt("isusual"));
			extResult.getCategories().add(category);
		});

		Files.write(exportJsonFile, gson.toJson(extResult).getBytes(StandardCharsets.UTF_8));

		int queryLimit = 100;
		int currentOffset = 0;
		boolean hasNext = true;
		int contactSizePerJson = 100;
		int contactJsonIndex = 0;
		Path contactJsonPath = workingDir.resolve(String.format("%s.json", contactJsonIndex));
		List<Contact> contacts = new ArrayList<>();

		while(hasNext) {
			AtomicInteger count = new AtomicInteger();
			jdbcTemplate.query("SELECT * FROM CONTACT WHERE COMPANYGUID=? ORDER BY CREATETIME ASC LIMIT ? OFFSET ?", rs -> {
				count.incrementAndGet();
				contacts.add(
						Contact.builder()
								.guid(rs.getString("guid"))
								.version(rs.getInt("version"))
								.note(rs.getString("note"))
								.birthday(rs.getString("birthday"))
								.uniformnumber(rs.getString("uniformnumber"))
								.nickname(rs.getString("nickname"))
								.recoglanguagefront(rs.getString("recoglanguagefront"))
								.recoglanguageback(rs.getString("recoglanguageback"))
								.createtime(rs.getString("createtime"))
								.modifytime(rs.getString("modifytime"))
								.iscorrected(rs.getInt("iscorrected"))
								.isdeleted(rs.getInt("isdeleted"))
								.textsha1(rs.getString("textsha1"))
								.ownerguid(rs.getString("ownerguid"))
								.creatorguid(rs.getString("creatorguid"))
								.modifytimefordisplay(rs.getString("modifytimefordisplay"))
								.accountscanviewsha1(rs.getString("accountscanviewsha1"))
								.editorguid(rs.getString("editorguid"))
								.modifytimeforcrmsync(rs.getString("modifytimeforcrmsync"))
								.modifytimeforcontactserversync(rs.getString("modifytimeforcontactserversync"))
								.modifytimeforsearch(rs.getLong("modifytimeforsearch"))
								.modifytimeinsearch(rs.getLong("modifytimeinsearch"))
								.companyguid(rs.getString("companyguid"))
								.isvertify(rs.getInt("isvertify"))
								.build()
				);
			}, configCompanyGuid, queryLimit, currentOffset);
			if(count.get() < queryLimit) {
				hasNext = false;
			}
			if(contacts.size() >= contactSizePerJson){
				executeDetail(contacts);
				Files.write(contactJsonPath, gson.toJson(contacts).getBytes(StandardCharsets.UTF_8));
				contacts.clear();
				contactJsonPath = workingDir.resolve(String.format("%s.json", ++contactJsonIndex));
			}

			if(hasNext){
				currentOffset += queryLimit;
			}
		}
		if(contacts.size() > 0){
			executeDetail(contacts);
			Files.write(contactJsonPath, gson.toJson(contacts).getBytes(StandardCharsets.UTF_8));
		}
		System.out.println("directory: " + workingDir.toAbsolutePath());
		System.out.println(configCompanyGuid + " finished.....");
	}


	private void executeDetail(List<Contact> contacts) {
		for(Contact contact : contacts) {
			System.out.println("contact : " + contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTNAME WHERE CONTACT_GUID =?", rs -> {
					contact.getContactnames().add(
							Contactname.builder()
									.guid(rs.getString(1))
									.firstname(rs.getString(2))
									.lastname(rs.getString(3))
									.middlename(rs.getString(4))
									.prefix(rs.getString(5))
									.suffix(rs.getString(6))
									.firstnamepronunce(rs.getString(7))
									.lastnamepronunce(rs.getString(8))
									.recognizesource(rs.getString(9))
									.fieldorder(rs.getInt(10))
									.build()
					);
				}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTJOBINFO WHERE CONTACT_GUID =?", rs -> {
				contact.getContactjobinfos().add(
						Contactjobinfo.builder()
								.guid(rs.getString(1))
								.companyname(rs.getString(2))
								.companypronunce(rs.getString(3))
								.jobtitle(rs.getString(4))
								.department(rs.getString(5))
								.recognizesource(rs.getString(6))
								.fieldorder(rs.getInt(7))
								.build()
				);
			}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTPHONE WHERE CONTACT_GUID =?", rs -> {
					contact.getContactphones().add(
							Contactphone.builder()
									.guid(rs.getString(1))
									.phonevalue(rs.getString(2))
									.phonetype(rs.getString(3))
									.recognizesource(rs.getString(4))
									.fieldorder(rs.getInt(6))
									.build()
					);
				}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTADDRESS WHERE CONTACT_GUID =?", rs -> {
					contact.getContactaddress().add(
							Contactaddress.builder()
									.guid(rs.getString(1))
									.street(rs.getString(2))
									.city(rs.getString(3))
									.state(rs.getString(4))
									.zip(rs.getString(5))
									.countryname(rs.getString(6))
									.countrycode(rs.getString(7))
									.addressformat(rs.getInt(8))
									.recognizesource(rs.getString(9))
									.fieldorder(rs.getInt(12))
									.build()
					);
				}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTEMAIL WHERE CONTACT_GUID =?", rs -> {
					contact.getContactemails().add(
							Contactemail.builder()
									.guid(rs.getString(1))
									.emailValue(rs.getString(2))
									.emailtype(rs.getString(3))
									.recognizesource(rs.getString(4))
									.fieldorder(rs.getInt(6))
									.build()
					);
				}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTURL WHERE CONTACT_GUID =?", rs -> {
				contact.getContacturls().add(
						Contacturl.builder()
								.guid(rs.getString(1))
								.urlvalue(rs.getString(2))
								.urltype(rs.getString(3))
								.recognizesource(rs.getString(4))
								.fieldorder(rs.getInt(6))
								.build()
				);
			}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTIM WHERE CONTACT_GUID =?", rs -> {
				contact.getContactims().add(
						Contactim.builder()
								.guid(rs.getString(1))
								.imvalue(rs.getString(2))
								.imtype(rs.getString(3))
								.recognizesource(rs.getString(4))
								.fieldorder(rs.getInt(6))
								.build()
				);
			}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTSOCIAL WHERE CONTACT_GUID =?", rs -> {
				contact.getContactsocials().add(
						Contactsocial.builder()
								.guid(rs.getString(1))
								.socialvalue(rs.getString(2))
								.socialtype(rs.getString(3))
								.recognizesource(rs.getString(4))
								.fieldorder(rs.getInt(6))
								.build()
				);
			}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTDATE WHERE CONTACT_GUID =?", rs -> {
				contact.getContactdates().add(
						Contactdate.builder()
								.guid(rs.getString(1))
								.datevalue(rs.getString(2))
								.datetype(rs.getString(3))
								.fieldorder(rs.getInt(5))
								.build()
				);
			}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTCUSTOMDATA WHERE CONTACT_GUID =?", rs -> {
				Contactcustomdata custom = Contactcustomdata.builder()
						.guid(rs.getString(1))
						.fieldtype(rs.getString(5))
						.fieldsettingguid(rs.getString(6))
						.build();
				CustomFieldContactAttribute fieldType = CustomFieldContactAttribute.valueOf(custom.getFieldtype());
				if(fieldType.equals(CustomFieldContactAttribute.DATE) || fieldType.equals(CustomFieldContactAttribute.DATE_TIME)) {
					custom.setDatetimevalue(rs.getString(8));
				}
				if(fieldType.equals(CustomFieldContactAttribute.EMAIL)
						|| fieldType.equals(CustomFieldContactAttribute.PICKLIST)
						|| fieldType.equals(CustomFieldContactAttribute.TEXT)
						|| fieldType.equals(CustomFieldContactAttribute.URL)) {
					custom.setTextvalue(rs.getString(4));
				}
				if(fieldType.equals(CustomFieldContactAttribute.NUMBER)) {
					custom.setIntvalue((long) rs.getInt(3));
				}
				if(fieldType.equals(CustomFieldContactAttribute.FLOAT)) {
					custom.setFloatvalue(rs.getDouble(7));
				}
				contact.getContactcustomdata().add(custom);
			}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM ACCOUNT_CAN_VIEW_CONTACT WHERE CONTACT_GUID =?", rs -> {
				contact.getViewers().add(rs.getString(1));
			}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CATEGORY_HAS_CONTACT WHERE CONTACT_GUID =?", rs -> {
				contact.getCategories().add(rs.getString(1));
			}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTIMAGE WHERE CONTACT_GUID =?", rs -> {
					String stringTypeOf = rs.getString(3);
					if(stringTypeOf == null) return ;
					ContactImageType type = ContactImageType.valueOf(stringTypeOf);
					String guid = rs.getString(1);
					byte[] data = rs.getBytes(8);
					Contactimage image = Contactimage.builder()
							.guid(guid)
							.imagetype(stringTypeOf)
							.createtime(rs.getString(4))
							.updatetime(rs.getString(5))
							.sha1(rs.getString(6))
							.isdeleted(rs.getBoolean(9))
							.build();
					contact.getContactimages().add(image);
					if(ContactImageType.LOGO.equals(type) && data != null) {
						image.setContentdatapath(FileWrite(guid, data));
					}
					if(ContactImageType.FRONT.equals(type) && data != null) {
						image.setContentdatapath(FileWrite(guid, data));
					}
					if(ContactImageType.REAR.equals(type) && data != null) {
						image.setContentdatapath(FileWrite(guid, data));
					}
				}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTFULLTEXT WHERE CONTACT_GUID =?", rs -> {
				contact.setFulltext(rs.getString(2));
			}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTSIMPLEINFO WHERE CONTACT_GUID =?", rs -> {
				contact.setSimpleInfo(
						Contactsimpleinfo.builder()
								.guid(rs.getString(1))
								.fullnameeastlastwestlast(rs.getString(2))
								.company(rs.getString(3))
								.department(rs.getString(4))
								.jobtitle(rs.getString(5))
								.createtime(rs.getString(6))
								.fullnameeastfirstwestfirst(rs.getString(8))
								.fullnameeastfirstwestlast(rs.getString(9))
								.fullnameeastlastwestfirst(rs.getString(10))
								.build()
				);
			}, contact.getGuid());
			jdbcTemplate.query("SELECT * FROM CONTACTSTATUSFORACCOUNT WHERE CONTACT_GUID =?", rs -> {
					contact.getStatus().add(rs.getString(1));
					contact.getStatusDetail().add(
							Contactstatusforaccount.builder()
									.guid(rs.getString(1))
									.categorysha1(rs.getString(2))
									.categoryguids(rs.getString(3))
									.isdeletedfromaccount(rs.getBoolean(4))
									.statusupdatetime(rs.getString(5))
									.contactguid(rs.getString(6))
									.accountguid(rs.getString(7))
									.exporttosalesforceid(rs.getString(8))
									.exporttosugarcrmid(rs.getString(9))
									.salesforceaccount(rs.getString(10))
									.sugarcrmaccount(rs.getString(11))
									.exporttosalesforceleadid(rs.getString(12))
									.modifyTimeForCrmSync(rs.getString(13))
									.previousModifiedTimeForSalesforceContact(rs.getString(14))
									.previousModifiedTimeForSalesforceLead(rs.getString(15))
									.exchangeaccount(rs.getString(16))
									.exporttoexchangeid(rs.getString(17))
									.previousModifiedTimeForExchange(rs.getString(18))
									.modifyTimeForContactServerSync(rs.getString(19))
									.office365account(rs.getString(20))
									.exporttooffice365id(rs.getString(21))
									.previousModifiedTimeForOffice365(rs.getString(22))
									.crmexportfailedreason(rs.getString(23))
									.build()
					);
				}, contact.getGuid());
		}
	}

	private String configure() {
		final String sql = "SELECT * FROM GLOBALINFOCOMPANY WHERE CONFIGKEY = ?";
		String configCompanyGuid = companyId;
		if(!StringUtils.hasText(companyId) && !StringUtils.hasText(serviceId)) {
			throw new NullPointerException("without company guid and service id.");
		}
		if(StringUtils.hasText(serviceId)) {
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, "SUBSCRIBE_SERVICEID");
			if(rs.next() && serviceId.equals(rs.getString(3))) {
				configCompanyGuid = rs.getString(4);
			}
		}
		return configCompanyGuid;
	}

	private String FileWrite(String guid, byte[] data) {
		final String fileName = String.format("%s.jpg", guid);
		FileWrite(workingDir.resolve(fileName), data);
		return fileName;
	}

	public static void FileWrite(Path path, byte[] bytes) {
		try {
			Files.write(workingDir.resolve(path), bytes);
		} catch (IOException e) {
			log.error("Path {}", path.toString());
			e.printStackTrace();
		}
	}
}
