package com.envisioniot.enos.iot_mqtt_sdk.message.upstream.topo;

import java.util.List;
import java.util.Map;

import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.DeliveryTopicFormat;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.MethodConstants;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.BaseMqttRequest;
import com.envisioniot.enos.iot_mqtt_sdk.util.Pair;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Description: delete topotaxy request
 *
 * @author zhonghua.wu
 * @create 2018-07-09 14:28
 */
public class DeleteTopoRequest extends BaseMqttRequest<DeleteTopoResponse>
{
	private List<Pair<String, String>> subDeviceList = Lists.newArrayList();
	public DeleteTopoRequest()
	{
		setMethod(MethodConstants.TOPO_DELETE);
	}

	public void setSubDevices(List<Pair<String, String>> subDeviceList)
	{
		this.subDeviceList = subDeviceList;
	}

	public List<Pair<String, String>> getSubDeviceList()
	{
		return subDeviceList;
	}

	public void setSubDeviceList(List<Pair<String, String>> subDeviceList)
	{
		this.subDeviceList = subDeviceList;
	}

	public void addSubDevice(String productKey, String deviceKey)
	{
		subDeviceList.add(Pair.makePair(productKey, deviceKey));
	}

	public void addSubDevices(List<Pair<String, String>> subDeviceList)
	{
		this.subDeviceList.addAll(subDeviceList);
	}

	@Override
	public Object getParams()
	{
		List<Map<String, String>> params = Lists.newArrayList();
		for (Pair<String, String> pair : subDeviceList)
		{
			Map<String, String> map = Maps.newHashMap();
			map.put("productKey", pair.getFirst());
			map.put("deviceKey", pair.getSecond());
			params.add(map);
		}
		return params;
	}


	@Override
	public Class<DeleteTopoResponse> getAnswerType()
	{
		return DeleteTopoResponse.class;
	}

    @Override
    protected String _getPK_DK_FormatTopic()
    {
        return DeliveryTopicFormat.TOPO_DELETE_TOPIC_FMT;
    }

}
