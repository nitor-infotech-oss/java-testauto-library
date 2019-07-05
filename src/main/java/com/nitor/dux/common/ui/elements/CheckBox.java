package com.nitor.dux.common.ui.elements;

import org.openqa.selenium.WebElement;

import com.nitor.dux.common.ui.ElementImpl;

/**
 * Wrapper class like Select that wraps basic checkbox functionality.
 */
public class CheckBox extends ElementImpl implements ICheckBox {

    /**
     * Wraps a WebElement with checkbox functionality.
     *
     * @param element to wrap up
     */
    public CheckBox(WebElement element) {
        super(element);
    }

    public void toggle() {
        getWrappedElement().click();
    }

    public void check() {
        if (!isChecked()) {
            toggle();
        }
    }

    public void uncheck() {
        if (isChecked()) {
            toggle();
        }
    }

    public boolean isChecked() {
        return getWrappedElement().isSelected();
    }
}