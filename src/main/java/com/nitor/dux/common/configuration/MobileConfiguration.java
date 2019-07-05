package com.nitor.dux.common.configuration;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.remote.MobileCapabilityType;

@SuppressWarnings("unused")
public class MobileConfiguration extends BaseConfiguration {

	private String automationEngine;
	private String platformName;
	private String platformVersion;
	private String deviceName;
	private String app;
	private String browserName;
	private String newCommandTimeout;
	private String autoLaunch;
	private String language;
	private String locale;
	private String udid;
	private String orientation;
	private String autoWebView;
	
	public MobileConfiguration(JSONObject configuration) {
		super(configuration);
		
		this.automationEngine = configuration.isNull(ConfigurationKeys.AUTOMATION_ENGINE_KEY.getText())?
				"":configuration.getString(ConfigurationKeys.AUTOMATION_ENGINE_KEY.getText());
		this.platformName = configuration.isNull(ConfigurationKeys.PLATFORM_NAME_KEY.getText())?
				"":configuration.getString(ConfigurationKeys.PLATFORM_NAME_KEY.getText());
		this.platformVersion = configuration.isNull(ConfigurationKeys.PLATFORM_VERSION_KEY.getText())?
				"":configuration.getString(ConfigurationKeys.PLATFORM_VERSION_KEY.getText());
		this.deviceName = configuration.isNull(ConfigurationKeys.DEVICE_NAME_KEY.getText())?
				"":configuration.getString(ConfigurationKeys.DEVICE_NAME_KEY.getText());
		this.app = configuration.isNull(ConfigurationKeys.APP_KEY.getText())?
				"":configuration.getString(ConfigurationKeys.APP_KEY.getText());
		this.browserName = configuration.isNull(ConfigurationKeys.BROWSER_NAME_KEY.getText())?
				"":configuration.getString(ConfigurationKeys.BROWSER_NAME_KEY.getText());
		this.newCommandTimeout = configuration.isNull(ConfigurationKeys.NEW_COMMAND_TIMEOUT_KEY.getText())?
				"":configuration.getString(ConfigurationKeys.NEW_COMMAND_TIMEOUT_KEY.getText());
		this.autoLaunch = configuration.isNull(ConfigurationKeys.AUTO_LAUNCH_KEY.getText())?
				"":configuration.getString(ConfigurationKeys.AUTO_LAUNCH_KEY.getText());
	}
	
	public DesiredCapabilities getDesiredCapabilities(){
		DesiredCapabilities capabilities = new DesiredCapabilities();		
		capabilities.setCapability(MobileCapabilityType.APP, this.app);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, this.platformName);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, this.deviceName);
		return capabilities;
	}

}
