package com.nitor.dux.common.ui.elements;

import com.nitor.dux.common.ui.IElement;
import com.nitor.dux.common.ui.ImplementedBy;



/**
 * Interface that wraps a WebElement in CheckBox functionality.
 */
@ImplementedBy(CheckBox.class)
public interface ICheckBox extends IElement {

    /**
     * Toggle the state of the checkbox.
     */
    void toggle();

    /**
     * Checks checkbox if unchecked.
     */
    void check();

    /**
     * Un-checks checkbox if checked.
     */
    void uncheck();

    /**
     * Check if an element is selected, and return boolean.
     *
     * @return true if check is checked, return false in other case
     */
    boolean isChecked();
}