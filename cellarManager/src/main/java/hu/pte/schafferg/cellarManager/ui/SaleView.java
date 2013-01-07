package hu.pte.schafferg.cellarManager.ui;

import java.util.Date;

import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.model.Sale;
import hu.pte.schafferg.cellarManager.model.Wine;
import hu.pte.schafferg.cellarManager.ui.components.SaleForm;
import hu.pte.schafferg.cellarManager.ui.components.SaleList;

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

/**
 * View for Sales
 * @author Da3mon
 *
 */
public class SaleView extends VerticalLayout implements ClickListener,
		TextChangeListener, ValueChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1208102846984532898L;
	private HorizontalLayout toolbar = new HorizontalLayout();
	private Button newSale = new Button("New");
	private Button editSale = new Button("Edit");
	private Button saveSale= new Button("Save");
	private Button delSale = new Button("Delete");
	private TextField searchField = new TextField();
	private Select searchSelect = new Select();
	@Autowired
	private SaleList saleList;
	@Autowired
	private SaleForm saleForm;
	private Sale selection = null;
	private Logger logger = Logger.getLogger(SaleView.class);
	private boolean newSaleMode = false;
	
	/**
	 * Builds the GUI
	 */
	public void initContent(){
		setMargin(true);
		setSpacing(true);
		setWidth("98%");
		toolbar.setSpacing(true);

		newSale.addStyleName("big");
		newSale.setIcon(new ThemeResource("icons/add.png"));
		newSale.addListener((ClickListener) this);
		toolbar.addComponent(newSale);

		editSale.addStyleName("big");
		editSale.setIcon(new ThemeResource("icons/edit.png"));
		editSale.addListener((ClickListener) this);
		toolbar.addComponent(editSale);

		saveSale.addStyleName("big");
		saveSale.setIcon(new ThemeResource("icons/save.png"));
		saveSale.addListener((ClickListener) this);
		toolbar.addComponent(saveSale);

		delSale.addStyleName("big");
		delSale.setIcon(new ThemeResource("icons/delete.png"));
		delSale.addListener((ClickListener) this);
		toolbar.addComponent(delSale);
		
		
		searchField.addStyleName("search big");
		searchField.addListener((TextChangeListener)this);
		toolbar.addComponent(searchField);
		toolbar.setComponentAlignment(searchField, Alignment.MIDDLE_CENTER);
		
		searchSelect.addStyleName("big");
		Object[] visibleProperties = saleList.getListOfVisibleColumns();
		String[] properNames = saleList.getListOfColumnHeaders();
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

		saleList.addListener((ValueChangeListener) this);
		addComponent(saleList);
		
		Panel saleDetails = new Panel();
		saleDetails.setCaption("Sale Details");
		saleDetails.addComponent(saleForm);
		addComponent(saleDetails);
	}
	
	/**
	 * Calls the table to create a sale in db.
	 */
	public void createSale(){
		if(selection == null || saleForm.getItemDataSource() == null){
			getWindow().showNotification("Save Failed",
					"Please click New first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}else{
			selection = commitFromForm(selection);
			
			try {
				saleList.create(selection);
				getWindow().showNotification("Success!", selection+"was created successfully", Notification.TYPE_TRAY_NOTIFICATION);
			} catch (RuntimeException e) {
				getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			} finally {
				changeCurrentSelection(selection);
			}
		
		}
		
	}
	
	/**
	 * Calls the table to update a sale in db.
	 */
	public void updateSale(){
		logger.info("Trying update Person method");

		if (selection == null || saleForm.getItemDataSource() == null) {
			getWindow().showNotification("Save Failed",
					"Please select somebody first!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}

		selection = commitFromForm(selection);
		
		try {
			saleList.update(selection);
			getWindow().showNotification("Success!", selection+" was updated successfully", Notification.TYPE_TRAY_NOTIFICATION);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}finally {
			changeCurrentSelection(selection);
		}
		
	}
	
	/**
	 * Calls the table to delete a sale from db.
	 */
	public void deleteSale(){
		try {
			saleList.delete(selection);
		} catch (RuntimeException e) {
			getWindow().showNotification("Error!", e.getMessage() , Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		
		getWindow().showNotification(selection+" was deleted successfully", Notification.TYPE_TRAY_NOTIFICATION);
		selection = null;
		saleForm.setItemDataSource(null);
		
	}
	
	/**
	 * Changes current selection
	 * @param select
	 */
	private void changeCurrentSelection(Object select){
		Sale selectedSale = (Sale)select;
		
		selection = selectedSale;
		BeanItem<Sale> mustToEdit = convertSaleToBeanItem(selection);
		saleForm.setItemDataSource(mustToEdit);
		logger.debug("Current selection: " + selection);
	}
	
	/**
	 * Converts the selection to BeanItem
	 * @param sale
	 * @return
	 */
	private BeanItem<Sale> convertSaleToBeanItem(Sale sale){
		return new BeanItem<Sale>(sale, new String[]{"toWho", "date", "what", "numOfBottles", "wineDocID"});
	}
	
	/**
	 * Saves parameters from Form to the selection
	 * @param sale
	 * @return
	 */
	private Sale commitFromForm(Sale sale){
		sale.setToWho((Person) saleForm.getItemProperty("toWho").getValue());
		sale.setDate((Date) saleForm.getItemProperty("date").getValue());
		sale.setWhat((Wine) saleForm.getItemProperty("what").getValue());
		sale.setNumOfBottles((int) saleForm.getItemProperty("numOfBottles").getValue());
		sale.setWineDocID((String) saleForm.getItemProperty("wineDocID").getValue());
		
		return sale;
	}
	
	private void openDeleteConfirmWindow() {
		ConfirmDialog confirmDialog = ConfirmDialog.show(getWindow(),
				"Are you sure you want to <b>DELETE</b> the selected sale?",
				new ConfirmDialog.Listener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -313817438000138156L;

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							deleteSale();
						}

					}
				});
		confirmDialog.setContentMode(ConfirmDialog.CONTENT_HTML);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		logger.debug("valuechange. " + event);
		Property eventProperty = event.getProperty();
		if (eventProperty == saleList) {
			logger.debug("Value changed to: " + saleList.getValue());
			setNewSaleMode(false);
			changeCurrentSelection(saleList.getValue());
		}else if(eventProperty == searchSelect){
			logger.info("Value changed to: "+searchSelect.getValue());
			SimpleStringFilter filter = null;
			filter = new SimpleStringFilter(searchSelect.getValue(), (String) searchField.getValue(), true, false);
			saleList.addFilter(filter);
		}

	}

	@Override
	public void textChange(TextChangeEvent event) {
		SimpleStringFilter filter = null;
		
		filter = new SimpleStringFilter(searchSelect.getValue(), event.getText(), true, false);
		saleList.addFilter(filter);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		
		if(source == delSale){
			if (selection == null || saleForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select something first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				openDeleteConfirmWindow();
			}
		} else if (source == editSale) {
			if (selection == null || saleForm.getItemDataSource() == null) {
				getWindow().showNotification("Warning",
						"Please select something first!",
						Notification.TYPE_WARNING_MESSAGE);
			} else {
				saleForm.setReadOnly(false);
			}
		} else if (source == saveSale) {
			if (!saleForm.isValid()) {
				getWindow().showNotification("Update Error",
						"Are all the required fields filled out?",
						Notification.TYPE_WARNING_MESSAGE);
				return;
			}
			saleForm.commit();

			if(newSaleMode){
				createSale();
			}else{
				updateSale();
			}

			saleForm.setReadOnly(true);
			setNewSaleMode(false);
		} else if (source == newSale) {
			setNewSaleMode(true);
			Sale saleToAdd = new Sale();
					
			saleForm.setReadOnly(false);
			changeCurrentSelection(saleToAdd);
		}

	}

	public boolean isNewSaleMode() {
		return newSaleMode;
	}

	public void setNewSaleMode(boolean newSaleMode) {
		this.newSaleMode = newSaleMode;
		saleForm.setNewSaleMode(newSaleMode);
	}

	public SaleList getSaleList() {
		return saleList;
	}

	public void setSaleList(SaleList saleList) {
		this.saleList = saleList;
	}

	public SaleForm getSaleForm() {
		return saleForm;
	}

	public void setSaleForm(SaleForm saleForm) {
		this.saleForm = saleForm;
	}

	public Sale getSelection() {
		return selection;
	}

	public void setSelection(Sale selection) {
		this.selection = selection;
	}
	
	

}
