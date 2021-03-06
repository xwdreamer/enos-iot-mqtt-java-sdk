package com.envisioniot.enos.iot_mqtt_sdk.message.upstream.topo;

import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.ArrivedTopicPattern;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.BaseMqttResponse;

import java.util.regex.Pattern;

/**
 * Description: delete topotaxy response
 *
 * @author zhonghua.wu
 * @create 2018-07-09 14:26
 */
public class DeleteTopoResponse extends BaseMqttResponse
{

    private static Pattern pattern = Pattern.compile(ArrivedTopicPattern.TOPO_DELETE_REPLY);

    @Override
    public Pattern getMatchTopicPattern()
    {
        return pattern;
    }
}
