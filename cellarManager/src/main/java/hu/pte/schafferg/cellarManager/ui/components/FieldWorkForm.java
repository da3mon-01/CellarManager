package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.services.ContactsService;
import hu.pte.schafferg.cellarManager.services.FieldWorkService;
import hu.pte.schafferg.cellarManager.services.LandService;
import hu.pte.schafferg.cellarManager.util.FieldWorkType;

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

public class FieldWorkForm extends Form {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2870793138757362983L;
	private GridLayout layout = new GridLayout(2,1);
	private boolean newFieldWorkMode = false;
	private static Logger logger = Logger.getLogger(FieldWorkForm.class);
	@Autowired
	private FieldWorkService fieldWorkService;
	@Autowired
	private ContactsService contactService;
	@Autowired
	private LandService landService;
	
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
			private static final long serialVersionUID = 2777011732674584778L;

			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				String pid = (String) propertyId;
				Field field = null;
				
				if(pid.equals("onWhat")){
					List<Land> lands = landService.readAll();
					Select landSelect = new Select("Worked Land");
					for(Land l : lands){
						landSelect.addItem(l);
						landSelect.setItemCaption(l, l.getLandOff()+"/"+l.getLandOffId());
					}
					landSelect.setNewItemsAllowed(false);
					landSelect.setNullSelectionAllowed(false);
					field = landSelect;
				}else if(pid.equals("when")){
					DateField date = new DateField("Date");
					date.setResolution(DateField.RESOLUTION_DAY);
					date.setRequired(true);
					date.setRequiredError("Date is Required");
					date.setDateFormat("yyyy-MMM-dd");
					field = date;
				}else if(pid.equals("who")){
					List<Person> contacts = contactService.readAll();
					Select owners = new Select("Worker");
					for(Person p : contacts){
						owners.addItem(p);
						owners.setItemCaption(p, p.getFirstName()+" "+p.getLastName());
					}
					owners.setNewItemsAllowed(false);
					owners.setNullSelectionAllowed(false);
					field = owners;
				}else if(pid.equals("work")){
					Select work = new Select("Type of Work");
					FieldWorkType[] fwTypes = FieldWorkType.values();
					for(int i=0; i<fwTypes.length; i++){
						work.addItem(fwTypes[i]);
					}
					work.setNewItemsAllowed(false);
					work.setNullSelectionAllowed(false);
					field = work;
				}
				
				field.addStyleName("formcaption");
				field.setReadOnly(!newFieldWorkMode);
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

	public boolean isNewFieldWorkMode() {
		return newFieldWorkMode;
	}

	public void setNewFieldWorkMode(boolean newFieldWorkMode) {
		this.newFieldWorkMode = newFieldWorkMode;
	}

	public FieldWorkService getFieldWorkService() {
		return fieldWorkService;
	}

	public void setFieldWorkService(FieldWorkService fieldWorkService) {
		this.fieldWorkService = fieldWorkService;
	}

	public ContactsService getContactService() {
		return contactService;
	}

	public void setContactService(ContactsService contactService) {
		this.contactService = contactService;
	}

	public LandService getLandService() {
		return landService;
	}

	public void setLandService(LandService landService) {
		this.landService = landService;
	}
	
	

}
