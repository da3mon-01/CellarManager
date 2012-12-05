package hu.pte.schafferg.cellarManager.ui.components;

import java.util.List;

import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.services.ContactsService;

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

public class LandsForm extends Form{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7782893366646109746L;
	private GridLayout layout = new GridLayout(2,1);
	private boolean newLandMode = false;
	private static Logger logger = Logger.getLogger(LandsForm.class);
	@Autowired
	private ContactsService contactService;
	
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
			private static final long serialVersionUID = -6190623490449118374L;
			
			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				String pid = (String) propertyId;
				Field field = null;
				
				if(pid.equals("landOffId")){
					TextField landOffid = new TextField("Land Office ID");
					landOffid.setRequired(true);
					landOffid.setRequiredError("Land Office IDs are required");
					landOffid.setNullRepresentation("");
					field = landOffid;
				}else if(pid.equals("landOff")){
					TextField landOff = new TextField("County Land Office");
					landOff.setRequired(true);
					landOff.setRequiredError("Land Office is required");
					landOff.setNullRepresentation("");
					field = landOff;
				}else if(pid.equals("size")){
					TextField size = new TextField("Size [m2]");
					size.setRequired(true);
					size.setRequiredError("Land Size is are required (in m2)");
					size.setNullRepresentation("");
					field = size;
				}else if(pid.equals("owner")){
					List<Person> contacts = contactService.readAll();
					Select owners = new Select("Owner");
					for(Person p : contacts){
						owners.addItem(p);
						owners.setItemCaption(p, p.getFirstName()+" "+p.getLastName());
					}
					owners.setNewItemsAllowed(false);
					owners.setNullSelectionAllowed(false);
					field = owners;
					
				}
				
				field.addStyleName("formcaption");
				field.setReadOnly(!newLandMode);
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

	public boolean isNewLandMode() {
		return newLandMode;
	}

	public void setNewLandMode(boolean newLandMode) {
		this.newLandMode = newLandMode;
	}

	public ContactsService getContactService() {
		return contactService;
	}

	public void setContactService(ContactsService contactService) {
		this.contactService = contactService;
	}
	
	
	

}
