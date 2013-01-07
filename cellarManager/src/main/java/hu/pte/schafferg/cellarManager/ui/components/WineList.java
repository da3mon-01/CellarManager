package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Wine;
import hu.pte.schafferg.cellarManager.services.WineService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Table;

/**
 * Table of all Wines
 * @author Da3mon
 *
 */
public class WineList extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7094389723008747107L;
	@Autowired
	private WineService wineService;
	private static Logger logger = Logger.getLogger(WineList.class);
	private Object[] listOfVisibleColumns = new Object[]{"obiNumber", "madeFrom", "alcoholDegree", "sweetness", "bottler", "numOfBottles"};
	private String[] listOfColumnHeaders = new String[]{"OBI Number", "Made from", "Alcohol Degree", "Sweetness", "Bottled by:", "Number of Bottles"};
	private List<Wine> wines = new ArrayList<Wine>();
	private BeanItemContainer<Wine> wineContainer = new BeanItemContainer<Wine>(Wine.class);
	
	/**
	 * Builds the GUI
	 */
	public void initContent(){
		logger.info("InitContent Called");
		
		
		updateTableContents();
		
		addStyleName("striped");
		
		setContainerDataSource(wineContainer);
		setSelectable(true);
		setNullSelectionAllowed(false);
		
		
		setWidth("100%");
		setImmediate(true);
		
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);
		
		
	}
	
	/**
	 * Calls the service to create w
	 * @param w
	 * @throws RuntimeException
	 */
	public void create(Wine w) throws RuntimeException{
		try {
			wineService.create(w);
		} catch (RuntimeException e) {
			throw e;
		}finally {
			updateTableContents();
		}
	}
	
	/**
	 * Calls the service to update w
	 * @param w
	 * @throws RuntimeException
	 */
	public void update(Wine w) throws RuntimeException{
		try {
			wineService.update(w);
		} catch (Exception e) {
			throw e;
		}finally{
			updateTableContents();
		}
	}
	
	/**
	 * Calls the service to delete w
	 * @param w
	 * @throws RuntimeException
	 */
	public void delete(Wine w) throws RuntimeException {
		try {
			wineService.delete(w);
		} catch (Exception e) {
			throw e;
		} finally {
			updateTableContents();
		}
	}

	/**
	 * Updates the tables contents.
	 */
	private void updateTableContents() {
		wines.clear();
		wineContainer.removeAllItems();
		wines = wineService.readAll();
		
		wineContainer.addAll(wines);
		
		if(wineContainer.size() != 0){
			logger.info("Lands Container has stuff in it.");
		}
		
	}
	
	/**
	 * Adds a filter
	 * @param filter
	 */
	public void addFilter(SimpleStringFilter filter){
		Filterable f = (Filterable)getContainerDataSource();
		if(filter != null){
			f.removeAllContainerFilters();
		}
		
		f.addContainerFilter(filter);
		logger.info("Filter added: "+filter.getFilterString()+" in "+filter.getPropertyId());
		
	}
	
	@Override
	public void attach() {
		super.attach();
		updateTableContents();
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
