package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Land;
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

public class ContactListOfLandOwned extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1496962031637930403L;
	@Autowired
	private ContactsService contactService;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private Person worker;
	private static Logger logger = Logger.getLogger(ContactListOfLandOwned.class);
	private Object[] listOfVisibleColumns = new Object[]{"landOff", "landOffId", "size"};
	private String[] listOfColumnHeaders = new String[]{"County Land Office", "Land Office ID", "Size [m2]"};
	private List<Land> lands = new ArrayList<Land>();
	private BeanItemContainer<Land> landContainer = new BeanItemContainer<Land>(Land.class);

	public void initContent(){
		logger.info("InitContent Called");
		
		updateTableContents();
		
		addStyleName("striped");
		
		setContainerDataSource(landContainer);
		setSelectable(true);
		setNullSelectionAllowed(false);
		
		
		setWidth("100%");
		setImmediate(true);
		
		
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);
		
	}
	
	private void updateTableContents() {
		landContainer.removeAllItems();
		lands.clear();
		if(worker != null){
		lands = contactService.getAllLandsOwned(worker);
		landContainer.addAll(lands);
		}
		if(landContainer.size()!=0){
			logger.info("ContactLandContainer has stuff in it. for ex: " +landContainer.firstItemId());
		}

	}
	
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
