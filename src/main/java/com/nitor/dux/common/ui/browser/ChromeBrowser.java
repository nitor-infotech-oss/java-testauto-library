package com.nitor.dux.common.ui.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.nitor.dux.common.configuration.UIConfiguration;

public class ChromeBrowser implements IBrowser {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChromeBrowser.class);
	
	private final DesiredCapabilities desiredCapabilities;
    private final String binaryPath;

    public ChromeBrowser(UIConfiguration driverConfiguration) {
        Preconditions.checkNotNull(driverConfiguration.getExecutablePath());
        desiredCapabilities = driverConfiguration.getChromeDesiredCapabilities();
        binaryPath = driverConfiguration.getExecutablePath();
    }

    public WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", binaryPath);
        WebDriver driver = new ChromeDriver(desiredCapabilities);
        LOGGER.info(String.format("Desired Capabilities: %s", desiredCapabilities));
        return driver;
    }

}
