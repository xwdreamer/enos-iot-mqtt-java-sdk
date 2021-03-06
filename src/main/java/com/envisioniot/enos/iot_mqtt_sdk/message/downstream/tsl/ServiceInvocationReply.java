package com.envisioniot.enos.iot_mqtt_sdk.message.downstream.tsl;

import java.util.HashMap;
import java.util.Map;

import com.envisioniot.enos.iot_mqtt_sdk.core.exception.EnvisionException;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.DeliveryTopicFormat;
import com.envisioniot.enos.iot_mqtt_sdk.message.downstream.BaseMqttReply;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.ResponseCode;
import com.envisioniot.enos.iot_mqtt_sdk.util.CheckUtil;

/**
 *
 * @author zhensheng.cai
 * @date 2018/7/12.
 */
public class ServiceInvocationReply extends BaseMqttReply
{
	private static final long serialVersionUID = 1506910581305477787L;
	private String serviceIdentifier;

	public ServiceInvocationReply(String serviceIdentifier){
		super();
		this.setServiceIdentifier(serviceIdentifier);
	}

	public ServiceInvocationReply(String productKey, String deviceKey, String serviceIdentifier)
	{
		this.serviceIdentifier = serviceIdentifier;
		this.setData(new HashMap<String, Object>());
		this.setCode(ResponseCode.SUCCESS);
		setProductKey(productKey);
		setDeviceKey(deviceKey);
	}

	public void addOutputData(String point, Object value)
	{
	    Map<String, Object> map = getData();
	    map.put(point, value);
	}

	public void addOutputDatas(Map<String, Object> values)
	{
		Map<String, Object> map = getData();
		map.putAll(values);
	}

	public void setOutputDatas(Map<String, Object> values)
	{
		this.setData(values);
	}

	@Override
	public void check() throws EnvisionException
	{
		super.check();
		CheckUtil.checkNotEmpty(serviceIdentifier, "service.identifier");
	}

	@Override public String getMessageTopic()
	{
		return String.format(_getPK_DK_FormatTopic(), getProductKey(), getDeviceKey(), serviceIdentifier);
	}

	public String getServiceIdentifier()
	{
		return serviceIdentifier;
	}

	public void setServiceIdentifier(String serviceIdentifier)
	{
		this.serviceIdentifier = serviceIdentifier;
	}

    @Override
    protected String _getPK_DK_FormatTopic()
    {
        return DeliveryTopicFormat.SERVICE_INVOKE_REPLY;
    }
}
