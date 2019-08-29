/*
 * 文 件 名:  CcpMqqtCallback.java
 * 版    权:  Wulian Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  姓名 工号
 * 修改时间:  2015年11月16日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package mqtt.connect;


//import org.apache.commons.codec.binary.Base64;
import mqtt.com.wulian.wls.SecretUtil;
import mqtt.common.JsonAnalysis;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

//import com.wulian.wls.SecretUtil;
//
//import common.JsonAnalysis;
//import common.Md5;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 姓名 工号
 * @version [版本号, 2015年11月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MqttsClientCallback implements MqttCallback {
	private SecretUtil secretUtil=new SecretUtil();
	private JsonAnalysis jas=new JsonAnalysis();
//	private String key="t7wgbd0314cfa3439efcf6";

	@Override
	public void connectionLost(Throwable throwable) {
		System.out.println("connectionLost");
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// System.out.println("this is test." + topic+"
		// "+mqttmessage.toString()); new String(message.getPayload())
		System.out.println("接收消息主题 : " + topic);
		System.out.println("接收消息Qos : " + message.getQos());
		System.out.println("接收消息内容 : " + message.toString());
		jas.decry(message.toString());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("deliveryComplete");
	}

	/**
	 * 秘钥生成方式
		AESKey：Base64.decode(echoStr + MD5(uid)后16位 + "==") 注：V6.00.02之前版本为 Base64.decode(echoStr +  "==")
	 *  
	 */
	
	
//	public String getKey1(){
//		String uid=jas.getUid()+"==";
//		String key=Base64.decodeBase64(uid).toString();
//		System.out.println(key);
//		return key;
//	}
//	
//	public static void main(String[] args) {
//		MqttsClientCallback mc=new MqttsClientCallback();
//		mc.getKey();
//	}

}
