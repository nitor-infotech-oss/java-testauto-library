package com.nitor.dux.common.ui.driver;

import java.io.File;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.interactions.Actions;

public interface IWebDriverActions {

	 public boolean isAlertPresent();

	    public String getAlertText() ;

	    public void acceptAlert() ;
	    
	    public String getText() ;

	    public String getPageSource() ;
	    
	    public String getHtml() ;

	    public String getCurrentUrl() ;

	    public String getTitle() ;

	    public String getUserAgent() ;

	    public Set<String> getWindowHandles() ;

	    public void deleteAllCookies() ;
	    
	    public void deleteCookie(Cookie cookie) ;

	    public void addCoookie(Cookie cookie) ;

	    public Cookie getCookie(String name) ;

	    public void deleteCookie(String name) ;

	    public Set<Cookie> getCookies() ;
	    
	    public TargetLocator switchTo() ;

	    public Actions getActions() ;

	    public Navigation navigate() ;

	    public Object executeScript(String script) ;

	    public Object executeScript(String script, Object... args) ;

	    public File takeScreenshot() ;

	    public WebDriver.Window getWindow() ;

	    public void maximize() ;

	    public void setSize(Dimension dimension) ;

}
