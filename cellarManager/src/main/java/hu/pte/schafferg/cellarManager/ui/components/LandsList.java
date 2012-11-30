package hu.pte.schafferg.cellarManager.ui.components;

import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.services.LandService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;

public class LandsList extends Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6413417016234798538L;
	@Autowired
	LandService landService;
	private static Logger logger = Logger.getLogger(LandsList.class);
	private Object[] listOfVisibleColumns = new Object[]{"landOff", "landOffId", "size", "owner.firstName", "owner.lastName", "owner.phoneNumber", "owner.email"};
	private String[] listOfColumnHeaders = new String[]{"County Land Office", "Land Office ID", "Size [m2]", "Owner First Name", "Owner Last name","Owner Phone Number", "Owner E-mail"};
	private List<Land> lands = new ArrayList<Land>();
	private BeanItemContainer<Land> landsContainer = new BeanItemContainer<Land>(Land.class);
	
	public void initContent(){
		logger.info("InitContent Called");
		
		landsContainer.addNestedContainerProperty("owner.firstName");
		landsContainer.addNestedContainerProperty("owner.lastName");
		landsContainer.addNestedContainerProperty("owner.phoneNumber");
		landsContainer.addNestedContainerProperty("owner.email");
		updateTableContents();
		
		addStyleName("striped");
		
		setContainerDataSource(landsContainer);
		setSelectable(true);
		setNullSelectionAllowed(false);
		
		
		setWidth("100%");
		setImmediate(true);
		
		addGeneratedColumn("owner.email", new ColumnGenerator() {


			/**
			 * 
			 */
			private static final long serialVersionUID = 7485988415669293270L;

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				Land item = (Land)itemId;
				Link link = new Link();
				link.setResource(new ExternalResource("mailto:"+item.getOwner().getEmail()));
				link.setCaption(item.getOwner().getEmail());
				return link;
			}
		});
		
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);
		
	}
	
	public void create(Land l) throws RuntimeException{
		try {
			landService.create(l);
		} catch (RuntimeException e) {
			throw e;
		}finally {
			updateTableContents();
		}
	}
	
	public void update(Land l) throws RuntimeException{
		try {
			landService.update(l);
		} catch (Exception e) {
			throw e;
		}finally{
			updateTableContents();
		}
	}
	
	public void delete(Land l) throws RuntimeException {
		try {
			landService.delete(l);
		} catch (Exception e) {
			throw e;
		} finally {
			updateTableContents();
		}
	}

	private void updateTableContents() {
		lands.clear();
		landsContainer.removeAllItems();
		lands = landService.readAll();
		
		landsContainer.addAll(lands);
		
		if(landsContainer.size() != 0){
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

	public LandService getLandService() {
		return landService;
	}

	public void setLandService(LandService landService) {
		this.landService = landService;
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

	public List<Land> getLands() {
		return lands;
	}

	public void setLands(List<Land> lands) {
		this.lands = lands;
	}

	public BeanItemContainer<Land> getLandsContainer() {
		return landsContainer;
	}

	public void setLandsContainer(BeanItemContainer<Land> landsContainer) {
		this.landsContainer = landsContainer;
	}
	
	

}
