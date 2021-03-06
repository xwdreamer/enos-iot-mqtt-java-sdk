package com.envisioniot.enos.iot_mqtt_sdk.message.downstream.tsl;

import java.util.List;
import java.util.regex.Pattern;

import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.ArrivedTopicPattern;
import com.envisioniot.enos.iot_mqtt_sdk.message.downstream.BaseMqttCommand;

/**
 * { "id": "123", "version": "1.0", "params": [ "power", "temp" ], "method":
 * "thing.service.property.get" }
 * 
 * @author zhensheng.cai
 * @date 2018/7/12.
 */
public class MeasurepointGetCommand extends BaseMqttCommand<MeasurepointGetReply>
{
	private static Pattern pattern = Pattern.compile(ArrivedTopicPattern.MEASUREPOINT_GET_COMMAND);

	@SuppressWarnings("unchecked")
    public List<String> getMeasurepoints()
	{
		return (List<String>) this.getParams();
	}

    @Override
    public Class<MeasurepointGetReply> getAnswerType()
    {
        return MeasurepointGetReply.class;
    }

    @Override
    public Pattern getMatchTopicPattern()
    {
		return pattern;
	}
}
