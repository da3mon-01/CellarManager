package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Grape;
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

/**
 * Table of all Grapes planted on a Land
 * @author Da3mon
 *
 */
public class LandGrapesPlanted extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3887244002618292277L;
	@Autowired
	private LandService landService;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private Land land;
	private static Logger logger = Logger.getLogger(LandGrapesPlanted.class);
	private Object[] listOfVisibleColumns = new Object[]{"type", "planted", "quantity"};
	private String[] listOfColumnHeaders = new String[]{"Type", "Date planted", "Quantity [num of plants]"};
	private List<Grape> grapes = new ArrayList<Grape>();
	private BeanItemContainer<Grape> grapeContainer = new BeanItemContainer<Grape>(Grape.class);
	
	
	/**
	 * Builds the GUI
	 */
	public void initContent(){
		logger.info("InitContent Called");
		
		updateTableContents();
		
		addStyleName("striped");
		
		setContainerDataSource(grapeContainer);
		setSelectable(true);
		setNullSelectionAllowed(false);
		
		
		setWidth("100%");
		setImmediate(true);
		
		
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);
		
	}
	
	/**
	 * Updates the tables contents.
	 */
	private void updateTableContents() {
		grapeContainer.removeAllItems();
		grapes.clear();
		if(land != null){
		grapes = landService.readAllPlanetOn(land);
		grapeContainer.addAll(grapes);
		}
		if(grapeContainer.size()!=0){
			logger.info("LandGrapeContainer has stuff in it. for ex: " +grapeContainer.firstItemId());
		}

	}
	
	/**
	 * Sets the Land
	 * @param land
	 */
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


}
