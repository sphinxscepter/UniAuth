package com.yyt.UniAuth.customoauth;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomOAuthExceptionJacksonSerializer extends StdSerializer<CustomOAuthException> {

	protected CustomOAuthExceptionJacksonSerializer() {
		super(CustomOAuthException.class);
	}

	@Override
	public void serialize(
			CustomOAuthException exp, 
			JsonGenerator jsonGen, 
			SerializerProvider sp) throws IOException {
		jsonGen.writeStartObject();
		jsonGen.writeObjectField("status", exp.getHttpErrorCode());
		String errMessage = exp.getMessage();
		if(null != errMessage && !"".equals(errMessage)) {
			errMessage = HtmlUtils.htmlEscape(errMessage);
		}
		jsonGen.writeObjectField("message", errMessage);
		if(exp.getAdditionalInformation() != null) {
			for(Map.Entry<String, String> entry : exp.getAdditionalInformation().entrySet()) {
				jsonGen.writeObjectField(entry.getKey(), entry.getValue());
			}
		}
		jsonGen.writeEndObject();
	}

}
