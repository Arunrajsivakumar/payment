package com.nn.json;

public class Capture {
	
public static String intiateCaptureCancel (String tid) {
	String request = "{\n"
			+ "	  \"transaction\":{\n"
			+ "	    \"tid\":\""+tid+"\"\n"
			+ "	  },\n"
			+ "	  \"custom\":{\n"
			+ "	    \"lang\":\"EN\"\n"
			+ "	  }\n"
			+ "	}";
	return request;

}
}


