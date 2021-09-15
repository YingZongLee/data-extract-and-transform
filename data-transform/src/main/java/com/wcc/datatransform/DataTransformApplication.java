package com.wcc.datatransform;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wcc.dataextract.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
public class DataTransformApplication implements CommandLineRunner {

	private final String filename = "export.json";
	private Gson gson;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private MongoTemplate mongoTemplate;

	@PostConstruct
	public void init() {
		gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	}
	public static void main(String[] args) {
		SpringApplication.run(DataTransformApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String directory = Arrays.stream(args).findFirst().orElseThrow(() -> new NullPointerException("Directory not found."));
		System.out.println("dataSource: " + jdbcTemplate.getDataSource().getConnection().getMetaData().getURL());
		try(Reader reader = Files.newBufferedReader(Paths.get(directory).resolve(filename))) {
			ExportResult result = gson.fromJson(reader, ExportResult.class);
			updateDevice(result.getDevice());
			updateCompany(result.getCompany());
			updateGlobalInfoCompany(result.getGlobalinfocompanies());
			updateCustomField(result.getCustomfields());
			updatePickingList(result.getPicklistcontents());
			updateAccounts(result.getAccounts());
			updateBoss(linkedAccount(result.getAccounts()));
			updateAccountBindingDeviceInfos(result.getAccountbindingdeviceinfos());
			updateAccountLoginRecords(result.getAccountloginrecords());
			updateAccountMyCardSettings(result.getAccountmycardsettings(), directory);
			updateAccountSecuritySettings(result.getAccountsecuritysettings());
			updateAccountShareTargetSettings(result.getAccountsharetargetsettings());
			updateAccountPrivateSettings(result.getAccountprivatesettings());
			updateAccountShare2Items(result.getAccountsharetoaccountitems());
			updateForgetPasswordSession(result.getForgetpasswordsessions());
			updateLoginFailInfos(result.getLoginfailedinfos());
			updateNotices(result.getNotices());
			updateTokens(result.getTokens());
			updateCategory(result.getCategories());
		}
		catch (Exception e) {
//			StackTraceElement[] stackTrace = e.getStackTrace();
//			Arrays.stream(stackTrace).forEach(stackTraceElement -> log.info("||||||||||| {} |||||||||||", stackTraceElement.toString()));
//			log.error(stackTrace[7].toString());
			throw e;
//			e.printStackTrace();
		}
	}

	private void updateCategory(List<Category> categories) {
		for(Category category : categories) {

		}
	}

	private void updateTokens(List<Token> tokens) throws Exception {
		final String sql = "" +
				"	INSERT INTO TOKEN VALUES (" +
				"    :TOKEN, :USERGUID, :CREATEDTIME, :EXPIREDTIME, :INVALIDTIME, :IP, :DEVICEID," +
				"    :PLATFORM, :PLATFORMCATEGORY, :HOSTNAME" +
				"	)" +
				"	ON CONFLICT ON CONSTRAINT IDX_TOKEN_PRIMARY" +
				"	DO UPDATE SET USERGUID =:USERGUID, CREATEDTIME =:CREATEDTIME, EXPIREDTIME =:EXPIREDTIME," +
				"    INVALIDTIME =:INVALIDTIME, IP =:IP, DEVICEID =:DEVICEID, PLATFORM =:PLATFORM," +
				"    PLATFORMCATEGORY =:PLATFORMCATEGORY, HOSTNAME =:HOSTNAME;";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Token token : tokens) {
			parameters.add(new MapSqlParameterSource()
					.addValue("TOKEN", token.getToken())
					.addValue("USERGUID", token.getUserguid())
					.addValue("CREATEDTIME", token.getCreatedtime())
					.addValue("EXPIREDTIME", token.getExpiredtime())
					.addValue("INVALIDTIME", token.getInvalidtime())
					.addValue("IP", token.getIp())
					.addValue("DEVICEID", token.getDeviceid())
					.addValue("PLATFORM", token.getPlatform())
					.addValue("PLATFORMCATEGORY", token.getPlatformcategory())
					.addValue("HOSTNAME", token.getHostname())
			);
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != tokens.size()) {
			throw new Exception();
		}
	}

	private void updateNotices(List<Notice> notices) throws Exception {
		final String sql = "" +
				"	INSERT INTO NOTICE VALUES (" +
				"   	:GUID, :NOTIFYTYPE, :NOTIFYTIME, :ISVIEWED, :RECEIVERGUID, :NOTIFYCONTENT, :NOTIFYCATEGORY," +
				"   	:SENDERGUID, :COMPANYGUID" +
				"	)" +
				"	ON CONFLICT ON CONSTRAINT IDX_NOTICE_PRIMARY" +
				"	DO UPDATE SET NOTIFYTYPE =:NOTIFYTYPE, NOTIFYTIME =:NOTIFYTIME, ISVIEWED =:ISVIEWED, " +
				"      	RECEIVERGUID =:RECEIVERGUID, NOTIFYCONTENT =:NOTIFYCONTENT, NOTIFYCATEGORY =:NOTIFYCATEGORY," +
				"      	SENDERGUID =:SENDERGUID, COMPANYGUID =:COMPANYGUID";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Notice notice : notices) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", notice.getGuid())
					.addValue("NOTIFYTYPE", notice.getNotifytype())
					.addValue("NOTIFYTIME", notice.getNotifytime())
					.addValue("ISVIEWED", notice.getIsviewed())
					.addValue("RECEIVERGUID", notice.getReceiverguid())
					.addValue("NOTIFYCONTENT", notice.getNotifycontent())
					.addValue("NOTIFYCATEGORY", notice.getNotifycategory())
					.addValue("SENDERGUID", notice.getSenderguid())
					.addValue("COMPANYGUID", notice.getCompanyguid())
			);
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != notices.size()) {
			throw new Exception();
		}
	}

	private void updateLoginFailInfos(List<Loginfailedinfo> infos) throws Exception {
		final String sql = "" +
				"	INSERT INTO LOGINFAILEDLINFO VALUES (:GUID, :IPADDR, :LASTIME, :ACCOUNT, :COUNT, :TYPE)" +
				"	ON CONFLICT ON CONSTRAINT IDX_LOGINFAILEDLINFO_PRIMARY" +
				"	DO UPDATE SET IPADDRESS =:IPADDR, LASTFAILURETIME =:LASTIME, USERACCOUNT =:ACCOUNT," +
				"             FAILURECOUNT =:COUNT, FAILURETYPE =:TYPE";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Loginfailedinfo info : infos) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", info.getGuid())
					.addValue("IPADDR", info.getIpaddress())
					.addValue("LASTIME", info.getLastfailuretime())
					.addValue("ACCOUNT", info.getUseraccount())
					.addValue("COUNT", info.getFailurecount())
					.addValue("TYPE", info.getFailuretype())
			);
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != infos.size()) {
			throw new Exception();
		}
	}

	private void updateForgetPasswordSession(List<Forgetpasswordsession> sessions) throws Exception {
		final String sql = "" +
				"	INSERT INTO FORGET_PASSWORD_SESSION VALUES (:GUID, :EMAIL, :TOKEN, :CREATEDATE)" +
				"	ON CONFLICT ON CONSTRAINT IDX_FORGET_PASSWORD_SESSION_PRIMARY" +
				"	DO UPDATE SET EMAIL =:EMAIL, TOKEN =:TOKEN, CREATEDATE =:CREATEDATE";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Forgetpasswordsession session : sessions) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", session.getGuid())
					.addValue("EMAIL", session.getEmail())
					.addValue("TOKEN", session.getToken())
					.addValue("CREATEDATE", session.getCreatedate())
			);
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != sessions.size()) {
			throw new Exception();
		}
	}

	private void updateAccountShare2Items(List<Accountsharetoaccountitem> sharing) throws Exception {
		final String sql = "" +
				"	INSERT INTO ACCOUNT_SHARE_TO_ACCOUNT_ITEMS VALUES (:GUID, :SHAREDGUID, :BESHAREDGUID, :ITEMGUID, :ITEMTYPE)" +
				"		ON CONFLICT ON CONSTRAINT IDX_ACCOUNT_SHARE_TO_ACCOUNT_ITEMS_PRIMARY" +
				"	DO UPDATE SET GUID =:GUID, SHARE_ACCOUNT_GUID =:SHAREDGUID, BESHARED_ACCOUNT_GUID =:BESHAREDGUID," +
				"              ITEM_GUID =:ITEMGUID, ITEM_TYPE =:ITEMTYPE";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Accountsharetoaccountitem item : sharing) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", item.getGuid())
					.addValue("SHAREDGUID", item.getShare_account_guid())
					.addValue("BESHAREDGUID", item.getBeshared_account_guid())
					.addValue("ITEMGUID", item.getItem_guid())
					.addValue("ITEMTYPE", item.getItem_type())
			);
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != sharing.size()) {
			throw new Exception();
		}
	}

	private void updateAccountPrivateSettings(List<Accountprivatesetting> settings) throws Exception {
		final String sql = "" +
				"	INSERT INTO ACCOUNT_PRIVATE_SETTINGS (" +
				"    GUID, MAPENGINE, WESTERNSURNAMEORDER, ASIANSURNAMEORDER, AUTOSHARE," +
				"    LASTNOTICECOUNTVIEW, SORTINGORDER, IDENTIFICATIONRESULT, USERSTOREDCRMTYPE," +
				"    CRMEXPORTMODE, USERCRMACCOUNT, CRMLOGINTOKEN, MODIFYTIME," +
				"    CRMSYNCLASTCOMPLETEDTIMEFORWCT, CRMSYNCLASTCOMPLETEDTIMEFORCRM," +
				"    LASTDEEPLYDELETEDCONTACTTIME, CONTACTSERVERTYPE, CONTACTSERVEREXPORTMODE," +
				"    EXCHANGEAUTHINFO, OFFICE365AUTHINFO, CONTACTSERVERSYNCLASTCOMPLETEDTIMEFORWCT," +
				"    CONTACTSERVERSYNCLASTCOMPLETEDTIMEFORCONTACTSERVER, OFFICE365DELTALINKFORSYNC," +
				"    ISADDNOTEINFOAFTERRECOG, ISEDITAFTERRECOG, CRMCOMPANYASSIGNMENTOPTION," +
				"    LEADSYNCLASTCOMPLETEDTIMEFORWCT, LEADSYNCLASTCOMPLETEDTIMEFORLEAD, ACCOUNT_GUID" +
				"	)" +
				"	VALUES (" +
				"    :GUID, :MAPENGINE, :WESTERNSURNAMEORDER, :ASIANSURNAMEORDER, :AUTOSHARE," +
				"    :LASTNOTICECOUNTVIEW, :SORTINGORDER, :IDENTIFICATIONRESULT, :USERSTOREDCRMTYPE," +
				"    :CRMEXPORTMODE, :USERCRMACCOUNT, :CRMLOGINTOKEN, :MODIFYTIME," +
				"    :CRMSYNCLASTCOMPLETEDTIMEFORWCT, :CRMSYNCLASTCOMPLETEDTIMEFORCRM," +
				"    :LASTDEEPLYDELETEDCONTACTTIME, :CONTACTSERVERTYPE, :CONTACTSERVEREXPORTMODE," +
				"    :EXCHANGEAUTHINFO, :OFFICE365AUTHINFO, :CONTACTSERVERSYNCLASTCOMPLETEDTIMEFORWCT," +
				"    :CONTACTSERVERSYNCLASTCOMPLETEDTIMEFORCONTACTSERVER, :OFFICE365DELTALINKFORSYNC," +
				"    :ISADDNOTEINFOAFTERRECOG, :ISEDITAFTERRECOG, :CRMCOMPANYASSIGNMENTOPTION," +
				"    :LEADSYNCLASTCOMPLETEDTIMEFORWCT, :LEADSYNCLASTCOMPLETEDTIMEFORLEAD, :ACCOUNTGUID" +
				"	)" +
				"	ON CONFLICT ON CONSTRAINT IDX_ACCOUNT_PRIVATE_SETTINGS_PRIMARY" +
				"	DO UPDATE SET " +
				"    GUID =:GUID, MAPENGINE =:MAPENGINE, WESTERNSURNAMEORDER =:WESTERNSURNAMEORDER, " +
				"    ASIANSURNAMEORDER =:ASIANSURNAMEORDER, AUTOSHARE =:AUTOSHARE, LASTNOTICECOUNTVIEW =:LASTNOTICECOUNTVIEW, " +
				"    SORTINGORDER =:SORTINGORDER, IDENTIFICATIONRESULT =:IDENTIFICATIONRESULT, USERSTOREDCRMTYPE =:USERSTOREDCRMTYPE," +
				"    CRMEXPORTMODE =:CRMEXPORTMODE, USERCRMACCOUNT =:USERCRMACCOUNT, CRMLOGINTOKEN =:CRMLOGINTOKEN, MODIFYTIME =:MODIFYTIME," +
				"    CRMSYNCLASTCOMPLETEDTIMEFORWCT =:CRMSYNCLASTCOMPLETEDTIMEFORWCT, CRMSYNCLASTCOMPLETEDTIMEFORCRM =:CRMSYNCLASTCOMPLETEDTIMEFORCRM," +
				"    LASTDEEPLYDELETEDCONTACTTIME =:LASTDEEPLYDELETEDCONTACTTIME, CONTACTSERVERTYPE =:CONTACTSERVERTYPE, " +
				"    CONTACTSERVEREXPORTMODE =:CONTACTSERVEREXPORTMODE," +
				"    EXCHANGEAUTHINFO =:EXCHANGEAUTHINFO, OFFICE365AUTHINFO =:OFFICE365AUTHINFO," +
				"    CONTACTSERVERSYNCLASTCOMPLETEDTIMEFORWCT =:CONTACTSERVERSYNCLASTCOMPLETEDTIMEFORWCT," +
				"    CONTACTSERVERSYNCLASTCOMPLETEDTIMEFORCONTACTSERVER =:CONTACTSERVERSYNCLASTCOMPLETEDTIMEFORCONTACTSERVER," +
				"    OFFICE365DELTALINKFORSYNC =:OFFICE365DELTALINKFORSYNC," +
				"    ISADDNOTEINFOAFTERRECOG =:ISADDNOTEINFOAFTERRECOG, ISEDITAFTERRECOG =:ISEDITAFTERRECOG, " +
				"    CRMCOMPANYASSIGNMENTOPTION =:CRMCOMPANYASSIGNMENTOPTION," +
				"    LEADSYNCLASTCOMPLETEDTIMEFORWCT =:LEADSYNCLASTCOMPLETEDTIMEFORWCT," +
				"    LEADSYNCLASTCOMPLETEDTIMEFORLEAD =:LEADSYNCLASTCOMPLETEDTIMEFORLEAD, ACCOUNT_GUID =:ACCOUNTGUID";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Accountprivatesetting setting : settings) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", setting.getGuid())
					.addValue("MAPENGINE", setting.getMapengine())
					.addValue("WESTERNSURNAMEORDER", setting.getWesternsurnameorder())
					.addValue("ASIANSURNAMEORDER", setting.getAsiansurnameorder())
					.addValue("AUTOSHARE", setting.getAutoshare())
					.addValue("LASTNOTICECOUNTVIEW", setting.getLastnoticecountview())
					.addValue("SORTINGORDER", setting.getSortingorder())
					.addValue("IDENTIFICATIONRESULT", setting.getIdentificationresult())
					.addValue("USERSTOREDCRMTYPE", setting.getUserstoredcrmtype())
					.addValue("CRMEXPORTMODE", setting.getCrmexportmode())
					.addValue("USERCRMACCOUNT", setting.getUsercrmaccount())
					.addValue("CRMLOGINTOKEN", setting.getCrmlogintoken())
					.addValue("MODIFYTIME", setting.getModifytime())
					.addValue("CRMSYNCLASTCOMPLETEDTIMEFORWCT", setting.getCrmsynclastcompletedtimeforwct())
					.addValue("CRMSYNCLASTCOMPLETEDTIMEFORCRM", setting.getCrmsynclastcompletedtimeforcrm())
					.addValue("LASTDEEPLYDELETEDCONTACTTIME", setting.getLastdeeplydeletedcontacttime())
					.addValue("CONTACTSERVERTYPE", setting.getContactservertype())
					.addValue("CONTACTSERVEREXPORTMODE", setting.getContactserverexportmode())
					.addValue("EXCHANGEAUTHINFO", setting.getExchangeauthinfo())
					.addValue("OFFICE365AUTHINFO", setting.getOffice365authinfo())
					.addValue("CONTACTSERVERSYNCLASTCOMPLETEDTIMEFORWCT", setting.getContactserversynclastcompletedtimeforwct())
					.addValue("CONTACTSERVERSYNCLASTCOMPLETEDTIMEFORCONTACTSERVER", setting.getContactserversynclastcompletedtimeforcontactserver())
					.addValue("OFFICE365DELTALINKFORSYNC", setting.getOffice365deltalinkforsync())
					.addValue("ISADDNOTEINFOAFTERRECOG", setting.getIsaddnoteinfoafterrecog())
					.addValue("ISEDITAFTERRECOG", setting.getIseditafterrecog())
					.addValue("CRMCOMPANYASSIGNMENTOPTION", setting.getCrmcompanyassignmentoption())
					.addValue("LEADSYNCLASTCOMPLETEDTIMEFORWCT", setting.getLeadsynclastcompletedtimeforwct())
					.addValue("LEADSYNCLASTCOMPLETEDTIMEFORLEAD", setting.getLeadsynclastcompletedtimeforlead())
					.addValue("ACCOUNTGUID", setting.getAccount_guid())
			);
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != settings.size()) {
			throw new Exception();
		}
	}

	private void updateAccountShareTargetSettings(List<Accountsharetargetsetting> settings) throws Exception {
		final String sql = "" +
				"	INSERT INTO ACCOUNTSHARETARGETSETTING VALUES (:GUID, :SHAREITEM, :SHAREDACCOUNTGUID, :ACCOUNTGUID)" +
				"    ON CONFLICT ON CONSTRAINT IDX_ACCOUNT_SHARE_TARGET_SETTING_PRIMARY" +
				"	DO UPDATE SET SHAREITEM = :SHAREITEM, SHAREDACCOUNTGUID = :SHAREDACCOUNTGUID, ACCOUNT_GUID = :ACCOUNTGUID";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Accountsharetargetsetting setting : settings) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", setting.getGuid())
					.addValue("SHAREITEM", setting.getShareitem())
					.addValue("SHAREDACCOUNTGUID", setting.getSharedaccountguid())
					.addValue("ACCOUNTGUID", setting.getAccount_guid())
			);
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != settings.size()) {
			throw new Exception();
		}
	}

	private void updateAccountSecuritySettings(List<Accountsecuritysetting> settings) throws Exception {
		final String sql = "" +
				"	INSERT INTO ACCOUNTSECURITYSETTING VALUES (" +
				"   	:GUID, :IOS, :ANDROID, :WINS, :MAC, :WEB," +
				"   	:IOSBIND, :ANDROIDBIND, :WINSBIND, :MACBIND, :ACCOUNTGUID" +
				"	)" +
				"   	ON CONFLICT ON CONSTRAINT IDX_ACCOUNT_SECURITY_SETTING_PRIMARY" +
				"	DO UPDATE SET IOSENABLE = :IOS, ANDROIDENABLE = :ANDROID, WINDOWSENABLE = :WINS, MACENABLE = :MAC," +
				"    	WEBENABLE = :WEB, IOSBINDINGENABLE = :IOSBIND, ANDROIDBINDINGENABLE = :ANDROIDBIND," +
				"   	WINDOWSBINDINGENABLE = :WINSBIND, MACBINDINGENABLE = :MACBIND, ACCOUNT_GUID = :ACCOUNTGUID";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Accountsecuritysetting setting : settings) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", setting.getGuid())
					.addValue("IOS", setting.getIosenable())
					.addValue("ANDROID", setting.getAndroidenable())
					.addValue("WINS", setting.getWindowsenable())
					.addValue("MAC", setting.getMacenable())
					.addValue("WEB", setting.getWebenable())
					.addValue("IOSBIND", setting.getIosbindingenable())
					.addValue("ANDROIDBIND", setting.getAndroidbindingenable())
					.addValue("WINSBIND", setting.getWindowsbindingenable())
					.addValue("MACBIND", setting.getMacbindingenable())
					.addValue("ACCOUNTGUID", setting.getAccount_guid())
			);
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != settings.size()) {
			throw new Exception();
		}
	}

	private void updateAccountMyCardSettings(List<Accountmycardsetting> settings, String directory) throws Exception {
		final String sql = "" +
				"	INSERT INTO ACCOUNTMYCARDSETTING" +
				"    (GUID, ISENABLE, MYCARDINFO, LOGOIMAGEDATA, FRONTIMAGEDATA, ACCOUNT_GUID)" +
				"	VALUES (:GUID, :ISENABLE, :MYCARDINFO, :LOGO, :FRONT, :ACCOUNTGUID)" +
				"    ON CONFLICT ON CONSTRAINT IDX_ACCOUNT_MYCARD_SETTING_PRIMAY" +
				"	DO UPDATE SET ISENABLE =:ISENABLE, MYCARDINFO = :MYCARDINFO, LOGOIMAGEDATA = :LOGO," +
				"    FRONTIMAGEDATA = :FRONT, ACCOUNT_GUID = :ACCOUNTGUID";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Accountmycardsetting setting : settings) {
			MapSqlParameterSource source = new MapSqlParameterSource()
					.addValue("GUID", setting.getGuid())
					.addValue("ISENABLE", setting.getIsenable())
					.addValue("MYCARDINFO", setting.getMycardinfo())
					.addValue("ACCOUNTGUID", setting.getAccount_guid())
					.addValue("LOGO", null)
					.addValue("FRONT", null);
			if(StringUtils.hasText(setting.getLogoimagedatapath())) {
				log.info("my card logo path {}", Paths.get(directory, setting.getLogoimagedatapath()).toString());
				source.addValue("LOGO", toByteArray(Paths.get(directory, setting.getLogoimagedatapath())), Types.BINARY);
			}
			if(StringUtils.hasText(setting.getFrontimagedatapath())) {
				log.info("my card front path {}", Paths.get(directory, setting.getFrontimagedatapath()).toString());
				source.addValue("FRONT", toByteArray(Paths.get(directory, setting.getFrontimagedatapath())), Types.BINARY);
			}
			parameters.add(source);
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != settings.size()) {
			throw new Exception();
		}
	}

	private void updateAccountLoginRecords(List<Accountloginrecord> records) throws Exception {
		final String sql = "" +
				"	INSERT INTO ACCOUNTLOGINRECORD VALUES (:GUID, :LOGINTIME, :DEVICEGUID, :ACCOUNTGUID, :LOGINTIMEFORBACKUPRESTORE)" +
				"    ON CONFLICT ON CONSTRAINT IDX_ACCOUNT_LOGIN_RECORD_PIMARY" +
				"	DO UPDATE SET LOGINTIME = :LOGINTIME, DEVICE_GUID = :DEVICEGUID, ACCOUNT_GUID = :ACCOUNTGUID," +
				"              LOGINTIMEFORBACKUPRESTORE = :LOGINTIMEFORBACKUPRESTORE";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Accountloginrecord record : records) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", record.getGuid())
					.addValue("LOGINTIME", record.getLogintime())
					.addValue("DEVICEGUID", record.getDevice_guid())
					.addValue("ACCOUNTGUID", record.getAccount_guid())
					.addValue("LOGINTIMEFORBACKUPRESTORE", record.getLogintimeforbackuprestore())
			);
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != records.size()) {
			throw new Exception();
		}
	}

	private void updateAccountBindingDeviceInfos(List<Accountbindingdeviceinfo> accountbindingdeviceinfos) throws Exception {
		final String sql = "" +
				"	INSERT INTO ACCOUNTBINDINGDEVICEINFO VALUES(:GUID, :IPCONSTRAINTENABLE, :ACCOUNTGUID, :DEVICEGUID)" +
				"    ON CONFLICT ON CONSTRAINT IDX_ACCOUNT_BINDING_DEVICE_INFO_PRIMARY" +
				"	DO UPDATE SET IPCONSTRAINTENABLE = :IPCONSTRAINTENABLE, ACCOUNT_GUID = :ACCOUNTGUID, DEVICE_GUID = :DEVICEGUID";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Accountbindingdeviceinfo info : accountbindingdeviceinfos) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", info.getGuid())
					.addValue("IPCONSTRAINTENABLE", info.getIpconstraintenable())
					.addValue("ACCOUNTGUID", info.getAccount_guid())
					.addValue("DEVICEGUID", info.getDevice_guid())
			);
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != accountbindingdeviceinfos.size()) {
			throw new Exception();
		}
	}


	private void updateBoss(List<Account> accounts) throws Exception {
		final String qSql = "SELECT GUID, SEQUENCE FROM ACCOUNT WHERE COMPANY_GUID = ?";
		final String oSql = "SELECT LTREE2TEXT(RELATIONSHIP_PATH) AS ORIGINAL FROM ACCOUNT WHERE GUID = ?";
		final String uSql = "UPDATE ACCOUNT SET BOSS = ?, RELATIONSHIP_PATH = TEXT2LTREE(?) WHERE GUID = ?";
		final String rSql = "" +
				"	UPDATE ACCOUNT SET RELATIONSHIP_PATH = TEXT2LTREE(" +
				"        REGEXP_REPLACE(LTREE2TEXT(RELATIONSHIP_PATH), ?, ?)" +
				"    )" +
				"	WHERE COMPANY_GUID = ? AND RELATIONSHIP_PATH ~ CAST(? AS LQUERY)";
		String companyGuid = accounts.get(0).getCompany_guid();
		List<Map<String, Object>> sequences = jdbcTemplate.queryForList(qSql, companyGuid);
		Map<String, String> sequenceMap = sequences.stream().collect(Collectors.toMap(s-> (String)s.get("guid"), s-> String.valueOf(s.get("sequence"))));
		for(Account account : accounts) {
			String oRelation = jdbcTemplate.queryForObject(oSql, String.class, account.getGuid());
			String relation = sequenceMap.get(account.getGuid());
			String boss = account.getBoss();
			log.info("account:{}, boss:{}, oRelation:{}", account.getGuid(), account.getBoss(), oRelation);
			if(StringUtils.hasText(boss) && sequenceMap.containsKey(boss)) {
				String bossRelation = jdbcTemplate.queryForObject(oSql, String.class, boss);
				relation = String.join(".", bossRelation, relation);
			}
			int status = jdbcTemplate.update(uSql, boss, relation, account.getGuid());
			if(status != 1) {
				throw new Exception("updateBoss error");
			}
			if(!oRelation.equals(relation)) {
				jdbcTemplate.update(rSql, String.format("^%s", oRelation), relation, companyGuid, oRelation);
			}
		}
		log.info("=====================================================");
	}

	private void updateAccounts(List<Account> accounts) throws Exception {
		final String sql = "" +
				"	INSERT INTO ACCOUNT (" +
				"	  GUID, EMAIL, PASSWORD, DISPLAYNAME, CREATETIME, STATUS, ROLE, EXPORTABILITY," +
				"     COMPANY_GUID, CATEGORYORDERUPDATETIME, RESIGNDATE, INHERITDATE, INHERITEDACCOUNT," +
				"     SECRETARY, PRINTABILITY, INHERITANCESTATUS, CONTACTCOUNTINPRIVATE, ACCOUNTTYPE," +
				"     ACCOUNTSUBSCRIPTIONSTATUS, LASTPASSWORDCHANGETIME, LOCKEDISSUE, LOCKCASE, ASSISTANTGUID" +
				"	)" +
				"	VALUES (" +
				"    :GUID, :EMAIL, :PASSWORD, :DISPLAYNAME, :CREATETIME, :STATUS, :ROLE, :EXPORTABILITY," +
				"    :COMPANYGUID, :CATEGORYUPDATIME, :RESIGNDATE, :INHERITDATE, :INHERITACCOUNT," +
				"    :SECRETARY, :PRINTABILITY, :INHERITSTATUS, :CONTACTCOUNT, :ACCOUNTTYPE," +
				"    :SUBSCRIPTIONSTATUS, :LASTPASSWORDCHANGETIME, :LOCKEDISSUE, :LOCKCASE, :ASSISTANTGUID" +
				"	)" +
				"    	ON CONFLICT ON CONSTRAINT IDX_ACCOUNT_PRIMARY" +
				"	DO UPDATE SET EMAIL = :EMAIL, PASSWORD = :PASSWORD, DISPLAYNAME = :DISPLAYNAME, CREATETIME = :CREATETIME, STATUS = :STATUS," +
				"    ROLE = :ROLE, EXPORTABILITY = :EXPORTABILITY, company_guid = :COMPANYGUID, CATEGORYORDERUPDATETIME = :CATEGORYUPDATIME," +
				"    RESIGNDATE = :RESIGNDATE, INHERITDATE = :INHERITDATE, INHERITEDACCOUNT = :INHERITACCOUNT, SECRETARY = :SECRETARY, PRINTABILITY = :PRINTABILITY," +
				"    INHERITANCESTATUS = :INHERITSTATUS, CONTACTCOUNTINPRIVATE = :CONTACTCOUNT, ACCOUNTTYPE = :ACCOUNTTYPE, ACCOUNTSUBSCRIPTIONSTATUS = :SUBSCRIPTIONSTATUS," +
				"    LASTPASSWORDCHANGETIME = :LASTPASSWORDCHANGETIME, LOCKEDISSUE = :LOCKEDISSUE, LOCKCASE = :LOCKCASE, ASSISTANTGUID = :ASSISTANTGUID";
		List<SqlParameterSource> parameters = new ArrayList<>();

		for(Account account : accounts) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", account.getGuid())
					.addValue("EMAIL", account.getEmail())
					.addValue("PASSWORD", account.getPassword())
					.addValue("DISPLAYNAME", account.getDisplayname())
					.addValue("CREATETIME", account.getCreatetime())
					.addValue("STATUS", account.getStatus())
					.addValue("ROLE", account.getRole())
					.addValue("EXPORTABILITY", account.getExportability())
					.addValue("COMPANYGUID", account.getCompany_guid())
					.addValue("CATEGORYUPDATIME", account.getCategoryorderupdatetime())
					.addValue("RESIGNDATE", account.getResigndate())
					.addValue("INHERITDATE", account.getInheritdate())
					.addValue("INHERITACCOUNT", account.getInheritedaccount())
					.addValue("SECRETARY", account.getSecretary())
					.addValue("PRINTABILITY", account.getPrintability())
					.addValue("INHERITSTATUS", account.getInheritancestatus())
					.addValue("CONTACTCOUNT", account.getContactcountinprivate())
					.addValue("ACCOUNTTYPE", account.getAccounttype())
					.addValue("SUBSCRIPTIONSTATUS", account.getAccountsubscriptionstatus())
					.addValue("ACCOUNTTYPE", account.getAccounttype())
					.addValue("LASTPASSWORDCHANGETIME", account.getLastpasswordchangetime())
					.addValue("LOCKEDISSUE", account.getLockedissue())
					.addValue("LOCKCASE", account.getLockcase())
					.addValue("ASSISTANTGUID", account.getAssistantguid()));
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != accounts.size()) {
			throw new Exception();
		}
	}

	private void updatePickingList(List<Picklistcontent> picklistContents) throws Exception {
		final String sql = "" +
				"	INSERT INTO PICKLIST_CONTENT VALUES (:GUID, :CONTENT, :CUSTOMFIELDGUID, :ORDER)" +
				"    ON CONFLICT ON CONSTRAINT IDX_PICKLIST_CONTENT_PRIMAY DO" +
				"	UPDATE SET CONTENT = :CONTENT, CUSTOMFIELD_GUID = :CUSTOMFIELDGUID, SORTORDER = :ORDER;";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Picklistcontent content : picklistContents) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", content.getGuid())
					.addValue("CONTENT", content.getContent())
					.addValue("CUSTOMFIELDGUID", content.getCustomfield_guid())
					.addValue("ORDER", content.getSortorder()));
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != picklistContents.size()) {
			throw new Exception();
		}
	}
	private void updateCustomField(final List<Customfield> customFields) throws Exception {
		final String sql = "" +
				"	INSERT INTO CUSTOMFIELD VALUES (:GUID, :CATEGORY, :NAME, :ATTRIBUTE, :ORDER, :COMPANYGUID)" +
				"   	ON CONFLICT ON CONSTRAINT IDX_CUSTOMFIELD_PRIMARY" +
				"	DO UPDATE SET CUSTOMFIELDCATEGORY = :CATEGORY, CUSTOMFIELDNAME = :NAME," +
				"      CUSTOMFIELDCONTACTATTRIBUTE = :ATTRIBUTE, SORTORDER = :ORDER," +
				"      COMPANYGUID = :COMPANYGUID";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Customfield field : customFields) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", field.getGuid())
					.addValue("CATEGORY", field.getCustomfieldcategory())
					.addValue("NAME", field.getCustomfieldname())
					.addValue("ATTRIBUTE", field.getCustomfieldcontactattribute())
					.addValue("ORDER", field.getSortorder())
					.addValue("COMPANYGUID", field.getCompanyguid()));
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != customFields.size()) {
			throw new Exception();
		}
	}
	private void updateGlobalInfoCompany(final List<Globalinfocompany> companies) throws Exception {
		final String sql = "" +
				"INSERT INTO GLOBALINFOCOMPANY VALUES (:GUID, :CONFIGKEY, :CONFIGVALUE, :COMPANYGUID)" +
				"   ON CONFLICT ON CONSTRAINT IDX_GLOBALINFOCOMPANY_PRIMARY " +
				"DO UPDATE SET CONFIGKEY = :CONFIGKEY, CONFIGVALUE = :CONFIGVALUE, COMPANYGUID = :COMPANYGUID";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Globalinfocompany company : companies) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", company.getGuid())
					.addValue("CONFIGKEY", company.getConfigkey())
					.addValue("CONFIGVALUE", company.getConfigvalue())
					.addValue("COMPANYGUID", company.getCompanyguid()));
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		if(Arrays.stream(status).sum() != companies.size()) {
			throw new Exception();
		}
	}
	private void updateCompany(final Company company) throws Exception {
		final String sql = "" +
				"INSERT INTO COMPANY VALUES (:GUID, :NAME, :TELPHONE)" +
				"	ON CONFLICT ON CONSTRAINT IDX_COMPANY_PRIMARY " +
				"DO UPDATE SET NAME = :NAME, TELPHONE = :TELPHONE;";

		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("GUID", company.getGuid())
				.addValue("NAME", company.getName())
				.addValue("TELPHONE", company.getTelphone());
		int status = namedParameterJdbcTemplate.update(sql, namedParameters);
		if(status == 0) {
			throw new Exception();
		}
	}

	private void updateDevice(List<Device> devices) throws Exception {
		final String sql = "INSERT INTO DEVICE VALUES (:GUID, :NAME, :PLATFORM) ON CONFLICT ON CONSTRAINT IDX_DEVICE_PRIMARY DO NOTHING";
		List<SqlParameterSource> parameters = new ArrayList<>();
		for(Device device : devices) {
			parameters.add(new MapSqlParameterSource()
					.addValue("GUID", device.getGuid())
					.addValue("NAME", device.getName())
					.addValue("PLATFORM", device.getPlatform()));
		}
		int[] status = namedParameterJdbcTemplate.batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
//		if(Arrays.stream(status).sum() != devices.size()) {
//			throw new Exception();
//		}
	}

	private List<Account> linkedAccount(List<Account> accounts) {
		LinkedList<Account> linkedAccount = new LinkedList<>();
		for (Account account : accounts) {
			recursiveAccount(account, linkedAccount, accounts);
		}
		log.info("=====================================================");
		linkedAccount.forEach(a -> log.info("account:{}, boss:{}", a.getGuid(), a.getBoss()));
		log.info("=====================================================");
		return linkedAccount;
	}

	private void recursiveAccount(Account account, LinkedList<Account> linkedAccount, List<Account> originAccount) {
		Account origin = account;
		if(!linkedAccount.contains(origin)) linkedAccount.add(origin);
		String imBossGuid = origin.getBoss();
		if(StringUtils.hasText(imBossGuid)) {
			int position = linkedAccount.indexOf(origin);
			String finalBossGuid = imBossGuid;
			Account finalBossAccount = originAccount.stream().filter(acct-> acct.getGuid().equals(finalBossGuid)).findFirst().orElse(null);
			if(finalBossAccount != null && !linkedAccount.contains(finalBossAccount)) {
				linkedAccount.add(position, finalBossAccount);
				origin = finalBossAccount;
				recursiveAccount(origin, linkedAccount, originAccount);
			}
		}
	}

	private byte[] toByteArray(final Path path) {
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void testPSQL() {
		Integer result = jdbcTemplate.queryForObject("select 1", Integer.class);
		log.info("testPSQL: {}", result);
	}
}
