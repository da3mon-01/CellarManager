package hu.pte.schafferg.cellarManager.ui;

import hu.pte.schafferg.cellarManager.model.Grape;
import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.ui.components.GrapeMustForm;
import hu.pte.schafferg.cellarManager.ui.components.GrapeMustList;
import hu.pte.schafferg.cellarManager.ui.components.GrapeMustListOfAnalytics;
import hu.pte.schafferg.cellarManager.ui.components.GrapeMustListOfWinesMade;

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
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class GrapeMustView extends VerticalLayout implements ClickListener,
		TextChangeListener, ValueChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1657608291784214447L;
	private HorizontalLayout toolbar = new HorizontalLayout();
	private Button newGrapeMust = new Button("New");
	private Button editGrapeMust = new Button("Edit");
	private Button saveGrapeMust= new Button("Save");
	private Button delGrapeMust = new Button("Delete");
	private TextField searchField = new TextField();
	private Select searchSelect = new Select();
	@Autowired
	private GrapeMustList grapeMustList;
	@Autowired
	private GrapeMustForm grapeMustForm;
	@Autowired
	private GrapeMustListOfAnalytics listOfAnalytics;
	@Autowired
	private GrapeMustListOfWinesMade listOfWinesMade;
	private GrapeMust selection = null;
	private Logger logger = Logger.getLogger(LandsView.class);
	private boolean newGrapeMustMode = false;
	private TabSheet mustTab = new TabSheet();
	
	public void initContent(){
		setMargin(true);
		setSpacing(true);
		setWidth("98%");
		toolbar.setSpacing(true);

		newGrapeMust.addStyleName("big");
		newGrapeMust.setIcon(new ThemeResource("icons/add.png"));
		newGrapeMust.addListener((ClickListener) this);
		toolbar.addComponent(newGrapeMust);

		editGrapeMust.addStyleName("big");
		editGrapeMust.setIcon(new ThemeResource("icons/edit.png"));
		editGrapeMust.addListener((ClickListener) this);
		toolbar.addComponent(editGrapeMust);

		saveGrapeMust.addStyleName("big");
		saveGrapeMust.setIcon(new ThemeResource("icons/save.png"));
		saveGrapeMust.addListener((ClickListener) this);
		toolbar.addComponent(saveGrapeMust);

		delGrapeMust.addStyleName("big");
		delGrapeMust.setIcon(new ThemeResource("icons/delete.png"));
		delGrapeMust.addListener((ClickListener) this);
		toolbar.addComponent(delGrapeMust);
		
		
		searchField.addStyleName("search big");
		searchField.addListener((TextChangeListener)this);
		toolbar.addComponent(searchField);
		toolbar.setComponentAlignment(searchField, Alignment.MIDDLE_CENTER);
		
		searchSelect.addStyleName("big");
		Object[] visibleProperties = grapeMustList.getListOfVisibleColumns();
		String[] properNames = grapeMustList.getListOfColumnHeaders();
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

		grapeMustList.addListener((ValueChangeListener) this);
		addComponent(grapeMustList);
		
		Panel mustDetails = new Panel();
		mustDetails.setCaption("Grape must Details");
		mustDetails.addComponent(mustTab);
		mustTab.addTab(grapeMustForm).setCaption("Grape Must");
		mustTab.addTab(listOfAnalytics).setCaption("Analytics");
		mustTab.addTab(listOfWinesMade).setCaption("Wines Made");
		addComponent(mustDetails);
	}
	
	public void createGrapeMust(){
		if(selection == null || grapeMustForm.getItemDataSource() == null){
			getWindow().showNotification("Save Failed",
					"Please click New first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}else{
			selection = commitFromForm(selection);
			
			try {
				grapeMustList.create(selection);
				getWindow().showNotification("Success!", "Grape must "+selection+"was created successfully", Notification.TYPE_TRAY_NOTIFICATION);
			} catch (RuntimeException e) {
				getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			} finally {
				changeCurrentSelection(selection);
			}
		
		}
		
	}
	
	public void updateGrapeMust(){
		logger.info("Trying update Person method");

		if (selection == null || grapeMustForm.getItemDataSource() == null) {
			getWindow().showNotification("Save Failed",
					"Please select somebody first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}

		selection = commitFromForm(selection);
		
		try {
			grapeMustList.update(selection);
			getWindow().showNotification("Success!", "Grape must "+selection+" was updated successfully", Notification.TYPE_TRAY_NOTIFICATION);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}finally {
			changeCurrentSelection(selection);
		}
		
	}
	
	public void deleteGrapeMust(){
		try {
			grapeMustList.delete(selection);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		
		getWindow().showNotification("Grape must "+selection+" was deleted successfully", Notification.TYPE_TRAY_NOTIFICATION);
		selection = null;
		grapeMustForm.setItemDataSource(null);
		
	}
	
	private void changeCurrentSelection(Object select){
		GrapeMust selectedGrapeMust = (GrapeMust)select;
		
		selection = selectedGrapeMust;
		BeanItem<GrapeMust> mustToEdit = convertGrapeMustToBeanItem(selection);
		grapeMustForm.setItemDataSource(mustToEdit);
		listOfAnalytics.setMustData(selection);
		listOfWinesMade.setMustData(selection);
		logger.debug("Current selection: " + selection);
	}
	
	private BeanItem<GrapeMust> convertGrapeMustToBeanItem(GrapeMust must){
		return new BeanItem<GrapeMust>(must, new String[]{"madeFrom", "quantityAfterHarvest", "quantityLostAfterRacking", "mustDegree", "enriched", "enrichmentDegree", "sweetened"});
	}
	
	private GrapeMust commitFromForm(GrapeMust must){
		must.setMadeFrom((Grape) grapeMustForm.getItemProperty("madeFrom").getValue());
		must.setMustDegree((double) grapeMustForm.getItemProperty("mustDegree").getValue());
		must.setQuantityAfterHarvest((double) grapeMustForm.getItemProperty("quantityAfterHarvest").getValue());
		must.setQuantityLostAfterRacking((double) grapeMustForm.getItemProperty("quantityLostAfterRacking").getValue());
		must.setEnriched((boolean) grapeMustForm.getItemProperty("enriched").getValue());
		must.setEnrichmentDegree((double) grapeMustForm.getItemProperty("enrichmentDegree").getValue());
		must.setSweetened((boolean) grapeMustForm.getItemProperty("sweetened").getValue());
		
		return must;
	}
	
	private void openDeleteConfirmWindow() {
		ConfirmDialog confirmDialog = ConfirmDialog.show(getWindow(),
				"Are you sure you want to <b>DELETE</b> the selected Grape must?",
				new ConfirmDialog.Listener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -313817438000138156L;

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							deleteGrapeMust();
						}

					}
				});
		confirmDialog.setContentMode(ConfirmDialog.CONTENT_HTML);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		logger.debug("valuechange. " + event);
		Property eventProperty = event.getProperty();
		if (eventProperty == grapeMustList) {
			logger.debug("Value changed to: " + grapeMustList.getValue());
			setNewGrapeMustMode(false);
			changeCurrentSelection(grapeMustList.getValue());
		}else if(eventProperty == searchSelect){
			logger.info("Value changed to: "+searchSelect.getValue());
			SimpleStringFilter filter = null;
			filter = new SimpleStringFilter(searchSelect.getValue(), (String) searchField.getValue(), true, false);
			grapeMustList.addFilter(filter);
		}

	}

	@Override
	public void textChange(TextChangeEvent event) {
		SimpleStringFilter filter = null;
		
		filter = new SimpleStringFilter(searchSelect.getValue(), event.getText(), true, false);
		grapeMustList.addFilter(filter);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		
		if(source == delGrapeMust){
			if (selection == null || grapeMustForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select something first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				openDeleteConfirmWindow();
			}
		} else if (source == editGrapeMust) {
			if (selection == null || grapeMustForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select something first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				grapeMustForm.setReadOnly(false);
			}
		} else if (source == saveGrapeMust) {
			if (!grapeMustForm.isValid()) {
				getWindow().showNotification("Update Error",
						"Are all the required fields filled out?",
						Notification.TYPE_WARNING_MESSAGE);
				return;
			}
			grapeMustForm.commit();

			if(newGrapeMustMode){
				createGrapeMust();
			}else{
				updateGrapeMust();
			}

			grapeMustForm.setReadOnly(true);
			setNewGrapeMustMode(false);
		} else if (source == newGrapeMust) {
			setNewGrapeMustMode(true);
			GrapeMust mustToAdd = new GrapeMust();
					
			grapeMustForm.setReadOnly(false);
			changeCurrentSelection(mustToAdd);
		}

	}

	public boolean isNewGrapeMustMode() {
		return newGrapeMustMode;
	}

	public void setNewGrapeMustMode(boolean newGrapeMustMode) {
		this.newGrapeMustMode = newGrapeMustMode;
		grapeMustForm.setNewGrapeMustMode(newGrapeMustMode);
	}

	public GrapeMustList getGrapeMustList() {
		return grapeMustList;
	}

	public void setGrapeMustList(GrapeMustList grapeMustList) {
		this.grapeMustList = grapeMustList;
	}

	public GrapeMustForm getGrapeMustForm() {
		return grapeMustForm;
	}

	public void setGrapeMustForm(GrapeMustForm grapeMustForm) {
		this.grapeMustForm = grapeMustForm;
	}

	public GrapeMustListOfAnalytics getListOfAnalytics() {
		return listOfAnalytics;
	}

	public void setListOfAnalytics(GrapeMustListOfAnalytics listOfAnalytics) {
		this.listOfAnalytics = listOfAnalytics;
	}

	public GrapeMustListOfWinesMade getListOfWinesMade() {
		return listOfWinesMade;
	}

	public void setListOfWinesMade(GrapeMustListOfWinesMade listOfWinesMade) {
		this.listOfWinesMade = listOfWinesMade;
	}
	
	
	
	

}
