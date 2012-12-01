package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Grape;
import hu.pte.schafferg.cellarManager.services.GrapeService;

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

public class GrapeList extends Table{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6376446782988157087L;
	@Autowired
	private SimpleDateFormat sdf;
	@Autowired
	GrapeService grapeService;
	private static Logger logger = Logger.getLogger(GrapeList.class);
	private Object[] listOfVisibleColumns = new Object[]{"type","plantedOn.landOff", "plantedOn.landOffId", "planted", "quantity"};
	private String[] listOfColumnHeaders = new String[]{"Type", "County Land Office", "Land Office ID", "Date planted", "Quantity [num of plants]"};
	private List<Grape> grapes = new ArrayList<Grape>();
	private BeanItemContainer<Grape> grapesContainer = new BeanItemContainer<Grape>(Grape.class);
	private Object[] ordering = new Object[]{"planted"};
	private boolean[] ascending = new boolean[]{false};
	
	public void initContent(){
		logger.info("InitContent Called");
		
		grapesContainer.addNestedContainerProperty("plantedOn.landOff");
		grapesContainer.addNestedContainerProperty("plantedOn.landOffId");
		
		updateTableContents();
		
		addStyleName("striped");
		
		setContainerDataSource(grapesContainer);
		setSelectable(true);
		setNullSelectionAllowed(false);
		
		
		setWidth("100%");
		setImmediate(true);
		
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);
		
		sort(ordering, ascending);
		
	}
	
	public void create(Grape g) throws RuntimeException{
		try {
			grapeService.create(g);
		} catch (RuntimeException e) {
			throw e;
		}finally {
			updateTableContents();
		}
	}
	
	public void update(Grape g) throws RuntimeException{
		try {
			grapeService.update(g);
		} catch (Exception e) {
			throw e;
		}finally{
			updateTableContents();
		}
	}
	
	public void delete(Grape g) throws RuntimeException {
		try {
			grapeService.delete(g);
		} catch (Exception e) {
			throw e;
		} finally {
			updateTableContents();
		}
	}

	private void updateTableContents() {
		grapes.clear();
		grapesContainer.removeAllItems();
		grapes = grapeService.readAll();
		
		grapesContainer.addAll(grapes);
		
		if(grapesContainer.size() != 0){
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
	protected String formatPropertyValue(Object rowId, Object colId,
			Property property) {
		if(property.getType() == Date.class){
			return sdf.format((Date)property.getValue());			
		}
		return super.formatPropertyValue(rowId, colId, property);
	}
	

	@Override
	public void attach() {
		super.attach();
		updateTableContents();
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public GrapeService getGrapeService() {
		return grapeService;
	}

	public void setGrapeService(GrapeService grapeService) {
		this.grapeService = grapeService;
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

	public List<Grape> getGrapes() {
		return grapes;
	}

	public void setGrapes(List<Grape> grapes) {
		this.grapes = grapes;
	}

	public BeanItemContainer<Grape> getGrapesContainer() {
		return grapesContainer;
	}

	public void setGrapesContainer(BeanItemContainer<Grape> grapesContainer) {
		this.grapesContainer = grapesContainer;
	}
	
	
}
