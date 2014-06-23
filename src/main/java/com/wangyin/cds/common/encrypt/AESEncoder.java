/**
 * 
 */
package com.wangyin.cds.common.encrypt;

/**
 * 简单的AES加密解密方法
 * <p>注意,请后续将key放入配置中去</p>
 * 
 * @author wymaoxiaolaing
 */
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加密
 */
public class AESEncoder {
    private static final String key = "wymaoxiaoliang";

    //    public static void main(String[] args) throws Exception {
    //        System.out.println(AESEncoder.decrypt( AESEncoder.encrypt("jdbc:oracle:thin:@10.250.4.116:1521:alifindev")));
    //    }

    /**
     * 解密方法
     * 
     * @param src
     * @return
     * @throws Exception
     */
    public static String decrypt(String src) throws Exception {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted = hex2byte(src);
            byte[] original = cipher.doFinal(encrypted);
            return new String(original);
        } catch (Exception ex) {
            throw new Exception("密钥错误,解密失败", ex);
        }
    }

    /**
     * 加密方法
     * 
     * @param src
     * @return
     * @throws Exception
     */
    public static String encrypt(String src) throws Exception {
        byte[] raw = key.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(src.getBytes());
        return byte2hex(encrypted).toLowerCase();
    }

    /**
     * HEX到字节数组转换
     * 
     * @param hex
     * @return
     */
    private static byte[] hex2byte(String hex) {
        if (hex == null) {
            return null;
        }
        int l = hex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] bytes = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }

    /**
     * 字节数组到HEX转换
     * 
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        String hs = "";
        String stmp = "";
        for (int i = 0; i < bytes.length; i++) {
            stmp = (java.lang.Integer.toHexString(bytes[i] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
}
