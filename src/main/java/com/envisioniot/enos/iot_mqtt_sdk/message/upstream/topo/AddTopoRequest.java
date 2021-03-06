package com.envisioniot.enos.iot_mqtt_sdk.message.upstream.topo;

import java.util.List;
import java.util.Map;

import com.envisioniot.enos.iot_mqtt_sdk.core.exception.EnvisionException;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.DeliveryTopicFormat;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.MethodConstants;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.BaseMqttRequest;
import com.envisioniot.enos.iot_mqtt_sdk.util.CheckUtil;
import com.google.common.collect.Lists;

/**
 * Description: add topo request
 *
 * @author zhonghua.wu
 * @create 2018-07-04 11:12
 */
public class AddTopoRequest extends BaseMqttRequest<AddTopoResponse>
{
	private List<SubDeviceInfo> subDeviceInfoList = Lists.newArrayList();

	public AddTopoRequest()
	{
		this.setMethod(MethodConstants.TOPO_ADD);
	}

	public List<SubDeviceInfo> getSubDeviceInfoList()
	{
		return subDeviceInfoList;
	}

	public void setSubDeviceInfoList(List<SubDeviceInfo> subDeviceInfoList)
	{
		this.subDeviceInfoList = subDeviceInfoList;
	}

	public void addSubDevice(SubDeviceInfo deviceInfo)
	{
		subDeviceInfoList.add(deviceInfo);
	}

	public void addSubDevices(List<SubDeviceInfo> deviceInfoList)
	{
		subDeviceInfoList.addAll(deviceInfoList);
	}

	@Override
	public Object getParams()
	{
		List<Map<String, String>> params = Lists.newArrayList();
		for (SubDeviceInfo deviceInfo : subDeviceInfoList)
		{
			params.add(deviceInfo.getObject());
		}
		return params;
	}

	@Override
	public Class<AddTopoResponse> getAnswerType()
	{
		return AddTopoResponse.class;
	}

	@Override
	public void check() throws EnvisionException
	{
	    super.check();
		CheckUtil.checkNotEmpty(subDeviceInfoList, "subDeviceInfoList");
		for (SubDeviceInfo subDeviceInfo : subDeviceInfoList)
		{
	        CheckUtil.checkNotEmpty(subDeviceInfo.getProductKey(), "subDeviceInfo.productKey");
	        CheckUtil.checkNotEmpty(subDeviceInfo.getDeviceKey(), "subDeviceInfo.deviceKey");
	        CheckUtil.checkNotEmpty(subDeviceInfo.getClientId(), "subDeviceInfo.client");
	        CheckUtil.checkNotEmpty(subDeviceInfo.getSignMethod(), "subDeviceInfo.signMethod");
	        CheckUtil.checkNotEmpty(subDeviceInfo.getSign(), "subDeviceInfo.sign");
		}
	}

    @Override
    protected String _getPK_DK_FormatTopic()
    {
        return DeliveryTopicFormat.TOPO_ADD_TOPIC_FMT;
    }
}
