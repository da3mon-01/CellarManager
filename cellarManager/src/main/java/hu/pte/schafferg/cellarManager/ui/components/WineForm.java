package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.services.ContactsService;
import hu.pte.schafferg.cellarManager.services.GrapeMustService;
import hu.pte.schafferg.cellarManager.util.WineSweetness;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

/**
 * Form for all Wines
 * @author Da3mon
 *
 */
public class WineForm extends Form {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6833668679525862828L;
	private GridLayout layout = new GridLayout(2,1);
	private boolean newWineMode = false;
	private static Logger logger = Logger.getLogger(GrapeForm.class);
	@Autowired
	private GrapeMustService mustService;
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

				if(pid.equals("obiNumber")){
					TextField obiNumber = new TextField("OBI number");
					obiNumber.setRequired(true);
					obiNumber.setRequiredError("OBI number is are required");
					obiNumber.setNullRepresentation("");
					field = obiNumber;
				}else if(pid.equals("type")){
					TextField type = new TextField("Type");
					type.setRequired(true);
					type.setRequiredError("Type is required");
					type.setNullRepresentation("");
					field = type;
				}else if(pid.equals("alcoholDegree")){
					TextField alcoholDegree = new TextField("Alcohol Degree[°]");
					alcoholDegree.setRequired(true);
					alcoholDegree.setRequiredError("Alcohol Degree is required");
					alcoholDegree.setNullRepresentation("");
					field = alcoholDegree;
				}else if(pid.equals("sweetness")){
					Select sweetness = new Select("Sweetness");
					WineSweetness[] listOfsweetness = WineSweetness.values();
					for(int i=0; i<listOfsweetness.length; i++){
						sweetness.addItem(listOfsweetness[i]);
					}
					sweetness.setNewItemsAllowed(false);
					sweetness.setNullSelectionAllowed(false);
					field = sweetness;
				}else if(pid.equals("madeFrom")){
					List<GrapeMust> musts = mustService.readAll();
					Select mustSelect = new Select("Made From:");
					for(GrapeMust g : musts){
						mustSelect.addItem(g);
					}
					mustSelect.setNewItemsAllowed(false);
					mustSelect.setNullSelectionAllowed(false);
					mustSelect.setRequired(true);
					mustSelect.setRequiredError("Made from must is required!");
					field = mustSelect;
				}else if(pid.equals("bottler")){
					List<Person> contacts = contactsService.readAll();
					Select bottlerSelect = new Select("Bottled by:");
					for(Person p : contacts){
						bottlerSelect.addItem(p);
					}
					bottlerSelect.setNewItemsAllowed(false);
					bottlerSelect.setNullSelectionAllowed(false);
					bottlerSelect.setRequired(true);
					bottlerSelect.setRequiredError("Bottler is required!");
					field = bottlerSelect;
				}else if(pid.equals("numOfBottles")){
					TextField numOfBottles = new TextField("Number of Bottles made");
					numOfBottles.setRequired(true);
					numOfBottles.setRequiredError("Number of Bottles made is required");
					numOfBottles.setNullRepresentation("");
					field = numOfBottles;
				}

				field.addStyleName("formcaption");
				field.setReadOnly(!newWineMode);
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

	public boolean isNewWineMode() {
		return newWineMode;
	}

	public void setNewWineMode(boolean newWineMode) {
		this.newWineMode = newWineMode;
	}

	public GrapeMustService getWineService() {
		return mustService;
	}

	public void setWineService(GrapeMustService wineService) {
		this.mustService = wineService;
	}

	public ContactsService getContactsService() {
		return contactsService;
	}

	public void setContactsService(ContactsService contactsService) {
		this.contactsService = contactsService;
	}
	
	


}
