package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Grape;
import hu.pte.schafferg.cellarManager.services.GrapeService;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Item;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

/**
 * Form for GrapeMusts
 * @author Da3mon
 *
 */
public class GrapeMustForm extends Form {

	/**
	 * 
	 */
	private static final long serialVersionUID = 578371929885151171L;
	private GridLayout layout = new GridLayout(2,1);
	private boolean newGrapeMustMode = false;
	private static Logger logger = Logger.getLogger(GrapeForm.class);
	@Autowired
	private GrapeService grapeService;

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

				if(pid.equals("quantityLostAfterRacking")){
					TextField quantityAfterRacking = new TextField("Quantity after racking");
					quantityAfterRacking.setRequired(true);
					quantityAfterRacking.setRequiredError("Quantity after racking is are required");
					quantityAfterRacking.setNullRepresentation("");
					field = quantityAfterRacking;
				}else if(pid.equals("quantityAfterHarvest")){
					TextField quantityAfterHarvest = new TextField("Quantity after harvest");
					quantityAfterHarvest.setRequired(true);
					quantityAfterHarvest.setRequiredError("Quantity after harvest is required");
					quantityAfterHarvest.setNullRepresentation("");
					field = quantityAfterHarvest;
				}else if(pid.equals("mustDegree")){
					TextField mustDegree = new TextField("Must Degree[°]");
					mustDegree.setRequired(true);
					mustDegree.setRequiredError("Must Degree is required");
					mustDegree.setNullRepresentation("");
					field = mustDegree;
				}else if(pid.equals("enrichmentDegree")){
					TextField enrichmentDegree = new TextField("Enrichment Degree[°]");
					enrichmentDegree.setRequired(true);
					enrichmentDegree.setRequiredError("Enrichment Degree is required");
					enrichmentDegree.setNullRepresentation("");
					field = enrichmentDegree;
				}else if(pid.equals("madeFrom")){
					List<Grape> grapes = grapeService.readAll();
					Select grapeSelect = new Select("Made From:");
					for(Grape g : grapes){
						grapeSelect.addItem(g);
					}
					grapeSelect.setNewItemsAllowed(false);
					grapeSelect.setNullSelectionAllowed(false);
					grapeSelect.setRequired(true);
					grapeSelect.setRequiredError("Made from Grape is required!");
					field = grapeSelect;
				}else if(pid.equals("enriched")){
					CheckBox enriched = new CheckBox("Enriched?");
					field = enriched;
				}else if(pid.equals("sweetened")){
					CheckBox sweetened = new CheckBox("Sweetened?");
					field = sweetened;
				}

				field.addStyleName("formcaption");
				field.setReadOnly(!newGrapeMustMode);
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

	public boolean isNewGrapeMustMode() {
		return newGrapeMustMode;
	}

	public void setNewGrapeMustMode(boolean newGrapeMustMode) {
		this.newGrapeMustMode = newGrapeMustMode;
	}
	
	
	

}
