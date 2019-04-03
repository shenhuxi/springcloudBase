package com.dingxin.properties;
/**
 * 
* Title: ImageCodeProperties 
* Description:   图片验证码配置项
* @author dicky  
* @date 2018年6月25日 下午5:48:58
 */
public class ImageCodeProperties extends SmsCodeProperties{

    public ImageCodeProperties(){
        setLength(4);
    }

    private int width = 67;
    private int height = 23;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
