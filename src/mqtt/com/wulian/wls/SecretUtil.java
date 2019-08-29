package mqtt.com.wulian.wls;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 加解密工具类
 * 从TPP拷贝而来
 */
public class SecretUtil {
    private static Charset CHARSET = Charset.forName("utf-8");
    /**
     * 对明文进行加密
     */
    public static String encryptAES(String key, String message) throws Exception {
        ByteGroup byteCollector = new ByteGroup();
        byte[] textBytes = message.getBytes(CHARSET);
        byteCollector.addBytes(textBytes);

        // ... + pad: 使用自定义的填充方式对明文进行补位填
        byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
        byteCollector.addBytes(padBytes);

        // 获得最终的字节流, 未加密
        byte[] unencrypted = byteCollector.toBytes();
        // 设置加密模式为AES的CBC模式
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        byte[] aesKey = Base64.decodeBase64(key + "==");

        SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        // 加密
        byte[] encrypted = cipher.doFinal(unencrypted);

        // 使用BASE64对加密后的字符串进行编码
        Base64 base64 = new Base64();
        String base64Encrypted = base64.encodeToString(encrypted);

        return base64Encrypted;
    }

    /**
     * 对密文进行解
     */
    public static String decryptAES(String key, String message) throws Exception {
        byte[] original;
        // 设置解密模式为AES的ECB模式
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        byte[] aesKey = Base64.decodeBase64(key + "==");
        SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
        cipher.init(Cipher.DECRYPT_MODE, key_spec);

        // 使用BASE64对密文进行解码
        byte[] encrypted = Base64.decodeBase64(message);

        // 解密
        original = cipher.doFinal(encrypted);
        String xmlContent;
        // 去除补位字符
        byte[] bytes = PKCS7Encoder.decode(original);

        xmlContent = new String(Arrays.copyOfRange(bytes, 0, bytes.length), CHARSET);
        return xmlContent;

    }

    /**
     * 数据加密
     * 对要发送的消息进行AES-ECB加密
     */
    public static String encrypt(String key, String message) throws Exception {
        // 加密
        String encrypt = encryptAES(key, message);

        String timeStamp = Long.toString(System.currentTimeMillis());
        String nonce = StringUtils.lowerCase(RandomStringUtils.randomAlphanumeric(6));

        String signature = SHA256.getSHA256(key, timeStamp, nonce, encrypt);

        JSONObject encryptMsgJson = new JSONObject();
        encryptMsgJson.put("msgContent", encrypt);
        encryptMsgJson.put("signature", signature);
        encryptMsgJson.put("timestamp", timeStamp);
        encryptMsgJson.put("nonce", nonce);

        return encryptMsgJson.toString();
    }
}