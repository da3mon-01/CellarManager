package hu.pte.schafferg.cellarManager;

import hu.pte.schafferg.cellarManager.services.UserService;
import hu.pte.schafferg.cellarManager.ui.UsersView;
import hu.pte.schafferg.cellarManager.ui.WelcomeView;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

public class CellarManager extends Application  implements ClickListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6931622312405516905L;

	private VerticalSplitPanel verticalSplit = new VerticalSplitPanel();
	private String appTitle;
	@Autowired
	private WelcomeView welcomeView;
	protected static Logger logger = Logger.getLogger(CellarManager.class);
	private Button logoutButton = new Button("Log out", (ClickListener)this);
	private Button usersButton = new Button("Users", (ClickListener)this);
	private Label welcomeLabel;
	@Autowired
	private UsersView usersView;
	@Autowired
	private UserService userService;



	@Override
	public void init() {
		VerticalLayout layout = new VerticalLayout();
		
		setCurrentUser();
		
		setLogoutURL("j_spring_security_logout");
		layout.setSizeFull();
		layout.addComponent(verticalSplit);
		layout.setExpandRatio(verticalSplit, 1);
		verticalSplit.setSplitPosition(25, VerticalSplitPanel.UNITS_PIXELS);
		verticalSplit.setLocked(true);
		verticalSplit.setFirstComponent(createMainMenu());
		setMainComponent(welcomeView);
		
		setMainWindow(new Window(appTitle, layout));

	}

	private void setCurrentUser() {
		setUser(null);
		hu.pte.schafferg.cellarManager.model.User loggedInUser = new hu.pte.schafferg.cellarManager.model.User();
		loggedInUser = userService.readByUserName(getCurrentAuthenticatedUser().getUsername());
		logger.info("Current User: "+loggedInUser.getUsername()+" has following authorities "+getCurrentAuthenticatedUser().getAuthorities().toString());
		setUser(loggedInUser);
		
	}


	private Component createMainMenu() {
		HorizontalLayout horiLayout = new HorizontalLayout();
		horiLayout.setWidth("100%");
		welcomeLabel = new Label ("Welcome %USER-TODO%");
		horiLayout.addComponent(welcomeLabel);
		horiLayout.addComponent(usersButton);
		horiLayout.addComponent(logoutButton);
		return horiLayout;
		
	}
	
	private void logout() {
		logger.info("Logging out current User.");
		this.close();
		
	}
	
	public boolean hasRole(String role){
		boolean hasRole = false;
		Collection<GrantedAuthority> listOfAuths = getCurrentAuthenticatedUser().getAuthorities();
		for(GrantedAuthority auth: listOfAuths){
			if(auth.getAuthority().equals(role) && hasRole!=true){
				hasRole=true;
			}
		}
		
		return hasRole;
	}
	
	public User getCurrentAuthenticatedUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = null;
		
		if(principal instanceof User){
			user = (User)principal;
		}
		
		return user;
	}
	
	public void setMainComponent(Component c){
		verticalSplit.setSecondComponent(c);
		logger.info("Main component set to: "+ c.getClass().getSimpleName());
	}
	
	public void buttonClick(ClickEvent event) {
		if(event.getSource() == usersButton){
			setMainComponent(usersView);
		}else if(event.getSource()==logoutButton){
			logout();
		}
		
	}

	public String getAppTitle() {
		return appTitle;
	}

	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	public WelcomeView getWelcomeView() {
		return welcomeView;
	}

	public void setWelcomeView(WelcomeView welcomeView) {
		this.welcomeView = welcomeView;
	}

	public UsersView getUsersView() {
		return usersView;
	}



	public void setUsesView(UsersView usersView) {
		this.usersView = usersView;
	}
	
	
	
	
	
	
	
	















}
