package com.nitor.dux.common.ui.browser;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.nitor.dux.common.configuration.UIConfiguration;

public class FirefoxBrowser implements IBrowser {
	private static final Logger LOGGER = LoggerFactory.getLogger(FirefoxBrowser.class);
	private FirefoxProfile firefoxProfile;
    private String binaryPath;


    public FirefoxBrowser(UIConfiguration driverConfiguration) {
        firefoxProfile = driverConfiguration.getFirefoxProfile();
        binaryPath = driverConfiguration.getExecutablePath();
    }

    @Override
    public WebDriver getDriver() {
    	LOGGER.info(String.format("Firefox Profile: %s", firefoxProfile));
        if (StringUtils.isNotBlank(binaryPath)) {
            File file = new File(binaryPath);
            Preconditions.checkArgument(file.exists());
            LOGGER.info("Using the following FireFox executable: " + file);
            FirefoxBinary firefoxBinary = new FirefoxBinary((file));
            return new FirefoxDriver(firefoxBinary, firefoxProfile);
        }
        return new FirefoxDriver(firefoxProfile);
    }
}
