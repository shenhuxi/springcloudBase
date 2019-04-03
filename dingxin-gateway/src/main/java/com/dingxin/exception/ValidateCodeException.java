/**
 * 
 */
package com.dingxin.exception;

/**  
* Title: ValidateCodeException 
* Description:  
* @author dicky  
* @date 2018年6月27日 下午5:46:25  
*/
public class ValidateCodeException extends Exception{

	private static final long serialVersionUID = 568888733827470967L;
	
	public ValidateCodeException(String msg) {
        super(msg);
    }
}
