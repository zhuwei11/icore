package cn.zw.icore.ap.util;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

public class CommUtil {

    public static final String CommUtil_01 = "类型[%s]不是Map类型不能转换";
    public static final String CommUtil_02 = "进行lpad的添加字符串不能为null且长度不能为0!";
    public static final String CommUtil_03 = "进行lpad的长度无效!";
    public static final String CommUtil_04 = "无效的编码值[%s]";
    public static final String CommUtil_05 = "进行Rpad的添加字符串不能为null且长度不能为0!";
    public static final String CommUtil_06 = "进行Rpad的长度无效!";
    public static final String CommUtil_07 = "like函数参数值无效!";
    public static final String CommUtil_08 = "like函数不支持中间匹配!";
    public static final String CommUtil_09 = "In函数参数值无效!";
    public static final String CommUtil_10 = "In函数参数类型必须相同!";
    public static final String CommUtil_11 = "Between函数参数值无效!";
    public static final String CommUtil_12 = "Between函数参数类型必须相同!";
    public static final String CommUtil_13 = "round精度不能为负数!";
    public static final String CommUtil_14 = "Floor参数不能为null!";
    public static final String CommUtil_15 = "Ceil参数不能为null!";
    public static final String CommUtil_16 = "toMap转换异常!";
    public static final String CommUtil_17 = "卡号/账号格式不符合规则!";
    public static Map<String, Object> toMap(Object bean) {
        if (bean == null) {
            return null;
        } else if (bean instanceof Map) {
            return (Map) bean;
        } else {
            throw new IllegalArgumentException(CommUtil_16);
        }
    }

    public static List<Map<String, Object>> toListMap(List<?> list) {
        if (list == null) {
            return null;
        } else {
            List<Map<String, Object>> ret = new ArrayList();
            Iterator var2 = list.iterator();

            while (var2.hasNext()) {
                Object o = var2.next();
                ret.add(toMap(o));
            }

            return ret;
        }
    }

//    public static void copyProperties(Object dest, Object src) {
//        copyProperties(src, dest, true);
//    }
//
//    public static void copyPropertiesWithTypeConvert(Object dest, Object src) {
//        copyPropertiesWithTypeConvert(dest, src, true);
//    }
//
//    public static void copyPropertiesWithTypeConvert(Object dest, Object src, boolean withNoEmpty) {
//        BaseBeanUtil.copyProperties(src, dest, withNoEmpty, (List) null, true);
//    }
//
//    public static void copyProperties(Object src, Object dest, boolean withNoEmpty) {
//        BaseBeanUtil.copyProperties(src, dest, withNoEmpty, (List) null, false);
//    }
//
//    public static void copyElement(Object src, Object dest, boolean withNoEmpty) {
//        BaseBeanUtil.copyProperties(src, dest, withNoEmpty, (List) null, false);
//    }
//
//    public static void copyPropertyList(Object dest, Object src, String... propList) {
//        BaseBeanUtil.copyProperties(src, dest, true, Arrays.asList(propList), false);
//    }

    public static <T> T nvl(T s, T defaultValue) {
        return isNull(s) ? defaultValue : s;
    }

    public static boolean isNull(Object o) {
        if (isEmptyOrBlank(o,false)) {
            return true;
        }
        return false;
    }

    private static boolean isEmptyOrBlank(Object obj, boolean trim) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            String ss = (String) obj;
            return (trim ? ss.trim() : ss).length() == 0;
        } else if (obj instanceof Object[]) {
            return isEmptyOrBlankObj(obj, trim);
        } else if (obj instanceof Collection) {
            return isEmptyOrBlankCollection(obj, trim);
        } else {
            return obj instanceof Map ? ((Map) obj).isEmpty() : false;
        }
    }

    private static boolean isEmptyOrBlankCollection(Object obj, boolean trim) {
        Collection<Object> oo = (Collection) obj;
        Iterator i = oo.iterator();

        do {
            if (!i.hasNext()) {
                return true;
            }
        } while (isEmptyOrBlank(i.next(), trim));

        return false;
    }

    private static boolean isEmptyOrBlankObj(Object obj, boolean trim) {
        Object[] oo = (Object[]) ((Object[]) obj);

        for (int i = 0; i < oo.length; ++i) {
            if (!isEmptyOrBlank(oo[i], trim)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNotNull(Object o) {
        return !isNull(o);
    }

    public static String trim(String s) {
        return s == null ? null : s.trim();
    }

    public static String rtrim(String s) {
        if (s == null) {
            return null;
        } else {
            StringBuffer sb = new StringBuffer(s);

            for (int i = sb.length() - 1; i >= 0 && sb.charAt(i) == ' '; --i) {
                sb.deleteCharAt(i);
            }

            return sb.toString();
        }
    }

    public static String ltrim(String s) {
        if (s == null) {
            return null;
        } else {
            String ret = "";

            for (int i = 0; i < s.length(); ++i) {
                if (s.charAt(i) != ' ') {
                    ret = s.substring(i);
                    break;
                }
            }

            return ret;
        }
    }

    public static String lpad(String s, int i, String s1) {
        if (s == null) {
            return null;
        } else if (s1 != null && s1.length() > 0) {
            if (i <= 0) {
                throw new IllegalArgumentException(CommUtil_03);
            } else if (i <= s.length()) {
                return s.substring(0, i);
            } else {
                StringBuffer sb = new StringBuffer(s);
                char[] c1 = s1.toCharArray();
                boolean var5 = false;

                while (sb.length() < i) {
                    int index = 0;

                    while (sb.length() < i && index < c1.length) {
                        sb.insert(0, c1[index++]);
                    }
                }

                return sb.toString();
            }
        } else {
            throw new IllegalArgumentException(CommUtil_02);
        }
    }

    public static String lpad(String s, int i, String s1, String enCoding) {
        if (s == null) {
            return null;
        } else if (s1 != null && s1.length() > 0) {
            if (i <= 0) {
                throw new IllegalArgumentException(CommUtil_03);
            } else {
                try {
                    byte[] pad = s1.getBytes(enCoding);
                    byte[] by = s.getBytes(enCoding);
                    byte[] des = new byte[i];
                    int srcLen = by.length;
                    int padLen = pad.length;
                    if (i <= srcLen) {
                        System.arraycopy(by, 0, des, 0, i);
                        return new String(des, enCoding);
                    } else {
                        System.arraycopy(by, 0, des, i - srcLen, srcLen);

                        for (; srcLen < i; srcLen += padLen) {
                            if (padLen + srcLen > i) {
                                System.arraycopy(pad, 0, des, 0, i - srcLen);
                            } else {
                                System.arraycopy(pad, 0, des, i - srcLen - padLen, padLen);
                            }
                        }

                        return new String(des, enCoding);
                    }
                } catch (UnsupportedEncodingException var9) {
                    throw new IllegalArgumentException(String.format(CommUtil_04, enCoding));
                }
            }
        } else {
            throw new IllegalArgumentException(CommUtil_02);
        }
    }

    public static String rpad(String s, int i, String s1) {
        if (s == null) {
            return null;
        } else if (s1 != null && s1.length() > 0) {
            if (i <= 0) {
                throw new IllegalArgumentException(CommUtil_06);
            } else if (i <= s.length()) {
                return s.substring(0, i);
            } else {
                StringBuffer sb = new StringBuffer(s);
                char[] c1 = s1.toCharArray();
                boolean var5 = false;

                while (sb.length() < i) {
                    int index = 0;

                    while (sb.length() < i && index < c1.length) {
                        sb.append(c1[index++]);
                    }
                }

                return sb.toString();
            }
        } else {
            throw new IllegalArgumentException(CommUtil_05);
        }
    }

    public static String rpad(String s, int i, String s1, String enCoding) {
        if (s == null) {
            return null;
        } else if (s1 != null && s1.length() > 0) {
            if (i <= 0) {
                throw new IllegalArgumentException(CommUtil_06);
            } else {
                try {
                    byte[] pad = s1.getBytes(enCoding);
                    byte[] by = s.getBytes(enCoding);
                    byte[] des = new byte[i];
                    int srcLen = by.length;
                    int padLen = pad.length;
                    if (i <= by.length) {
                        System.arraycopy(by, 0, des, 0, i);
                        return new String(des, enCoding);
                    } else {
                        System.arraycopy(by, 0, des, 0, srcLen);

                        for (; srcLen < i; srcLen += padLen) {
                            if (padLen + srcLen > i) {
                                System.arraycopy(pad, 0, des, srcLen, i - srcLen);
                            } else {
                                System.arraycopy(pad, 0, des, srcLen, padLen);
                            }
                        }

                        return new String(des, enCoding);
                    }
                } catch (UnsupportedEncodingException var9) {
                    throw new IllegalArgumentException(String.format(CommUtil_04, enCoding));
                }
            }
        } else {
            throw new IllegalArgumentException(CommUtil_05);
        }
    }

    public static boolean like(String s1, String s2) {
        if (s1 != null && s2 != null) {
            int len = s2.length();
            boolean startWith = false;
            boolean endWith = false;
            if (s2.charAt(0) == '%') {
                startWith = true;
            }

            int p = s2.indexOf(37, 1);
            if (p > 0 && p < s2.length() - 1) {
                throw new IllegalArgumentException(CommUtil_08);
            } else {
                if (s2.charAt(len - 1) == '%') {
                    endWith = true;
                }

                s2 = s2.replace("%", "");
                if (startWith && endWith) {
                    return s1.indexOf(s2) >= 0;
                } else if (startWith) {
                    return s1.endsWith(s2);
                } else {
                    return endWith ? s1.startsWith(s2) : s1.equals(s2);
                }
            }
        } else {
            throw new IllegalArgumentException(CommUtil_07);
        }
    }

//    public static boolean in(Object a, Object... a1) {
//        if (a == null) {
//            return false;
//        } else if (a1 != null && a1.length > 0) {
//            for (int i = 0; i < a1.length; ++i) {
//                if (!a.getClass().isAssignableFrom(a1[i].getClass())) {
//                    throw new IllegalArgumentException(CommUtil_10);
//                }
//
//                if (compare(a, a1[i]) == 0) {
//                    return true;
//                }
//            }
//
//            return false;
//        } else {
//            throw new IllegalArgumentException(CommUtil_09);
//        }
//    }

    public static <T> boolean in(T object, List<T> objects) {
        return objects.indexOf(object) >= 0;
    }

//
//    public static boolean Between(Object a, Object start, Object end) {
//        if (a != null && start != null && end != null) {
//            if (a.getClass().isAssignableFrom(start.getClass()) && a.getClass().isAssignableFrom(end.getClass())) {
//                return compare(a, start) >= 0 && compare(a, end) <= 0;
//            } else {
//                throw new IllegalArgumentException(CommUtil_12);
//            }
//        } else {
//            throw new IllegalArgumentException(CommUtil_11);
//        }
//    }

//    public static <T extends Comparable<? super T>> int compare(T o1, T o2, boolean ignoreCase,
//                                                                boolean ignoreNullAndEmpty) {
//        return ComparableUtil.compare(o1, o2, ignoreCase, ignoreNullAndEmpty);
//    }
//
//    public static int compare(Object o1, Object o2) {
//        return ComparableUtil.compare(o1, o2);
//    }
//
//    public static BigDecimal trunc(BigDecimal am) {
//        return am == null ? null : ConvertUtil.toBigDecimal(am, (BigDecimal) null);
//    }

    public static BigDecimal round(BigDecimal amt, int scale) {
        return round(amt, scale, 4);
    }

    public static BigDecimal round(BigDecimal amt, int scale, int roundingMode) {
        if (amt == null) {
            return null;
        } else if (scale < 0) {
            throw new IllegalArgumentException(CommUtil_13);
        } else {
            return amt.setScale(scale, roundingMode);
        }
    }

    public static long floor(Object val) {
        if (val == null) {
            throw new IllegalArgumentException(CommUtil_14);
        } else if (val instanceof BigDecimal) {
            return ((BigDecimal) val).longValue();
        } else if (val instanceof Double) {
            return (long) Math.floor((Double) val);
        } else if (val instanceof Float) {
            return (long) Math.floor((double) (Float) val);
        } else {
            return val instanceof Long ? (Long) val : 0L;
        }
    }

    public static long ceil(Object val) {
        if (val == null) {
            throw new IllegalArgumentException(CommUtil_15);
        } else if (val instanceof BigDecimal) {
            return round((BigDecimal) val, 0, 2).longValue();
        } else if (val instanceof Double) {
            return (long) Math.ceil((Double) val);
        } else {
            return val instanceof Float ? (long) Math.ceil((double) (Float) val) : 0L;
        }
    }

//    public static boolean equals(Object o1, Object o2) {
//        return compare(o1, o2) == 0;
//    }

    public static Map<String, Object> shrinkHttpParameters(Map<String, String[]> parameters) {
        boolean decode = parameters.containsKey("q:");
        Map<String, Object> ret = new HashMap();
        Iterator var3 = parameters.entrySet().iterator();

        while (true) {
            while (var3.hasNext()) {
                Entry<String, String[]> entry = (Entry) var3.next();
                String[] value = (String[]) entry.getValue();
                if (value.length == 1) {
                    ret.put(entry.getKey(), convertHtmlParameter(value[0], decode));
                } else {
                    List<String> values = new ArrayList();
                    String[] var8 = value;
                    int var9 = value.length;

                    for (int var10 = 0; var10 < var9; ++var10) {
                        String v = var8[var10];
                        if (isNotNull(v)) {
                            values.add(convertHtmlParameter(v, decode));
                        }
                    }

                    ret.put(entry.getKey(), values.toArray(new String[values.size()]));
                }
            }

            return ret;
        }
    }

    private static String convertHtmlParameter(String p, boolean decode) {
        if (decode) {
            try {
                p = new String(p.getBytes("ISO8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException var3) {
                throw new RuntimeException(var3);
            }
        }

        if (isNotNull(p)) {
            p = p.replaceAll("(?im)<([\\s/]*(script|iframe|img))", "&lt;$1");
        }

        return p;
    }

    /**
     * @Author chensy
     *         <p>
     *         <li>2016年12月13日-上午9:57:21</li>
     *         <li>功能说明：生成账\卡号校验位，模10隔位乘2加"校验位算法。</li>
     *         </p>
     * @param cardno
     *        不含校验位的账号(卡号)
     * @return 校验位
     */
    public static int genCardnoCheckBit(String cardno) {
//        fieldNotNull(cardno, "acct_no", "account no");
        cardno = CommUtil.trim(cardno);

        if (!cardno.matches("^[0-9]*$")) {
            throw new IllegalArgumentException(CommUtil_17);
        }

        int sum = 0;
        int temp = 0;
        char[] array = cardno.toCharArray();
        for (int i = 0; i < array.length; i++) {
            if (i % 2 == 0) {
                temp = (Character.getNumericValue(array[i]) * 2);
                if (temp > 9) {
                    temp = temp - 9;
                }
            } else {
                temp = Character.getNumericValue(array[i]);
            }
            sum += temp;
        }

        int parity = 10 - sum % 10;
        if (parity == 10) {
            parity = 0;
        }

        return parity;
    }
}
