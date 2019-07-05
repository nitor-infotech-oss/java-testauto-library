package com.nitor.dux.common.ui.elements;

import com.nitor.dux.common.ui.IElement;
import com.nitor.dux.common.ui.ImplementedBy;

/**
 * Text field functionality.
 */
@ImplementedBy(TextInput.class)
public interface ITextInput extends IElement {
    /**
     * @param text The text to type into the field.
     */
    void set(String text);
}