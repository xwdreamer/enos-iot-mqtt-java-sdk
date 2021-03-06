package com.envisioniot.enos.iot_mqtt_sdk.message.upstream.tag;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.envisioniot.enos.iot_mqtt_sdk.core.exception.EnvisionException;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.DeliveryTopicFormat;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.MethodConstants;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.BaseMqttRequest;
import com.envisioniot.enos.iot_mqtt_sdk.util.CheckUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Description: delete device tags
 *
 * @author zhonghua.wu
 * @create 2018-07-09 14:43
 */
public class DeleteDeviceTagRequest extends BaseMqttRequest<DeleteDeviceTagResponse>
{
    private List<String> tags = Lists.newArrayList();

    public DeleteDeviceTagRequest(){

    }

    public DeleteDeviceTagRequest(String productKey, String deviceKey)
    {
        this.setMethod(MethodConstants.TAG_DELETE);
        setProductKey(productKey);
        setDeviceKey(deviceKey);
    }

    public void addTagKey(String tagKey)
    {
        tags.add(tagKey);
    }

    public void addTagKeys(Collection<String> tagKeys)
    {
        tags.addAll(tagKeys);
    }

    @Override
    public Object getParams()
    {
        List<Map<String, String>> params = Lists.newArrayList();
        for (String tagKey : tags)
        {
            Map<String, String> keyMap = Maps.newHashMap();
            keyMap.put("tagKey", tagKey);
            params.add(keyMap);
        }
        return params;
    }

    public List<String> getTags()
    {
        return tags;
    }

    public void setTags(List<String> tags)
    {
        this.tags = tags;
    }

    @Override
    public void check() throws EnvisionException
    {
        super.check();
        CheckUtil.checkNotEmpty(tags, "tags");
    }

    @Override
    public Class<DeleteDeviceTagResponse> getAnswerType()
    {
        return DeleteDeviceTagResponse.class;
    }

    @Override
    protected String _getPK_DK_FormatTopic()
    {
        return DeliveryTopicFormat.TAG_DELETE_TOPIC_FMT;
    }
}
