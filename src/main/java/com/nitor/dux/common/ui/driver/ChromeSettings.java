package com.nitor.dux.common.ui.driver;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChromeSettings {
	 private static final Logger LOGGER = LoggerFactory.getLogger(ChromeSettings.class);
	
	 public static DesiredCapabilities getDesiredCapabilities(String downloadDirectory) {
	        Map<String, Object> prefs = new HashMap<>();
	        DesiredCapabilities profile = DesiredCapabilities.chrome();
	        ChromeOptions options = new ChromeOptions();

	        prefs.put("download.default_directory", downloadDirectory);
	        prefs.put("profile.default_content_settings.popups", 0);
	        options.setExperimentalOption("prefs", prefs);
	        profile.setCapability("name", "chrome");
	        profile.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
	        profile.setCapability(ChromeOptions.CAPABILITY, options);
	        profile.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	        return profile;
	    }
}
