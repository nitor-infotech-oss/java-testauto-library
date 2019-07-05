package com.nitor.dux.common.ui.elements;

import com.nitor.dux.common.ui.IElement;
import com.nitor.dux.common.ui.ImplementedBy;

/**
 * Html form label.
 */
@ImplementedBy(Label.class)
public interface ILabel extends IElement {
    /**
     * Gets the for attribute on the label.
     *
     * @return string containing value of the for attribute, null if empty.
     */
    String getFor();
}
