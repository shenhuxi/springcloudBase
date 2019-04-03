package com.dingxin.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dingxin.common.constant.EncryptionConstant;
import com.dingxin.common.annotation.Encryption;

/**
 * 加密对象工具类
 * @author shixh
 */
public class EncryptionEntityUtil {
	
	/**
	 * 加密对象属性(带@Encryption的属性)
	 * @param object
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void encode(Object object) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz1 = object.getClass();
		Field[] field = clazz1.getDeclaredFields();
		for(int i=0;i<field.length;i++) {
			Encryption name = field[i].getAnnotation(Encryption.class);
			if(name!=null && EncryptionConstant.ENCRYPTION_A.equals(name.name())){
				String value = (String)ReflectionUtils.getFieldValue(object,field[i].getName());
				if(StringUtils.isNotBlank(value)) {
					value = AES.AESEncode(value);
					ReflectionUtils.setFieldValue(object,field[i].getName(),value);
				}
				
			}
		}
	}
	
	/**
	 * 解密对象属性(带@Encryption的属性)
	 * @param object
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void decode(Object object) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz1 = object.getClass();
		Field[] field = clazz1.getDeclaredFields();
		for(int i=0;i<field.length;i++) {
			Encryption name = field[i].getAnnotation(Encryption.class);
			if(name!=null && EncryptionConstant.ENCRYPTION_A.equals(name.name())){
				String value = (String)ReflectionUtils.getFieldValue(object,field[i].getName());
				if(StringUtils.isNotBlank(value)) {
					value = AES.AESDecode(value);
					ReflectionUtils.setFieldValue(object,field[i].getName(),value);
				}
			}
		}
	}
	
	
	/**
	 * 加密集合对象属性(带@Encryption的属性)
	 * @param objects
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void encode(List<Object> objects) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz1 = objects.get(0).getClass();
		Field[] field = clazz1.getDeclaredFields();
		List<String> fieldNames = new ArrayList<String>();
		for(int i=0;i<field.length;i++) {
			Encryption name = field[i].getAnnotation(Encryption.class);
			if(name!=null && EncryptionConstant.ENCRYPTION_A.equals(name.name())){
				fieldNames.add(field[i].getName());
			}
		}
		if(fieldNames.isEmpty()) {
            return;
        }
		
		for(Object object:objects) {
			for(String fieldName:fieldNames) {
				String value = (String)ReflectionUtils.getFieldValue(object,fieldName);
				if(StringUtils.isNotBlank(value)) {
					value = AES.AESEncode(value);
					ReflectionUtils.setFieldValue(object,fieldName,value);
				}
			}
		}
	}
	
	
	/**
	 * 解密集合对象属性(带@Encryption的属性)
	 * @param objects
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void decode(List<Object> objects) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz1 = objects.get(0).getClass();
		Field[] field = clazz1.getDeclaredFields();
		List<String> fieldNames = new ArrayList<String>();
		for(int i=0;i<field.length;i++) {
			Encryption name = field[i].getAnnotation(Encryption.class);
			if(name!=null && EncryptionConstant.ENCRYPTION_A.equals(name.name())){
				fieldNames.add(field[i].getName());
			}
		}
		if(fieldNames.isEmpty()) {
            return;
        }
		
		for(Object object:objects) {
			for(String fieldName:fieldNames) {
				String value = (String)ReflectionUtils.getFieldValue(object,fieldName);
				if(StringUtils.isNotBlank(value)) {
					value = AES.AESDecode(value);
					ReflectionUtils.setFieldValue(object,fieldName,value);
				}
			}
		}
	}
}
