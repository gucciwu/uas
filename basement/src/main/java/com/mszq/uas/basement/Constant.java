package com.mszq.uas.basement;

/**
 * 系统变量名称
 *
 * @author liugy
 */
public class Constant {

    /**
     * 存储在session中的字段名称
     */
    public class SESSION {
        /**
         * 员工编号
         */
        public static final String JOB_NUMBER = "jobNumber";
        /**
         * 用户号
         */
        public static final String USER_ID = "userId";

    }

    /**
     * 证件类型
     */
    public class CertificateDict {
        /*身份证*/
        public static final int ID = 0;
        /*护照号*/
        public static final int PASSPORT_NO = 1;
        /*军官证*/
        public static final int CERTIFICATE_OF_OFFICERS = 2;
        /*士兵证*/
        public static final int SOLDIER_CARD = 3;
        /*回乡证*/
        public static final int HOME_PERMIT = 4;
        /*户口本*/
        public static final int ACCOUNT_BOOK = 5;
        /*外国护照*/
        public static final int FOREIGN_PASSPORT = 6;
        /*解放军文职干部*/
        public static final int PLA_CIVILIAN_CADRES = 7;
        /*临时身份证*/
        public static final int TEMPORARY_ID_CARD = 8;
        /*其它证件*/
        public static final int OTHER_CERIFICATE = 9;
        /*香港居民通行证*/
        public static final int HONGKONG_RESIDENT_PASS = 10;
        /*澳门居民通行证*/
        public static final int MACAO_RESIDENT_PASS = 11;
        /*台湾居民通行证*/
        public static final int TAIWAN_RESIDENT_PASS = 12;
        /*外国人永久居留证*/
        public static final int ALIEN_PERMANENT_RESIDENCE_PERMIT = 13;
        /*社会保障号*/
        public static final int SOCIAL_SECURITY_NUMBER = 14;
        /*文职证*/
        public static final int CIVIL_CERTIFICATE = 15;
        /*警官证*/
        public static final int POLICE_CARD = 16;
        /*香港居民身份证*/
        public static final int HONGKONG_RESIDENT_IDENTITY_CARD = 17;
        /*澳门居民身份证*/
        public static final int MACAO_RESIDENT_IDENTITY_CARD = 18;
        /*工商营业执照*/
        public static final int BUSINESS_LICENSE = 19;
        /*社团法人注册登记证书*/
        public static final int CERTIFICATE_OF_REGISTRATION = 20;
        /*机关法人成立批文*/
        public static final int LEGAL_PERSON_ESTABLISHMENT_OF_OFFICE = 21;
        /*事业法人成立批文*/
        public static final int LEGAL_PERSON_ESTABLISHMENT_OF_INSTITUTION = 22;
        /*境外有效商业登记证明*/
        public static final int VALID_BUSINESS_REGISTRATION_CERTIFICATE = 23;
        /*武警*/
        public static final int ARMED_POLICE = 24;
        /*军队*/
        public static final int ARMY = 25;
        /*基金会*/
        public static final int FOUNDATION = 26;
        /*技术监督局号码*/
        public static final int TECHNICAL_SUPERVISION_BUREAU_NUMBER = 27;
        /*其它证件*/
        public static final int OTHER_DOCUMENTS = 28;
        /*组织机构代码证*/
        public static final int ORGANIZATION_CODE_CERTIFICATE = 29;
        /*税务登记证*/
        public static final int TAX_REGISTRATION_CERTIFICATE = 30;
        /*批文*/
        public static final int APPROVAL = 31;
        /*注册号*/
        public static final int REGISTRATION_NUMBER = 32;
    }

    /**
     * 账户状态
     */
    public class USER_STATUS {
        /**
         * 正常
         */
        public static final short OK = 1;
        /**
         * 注销
         */
        public static final short UNSIGNED = 2;
        /**
         * 挂起
         */
        public static final short SUSPEND = 3;
    }

    /**
     * 组织架构状态
     */
    public class ORG_STATUS {
        /**
         * 正常
         */
        public static final short OK = 1;
        /**
         * 注销
         */
        public static final short UNSIGNED = 2;
    }

    /**
     * 角色状态
     */
    public class ROLE_STATUS {
        /**
         * 正常
         */
        public static final short OK = 0;
        /**
         * 注销
         */
        public static final short UNSIGNED = 1;
    }

    /**
     * 组织架构类型
     */
    public class ORG_TYPE {
        public static final short HR = 1;
    }

    /**
     * IP列表状态
     */
    public class IP_CONDITION {
        /**
         * 禁止登陆
         */
        public static final int FORBIDDEN = 1;
    }

    /**
     * 请求返回码，0为成功
     */
    public static final String CODE = "code";
    /**
     * 返回提示信息
     */
    public static final String MESSAGE = "message";
    /**
     * 返回的业务数据，json格式封装的字符串
     */
    public static final String BIZ_DATA = "bizData";
}
