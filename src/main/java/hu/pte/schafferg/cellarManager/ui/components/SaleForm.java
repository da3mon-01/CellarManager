package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.model.Wine;
import hu.pte.schafferg.cellarManager.services.ContactsService;
import hu.pte.schafferg.cellarManager.services.WineService;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

/**
 * Form for Sales
 * @author Da3mon
 *
 */
public class SaleForm extends Form {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9060323846436089185L;
	private GridLayout layout = new GridLayout(2,1);
	private boolean newSaleMode = false;
	private static Logger logger = Logger.getLogger(SaleForm.class);
	@Autowired
	private WineService wineService;
	@Autowired
	private ContactsService contactsService;

	/**
	 * Builds the GUI
	 */
	public void initContent(){
		setWriteThrough(false);
		setWidth("500px");
		setImmediate(true);

		layout.setWidth("100%");
		layout.setSpacing(true);
		layout.setMargin(true);
		layout.setColumnExpandRatio(0, 0.5f);
		layout.setColumnExpandRatio(1, 0.5f);
		setLayout(layout);

		setFormFieldFactory(new DefaultFieldFactory(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 9157485324984991827L;

			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				String pid = (String) propertyId;
				Field field = null;

				if(pid.equals("toWho")){
					List<Person> contacts = contactsService.readAll();
					Select soldToSelect = new Select("Sold to:");
					for(Person p : contacts){
						soldToSelect.addItem(p);
					}
					soldToSelect.setNewItemsAllowed(false);
					soldToSelect.setNullSelectionAllowed(false);
					soldToSelect.setRequired(true);
					soldToSelect.setRequiredError("Sold to is required!");
					field = soldToSelect;
				}else if(pid.equals("date")){
					DateField date = new DateField("Sale Date");
					date.setResolution(DateField.RESOLUTION_DAY);
					date.setRequired(true);
					date.setRequiredError("Sale Date is Required");
					date.setDateFormat("yyyy-MMM-dd");
					field = date;
				}else if(pid.equals("what")){
					List<Wine> wines = wineService.readAll();
					Select wineSelect = new Select("Item sold:");
					for(Wine w : wines){
						wineSelect.addItem(w);
					}
					wineSelect.setNewItemsAllowed(false);
					wineSelect.setNullSelectionAllowed(false);
					wineSelect.setRequired(true);
					wineSelect.setRequiredError("Item sold is required!");
					field = wineSelect;
				}else if(pid.equals("numOfBottles")){
					TextField numOfBottles = new TextField("Number of Bottles sold");
					numOfBottles.setRequired(true);
					numOfBottles.setRequiredError("Number of Bottles made is required");
					numOfBottles.setNullRepresentation("");
					field = numOfBottles;
				}else if(pid.equals("wineDocID")){
					TextField numOfBottles = new TextField("Wine Documentation ID");
					numOfBottles.setRequired(true);
					numOfBottles.setRequiredError("Wine Documentation ID is required");
					numOfBottles.setNullRepresentation("");
					field = numOfBottles;
				}

				field.addStyleName("formcaption");
				field.setReadOnly(!newSaleMode);
				field.setWidth("100%");
				return field;
			}
		});
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		logger.info("ReadOnly Called: "+readOnly+" Effect: "+this.isReadOnly());
	}

	public boolean isNewSaleMode() {
		return newSaleMode;
	}

	public void setNewSaleMode(boolean newSaleMode) {
		this.newSaleMode = newSaleMode;
	}

	public WineService getWineService() {
		return wineService;
	}

	public void setWineService(WineService wineService) {
		this.wineService = wineService;
	}

	public ContactsService getContactsService() {
		return contactsService;
	}

	public void setContactsService(ContactsService contactsService) {
		this.contactsService = contactsService;
	}
	
	

}
