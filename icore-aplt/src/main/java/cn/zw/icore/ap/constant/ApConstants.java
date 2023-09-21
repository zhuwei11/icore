package cn.zw.icore.ap.constant;



/**
 * 应用层所有常量统一管理
 * 
 * @author lizhs
 * @date 2017年8月8日
 */
public class ApConstants {

    // dataMart字段公共运行变量固定写法
    public static final String RUN_ENVS = "RUN_ENVS";
    // 客户化参数信息(map object)
    public static final String ADDITIONAL_DATA_MART = "ADDITIONAL";
    // 最大报文长度
    public static final int PCKG_MAX_LEN = 4000;
    // 缺省的最大日期
    public static final String DEFAULT_MAX_DATE = "20991231";
    // 交易码Key
    //public static final String PRCSCD_NAME_KEY = "trxn_code"; 
    // 批量的系统编码
    public static final String SYSTEM_BATCH = "999";

    public static final long DEFAULT_PAGE_SIZE = 20L; // 默认页记录数

    public static final int MAX_TXN_TRY_TIMES = 5; // 默认最大重试次数

    
    
    public static final String API_ID = "api_id";
    /**
     * drs路由类型
     */
    public static final String DRS_ROUTER_ECIF = "E"; //客户号
    public static final String DRS_ROUTER_ACCOUNT = "A"; //电子账号
    public static final String DRS_ROUTER_CARDNO = "C"; //卡号
    public static final String DRS_ROUTER_IDENTIFICATION = "I"; //证件类型+证件号

    /**
     * 日终流程id
     */
    public static final String hx_before = "hx_before";
    public static final String hx_swday = "hx_swday";
    public static final String hx_dayend = "hx_dayend";
    public static final String gl_dayend = "gl_dayend";

    /**
     * 批量汇报事件Key
     */
    public static final String dayendRepADM_event_key = "dayendRepADM";

    public static final String dayendGLRepADM_event_key = "dayendGLRepADM";

    // 参数通配符
    public static final String WILDCARD = "*";
    
    //杂项参数通配符
    public static final String DEFAULT_SUB_KEY = "DEFAULT";

    // 动态下拉字典
    public static final String DYNAMIC_DROP_LIST = "DYNAMIC_DROP_LIST";

    public static final String TRANS_EVENT_OUTSVC = "OSVC";//外调服务
    public static final String TRANS_EVENT_OUTTXN = "OTXN";//外调交易

    // 交易控制错误ID
    public static final String TRXNCTRL_ERROR_ID = "MsBizEror.Online.E0009";

    public static final String TRANS_SUCCESS_STATUS = "S";
    
    public static final String TRANS_FAILED_STATUS = "F";
    
	public static final String GLOBAL_DATABASE_ID = "GLOBAL";
	
	public static final String RUNENVS_SHARE_FIELDS = "SHARERUNENV";
}
