package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.services.ContactsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;

/**
 * Table for contacts
 * @author Da3mon
 *
 */
public class ContactList extends Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6031762694363786553L;
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private SimpleDateFormat sdf;
	private static Logger logger = Logger.getLogger(ContactList.class);
	private Object[] listOfVisibleColumns = new Object[]{"firstName", "lastName", "phoneNumber", "email", "birthDate", "city", "zip", "address", "isUser"};
	private String[] listOfColumnHeaders = new String[]{"First Name", "Last Name", "Phone Number", "E-mail", "Birth date", "City", "Zip", "Address", "User?"};
	private List<Person> contacts = new ArrayList<Person>();
	private BeanItemContainer<Person> contactsContainer = new BeanItemContainer<Person>(Person.class);
	
	/**
	 * Builds the GUI
	 */
	public void initContent(){
		logger.info("InitContent Called");
		
		updateTableContents();
		
		addStyleName("striped");
		
		setContainerDataSource(contactsContainer);
		setSelectable(true);
		setNullSelectionAllowed(false);
		
		
		setWidth("100%");
		setImmediate(true);
		
		addGeneratedColumn("email", new ColumnGenerator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1703245259965864683L;

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				Person item = (Person)itemId;
				Link link = new Link();
				link.setResource(new ExternalResource("mailto:"+item.getEmail()));
				link.setCaption(item.getEmail());
				return link;
			}
		});
		
		addGeneratedColumn("isUser", new ColumnGenerator() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -8636014969368770784L;

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				Person item = (Person)itemId;
				if(item.isUser()){
					Embedded e = new Embedded();
					e.setHeight("15px");
					e.setSource(new ThemeResource("icons/pipa.png"));
					return e;
				}
				return null;
			}
		});
		
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);
		
	}
	
	/**
	 * Calls the service to create p
	 * @param p
	 * @throws RuntimeException
	 */
	public void createContact(Person p) throws RuntimeException{
		try {
			contactsService.create(p);
		} catch (RuntimeException e) {
			throw e;
		}finally {
			updateTableContents();
		}
	}
	
	/**
	 * Calls the service to update p
	 * @param p
	 * @throws RuntimeException
	 */
	public void updateContact(Person p) throws RuntimeException{
		try {
			contactsService.update(p);
		} catch (Exception e) {
			throw e;
		}finally{
			updateTableContents();
		}
	}
	
	/**
	 * Calls the service to delete p
	 * @param p
	 * @throws RuntimeException
	 */
	public void deleteContact(Person p) throws RuntimeException {
		try {
			contactsService.delete(p);
		} catch (Exception e) {
			throw e;
		} finally {
			updateTableContents();
		}
	}

	/**
	 * Updates the tables contents.
	 */
	private void updateTableContents() {
		contacts.clear();
		contactsContainer.removeAllItems();
		contacts = contactsService.readAll();
		
		contactsContainer.addAll(contacts);
		if(contactsContainer.size()!=0){
			logger.info("Contacts container has items in it.");
		}
		
	}
	
	/**
	 * Adds a filter to the table
	 * @param filter
	 */
	public void addFilter(SimpleStringFilter filter){
		Filterable f = (Filterable)getContainerDataSource();
		if(filter != null){
			f.removeAllContainerFilters();
		}
		
		f.addContainerFilter(filter);
		logger.info("Filter added: "+filter.getFilterString()+" in "+filter.getPropertyId());
		
	}
	
	@Override
	protected String formatPropertyValue(Object rowId, Object colId,
			Property property) {
		if(property.getType() == Date.class){
			return sdf.format((Date)property.getValue());			
		}
		return super.formatPropertyValue(rowId, colId, property);
	}

	@Override
	public void attach() {
		super.attach();
		updateTableContents();
	}

	public ContactsService getContactsService() {
		return contactsService;
	}

	public void setContactsService(ContactsService contactsService) {
		this.contactsService = contactsService;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public Object[] getListOfVisibleColumns() {
		return listOfVisibleColumns;
	}

	public void setListOfVisibleColumns(Object[] listOfVisibleColumns) {
		this.listOfVisibleColumns = listOfVisibleColumns;
	}

	public String[] getListOfColumnHeaders() {
		return listOfColumnHeaders;
	}

	public void setListOfColumnHeaders(String[] listOfColumnHeaders) {
		this.listOfColumnHeaders = listOfColumnHeaders;
	}
	
	
	
	
	
}
