package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Sale;
import hu.pte.schafferg.cellarManager.model.Wine;
import hu.pte.schafferg.cellarManager.services.WineService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

/**
 * Table of all sales related to a Wine
 * @author Da3mon
 *
 */
public class WineSoldItems extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4258160672502230223L;
	@Autowired
	private WineService wineService;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private Wine wine;
	private static Logger logger = Logger.getLogger(ContactListOfWineBought.class);
	private Object[] listOfVisibleColumns = new Object[]{"toWho", "date", "what", "numOfBottles", "wineDocID"};
	private String[] listOfColumnHeaders = new String[]{"Buyer", "Sale Date", "Item sold", "Number of bottles sold", "Wine Documentation ID"};
	private List<Sale> sales = new ArrayList<Sale>();
	private BeanItemContainer<Sale> saleContainer = new BeanItemContainer<Sale>(Sale.class);
	private Object[] ordering = new Object[]{"date"};
	private boolean[] ascending = new boolean[]{false};
	
	/**
	 * Builds the GUI
	 */
	public void initContent(){
		logger.info("InitContent Called");
		
		updateTableContents();
		
		addStyleName("striped");
		
		setContainerDataSource(saleContainer);
		setSelectable(true);
		setNullSelectionAllowed(false);
		
		
		setWidth("100%");
		setImmediate(true);
		
		
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);
		
		sort(ordering, ascending);
		
	}
	
	/**
	 * Updates the tables contents.
	 */
	private void updateTableContents() {
		saleContainer.removeAllItems();
		sales.clear();
		if(wine != null){
		sales = wineService.getAllSales(wine);
		saleContainer.addAll(sales);
		}
		if(saleContainer.size()!=0){
			logger.info("WineSaleContainer has stuff in it. for ex: " +saleContainer.firstItemId());
		}

	}
	
	/**
	 * Sets the Wine
	 * @param wine
	 */
	public void setWineData(Wine wine){
		this.wine = wine;
		logger.info(wine+" selected");
		updateTableContents();
	}
	
	@Override
	public void attach() {
		super.attach();
		updateTableContents();
	}
	
	@Override
	protected String formatPropertyValue(Object rowId, Object colId,
			Property property) {
		if(property.getType() == Date.class){
			return simpleDateFormat.format((Date)property.getValue());			
		}
		return super.formatPropertyValue(rowId, colId, property);
	}

	public SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}

	public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}

	public WineService getWineService() {
		return wineService;
	}

	public void setWineService(WineService wineService) {
		this.wineService = wineService;
	}

	public Object[] getListOfVisibleColumns() {
		return listOfVisibleColumns;
	}

	public void setListOfVisibleColumns(Object[] listOfVisibleColumns) {
		this.listOfVisibleColumns = listOfVisibleColumns;
	}

	public String[] getListOfColumnHeaders() {
		return listOfColumnHeaders;
	}

	public void setListOfColumnHeaders(String[] listOfColumnHeaders) {
		this.listOfColumnHeaders = listOfColumnHeaders;
	}
	
	

}
