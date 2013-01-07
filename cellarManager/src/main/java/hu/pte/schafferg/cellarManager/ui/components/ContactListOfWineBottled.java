package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.model.Wine;
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
 * Table for the Wines bottled by a contact
 * @author Da3mon
 *
 */
public class ContactListOfWineBottled extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8626316221549253049L;
	@Autowired
	private ContactsService contactService;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private Person worker;
	private static Logger logger = Logger.getLogger(ContactListOfWineBottled.class);
	private Object[] listOfVisibleColumns = new Object[]{"obiNumber", "madeFrom", "alcoholDegree", "sweetness", "numOfBottles"};
	private String[] listOfColumnHeaders = new String[]{"OBI Number", "Made from", "Alcohol Degree", "Sweetness",  "Number of Bottles"};
	private List<Wine> wines = new ArrayList<Wine>();
	private BeanItemContainer<Wine> wineContainer = new BeanItemContainer<Wine>(Wine.class);

	/**
	 * Builds the GUI
	 */
	public void initContent(){
		logger.info("InitContent Called");
		
		updateTableContents();
		
		addStyleName("striped");
		
		setContainerDataSource(wineContainer);
		setSelectable(true);
		setNullSelectionAllowed(false);
		
		
		setWidth("100%");
		setImmediate(true);
		
		
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);
		
	}
	
	/**
	 * Updates the tables contents.
	 */
	private void updateTableContents() {
		wineContainer.removeAllItems();
		wines.clear();
		if(worker != null){
		wines = contactService.getAllWinesBottled(worker);
		wineContainer.addAll(wines);
		}
		if(wineContainer.size()!=0){
			logger.info("ContactWineContainer has stuff in it. for ex: " +wineContainer.firstItemId());
		}

	}
	
	/**
	 * Sets the contact.
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


}
