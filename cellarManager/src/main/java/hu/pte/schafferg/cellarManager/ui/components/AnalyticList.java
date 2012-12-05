package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Analytic;
import hu.pte.schafferg.cellarManager.services.AnalyticService;

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

public class AnalyticList extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3881079149977751419L;
	@Autowired
	private AnalyticService analyticService;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private static Logger logger = Logger.getLogger(AnalyticList.class);
	private Object[] listOfVisibleColumns = new Object[]{"must", "when", "sulfur", "iron", "sugar", "extract"};
	private String[] listOfColumnHeaders = new String[]{"Must Tested", "Test Date", "Sulfur [mg]", "Iron [mg]", "Sugar [mg]", "Extract [mg]"};
	private Object[] ordering = new Object[]{"when"};
	private boolean[] ascending = new boolean[]{false};
	private List<Analytic> analytics = new ArrayList<Analytic>();
	private BeanItemContainer<Analytic> analyticContainer = new BeanItemContainer<Analytic>(Analytic.class);

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
	
	public void create(Analytic a) throws RuntimeException{
		try {
			analyticService.create(a);
		} catch (RuntimeException e) {
			throw e;
		}finally {
			updateTableContents();
		}
	}
	
	public void update(Analytic a) throws RuntimeException{
		try {
			analyticService.update(a);
		} catch (Exception e) {
			throw e;
		}finally{
			updateTableContents();
		}
	}
	
	public void delete(Analytic a) throws RuntimeException {
		try {
			analyticService.delete(a);
		} catch (Exception e) {
			throw e;
		} finally {
			updateTableContents();
		}
	}

	
	private void updateTableContents() {
		analyticContainer.removeAllItems();
		analytics.clear();
		analytics = analyticService.readAll();
		analyticContainer.addAll(analytics);
		if(analyticContainer.size()!=0){
			logger.info("WorkContainer has stuff in it. for ex: " +analyticContainer.firstItemId());
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

	public AnalyticService getAnalyticService() {
		return analyticService;
	}

	public void setAnalyticService(AnalyticService analyticService) {
		this.analyticService = analyticService;
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
	
	

}
