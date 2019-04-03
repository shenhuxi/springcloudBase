package com.dingxin.common.constant.system;

import java.util.HashMap;
import java.util.Map;

/**
 * 机构数据字典,后期转DB 
 * @author shixh
 *
 */
public class SysOrgDictionary {
	
	public static final Map<String,String> org_level = new HashMap<String, String>();
	static {
		org_level.put("1","网");
		org_level.put("2","省");
		org_level.put("3","区");
	}
	
	public static final Map<String,String> ent_level = new HashMap<String, String>();
	static {
		ent_level.put("1","A级");
		ent_level.put("2","B级");
	}
}
