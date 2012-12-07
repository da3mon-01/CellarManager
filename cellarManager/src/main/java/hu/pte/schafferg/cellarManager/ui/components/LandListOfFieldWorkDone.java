package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.FieldWork;
import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.services.LandService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class LandListOfFieldWorkDone extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7593085198343655102L;
	@Autowired
	private LandService landService;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private Land land;
	private static Logger logger = Logger.getLogger(LandListOfFieldWorkDone.class);
	private Object[] listOfVisibleColumns = new Object[]{"who.firstName", "who.lastName", "when", "work"};
	private String[] listOfColumnHeaders = new String[]{"Worker First Name", "Worker Last name", "Date", "Type of work"};
	private List<FieldWork> works = new ArrayList<FieldWork>();
	private BeanItemContainer<FieldWork> workContainer = new BeanItemContainer<FieldWork>(FieldWork.class);
	private Object[] ordering = new Object[]{"when"};
	private boolean[] ascending = new boolean[]{false};
	
	
	public void initContent(){
		logger.info("InitContent Called");
		
		workContainer.addNestedContainerProperty("who.firstName");
		workContainer.addNestedContainerProperty("who.lastName");
		updateTableContents();
		
		addStyleName("striped");
		
		setContainerDataSource(workContainer);
		setSelectable(true);
		setNullSelectionAllowed(false);
		
		
		setWidth("100%");
		setImmediate(true);
		
		
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);
		sort(ordering, ascending);
	}
	
	private void updateTableContents() {
		workContainer.removeAllItems();
		works.clear();
		if(land != null){
		works = landService.readAllFieldWorkDone(land);
		workContainer.addAll(works);
		}
		if(workContainer.size()!=0){
			logger.info("LandWorkContainer has stuff in it. for ex: " +workContainer.firstItemId());
		}

	}
	
	public void setLandData(Land land){
		this.land = land;
		logger.info(this.land+" selected");
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

	public LandService getLandService() {
		return landService;
	}

	public void setLandService(LandService landService) {
		this.landService = landService;
	}

	public Land getLand() {
		return land;
	}

	public void setLand(Land land) {
		this.land = land;
	}

	public Object[] getOrdering() {
		return ordering;
	}

	public void setOrdering(Object[] ordering) {
		this.ordering = ordering;
	}

	

}
