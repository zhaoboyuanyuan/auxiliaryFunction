package mqtt.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author 赵永健
 * 			连接登录接口，获取json信息：{"data":{"mqttInfo":{"host":"testv6.wulian.cc",
 *         "passwd":"238559e358908aefe3d1f97cf5e7912d","port":"8884","protocal":
 *         "ssl://","ssl":true,"user":"89f23f4f1e9711e7afa29c5c8ed091d7"},
 *         "secretKey":"ifdtmw","server":"https://testv6.wulian.cc","token":
 *         "627a91583027709d1ad381b76433717d","uId":
 *         "89f23f4f1e9711e7afa29c5c8ed091d7"},"requestId":"23fa3a0ffa74daee",
 *         "resultCode":"0","resultDesc":"success"} 
 *         签名规则：
 *         partnerId+"&"+参数部分+"&"+partnerKey+"&"+当前时间戳(毫秒)
 *         上述字符串进行sha1加密然后转换成小写字符串,就是本次请求的签名
 * 
 *
 */
public class RequestServer {

	private String addUrl="https://iot.wuliancloud.com:443/sso/login/byphone";//正式
//	private String addUrl = "https://testv6.wulian.cc/sso/login/byphone";
	private HttpURLConnection connection;

	private static String timeStamp = System.currentTimeMillis() + "";
	private static final String partnerId = "wulian_app";
	private static JsonInfo jsonInfo = new JsonInfo();
	private static final String partnerKey = "fb1bbde01c9a4d45d82d5f5107b1f4dd7c105af06c928ce14878cdda03874dcc";
	private static String data = partnerId + "&" + jsonInfo.getData() + "&" + partnerKey + "&" + timeStamp;

	/**
	 * 连接服务器
	 */
	public void connectHttp() {
		try {
			URL url = new URL(addUrl);
			try {

				connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("POST");
				connection.setUseCaches(false);
				connection.setInstanceFollowRedirects(true);
				// connection.setRequestProperty("Content-Type",
				// "application/x-www-form-urlencoded");

				connection.setRequestProperty("Content-Type", "application/json");
				connection.setRequestProperty("WL-PARTNER-ID", "wulian_app");
				connection.setRequestProperty("WL-TID", "de3f365cf53a76f62b9624315349ab7a");
				connection.setRequestProperty("WL-TIMESTAMP", timeStamp);
				connection.setRequestProperty("WL-SIGN", getSign());
				connection.connect();
//				System.out.println("连接成功！");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// post请求
	public void post() {
		DataOutputStream out;
		try {
			out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(jsonInfo.getData());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 读取返回值
	public StringBuffer getBackInfo() {
		connectHttp();
		post();
		BufferedReader reader;
		StringBuffer sb1 = null;
		try {
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			sb1 = sb;
			// System.out.println(sb);
			reader.close();
			// 断开连接
			connection.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb1;

	}
//获取签名
	public static String getSign() {
		GetSha1 sh = new GetSha1();
		return sh.getSha1(data).toLowerCase();

	}

	public static void main(String[] args) {
		 RequestServer rs=new RequestServer();
		 System.out.println(rs.getBackInfo());
		// System.out.println(getSign());
	}
}
