package com.dingxin.properties;

import java.util.HashSet;
import java.util.Set;

/**
 * 
* Title: ValidateCodeProperties 
* Description:  验证码配置
* @author dicky  
* @date 2018年6月25日 下午5:46:42
 */
public class ValidateCodeProperties {
	/**
	 * 验证码开关配置：为true时开启，默认不开启
	 */
	private boolean validate = false;
	
	/**
	 * 需要校验验证码的URL
	 */
	private Set<String> validateUrls = new HashSet<>();
    /**
     * 短信验证码配置
     */
    private SmsCodeProperties sms = new SmsCodeProperties();

    /**
     * 图片验证码配置
     */
    private ImageCodeProperties image = new ImageCodeProperties();

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public SmsCodeProperties getSms() {
		return sms;
	}

	public void setSms(SmsCodeProperties sms) {
		this.sms = sms;
	}

	public ImageCodeProperties getImage() {
		return image;
	}

	public void setImage(ImageCodeProperties image) {
		this.image = image;
	}

	public Set<String> getValidateUrls() {
		return validateUrls;
	}

	public void setValidateUrls(Set<String> validateUrls) {
		this.validateUrls = validateUrls;
	}


}
