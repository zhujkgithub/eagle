package com.soaring.eagle.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/4/1
 * Time: 11:11
 * description: 去除文本的html标签
 */
public class ReduceHtml2TextUtil {

    public static String removeHtmlTag(String inputString) {
        if (inputString == null) {
            return null;
        } else {
            String htmlStr = inputString;
            String textStr = "";

            try {
                String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
                String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
                String regEx_html = "<[^>]+>";
                String regEx_special = "\\&[a-zA-Z]{1,10};";
                String regEx_blank = "\\s*|\t|\r|\n";
                Pattern p_script = Pattern.compile(regEx_script, 2);
                Matcher m_script = p_script.matcher(htmlStr);
                htmlStr = m_script.replaceAll("");
                Pattern p_style = Pattern.compile(regEx_style, 2);
                Matcher m_style = p_style.matcher(htmlStr);
                htmlStr = m_style.replaceAll("");
                Pattern p_html = Pattern.compile(regEx_html, 2);
                Matcher m_html = p_html.matcher(htmlStr);
                htmlStr = m_html.replaceAll("");
                Pattern p_special = Pattern.compile(regEx_special, 2);
                Matcher m_special = p_special.matcher(htmlStr);
                htmlStr = m_special.replaceAll("");
                Pattern p_blank = Pattern.compile(regEx_blank, 2);
                Matcher m_blank = p_blank.matcher(htmlStr);
                htmlStr = m_blank.replaceAll("");
                textStr = htmlStr;
            } catch (Exception var18) {
                var18.printStackTrace();
            }

            return textStr;
        }
    }

}
