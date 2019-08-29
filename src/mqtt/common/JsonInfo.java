package mqtt.common;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class JsonInfo {
	public String getData(){
		JSONObject obj=new JSONObject();
		obj.put("phone", "15951644332");
		obj.put("phoneCountryCode", "86");
		obj.put("password", "bc9b5718afdffe85fb13555347969ff5");
		obj.put("terminalId", "de3f365cf53a76f62b9624315349ab7a");
		return obj.toString();	
	}

	
	

}
