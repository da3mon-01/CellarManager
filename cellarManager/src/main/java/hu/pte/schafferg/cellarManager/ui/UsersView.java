package hu.pte.schafferg.cellarManager.ui;



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
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class UsersView extends VerticalLayout implements ValueChangeListener, ClickListener{

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
	private User selectedUser = null;
	private HorizontalLayout toolbar = new HorizontalLayout();
	@Autowired
	private UserForm userForm;
	private boolean newUserMode = false;
	@Autowired
	private RoleHelperService roleHelper;

	public void initContent(){
		setMargin(true);
		setSpacing(true);
		toolbar.setSpacing(true);

		newUser.addStyleName("icon-on-top");
		newUser.setIcon(new ThemeResource("icons/add.png"));
		newUser.addListener((ClickListener)this);
		toolbar.addComponent(newUser);

		editUser.addStyleName("icon-on-top");
		editUser.setIcon(new ThemeResource("icons/edit.png"));
		editUser.addListener((ClickListener)this);
		toolbar.addComponent(editUser);

		saveUser.addStyleName("icon-on-top");
		saveUser.setIcon(new ThemeResource("icons/save.png"));
		saveUser.addListener((ClickListener)this);
		toolbar.addComponent(saveUser);

		delUser.addStyleName("icon-on-top");
		delUser.setIcon(new ThemeResource("icons/delete.png"));
		delUser.addListener((ClickListener)this);
		toolbar.addComponent(delUser);
		
		addComponent(toolbar);

		userList.addListener((ValueChangeListener)this);
		addComponent(userList);
		addComponent(userForm);


	}

	private void changeCurrentSelection(Object user) {
		User selection = (User)user;
		selectedUser = selection;

		BeanItem<User> editedUser = convertUserToBeanItem(selection);
		userForm.setItemDataSource(editedUser);

		logger.debug("Current selection: " + selectedUser.getUsername());

	}

	private BeanItem<User> convertUserToBeanItem(User user){

		BeanItem<User> editedUser = new BeanItem<User>(user, new String[]{"username"});	

		editedUser.addItemProperty("personFirstName", new NestedMethodProperty(user,"person.firstName") );
		editedUser.addItemProperty("personLastName", new NestedMethodProperty(user,"person.lastName") );
		editedUser.addItemProperty("personCity", new NestedMethodProperty(user,"person.city") );
		editedUser.addItemProperty("personAddress", new NestedMethodProperty(user,"person.address") );
		editedUser.addItemProperty("personZip", new NestedMethodProperty(user,"person.zip") );
		editedUser.addItemProperty("personBirthDate", new NestedMethodProperty(user,"person.birthDate") );
		editedUser.addItemProperty("personEmail", new NestedMethodProperty(user,"person.email") );
		editedUser.addItemProperty("personPhoneNumber", new NestedMethodProperty(user,"person.phoneNumber") );
		editedUser.addItemProperty("roleRole", new NestedMethodProperty(user,"role.role") );

		return editedUser;
	}
	
	private User commitToUserFromForm(User user){
		user.setUsername((String) userForm.getItemProperty("username").getValue());
		user.getPerson().setFirstName((String) userForm.getItemProperty("personFirstName").getValue());
		user.getPerson().setLastName((String) userForm.getItemProperty("personLastName").getValue());
		user.getPerson().setCity((String) userForm.getItemProperty("personCity").getValue());
		user.getPerson().setAddress((String) userForm.getItemProperty("personAddress").getValue());
		user.getPerson().setZip((int) userForm.getItemProperty("personZip").getValue());
		user.getPerson().setBirthDate((Date) userForm.getItemProperty("personBirthDate").getValue());
		user.getPerson().setEmail((String) userForm.getItemProperty("personEmail").getValue());
		user.getPerson().setPhoneNumber((String) userForm.getItemProperty("personPhoneNumber").getValue());
		user.getRole().setRole((Integer) userForm.getItemProperty("roleRole").getValue());
		
		return user;
		
	}

	private void createUser() {
		// TODO Auto-generated method stub

	}

	private void updateUser() {
		logger.info("Trying update method");

		if(selectedUser == null || userForm.getItemDataSource() == null){
			getWindow().showNotification("Update Failed", "Please select somebody first!", Notification.TYPE_WARNING_MESSAGE);
			return;
		}
		
		selectedUser = commitToUserFromForm(selectedUser);

		if(userList.updateUser(selectedUser)){
			getWindow().showNotification("Update successfull", selectedUser.getUsername()+"was updated successfully", Notification.TYPE_TRAY_NOTIFICATION);
		}else{
			getWindow().showNotification("Update Error!", selectedUser.getUsername()+"was not updated correctly", Notification.TYPE_ERROR_MESSAGE);
		}
	}

	private void deleteUser() {
		if(userList.deleteUser(selectedUser)){
			getWindow().showNotification("Delete Successfull", selectedUser.getUsername()+" successfully deleted", Notification.TYPE_TRAY_NOTIFICATION);
		}else{
			getWindow().showNotification("Delete Error", selectedUser.getUsername()+ "was not deleted", Notification.TYPE_ERROR_MESSAGE);
		}
		selectedUser = null;
		userForm.setItemDataSource(null);
	}

	private void openDeleteConfirmWindow(){
		ConfirmDialog confirmDialog =ConfirmDialog.show(getWindow(), "Are you sure you want to <b>DELETE</b> the selected user?", new ConfirmDialog.Listener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -313817438000138156L;

			public void onClose(ConfirmDialog dialog) {
				if(dialog.isConfirmed()){
					deleteUser();
				}

			}
		});
		confirmDialog.setContentMode(ConfirmDialog.CONTENT_HTML);
	}

	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		logger.info(source.getCaption() +" was clicked");
		if(source == delUser){
			if(selectedUser == null || userForm.getItemDataSource() == null){
				getWindow().showNotification("Warning", "Please select somebody first!", Notification.TYPE_WARNING_MESSAGE);
			}else{
				if(selectedUser.getUsername().equals(roleHelper.getCurrentAuthenticatedUser().getUsername())){
					getWindow().showNotification("Delete Error", "You cannot delete yourself! Please ask another admin to do it.", Notification.TYPE_ERROR_MESSAGE);
				}else{
					openDeleteConfirmWindow();
				}
			}
		}else if(source == editUser){
			if(selectedUser == null || userForm.getItemDataSource() == null){
				getWindow().showNotification("Warning", "Please select somebody first!", Notification.TYPE_WARNING_MESSAGE);
			}else{
				userForm.setReadOnly(false);
			}
		}else if(source == saveUser){
			if(!userForm.isValid()){
				getWindow().showNotification("Update Error", "Are all the required fields filled out?", Notification.TYPE_WARNING_MESSAGE);
				return;
			}
			userForm.commit();
			if(newUserMode == true){
				createUser();
			}else{
				updateUser();
			}

			userForm.setReadOnly(true);
			setNewUserMode(false);
		}else if(source == newUser){
			setNewUserMode(true);
		}
			
	}

	public void valueChange(ValueChangeEvent event) {
		logger.debug("valuechange. "+event);
		Property eventProperty = event.getProperty();
		if(eventProperty == userList){
			logger.debug("Value changed to: "+userList.getValue());
			changeCurrentSelection(userList.getValue());
		}

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
