package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.services.GrapeMustService;

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
 * Form for analytics
 * @author Da3mon
 *
 */
public class AnalyticForm extends Form {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5958955289596481242L;
	private GridLayout layout = new GridLayout(2,1);
	private boolean newAnalyticMode = false;
	private static Logger logger = Logger.getLogger(AnalyticForm.class);
	@Autowired
	private GrapeMustService grapeMustService;
	
	/**
	 * Build the GUI
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
			private static final long serialVersionUID = 2777011732674584778L;

			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				String pid = (String) propertyId;
				Field field = null;
				
				if(pid.equals("must")){
					List<GrapeMust> musts = grapeMustService.readAll();
					Select mustSelect = new Select("Tested Must");
					for(GrapeMust g : musts){
						mustSelect.addItem(g);
					}
					mustSelect.setNewItemsAllowed(false);
					mustSelect.setNullSelectionAllowed(false);
					field = mustSelect;
				}else if(pid.equals("when")){
					DateField date = new DateField("Date");
					date.setResolution(DateField.RESOLUTION_DAY);
					date.setRequired(true);
					date.setRequiredError("Date is Required");
					date.setDateFormat("yyyy-MMM-dd");
					field = date;
				}else if(pid.equals("sulfur")){
					TextField sulfur = new TextField("Sulfur [mg]");
					sulfur.setRequired(true);
					sulfur.setRequiredError("Sulfur is Required");
					sulfur.setNullRepresentation("");
					field = sulfur;
				}else if(pid.equals("sugar")){
					TextField sugar = new TextField("Sugar [mg]");
					sugar.setRequired(true);
					sugar.setRequiredError("Sugar is Required");
					sugar.setNullRepresentation("");
					field = sugar;
				}else if(pid.equals("iron")){
					TextField iron = new TextField("Iron [mg]");
					iron.setRequired(true);
					iron.setRequiredError("Iron is Required");
					iron.setNullRepresentation("");
					field = iron;
				}else if(pid.equals("extract")){
					TextField extract = new TextField("Extract [mg]");
					extract.setRequired(true);
					extract.setRequiredError("Extract is Required");
					extract.setNullRepresentation("");
					field = extract;
				}
				field.addStyleName("formcaption");
				field.setReadOnly(!newAnalyticMode);
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

	public boolean isNewAnalyticMode() {
		return newAnalyticMode;
	}

	public void setNewAnalyticMode(boolean newAnalyticMode) {
		this.newAnalyticMode = newAnalyticMode;
	}

	public GrapeMustService getGrapeMustService() {
		return grapeMustService;
	}

	public void setGrapeMustService(GrapeMustService grapeMustService) {
		this.grapeMustService = grapeMustService;
	}
	
	


}
