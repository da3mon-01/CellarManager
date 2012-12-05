package hu.pte.schafferg.cellarManager.ui;

import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.ui.components.LandsForm;
import hu.pte.schafferg.cellarManager.ui.components.LandsList;

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

public class LandsView extends VerticalLayout implements ClickListener,
ValueChangeListener, TextChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 107193980695330296L;
	private HorizontalLayout toolbar = new HorizontalLayout();
	private Button newLand = new Button("New");
	private Button editLand = new Button("Edit");
	private Button saveLand= new Button("Save");
	private Button delLand = new Button("Delete");
	private TextField searchField = new TextField();
	private Select searchSelect = new Select();
	@Autowired
	private LandsList landsList;
	@Autowired
	private LandsForm landsForm;
	private Land selection = null;
	private Logger logger = Logger.getLogger(LandsView.class);
	private boolean newLandMode = false;
	
	public void initContent(){
		setMargin(true);
		setSpacing(true);
		toolbar.setSpacing(true);

		newLand.addStyleName("big");
		newLand.setIcon(new ThemeResource("icons/add.png"));
		newLand.addListener((ClickListener) this);
		toolbar.addComponent(newLand);

		editLand.addStyleName("big");
		editLand.setIcon(new ThemeResource("icons/edit.png"));
		editLand.addListener((ClickListener) this);
		toolbar.addComponent(editLand);

		saveLand.addStyleName("big");
		saveLand.setIcon(new ThemeResource("icons/save.png"));
		saveLand.addListener((ClickListener) this);
		toolbar.addComponent(saveLand);

		delLand.addStyleName("big");
		delLand.setIcon(new ThemeResource("icons/delete.png"));
		delLand.addListener((ClickListener) this);
		toolbar.addComponent(delLand);
		
		
		searchField.addStyleName("search big");
		searchField.addListener((TextChangeListener)this);
		toolbar.addComponent(searchField);
		toolbar.setComponentAlignment(searchField, Alignment.MIDDLE_CENTER);
		
		searchSelect.addStyleName("big");
		Object[] visibleProperties = landsList.getListOfVisibleColumns();
		String[] properNames = landsList.getListOfColumnHeaders();
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

		landsList.addListener((ValueChangeListener) this);
		addComponent(landsList);
		
		Panel landsDetails = new Panel();
		landsDetails.setCaption("Land Details");
		landsDetails.addComponent(landsForm);
		addComponent(landsDetails);
	}
	
	public void createLand(){
		if(selection == null || landsForm.getItemDataSource() == null){
			getWindow().showNotification("Save Failed",
					"Please click New first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}else{
			selection = commitFromForm(selection);
			
			try {
				landsList.create(selection);
				getWindow().showNotification("Success!", "Land "+selection.getLandOff()+"/"+selection.getLandOffId()+" was created successfully", Notification.TYPE_TRAY_NOTIFICATION);
			} catch (RuntimeException e) {
				getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			} finally {
				changeCurrentSelection(selection);
			}
		
		}
		
	}
	
	public void updateLand(){
		logger.info("Trying update Person method");

		if (selection == null || landsForm.getItemDataSource() == null) {
			getWindow().showNotification("Save Failed",
					"Please select somebody first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}

		selection = commitFromForm(selection);
		
		try {
			landsList.update(selection);
			getWindow().showNotification("Success!", "Land "+selection.getLandOff()+"/"+selection.getLandOffId()+" was updated successfully", Notification.TYPE_TRAY_NOTIFICATION);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}finally {
			changeCurrentSelection(selection);
		}
		
	}
	
	public void deleteLand(){
		try {
			landsList.delete(selection);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		
		getWindow().showNotification("Land "+selection.getLandOff()+"/"+selection.getLandOffId()+" was deleted successfully", Notification.TYPE_TRAY_NOTIFICATION);
		selection = null;
		landsForm.setItemDataSource(null);
		
	}
	
	private void changeCurrentSelection(Object select){
		Land selectedLand = (Land)select;
		
		selection = selectedLand;
		BeanItem<Land> landToEdit = convertLandToBeanItem(selection);
		landsForm.setItemDataSource(landToEdit);
		logger.debug("Current selection: " + selection.getLandOff()+"/"+selection.getLandOffId());
	}
	
	private BeanItem<Land> convertLandToBeanItem(Land land){
		return new BeanItem<Land>(land, new String[]{"landOffId", "landOff", "size", "owner"});
	}
	
	private Land commitFromForm(Land land){
		land.setLandOff((String) landsForm.getItemProperty("landOff").getValue());
		land.setLandOffId((String) landsForm.getItemProperty("landOffId").getValue());
		land.setSize( (int) landsForm.getItemProperty("size").getValue());
		land.setOwner((Person) landsForm.getItemProperty("owner").getValue());
		
		return land;
	}
	
	private void openDeleteConfirmWindow() {
		ConfirmDialog confirmDialog = ConfirmDialog.show(getWindow(),
				"Are you sure you want to <b>DELETE</b> the selected land?",
				new ConfirmDialog.Listener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -313817438000138156L;

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							deleteLand();
						}

					}
				});
		confirmDialog.setContentMode(ConfirmDialog.CONTENT_HTML);
	}

	@Override
	public void textChange(TextChangeEvent event) {
		SimpleStringFilter filter = null;
		
		filter = new SimpleStringFilter(searchSelect.getValue(), event.getText(), true, false);
		landsList.addFilter(filter);
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		logger.debug("valuechange. " + event);
		Property eventProperty = event.getProperty();
		if (eventProperty == landsList) {
			logger.debug("Value changed to: " + landsList.getValue());
			setNewLandMode(false);
			changeCurrentSelection(landsList.getValue());
		}else if(eventProperty == searchSelect){
			logger.info("Value changed to: "+searchSelect.getValue());
			SimpleStringFilter filter = null;
			filter = new SimpleStringFilter(searchSelect.getValue(), (String) searchField.getValue(), true, false);
			landsList.addFilter(filter);
		}
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		
		if(source == delLand){
			openDeleteConfirmWindow();
		} else if (source == editLand) {
			if (selection == null || landsForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select something first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				landsForm.setReadOnly(false);
			}
		} else if (source == saveLand) {
			if (!landsForm.isValid()) {
				getWindow().showNotification("Update Error",
						"Are all the required fields filled out?",
						Notification.TYPE_WARNING_MESSAGE);
				return;
			}
			landsForm.commit();

			if(newLandMode){
				createLand();
			}else{
				updateLand();
			}

			landsForm.setReadOnly(true);
			setNewLandMode(false);
		} else if (source == newLand) {
			setNewLandMode(true);
			Land landToAdd = new Land();
					
			landsForm.setReadOnly(false);
			changeCurrentSelection(landToAdd);
		}
		
	}

	public TextField getSearchField() {
		return searchField;
	}

	public void setSearchField(TextField searchField) {
		this.searchField = searchField;
	}

	public Select getSearchSelect() {
		return searchSelect;
	}

	public void setSearchSelect(Select searchSelect) {
		this.searchSelect = searchSelect;
	}

	public LandsList getLandsList() {
		return landsList;
	}

	public void setLandsList(LandsList landsList) {
		this.landsList = landsList;
	}

	public LandsForm getLandsForm() {
		return landsForm;
	}

	public void setLandsForm(LandsForm landsForm) {
		this.landsForm = landsForm;
	}

	public Land getSelection() {
		return selection;
	}

	public void setSelection(Land selection) {
		this.selection = selection;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public boolean isNewLandMode() {
		return newLandMode;
	}

	public void setNewLandMode(boolean newLandMode) {
		this.newLandMode = newLandMode;
		landsForm.setNewLandMode(newLandMode);
	}
	
	

}
