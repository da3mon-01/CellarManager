package hu.pte.schafferg.cellarManager.ui.components;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import hu.pte.schafferg.cellarManager.CellarManager;
import hu.pte.schafferg.cellarManager.services.RoleHelperService;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;

public class MainMenu extends CssLayout implements ClickListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5829612919842748130L;
	private NativeButton logoutButton = new NativeButton("Log out", (ClickListener)this);
	private NativeButton usersButton = new NativeButton("Users", (ClickListener)this);
	private NativeButton userSettingsButton = new NativeButton("User Settings", (ClickListener)this);
	private NativeButton contactsButton = new NativeButton("Contacts", (ClickListener)this);
	private Label adminLabel = new Label("Admin");
	private Label applicationLabel = new Label("Application");
	private Label manageLabel = new Label("Manage");
	private Embedded appLogo;
	private CellarManager application;
	@Autowired
	private RoleHelperService roleHelper;
	
	public void initContent(){
		setStyleName("sidebar-menu");
		appLogo = new Embedded();
		appLogo.setSource(new ThemeResource("img/applogo.png"));
		this.setWidth("100%");
		appLogo.setWidth("100%");
		this.setHeight("100%");
		this.addComponent(appLogo);
		if(roleHelper.hasRole("ROLE_ADMIN")){
			this.addComponent(adminLabel);
			this.addComponent(usersButton);
		}
		
		this.addComponent(manageLabel);
		this.addComponent(contactsButton);
		
		this.addComponent(applicationLabel);
		this.addComponent(userSettingsButton);
		this.addComponent(logoutButton);
		
	}
	
	
	
	@Override
	public void attach() {
		super.attach();
		application = (CellarManager)getApplication();
	}



	private void updateButtonStyles() {
		for(Iterator<Component> iterator = this.getComponentIterator(); iterator.hasNext(); ){
			Component c = iterator.next();
			c.removeStyleName("selected");
		}
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		updateButtonStyles();
		event.getButton().addStyleName("selected");
		if(event.getButton() == logoutButton){
			application.logout();
		}else if(event.getButton() == usersButton){
			application.switchtoUsers();
		}else if(event.getButton() == userSettingsButton){
			application.switchtoUserSettings();
		}else if(event.getButton() == contactsButton){
			application.switchtoContacts();
		}
	}

}
