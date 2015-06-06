package com.my.common.util;

import java.nio.charset.CharsetEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by makun
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 替换字符串
     * 
     * @param sb
     * @param what
     * @param with
     * @return
     */
    public static StringBuilder replace(StringBuilder sb, String what, String with) {
        int pos = sb.indexOf(what);
        while (pos > -1) {
            sb.replace(pos, pos + what.length(), with);
            pos = sb.indexOf(what);
        }
        return sb;
    }

    /**
     * 替换字符串
     * 
     * @param s
     * @param what
     * @param with
     * @return
     */
    public static String replace(String s, String what, String with) {
        return replace(new StringBuilder(s), what, with).toString();
    }

    /**
     * 文本转html
     *
     * @param txt
     * @return
     */
    public static String txt2htm(String txt) {
        if (StringUtils.isBlank(txt)) {
            return txt;
        }
        StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
        char c;
        for (int i = 0 ; i < txt.length() ; i++) {
            c = txt.charAt(i);
            switch (c) {
                case '&':
                    sb.append("&amp;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case ' ':
                    sb.append("&nbsp;");
                    break;
                case '\n':
                    sb.append("<br/>");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * @return
     */
    public static String getRandomId() {
        return StringUtils.remove(UUID.randomUUID().toString(), '-');
    }

    /**
     * 剪切文本。如果进行了剪切，则在文本后加上"..."
     * 
     * @param s
     *            剪切对象。
     * @param len
     *            编码小于256的作为一个字符，大于256的作为两个字符。
     * @return
     */
    public static String textCut(String s, int len, String append) {
        if (s == null) {
            return null;
        }
        int slen = s.length();
        if (slen <= len) {
            return s;
        }
        // 最大计数（如果全是英文）
        int maxCount = len * 2;
        int count = 0;
        int i = 0;
        for ( ; count < maxCount && i < slen ; i++) {
            if (s.codePointAt(i) < 256) {
                count++;
            }
            else {
                count += 2;
            }
        }
        if (i < slen) {
            if (count > maxCount) {
                i--;
            }
            if (!StringUtils.isBlank(append)) {
                if (s.codePointAt(i - 1) < 256) {
                    i -= 2;
                }
                else {
                    i--;
                }
                return s.substring(0, i) + append;
            }
            else {
                return s.substring(0, i);
            }
        }
        else {
            return s;
        }
    }

    //用线程安全的对象 格式化日期
    private static ThreadLocal<SimpleDateFormat> formatDate_tl            = new ThreadLocal<SimpleDateFormat>() {

                                                                              @Override
                                                                              protected SimpleDateFormat initialValue() {
                                                                                  return new SimpleDateFormat("yyyy-MM-dd");
                                                                              }

                                                                          };
    private static ThreadLocal<SimpleDateFormat> formatYearMonth_tl       = new ThreadLocal<SimpleDateFormat>() {

                                                                              @Override
                                                                              protected SimpleDateFormat initialValue() {
                                                                                  return new SimpleDateFormat("yyyy-MM");
                                                                              }

                                                                          };
    private static ThreadLocal<SimpleDateFormat> formatDatetime_tl        = new ThreadLocal<SimpleDateFormat>() {

                                                                              @Override
                                                                              protected SimpleDateFormat initialValue() {
                                                                                  return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                                              }

                                                                          };
    private static ThreadLocal<SimpleDateFormat> formatDateCompact_tl     = new ThreadLocal<SimpleDateFormat>() {

                                                                              @Override
                                                                              protected SimpleDateFormat initialValue() {
                                                                                  return new SimpleDateFormat("yyyyMMdd");
                                                                              }

                                                                          };
    private static ThreadLocal<SimpleDateFormat> formatDatetimeCompact_tl = new ThreadLocal<SimpleDateFormat>() {

                                                                              @Override
                                                                              protected SimpleDateFormat initialValue() {
                                                                                  return new SimpleDateFormat("yyyyMMddHHmmss");
                                                                              }

                                                                          };
    private static ThreadLocal<SimpleDateFormat> formatDateforDataBase_tl = new ThreadLocal<SimpleDateFormat>() {

                                                                              @Override
                                                                              protected SimpleDateFormat initialValue() {
                                                                                  return new SimpleDateFormat("yyMMddHHmmss");
                                                                              }

                                                                          };

    /**
     * Returns whether or not the String is empty. A String is considered to be
     * empty if it is null or if it has a length of 0.
     *
     * @param string
     *            The String that should be examined.
     * @return Whether or not the String is empty.
     */
    public static boolean isEmpty(String string) {
        return ((string == null) || (string.trim().length() == 0));
    }

    /**
     * 将一个字符串的首字母改为大写或者小写
     *
     * @param srcString
     *            源字符串
     * @param lowercase
     *            大小写标识，ture小写，false大些
     * @return 改写后的新字符串
     */
    public static String toLowerCaseInitial(String srcString, boolean lowercase) {
        StringBuilder sb = new StringBuilder();
        if (lowercase) {
            sb.append(Character.toLowerCase(srcString.charAt(0)));
        }
        else {
            sb.append(Character.toUpperCase(srcString.charAt(0)));
        }
        sb.append(srcString.substring(1));
        return sb.toString();
    }

    /**
     * 返回非空对象值，若对象null，则返回空字符串
     *
     * @param o
     * @return
     */
    public static Object notNull(Object o) {
        return null == o ? "" : o;
    }

    /**
     * 将一个字符串按照句点（.）分隔，返回最后一段
     *
     * @param clazzName
     *            源字符串
     * @return 句点（.）分隔后的最后一段字符串
     */
    public static String getLastName(String clazzName) {
        String[] ls = clazzName.split("\\.");
        return ls[ls.length - 1];
    }

    /**
     * 把String安全转化为Long 还需验证string是否可以转换为long,待加
     *
     * @param s
     * @return
     */
    public static final Long safeToLong(String s) {
        if (s == null || "".equals(s))
            return null;
        try {
            return Long.parseLong(s);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    public static final String safeToString(Object o) {
        if (o == null)
            return null;
        else
            return o.toString();
    }

    public static final Integer safeToInteger(String s) {
        if (s == null || "".equals(s)) {
            return null;
        }
        else {
            return Integer.parseInt(s);
        }
    }

    public static final boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static final String listToStringBySepartor(List<String> list) {
        if (list == null || list.size() == 0)
            return null;
        String cus = "";
        for (String s : list) {
            if ("".equals(cus)) {
                cus = s;
            }
            else
                cus = cus + "," + s;
        }
        return cus;
    }

    public static final List<String> SpiltString(String s, String separtor) {
        if (s == null || "".equals(s))
            return new ArrayList<String>();
        String[] sp = s.split(separtor);
        if (sp.length > 0)
            return java.util.Arrays.asList(sp);
        return new ArrayList<String>();
    }

    public static String formatDate(Date date) {
        if (date == null)
            return null;
        return formatDate_tl.get().format(date);
    }

    public static String formatYearMonth(Date date) {
        if (date == null)
            return null;
        return formatYearMonth_tl.get().format(date);
    }

    public static String formatDateForDataBase(Date date) {
        if (date == null) {
            return null;
        }
        return formatDateforDataBase_tl.get().format(date);
    }

    /**
     * 返回"yyyy-MM-dd HH:mm:ss"格式时间字符串
     *
     * @param date
     * @return
     */
    public static String formatDatetime(Date date) {
        if (date == null)
            return null;
        return formatDatetime_tl.get().format(date);
    }

    /**
     * 返回"yyyyMMddHHmmss"紧凑格式时间字符串
     *
     * @param date
     * @return
     */
    public static String formatDatetimeCompact(Date date) {
        if (date == null)
            return null;
        return formatDatetimeCompact_tl.get().format(date);
    }

    /**
     * 返回"yyyyMMdd"紧凑格式时间字符串
     *
     * @param date
     * @return
     */
    public static String formatDateCompact(Date date) {
        if (date == null)
            return null;
        return formatDateCompact_tl.get().format(date);
    }

    private static String getFullTime(String time) {
        if (time.split(":").length - 1 == 1)
            return time + ":00";
        else
            return time;

    }

    public static Date parseDate(String str) {
        Date res = null;
        try {
            res = formatDate_tl.get().parse(str);
        }
        catch (ParseException e) {
            throw new RuntimeException("日期解析错误:" + str, e);
        }
        return res;
    }

    public static Date parseDateTime(String str) {
        Date res = null;
        try {
            res = formatDatetime_tl.get().parse(str);
        }
        catch (ParseException e) {
            throw new RuntimeException("日期时间解析错误:" + str, e);
        }
        return res;
    }

    public static Date parseDateTime(String str, SimpleDateFormat formatDate) {
        Date res = null;
        try {
            res = formatDate.parse(str);
        }
        catch (ParseException e) {
            throw new RuntimeException("日期解析错误:" + str, e);
        }
        return res;
    }

    public static Date parseDateTime(Date date, String time) {
        String fullTime = getFullTime(time);
        return parseDateTime(formatDate(date) + " " + fullTime);
    }

    public static int[] getHMS(String time) {
        String fullTime = getFullTime(time);
        int[] res = new int[3];
        String[] tmp = fullTime.split(":");
        for (int i = 0 ; i < 3 ; i++) {
            res[i] = Integer.parseInt(tmp[i]);
        }

        return res;
    }

    /**
     * 指定范围内的随机数 获得的随机数范围为0-target
     *
     * @param target
     *            >1
     * @return
     */
    public static int random(int target) {
        Random random = new Random();
        return (random.nextInt()) % target;
    }

    public static void removeLastComma(StringBuffer sb) {
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public static String removeLastComma(String s) {
        if (s.charAt(s.length() - 1) == ',') {
            return s.substring(0, s.length() - 1);
        }
        else {
            return s;
        }
    }

    /**
     * 字符串转换，如果为null则转为空字符串
     *
     * @param source
     *            原字符串
     * @param isTrim
     *            是否去掉头尾空格
     * @return
     */
    public static String nullToEmptyStr(String source, boolean isTrim) {
        if (source == null)
            return "";
        if (isTrim)
            source = source.trim();
        return source;
    }

    /**
     * 按字节截取字符串子串
     *
     * @param value
     * @param length
     * @return
     */
    public static String leftOfByte(String value, int length) {
        if (value == null || value.equals("")) {
            return "";
        }
        String temp;
        int yy = length;
        if (value.length() < length) {
            temp = value;
            yy = temp.length();
        }
        else {
            temp = value.substring(0, length);
        }

        int xx = lengthOfByte(temp, 2) - length;

        while (xx > 0) {
            if (xx == 1) {
                yy = yy - xx;
            }
            else {
                yy = yy - xx / 2;
            }

            temp = temp.substring(0, yy);
            xx = lengthOfByte(temp, 2) - length;
        }

        return temp;
    }

    /**
     * 获得字符串的字节长度
     *
     * @param value
     *            测量字符串
     * @param cLength
     *            一个中文字符等于多少字节，如果是0则让系统计算
     * @return
     */
    public static int lengthOfByte(String value, int cLength) {
        if (value == null || value.equals(""))
            return 0;

        StringBuffer buff = new StringBuffer(value);
        int length = 0;
        String stmp;
        for (int i = 0 ; i < buff.length() ; i++) {
            stmp = buff.substring(i, i + 1);

            try {
                stmp = new String(stmp.getBytes("utf8"));
            }
            catch (Exception e) {
            }

            if (stmp.getBytes().length > 1) {
                if (cLength == 0) {
                    length += stmp.getBytes().length;
                }
                else {
                    length += cLength;
                }

            }
            else {
                length += 1;
            }
        }
        return length;
    }

    /**
     * 转换为中文字符,将str转换为"ISO-8859-1"格式字符
     *
     * @param str
     * @return
     */
    public static String encodeISO(Object str) {
        try {
            if (str != null)
                return new String(((String) str).getBytes("ISO-8859-1"), "utf-8");
            else
                return "";
        }
        catch (Exception ex) {
            return "";
        }
    }

    /**
     * 将字符串转为Unicode形式 "\\uxxxx"
  	 *
  	 * @param str
  	 * @return
  	 */
    public static String String2Unicode(String str) {
        if (str == null || str.equals("")) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        char[] charArr = str.toCharArray();
        for (char chr1 : charArr) {
            if ((int) chr1 <= 255) {
                result.append("\\u00" + Integer.toHexString((int) chr1));
            }
            else {
                result.append("\\u" + Integer.toHexString((int) chr1));
            }
        }
        return result.toString();
    }

    /**
     * 将Unicode形式 "\\uxxxx"转为字符串
  	 *
  	 * @param str
  	 * @return
  	 */
    public static String Unicode2String(String str) {
        if (str != null) {
            StringBuffer sb = new StringBuffer();
            int cp = str.indexOf("\\u");
            if (cp == -1) {
                return str;
            }
            else if (cp > 0) {
                sb.append(str.substring(0, cp));
                str = str.substring(cp);
            }

            StringTokenizer st = new StringTokenizer(str, "\\u");
            while (st.hasMoreTokens()) {
                String unicode = st.nextToken();
                if (unicode.length() > 4) {
                    sb.append((char) Integer.parseInt(unicode.substring(0, 4), 16));
                    sb.append(unicode.substring(4));
                }
                else {
                    sb.append((char) Integer.parseInt(unicode, 16));
                }
            }
            return sb.toString();
        }
        else {
            return null;
        }
    }

    /**
     * "gb2312"到"Unicode"的转码
     *
     * @param str
     * @return
     */
    public static String toUnicode(String str) {
        char[] arChar = str.toCharArray();
        int iValue = 0;
        String uStr = "";
        for (int i = 0 ; i < arChar.length ; i++) {
            iValue = (int) str.charAt(i);
            if (iValue <= 256) {
                uStr += "\\u00" + Integer.toHexString(iValue);
            }
            else {
                uStr += "\\u" + Integer.toHexString(iValue);
            }
        }
        return uStr;
    }

    /**
     * "gb2312"到"ISO-8859-1"的转码
     *
     * @param str
     * @return
     */
    public static String iso2gb(String str) {
        try {
            str = new String(str.getBytes("ISO-8859-1"), "gb2312");
        }
        catch (Exception e) {
            System.out.println("Encoding Error!");
        }
        return str;
    }

    /**
     * "ISO-8859-1"到"gb2312"的转码
     *
     * @param str
     * @return
     */
    public static String gb2iso(String str) {
        try {
            str = new String(str.getBytes("gb2312"), "ISO-8859-1");
        }
        catch (Exception e) {
            System.out.println("Encoding Error!");
        }
        return str;
    }

    /**
     *
     * @param InputString
     * @return
     * @throws Exception
     */
    public static String getChinese(String InputString) throws Exception {
        byte b[] = InputString.getBytes("ISO-8859-1");
        String OutputString = new String(b);
        return OutputString;
    }

    /**
     * 字符串中有多少相同字符
     *
     * @param str
     *            原字符串
     * @param temp
     *            计算字符
     * @return 个数
     */
    public static int getLength(String str, String temp) {
        int leng = 0;
        if (str != null && !str.equals("") && temp != null && !temp.equals("")) {
            int a = str.length();
            str = str.replaceAll(temp, "");
            int b = str.length();
            leng = a - b;
        }
        return leng;
    }

    public static String getRandomString(String prefix, int length) {
        StringBuffer result = new StringBuffer(length);
        if (prefix != null)
            result.append(prefix);
        String bound = "_";
        java.util.Random rnd = new java.util.Random();

        while (result.length() < length) {
            int n = rnd.nextInt(255);
            char c = (char) n;
            if ((n >= 48 && n <= 57) || (n >= 65 && n <= 90) || (n >= 97 && n <= 122) || (isIn(c, bound))) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static boolean isIn(char c, String bound) {
        if (bound == null)
            return false;
        for (int i = 0 ; i < bound.length() ; i++) {
            if (bound.indexOf(i) == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * 组装完整时间
     *
     * @param date
     *            日期 YYYY-MM-DD： 2012-07-09
     * @param hour
     *            小时 hh：18
     * @param minute
     *            分钟 mm: 12
     * @param second
     *            秒 ss ： 00
     * @return
     */
    public static String getFullDateTime(String date, String hour, String minute, String second) {
        return date + " " + hour + ":" + minute + ":" + second;
    }

    /**
     * 检查是否是double类型
     *
     * @param str
     * @return
     */
    public static boolean checkDouble(String str) {
        try {
            if (StringUtils.isEmpty(str))
                return false;
            Double.parseDouble(str);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * 检查是否是数字类型
     *
     * @param str
     * @return
     */
    public static boolean checkNumber(String str) {
        try {
            if (StringUtils.isEmpty(str))
                return false;
            Integer.parseInt(str);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * 字符转换成 HTML语言
     *
     * @param args
     */
    public static String convertHTML(String sql) {

        if (StringUtils.isEmpty(sql))
            return sql;
        sql = sql.replaceAll("\r\n", "<br />");
        sql = sql.replaceAll("\n", "<br />");
        return sql;
    }

    /**
     * 过滤utf8不支持的文字
     * @param txt
     * @return
     */
    public static String encodeFilter(String txt) {
        if (StringUtils.isEmpty(txt)) {
            return txt;
        }
        char[] chars = txt.toCharArray();
        CharsetEncoder encoder = java.nio.charset.Charset.forName("utf-8").newEncoder();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            boolean f = encoder.canEncode(c);
            if (f) {
                sb.append(c);
            }
        }
        txt = sb.toString();
        return txt;
    }

    /**
     * 字符转换成 HTML语言
     *
     * @param args
     */
    public static String decodeHTML(String str) {

        if (StringUtils.isEmpty(str))
            return str;
        str = str.replaceAll("<br />", "\n").replaceAll("<br/>", "\n").replaceAll("<br>", "\n");
        return str;
    }

    /**
     * 过滤json字符串的特殊字符 \b \t \n \f \r
     * @param str
     * @return String 安全的字符串
     */
    public static String jsonFilter(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        str = str.replaceAll("\b", "\\\b").replaceAll("\t", "\\\t").replaceAll("\n", "\\\n").replaceAll("\f", "\\\f").replaceAll("\r", "\\\r");
        return str;
    }

    /**
     * 将文本中的http:// 和 https://转换成超链接<a>标签形式 
     * @param txt
     * @param _http
     * @return
     */
    public static String httpAndHttpsLink(String txt, String _http) {
        int length = txt.length();
        int startPos = -1;
        int endPos = -1;
        startPos = txt.indexOf(_http);
        if (startPos >= 0) {
            String tailStr = txt.substring(startPos, txt.length());
            endPos = tailStr.indexOf(" ");
        }
        if (startPos >= 0 && endPos > 0) {
            String firstSegContent = txt.substring(0, startPos);
            String targetContext = txt.substring(startPos, endPos + startPos);
            String lastSegContent = txt.substring(endPos + startPos, length);
            return firstSegContent + "<a href=\'" + targetContext + "\' style=\'color:#047ECB\' target=\'_blank\'>" + targetContext + "</a>" + httpAndHttpsLink(lastSegContent, _http);
        }
        return txt;
    }

    /**
     * 将排序条件的Map转换为排序字符串,得到字符串：ARTICLE_TITLE asc,ARTICLE_CONTENT desc
     * @return
     */
    public static String getSortByMap(Map<String, Object> map) {
        StringBuffer sortString = new StringBuffer("");
        Set<String> key = map.keySet();
        for (Iterator it = key.iterator() ; it.hasNext() ;) {
            String s = (String) it.next();
            sortString.append(" " + columnString(s) + " " + map.get(s) + ",");
            System.out.println(map.get(s));
        }

        return sortString.substring(0, sortString.length() - 1);
    }

    /**
     * 将排序条件的Map转换为排序字符串,例如将articleTitle转为ARTICLE_TITLE
     * @return
     */
    public static String columnString(String s) {
        StringBuffer returnStr = new StringBuffer("");
        for (int i = 0 ; i < s.length() ; i++) {
            char a = s.charAt(i);
            String b = "" + a;
            if (a > 64 && a < 91) { //大写字母的ASCLL码取值范围                
                returnStr.append("_" + a);
            }
            else {
                returnStr.append((a + "").toUpperCase()); //小写转大写                
            }
        }
        return returnStr.toString();
    }

    /**
     * 将驼峰命名法的字段转换成数据库字段
     * @param name
     * @return
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1 ; i < name.length() ; i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }

    /**
     * unicode 转换成 中文
     * 
     * @author xujie
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0 ; x < len ;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0 ; i < 4 ; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed      encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                }
                else {
                    if (aChar == 't') {
                        aChar = '\t';
                    }
                    else if (aChar == 'r') {
                        aChar = '\r';
                    }
                    else if (aChar == 'n') {
                        aChar = '\n';
                    }
                    else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            }
            else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * 将Map转换为request请求参数的字符串
     * @return
     */
    public static String getUrlVariables(Map<String, Object> urlVariables) {
        StringBuffer varStringBuffer = new StringBuffer("?");

        Set<String> key = urlVariables.keySet();
        for (Iterator it = key.iterator() ; it.hasNext() ;) {
            String s = (String) it.next();
            varStringBuffer.append(s + "=" + urlVariables.get(s).toString() + "&");
        }

        return varStringBuffer.toString();
    }

    public static void main(String[] args) {

        String returnStr = "{'code':0,'data':{'country':'\u4e2d\u56fd','country_id':'CN','area':'\u534e\u5357','area_id':'800000','region':'\u5e7f\u4e1c\u7701','region_id':'440000','city':'\u5e7f\u5dde\u5e02','city_id':'440100','county':'','county_id':'-1','isp':'\u6559\u80b2\u7f51','isp_id':'100027','ip':'218.192.3.42'}}";
        if (returnStr != null) {
            // 处理返回的省市区信息
            // System.out.println(returnStr);
            String[] temp = returnStr.split(",");
            if (temp.length < 3) {
            }

            String city = (temp[7].split(":"))[1].replaceAll("\"", "");
            city = StringUtils.decodeUnicode(city);// 市区
            System.out.println(city);
        }
    }
}
