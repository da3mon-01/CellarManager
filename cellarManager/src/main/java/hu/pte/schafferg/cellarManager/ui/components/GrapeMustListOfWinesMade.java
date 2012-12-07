package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.model.Wine;
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

public class GrapeMustListOfWinesMade extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2048625883941004055L;
	@Autowired
	private GrapeMustService mustService;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private GrapeMust must;
	private static Logger logger = Logger.getLogger(GrapeMustListOfWinesMade.class);
	private Object[] listOfVisibleColumns = new Object[]{"obiNumber", "alcoholDegree", "sweetness", "bottler", "numOfBottles"};
	private String[] listOfColumnHeaders = new String[]{"OBI Number", "Alcohol Degree", "Sweetness", "Bottled by:", "Number of Bottles"};
	private List<Wine> analytics = new ArrayList<Wine>();
	private BeanItemContainer<Wine> analyticContainer = new BeanItemContainer<Wine>(Wine.class);
	
	
	
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
		
	}
	
	private void updateTableContents() {
		analyticContainer.removeAllItems();
		analytics.clear();
		if(must != null){
		analytics = mustService.getAllWineMade(must);
		analyticContainer.addAll(analytics);
		}
		if(analyticContainer.size()!=0){
			logger.info("MustWineContainer has stuff in it. for ex: " +analyticContainer.firstItemId());
		}

	}
	
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
