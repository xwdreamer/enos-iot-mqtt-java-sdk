package com.envisioniot.enos.iot_mqtt_sdk.message;

import com.envisioniot.enos.iot_mqtt_sdk.util.GsonUtil;
import com.google.common.collect.ImmutableMap;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhensheng.cai
 * @date 2018/8/13.
 */
public class AnswerableMessageBody implements Serializable
{

	private String id;
	private String method;
	private String version;
	private Object params;


	public byte[] encode()
	{
		Map<String, Object> payload = new HashMap<>();
		if(getId()!= null){
			payload.put("id", getId());
		}
		if(getVersion()!= null){
			payload.put("version", getVersion());
		}
		if(getMethod()!= null){
			payload.put("method", getMethod());
		}
		if(getParams()!= null){
			payload.put("params", getParams());
		}
		return GsonUtil.toJson(payload).getBytes();
	}



	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMethod()
	{
		return method;
	}

	public void setMethod(String method)
	{
		this.method = method;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public <T> T getParams()
	{
		return (T) params;
	}

	public void setParams(Object params)
	{
		this.params = params;
	}

	@Override public String toString()
	{
		return "AnswerableMessageBody{" + "id='" + id + '\'' + ", method='" + method + '\'' + ", version='" + version
				+ '\'' + ", params=" + params + '}';
	}
}
