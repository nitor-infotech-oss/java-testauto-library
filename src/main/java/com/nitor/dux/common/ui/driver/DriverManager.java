package com.nitor.dux.common.ui.driver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.nitor.dux.common.configuration.UIConfiguration;

import lombok.Setter;
import lombok.experimental.Delegate;

public enum DriverManager {		
	INSTANCE;
	private static final Logger LOGGER = LoggerFactory.getLogger(DriverManager.class);
	
    private int activeDriverIndex = 0;

    @Setter
    private UIConfiguration driverConfiguration;
    private LinkedList<DriverInstance> drivers = new LinkedList<>();

    @Delegate
    public WebDriverActions webdriver() {
        return new WebDriverActions(getDriver());
    }

    public UIConfiguration getDriverConfiguration() {
        Preconditions.checkNotNull(driverConfiguration);
        return driverConfiguration;
    }

    public void startDriver() {
        Preconditions.checkNotNull(driverConfiguration);
        DriverInstance driverInstance = new DriverInstance(driverConfiguration);
        drivers.add(driverInstance);
        resetActiveDriverIndex();
        LOGGER.info("User Agent: " + getUserAgent());
    }

    public void useDriver(WebDriver driver) {
        Preconditions.checkNotNull(driver);
        DriverInstance driverInstance = new DriverInstance(driver);
        drivers.add(driverInstance);
        resetActiveDriverIndex();
        LOGGER.info("User Agent: " + getUserAgent());
    }

    public void stopDriver() {
        DriverInstance driverInstance = drivers.removeLast();
        LOGGER.info(String.format("Stopping Last Opened Driver. Drivers Running: %s", drivers.size()));
        driverInstance.getDriver().quit();
        resetActiveDriverIndex();
        if (isDriverStarted()) {
            switchWindow();
        } else {
            activeDriverIndex = 0;
        }
    }

    public int getNumberOfDrivers() {
        return drivers.size();
    }

    public void get(String url) {
        try {
            getDriver().get(url);
        } catch (UnreachableBrowserException e) {
            // this is a workaround for losing the connection or failing to start driver
        	LOGGER.info("WebDriver died...attempting restart");
            stopDriver();
            startDriver();
            getDriver().get(url);
        }
        //waitForPageLoad();
        
    }

    // package private so we don't leak this outside of the abstraction
    public WebDriver getDriver() {
        if (!isDriverStarted()) throw new DriverNotFoundException("Unable to locate a started WebDriver instance");
        return drivers.get(activeDriverIndex).getDriver();
    }

    // switches to the last opened window
    public void switchWindow() {
        List<String> windowHandles = new ArrayList<>(getWindowHandles());
        getDriver().switchTo().window(windowHandles.get(windowHandles.size() - 1));
    }

    // selects active driver
    public void switchDriver(int index) {
        Preconditions.checkArgument(index < drivers.size());
        this.activeDriverIndex = index;
    }

    // closes the last opened window
    public void closeWindow() {
        switchWindow();
        if (getWindowHandles().size() > 1) {
            getDriver().close();
            switchWindow();
        }
    }

    public void stopAllDrivers() {
        while (isDriverStarted()) {
            try {
                stopDriver();
            } catch (WebDriverException e) {
            	LOGGER.info(String.format("There was an ignored exception closing the web driver : %s", e));
            }
        }
    }

    public String getDownloadDirectory() {
        return getDriverConfiguration().getDownloadDirectory();
    }

    public boolean isDriverStarted() {
        return drivers.size() > 0;
    }

    public LogEntries getConsoleLog() {
    	LOGGER.info("Console Log output: ");
        DriverManager.INSTANCE.executeScript("console.log('Logging Errors');");
        return DriverManager.INSTANCE.getDriver().manage().logs().get(LogType.BROWSER);
    }

    private void resetActiveDriverIndex() {
        activeDriverIndex = drivers.size() - 1;
    }

}