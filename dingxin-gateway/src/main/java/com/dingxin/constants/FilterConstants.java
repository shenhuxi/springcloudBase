/**
 * 
 */
package com.dingxin.constants;

/**  
* Title: Snippet 
* Description:  
* @author dicky  
* @date 2018年6月27日 下午5:15:20  
*/  
public interface FilterConstants {
	/**
     * 指定该Filter的类型
     */
	public static final String ERROR_TYPE = "error";
	
	public static final String POST_TYPE = "post";
	
	public static final String PRE_TYPE = "pre";
	
	public static final String ROUTE_TYPE = "route";
	
	/**
     * 指定该Filter执行的顺序（Filter从小到大执行）
     */
	public static final int DEBUG_FILTER_ORDER = 1;
	public static final int PRE_DECORATION_FILTER_ORDER = 5;
	public static final int FORM_BODY_WRAPPER_FILTER_ORDER = -1;
	public static final int RIBBON_ROUTING_FILTER_ORDER = 10;
	public static final int SEND_ERROR_FILTER_ORDER = 0;
	public static final int SEND_FORWARD_FILTER_ORDER = 500;
	public static final int SEND_RESPONSE_FILTER_ORDER = 1000;
	public static final int SIMPLE_HOST_ROUTING_FILTER_ORDER = 100;
	public static final int SERVLET_30_WRAPPER_FILTER_ORDER = -2;
	public static final int SERVLET_DETECTION_FILTER_ORDER = -3;
}

