package com.envisioniot.enos.iot_mqtt_sdk.message.downstream.tsl;

import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.DeliveryTopicFormat;
import com.envisioniot.enos.iot_mqtt_sdk.message.downstream.BaseMqttReply;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.ResponseCode;
import com.envisioniot.enos.iot_mqtt_sdk.util.BASE64Coding;

/**
 * @author zhensheng.cai
 * @date 2018/7/12.
 */
public class ModelDownRawReply extends BaseMqttReply
{
	public ModelDownRawReply(String productKey, String deviceKey)
	{
		setProductKey(productKey);
		setDeviceKey(deviceKey);
		this.setCode(ResponseCode.SUCCESS);
		this.setData("");
	}


    @Override
    protected String _getPK_DK_FormatTopic()
    {
        return DeliveryTopicFormat.MODEL_DOWN_RAW_REPLY;
    }

	
}
