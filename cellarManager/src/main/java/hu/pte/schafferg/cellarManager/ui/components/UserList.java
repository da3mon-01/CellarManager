package hu.pte.schafferg.cellarManager.ui.components;



import hu.pte.schafferg.cellarManager.model.User;
import hu.pte.schafferg.cellarManager.services.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;

public class UserList extends Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = -275345091073809817L;
	private List<User> users = new ArrayList<User>();
	final BeanItemContainer<User> userContainer = new BeanItemContainer<User>(User.class);


	@Autowired
	private SimpleDateFormat simpleDateFormat;

	@Autowired
	private UserService userService;
	private Object[] listOfVisibleColumns = new Object[]{"username", "person.firstName", "person.lastName", "person.phoneNumber", "person.email", "person.birthDate", "person.city", "person.zip", "person.address"};
	private String[] listOfColumnHeaders = new String[]{"Username", "First Name", "Last Name", "Phone Number", "E-mail", "Birth Date", "City", "ZIP", "Address"};
	
	protected static Logger logger = Logger.getLogger(UserList.class);



	public UserList() {

	}

	public void initContent(){

		logger.debug("Addcomponent Called");

		updateTableContents();
		this.addStyleName("striped");
		userContainer.addNestedContainerProperty("person.firstName");
		userContainer.addNestedContainerProperty("person.lastName");
		userContainer.addNestedContainerProperty("person.phoneNumber");
		userContainer.addNestedContainerProperty("person.email");
		userContainer.addNestedContainerProperty("person.birthDate");
		userContainer.addNestedContainerProperty("person.city");
		userContainer.addNestedContainerProperty("person.zip");
		userContainer.addNestedContainerProperty("person.address");
		
		setWidth("100%");
		setImmediate(true);
		
		addGeneratedColumn("person.email", new ColumnGenerator() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -8489927179844145811L;

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				User item = (User)itemId;
				Link link = new Link();
				link.setResource(new ExternalResource("mailto:"+item.getPerson().getEmail()));
				link.setCaption(item.getPerson().getEmail());
				return link;
			}
		});
		
		setContainerDataSource(userContainer);
		setNullSelectionAllowed(false);
		setVisibleColumns(listOfVisibleColumns);
		setColumnHeaders(listOfColumnHeaders);		
		setSelectable(true);
		


	}

	private void updateTableContents() {
		userContainer.removeAllItems();
		users.clear();
		users = userService.readAll();
		userContainer.addAll(users);
		if(userContainer.size()!=0){
			logger.info("UserContainer has stuff in it. for ex: " +userContainer.firstItemId());
		}

	}
	
	public void createUser(User userToCreate) throws MessagingException{
		
		try {
			userService.create(userToCreate);
		} catch (RuntimeException e){
			throw e;
		} finally {
			updateTableContents();
		}

	}
	
	public void updateUser(User selectedUser){
		try {
			userService.update(selectedUser).equals(selectedUser);
		} catch (RuntimeException e){
			throw e;
		}finally {
			updateTableContents();
		}
		
	}

	public void deleteUser(User selectedUser) {
		try {
			userService.delete(selectedUser);
		} catch (RuntimeException e) {
			throw e;
		}finally {
			updateTableContents();
		}
		
	}
	
	public void resetPassword(User user) throws Exception{
		
		
		try {
			userService.resetPassword(user);
		} catch (RuntimeException e) {
			throw e;
		}finally {
			updateTableContents();
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
