package mqtt.common;

import com.alibaba.fastjson.JSONObject;
import mqtt.com.wulian.wls.SecretUtil;
//import com.wulian.wls.SecretUtil;
//
//import connect.MqttsClientCallback;

public class JsonAnalysis {
	RequestServer rs = new RequestServer();
	private String user;
	private String pwd;
	private String uid;
	private String seckey;

	public String getUser() {
		return user;
	}

	public String getPwd() {
		return pwd;
	}

	public String getUid() {
		return uid;
	}

	public String getSeckey() {
		return seckey;
	}

	public void getInfo() {
		JSONObject json = JSONObject.parseObject(rs.getBackInfo().toString());
		JSONObject data = json.getJSONObject("data");
		JSONObject mqttInfo = data.getJSONObject("mqttInfo");
		String userName = mqttInfo.getString("user");
		user = userName;
		String password = mqttInfo.getString("passwd");
		pwd = password;
		String uId = data.getString("uId");
		uid = uId;
		String secretKey = data.getString("secretKey");
		seckey = secretKey;
	}

	public String getMessage(String jsonText) {
		JSONObject json = new JSONObject().parseObject(jsonText);
		String msgContent = json.getString("msgContent");
		return msgContent;
	}

	public String getTimestamp(String jsonText) {
		JSONObject json = new JSONObject().parseObject(jsonText);
		String timestamp = json.getString("timestamp");
		return timestamp;
	}

	public String getSignature(String jsonText) {
		JSONObject json = new JSONObject().parseObject(jsonText);
		String signature = json.getString("signature");
		return signature;
	}

	public String getNonce(String jsonText) {
		JSONObject json = new JSONObject().parseObject(jsonText);
		String nonce = json.getString("nonce");
		return nonce;
	}

	/**
	 * 解密
	 * 
	 * @param messages
	 */
	public void decry(String messages) {
		String messageContent = getMessage(messages);
		// 注意MD5加密要小写,32位
		String uid = Md5.md532(getUid());
		String secretKey = getSeckey();
		String key = secretKey + uid.substring(uid.length() - 16, uid.length());
		try {
			String decrypt = SecretUtil.decryptAES(key, messageContent);
			System.out.println("消息解密内容:" + decrypt);
			System.out.println();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JsonAnalysis ja = new JsonAnalysis();
		ja.getInfo();
		System.out.println(ja.getUser());
		System.out.println(ja.getPwd());
		System.out.println(ja.getSeckey());
		System.out.println(ja.getUid());

	}
}
