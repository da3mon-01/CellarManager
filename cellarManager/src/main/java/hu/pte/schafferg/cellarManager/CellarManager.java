package hu.pte.schafferg.cellarManager;

import hu.pte.schafferg.cellarManager.services.RoleHelperService;
import hu.pte.schafferg.cellarManager.services.UserService;
import hu.pte.schafferg.cellarManager.ui.ContactsView;
import hu.pte.schafferg.cellarManager.ui.FieldWorkView;
import hu.pte.schafferg.cellarManager.ui.GrapeMustView;
import hu.pte.schafferg.cellarManager.ui.GrapeView;
import hu.pte.schafferg.cellarManager.ui.LandsView;
import hu.pte.schafferg.cellarManager.ui.UserSettingsView;
import hu.pte.schafferg.cellarManager.ui.UsersView;
import hu.pte.schafferg.cellarManager.ui.WelcomeView;
import hu.pte.schafferg.cellarManager.ui.components.MainMenu;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.Application;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.themes.Reindeer;

public class CellarManager extends Application{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6931622312405516905L;

	private HorizontalSplitPanel horizontalSplit = new HorizontalSplitPanel();
	private String appTitle;
	private Window appWindow;
	@Autowired
	private WelcomeView welcomeView;
	CssLayout menu = new CssLayout();
	protected static Logger logger = Logger.getLogger(CellarManager.class);
	@Autowired
	private UsersView usersView;
	@Autowired
	private UserService userService;
	@Autowired
	private UserSettingsView userSettingsView;
	@Autowired
	private MainMenu mainMenu;
	@Autowired
	private RoleHelperService roleHelper;
	@Autowired
	private ContactsView contacts;
	@Autowired
	private LandsView landsView;
	@Autowired
	private FieldWorkView fieldWorkView;
	@Autowired
	private GrapeView grapeView;
	@Autowired
	private GrapeMustView mustView;
	



	@Override
	public void init() {
		VerticalLayout layout = new VerticalLayout();
		
		setCurrentUser();
		setTheme("cellarManager");
		setLogoutURL("/cellarManager/logout");
		layout.setSizeFull();
		
		horizontalSplit.addStyleName(Reindeer.SPLITPANEL_SMALL);
		horizontalSplit.setSplitPosition(10, HorizontalSplitPanel.UNITS_PERCENTAGE);
		horizontalSplit.setLocked(true);
		
		horizontalSplit.setFirstComponent(mainMenu);
		setMainComponent(welcomeView);
		
		layout.addComponent(horizontalSplit);
		appWindow = new Window(appTitle, layout);
		appWindow.addListener(new Window.CloseListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -4913176188268744045L;

			@Override
			public void windowClose(CloseEvent e) {
				logout();
				
			}
		});
		setMainWindow(appWindow);

	}

	private void setCurrentUser() {
		setUser(null);
		hu.pte.schafferg.cellarManager.model.User loggedInUser = new hu.pte.schafferg.cellarManager.model.User();
		loggedInUser = userService.readByUserName(roleHelper.getCurrentAuthenticatedUser().getUsername());
		logger.info("Current User: "+loggedInUser.getUsername()+" has following authorities "+roleHelper.getCurrentAuthenticatedUser().getAuthorities().toString());
		setUser(loggedInUser);
		
	}

	
	public void switchtoUsers() {
		setMainComponent(usersView);
		
	}

	public void switchtoContacts() {
		setMainComponent(contacts);		
	}

	public void switchtoUserSettings() {
		setMainComponent(userSettingsView);
		
	}
	
	public void switchtoLands(){
		setMainComponent(landsView);
	}
	
	public void switchToFieldWorkView(){
		setMainComponent(fieldWorkView);
	}
	
	public void switchToGrapeView(){
		setMainComponent(grapeView);
	}
	
	public void switchToMustView(){
		setMainComponent(mustView);
	}

	public void logout() {
		logger.info("Logging out current User.");
		this.close();
		
	}
	
	
	public void setMainComponent(Component c){
		horizontalSplit.setSecondComponent(c);
		logger.info("Main component set to: "+ c.getClass().getSimpleName());
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

	public UserSettingsView getUserSettingsView() {
		return userSettingsView;
	}

	public void setUserSettingsView(UserSettingsView userSettingsView) {
		this.userSettingsView = userSettingsView;
	}

	public ContactsView getContacts() {
		return contacts;
	}

	public void setContacts(ContactsView contacts) {
		this.contacts = contacts;
	}

	public void setUsersView(UsersView usersView) {
		this.usersView = usersView;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public RoleHelperService getRoleHelper() {
		return roleHelper;
	}

	public void setRoleHelper(RoleHelperService roleHelper) {
		this.roleHelper = roleHelper;
	}

	public LandsView getLandsView() {
		return landsView;
	}

	public void setLandsView(LandsView landsView) {
		this.landsView = landsView;
	}

	public FieldWorkView getFieldWorkView() {
		return fieldWorkView;
	}

	public void setFieldWorkView(FieldWorkView fieldWorkView) {
		this.fieldWorkView = fieldWorkView;
	}

	public GrapeView getGrapeView() {
		return grapeView;
	}

	public void setGrapeView(GrapeView grapeView) {
		this.grapeView = grapeView;
	}
	
	
	
	

}
