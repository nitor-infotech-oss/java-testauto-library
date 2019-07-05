package com.nitor.dux.common.ui.elements;

import org.openqa.selenium.WebElement;

import com.nitor.dux.common.ui.ElementImpl;

/**
 * TextInput  wrapper.
 */
public class TextInput extends ElementImpl implements ITextInput {
    /**
     * Creates a Element for a given WebElement.
     *
     * @param element element to wrap up
     */
    public TextInput(WebElement element) {
        super(element);
    }

    @Override
    public void clear() {
        getWrappedElement().clear();
    }

    @Override
    public void set(String text) {
        WebElement element = getWrappedElement();
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Gets the value of an input field.
     * @return String with the value of the field.
     */
    @Override
    public String getText() {
        return getWrappedElement().getAttribute("value");
    }
}