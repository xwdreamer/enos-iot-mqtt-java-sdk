package com.envisioniot.enos.iot_mqtt_sdk.message.upstream.tsl;

import java.util.HashMap;
import java.util.Map;

import com.envisioniot.enos.iot_mqtt_sdk.core.exception.EnvisionException;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.DeliveryTopicFormat;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.MethodConstants;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.BaseMqttRequest;
import com.envisioniot.enos.iot_mqtt_sdk.util.CheckUtil;

/**
 * { "id": "123", "version": "1.0", "params": { "value": { "Power": "on", "WF":
 * "2" }, "time": 1524448722000 }, "method":
 * "thing.event.{tsl.event.identifier}.post" }
 * 
 * @author zhensheng.cai
 * @date 2018/7/3.
 */
public class EventPostRequest extends BaseMqttRequest<EventPostResponse>
{
	private static final long serialVersionUID = -8186172184432202539L;
	private String eventIdentifier;
	private Map<String, Object> valueMap;


	public EventPostRequest(String eventIdentifier)
	{
		this.eventIdentifier = eventIdentifier;
		this.valueMap = new HashMap<>();
		valueMap.put("value", new HashMap<String, Object>());
		valueMap.put("time", System.currentTimeMillis());
		this.setParams(valueMap);
		this.setMethod(String.format(MethodConstants.EVENT_POST, this.eventIdentifier));
	}



	@SuppressWarnings("unchecked")
	public void addValue(String point, Object value)
	{
		Map<String, Object> values = (Map<String, Object>) valueMap.get("value");
		values.put(point, value);
	}

	@SuppressWarnings("unchecked")
	public void addValue(String k1, Object v1, String k2, Object v2)
	{
		Map<String, Object> values = (Map<String, Object>) valueMap.get("value");
		values.put(k1, v1);
		values.put(k2, v2);
	}

	@SuppressWarnings("unchecked")
	public void addValues(Map<String, Object> value)
	{
		Map<String, Object> values = (Map<String, Object>) valueMap.get("value");
		values.putAll(value);
	}

	@SuppressWarnings("unchecked")
	public void setTimestamp(long timestamp)
	{
		Map<String, Object> params = (Map<String, Object>) this.getParams();
		params.put("time", timestamp);
	}

	@Override
	public void check() throws EnvisionException
	{
		super.check();
		CheckUtil.checkNotEmpty(eventIdentifier, "event.identifier");
	}

	@Override public String getMessageTopic()
	{
		return String.format(_getPK_DK_FormatTopic(), getProductKey(), getDeviceKey(), eventIdentifier);
	}

	@Override
	public Class<EventPostResponse> getAnswerType()
	{
		return EventPostResponse.class;
	}

	@Override public int getQos()
	{
		return 0;
	}

    @Override
    protected String _getPK_DK_FormatTopic()
    {
        return DeliveryTopicFormat.EVENT_POST;
    }



}
