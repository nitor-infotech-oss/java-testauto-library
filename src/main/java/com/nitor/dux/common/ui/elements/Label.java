package com.nitor.dux.common.ui.elements;

import org.openqa.selenium.WebElement;

import com.nitor.dux.common.ui.ElementImpl;

public class Label extends ElementImpl implements ILabel {

	public Label(WebElement element) {
		super(element);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getFor() {
		return null;
	}

}
