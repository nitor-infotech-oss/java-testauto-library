package com.nitor.dux.common.ui.driver;


import org.openqa.selenium.WebDriver;

import com.nitor.dux.common.configuration.UIConfiguration;
import com.nitor.dux.common.ui.browser.ChromeBrowser;
import com.nitor.dux.common.ui.browser.FirefoxBrowser;

import lombok.Data;

@Data
class DriverInstance {
    private UIConfiguration driverConfiguration;
    private WebDriver driver;

    public DriverInstance(UIConfiguration driverConfiguration) {
        this.driverConfiguration = driverConfiguration;
        startWebDriver(driverConfiguration);
    }
    
    public DriverInstance(WebDriver driver) {
        this.driver=driver;
    }

    private void startWebDriver(UIConfiguration driverConfiguration) {
        switch (driverConfiguration.getBrowserType()) {
            case FIREFOX:
                driver = new FirefoxBrowser(driverConfiguration).getDriver();
                break;
            case CHROME:
                driver = new ChromeBrowser(driverConfiguration).getDriver();
                break;
            
            default:
                throw new DriverNotFoundException("Not sure how to start browser for: " + driverConfiguration.getBrowserType().name());
        }
    }
}