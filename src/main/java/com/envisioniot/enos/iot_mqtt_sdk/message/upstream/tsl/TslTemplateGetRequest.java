package com.envisioniot.enos.iot_mqtt_sdk.message.upstream.tsl;

import java.util.HashMap;

import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.DeliveryTopicFormat;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.MethodConstants;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.BaseMqttRequest;

/**
 {
 "id": "123",
 "version": "1.0",
 "params": {},
 "method": "thing.dsltemplate.get"
 }
 * @author zhensheng.cai
 * @date 2018/7/12.
 */
public class TslTemplateGetRequest extends BaseMqttRequest<TslTemplateGetResponse>
{
    private static final long serialVersionUID = -8443886905071947074L;

    public TslTemplateGetRequest()
    {
        this.setParams(new HashMap<>());
        this.setMethod(MethodConstants.TSL_TEMPLATE_GET);
    }

    @Override
    public Class<TslTemplateGetResponse> getAnswerType()
    {
        return TslTemplateGetResponse.class;
    }

    @Override
    protected String _getPK_DK_FormatTopic()
    {
        return DeliveryTopicFormat.TSL_TEMPLATE_GET;
    }

}
