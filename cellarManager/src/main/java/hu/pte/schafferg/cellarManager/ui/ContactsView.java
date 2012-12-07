package hu.pte.schafferg.cellarManager.ui;

import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.ui.components.ContactList;
import hu.pte.schafferg.cellarManager.ui.components.ContactListOfFieldWork;
import hu.pte.schafferg.cellarManager.ui.components.ContactListOfLandOwned;
import hu.pte.schafferg.cellarManager.ui.components.ContactListOfWineBottled;
import hu.pte.schafferg.cellarManager.ui.components.ContactListOfWineBought;
import hu.pte.schafferg.cellarManager.ui.components.ContactsForm;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
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
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class ContactsView extends VerticalLayout implements ClickListener,
ValueChangeListener, TextChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3865970177598989349L;
	private HorizontalLayout toolbar = new HorizontalLayout();
	private Button newPerson = new Button("New");
	private Button editPerson = new Button("Edit");
	private Button savePerson= new Button("Save");
	private Button delPerson = new Button("Delete");
	private TextField searchField = new TextField();
	private Select searchSelect = new Select();
	@Autowired
	private ContactList contactList;
	@Autowired
	private ContactsForm contactForm;
	@Autowired
	private ContactListOfFieldWork listOfFieldWork;
	@Autowired
	private ContactListOfLandOwned listOfLandOwned;
	@Autowired
	private ContactListOfWineBottled listOfWineBottled;
	@Autowired
	private ContactListOfWineBought listOfWineBought;
	private Person selection = null;
	private Logger logger = Logger.getLogger(ContactsView.class);
	private boolean newPersonMode = false;
	private TabSheet contactTab = new TabSheet();

	public void initContent(){
		
		setWidth("98%");
		setMargin(true);
		setSpacing(true);
		toolbar.setSpacing(true);

		newPerson.addStyleName("big");
		newPerson.setIcon(new ThemeResource("icons/add.png"));
		newPerson.addListener((ClickListener) this);
		toolbar.addComponent(newPerson);

		editPerson.addStyleName("big");
		editPerson.setIcon(new ThemeResource("icons/edit.png"));
		editPerson.addListener((ClickListener) this);
		toolbar.addComponent(editPerson);

		savePerson.addStyleName("big");
		savePerson.setIcon(new ThemeResource("icons/save.png"));
		savePerson.addListener((ClickListener) this);
		toolbar.addComponent(savePerson);

		delPerson.addStyleName("big");
		delPerson.setIcon(new ThemeResource("icons/delete.png"));
		delPerson.addListener((ClickListener) this);
		toolbar.addComponent(delPerson);


		searchField.addStyleName("search big");
		searchField.addListener((TextChangeListener)this);
		toolbar.addComponent(searchField);
		toolbar.setComponentAlignment(searchField, Alignment.MIDDLE_CENTER);

		searchSelect.addStyleName("big");
		Object[] visibleProperties = contactList.getListOfVisibleColumns();
		String[] properNames = contactList.getListOfColumnHeaders();
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

		contactList.addListener((ValueChangeListener) this);
		addComponent(contactList);

		Panel contactDetails = new Panel();
		contactDetails.setCaption("Contact Details");
		contactDetails.addComponent(contactTab);
		contactTab.addTab(contactForm).setCaption("Contact");
		contactTab.addTab(listOfFieldWork).setCaption("Field work done");
		contactTab.addTab(listOfLandOwned).setCaption("Land owned");
		contactTab.addTab(listOfWineBottled).setCaption("Wine Bottled");
		contactTab.addTab(listOfWineBought).setCaption("Wine Bought");
		addComponent(contactDetails);

	}

	public void createContact(){
		if(selection == null || contactForm.getItemDataSource() == null){
			getWindow().showNotification("Save Failed",
					"Please click New first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}else{
			selection = commitFromForm(selection);

			try {
				contactList.createContact(selection);
				getWindow().showNotification("Success!", "Contact "+selection.getFirstName()+" "+selection.getLastName()+" was created successfully", Notification.TYPE_TRAY_NOTIFICATION);
			} catch (RuntimeException e) {
				getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			} finally {
				changeCurrentSelection(selection);
			}

		}

	}

	public void updateContact(){
		logger.info("Trying update Person method");

		if (selection == null || contactForm.getItemDataSource() == null) {
			getWindow().showNotification("Save Failed",
					"Please select somebody first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}

		selection = commitFromForm(selection);

		try {
			contactList.updateContact(selection);
			getWindow().showNotification("Success!", "Contact "+selection.getFirstName()+" "+selection.getLastName()+" was updated successfully", Notification.TYPE_TRAY_NOTIFICATION);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}finally {
			changeCurrentSelection(selection);
		}

	}

	public void deletePerson(){
		try {
			contactList.deleteContact(selection);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}

		getWindow().showNotification("Success!", "Contact "+selection.getFirstName()+" "+selection.getLastName()+" was deleted successfully", Notification.TYPE_TRAY_NOTIFICATION);
		selection = null;
		contactForm.setItemDataSource(null);

	}


	private void changeCurrentSelection(Object select){
		Person selectedPerson = (Person)select;

		selection = selectedPerson;
		BeanItem<Person> personToEdit = convertPersonToBeanItem(selection);
		contactForm.setItemDataSource(personToEdit);
		listOfFieldWork.setContactData(selection);
		listOfLandOwned.setContactData(selection);
		listOfWineBottled.setContactData(selection);
		listOfWineBought.setContactData(selection);
		logger.debug("Current selection: " + selection.getFirstName()+" "+selection.getLastName());
	}

	private BeanItem<Person> convertPersonToBeanItem(Person person){
		return new BeanItem<Person>(person, new String[]{
				"firstName", "lastName", "phoneNumber", "email", "birthDate", "city", "zip", "address"
		});

	}

	private Person commitFromForm(Person person){
		person.setFirstName((String) contactForm.getItemProperty("firstName").getValue());
		person.setLastName((String) contactForm.getItemProperty("lastName").getValue());
		person.setPhoneNumber((String) contactForm.getItemProperty("phoneNumber").getValue());
		person.setEmail((String) contactForm.getItemProperty("email").getValue());
		person.setBirthDate((Date) contactForm.getItemProperty("birthDate").getValue());
		person.setCity((String) contactForm.getItemProperty("city").getValue());
		person.setZip((int) contactForm.getItemProperty("zip").getValue());
		person.setAddress((String) contactForm.getItemProperty("address").getValue());

		return person;
	}

	private void openDeleteConfirmWindow() {
		ConfirmDialog confirmDialog = ConfirmDialog.show(getWindow(),
				"Are you sure you want to <b>DELETE</b> the selected contact?",
				new ConfirmDialog.Listener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -313817438000138156L;

			public void onClose(ConfirmDialog dialog) {
				if (dialog.isConfirmed()) {
					deletePerson();
				}

			}
		});
		confirmDialog.setContentMode(ConfirmDialog.CONTENT_HTML);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		logger.debug("valuechange. " + event);
		Property eventProperty = event.getProperty();
		if (eventProperty == contactList) {
			logger.debug("Value changed to: " + contactList.getValue());
			setNewPersonMode(false);
			changeCurrentSelection(contactList.getValue());
		}else if(eventProperty == searchSelect){
			logger.info("Value changed to: "+searchSelect.getValue());
			SimpleStringFilter filter = null;
			filter = new SimpleStringFilter(searchSelect.getValue(), (String) searchField.getValue(), true, false);
			contactList.addFilter(filter);
		}

	}

	@Override
	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();

		if(source == delPerson){
			if (selection == null || contactForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select something first!",
						Notification.TYPE_WARNING_MESSAGE);
			}else{
				if(selection.isUser()){
					getWindow().showNotification("Warning", "You cannot delete Users, please contact an Admin to it!", Notification.TYPE_WARNING_MESSAGE);
				}else{
					openDeleteConfirmWindow();
				}
			}
		} else if (source == editPerson) {
			if (selection == null || contactForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select somebody first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				contactForm.setReadOnly(false);
			}
		} else if (source == savePerson) {
			if (!contactForm.isValid()) {
				getWindow().showNotification("Update Error",
						"Are all the required fields filled out?",
						Notification.TYPE_WARNING_MESSAGE);
				return;
			}
			contactForm.commit();

			if(newPersonMode){
				createContact();
			}else{
				updateContact();
			}

			contactForm.setReadOnly(true);
			setNewPersonMode(false);
		} else if (source == newPerson) {
			setNewPersonMode(true);
			Person personToAdd = new Person();

			contactForm.setReadOnly(false);
			changeCurrentSelection(personToAdd);
		}

	}

	@Override
	public void textChange(TextChangeEvent event) {
		SimpleStringFilter filter = null;

		filter = new SimpleStringFilter(searchSelect.getValue(), event.getText(), true, false);
		contactList.addFilter(filter);

	}

	public ContactList getContactList() {
		return contactList;
	}

	public void setContactList(ContactList contactList) {
		this.contactList = contactList;
	}

	public boolean isNewPersonMode() {
		return newPersonMode;
	}

	public void setNewPersonMode(boolean newPersonMode) {
		this.newPersonMode = newPersonMode;
		contactForm.setNewPersonMode(newPersonMode);
	}

	public ContactListOfFieldWork getListOfFieldWork() {
		return listOfFieldWork;
	}

	public void setListOfFieldWork(ContactListOfFieldWork listOfFieldWork) {
		this.listOfFieldWork = listOfFieldWork;
	}

	public ContactListOfLandOwned getListOfLandOwned() {
		return listOfLandOwned;
	}

	public void setListOfLandOwned(ContactListOfLandOwned listOfLandOwned) {
		this.listOfLandOwned = listOfLandOwned;
	}

	public ContactListOfWineBottled getListOfWineBottled() {
		return listOfWineBottled;
	}

	public void setListOfWineBottled(ContactListOfWineBottled listOfWineBottled) {
		this.listOfWineBottled = listOfWineBottled;
	}

	public ContactListOfWineBought getListOfWineBought() {
		return listOfWineBought;
	}

	public void setListOfWineBought(ContactListOfWineBought listOfWineBought) {
		this.listOfWineBought = listOfWineBought;
	}
	
	

}
