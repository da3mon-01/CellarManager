package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.services.LandService;

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

public class GrapeForm extends Form {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3693913819387078177L;
	private GridLayout layout = new GridLayout(2,1);
	private boolean newGrapeMode = false;
	private static Logger logger = Logger.getLogger(GrapeForm.class);
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
			private static final long serialVersionUID = 4078435036111748460L;

			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				String pid = (String) propertyId;
				Field field = null;
				
				if(pid.equals("type")){
					TextField type = new TextField("Type");
					type.setRequired(true);
					type.setRequiredError("Type is are required");
					type.setNullRepresentation("");
					field = type;
				}else if(pid.equals("quantity")){
					TextField quantity = new TextField("Quantity");
					quantity.setRequired(true);
					quantity.setRequiredError("Quantity is required");
					quantity.setNullRepresentation("");
					field = quantity;
				}else if(pid.equals("planted")){
					DateField date = new DateField("Date Planted");
					date.setRequired(true);
					date.setRequiredError("Date Planted is are required");
					date.setDateFormat("yyyy-MMM-dd");
					date.setResolution(DateField.RESOLUTION_DAY);
					field = date;
				}else if(pid.equals("plantedOn")){
					List<Land> lands = landService.readAll();
					Select landSelect = new Select("Planted On:");
					for(Land l : lands){
						landSelect.addItem(l);
						landSelect.setItemCaption(l, l.getLandOff()+"/"+l.getLandOffId());
					}
					landSelect.setNewItemsAllowed(false);
					landSelect.setNullSelectionAllowed(false);
					landSelect.setRequired(true);
					landSelect.setRequiredError("Planted On Land is required");
					field = landSelect;
					
				}
				
				field.addStyleName("formcaption");
				field.setReadOnly(!newGrapeMode);
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

	public boolean isNewGrapeMode() {
		return newGrapeMode;
	}

	public void setNewGrapeMode(boolean newGrapeMode) {
		this.newGrapeMode = newGrapeMode;
	}

	public LandService getLandService() {
		return landService;
	}

	public void setLandService(LandService landService) {
		this.landService = landService;
	}
	
	

}
