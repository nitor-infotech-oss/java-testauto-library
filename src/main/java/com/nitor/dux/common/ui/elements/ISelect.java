package com.nitor.dux.common.ui.elements;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.nitor.dux.common.ui.IElement;
import com.nitor.dux.common.ui.ImplementedBy;

/**
 * Interface for a select element.
 */
@ImplementedBy(Select.class)
public interface ISelect extends IElement {

    boolean isMultiple();

    void deselectByIndex(int index);

    void selectByValue(String value);

    WebElement getFirstSelectedOption();

    void selectByVisibleText(String text);

    void deselectByValue(String value);

    void deselectAll();

    List<WebElement> getAllSelectedOptions();

    List<WebElement> getOptions();

    void deselectByVisibleText(String text);

    void selectByIndex(int index);

}