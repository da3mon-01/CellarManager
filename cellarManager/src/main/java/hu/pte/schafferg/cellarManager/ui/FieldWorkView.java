package hu.pte.schafferg.cellarManager.ui;

import hu.pte.schafferg.cellarManager.model.FieldWork;
import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.ui.components.FieldWorkForm;
import hu.pte.schafferg.cellarManager.ui.components.FieldWorkList;
import hu.pte.schafferg.cellarManager.util.FieldWorkType;

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

public class FieldWorkView extends VerticalLayout implements ClickListener,
		ValueChangeListener, TextChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4591399200143617972L;
	private HorizontalLayout toolbar = new HorizontalLayout();
	private Button newFieldWork = new Button("New");
	private Button editFieldWork = new Button("Edit");
	private Button saveFieldWork= new Button("Save");
	private Button delFieldWork = new Button("Delete");
	private TextField searchField = new TextField();
	private Select searchSelect = new Select();
	@Autowired
	private FieldWorkList fieldWorkList;
	@Autowired
	private FieldWorkForm fieldWorkForm;
	private FieldWork selection = null;
	private Logger logger = Logger.getLogger(LandsView.class);
	private boolean newFieldWorkMode = false;
	
	public void initContent(){
		setMargin(true);
		setSpacing(true);
		setWidth("98%");
		toolbar.setSpacing(true);

		newFieldWork.addStyleName("big");
		newFieldWork.setIcon(new ThemeResource("icons/add.png"));
		newFieldWork.addListener((ClickListener) this);
		toolbar.addComponent(newFieldWork);

		editFieldWork.addStyleName("big");
		editFieldWork.setIcon(new ThemeResource("icons/edit.png"));
		editFieldWork.addListener((ClickListener) this);
		toolbar.addComponent(editFieldWork);

		saveFieldWork.addStyleName("big");
		saveFieldWork.setIcon(new ThemeResource("icons/save.png"));
		saveFieldWork.addListener((ClickListener) this);
		toolbar.addComponent(saveFieldWork);

		delFieldWork.addStyleName("big");
		delFieldWork.setIcon(new ThemeResource("icons/delete.png"));
		delFieldWork.addListener((ClickListener) this);
		toolbar.addComponent(delFieldWork);
		
		
		searchField.addStyleName("search big");
		searchField.addListener((TextChangeListener)this);
		toolbar.addComponent(searchField);
		toolbar.setComponentAlignment(searchField, Alignment.MIDDLE_CENTER);
		
		searchSelect.addStyleName("big");
		Object[] visibleProperties = fieldWorkList.getListOfVisibleColumns();
		String[] properNames = fieldWorkList.getListOfColumnHeaders();
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

		fieldWorkList.addListener((ValueChangeListener) this);
		addComponent(fieldWorkList);
		
		Panel workDetails = new Panel();
		workDetails.setCaption("FieldWork Details");
		workDetails.addComponent(fieldWorkForm);
		addComponent(workDetails);
	}
	
	public void createFieldWork(){
		if(selection == null || fieldWorkForm.getItemDataSource() == null){
			getWindow().showNotification("Save Failed",
					"Please click New first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}else{
			selection = commitFromForm(selection);
			
			try {
				fieldWorkList.create(selection);
				getWindow().showNotification("Success!", "Field work "+selection.getWork()+" --> "+selection.getOnWhat().getLandOff()+"/"+selection.getOnWhat().getLandOffId()+" was created successfully", Notification.TYPE_TRAY_NOTIFICATION);
			} catch (RuntimeException e) {
				getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			} finally {
				changeCurrentSelection(selection);
			}
		
		}
		
	}
	
	public void updateFieldWork(){
		logger.info("Trying update FieldWork method");

		if (selection == null || fieldWorkForm.getItemDataSource() == null) {
			getWindow().showNotification("Save Failed",
					"Please select somebody first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}

		selection = commitFromForm(selection);
		
		try {
			fieldWorkList.update(selection);
			getWindow().showNotification("Success!", "Field work "+selection.getWork()+" --> "+selection.getOnWhat().getLandOff()+"/"+selection.getOnWhat().getLandOffId()+" was updated successfully", Notification.TYPE_TRAY_NOTIFICATION);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}finally {
			changeCurrentSelection(selection);
		}
		
	}
	
	public void deleteFieldWork(){
		try {
			fieldWorkList.delete(selection);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		
		getWindow().showNotification("Field work "+selection.getWork()+" --> "+selection.getOnWhat().getLandOff()+"/"+selection.getOnWhat().getLandOffId()+" was deleted successfully", Notification.TYPE_TRAY_NOTIFICATION);
		selection = null;
		fieldWorkForm.setItemDataSource(null);
		
	}
	
	
	private void changeCurrentSelection(Object select){
		FieldWork selectedWork = (FieldWork)select;
		
		selection = selectedWork;
		BeanItem<FieldWork> fieldworkToEdit = convertFieldWorkToBeanItem(selection);
		fieldWorkForm.setItemDataSource(fieldworkToEdit);
		logger.debug("Current selection: "+selection);
	}
	
	private BeanItem<FieldWork> convertFieldWorkToBeanItem(FieldWork work){
		return new BeanItem<FieldWork>(work, new String[]{ "who", "when", "work", "onWhat"});

	}
	
	private FieldWork commitFromForm(FieldWork work){
		work.setOnWhat((Land) fieldWorkForm.getItemProperty("onWhat").getValue());
		work.setWhen((Date) fieldWorkForm.getItemProperty("when").getValue());
		work.setWho((Person) fieldWorkForm.getItemProperty("who").getValue());
		work.setWork((FieldWorkType) fieldWorkForm.getItemProperty("work").getValue());
		
		return work;
	}
	
	private void openDeleteConfirmWindow() {
		ConfirmDialog confirmDialog = ConfirmDialog.show(getWindow(),
				"Are you sure you want to <b>DELETE</b> the selected field work?",
				new ConfirmDialog.Listener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -313817438000138156L;

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							deleteFieldWork();
						}

					}
				});
		confirmDialog.setContentMode(ConfirmDialog.CONTENT_HTML);
	}

	@Override
	public void textChange(TextChangeEvent event) {
		SimpleStringFilter filter = null;
		
		filter = new SimpleStringFilter(searchSelect.getValue(), event.getText(), true, false);
		fieldWorkList.addFilter(filter);

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		logger.debug("valuechange. " + event);
		Property eventProperty = event.getProperty();
		if (eventProperty == fieldWorkList) {
			logger.debug("Value changed to: " + fieldWorkList.getValue());
			setNewFieldWorkMode(false);
			changeCurrentSelection(fieldWorkList.getValue());
		}else if(eventProperty == searchSelect){
			logger.info("Value changed to: "+searchSelect.getValue());
			SimpleStringFilter filter = null;
			filter = new SimpleStringFilter(searchSelect.getValue(), (String) searchField.getValue(), true, false);
			fieldWorkList.addFilter(filter);
		}

	}

	@Override
	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		
		if(source == delFieldWork){
			if (selection == null || fieldWorkForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select something first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				openDeleteConfirmWindow();
			}
		} else if (source == editFieldWork) {
			if (selection == null || fieldWorkForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select something first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				fieldWorkForm.setReadOnly(false);
			}
		} else if (source == saveFieldWork) {
			if (!fieldWorkForm.isValid()) {
				getWindow().showNotification("Update Error",
						"Are all the required fields filled out?",
						Notification.TYPE_WARNING_MESSAGE);
				return;
			}
			fieldWorkForm.commit();

			if(newFieldWorkMode){
				createFieldWork();
			}else{
				updateFieldWork();
			}

			fieldWorkForm.setReadOnly(true);
			setNewFieldWorkMode(false);
		} else if (source == newFieldWork) {
			setNewFieldWorkMode(true);
			FieldWork workToAdd = new FieldWork();
					
			fieldWorkForm.setReadOnly(false);
			changeCurrentSelection(workToAdd);
		}

	}

	public FieldWorkList getLandsList() {
		return fieldWorkList;
	}

	public void setLandsList(FieldWorkList landsList) {
		this.fieldWorkList = landsList;
	}

	public FieldWorkForm getLandsForm() {
		return fieldWorkForm;
	}

	public void setLandsForm(FieldWorkForm landsForm) {
		this.fieldWorkForm = landsForm;
	}

	public boolean isNewFieldWorkMode() {
		return newFieldWorkMode;
	}

	public void setNewFieldWorkMode(boolean newFieldWorkMode) {
		this.newFieldWorkMode = newFieldWorkMode;
		fieldWorkForm.setNewFieldWorkMode(newFieldWorkMode);
	}

}
