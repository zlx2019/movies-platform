package com.movies.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 拼音工具类
 * @author lx Zhang
 * @date 219/9/29
 */
public class PinYin4jUtils {

	/**
	 * 首字母大写
	 * @param chinese
	 * @return
	 */
	public static String spell(String chinese) {
		if (chinese == null) {
			return null;
		}
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		// 小写
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		// 不标声调
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		// u:的声母替换为v
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < chinese.length(); i++) {
				String[] array = PinyinHelper.toHanyuPinyinStringArray(chinese
						.charAt(i), format);
				if (array == null || array.length == 0) {
					continue;
				}
				// 不管多音字,只取第一个
				String s = array[0];
				// 大写第一个字母
				char c = s.charAt(0);
				String pinyin = String.valueOf(c).toUpperCase();
				sb.append(pinyin);
			}
			return sb.toString();
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String hanYuConventPinYin(String chinese){
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		// 小写
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		// 不标声调
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		// u:的声母替换为v
		format.setVCharType(HanyuPinyinVCharType.WITH_V);

		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < chinese.length(); i++) {
				String[] array = PinyinHelper.toHanyuPinyinStringArray(chinese
						.charAt(i), format);
				if (array == null || array.length == 0) {
					continue;
				}
				sb.append(array[0]);
			}
			return sb.toString();
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return null;


	}


	/**
	 * 检查是否存在汉字并转换拼音
	 * @param hanZi
	 * @return
	 */
	public static String checkHanZiConventPinYin(String hanZi) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		String[] split = hanZi.split("");
		StringBuilder stringBuilder = new StringBuilder();
		for (String name : split) {
			Matcher m = p.matcher(name);
			if (m.find()) {
				stringBuilder.append(PinYin4jUtils.hanYuConventPinYin(name));
			} else{
				stringBuilder.append(name);
			}
		}
		return stringBuilder.toString();
	}


	public static String checkHanZiToSpell(String hanZi) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		String[] split = hanZi.split("");
		StringBuilder stringBuilder = new StringBuilder();
		for (String name : split) {
			Matcher m = p.matcher(name);
			if (m.find()) {
				stringBuilder.append(PinYin4jUtils.spell(name));
			} else{
				stringBuilder.append(name);
			}
		}
		return stringBuilder.toString();
	}


	public static void main(String[] args) {
		System.out.println(PinYin4jUtils.checkHanZiToSpell("huanama").toLowerCase());
	}
}
