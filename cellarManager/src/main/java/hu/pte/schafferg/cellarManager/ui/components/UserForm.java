package hu.pte.schafferg.cellarManager.ui.components;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.vaadin.data.Item;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

public class UserForm extends Form {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4048617237308959744L;
	private GridLayout layout = new GridLayout(2, 1);
	private String zipRegExp;
	private String zipRegExpError;
	private String phoneRegExp;
	private String phoneRegExpError;
	private boolean newUserMode = false;
	private static Logger logger = Logger.getLogger(UserForm.class);
	
	
	public void initContent(){
		setWriteThrough(false);
		setWidth("500px");
		setImmediate(true);
		
		layout.setWidth("100%");
		layout.setSpacing(true);
		layout.setColumnExpandRatio(0, 0.5f);
		layout.setColumnExpandRatio(1, 0.5f);
		setLayout(layout);
		
		
		setFormFieldFactory(new DefaultFieldFactory(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 7732868404505350970L;
			

			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				String pid = (String) propertyId;
				Field field = null;
				if(pid.equals("username")){
					TextField username = new TextField("Username");
					username.setRequired(true);
					username.setRequiredError("Username is required!");
					field = username;
				}else if(pid.equals("personFirstName")){
					TextField firstname = new TextField("First Name");
					firstname.setRequired(true);
					firstname.setRequiredError("First Name is Required");
					field = firstname;
				}else if(pid.equals("personLastName")){
					TextField lastname = new TextField("Last Name");
					lastname.setRequired(true);
					lastname.setRequiredError("Last Name is Required");
					field = lastname;
				}else if(pid.equals("personCity")){
					TextField city =  new TextField("City");
					city.setRequired(true);
					city.setRequiredError("City is Required");
					field = city;
				}else if(pid.equals("personAddress")){
					TextField address = new TextField("Address");
					address.setRequired(true);
					address.setRequiredError("Address is Required");
					field = address;
				}else if(pid.equals("personZip")){
					TextField zip = new TextField("Zip");
					zip.setRequired(true);
					zip.setRequiredError("ZIP code is required");
					zip.addValidator(new RegexpValidator(zipRegExp, zipRegExpError));
					field = zip;
				}else if(pid.equals("personBirthDate")){
					DateField bdate = new DateField("Birth Date");
					bdate.setRequired(true);
					bdate.setRequiredError("Birth Date is Required");
					bdate.setDateFormat("yyyy-MMM-dd");
					bdate.setResolution(DateField.RESOLUTION_DAY);
					field = bdate;
				}else if(pid.equals("personEmail")){
					TextField email = new TextField("E-mail");
					email.addValidator(new EmailValidator("Email is username@host.domain"));
					field = email;
				}else if(pid.equals("personPhoneNumber")){
					TextField phone = new TextField("Phone Number");
					phone.addValidator(new RegexpValidator(phoneRegExp, phoneRegExpError));
					field = phone;
				}else if(pid.equals("roleRole")){
					Select roleSelect = new Select("Role");
					roleSelect.addItem(1);
					roleSelect.setItemCaption(1, "Admin");
					roleSelect.addItem(2);
					roleSelect.setItemCaption(2, "User");
					roleSelect.setNullSelectionAllowed(false);
					field = roleSelect;
				}
				field.setReadOnly(!newUserMode);
				field.setWidth("100%");
				return field;
			}
			
		});
			
	}
	
	


	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		logger.info("ReadOnly Called: "+readOnly);
	}




	public String getZipRegExp() {
		return zipRegExp;
	}



	public void setZipRegExp(String zipRegExp) {
		this.zipRegExp = zipRegExp;
	}



	public String getZipRegExpError() {
		return zipRegExpError;
	}



	public void setZipRegExpError(String zipRegExpError) {
		this.zipRegExpError = zipRegExpError;
	}



	public String getPhoneRegExp() {
		return phoneRegExp;
	}



	public void setPhoneRegExp(String phoneRegExp) {
		this.phoneRegExp = phoneRegExp;
	}



	public String getPhoneRegExpError() {
		return phoneRegExpError;
	}



	public void setPhoneRegExpError(String phoneRegExpError) {
		this.phoneRegExpError = phoneRegExpError;
	}

	public boolean isNewUserMode() {
		return newUserMode;
	}

	public void setNewUserMode(boolean newUserMode) {
		this.newUserMode = newUserMode;
	}




	public void checkReadOnly() {
		for(Iterator<Component> iterator = getLayout().getComponentIterator();iterator.hasNext();){
			Component c = iterator.next();
			logger.info(c.getCaption()+" is currently Read Only: "+c.isReadOnly());
		}
	}
	
	
	

}
