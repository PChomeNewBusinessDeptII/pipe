package com.yiabi.puppy.restful.common;

public class ServiceConstant
{
	// proj package name
	public static final String PROJ_NAME = "pus";
	
	// top path
	public static final String YIABI_DATA_TOP_PATH = "/yiabiRoot";
	
	// sub folder
	public static final String YIABI_EBOOKLIB_DIR = YIABI_DATA_TOP_PATH + "/ebooklib";
	public static final String YIABI_ETC_DIR = YIABI_DATA_TOP_PATH + "/etc";
	public static final String YIABI_WEBUSER_DIR = YIABI_DATA_TOP_PATH + "/webuser";
	public static final String YIABI_MAPDB_DIR = YIABI_DATA_TOP_PATH + "/mapdb";
	public static final String YIABI_WEBSERVICE_DIR = YIABI_DATA_TOP_PATH + "/webservice";
	public static final String YIABI_SERVICE_DIR = YIABI_DATA_TOP_PATH + "/service";
	public static final String YIABI_STATIC_DATA_DIR = YIABI_DATA_TOP_PATH + "/static_data";
	public static final String YIABI_TMP_DIR = YIABI_DATA_TOP_PATH + "/temp";

	
	// client type
	public static final int CLIENT_WEB = 100000;
	public static final int CLIENT_MOBILE = 200000; // maybe android, ios, ...
	
	
	// conversion folder
	public static String CONVERSION_SERVER_DOCLIB 	   		= ServiceConstant.YIABI_WEBUSER_DIR + "/doclib";
	public static String CONVERSION_SERVER_ZIP_TEMP 		= CONVERSION_SERVER_DOCLIB + "/zip_temp";
	public static String CONVERSION_SERVER_UPLOAD_DOC 		= CONVERSION_SERVER_DOCLIB + "/upload_doc";
	public static String CONVERSION_SERVER_HTML_CONVERTED 	= CONVERSION_SERVER_DOCLIB + "/html_converted";
	public static String CONVERSION_SERVER_COVER 			= CONVERSION_SERVER_DOCLIB + "/cover";
	public static String CONVERSION_SERVER_LOG 	   			= CONVERSION_SERVER_DOCLIB + "/log";
	
	
	// mapdb
	public static final String YIABI_BROWSING_COUNTER_DIR = YIABI_MAPDB_DIR + "/browsing_counter";
	public static final String YIABI_COUNT24HR_DIR = YIABI_MAPDB_DIR + "/count24hr";
	public static final String YIABI_CUTTING_COUNTER_DIR = YIABI_MAPDB_DIR + "/cutting_counter";
	
	// webuser/softlink
	public static final String YIABI_WEBUSER_SOFTLINK_DIR = YIABI_WEBUSER_DIR + "/softlink";
	public static final String YIABI_WEBUSER_POSTTEMP_DIR = YIABI_WEBUSER_DIR + "/posttemp";
	public static final String YIABI_WEBUSER_ZIPSEND_DIR = YIABI_WEBUSER_DIR + "/zipsend";
	

	// service(s)
	public static final String YIABI_ACCOUNT_DIR = YIABI_SERVICE_DIR + "/account";
	public static final String YIABI_BUILD_APK_DIR = YIABI_SERVICE_DIR + "/build_apk";
	public static final String YIABI_EBOOK = YIABI_SERVICE_DIR + "/ebook";
	
	// WEB header
	public static final String CONTENT_TYPE = "application/json;charset=UTF-8";
	
//	public static final String MONGO_HOST = DFSConfig.isOnAccessMongo() ? "10.10.119.76" : "localhost";
//	public static final int MONGO_PORT = 27017;
	
	// admin
	public static final String ADMIN = "yiabiadmin";
	public static final String ADMIN_EMAIL = "yiabiadmin@yiabi.com";
	
	// unregister
	public static final String UNREGISTER = "未註冊會員"; //"yiabiunregister";
	public static final String UNREGISTER_EMAIL = "yiabiunregister@yiabi.com";
	
	// extractor
	public static final String EXTRACTOR = "yiabiextractor";
	public static final String EXTRACTOR_EMAIL = "yiabiextractor@yiabi.com";
	
	/*
	public static final String sSUCCESS              = "000";
    public static final String sDB_ERR               = "001";
    public static final String sINPUT_ERR            = "002";
    public static final String sGET_DATA_SUCCESS     = "003";
    public static final String sNO_DATA              = "004";
    public static final String sAUTHENTICATION_FAIL  = "005";
    public static final String sTRANSACTION_FAIL     = "006";
    public static final String sIO_ERR     			 = "600";
    public static final String sTOKEN_MISMATCH		 = "999";
    */
    
    
    // welson define
    public static final int SUCCESS           	= 200;
    public static final int DB_ERR              = 300;
    public static final int INPUT_ERR           = 400;
    public static final int FAIL 				= 500;
    public static final int AUTHENTICATION_FAIL = 501;
    public static final int TRANSACTION_FAIL    = 502;    
    public static final int PASSWORD_INCORRECT  = 503;    
    public static final int UNKNOWN_ERR  		= 555;    
    public static final int IO_ERR     		    = 600;
    
    // not found
    public static final int DATA_NOT_FOUND   		= 700;
    public static final int FILE_NOT_FOUND   		= 701;
    public static final int ACCOUNT_NOT_FOUND   	= 702;
    public static final int SERVICE_NOT_FOUND   	= 703;
    public static final int BOOK_NOT_PUBLIC   		= 704;
    public static final int BOOK_REMOVED_BY_OWNER	= 705;
    public static final int UNKNOWN_HOST 			= 750;
    
    // is exist
    public static final int DATA_FOUND   	  = 800;
    public static final int FILE_FOUND   	  = 801;
    public static final int ACCOUNT_FOUND     = 802;
    public static final int NICKNAME_FOUND    = 803;
    public static final int MOBILEPHONE_FOUND = 804;
    public static final int HAS_BEEN_ON_SHELF = 805;
    public static final int BLACKLIST_FOUND   = 806;
    public static final int PASSWORD_FOUND	  = 807;
    public static final int ACCOUNT_NICKNAME_MOBILE_FOUND 	= 811;
    public static final int ACCOUNT_NICKNAME_FOUND 			= 812;
    public static final int ACCOUNT_MOBILE_FOUND 			= 813;
    public static final int NICKNAME_MOBILE_FOUND 			= 814;
    
    public static final int RESERVED_WORD     = 899;
    
    // token
    public static final int TOKEN_EXPIRED 	= 998;
    public static final int TOKEN_MISMATCH 	= 999;
    
    // email & password
    public static final int PASSWORD_SHOULD_ALPHANUMERIC = 1001;
    public static final int PASSWORD_LEN_ERROR = 1002;
    public static final int PASSWORD_AT_LEAST_ONE_ALPHA = 1003;
    public static final int PASSWORD_AT_LEAST_ONE_DIGIT = 1004;
    public static final int PASSWORD_AT_LEAST_SPECIAL_SYMBOL = 1005;
    public static final int EMAIL_FORMAT_ERROR = 1100;
    public static final int NICKNAME_FORMAT_ERROR = 1101;
    
    // conversion status
    public static final int CONVERSION_FAIL = 1201;
    public static final int CONVERSION_TASK_FOUND = 1202;
    public static final int CONVERSION_TASK_NOT_FOUND = 1203;
    public static final int CONVERSION_IN_PROCESS = 1204;
    public static final int CONVERSION_INTERRUPTED = 1205;
    public static final int CONVERSION_STATUS_CANNOT_BE_CHANGE = 1206;

    // other
    public static final int PLS_CONTANT_CUSTOMER_SERVICE = 9000;
    public static final int USE_DEFAULT_COVER_IMAGE = 9001;
    public static final int PERMISSION_DENY = 9002;
    public static final int SYSTEM_IS_BUSY = 9003;
    public static final int FILE_TOO_LARGE = 9004;
}
