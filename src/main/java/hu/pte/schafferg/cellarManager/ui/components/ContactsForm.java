package hu.pte.schafferg.cellarManager.ui.components;

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
import com.vaadin.ui.TextField;

/**
 * Form for Contacts
 * @author Da3mon
 *
 */
public class ContactsForm extends Form {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8228376874034817006L;
	private GridLayout layout = new GridLayout(2,1);
	private String zipRegExp;
	private String zipRegExpError;
	private String phoneRegExp;
	private String phoneRegExpError;
	private boolean newPersonMode = false;
	

	private static Logger logger = Logger.getLogger(ContactsForm.class);
	
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
			private static final long serialVersionUID = -6190623490449118374L;
			
			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				String pid = (String) propertyId;
				Field field = null;
				if(pid.equals("firstName")){
					TextField firstname = new TextField("First Name");
					firstname.setRequired(true);
					firstname.setRequiredError("First Name is Required");
					firstname.setNullRepresentation("");
					field = firstname;
				}else if(pid.equals("lastName")){
					TextField lastname = new TextField("Last Name");
					lastname.setRequired(true);
					lastname.setRequiredError("Last Name is Required");
					lastname.setNullRepresentation("");
					field = lastname;
				}else if(pid.equals("city")){
					TextField city =  new TextField("City");
					city.setRequired(true);
					city.setRequiredError("City is Required");
					city.setNullRepresentation("");
					field = city;
				}else if(pid.equals("address")){
					TextField address = new TextField("Address");
					address.setRequired(true);
					address.setRequiredError("Address is Required");
					address.setNullRepresentation("");
					field = address;
				}else if(pid.equals("zip")){
					TextField zip = new TextField("Zip");
					zip.setRequired(true);
					zip.setRequiredError("ZIP code is required");
					zip.addValidator(new RegexpValidator(zipRegExp, zipRegExpError));
					zip.setNullRepresentation("");
					field = zip;
				}else if(pid.equals("birthDate")){
					DateField bdate = new DateField("Birth Date");
					bdate.setRequired(true);
					bdate.setRequiredError("Birth Date is Required");
					bdate.setDateFormat("yyyy-MMM-dd");
					bdate.setResolution(DateField.RESOLUTION_DAY);
					field = bdate;
				}else if(pid.equals("email")){
					TextField email = new TextField("E-mail");
					email.addValidator(new EmailValidator("Email is username@host.domain"));
					email.setNullRepresentation("");
					field = email;
				}else if(pid.equals("phoneNumber")){
					TextField phone = new TextField("Phone Number");
					phone.addValidator(new RegexpValidator(phoneRegExp, phoneRegExpError));
					phone.setNullRepresentation("");
					field = phone;
				}
				field.addStyleName("formcaption");
				field.setReadOnly(!newPersonMode);
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
	
	public boolean isNewPersonMode() {
		return newPersonMode;
	}

	public void setNewPersonMode(boolean newPersonMode) {
		this.newPersonMode = newPersonMode;
	}

}
