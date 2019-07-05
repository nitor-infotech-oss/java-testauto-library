package com.nitor.dux.common.ui.elements;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.nitor.dux.common.ui.ElementImpl;

public class Select extends ElementImpl implements ISelect {

	public Select(WebElement element) {
		super(element);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMultiple() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deselectByIndex(int index) {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectByValue(String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public WebElement getFirstSelectedOption() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void selectByVisibleText(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deselectByValue(String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deselectAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<WebElement> getAllSelectedOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> getOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deselectByVisibleText(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectByIndex(int index) {
		// TODO Auto-generated method stub

	}

}
