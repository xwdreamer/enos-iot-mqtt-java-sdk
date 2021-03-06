package com.envisioniot.enos.iot_mqtt_sdk.message.upstream.tsl;

import java.util.HashMap;
import java.util.Map;

import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.DeliveryTopicFormat;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.MethodConstants;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.BaseMqttRequest;

/**
 * { "id": "123", "version": "1.0", "params": { "Power": { "value": "on",
 * "time": 1524448722000 }, "WF": { "value": 23.6, "time": 1524448722000 } },
 * "method": "thing.event.property.post" }
 * 
 * @author zhensheng.cai
 * @date 2018/7/10.
 */
public class MeasurepointPostRequest extends BaseMqttRequest<MeasurepointPostResponse>
{
	private static final long serialVersionUID = 4018722889739885894L;

	public MeasurepointPostRequest()
	{
		this.setParams(new HashMap<String, Map<String, Object>>());
		this.setMethod(MethodConstants.MEASUREPOINT_POST);
	}

	public void addMeasurePoint(String key, Object value)
	{
		this.addMeasurePoint(key, value, System.currentTimeMillis());
	}

	public void addMeasurePoint(String key, Object value, long timestamp)
	{
		Map<String, Map<String, Object>> pointValues = this.getParams();
		Map<String, Object> pointValue = new HashMap<>();
		pointValue.put("value", value);
		pointValue.put("timestamp", timestamp);
		pointValues.put(key, pointValue);
	}


	@Override
	public Class<MeasurepointPostResponse> getAnswerType()
	{
		return MeasurepointPostResponse.class;
	}

    @Override
    protected String _getPK_DK_FormatTopic()
    {
        return DeliveryTopicFormat.MEASUREPOINT_POST;
    }

}
