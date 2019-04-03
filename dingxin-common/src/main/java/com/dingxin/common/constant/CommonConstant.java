package com.dingxin.common.constant;

public class CommonConstant {

	public static final int STOP_STATE = 0;// 停用
	public static final int USE_STATE = 1;// 启用
	public static final int DESTROY_STATE = 2;// 注销

	public static final int IS_PARENT = 0;// 是父级
	public static final int NO_PARENT = 1;// 不是父级

	public static final int NORMAL = 0;// 正常的数据
	public static final int DELETE = 1;// 已删除数据

	public static final String PAGE_PAGENUM = "pageNum";
	public static final String PAGE_PAGESIZE = "pageSize";
	public static final String PAGE_SORT = "sort";
	public static final String PAGE_SORTTYPE = "sortType";
	public static final String PAGE_10 = "10";
	public static final String PAGE_1 = "1";

	/*
	 * 违纪违法行为业务类型
	 */
	public static final int AGAINSTLAW_LETTER = 1;// 信访类型
	public static final int AGAINSTLAW_CLUE = 2;// 线索类型
	public static final int AGAINSTLAW_GOVERN = 3;// 执纪审查类型
	public static final int AGAINSTLAW_FILECHECK = 4;// 立案审查类型
	public static final int AGAINSTLAW_LAWCASE = 5;// 案件类型

	/*
	 * 违纪违法行为显示顺序，0主1从 主涉案人员显示顺序0主1从
	 */
	public static final int SHOWMASTER = 0;// 主违纪违法行为或主涉案人
	public static final int SHOWSLAVE = 1;// 从违纪违法行为或从涉案人

	/*
	 * redis 缓存 key
	 */
	public static final String DICTSTATICTREE = "STATIC_TREE";
	
	public static final String DICTITEM = "DICT_ITEM";

	/*
	 * redis 过期时间
	 */
	public static final long WEEK = 7 * 24 * 24 * 60;
	
	/*
	 * RabbitMQ队列名
	 */
	public static final String LOGQUEUE = "LOG_QUEUE";
}
