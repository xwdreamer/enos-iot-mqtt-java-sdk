package com.envisioniot.enos.iot_mqtt_sdk.core.internals;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import com.envisioniot.enos.iot_mqtt_sdk.core.IConnectCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.envisioniot.enos.iot_mqtt_sdk.core.IResponseCallback;
import com.envisioniot.enos.iot_mqtt_sdk.core.exception.EnvisionError;
import com.envisioniot.enos.iot_mqtt_sdk.core.exception.EnvisionException;
import com.envisioniot.enos.iot_mqtt_sdk.core.msg.IMqttRequest;
import com.envisioniot.enos.iot_mqtt_sdk.core.msg.IMqttResponse;
import com.envisioniot.enos.iot_mqtt_sdk.core.profile.Profile;

/**
 * @author zhensheng.cai
 * @date 2018/7/17.
 */
public class MqttConnection
{
	private static final Logger logger = LoggerFactory.getLogger(MqttConnection.class);
	
	private final MqttClient mqttClient;
	//private final EnvClientContext clientContext;
	private final DefaultProcessor mqttProcessor;
	
	private Profile profile;
	private SubTopicCache subTopicCache = new SubTopicCache();

	
	/**
	 * -1 means wait forever
	 */

	public MqttConnection(Profile profile)
	{
		this.profile = profile;
		
		try
		{
			this.mqttClient = new MqttClient(profile.getRegionURL(), profile.getClientId(), new MemoryPersistence());
			this.mqttProcessor = new DefaultProcessor(mqttClient ,profile ,subTopicCache);
			this.mqttClient.setCallback(mqttProcessor);
			this.mqttClient.setTimeToWait(profile.getTimeToWait() * 1000);
		}
		catch (MqttException e)
		{
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}


	public void connect() throws EnvisionException
	{
		this.connect(null);
	}

	public void connect(IConnectCallback callback) throws EnvisionException{
		if(callback!=null ){
			this.mqttProcessor.setConnectCallback(callback);
		}
		try
		{
			this.mqttClient.connect(profile.createConnectOptions());
		}
		catch (MqttException e)
		{
			this.mqttProcessor.onConnectFailed(e.getReasonCode());
			logger.error("connect err.", e);
			throw new EnvisionException(e.getMessage(), e.getCause(), EnvisionError.MQTT_CLIENT_CONNECT_FAILED);
		}
	}

	public void disconnect() throws EnvisionException
	{
		try
		{
			this.mqttClient.disconnect();
		}
		catch (MqttException e)
		{
			throw new EnvisionException(e.getMessage(), e.getCause(), EnvisionError.MQTT_CLIENT_DISCONNECT_FAILED);
		}
	}

	public String getClientId()
	{
		return this.mqttClient.getClientId();
	}

	public void close() throws EnvisionException
	{
		try
		{
			this.mqttClient.close();
		}
		catch (MqttException e)
		{
			throw new EnvisionException(e.getMessage(), e.getCause(), EnvisionError.MQTT_CLIENT_CLOSE_FAILED);
		}
	}
	
	public DefaultProcessor getProcessor()
	{
	    return mqttProcessor;
	}


	public <T extends IMqttResponse> void fastPublish(IMqttRequest<T> request ) throws Exception
	{
		mqttProcessor.doFastPublish(request);
	}
	
	public <T extends IMqttResponse> void publish(IMqttRequest<T> request, IResponseCallback<T> callback) throws Exception
	{
	    String topic = request.getAnswerTopic();
        if (subTopicCache.exists(topic) == false)
        {
            this.mqttClient.subscribe(topic);
            subTopicCache.put(topic);
        }

		mqttProcessor.createCallbackTask(request, callback,mqttClient.getTimeToWait());
	}
	
	public <T extends IMqttResponse> T publish(IMqttRequest<T> request) throws Exception
	{
	    String topic = request.getAnswerTopic();
        if (subTopicCache.exists(topic) == false)
        {
            this.mqttClient.subscribe(topic);
            subTopicCache.put(topic);
        }
        
        FutureTask<T> future = mqttProcessor.createFutureTask(request);
        return future.get(mqttClient.getTimeToWait(), TimeUnit.MILLISECONDS);
	}

	public boolean isConnected()
	{
		return mqttClient != null && mqttClient.isConnected();
	}
}
