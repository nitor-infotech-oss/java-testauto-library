package com.nitor.dux.common.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nitor.dux.common.ui.browser.BrowserType;

/**
 * {@link UIConfiguration} to store any ui related configs. As shown in this class there are firefox and chrome related configurations 
 * to be stored with this class.
 * @author anup.manekar
 *
 */
public class UIConfiguration extends BaseConfiguration {
	static final Logger LOGGER = LoggerFactory.getLogger(UIConfiguration.class);
	
	private int remotePort;
    private BrowserType browserType;
    private String executablePath;
    private FirefoxProfile firefoxProfile;
    private DesiredCapabilities chromeDesiredCapabilities;
    private String downloadDirectory;
    private String remoteWebdriverAddress;
    
    /**
     * Constructor
     * @param configuration
     */
    public UIConfiguration(JSONObject configuration){
    	super(configuration);
    	try{
    		this.remotePort = this.getConfig().getInt("REMOTEPORT");
        	this.executablePath = this.getConfig().getString("EXECUTABLEPATH");
        	this.downloadDirectory = this.getConfig().getString("DOWNLOADDIRECTORY");
        	this.chromeDesiredCapabilities = this.getChromeDesiredCapabilities(this.downloadDirectory);
        	this.firefoxProfile = this.getFirefoxProfile(this.downloadDirectory);
        	this.remoteWebdriverAddress = this.getConfig().getString("REMOTEWEBDRIVERADDRESS");
        	if(this.getConfig().getString("BROWSERTYPE").equalsIgnoreCase("CHROME"))
        		this.setBrowserType(BrowserType.CHROME);
        	if(this.getConfig().getString("BROWSERTYPE").equalsIgnoreCase("FIREFOX"))
        		this.setBrowserType(BrowserType.FIREFOX);
        	if(this.getConfig().getString("BROWSERTYPE").trim().equalsIgnoreCase(""))
        		this.setBrowserType(BrowserType.CHROME);
    	}catch(JSONException e){
    		LOGGER.error("Error while setting UI configuration:" + e.getMessage());
    		throw new Error(e);
    	}
    	    	
    	
    }
    
    /**
     * Getting chrome desired capabilities object
     * @param downloadDirectory
     * @return
     */
    public DesiredCapabilities getChromeDesiredCapabilities(String downloadDirectory) {
        Map<String, Object> prefs = new HashMap<>();
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();

        prefs.put("download.default_directory", downloadDirectory);
        prefs.put("profile.default_content_settings.popups", 0);
        options.setExperimentalOption("prefs", prefs);
        desiredCapabilities.setCapability("name", "chrome");
        desiredCapabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        return desiredCapabilities;
    }
    
    /**
     * Getting firefox profile object
     * @param downloadDirectory
     * @return
     */
    public FirefoxProfile getFirefoxProfile(String downloadDirectory) {
        FirefoxProfile profile = new FirefoxProfile();
        List<String> properties = new ArrayList<>();
        properties.add(String.format("browser.download.dir=%s", downloadDirectory));
        properties.add("warnOnOpen=false");
        properties.add("browser.download.folderList=2");
        properties.add("browser.download.manager.showWhenStarting=false");
        properties.add("browser.helperApps.neverAsk.saveToDisk=text/csv,application/pdf,application/csv,application/vnd.ms-excel");
        properties.add("browser.download.manager.showAlertOnComplete=false");
        properties.add("browser.download.manager.focusWhenStarting=false");
        properties.add("browser.download.manager.closeWhenDone=true");
        properties.add("browser.download.panel.shown=false");
        properties.add("browser.download.useToolkitUI=true");
        properties.add("app.update.auto=false");
        properties.add("app.update.enabled=false");

        if (properties != null) {
            for (String property : properties) {
                if (property == null) continue;
                String lineRegex = "([^=]+)=(.+)$";
                Pattern p = Pattern.compile(lineRegex);
                Matcher m = p.matcher(property);
                m.find(0);
                String key = m.group(1).trim();
                String value = m.group(2).trim();
                if (value.matches("\\d+")) {
                    profile.setPreference(key, Integer.parseInt(value));
                } else if (value.matches("true|false")) {
                    profile.setPreference(key, Boolean.parseBoolean(value));
                } else {  //treat as string
                    profile.setPreference(key, value);
                }
            }
        }
        profile.setEnableNativeEvents(false);
        return profile;
    }

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public BrowserType getBrowserType() {
		return browserType;
	}

	public void setBrowserType(BrowserType browserType) {
		this.browserType = browserType;
	}

	public String getExecutablePath() {
		return executablePath;
	}

	public void setExecutablePath(String executablePath) {
		this.executablePath = executablePath;
	}

	public FirefoxProfile getFirefoxProfile() {
		return firefoxProfile;
	}

	public void setFirefoxProfile(FirefoxProfile firefoxProfile) {
		this.firefoxProfile = firefoxProfile;
	}

	public DesiredCapabilities getChromeDesiredCapabilities() {
		return chromeDesiredCapabilities;
	}

	public void setChromeDesiredCapabilities(DesiredCapabilities chromeDesiredCapabilities) {
		this.chromeDesiredCapabilities = chromeDesiredCapabilities;
	}

	public String getDownloadDirectory() {
		return downloadDirectory;
	}

	public void setDownloadDirectory(String downloadDirectory) {
		this.downloadDirectory = downloadDirectory;
	}

	public String getRemoteWebdriverAddress() {
		return remoteWebdriverAddress;
	}

	public void setRemoteWebdriverAddress(String remoteWebdriverAddress) {
		this.remoteWebdriverAddress = remoteWebdriverAddress;
	}

}
