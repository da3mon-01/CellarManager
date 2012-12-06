package hu.pte.schafferg.cellarManager.ui;

import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.model.Wine;
import hu.pte.schafferg.cellarManager.ui.components.WineForm;
import hu.pte.schafferg.cellarManager.ui.components.WineList;
import hu.pte.schafferg.cellarManager.util.WineSweetness;

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

public class WineView extends VerticalLayout implements ClickListener, TextChangeListener, ValueChangeListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2809171457784545874L;
	private HorizontalLayout toolbar = new HorizontalLayout();
	private Button newWine = new Button("New");
	private Button editWine = new Button("Edit");
	private Button saveWine= new Button("Save");
	private Button delWine = new Button("Delete");
	private TextField searchField = new TextField();
	private Select searchSelect = new Select();
	@Autowired
	private WineList wineList;
	@Autowired
	private WineForm wineForm;
	private Wine selection = null;
	private Logger logger = Logger.getLogger(WineView.class);
	private boolean newWineMode = false;
	
	public void initContent(){
		setMargin(true);
		setSpacing(true);
		toolbar.setSpacing(true);

		newWine.addStyleName("big");
		newWine.setIcon(new ThemeResource("icons/add.png"));
		newWine.addListener((ClickListener) this);
		toolbar.addComponent(newWine);

		editWine.addStyleName("big");
		editWine.setIcon(new ThemeResource("icons/edit.png"));
		editWine.addListener((ClickListener) this);
		toolbar.addComponent(editWine);

		saveWine.addStyleName("big");
		saveWine.setIcon(new ThemeResource("icons/save.png"));
		saveWine.addListener((ClickListener) this);
		toolbar.addComponent(saveWine);

		delWine.addStyleName("big");
		delWine.setIcon(new ThemeResource("icons/delete.png"));
		delWine.addListener((ClickListener) this);
		toolbar.addComponent(delWine);
		
		
		searchField.addStyleName("search big");
		searchField.addListener((TextChangeListener)this);
		toolbar.addComponent(searchField);
		toolbar.setComponentAlignment(searchField, Alignment.MIDDLE_CENTER);
		
		searchSelect.addStyleName("big");
		Object[] visibleProperties = wineList.getListOfVisibleColumns();
		String[] properNames = wineList.getListOfColumnHeaders();
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

		wineList.addListener((ValueChangeListener) this);
		addComponent(wineList);
		
		Panel landsDetails = new Panel();
		landsDetails.setCaption("Wine Details");
		landsDetails.addComponent(wineForm);
		addComponent(landsDetails);
	}
	
	public void createWine(){
		if(selection == null || wineForm.getItemDataSource() == null){
			getWindow().showNotification("Save Failed",
					"Please click New first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}else{
			selection = commitFromForm(selection);
			
			try {
				wineList.create(selection);
				getWindow().showNotification("Success!", selection+"was created successfully", Notification.TYPE_TRAY_NOTIFICATION);
			} catch (RuntimeException e) {
				getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			} finally {
				changeCurrentSelection(selection);
			}
		
		}
		
	}
	
	public void updateWine(){
		logger.info("Trying update Person method");

		if (selection == null || wineForm.getItemDataSource() == null) {
			getWindow().showNotification("Save Failed",
					"Please select somebody first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}

		selection = commitFromForm(selection);
		
		try {
			wineList.update(selection);
			getWindow().showNotification("Success!", selection+" was updated successfully", Notification.TYPE_TRAY_NOTIFICATION);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}finally {
			changeCurrentSelection(selection);
		}
		
	}
	
	public void deleteWine(){
		try {
			wineList.delete(selection);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		
		getWindow().showNotification(selection+" was deleted successfully", Notification.TYPE_TRAY_NOTIFICATION);
		selection = null;
		wineForm.setItemDataSource(null);
		
	}
	
	private void changeCurrentSelection(Object select){
		Wine selectedWine = (Wine)select;
		
		selection = selectedWine;
		BeanItem<Wine> mustToEdit = convertWineToBeanItem(selection);
		wineForm.setItemDataSource(mustToEdit);
		logger.debug("Current selection: " + selection);
	}
	
	private BeanItem<Wine> convertWineToBeanItem(Wine wine){
		return new BeanItem<Wine>(wine, new String[]{"obiNumber", "alcoholDegree", "sweetness", "madeFrom", "bottler", "numOfBottles"});
	}
	
	private Wine commitFromForm(Wine wine){
		wine.setObiNumber((String) wineForm.getItemProperty("obiNumber").getValue());
		wine.setAlcoholDegree((double) wineForm.getItemProperty("alcoholDegree").getValue());
		wine.setSweetness((WineSweetness) wineForm.getItemProperty("sweetness").getValue());
		wine.setMadeFrom((GrapeMust) wineForm.getItemProperty("madeFrom").getValue());
		wine.setBottler((Person) wineForm.getItemProperty("bottler").getValue());
		wine.setNumOfBottles((int) wineForm.getItemProperty("numOfBottles").getValue());
		
		return wine;
	}
	
	private void openDeleteConfirmWindow() {
		ConfirmDialog confirmDialog = ConfirmDialog.show(getWindow(),
				"Are you sure you want to <b>DELETE</b> the selected wine?",
				new ConfirmDialog.Listener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -313817438000138156L;

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							deleteWine();
						}

					}
				});
		confirmDialog.setContentMode(ConfirmDialog.CONTENT_HTML);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		logger.debug("valuechange. " + event);
		Property eventProperty = event.getProperty();
		if (eventProperty == wineList) {
			logger.debug("Value changed to: " + wineList.getValue());
			setNewWineMode(false);
			changeCurrentSelection(wineList.getValue());
		}else if(eventProperty == searchSelect){
			logger.info("Value changed to: "+searchSelect.getValue());
			SimpleStringFilter filter = null;
			filter = new SimpleStringFilter(searchSelect.getValue(), (String) searchField.getValue(), true, false);
			wineList.addFilter(filter);
		}
		
	}

	@Override
	public void textChange(TextChangeEvent event) {
		SimpleStringFilter filter = null;
		
		filter = new SimpleStringFilter(searchSelect.getValue(), event.getText(), true, false);
		wineList.addFilter(filter);
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		
		if(source == delWine){
			if (selection == null || wineForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select something first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				openDeleteConfirmWindow();
			}
		} else if (source == editWine) {
			if (selection == null || wineForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select something first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				wineForm.setReadOnly(false);
			}
		} else if (source == saveWine) {
			if (!wineForm.isValid()) {
				getWindow().showNotification("Update Error",
						"Are all the required fields filled out?",
						Notification.TYPE_WARNING_MESSAGE);
				return;
			}
			wineForm.commit();

			if(newWineMode){
				createWine();
			}else{
				updateWine();
			}

			wineForm.setReadOnly(true);
			setNewWineMode(false);
		} else if (source == newWine) {
			setNewWineMode(true);
			Wine wineToAdd = new Wine();
					
			wineForm.setReadOnly(false);
			changeCurrentSelection(wineToAdd);
		}

	}
		


	public boolean isNewWineMode() {
		return newWineMode;
	}

	public void setNewWineMode(boolean newWineMode) {
		this.newWineMode = newWineMode;
		wineForm.setNewWineMode(newWineMode);
	}

	public WineList getWineList() {
		return wineList;
	}

	public void setWineList(WineList wineList) {
		this.wineList = wineList;
	}

	public WineForm getWineForm() {
		return wineForm;
	}

	public void setWineForm(WineForm wineForm) {
		this.wineForm = wineForm;
	}
	
	
	
	

}
