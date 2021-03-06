package com.envisioniot.enos.iot_mqtt_sdk.message.upstream.tsl;

import java.io.ByteArrayOutputStream;

import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.DeliveryTopicFormat;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.MethodConstants;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.BaseMqttRequest;
import com.envisioniot.enos.iot_mqtt_sdk.util.CodingUtil;

/**
 * @author zhensheng.cai
 * @date 2018/7/5.
 */
public class ModelUpRawRequest extends BaseMqttRequest<ModelUpRawResponse>
{
	private static final long serialVersionUID = -8326661698539822393L;

	public ModelUpRawRequest()
	{
		this.setMethod(MethodConstants.THING_MODEL_UP_RAW);
	}

	public void setRawContent(byte[] rawContent){

		this.setParams(rawContent);
	}


	@Override
	public Class<ModelUpRawResponse> getAnswerType()
	{
		return ModelUpRawResponse.class;
	}


	/**
	 * id : String -> encode int (length) :encode String
	 * version : String -> encode int(length) : encode string
	 * method : same as version
	 * params/rawContent  : byte[] -> encode int(length) : encode byte[]
	 * @return
	 */
	@Override
	public byte[] encode(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CodingUtil.paddingString(bos, getMessageId());
		CodingUtil.paddingString(bos, getVersion());
		CodingUtil.paddingString(bos, getMethod());
		CodingUtil.paddingBytes(bos, (byte[]) getParams());
		return bos.toByteArray();
	}

	
    @Override
    protected String _getPK_DK_FormatTopic()
    {
        return DeliveryTopicFormat.MODEL_UP_RAW;
    }
}
