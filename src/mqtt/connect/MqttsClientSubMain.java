package mqtt.connect;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import mqtt.common.JsonAnalysis;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
//import org.eclipse.paho.client.mqttv3.MqttTopic;
//
//import common.JsonAnalysis;

/**
 * 需要用不同的手机登录，与虚拟账号不同，但两个账号绑定相同的网关
 * @author 赵永健
 * 测试环境和正式环境切换办法：1、RequestServer中更改addUrl为正式；2、在Mqtt客户端第59行
 *
 */

public class MqttsClientSubMain {

	public static JsonAnalysis jas;
//	private static String TOPIC = "wl/user/"+jas.getUserName()+"/data";
//	private static String TOPIC = "wl/push/89f23f4f1e9711e7afa29c5c8ed091d7/alarm";
//	private static String TOPIC2 = "wl/user/"+jas.getUserName()+"/alarm";
	
//	 private static String TOPIC="wl/user/89f23f4f1e9711e7afa29c5c8ed091d7/push/alarm";

	public static void main(String[] args) throws MqttSecurityException, MqttException, InterruptedException,
			KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		try {
			jas=new JsonAnalysis();
			jas.getInfo();
			MqttConfigInfo config = tidyMqttConfig();
			MqttConnectOptions options = tidyMqttOptions(config);
			MqttsClientCallback callback = new MqttsClientCallback();
			MqttClient client = createMqttClient(MqttClient.generateClientId(), callback, config, options);

		} catch (Exception e) {
			System.out.println("mqtts error  0!");
		}
	}
	
	private static MqttConfigInfo tidyMqttConfig() {
		// testv6.wulian.cc:8883 sys_kaifa wulian123 刘毅提供
		// testv6.wulian.cc:8884  测试环境
		// umqttcn.wuliancloud.com: 18884 正式环境
		MqttConfigInfo config = new MqttConfigInfo();
		config.setHost("umqttcn.wuliancloud.com");// 地址
		config.setPort(18884);// 端口
		config.setProtocal("ssl://");
		config.setClean(true);
		config.setKeepAliveInterval(50);
		config.setUsername(jas.getUser());
		config.setPassword(jas.getPwd());
		config.setQos(1);
		return config;
	}

	public static MqttConnectOptions tidyMqttOptions(MqttConfigInfo mqttConfig)
			throws MqttSecurityException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(mqttConfig.isClean());
		options.setUserName(mqttConfig.getUsername());
		options.setPassword(mqttConfig.getPassword().toCharArray());
		options.setKeepAliveInterval(mqttConfig.getKeepAliveInterval());

		// 创建X509信任管理器
		X509TrustManager x509TM = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] xcs, String string) {
			}

			public void checkServerTrusted(X509Certificate[] xcs, String string) {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(null, new TrustManager[] { x509TM }, null);

		SSLSocketFactory sslFactory = ctx.getSocketFactory();
		options.setSocketFactory(sslFactory);

		return options;
	}

	public static MqttClient createMqttClient(String clientId, MqttCallback callBack, MqttConfigInfo mqttConfig,
			MqttConnectOptions options) throws MqttException, MqttSecurityException {
		String serverURI = mqttConfig.getProtocal() + mqttConfig.getHost() + ":" + mqttConfig.getPort();
		MqttClient client = new MqttClient(serverURI, clientId);
		client.setCallback(new MqttCallback() {
			
			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("接收消息主题 : " + topic);
				System.out.println("接收消息Qos : " + message.getQos());
				System.out.println("接收消息内容 : " + message.toString());
				jas.decry(message.toString());
			}
			
			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
				// TODO Auto-generated method stub
				System.out.println("deliveryComplete");
			}
			
			@Override
			public void connectionLost(Throwable throwable) {
				// TODO Auto-generated method stub
				System.out.println("connectionLost");
			}
		});

		// MqttTopic topic = client.getTopic(TOPIC);
		//
		//// setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
		// options.setWill(topic, "close".getBytes(), 2, true);
		client.connect(options);
		System.out.println("mqtts client success!");

		// 订阅消息
		String TOPIC = "wl/user/"+jas.getUser()+"/data";
		String TOPIC2 = "wl/user/"+jas.getUser()+"/alarm";
		int[] Qos = {1,1};
		String[] topic1 = {TOPIC,TOPIC2};
		client.subscribe(topic1, Qos);
		return client;
	}

}
