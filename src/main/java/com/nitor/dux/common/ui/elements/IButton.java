package com.nitor.dux.common.ui.elements;

import com.nitor.dux.common.ui.IElement;
import com.nitor.dux.common.ui.ImplementedBy;

@ImplementedBy(Button.class)
public interface IButton extends IElement{

	 void click();
	 
}
