package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.FieldWork;
import hu.pte.schafferg.cellarManager.services.FieldWorkService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Table;

public class FieldWorkList extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8876930119158294171L;
	@Autowired
	private FieldWorkService fieldWorkService;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private static Logger logger = Logger.getLogger(FieldWorkList.class);
	private Object[] listOfVisibleColumns = new Object[]{"who.firstName", "who.lastName", "when", "onWhat.landOff", "onWhat.landOffId", "work"};
	private String[] listOfColumnHeaders = new String[]{"Worker First Name", "Worker Last name", "Date", "County Land Office", "Land Office ID", "Type of work"};
	private List<FieldWork> works = new ArrayList<FieldWork>();
	private BeanItemContainer<FieldWork> workContainer = new BeanItemContainer<FieldWork>(FieldWork.class);

	public void initContent(){
		logger.info("InitContent Called");
		
		workContainer.addNestedContainerProperty("who.firstName");
		workContainer.addNestedContainerProperty("who.lastName");
		workContainer.addNestedContainerProperty("onWhat.landOff");
		workContainer.addNestedContainerProperty("onWhat.landOffId");
		updateTableContents();
		
		addStyleName("striped");
		
		setContainerDataSource(workContainer);
		setSelectable(true);
		setNullSelectionAllowed(false);
		
		
		setWidth("100%");
		setImmediate(true);
		
		
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);
		
	}
	
	public void create(FieldWork f) throws RuntimeException{
		try {
			fieldWorkService.create(f);
		} catch (RuntimeException e) {
			throw e;
		}finally {
			updateTableContents();
		}
	}
	
	public void update(FieldWork f) throws RuntimeException{
		try {
			fieldWorkService.update(f);
		} catch (Exception e) {
			throw e;
		}finally{
			updateTableContents();
		}
	}
	
	public void delete(FieldWork f) throws RuntimeException {
		try {
			fieldWorkService.delete(f);
		} catch (Exception e) {
			throw e;
		} finally {
			updateTableContents();
		}
	}

	
	private void updateTableContents() {
		workContainer.removeAllItems();
		works.clear();
		works = fieldWorkService.readAll();
		workContainer.addAll(works);
		if(workContainer.size()!=0){
			logger.info("WorkContainer has stuff in it. for ex: " +workContainer.firstItemId());
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
	protected String formatPropertyValue(Object rowId, Object colId,
			Property property) {
		if(property.getType() == Date.class){
			return simpleDateFormat.format((Date)property.getValue());			
		}
		return super.formatPropertyValue(rowId, colId, property);
	}
	
	@Override
	public void attach() {
		super.attach();
		updateTableContents();
	}

	public FieldWorkService getFieldWorkService() {
		return fieldWorkService;
	}

	public void setFieldWorkService(FieldWorkService fieldWorkService) {
		this.fieldWorkService = fieldWorkService;
	}

	public SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}

	public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		FieldWorkList.logger = logger;
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
