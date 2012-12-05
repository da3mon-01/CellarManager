package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.services.GrapeMustService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Table;

public class GrapeMustList extends Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1346709779366819317L;

	@Autowired
	private GrapeMustService grapeMustService;
	private static Logger logger = Logger.getLogger(GrapeMustList.class);
	private Object[] listOfVisibleColumns = new Object[]{"madeFrom.type", "quantity", "quantityAfterHarvest", "quantityLostAfterRacking", "mustDegree", "enriched", "enrichmentDegree", "sweetened"};
	private String[] listOfColumnHeaders = new String[]{"Type", "Quantity", "Quantity after harvest[L]", "Quantity Lost after racking[L]", "Must Degree[°]", "Enriched?", "Enrichment Degree[°]", "Sweetened?"};
	private List<GrapeMust> musts = new ArrayList<GrapeMust>();
	private BeanItemContainer<GrapeMust> mustsContainer = new BeanItemContainer<GrapeMust>(GrapeMust.class);
	
	public void initContent(){
		logger.info("InitContent Called");
		
		mustsContainer.addNestedContainerProperty("madeFrom.type");
		
		updateTableContents();
		
		addStyleName("striped");
		this.addContainerProperty("quantity", Double.class, 0);
		addGeneratedColumn("quantity", new ColumnGenerator() {
			

			/**
			 * 
			 */
			private static final long serialVersionUID = -3082963343481593549L;

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				GrapeMust item = (GrapeMust)itemId;
				return item.getQuantityAfterHarvest() - item.getQuantityLostAfterRacking();
			}
		});
		
		addGeneratedColumn("enriched", new ColumnGenerator() {
						/**
			 * 
			 */
			private static final long serialVersionUID = 8026104571366563149L;

						@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				GrapeMust item = (GrapeMust)itemId;
				if(item.isEnriched()){
					Embedded e = new Embedded();
					e.setHeight("15px");
					e.setSource(new ThemeResource("icons/pipa.png"));
					return e;
				}
				return null;
			}
		});
		
		addGeneratedColumn("sweetened", new ColumnGenerator() {
			

			/**
			 * 
			 */
			private static final long serialVersionUID = -5343302737167006113L;

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				GrapeMust item = (GrapeMust)itemId;
				if(item.isSweetened()){
					Embedded e = new Embedded();
					e.setHeight("15px");
					e.setSource(new ThemeResource("icons/pipa.png"));
					return e;
				}
				return null;
			}
		});
		
		setContainerDataSource(mustsContainer);
		setSelectable(true);
		setNullSelectionAllowed(false);
		
		
		setWidth("100%");
		setImmediate(true);
		
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);
		
		
	}
	
	public void create(GrapeMust g) throws RuntimeException{
		try {
			grapeMustService.create(g);
		} catch (RuntimeException e) {
			throw e;
		}finally {
			updateTableContents();
		}
	}
	
	public void update(GrapeMust g) throws RuntimeException{
		try {
			grapeMustService.update(g);
		} catch (Exception e) {
			throw e;
		}finally{
			updateTableContents();
		}
	}
	
	public void delete(GrapeMust g) throws RuntimeException {
		try {
			grapeMustService.delete(g);
		} catch (Exception e) {
			throw e;
		} finally {
			updateTableContents();
		}
	}

	private void updateTableContents() {
		musts.clear();
		mustsContainer.removeAllItems();
		musts = grapeMustService.readAll();
		
		mustsContainer.addAll(musts);
		
		if(mustsContainer.size() != 0){
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

	public GrapeMustService getGrapeMustService() {
		return grapeMustService;
	}

	public void setGrapeMustService(GrapeMustService grapeMustService) {
		this.grapeMustService = grapeMustService;
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
