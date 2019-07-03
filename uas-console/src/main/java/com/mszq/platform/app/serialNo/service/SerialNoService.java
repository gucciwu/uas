package com.mszq.platform.app.serialNo.service;

import java.util.Date;

/**
 * 自动生成单据编号
 * 
 * @author shenxg
 *
 */
public interface SerialNoService {
	
	
	public static String CODE_TYPE_ZJDB="ZJDB";
	public static String CODE_TYPE_BFDB="BFDB";
	
	public static String CODE_TYPE_YHTZ="YHTZ";
	
	public static String CODE_TYPE_BFTZ="BFTZ";
	
	public static String CODE_TYPE_BZTZ="BZTZ";
	
	public static String CODE_TYPE_ZGBF="ZGBF";
	
	public static String CODE_TYPE_ZGBZ="ZGBZ";
	
	public static String CODE_TYPE_LLHH="LLHH";
	
	public static String CODE_TYPE_QHTZ="QHTZ";
	
	public static String CODE_TYPE_DQCK="DQCK";
	
	public static String CODE_TYPE_DQZH="DQZH";
	
	public static String CODE_TYPE_LCSGG="LCSGG";
	public static String CODE_TYPE_LCSHG="LCSHG";
	
	public static String CODE_TYPE_LCLXG="LCLXG";
	
	public static String CODE_TYPE_LCSGS="LCLGS";
	
	public static String CODE_TYPE_LCSHS="LCSHS";
	
	public static String CODE_TYPE_MRFS="MRFS";
	/**
	 * 规模内资金使用/回款单
	 */
	public static String CODE_TYPE_GSTZ="GSTZ";
	/**
	 * 日间头寸占用单
	 */
	public static String CODE_TYPE_RJTC="RJTC";

    /**
     * 获取自动编号(单据编号最后四位)
     * 
     * @param code String 单据编码
     * @param busiDate String 业务日期
     * @return
     */
    String getSerialNo(String code, String busiDate);

    /**
     * 获取单据编号(单据编码+业务日期+自动编号)
     * 
     * @param code String 单据编码
     * @param busiDate String 业务日期
     * @return
     */
    String getCode(String code, String busiDate);

    /**
     * 获取自动编号(单据编号最后四位)
     * 
     * @param code String 单据编码
     * @param busiDate Date 业务日期
     * @return
     */
    String getSerialNo(String code, Date busiDate);

    /**
     * 获取单据编号(单据编码+业务日期+自动编号)
     * 
     * @param code String 单据编码
     * @param busiDate Date 业务日期
     * @return
     */
    String getCode(String code, Date busiDate);

    int insert(Long id, String code);
}
