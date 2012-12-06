package hu.pte.schafferg.cellarManager.ui;

import hu.pte.schafferg.cellarManager.model.Grape;
import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.ui.components.GrapeForm;
import hu.pte.schafferg.cellarManager.ui.components.GrapeList;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class GrapeView extends VerticalLayout implements ClickListener,
		TextChangeListener, ValueChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5538418608469594388L;
	private HorizontalLayout toolbar = new HorizontalLayout();
	private Button newGrape = new Button("New");
	private Button editGrape = new Button("Edit");
	private Button saveGrape= new Button("Save");
	private Button delGrape = new Button("Delete");
	private TextField searchField = new TextField();
	private Select searchSelect = new Select();
	@Autowired
	private GrapeList grapesList;
	@Autowired
	private GrapeForm grapesForm;
	private Grape selection = null;
	private Logger logger = Logger.getLogger(LandsView.class);
	private boolean newGrapeMode = false;
	
	public void initContent(){
		setMargin(true);
		setSpacing(true);
		toolbar.setSpacing(true);

		newGrape.addStyleName("big");
		newGrape.setIcon(new ThemeResource("icons/add.png"));
		newGrape.addListener((ClickListener) this);
		toolbar.addComponent(newGrape);

		editGrape.addStyleName("big");
		editGrape.setIcon(new ThemeResource("icons/edit.png"));
		editGrape.addListener((ClickListener) this);
		toolbar.addComponent(editGrape);

		saveGrape.addStyleName("big");
		saveGrape.setIcon(new ThemeResource("icons/save.png"));
		saveGrape.addListener((ClickListener) this);
		toolbar.addComponent(saveGrape);

		delGrape.addStyleName("big");
		delGrape.setIcon(new ThemeResource("icons/delete.png"));
		delGrape.addListener((ClickListener) this);
		toolbar.addComponent(delGrape);
		
		
		searchField.addStyleName("search big");
		searchField.addListener((TextChangeListener)this);
		toolbar.addComponent(searchField);
		toolbar.setComponentAlignment(searchField, Alignment.MIDDLE_CENTER);
		
		searchSelect.addStyleName("big");
		Object[] visibleProperties = grapesList.getListOfVisibleColumns();
		String[] properNames = grapesList.getListOfColumnHeaders();
		for(int i=0; i < visibleProperties.length; i++){
			searchSelect.addItem(visibleProperties[i]);
			searchSelect.setItemCaption(visibleProperties[i], properNames[i]);
		}
		searchSelect.setNewItemsAllowed(false);
		searchSelect.setNullSelectionAllowed(false);
		searchSelect.setImmediate(true);
		searchSelect.select(visibleProperties[0]);
		searchSelect.addListener((ValueChangeListener)this);
		toolbar.addComponent(searchSelect);
		toolbar.setComponentAlignment(searchSelect, Alignment.MIDDLE_CENTER);

		addComponent(toolbar);

		grapesList.addListener((ValueChangeListener) this);
		addComponent(grapesList);
		
		Panel landsDetails = new Panel();
		landsDetails.setCaption("Grape Details");
		landsDetails.addComponent(grapesForm);
		addComponent(landsDetails);
	}
	
	public void createGrape(){
		if(selection == null || grapesForm.getItemDataSource() == null){
			getWindow().showNotification("Save Failed",
					"Please click New first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}else{
			selection = commitFromForm(selection);
			
			try {
				grapesList.create(selection);
				getWindow().showNotification("Success!", "Grape "+selection.getType()+"@"+selection.getPlantedOn().getLandOff()+"/"+selection.getPlantedOn().getLandOffId()+"was created successfully", Notification.TYPE_TRAY_NOTIFICATION);
			} catch (RuntimeException e) {
				getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			} finally {
				changeCurrentSelection(selection);
			}
		
		}
		
	}
	
	public void updateGrape(){
		logger.info("Trying update Person method");

		if (selection == null || grapesForm.getItemDataSource() == null) {
			getWindow().showNotification("Save Failed",
					"Please select somebody first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}

		selection = commitFromForm(selection);
		
		try {
			grapesList.update(selection);
			getWindow().showNotification("Success!", "Grape "+selection.getType()+"@"+selection.getPlantedOn().getLandOff()+"/"+selection.getPlantedOn().getLandOffId()+" was updated successfully", Notification.TYPE_TRAY_NOTIFICATION);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}finally {
			changeCurrentSelection(selection);
		}
		
	}
	
	public void deleteGrape(){
		try {
			grapesList.delete(selection);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		
		getWindow().showNotification("Grape "+selection.getType()+"@"+selection.getPlantedOn().getLandOff()+"/"+selection.getPlantedOn().getLandOffId()+" was deleted successfully", Notification.TYPE_TRAY_NOTIFICATION);
		selection = null;
		grapesForm.setItemDataSource(null);
		
	}
	
	private void changeCurrentSelection(Object select){
		Grape selectedGrape = (Grape)select;
		
		selection = selectedGrape;
		BeanItem<Grape> grapeToEdit = convertGrapeToBeanItem(selection);
		grapesForm.setItemDataSource(grapeToEdit);
		logger.debug("Current selection: " + selection);
	}
	
	private BeanItem<Grape> convertGrapeToBeanItem(Grape grape){
		return new BeanItem<Grape>(grape, new String[]{"type","plantedOn", "planted", "quantity"});
	}
	
	private Grape commitFromForm(Grape grape){
		grape.setType((String) grapesForm.getItemProperty("type").getValue());
		grape.setPlantedOn((Land) grapesForm.getItemProperty("plantedOn").getValue());
		grape.setPlanted((Date) grapesForm.getItemProperty("planted").getValue());
		grape.setQuantity((int) grapesForm.getItemProperty("quantity").getValue());
		
		return grape;
	}
	
	private void openDeleteConfirmWindow() {
		ConfirmDialog confirmDialog = ConfirmDialog.show(getWindow(),
				"Are you sure you want to <b>DELETE</b> the selected Grape?",
				new ConfirmDialog.Listener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -313817438000138156L;

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							deleteGrape();
						}

					}
				});
		confirmDialog.setContentMode(ConfirmDialog.CONTENT_HTML);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		logger.debug("valuechange. " + event);
		Property eventProperty = event.getProperty();
		if (eventProperty == grapesList) {
			logger.debug("Value changed to: " + grapesList.getValue());
			setNewGrapeMode(false);
			changeCurrentSelection(grapesList.getValue());
		}else if(eventProperty == searchSelect){
			logger.info("Value changed to: "+searchSelect.getValue());
			SimpleStringFilter filter = null;
			filter = new SimpleStringFilter(searchSelect.getValue(), (String) searchField.getValue(), true, false);
			grapesList.addFilter(filter);
		}

	}

	@Override
	public void textChange(TextChangeEvent event) {
		SimpleStringFilter filter = null;
		
		filter = new SimpleStringFilter(searchSelect.getValue(), event.getText(), true, false);
		grapesList.addFilter(filter);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		
		if(source == delGrape){
			if (selection == null || grapesForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select something first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				openDeleteConfirmWindow();
			}
		} else if (source == editGrape) {
			if (selection == null || grapesForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select something first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				grapesForm.setReadOnly(false);
			}
		} else if (source == saveGrape) {
			if (!grapesForm.isValid()) {
				getWindow().showNotification("Update Error",
						"Are all the required fields filled out?",
						Notification.TYPE_WARNING_MESSAGE);
				return;
			}
			grapesForm.commit();

			if(newGrapeMode){
				createGrape();
			}else{
				updateGrape();
			}

			grapesForm.setReadOnly(true);
			setNewGrapeMode(false);
		} else if (source == newGrape) {
			setNewGrapeMode(true);
			Grape grapeToAdd = new Grape();
					
			grapesForm.setReadOnly(false);
			changeCurrentSelection(grapeToAdd);
		}
		
	}

	public boolean isNewGrapeMode() {
		return newGrapeMode;
	}

	public void setNewGrapeMode(boolean newGrapeMode) {
		this.newGrapeMode = newGrapeMode;
		grapesForm.setNewGrapeMode(newGrapeMode);
	}

	public GrapeList getGrapesList() {
		return grapesList;
	}

	public void setGrapesList(GrapeList grapesList) {
		this.grapesList = grapesList;
	}

	public GrapeForm getGrapesForm() {
		return grapesForm;
	}

	public void setGrapesForm(GrapeForm grapesForm) {
		this.grapesForm = grapesForm;
	}
	
	
	
	


}
