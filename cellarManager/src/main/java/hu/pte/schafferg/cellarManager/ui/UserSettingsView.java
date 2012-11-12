package hu.pte.schafferg.cellarManager.ui;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class UserSettingsView extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = -518244813705398334L;
	private Label label = new Label("placeHolder view");

	
	public void initContent(){
		addComponent(label);
	}
}
