package com.envisioniot.enos.iot_mqtt_sdk.message.downstream.device;

import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.DeliveryTopicFormat;
import com.envisioniot.enos.iot_mqtt_sdk.message.downstream.BaseMqttReply;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.ResponseCode;

/**
 * Created by yi.dai on 2018/7/17.
 */
public class DeleteDeviceCommandReply extends BaseMqttReply
{


	public DeleteDeviceCommandReply(String productKey, String deviceKey)
	{
		setProductKey(productKey);
		setDeviceKey(deviceKey);
		setCode(ResponseCode.SUCCESS);
		setData("");
	}

    @Override
    protected String _getPK_DK_FormatTopic()
    {
        return DeliveryTopicFormat.DELETE_DEVICE_REPLY;
    }

}
