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
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

//import common.JsonAnalysis;

public class MqttsClientPubMain
{
	private static JsonAnalysis jas=new JsonAnalysis();
    public static void main(String[] args)
        throws MqttSecurityException, MqttException, InterruptedException, KeyManagementException,
        NoSuchAlgorithmException, KeyStoreException
    {
        try
        {
            MqttConfigInfo config = tidyMqttConfig();
            
            MqttConnectOptions options = tidyMqttOptions(config);
            
            MqttsClientCallback callback = new MqttsClientCallback();
            
            MqttClient client = createMqttClient(MqttClient.generateClientId(), callback, config, options);
            
            System.out.println("mqtts client pub creating!");
            
            Thread.sleep(1000);
            
            MqttMessage mqttMsg = new MqttMessage();
            mqttMsg.setPayload(("this is test").getBytes());
            client.publish("wl/push/89f23f4f1e9711e7afa29c5c8ed091d7/alarm", mqttMsg);
            
            
            System.out.println("mqtts client pub success!");
            Thread.sleep(3000);
//            client.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private static MqttConfigInfo tidyMqttConfig()
    {
    	////testv6.wulian.cc:8883  sys_kaifa wulian123
        MqttConfigInfo config = new MqttConfigInfo();
        config.setHost("testv6.wulian.cc");
        config.setPort(8883);
        config.setProtocal("ssl://");
        config.setClean(true);
        config.setKeepAliveInterval(50);
//        config.setUsername(jas.getUserName());
//        config.setPassword(jas.getpassword());
        config.setQos(1);
        return config;
    }
    
    public static MqttConnectOptions tidyMqttOptions(MqttConfigInfo mqttConfig)
        throws MqttSecurityException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException
    {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(mqttConfig.isClean());
        options.setUserName(mqttConfig.getUsername());
        options.setPassword(mqttConfig.getPassword().toCharArray());
        options.setKeepAliveInterval(mqttConfig.getKeepAliveInterval());
        options.setWill("wl/will/test", "this is test".getBytes(), 1, false);
        
        /****** 方法3 *******/
        // 创建X509信任管理器
        X509TrustManager x509TM = new X509TrustManager()
        {
            public void checkClientTrusted(X509Certificate[] xcs, String string)
            {
            }
            
            public void checkServerTrusted(X509Certificate[] xcs, String string)
            {
            }
            
            public X509Certificate[] getAcceptedIssuers()
            {
                return null;
            }
        };
        
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, new TrustManager[] {x509TM}, null);
        
        SSLSocketFactory sslFactory = ctx.getSocketFactory();
        options.setSocketFactory(sslFactory);
        
        return options;
    }
    
    public static MqttClient createMqttClient(String clientId, MqttCallback callBack, MqttConfigInfo mqttConfig,
        MqttConnectOptions options)
        throws MqttException, MqttSecurityException
    {
        String serverURI = mqttConfig.getProtocal() + mqttConfig.getHost() + ":" + mqttConfig.getPort();
        MqttClient client = new MqttClient(serverURI, clientId);
        client.setCallback(callBack);
        client.connect(options);
        
        return client;
    }
}
