package com.envisioniot.enos.iot_mqtt_sdk.message.upstream.tag;

import com.envisioniot.enos.iot_mqtt_sdk.core.exception.EnvisionException;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.DeliveryTopicFormat;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.MethodConstants;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.BaseMqttRequest;
import com.envisioniot.enos.iot_mqtt_sdk.util.CheckUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Description: update device tags
 *
 * @author zhonghua.wu
 * @create 2018-07-09 14:42
 */
public class UpdateDeviceTagRequest extends BaseMqttRequest<UpdateDeviceTagResponse>
{
    private Map<String, String> tags = Maps.newHashMap();

    public UpdateDeviceTagRequest(){

    }

    public UpdateDeviceTagRequest(String productKey, String deviceKey)
    {
        setProductKey(productKey);
        setDeviceKey(deviceKey);
        this.setMethod(MethodConstants.TAG_UPDATE);
    }

    public void addTag(String tagKey, String tagValue)
    {
        tags.put(tagKey, tagValue);
    }

    public void addTags(Map<String, String> tags)
    {
        this.tags.putAll(tags);
    }

    @Override
    public Object getParams()
    {
        List<Map<String, String>> params = Lists.newArrayList();
        for (Map.Entry<String, String> entry : tags.entrySet())
        {
            String tagKey = entry.getKey();
            String tagValue = entry.getValue();
            Map<String, String> map = Maps.newHashMap();
            map.put("tagKey", tagKey);
            map.put("tagValue", tagValue);
            params.add(map);
        }
        return params;
    }

    public Map<String, String> getTags()
    {
        return tags;
    }

    public void setTags(Map<String, String> tags)
    {
        this.tags = tags;
    }

    @Override
    public Class<UpdateDeviceTagResponse> getAnswerType()
    {
        return UpdateDeviceTagResponse.class;
    }

    @Override public void check() throws EnvisionException
    {
        super.check();
        for (Map.Entry<String, String> entry : tags.entrySet())
        {
            String tagKey = entry.getKey();
            String tagValue = entry.getValue();
            CheckUtil.checkNotEmpty(tagKey, "tagKey");
            CheckUtil.checkNotEmpty(tagValue, "tagValue");
        }
    }

    @Override
    protected String _getPK_DK_FormatTopic()
    {
        return DeliveryTopicFormat.TAG_UPDATE_TOPIC_FMT;
    }

}
