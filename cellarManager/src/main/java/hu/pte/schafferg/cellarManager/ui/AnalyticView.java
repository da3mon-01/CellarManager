package hu.pte.schafferg.cellarManager.ui;

import hu.pte.schafferg.cellarManager.model.Analytic;
import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.ui.components.AnalyticForm;
import hu.pte.schafferg.cellarManager.ui.components.AnalyticList;

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

public class AnalyticView extends VerticalLayout implements ClickListener,
		TextChangeListener, ValueChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6632094229536254771L;
	private HorizontalLayout toolbar = new HorizontalLayout();
	private Button newAnalytic = new Button("New");
	private Button editAnalytic = new Button("Edit");
	private Button saveAnalytic= new Button("Save");
	private Button delAnalytic = new Button("Delete");
	private TextField searchField = new TextField();
	private Select searchSelect = new Select();
	@Autowired
	private AnalyticList analyticList;
	@Autowired
	private AnalyticForm analyticForm;
	private Analytic selection = null;
	private Logger logger = Logger.getLogger(AnalyticView.class);
	private boolean newAnalyticMode = false;
	
	public void initContent(){
		setMargin(true);
		setSpacing(true);
		toolbar.setSpacing(true);

		newAnalytic.addStyleName("big");
		newAnalytic.setIcon(new ThemeResource("icons/add.png"));
		newAnalytic.addListener((ClickListener) this);
		toolbar.addComponent(newAnalytic);

		editAnalytic.addStyleName("big");
		editAnalytic.setIcon(new ThemeResource("icons/edit.png"));
		editAnalytic.addListener((ClickListener) this);
		toolbar.addComponent(editAnalytic);

		saveAnalytic.addStyleName("big");
		saveAnalytic.setIcon(new ThemeResource("icons/save.png"));
		saveAnalytic.addListener((ClickListener) this);
		toolbar.addComponent(saveAnalytic);

		delAnalytic.addStyleName("big");
		delAnalytic.setIcon(new ThemeResource("icons/delete.png"));
		delAnalytic.addListener((ClickListener) this);
		toolbar.addComponent(delAnalytic);
		
		
		searchField.addStyleName("search big");
		searchField.addListener((TextChangeListener)this);
		toolbar.addComponent(searchField);
		toolbar.setComponentAlignment(searchField, Alignment.MIDDLE_CENTER);
		
		searchSelect.addStyleName("big");
		Object[] visibleProperties = analyticList.getListOfVisibleColumns();
		String[] properNames = analyticList.getListOfColumnHeaders();
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

		analyticList.addListener((ValueChangeListener) this);
		addComponent(analyticList);
		
		Panel landsDetails = new Panel();
		landsDetails.setCaption("Analytic Details");
		landsDetails.addComponent(analyticForm);
		addComponent(landsDetails);
	}
	
	public void createFieldWork(){
		if(selection == null || analyticForm.getItemDataSource() == null){
			getWindow().showNotification("Save Failed",
					"Please click New first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}else{
			selection = commitFromForm(selection);
			
			try {
				analyticList.create(selection);
				getWindow().showNotification("Success!", "Analytic "+selection.getMust()+"/"+selection.getWhen()+ "was created successfully", Notification.TYPE_TRAY_NOTIFICATION);
			} catch (RuntimeException e) {
				getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			} finally {
				changeCurrentSelection(selection);
			}
		
		}
		
	}
	
	public void updateFieldWork(){
		logger.info("Trying update FieldWork method");

		if (selection == null || analyticForm.getItemDataSource() == null) {
			getWindow().showNotification("Save Failed",
					"Please select somebody first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}

		selection = commitFromForm(selection);
		
		try {
			analyticList.update(selection);
			getWindow().showNotification("Success!", "Analytic "+selection.getMust()+"/"+selection.getWhen()+" was updated successfully", Notification.TYPE_TRAY_NOTIFICATION);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}finally {
			changeCurrentSelection(selection);
		}
		
	}
	
	public void deleteFieldWork(){
		try {
			analyticList.delete(selection);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		
		getWindow().showNotification("Analytic "+selection.getMust()+"/"+selection.getWhen()+" was deleted successfully", Notification.TYPE_TRAY_NOTIFICATION);
		selection = null;
		analyticForm.setItemDataSource(null);
		
	}
	
	private void changeCurrentSelection(Object select){
		Analytic selectedWork = (Analytic)select;
		
		selection = selectedWork;
		BeanItem<Analytic> personToEdit = convertAnalyticToBeanItem(selection);
		analyticForm.setItemDataSource(personToEdit);
		logger.debug("Current selection: "+selection);
	}
	
	private BeanItem<Analytic> convertAnalyticToBeanItem(Analytic analytic){
		return new BeanItem<Analytic>(analytic, new String[]{ "must", "when", "sulfur", "sugar", "iron", "extract"});

	}
	
	private Analytic commitFromForm(Analytic analytic){
		analytic.setMust((GrapeMust) analyticForm.getItemProperty("must").getValue());
		analytic.setSulfur((double) analyticForm.getItemProperty("sulfur").getValue());
		analytic.setSugar((double) analyticForm.getItemProperty("sugar").getValue());
		analytic.setIron((double) analyticForm.getItemProperty("iron").getValue());
		analytic.setExtract((double) analyticForm.getItemProperty("extract").getValue());
		
		return analytic;
	}
	
	private void openDeleteConfirmWindow() {
		ConfirmDialog confirmDialog = ConfirmDialog.show(getWindow(),
				"Are you sure you want to <b>DELETE</b> the selected analytic?",
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
	public void valueChange(ValueChangeEvent event) {
		logger.debug("valuechange. " + event);
		Property eventProperty = event.getProperty();
		if (eventProperty == analyticList) {
			logger.debug("Value changed to: " + analyticList.getValue());
			setNewAnalyticMode(false);
			changeCurrentSelection(analyticList.getValue());
		}else if(eventProperty == searchSelect){
			logger.info("Value changed to: "+searchSelect.getValue());
			SimpleStringFilter filter = null;
			filter = new SimpleStringFilter(searchSelect.getValue(), (String) searchField.getValue(), true, false);
			analyticList.addFilter(filter);
		}

	}

	@Override
	public void textChange(TextChangeEvent event) {
		SimpleStringFilter filter = null;
		
		filter = new SimpleStringFilter(searchSelect.getValue(), event.getText(), true, false);
		analyticList.addFilter(filter);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		
		if(source == delAnalytic){
			openDeleteConfirmWindow();
		} else if (source == editAnalytic) {
			if (selection == null || analyticForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select something first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				analyticForm.setReadOnly(false);
			}
		} else if (source == saveAnalytic) {
			if (!analyticForm.isValid()) {
				getWindow().showNotification("Update Error",
						"Are all the required fields filled out?",
						Notification.TYPE_WARNING_MESSAGE);
				return;
			}
			analyticForm.commit();

			if(newAnalyticMode){
				createFieldWork();
			}else{
				updateFieldWork();
			}

			analyticForm.setReadOnly(true);
			setNewAnalyticMode(false);
		} else if (source == newAnalytic) {
			setNewAnalyticMode(true);
			Analytic analyticToAdd = new Analytic();
					
			analyticForm.setReadOnly(false);
			changeCurrentSelection(analyticToAdd);
		}

	}

	public boolean isNewAnalyticMode() {
		return newAnalyticMode;
	}

	public void setNewAnalyticMode(boolean newAnalyticMode) {
		this.newAnalyticMode = newAnalyticMode;
		analyticForm.setNewAnalyticMode(newAnalyticMode);
	}
	
	

}
