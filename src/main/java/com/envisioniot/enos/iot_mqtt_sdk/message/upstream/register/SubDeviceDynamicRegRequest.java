package com.envisioniot.enos.iot_mqtt_sdk.message.upstream.register;

import java.util.*;

import com.envisioniot.enos.iot_mqtt_sdk.core.exception.EnvisionException;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.DeliveryTopicFormat;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.MethodConstants;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.BaseMqttRequest;
import com.envisioniot.enos.iot_mqtt_sdk.util.CheckUtil;
import com.envisioniot.enos.iot_mqtt_sdk.util.GsonUtil;
import com.envisioniot.enos.iot_mqtt_sdk.util.Pair;
import com.envisioniot.enos.iot_mqtt_sdk.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import javax.crypto.spec.OAEPParameterSpec;

/**
 * Description: sub-device dynamic register request
 *
 * @author zhonghua.wu
 * @create 2018-07-20 10:33
 */
public class SubDeviceDynamicRegRequest extends BaseMqttRequest<SubDeviceDynamicRegResponse>
{
	private static final int MAX_DEVICE_SIZE = 1000;


	public SubDeviceDynamicRegRequest()
	{
		super();
		super.setMethod(MethodConstants.SUB_DEVICE_REGISTER);
		List<Map<String, Object>> params = new ArrayList<>();
		super.setParams(params);
	}


	public void addSubBatchRegisterInfo(String subProductKey, List<DeviceRegOption> regOptions){
		List<Map<String, Object>> params = this.getParams();
		params.addAll(createBatchRegInfoMap(subProductKey, regOptions));
	}

	public void setSubBatchRegisterInfo(String subProductKey, List<DeviceRegOption> regOptions){
		this.setParams(createBatchRegInfoMap(subProductKey, regOptions));
	}

	public void addSubRegisterInfo(String subProductKey, DeviceRegOption regOption){
		List<Map<String, Object>> params = this.getParams();
		params.add(createRegInfoMap(subProductKey, regOption));
	}

	public void addSubRegisterInfo(String subProductKey, String deviceKey, String deviceName, String deviceDesc)
	{
		addSubRegisterInfo(subProductKey, new DeviceRegOption(deviceKey, deviceName, deviceDesc));
	}
	public void addSubRegisterInfo(String subProductkey, String deviceKey, String deviceName, String deviceDesc,
			Map<String, Object> deviceAttrs)
	{
		addSubRegisterInfo(subProductkey, new DeviceRegOption(deviceKey, deviceName, deviceDesc, deviceAttrs));
	}
	private List<Map<String, Object>> createBatchRegInfoMap(String subProductKey, List<DeviceRegOption> regOptions){
		List<Map<String, Object>> params = new ArrayList<>();
		for (DeviceRegOption regOption : regOptions)
		{
			params.add(createRegInfoMap(subProductKey, regOption));
		}
		return params;
	}

	private  Map<String, Object> createRegInfoMap(String subProductKey, DeviceRegOption regOption){
		Map<String, Object> param = new HashMap<>();
		param.put("productKey", subProductKey);

		if (regOption.deviceAttributes != null && !regOption.deviceAttributes.isEmpty()) {
			param.put("deviceAttributes", regOption.deviceAttributes);
		}
		if (StringUtil.isNotEmpty(regOption.deviceKey)) {
			param.put("deviceKey", regOption.deviceKey);
		}
		if (StringUtil.isNotEmpty(regOption.deviceName)) {
			param.put("deviceName", regOption.deviceName);
		}
		if (StringUtil.isNotEmpty(regOption.deviceDesc)) {
			param.put("deviceDesc", regOption.deviceDesc);
		}
		return param;
	}


	@Override
	public Class<SubDeviceDynamicRegResponse> getAnswerType()
	{
		return SubDeviceDynamicRegResponse.class;
	}

	@Override
	public void check() throws EnvisionException
	{
		List<Map<String, Object>> params = this.getParams();
		CheckUtil.checkMaxSize(params, MAX_DEVICE_SIZE, "regOptionList");
	}

	public static void main(String[] args)
	{
		Map<String, Object> deviceAttributes = Maps.newHashMap();
		deviceAttributes.put("color", "red");
		DeviceRegOption regOption = new DeviceRegOption();
		regOption.deviceAttributes = deviceAttributes;
		regOption.deviceKey = "deviceKey1";
		regOption.deviceName = "deviceName1";
		regOption.deviceDesc = "deviceDesc1";
		SubDeviceDynamicRegRequest request = new SubDeviceDynamicRegRequest();
//		request.addRegOption(regOption);
		System.out.println(new Gson().toJson(request.getParams()));
	}

    @Override
    protected String _getPK_DK_FormatTopic()
    {
        return DeliveryTopicFormat.SUB_DEVICE_REGISTER_TOPIC_FMT;
    }
}
