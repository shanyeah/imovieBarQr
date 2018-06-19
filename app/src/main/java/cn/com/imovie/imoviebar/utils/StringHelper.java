package cn.com.imovie.imoviebar.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

/**
 * <p>Title: 锟街凤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p><p>
 * CreateTime: 2005-4-30 21:06:58
 * @author 21锟斤拷锟酵的匡拷锟斤拷 topilee@gmail.com
 * @version 1.3
 */
public class StringHelper {
    private static final String TAG = "XMLDataGetter";
    /**
     * 锟斤拷锟街凤拷锟斤拷转锟斤拷锟斤拷锟斤拷锟斤拷
     * @param in String : 要转锟斤拷锟斤拷锟街凤拷锟斤拷"a"
     * @param defaultValue int : 默锟斤拷值0
     * @return int : 锟斤拷锟斤拷址锟斤拷锟斤拷戏锟斤拷锟斤拷锟斤拷锟阶拷锟斤拷锟斤拷蚍祷锟阶拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟津返伙拷默锟斤拷值
     */
    public static int parseInt(final String in, final int defaultValue){
        if(in == null) return defaultValue;
        try{
            return Integer.parseInt(in);
        }catch(NumberFormatException nfe){
            VV8Utils.printLog(TAG,"parseInt(" + in + "):NumberFormatException-->" + nfe.getMessage());
            return defaultValue;
        }
    }
    public static Integer parseInteger(final String in,final int defaultValue){
        return new Integer(parseInt(in,defaultValue));
    }
    /**
     * 锟叫讹拷锟角凤拷为锟斤拷确锟斤拷锟斤拷锟斤拷锟街?
     * @param in String : 锟斤拷锟斤拷锟街?
     * @return boolean : 锟斤拷锟斤拷呛戏锟斤拷锟斤拷锟斤拷址锟斤拷锟斤拷true锟斤拷锟斤拷锟津返伙拷false
     */
    public static boolean isEmail(final String in){
        if(StringHelper.isEmpty(in)) return false;
        int at = in.indexOf("@");
        int dot = in.indexOf(".");
        if(at < 0 || dot < 0) return false;
        if((at + 1) >= dot) return false;
        if((dot + 1) >= in.length()) return false;
        return true;
    }
    /**
     * 锟斤拷锟街凤拷锟斤拷转锟斤拷锟斤拷double
     * @param in String : 要转锟斤拷锟斤拷锟街凤拷锟斤拷
     * @param defaultValue double : 默锟斤拷值
     * @return double : 锟斤拷锟斤拷址锟斤拷锟斤拷锟斤拷锟阶拷锟斤拷锟斤拷蚍祷锟阶拷锟斤拷锟斤拷double锟斤拷锟斤拷锟津返伙拷默锟斤拷值
     */
    public static double parseDouble(final String in, final double defaultValue){
        if(in == null) return defaultValue;
        try{
            return Double.parseDouble(in);
        }catch(NumberFormatException nfe){
            VV8Utils.printLog(TAG,"parseDouble(" + in + "):NumberFormatException-->" + nfe.getMessage());
            return defaultValue;
        }
    }

    /**
     * 锟斤拷锟街凤拷锟斤拷转锟斤拷锟斤拷float
     * @param in String : 要转锟斤拷锟斤拷锟街凤拷锟斤拷
     * @param defaultValue double : 默锟斤拷值
     * @return double : 锟斤拷锟斤拷址锟斤拷锟斤拷锟斤拷锟阶拷锟斤拷锟斤拷蚍祷锟阶拷锟斤拷锟斤拷double锟斤拷锟斤拷锟津返伙拷默锟斤拷值
     */
    public static float parseFloat(final String in, final float defaultValue){
        if(in == null) return defaultValue;
        try{
            return Float.parseFloat(in);
        }catch(NumberFormatException nfe){
            VV8Utils.printLog(TAG,"parseFloat(" + in + "):NumberFormatException-->" + nfe.getMessage());
            return defaultValue;
        }
    }
    /**
     * 锟斤拷锟街节猴拷锟斤拷锟饺★拷址锟斤拷锟?锟斤拷锟斤拷<br>
     * truncate("china,锟斤拷锟斤拷锟叫癸拷锟斤拷",5) = "china"; <br>
     * truncate("china,锟斤拷锟斤拷锟叫癸拷锟斤拷",8) = "china,锟斤拷"; <br>
     * truncate("china,锟斤拷锟斤拷锟叫癸拷锟斤拷",9) = "china,锟斤拷"; <br>
     * @param in String : 锟斤拷锟斤拷取锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟侥硷拷锟街凤拷
     * @param length int : 要锟斤拷取锟斤拷锟街凤拷锟斤拷锟街斤拷锟斤拷
     * @return String : 锟斤拷取锟斤拷锟斤拷址锟斤拷锟?
     */
    public static String truncate(final String in, final int length){
        return truncate(in,length,"");
    }
    /**
     * 锟斤拷锟街节猴拷锟斤拷锟饺★拷址锟斤拷锟?锟斤拷锟斤拷<br>
     * truncate("china,锟斤拷锟斤拷锟叫癸拷锟斤拷",5) = "china"; <br>
     * truncate("china,锟斤拷锟斤拷锟叫癸拷锟斤拷",8) = "china,锟斤拷"; <br>
     * truncate("china,锟斤拷锟斤拷锟叫癸拷锟斤拷",9) = "china,锟斤拷"; <br>
     * @param in String : 锟斤拷锟斤拷取锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟侥硷拷锟街凤拷
     * @param length int : 要锟斤拷取锟斤拷锟街凤拷锟斤拷锟街斤拷锟斤拷
     * @param ender String : 锟斤拷锟斤拷锟斤拷
     * @return String : 锟斤拷取锟斤拷锟斤拷址锟斤拷锟?
     */
    public static String truncate(final String in,final int length,final String ender){
        if(length <0 || in == null || in.length() <= length ) return in;
        int index = getMaxTruncateLength(in, length);
        return in.substring(0, index) + ender;
    }
    /**
     * 取锟矫可斤拷取锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷蟪ざ锟?
     * @param in String : 锟斤拷锟斤拷取锟斤拷锟街凤拷锟叫ｏ拷锟斤拷锟斤拷锟斤拷锟侥碉拷
     * @param length int : 锟斤拷锟斤拷取锟斤拷锟街凤拷锟斤拷锟街斤拷锟斤拷
     * @return int : 锟斤拷锟缴斤拷取锟侥筹拷锟斤拷
     */
    public static int getMaxTruncateLength(final String in, final int length){
        if(in == null || in.trim().length() <= 0) return 0;
        int len = in.getBytes().length;
        if(len <= length) return in.length();
        byte[] bytes = in.getBytes();
        int count = 0;
        for (int i = 0; i < length; i++){
            if(bytes[i] < 0){
                count++;
            }
        }
        if(count % 2 == 0){
            return length - count / 2;
        }else{
            return length - 1 - count / 2;
        }
    }
    /**
     * 锟街凤拷锟斤拷锟芥换锟斤拷锟界： <br>
     * replace("ABCDEFGHIJKL",1,"222")="A222EFGHIJKL";
     * @param mother String : 锟斤拷锟芥换锟斤拷源锟斤拷
     * @param index int : 锟斤拷始锟芥换锟斤拷锟斤拷牛锟斤拷锟?锟斤拷始
     * @param child String : 锟芥换锟街凤拷锟斤拷
     * @return String : 锟芥换锟斤拷锟斤拷址锟斤拷锟?
     */
    public static String replace(final String mother, final int index, final String child){
        if(index < 0 || index >= mother.length()) return mother;
        StringBuffer out = new StringBuffer();
        //前缀
        if(index > 0){
            out.append(mother.substring(0, index));
        }
        //锟叫硷拷锟芥换
        if(index + child.length() > mother.length()){
            out.append(child.substring(0, mother.length() - index));
        }else{
            out.append(child);
            //锟斤拷缀
            out.append(mother.substring(index + child.length()));
        }
        return out.toString();
    }
    /**
     * 锟叫讹拷锟街凤拷锟斤拷锟角凤拷为锟秸伙拷锟角空达拷
     * @param in String : 锟斤拷锟叫断碉拷锟街凤拷锟斤拷
     * @return boolean : 为锟秸伙拷为锟秸达拷锟斤拷锟斤拷锟斤拷true锟斤拷锟斤拷锟津返伙拷false
     */
    public static boolean isEmpty(final String in){
        if(in == null || in.trim().length() <= 0) return true;
        return false;
    }
    /**
     * 锟斤拷示锟斤拷锟斤拷锟斤拷锟斤拷
     * @param in Object[] : 锟斤拷锟斤拷
     * @return String : 锟斤拷锟斤拷锟斤拷锟?
     */
    public static String toString(final Object[] in){
        StringBuffer desc = new StringBuffer();
        desc.append("[");
        if(in != null){
            for(int i=0;i<in.length;i++){
                desc.append(in[i]).append(";");
            }
        }
        desc.append("]");
        return desc.toString();
    }
    /**
     * 取锟斤拷锟街凤拷锟斤拷指锟斤拷锟斤拷锟斤拷锟斤拷址锟斤拷锟绞?
     * @param in String : 源锟街凤拷锟斤拷
     * @param charset String : 锟斤拷锟斤拷
     * @return String : 锟斤拷锟斤拷锟斤拷应锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷锟街革拷锟斤拷谋锟斤拷氩伙拷戏锟斤拷锟斤拷蚍祷锟皆达拷址锟斤拷锟?
     */
    public static String getBytes(final String in, final String charset){
        if(in == null) return null;
        try{
            return new String(in.getBytes(charset));
        }catch(UnsupportedEncodingException uee){
            VV8Utils.printLog(TAG,"getBytes(" + in + "," + charset + "):UnsupportedEncodingException-->" + uee.getMessage());
            return in;
        }
    }
    /**
     * 锟叫讹拷锟街凤拷锟斤拷锟斤拷锟街斤拷锟斤拷锟角否超筹拷锟斤拷锟斤拷锟街斤拷锟斤拷
     * @param in String : 锟斤拷锟叫断碉拷锟街凤拷锟斤拷
     * @param length int : 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟街斤拷锟斤拷
     * @return boolean : 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷疲锟斤拷锟斤拷锟絫rue锟斤拷锟斤拷锟津返伙拷false
     */
    public static boolean isExceed(final String in, final int length){
        if(in == null || length <= 0) return false;
        if(in.getBytes().length <= length) return false;
        return true;
    }
    /**
     * 锟斤拷锟杰撅拷锟斤拷锟斤拷 <br>
     * String mother = "child1@@@child2@@@child3"; <br>
     * add(mother,"@@@","child1") = "child1@@@child2@@@child3"; <br>
     * add(mother,"@@@","child4") = "child1@@@child2@@@child3@@@child4"; <br>
     * add(mother,"@@@@","child1") = "child1@@@child2@@@child3@@@@child1"; <br>
     * @param mother String : 原锟斤拷锟街凤拷锟斤拷
     * @param splitter String : 锟街革拷锟斤拷
     * @param child String : 锟斤拷锟斤拷锟街凤拷锟斤拷
     * @return String : 锟斤拷锟捷癸拷锟津返回合诧拷锟斤拷锟斤拷址锟斤拷锟?
     */
    public static String add(final String mother, final String splitter, final String child){
        return add(mother, splitter, child, false);
    }
    /**
     * 锟斤拷指锟斤拷锟街革拷锟斤拷拼锟斤拷锟街凤拷锟斤拷
     * @param mother String : 原锟斤拷锟街凤拷锟斤拷
     * @param splitter String : 锟街革拷锟斤拷
     * @param child String : 锟斤拷锟斤拷锟街凤拷锟斤拷
     * @param duplicate boolean : 锟角凤拷锟斤拷锟斤拷锟斤拷锟截革拷锟斤拷锟街达拷
     * @return String : 锟斤拷锟捷癸拷锟津返回合诧拷锟斤拷锟斤拷址锟斤拷锟?
     */
    public static String add(final String mother, final String splitter, final String child, final boolean duplicate){
        if(mother == null) return child;
        StringBuffer ret = new StringBuffer();
        ret.append(mother);
        //锟斤拷锟斤拷child锟截革拷
        if(duplicate == true){
            ret.append(splitter).append(child);
        }else{
            if(mother.indexOf(child) < 0){
                ret.append(splitter).append(child);
            }
        }
        return ret.toString();
    }
    /**
     * 锟斤拷锟杰撅拷锟斤拷锟斤拷 <br>
     * String mother = "child1@@@child2@@@child3"; remove(mother,"@@@","child1") = "child2@@@child3"; <br>
     * remove(mother,"@@@","child4") = "child1@@@child2@@@child3"; <br>
     * remove(mother,"@@@@","child1") = "child1@@@child2@@@child3"; <br>
     * @param mother String : 锟斤拷锟斤拷锟斤拷锟斤拷址锟斤拷锟?
     * @param splitter String : 锟街革拷锟斤拷
     * @param child String : 锟斤拷锟街凤拷锟斤拷
     * @return String : 锟斤拷锟斤拷锟狡筹拷锟斤拷锟斤拷址锟斤拷锟?
     */
    public static String remove(final String mother, final String splitter, final String child){
        if(isEmpty(child) || isEmpty(mother)) return mother;
        StringBuffer ret = new StringBuffer();
        int index = mother.indexOf(child);
        if(index < 0) return mother;
        if(index == 0){
            if(index + child.length() + splitter.length() < mother.length()){
                ret.append(mother.substring(index + child.length() + splitter.length()));
            }else{
                return null;
            }
        }else{
            ret.append(mother.substring(0, index - splitter.length()));
            if(index + child.length() + splitter.length() < mother.length()){
                ret.append(mother.substring(index + child.length()));
            }
        }
        return ret.toString();
    }
    /**
     * 锟斤拷锟街凤拷锟斤拷转锟斤拷锟斤拷long
     * @param in String : 要转锟斤拷锟斤拷锟街凤拷锟斤拷
     * @param defaultValue long : 默锟斤拷值
     * @return long : 锟斤拷锟斤拷址锟斤拷锟斤拷锟斤拷锟阶拷锟斤拷锟斤拷蚍祷锟阶拷锟斤拷锟斤拷long锟斤拷锟斤拷锟津返伙拷默锟斤拷值
     */
    public static long parseLong(final String in, final long defaultValue){
        if(in == null) return defaultValue;
        try{
            return Long.parseLong(in);
        }catch(NumberFormatException nfe){
            VV8Utils.printLog(TAG,"parseLong(" + in + "):NumberFormatException-->" + nfe.getMessage());
            return defaultValue;
        }
    }
    /**
     * 锟斤拷锟斤拷锟斤拷转锟街凤拷锟斤拷锟斤拷16锟斤拷锟狡ｏ拷
     * @param b byte[] : 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
     * @return String : 转锟斤拷锟斤拷锟斤拷址锟斤拷锟?
     */
    public static String bytes2Hex(final byte[] b){
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++){
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if(stmp.length() == 1) hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs;
    }
    /**
     * 十锟斤拷锟斤拷锟斤拷锟街凤拷锟斤拷转锟斤拷锟斤拷byte[]锟斤拷摘锟斤拷锟斤拷锟铰革拷
     * @param in String : 十锟斤拷锟斤拷锟斤拷锟街凤拷锟斤拷
     * @return byte[] : 转锟斤拷锟斤拷锟絙yte[]
     */
    public static byte[] hex2Bytes(String in){
        byte buf[] = new byte[in.length() / 2];
        char chars[] = in.toCharArray();
        for (int i = 0, j = 0; i < buf.length; i++){
            char c1 = chars[j++];
            char c2 = chars[j++];
            buf[i] = getByte(getValue(c1), getValue(c2));
        }
        return buf;
    }
    /**
     * 锟矫碉拷十锟斤拷锟斤拷锟斤拷锟街凤拷锟斤拷应锟斤拷锟斤拷值锟斤拷摘锟斤拷锟斤拷锟铰革拷
     * @param c char : [0,9],[a,f],[A,F]
     * @return byte : 锟斤拷应锟斤拷值锟斤拷byte锟斤拷示
     */
    public static byte getValue(char c){
        if(c >= '0' && c<='9') return (byte)(c-'0');
        if(c >= 'a' && c<='f') return (byte)(c-'a'+10);
        if(c >= 'A' && c<='F') return (byte)(c-'A'+10);
        return (byte)0;
    }
    //摘锟斤拷锟斤拷锟铰革拷
    private static byte getByte(byte b1, byte b2){
        return (byte) (b1 << 4 | b2);
    }
    /**
     * 锟叫讹拷锟街凤拷锟斤拷锟角凤拷为锟角凤拷锟街凤拷
     * @param in String : 锟斤拷锟叫断碉拷锟街凤拷锟斤拷
     * @param illegal String : 锟角凤拷锟街凤拷锟斤拷
     * @return boolean : 锟斤拷锟斤拷址锟斤拷锟斤拷诤锟斤拷锟斤拷锟揭伙拷欠锟斤拷址锟斤拷锟斤拷锟斤拷锟絫rue,锟斤拷锟津返伙拷false
     */
    public static boolean isIllegal(final String in, final String illegal){
        if(illegal == null || illegal.length() <= 0) return false;
        if(in == null || in.length() <= 0) return false;
        for (int i = 0; i < illegal.length(); i++){
            char c = illegal.charAt(i);
            if(in.indexOf(c) >= 0) return true;
        }
        return false;
    }
    /**
     * 锟街凤拷锟斤拷锟角凤拷戏锟?
     * @param in String : 锟斤拷锟叫断碉拷锟街凤拷锟斤拷
     * @param legal String : 锟斤拷锟叫的合凤拷锟街凤拷
     * @return boolean : 锟斤拷锟斤拷址锟斤拷锟斤拷锊伙拷锟斤拷魏畏欠锟斤拷址锟斤拷锟斤拷蚍祷锟絫ure,锟斤拷锟津返伙拷false
     */
    public static boolean isLegal(final String in, final String legal){
        if(legal == null || legal.length() <= 0) return false;
        if(in == null || in.length() <= 0) return true;
        for (int i = 0; i < in.length(); i++){
            char c = in.charAt(i);
            if(legal.indexOf(c) < 0) return false;
        }
        return true;
    }

    public static final String ALL_LOWER_LETTER = "abcdefghijklmnopqrstuvwxyz"; //小写锟斤拷母
    public static final String ALL_UPPER_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //锟斤拷写锟斤拷母
    public static final String ALL_NUMERIC_LETTER = "0123456789"; //锟斤拷锟斤拷
    /**
     * 锟叫讹拷锟街凤拷锟斤拷锟角凤拷为锟斤拷确锟斤拷锟街伙拷锟斤拷锟诫串锟斤拷锟斤拷确锟斤拷锟街伙拷锟斤拷锟斤拷锟斤拷锟斤拷"13"锟斤拷"15"锟斤拷头锟斤拷11位锟斤拷锟斤拷锟街凤拷锟斤拷
     * @param in String : 锟斤拷锟斤拷证锟侥猴拷锟诫串
     * @return boolean : 锟斤拷锟斤拷牵锟斤拷锟斤拷锟絫rue锟斤拷锟斤拷锟津返伙拷false
     */
    public static boolean isMobile(final String in){
        if(in == null || in.length() < 0) return false;
        if(in.length() != 11) return false;
        if(isLegal(in, ALL_NUMERIC_LETTER) == false) return false;
        if(in.startsWith("13") || in.startsWith("15")) return true;
        return false;
    }
    /**
     * 锟叫讹拷锟角凤拷为全锟斤拷锟斤拷锟街凤拷锟斤拷
     * @param in String : 锟街凤拷锟斤拷
     * @return boolean : 锟斤拷锟饺拷锟斤拷锟斤拷锟斤拷锟缴凤拷锟斤拷true锟斤拷锟斤拷锟津返伙拷false
     */
    public static boolean isNumberic(final String in){
        if(in == null || in.length() < 0) return false;
        return isLegal(in,ALL_NUMERIC_LETTER);
    }
    /**
     * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷纱锟斤拷锟斤拷
     * @param in int[] : 锟斤拷锟斤拷锟斤拷锟斤拷
     * @param splitter String : 锟街革拷锟斤拷
     * @return String : 锟缴达拷锟斤拷锟斤拷址锟?
     */
    public static String toBunch(final int[] in,final String splitter){
        if(in == null) return null;
        String[] ins = new String[in.length];
        for(int i=0;i<in.length;i++){
            ins[i] = String.valueOf(in[i]);
        }
        return toBunch(ins,splitter,0);
    }
    /**
     * 锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷锟斤拷纱锟斤拷锟斤拷
     * @param in String[] : 锟街凤拷锟斤拷锟斤拷锟斤拷
     * @param splitter String : 锟街革拷锟斤拷
     * @return String : 锟缴达拷锟斤拷锟斤拷址锟?
     */
    public static String toBunch(final String[] in,final String splitter){
        return toBunch(in,splitter,0);
    }
    /**
     * 锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷锟斤拷纱锟斤拷锟斤拷
     * @param in String[] : 锟街凤拷锟斤拷锟斤拷锟斤拷
     * @param splitter String : 锟街革拷锟斤拷
     * @param start int : 锟斤拷始位锟斤拷
     * @return String : 锟缴达拷锟斤拷锟斤拷址锟?
     */
    public static String toBunch(final String[] in,final String splitter,final int start){
        if(in == null || in.length <0 || start >= in.length) return null;
        StringBuffer bunch = new StringBuffer();
        for(int i = 0;i<in.length;i++){
            if(i < start) continue;
            bunch.append(in[i]).append(splitter);
        }
        return bunch.toString().substring(0,bunch.toString().length()-splitter.length());
    }
    /**
     * 锟斤拷锟街凤拷锟斤拷锟叫憋拷锟斤拷纱锟斤拷锟斤拷
     * @param in List : 锟街凤拷锟斤拷锟叫憋拷
     * @param splitter String : 锟街革拷锟斤拷
     * @return String : 锟缴达拷锟斤拷锟斤拷址锟?
     */
    public static String toBunch(final List<Object> in,final String splitter){
        if(in == null || in.size() <= 0) return null;
        StringBuffer bunch = new StringBuffer();
        for(Iterator<Object> it=in.iterator();it.hasNext();){
            bunch.append((String)it.next()).append(splitter);
        }
        return bunch.toString().substring(0,bunch.toString().length()-splitter.length());
    }
    /**
     * 小写锟斤拷一锟斤拷锟斤拷母
     * @param in String : 锟斤拷锟斤拷式锟斤拷锟斤拷锟街凤拷锟斤拷
     * @return String : 锟斤拷锟絠n==null锟斤拷in锟斤拷锟斤拷为0锟津返伙拷in
     */
    public static String lowerFirstChar(final String in){
        if(in == null) return null;
        return in.substring(0,1).toLowerCase()+in.substring(1);
    }
    /**
     * 锟斤拷写锟斤拷一锟斤拷锟斤拷母
     * @param in String : 锟斤拷锟斤拷式锟斤拷锟斤拷锟街凤拷锟斤拷
     * @return String : 锟斤拷锟絠n==null锟斤拷in锟斤拷锟斤拷为0锟津返伙拷in
     */
    public static String upperFirstChar(final String in){
        if(in == null || in.length() <= 0) return in;
        return in.substring(0,1).toUpperCase()+in.substring(1);

    }
    /**
     * 锟斤拷式锟斤拷锟斤拷锟斤拷锟斤拷锟?
     * @param in double : 锟斤拷锟斤拷锟斤拷锟街?
     * @param format String : 锟斤拷锟斤拷锟绞斤拷锟斤拷纾?0.00,###,###.00
     * @return String : 锟斤拷式锟斤拷锟斤拷锟斤拷址锟斤拷锟?
     */
    public static String toString(final double in,final String format){
        DecimalFormat formatter = new DecimalFormat(format);
        return formatter.format(in);
    }
    /**
     * 锟斤拷式锟斤拷锟斤拷锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷null值锟斤拷锟斤拷锟斤拷
     * @param in String : 锟斤拷锟斤拷锟斤拷锟斤拷址锟斤拷锟?
     * @param defaultNullValue String : 锟斤拷锟絠n == null时锟斤拷锟斤拷锟侥拷锟街?
     * @return String : 锟斤拷式锟斤拷锟斤拷锟斤拷址锟斤拷锟斤拷锟斤拷锟斤拷in == null锟津返伙拷默锟斤拷值
     */
    public static String formatNullValue(String in,String defaultNullValue){
        return (in==null)?defaultNullValue:in;
    }
    /**
     * 锟斤拷式锟斤拷锟斤拷锟斤拷锟斤拷锟?
     * @param in int : 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟?
     * @param format String : 锟斤拷锟斤拷锟绞斤拷锟斤拷纾?0.00,###,###.00
     * @return String : 锟斤拷式锟斤拷锟斤拷锟斤拷址锟斤拷锟?
     */
    public static String toString(final int in,final String format){
        DecimalFormat formatter = new DecimalFormat(format);

        return formatter.format(in);
    }
    /**
     * 锟斤拷锟斤拷锟截革拷N锟轿猴拷锟斤拷址锟斤拷锟?
     * @param in String : 锟斤拷锟截革拷锟斤拷锟街凤拷锟斤拷
     * @param count int : 锟截革拷锟斤拷锟斤拷锟斤拷[0,)
     * @return String : 锟截革拷锟斤拷锟斤拷址锟斤拷锟?
     */
    public static String repeat(String in,int count){
        if(in == null || count < 1) return "";
        StringBuffer out = new StringBuffer();
        for(int i=0;i<count;i++){
            out.append(in);
        }
        return out.toString();
    }
    /**
     *
     * @param in String : 源锟街凤拷锟斤拷
     * @param index int : 锟斤拷0锟斤拷始[0,)
     * @param splitter String : 锟街革拷锟斤拷
     * @param defaultValue String : 默锟斤拷值
     * @return String :
     */
    public static String getIndexString(String in,int index,String splitter,String defaultValue){
        if(in == null || splitter == null) return defaultValue;
        String[] infos = in.split(splitter);
        if(infos.length <= index) return defaultValue;
        if(infos[index] == null) return defaultValue;
        return infos[index];
    }
    public static String repLineFirstToUpper(String str){
        StringBuffer buf=new StringBuffer(str);
        int length=buf.length();
        for(int i=0;i<buf.length();i++){
            char c=buf.charAt(i);
            if(c=='_'){
                i++;
                if(i<length){
                    buf.setCharAt(i, (char)(buf.charAt(i)-32));
                }
            }
        }

        return buf.toString().replaceAll("_", "");
    }

    public static void main(String[] args){
//		System.out.println("ABC?CDE".replaceAll("\\?", "_"));
//		System.out.println("abcd*def?ccc".replaceAll("\\*", ".*").replace('?', '.'));
//		String in = "a";
//		System.out.println(StringHelper.parseInt(in, 0));
//		if(StringHelper.parseInt(in, -999999) == -9999999)
        System.out.println(URLEncoder.encode("http://zg.mop.com/index.html?id=127"));

    }
}