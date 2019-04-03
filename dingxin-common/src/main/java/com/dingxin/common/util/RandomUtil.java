package com.dingxin.common.util;

import java.util.Random;

public class RandomUtil {

	public static String getRandomCharAndNumr(Integer length) {
		StringBuffer sb = new StringBuffer();
		return getRandomStr(length, sb);
	}

	private static String getRandomStr(Integer length, StringBuffer sb) {
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			boolean b = random.nextBoolean();
			if (b) {
				sb.append((char) (65 + random.nextInt(26)));// 取得大写字母
			} else {
				sb.append(String.valueOf(random.nextInt(10)));
			}
		}
		return sb.toString();
	}

	public static String getLetterCode(){
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtil.getNowYYYYMMDDHHMMSS2());
		return getRandomStr(4, sb);
	}

	public static String getClueCode(){
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtil.getNowYYYYMMDDHHMMSS2());
		return getRandomStr(4, sb);
	}

	public static void main(String[] args) {
		System.out.println(getRandomCharAndNumr(4));
	}
}
