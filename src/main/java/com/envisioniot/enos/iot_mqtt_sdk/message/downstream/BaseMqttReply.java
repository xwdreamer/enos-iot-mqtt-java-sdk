package com.envisioniot.enos.iot_mqtt_sdk.message.downstream;


import com.envisioniot.enos.iot_mqtt_sdk.core.exception.EnvisionException;
import com.envisioniot.enos.iot_mqtt_sdk.core.msg.IMqttReply;
import com.envisioniot.enos.iot_mqtt_sdk.message.BaseAckMessage;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.ResponseCode;
import com.envisioniot.enos.iot_mqtt_sdk.util.CheckUtil;


/**
 * Notice： 需要有一个仅包含topic参数的构造方法。
 *
 * @author zhensheng.cai
 * @date 2018/7/10.
 */
public abstract class BaseMqttReply extends BaseAckMessage implements IMqttReply
{
	private String productKey;
	private String deviceKey;

	public BaseMqttReply()
	{
		this.setCode(ResponseCode.SUCCESS);
		this.setMessage("");

	}

	@Override public void check() throws EnvisionException
	{

		CheckUtil.checkProductKey(this.getProductKey());
		CheckUtil.checkDeviceKey(this.getDeviceKey());

	}


	/*默认的方法，对于有多个topic参数的场景需要重写该方法*/
	@Override public String getMessageTopic()
	{
		return String.format(_getPK_DK_FormatTopic(), getProductKey(), getDeviceKey());
	}

	@Override public void setMessageTopic(String topic)
	{
		throw new UnsupportedOperationException("answer message type can't set topic");
	}

	@Override public void setMessageId(String msgId)
	{
		setId(msgId);
	}
	@Override public String getMessageId()
	{
		return getId();
	}


	@Override public String getProductKey()
	{
		return productKey;
	}

	@Override public String getDeviceKey()
	{
		return deviceKey;
	}

	@Override public void setProductKey(String productKey)
	{
		this.productKey = productKey;
	}

	@Override public void setDeviceKey(String deviceKey)
	{
		this.deviceKey = deviceKey;
	}

	@Override public int getQos()
	{
		return 0;
	}

	protected abstract String _getPK_DK_FormatTopic();


}
