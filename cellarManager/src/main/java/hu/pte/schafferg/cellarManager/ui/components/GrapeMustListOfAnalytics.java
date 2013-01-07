package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Analytic;
import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.services.GrapeMustService;

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
 * Table for all Analytics done on a GrapeMust
 * @author Da3mon
 *
 */
public class GrapeMustListOfAnalytics extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1619058709050938868L;
	@Autowired
	private GrapeMustService mustService;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private GrapeMust must;
	private static Logger logger = Logger.getLogger(GrapeMustListOfAnalytics.class);
	private Object[] listOfVisibleColumns = new Object[]{"when", "sulfur", "iron", "sugar", "extract"};
	private String[] listOfColumnHeaders = new String[]{"Test Date", "Sulfur [mg]", "Iron [mg]", "Sugar [mg]", "Extract [mg]"};
	private List<Analytic> analytics = new ArrayList<Analytic>();
	private BeanItemContainer<Analytic> analyticContainer = new BeanItemContainer<Analytic>(Analytic.class);
	private Object[] ordering = new Object[]{"when"};
	private boolean[] ascending = new boolean[]{false};
	
	
	/**
	 * Builds the GUI
	 */
	public void initContent(){
		logger.info("InitContent Called");
		
		updateTableContents();
		
		addStyleName("striped");
		
		setContainerDataSource(analyticContainer);
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
		analyticContainer.removeAllItems();
		analytics.clear();
		if(must != null){
		analytics = mustService.getAllAnalytics(must);
		analyticContainer.addAll(analytics);
		}
		if(analyticContainer.size()!=0){
			logger.info("MustAnalyticContainer has stuff in it. for ex: " +analyticContainer.firstItemId());
		}

	}
	
	/**
	 * Sets the GrapeMust
	 * @param must
	 */
	public void setMustData(GrapeMust must){
		this.must = must;
		logger.info(this.must+" selected");
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

	public GrapeMustService getMustService() {
		return mustService;
	}

	public void setMustService(GrapeMustService mustService) {
		this.mustService = mustService;
	}
	
	

}
