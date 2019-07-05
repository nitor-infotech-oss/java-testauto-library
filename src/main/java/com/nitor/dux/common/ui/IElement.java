package com.nitor.dux.common.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

public interface IElement extends WebElement, WrapsElement, Locatable {

	boolean elementWired();
	
}
