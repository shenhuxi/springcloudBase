package com.dingxin.data.jpa.former;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.hibernate.HibernateException;
import org.hibernate.transform.ResultTransformer;

/**
 * 修正返回自定义pojo类型时找不到属性的BUG
 * @author shixh
 */
public class MyResultTransformer implements ResultTransformer {

    private static final long serialVersionUID = -3779317531110592988L;

    private final Class<?> resultClass;
    private Field[] fields;
    private BeanUtilsBean beanUtilsBean;

    public MyResultTransformer(final Class<?> resultClass) {
        this.resultClass = resultClass;
        this.fields = this.resultClass.getDeclaredFields();
        beanUtilsBean=BeanUtilsBean.getInstance();
    }

    /**
     * @param tuple 
     * @param aliases 别名
     */
    public Object transformTuple(final Object[] tuple, final String[] aliases) {
        Object result;
		try {
			result = this.resultClass.newInstance();
			for (int i = 0; i < aliases.length; i++) {
				for (Field field : this.fields) {
					String fieldName = field.getName();
					if (fieldName.equalsIgnoreCase(aliases[i].replaceAll("_", ""))) {
						beanUtilsBean.setProperty(result, fieldName, tuple[i]);
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName(), e);
		}
        return result;
    }

    @SuppressWarnings("rawtypes")
    public List transformList(final List collection) {
        return collection;
    }
}