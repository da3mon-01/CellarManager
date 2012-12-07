package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.model.Sale;
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

public class ContactListOfWineBought extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9023737030685711233L;
	@Autowired
	private ContactsService contactService;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private Person buyer;
	private static Logger logger = Logger.getLogger(ContactListOfWineBought.class);
	private Object[] listOfVisibleColumns = new Object[]{"date", "what", "numOfBottles", "wineDocID"};
	private String[] listOfColumnHeaders = new String[]{"Sale Date", "Item sold", "Number of bottles sold", "Wine Documentation ID"};
	private List<Sale> sales = new ArrayList<Sale>();
	private BeanItemContainer<Sale> saleContainer = new BeanItemContainer<Sale>(Sale.class);
	private Object[] ordering = new Object[]{"date"};
	private boolean[] ascending = new boolean[]{false};
	
	public void initContent(){
		logger.info("InitContent Called");
		
		updateTableContents();
		
		addStyleName("striped");
		
		setContainerDataSource(saleContainer);
		setSelectable(true);
		setNullSelectionAllowed(false);
		
		
		setWidth("100%");
		setImmediate(true);
		
		
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);
		
		sort(ordering, ascending);
		
	}
	
	private void updateTableContents() {
		saleContainer.removeAllItems();
		sales.clear();
		if(buyer != null){
		sales = contactService.getAllSales(buyer);
		saleContainer.addAll(sales);
		}
		if(saleContainer.size()!=0){
			logger.info("ContactWineContainer has stuff in it. for ex: " +saleContainer.firstItemId());
		}

	}
	
	public void setContactData(Person person){
		this.buyer = person;
		logger.info(buyer+" selected");
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

