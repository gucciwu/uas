package com.mszq.platform.util;

import java.util.Date;

import com.mszq.platform.base.shiro.UserSecurityManager;

public class Constants {
    /**
     * 日期格式-日期+时间
     */
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式-日期+时间
     */
    public static final String PATTERN_DATETIME_NO_SYMBOL = "yyyyMMddHHmmss";
    /**
     * 日期格式-日期+时间(无秒)
     */
    public static final String PATTERN_DATETIME_NO_SECOND = "yyyy-MM-dd HH:mm";
    /**
     * 日期格式-日期(10位)
     */
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    /**
     * 日期格式-日期(8位)
     */
    public static final String PATTERN_DATE_NO_SYMBOL = "yyyyMMdd";
    /**
     * 日期格式-日期(年月7位)
     */
    public static final String PATTERN_MONTH = "yyyy-MM";
    /**
     * 日期格式-日期(年月6位)
     */
    public static final String PATTERN_MONTH_NO_SYMBOL = "yyyyMM";
    /**
     * 文件管理页面-产品文件上传路径
     */
    public static final String UPLOAD_PRODUCT_FILE_PATH = "uploadProductFilePath";
    /**
     * 营业执照等其他文件上传路径
     */
    public static final String UPLOAD_OTHER_FILE_PATH = "uploadOtherFilePath";
    /**
     * 系统参数：当前时间
     */
    public static final Date SYS_CURRENT_TIME = new Date();
    /**
     * 系统参数：定时任务默认执行时间
     */
    public static final String SYS_CRON_TIME = "0 50 23 ";
    /**
     * 系统参数：当前登录用户
     */
    public static final Long SYS_CURRENT_USER = (Long)UserSecurityManager.getAttribute("ID");
    /**
     * 授信进度：申请中
     */
    public static final String CREDITSTATUS_APPLYING = "01";
    /**
     * 授信进度：结束
     */
    public static final String CREDITSTATUS_FINISH = "02";
    /**
     * 授信进度：终止
     */
    public static final String CREDITSTATUS_STOP = "03";
    /**
     * 债务工具状态：待发行
     */
    public static final String ISTATUS_ISSUE = "01";
    /**
     * 债务工具状态：存续中
     */
    public static final String ISTATUS_DURATION = "02";
    /**
     * 债务工具状态：已偿还
     */
    public static final String ISTATUS_REPAY = "03";
    /**
     * 融资类型：收益凭证
     */
    public static final String FINANCE_TYPE_IC = "0101";
    /**
     * 融资类型：转融通
     */
    public static final String FINANCE_TYPE_REFINANCING = "0201";
    /**
     * 融资类型：债券-公司债券
     */
    public static final String FINANCE_TYPE_BOND_COMPANY = "0301";
    /**
     * 融资类型：债券-次级债券
     */
    public static final String FINANCE_TYPE_BOND_SUBORDINATED = "0302";
    /**
     * 融资类型：债券-短期融资券
     */
    public static final String FINANCE_TYPE_BOND_SHORT_TIME = "0303";
    /**
     * 融资类型：同业拆借
     */
    public static final String FINANCE_TYPE_LENDING = "0401";
    /**
     * 融资类型：收益权转让-两融收益权
     */
    public static final String FINANCE_TYPE_USUFRUCT_TWO = "0501";
    /**
     * 融资类型：收益权转让-股票质押收益权
     */
    public static final String FINANCE_TYPE_USUFRUCT_PLEDGE = "0502";
    /**
     * 单据编码：融资-授信-授信申请单
     */
    public static final String BILL_FINANCE_CREDIT = "SXSQ";
    /**
     * 单据编码：融资-收益凭证-新增
     */
    public static final String BILL_FINANCE_IC_ADD = "PZZJ";
    /**
     * 单据编码：融资-收益凭证-兑付
     */
    public static final String BILL_FINANCE_IC_PAY = "PZDF";
    /**
     * 单据编码：融资-转融通-新增
     */
    public static final String BILL_FINANCE_REFINANCING_ADD = "ZRTZJ";
    /**
     * 单据编码：融资-转融通-偿还
     */
    public static final String BILL_FINANCE_REFINANCING_PAY = "ZRTDF";
    /**
     * 单据编码：融资-债券-新增
     */
    public static final String BILL_FINANCE_BOND_ADD = "ZQZJ";
    /**
     * 单据编码：融资-债券-兑付
     */
    public static final String BILL_FINANCE_BOND_PAY = "ZQDF";
    /**
     * 单据编码：融资-拆借-新增
     */
    public static final String BILL_FINANCE_LENDING_ADD = "CJZJ";
    /**
     * 单据编码：融资-拆借-偿还
     */
    public static final String BILL_FINANCE_LENDING_PAY = "CJDF";
    /**
     * 单据编码：融资-收益权转让-新增
     */
    public static final String BILL_FINANCE_TRANSFER_ADD = "SYQZJ";
    /**
     * 单据编码：融资-收益权转让-偿还
     */
    public static final String BILL_FINANCE_TRANSFER_PAY = "SYQDF";
    /**
     * 单据类型：融资-授信申请单
     */
    public static final String BILL_TYPE_FINANCE_CREDIT = "040101";
    /**
     * 单据类型：融资-收益凭证
     */
    public static final String BILL_TYPE_FINANCE_IC = "040201";
    /**
     * 单据类型：融资-转融通
     */
    public static final String BILL_TYPE_FINANCE_REFINANCING = "040301";
    /**
     * 单据类型：融资-债券
     */
    public static final String BILL_TYPE_FINANCE_BOND = "040401";
    /**
     * 单据类型：融资-拆借
     */
    public static final String BILL_TYPE_FINANCE_LENDING = "040501";
    /**
     * 单据类型：融资-收益权转让
     */
    public static final String BILL_TYPE_FINANCE_TRANSFER = "040601";
    /**
     * 单据类型：融资-偿还单
     */
    public static final String BILL_TYPE_FINANCE_REPAY = "040701";
    /**
     * 账户分类：银行账户-活期
     */
    public static final String ACCOUNT_TYPE_BANK_CURRENT = "0101";
    /**
     * 账户分类：银行账户-定期
     */
    public static final String ACCOUNT_TYPE_BANK_FIXED = "0102";
}
