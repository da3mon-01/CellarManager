package hu.pte.schafferg.cellarManager.ui;



import hu.pte.schafferg.cellarManager.model.User;
import hu.pte.schafferg.cellarManager.ui.components.UserList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
	private Label currentSelection = new Label("Currently Selected: ");
	private HorizontalLayout toolbar = new HorizontalLayout();
	
	public void initContent(){
		setMargin(true);
		setSpacing(true);
		toolbar.setSpacing(true);
		
		newUser.addStyleName("icon-on-top");
		newUser.setIcon(new ThemeResource("icons/add.png"));
		toolbar.addComponent(newUser);
		
		editUser.addStyleName("icon-on-top");
		editUser.setIcon(new ThemeResource("icons/edit.png"));
		toolbar.addComponent(editUser);
		
		saveUser.addStyleName("icon-on-top");
		saveUser.setIcon(new ThemeResource("icons/save.png"));
		toolbar.addComponent(saveUser);
				
		delUser.addStyleName("icon-on-top");
		delUser.setIcon(new ThemeResource("icons/delete.png"));
		delUser.addListener((ClickListener)this);
		toolbar.addComponent(delUser);
		
		addComponent(toolbar);
		
		userList.addListener((ValueChangeListener)this);
		addComponent(userList);
		addComponent(currentSelection);
		
		
	}
	
	private void changeCurrentSelection(Object user) {
		User selection = (User)user;
		currentSelection.setValue("Currently Selected: "+ selection.getUsername());
		selectedUser = selection;
		logger.debug("Current selection: " + selectedUser.getUsername());
		
	}

	private void deleteUser() {
		if(selectedUser != null){
			if(userList.deleteUser(selectedUser)){
				getWindow().showNotification("Delete Successfull", selectedUser.getUsername()+" successfully deleted", Notification.TYPE_TRAY_NOTIFICATION);
			}else{
				getWindow().showNotification("Delete Error", selectedUser.getUsername()+ "was not deleted", Notification.TYPE_ERROR_MESSAGE);
			}
			selectedUser = null;
		}
		
		
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
		if(source == delUser){
			openDeleteConfirmWindow();
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
	
	

}
