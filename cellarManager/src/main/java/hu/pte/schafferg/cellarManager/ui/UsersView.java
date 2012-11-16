package hu.pte.schafferg.cellarManager.ui;

import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.model.Role;
import hu.pte.schafferg.cellarManager.model.User;
import hu.pte.schafferg.cellarManager.services.RoleHelperService;
import hu.pte.schafferg.cellarManager.ui.components.UserForm;
import hu.pte.schafferg.cellarManager.ui.components.UserList;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.NestedMethodProperty;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class UsersView extends VerticalLayout implements ValueChangeListener,
		ClickListener, TextChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1751591394349374109L;
	private static Logger logger = Logger.getLogger(UsersView.class);
	@Autowired
	private UserList userList;
	private Button newUser = new Button("New");
	private Button editUser = new Button("Edit");
	private Button saveUser = new Button("Save");
	private Button delUser = new Button("Delete");
	private Button resetPass = new Button("Reset Password");
	private User selectedUser = null;
	private HorizontalLayout toolbar = new HorizontalLayout();
	private TextField searchField = new TextField();
	private Select searchSelect = new Select();
	
	@Autowired
	private UserForm userForm;
	private boolean newUserMode = false;
	@Autowired
	private RoleHelperService roleHelper;

	public void initContent() {
		setMargin(true);
		setSpacing(true);
		toolbar.setSpacing(true);

		newUser.addStyleName("big");
		newUser.setIcon(new ThemeResource("icons/add.png"));
		newUser.addListener((ClickListener) this);
		toolbar.addComponent(newUser);

		editUser.addStyleName("big");
		editUser.setIcon(new ThemeResource("icons/edit.png"));
		editUser.addListener((ClickListener) this);
		toolbar.addComponent(editUser);

		saveUser.addStyleName("big");
		saveUser.setIcon(new ThemeResource("icons/save.png"));
		saveUser.addListener((ClickListener) this);
		toolbar.addComponent(saveUser);

		delUser.addStyleName("big");
		delUser.setIcon(new ThemeResource("icons/delete.png"));
		delUser.addListener((ClickListener) this);
		toolbar.addComponent(delUser);
		
		resetPass.addStyleName("big");
		resetPass.setIcon(new ThemeResource("icons/genNewPass.png"));
		resetPass.addListener((ClickListener)this);
		toolbar.addComponent(resetPass);
		
		searchField.addStyleName("search big");
		searchField.addListener((TextChangeListener)this);
		toolbar.addComponent(searchField);
		toolbar.setComponentAlignment(searchField, Alignment.MIDDLE_CENTER);
		
		searchSelect.addStyleName("big");
		Object[] visibleProperties = userList.getListOfVisibleColumns();
		String[] properNames = userList.getListOfColumnHeaders();
		for(int i=0; i < visibleProperties.length; i++){
			searchSelect.addItem(visibleProperties[i]);
			searchSelect.setItemCaption(visibleProperties[i], properNames[i]);
		}
		searchSelect.setNewItemsAllowed(false);
		searchSelect.setNullSelectionAllowed(false);
		searchSelect.setImmediate(true);
		searchSelect.select(visibleProperties[0]);
		searchSelect.addListener((ValueChangeListener)this);
		toolbar.addComponent(searchSelect);
		toolbar.setComponentAlignment(searchSelect, Alignment.MIDDLE_CENTER);

		addComponent(toolbar);

		userList.addListener((ValueChangeListener) this);
		addComponent(userList);
		
		Panel formPanel = new Panel();
		formPanel.setCaption("User Details");
		formPanel.addComponent(userForm);
		addComponent(formPanel);

	}

	private void changeCurrentSelection(Object user) {
		User selection = (User) user;
		selectedUser = selection;

		BeanItem<User> editedUser = convertUserToBeanItem(selection);
		userForm.setItemDataSource(editedUser);

		logger.debug("Current selection: " + selectedUser.getUsername());

	}

	private BeanItem<User> convertUserToBeanItem(User user) {

		BeanItem<User> editedUser = new BeanItem<User>(user,
				new String[] { "username" });

		editedUser.addItemProperty("personFirstName", new NestedMethodProperty(
				user, "person.firstName"));
		editedUser.addItemProperty("personLastName", new NestedMethodProperty(
				user, "person.lastName"));
		editedUser.addItemProperty("personCity", new NestedMethodProperty(user,
				"person.city"));
		editedUser.addItemProperty("personAddress", new NestedMethodProperty(
				user, "person.address"));
		editedUser.addItemProperty("personZip", new NestedMethodProperty(user,
				"person.zip"));
		editedUser.addItemProperty("personBirthDate", new NestedMethodProperty(
				user, "person.birthDate"));
		editedUser.addItemProperty("personEmail", new NestedMethodProperty(
				user, "person.email"));
		editedUser.addItemProperty("personPhoneNumber",
				new NestedMethodProperty(user, "person.phoneNumber"));
		editedUser.addItemProperty("roleRole", new NestedMethodProperty(user,
				"role.role"));

		return editedUser;
	}

	private User commitToUserFromForm(User user) {
		user.setUsername((String) userForm.getItemProperty("username")
				.getValue());
		user.getPerson()
				.setFirstName(
						(String) userForm.getItemProperty("personFirstName")
								.getValue());
		user.getPerson().setLastName(
				(String) userForm.getItemProperty("personLastName").getValue());
		user.getPerson().setCity(
				(String) userForm.getItemProperty("personCity").getValue());
		user.getPerson().setAddress(
				(String) userForm.getItemProperty("personAddress").getValue());
		user.getPerson().setZip(
				(int) userForm.getItemProperty("personZip").getValue());
		user.getPerson().setBirthDate(
				(Date) userForm.getItemProperty("personBirthDate").getValue());
		user.getPerson().setEmail(
				(String) userForm.getItemProperty("personEmail").getValue());
		user.getPerson().setPhoneNumber(
				(String) userForm.getItemProperty("personPhoneNumber")
						.getValue());
		user.getRole().setRole(
				(Integer) userForm.getItemProperty("roleRole").getValue());

		return user;

	}

	private void createUser() {
		if(selectedUser == null || userForm.getItemDataSource() == null){
			getWindow().showNotification("Save Failed",
					"Please click New first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}else{
			selectedUser = commitToUserFromForm(selectedUser);
			
			try {
				userList.createUser(selectedUser);
				getWindow().showNotification("Success!", "User "+selectedUser.getUsername()+" was created successfully", Notification.TYPE_TRAY_NOTIFICATION);
			} catch (RuntimeException e) {
				getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			} finally {
				changeCurrentSelection(selectedUser);
			}
			
			
			
		}

	}

	private void updateUser() {
		logger.info("Trying update user method");

		if (selectedUser == null || userForm.getItemDataSource() == null) {
			getWindow().showNotification("Save Failed",
					"Please select somebody first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}

		selectedUser = commitToUserFromForm(selectedUser);
		
		try {
			userList.updateUser(selectedUser);
			getWindow().showNotification("Success!", "User "+selectedUser.getUsername()+" was updated successfully", Notification.TYPE_TRAY_NOTIFICATION);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}finally {
			changeCurrentSelection(selectedUser);
		}
		
		
	}

	private void deleteUser() {
		try {
			userList.deleteUser(selectedUser);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		
		getWindow().showNotification("Success!", "User "+selectedUser.getUsername()+" was deleted successfully", Notification.TYPE_TRAY_NOTIFICATION);
		selectedUser = null;
		userForm.setItemDataSource(null);
	}

	protected void resetPassword() {
		
		try {
			userList.resetPassword(selectedUser);
			getWindow().showNotification("Success!", selectedUser.getUsername()+"'s was password was changed.", Notification.TYPE_TRAY_NOTIFICATION);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			
		}finally {
			changeCurrentSelection(selectedUser);
		}
				
				
	}

	private void openDeleteConfirmWindow() {
		ConfirmDialog confirmDialog = ConfirmDialog.show(getWindow(),
				"Are you sure you want to <b>DELETE</b> the selected user?",
				new ConfirmDialog.Listener() {

					/**
			 * 
			 */
					private static final long serialVersionUID = -313817438000138156L;

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							deleteUser();
						}

					}
				});
		confirmDialog.setContentMode(ConfirmDialog.CONTENT_HTML);
	}

	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		logger.info(source.getCaption() + " was clicked");
		if (source == delUser) {
			if (selectedUser == null || userForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select somebody first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				if (selectedUser.getUsername().equals(
						roleHelper.getCurrentAuthenticatedUser().getUsername())) {
					getWindow()
							.showNotification(
									"Delete Error",
									"You cannot delete yourself! Please ask another admin to do it.",
									Notification.TYPE_ERROR_MESSAGE);
				} else {
					openDeleteConfirmWindow();
				}
			}
		} else if (source == editUser) {
			if (selectedUser == null || userForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select somebody first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				userForm.setReadOnly(false);
			}
		} else if (source == saveUser) {
			if (!userForm.isValid()) {
				getWindow().showNotification("Update Error",
						"Are all the required fields filled out?",
						Notification.TYPE_WARNING_MESSAGE);
				return;
			}
			userForm.commit();

			if(newUserMode){
				createUser();
			}else{
				updateUser();
			}

			userForm.setReadOnly(true);
			setNewUserMode(false);
		} else if (source == newUser) {
			setNewUserMode(true);
			User userToAdd = new User();
			Person personToAdd = new Person();
			Role roleToAdd = new Role();
			
			userToAdd.setPerson(personToAdd);
			userToAdd.setRole(roleToAdd);
			
			userForm.setReadOnly(false);
			
			changeCurrentSelection(userToAdd);
		}else if(source == resetPass){
			if(selectedUser == null || userForm.getItemDataSource() == null){
				getWindow().showNotification("Warning", "Please select something first!", Notification.TYPE_WARNING_MESSAGE);
				return;
			}else{
				ConfirmDialog d = ConfirmDialog.show(getWindow(), "Are you sure you want to <b>Reset the password</b> of "+selectedUser.getUsername(), new ConfirmDialog.Listener() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 7138346900303614721L;

					@Override
					public void onClose(ConfirmDialog dialog) {
						if(dialog.isConfirmed()){
							resetPassword();
						}
						
					}
				});
				d.setContentMode(ConfirmDialog.CONTENT_HTML);
			}
		}

	}

	public void valueChange(ValueChangeEvent event) {
		logger.debug("valuechange. " + event);
		Property eventProperty = event.getProperty();
		if (eventProperty == userList) {
			logger.debug("Value changed to: " + userList.getValue());
			setNewUserMode(false);
			changeCurrentSelection(userList.getValue());
		}else if(eventProperty == searchSelect){
			logger.info("Value changed to: "+searchSelect.getValue());
			SimpleStringFilter filter = null;
			filter = new SimpleStringFilter(searchSelect.getValue(), (String) searchField.getValue(), true, false);
			userList.addFilter(filter);
		}

	}

	@Override
	public void textChange(TextChangeEvent event) {
		SimpleStringFilter filter = null;
		
		filter = new SimpleStringFilter(searchSelect.getValue(), event.getText(), true, false);
		userList.addFilter(filter);
		
	}

	public UserList getUserList() {
		return userList;
	}

	public void setUserList(UserList userList) {
		this.userList = userList;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public UserForm getUserForm() {
		return userForm;
	}

	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}

	public boolean isNewUserMode() {
		return newUserMode;
	}

	public void setNewUserMode(boolean newUserMode) {
		this.newUserMode = newUserMode;
		this.userForm.setNewUserMode(this.newUserMode);
	}

}
