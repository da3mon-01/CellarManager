package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.FieldWork;
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
import com.vaadin.ui.Table;

/**
 * Table for the Fieldworks related to a contact
 * @author Da3mon
 *
 */
public class ContactListOfFieldWork extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4766878731810709791L;
	@Autowired
	private ContactsService contactService;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private Person worker;
	private static Logger logger = Logger.getLogger(ContactListOfFieldWork.class);
	private Object[] listOfVisibleColumns = new Object[]{"when", "onWhat.landOff", "onWhat.landOffId", "work"};
	private String[] listOfColumnHeaders = new String[]{"Date", "County Land Office", "Land Office ID", "Type of work"};
	private List<FieldWork> works = new ArrayList<FieldWork>();
	private BeanItemContainer<FieldWork> workContainer = new BeanItemContainer<FieldWork>(FieldWork.class);
	private Object[] ordering = new Object[]{"when"};
	private boolean[] ascending = new boolean[]{false};
	
	
	/**
	 * Builds the GUI
	 */
	public void initContent(){
		logger.info("InitContent Called");
		
		workContainer.addNestedContainerProperty("onWhat.landOff");
		workContainer.addNestedContainerProperty("onWhat.landOffId");
		updateTableContents();
		
		addStyleName("striped");
		
		setContainerDataSource(workContainer);
		setSelectable(true);
		setNullSelectionAllowed(false);
		
		
		setWidth("100%");
		setImmediate(true);
		
		
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);
		sort(ordering, ascending);
	}
	
	/**
	 * Updates the tables contents.
	 */
	private void updateTableContents() {
		workContainer.removeAllItems();
		works.clear();
		if(worker != null){
		works = contactService.getAllFieldWorkDone(worker);
		workContainer.addAll(works);
		}
		if(workContainer.size()!=0){
			logger.info("ContactWorkContainer has stuff in it. for ex: " +workContainer.firstItemId());
		}

	}
	
	/**
	 * Sets the contact
	 * @param person
	 */
	public void setContactData(Person person){
		this.worker = person;
		logger.info(worker+" selected");
		updateTableContents();
	}
	
	@Override
	public void attach() {
		super.attach();
		updateTableContents();
	}
	
	@Override
	protected String formatPropertyValue(Object rowId, Object colId,
			Property property) {
		if(property.getType() == Date.class){
			return simpleDateFormat.format((Date)property.getValue());			
		}
		return super.formatPropertyValue(rowId, colId, property);
	}

	public SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}

	public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}

	public Person getWorker() {
		return worker;
	}

	public void setWorker(Person worker) {
		this.worker = worker;
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

	public ContactsService getContactService() {
		return contactService;
	}

	public void setContactService(ContactsService contactService) {
		this.contactService = contactService;
	}
	
	

}
