package com.nitor.dux.common.configuration;

public enum ConfigurationKeys {
	AUTOMATION_ENGINE_KEY("AUTOMATIONENGINE"),
	PLATFORM_NAME_KEY("PLATFORMNAME"),
	PLATFORM_VERSION_KEY("PLATFORMVERSION"),
	DEVICE_NAME_KEY("DEVICENAME"),
	APP_KEY("APP"),
	BROWSER_NAME_KEY("BROWSERNAME"),
	NEW_COMMAND_TIMEOUT_KEY("NEWCOMMANDTIMEOUT"),
	AUTO_LAUNCH_KEY("AUTOLAUNCH"),
	LANGUAGE_KEY("LANGUAGE"),
	LOCALE_KEY("LOCALE"),
	UDID_KEY("UDID"),
	ORIENTATION_KEY("ORIENTATION"),
	AUTOWEBVIEW_KEY("AUTOWEBVIEW");
	
	private final String text;

    private ConfigurationKeys(final String text) {
        this.text = text;
    }

    public String getText() {
		return text;
	}

	@Override
    public String toString() {
        return text;
    }
	
	
	
}
