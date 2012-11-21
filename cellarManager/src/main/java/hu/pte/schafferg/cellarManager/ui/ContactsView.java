package hu.pte.schafferg.cellarManager.ui;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.ui.components.ContactList;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ContactsView extends VerticalLayout implements ClickListener,
		ValueChangeListener, TextChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3865970177598989349L;
	private HorizontalLayout toolbar = new HorizontalLayout();
	private Button newUser = new Button("New");
	private Button editUser = new Button("Edit");
	private Button saveUser = new Button("Save");
	private Button delUser = new Button("Delete");
	private TextField searchField = new TextField();
	private Select searchSelect = new Select();
	@Autowired
	private ContactList contactList;
	private Person selection = null;
	private Logger logger = Logger.getLogger(ContactsView.class);
	private boolean newPersonMode = false;
	
	public void initContent(){
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
		addComponent(contactDetails);
		
	}
	
	private void changeCurrentSelection(Object select){
		Person selectedPerson = (Person)select;
		
		selection = selectedPerson;
		BeanItem<Person> personToEdit = convertPersonToBeanItem(selection);
		logger.debug("Current selection: " + selection.getFirstName()+" "+selection.getLastName());
	}
	
	private BeanItem<Person> convertPersonToBeanItem(Person person){
		return new BeanItem<Person>(person, new String[]{
				"firstName", "lastName", "phoneNumber", "email", "birthDate", "city", "zip", "address"
		});
		
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
		// TODO Auto-generated method stub

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
	}
	
	
	

}
