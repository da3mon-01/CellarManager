package hu.pte.schafferg.cellarManager.ui;



import hu.pte.schafferg.cellarManager.CellarManager;
import hu.pte.schafferg.cellarManager.model.User;
import hu.pte.schafferg.cellarManager.services.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class UserSettingsView extends VerticalLayout implements ClickListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -518244813705398334L;
	@Autowired
	private StandardPasswordEncoder sde;
	@Autowired
	private UserService userService;
	private Form passwordForm= new Form();
	private String passwordRegExp;
	private String passwordRegExpError;
	private CellarManager application;
	private Button saveButton = new Button("Save");
	private Logger logger = Logger.getLogger(UserSettingsView.class);

	
	public void initContent(){
		
		setSpacing(true);
		setMargin(true);
		
		passwordForm.setImmediate(true);
		passwordForm.setReadThrough(false);
		
		PasswordField currentPass = new PasswordField("Current Password");
		currentPass.setRequired(true);
		passwordForm.addField("currentPass", currentPass);
		
		PasswordField newpass1 =  new PasswordField("New Password");
		newpass1.addValidator(new RegexpValidator(passwordRegExp, passwordRegExpError));
		newpass1.setRequired(true);
		passwordForm.addField("newpass1",newpass1);
		
		PasswordField newpass2 = new PasswordField("Repeat New Password");
		newpass2.addValidator(new RegexpValidator(passwordRegExp, passwordRegExpError));
		newpass2.setRequired(true);
		passwordForm.addField("newpass2", newpass2);
		
		HorizontalLayout footer = new HorizontalLayout();
		saveButton.addListener((ClickListener)this);
		footer.addComponent(saveButton);
		
		passwordForm.setFooter(footer);
		
		Panel passwordPanel = new Panel();
		passwordPanel.setCaption("Change Password");
		passwordPanel.addComponent(passwordForm);
		addComponent(passwordPanel);
	}



	private void changePassword() {
		if(!passwordForm.isValid()){
			getWindow().showNotification("Warning", "Have you entered everything correctly?", Notification.TYPE_WARNING_MESSAGE);
			logger.info("Password Form invalid");
			return;
		}
		
		if(!(passwordForm.getField("newpass1").getValue().equals(passwordForm.getField("newpass2").getValue()))){
			getWindow().showNotification("Warning", "The new passwords dont match!", Notification.TYPE_WARNING_MESSAGE);
			logger.info("New passwords dont match");
			return;
		}
		
		User userToChange = userService.readByUserName(((User)application.getUser()).getUsername());
		if(sde.matches((CharSequence) (passwordForm.getField("currentPass").getValue()), userToChange.getPassword())){
			userToChange.setPassword(sde.encode((CharSequence) passwordForm.getField("newpass1").getValue()));
			
			try {
				userService.update(userToChange);
			} catch (RuntimeException e) {
				getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
				return;
			}
			
			getWindow().showNotification("Success!", "Your password was changed!", Notification.TYPE_TRAY_NOTIFICATION);
			
		}else{
			getWindow().showNotification("Error", "You have entered your current password incorrectly!", Notification.TYPE_ERROR_MESSAGE);
			return;
		}
	}



	@Override
	public void attach() {
		super.attach();
		application = (CellarManager)getApplication();
	}



	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == saveButton){
			changePassword();
		}
		
	}



	public StandardPasswordEncoder getSde() {
		return sde;
	}



	public void setSde(StandardPasswordEncoder sde) {
		this.sde = sde;
	}



	public UserService getUserService() {
		return userService;
	}



	public void setUserService(UserService userService) {
		this.userService = userService;
	}



	public Form getPasswordForm() {
		return passwordForm;
	}



	public void setPasswordForm(Form passwordForm) {
		this.passwordForm = passwordForm;
	}



	public String getPasswordRegExp() {
		return passwordRegExp;
	}



	public void setPasswordRegExp(String passwordRegExp) {
		this.passwordRegExp = passwordRegExp;
	}



	public String getPasswordRegExpError() {
		return passwordRegExpError;
	}



	public void setPasswordRegExpError(String passwordRegExpError) {
		this.passwordRegExpError = passwordRegExpError;
	}
	
	
	
	
}
