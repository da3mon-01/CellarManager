package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Sale;
import hu.pte.schafferg.cellarManager.services.SaleService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Table;

public class SaleList extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2236611665177416684L;
	@Autowired
	private SaleService saleService;
	private static Logger logger = Logger.getLogger(WineList.class);
	private Object[] listOfVisibleColumns = new Object[]{"toWho", "what", "numOfBottles", "wineDocID"};
	private String[] listOfColumnHeaders = new String[]{"Sold to", "Item sold", "Number of bottles sold", "Wine Documentation ID"};
	private List<Sale> sales = new ArrayList<Sale>();
	private BeanItemContainer<Sale> saleContainer = new BeanItemContainer<Sale>(Sale.class);
	
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
		
		
	}
	
	public void create(Sale s) throws RuntimeException{
		try {
			saleService.create(s);
		} catch (RuntimeException e) {
			throw e;
		}finally {
			updateTableContents();
		}
	}
	
	public void update(Sale s) throws RuntimeException{
		try {
			saleService.update(s);
		} catch (Exception e) {
			throw e;
		}finally{
			updateTableContents();
		}
	}
	
	public void delete(Sale s) throws RuntimeException {
		try {
			saleService.delete(s);
		} catch (Exception e) {
			throw e;
		} finally {
			updateTableContents();
		}
	}

	private void updateTableContents() {
		sales.clear();
		saleContainer.removeAllItems();
		sales = saleService.readAll();
		
		saleContainer.addAll(sales);
		
		if(saleContainer.size() != 0){
			logger.info("Lands Container has stuff in it.");
		}
		
	}
	
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

	public SaleService getSaleService() {
		return saleService;
	}

	public void setSaleService(SaleService saleService) {
		this.saleService = saleService;
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
