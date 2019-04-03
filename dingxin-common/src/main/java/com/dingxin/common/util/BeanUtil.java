package com.dingxin.common.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 描述: update 操作时，属性拷贝忽略为null的属性
 * 作者: qinzhw
 * 创建时间: 2018-05-09 14:59
 **/
public class BeanUtil {

    /**
     * 返回空的属性数组
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 复制
     * @param src
     * @param target DB
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }   
    
    /**
     * 复制
     * @param src
     * @param target DB
     */
    public static <T> T copyProperties(Object src, T target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
        return target;
    }
    
}