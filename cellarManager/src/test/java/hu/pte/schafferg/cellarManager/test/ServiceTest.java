package hu.pte.schafferg.cellarManager.test;

import javax.mail.MessagingException;

import hu.pte.schafferg.cellarManager.model.Analytic;
import hu.pte.schafferg.cellarManager.model.FieldWork;
import hu.pte.schafferg.cellarManager.model.Grape;
import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.model.Role;
import hu.pte.schafferg.cellarManager.model.Sale;
import hu.pte.schafferg.cellarManager.model.User;
import hu.pte.schafferg.cellarManager.model.Wine;
import hu.pte.schafferg.cellarManager.services.AnalyticService;
import hu.pte.schafferg.cellarManager.services.ContactsService;
import hu.pte.schafferg.cellarManager.services.FieldWorkService;
import hu.pte.schafferg.cellarManager.services.GrapeMustService;
import hu.pte.schafferg.cellarManager.services.GrapeService;
import hu.pte.schafferg.cellarManager.services.LandService;
import hu.pte.schafferg.cellarManager.services.SaleService;
import hu.pte.schafferg.cellarManager.services.UserService;
import hu.pte.schafferg.cellarManager.services.WineService;
import hu.pte.schafferg.cellarManager.util.FieldWorkType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration(locations="classpath:/hu/pte/schafferg/cellarManager/test/test-context.xml")
public class ServiceTest extends AbstractTestNGSpringContextTests{
	@Autowired
	@Qualifier("person1")
	private Person person1;
	@Autowired
	@Qualifier("person2")
	private Person person2;
	@Autowired
	private UserService userService;
	@Autowired 
	private ContactsService contactsService;
	@Autowired 
	private LandService landService;
	@Autowired 
	private AnalyticService analyticService;
	@Autowired 
	private FieldWorkService fwService;
	@Autowired 
	private GrapeMustService mustService;
	@Autowired 
	private GrapeService grapeService;
	@Autowired 
	private SaleService saleService;
	@Autowired 
	private WineService wineService;
	@Autowired
	@Qualifier("land1")
	private Land land1;
	@Autowired
	@Qualifier("land2")
	private Land land2;
	@Autowired
	private User user;
	@Autowired
	private Role role;
	@Autowired
	private FieldWork fieldwork;
	@Autowired
	@Qualifier("grape1")
	private Grape grape1;
	@Autowired
	@Qualifier("grape2")
	private Grape grape2;
	@Autowired
	private Analytic analytic;
	@Autowired
	private GrapeMust must;
	@Autowired
	private Wine wine;
	@Autowired
	private Sale sale;
	@Autowired
	private MongoTemplate mongotemplate;
	
	
	@Test
	public void test() throws MessagingException {
		
		mongotemplate.dropCollection("user");
		mongotemplate.dropCollection("role");
		mongotemplate.dropCollection("person");
		mongotemplate.dropCollection("land");
		mongotemplate.dropCollection("fieldWork");
		mongotemplate.dropCollection("grape");
		mongotemplate.dropCollection("grapeMust");
		mongotemplate.dropCollection("analytic");
		mongotemplate.dropCollection("wine");
		mongotemplate.dropCollection("sale");
		
		User u;
		Person p;
		Land l1, l2;
		FieldWork f;
		Grape g1, g2;
		GrapeMust m;
		Wine w;
		Sale s;
		Analytic a;
		
		// Create Test
		
		u = userService.create(user);
		Assert.assertEquals(u, user);
		
		p = contactsService.create(person2);
		Assert.assertEquals(p, person2);
		
		l1 = landService.create(land1);
		Assert.assertEquals(l1, land1);
		
		l2 = landService.create(land2);
		Assert.assertEquals(l2, land2);
		
		f = fwService.create(fieldwork);
		Assert.assertEquals(f, fieldwork);
		
		g1 = grapeService.create(grape1);
		Assert.assertEquals(g1, grape1);
		
		g2 = grapeService.create(grape2);
		Assert.assertEquals(g2, grape2);
		
		m= mustService.create(must);
		Assert.assertEquals(m, must);
		
		a = analyticService.create(analytic);
		Assert.assertEquals(a, analytic);
		
		w = wineService.create(wine);
		Assert.assertEquals(w, wine);
		
		s = saleService.create(sale);
		Assert.assertEquals(s, sale);
		
		//UPDATE Test
		
		user.setPassword("sdasda");
		u = userService.update(user);
		Assert.assertEquals(u, user);
		
		person2.setAddress("asdadas");
		p = contactsService.update(person2);
		Assert.assertEquals(p, person2);
		
		land1.setLandOffId("sdass");
		l1 = landService.update(land1);
		Assert.assertEquals(l1, land1);
		
		land2.setLandOffId("sdassdasas");
		l2 = landService.update(land2);
		Assert.assertEquals(l2, land2);
		
		fieldwork.setWork(FieldWorkType.BINDING);
		f = fwService.update(fieldwork);
		Assert.assertEquals(f, fieldwork);
		
		grape1.setQuantity(24);
		g1 = grapeService.update(grape1);
		Assert.assertEquals(g1, grape1);
		
		grape2.setQuantity(5);
		g2 = grapeService.update(grape2);
		Assert.assertEquals(g2, grape2);
		
		must.setEnrichmentDegree(486);
		m= mustService.update(must);
		Assert.assertEquals(m, must);
		
		wine.setNumOfBottles(5);
		w = wineService.update(wine);
		Assert.assertEquals(w, wine);
		
		sale.setNumOfBottles(2);
		s = saleService.update(sale);
		Assert.assertEquals(s, sale);
		
		analytic.setExtract(15);
		a = analyticService.update(analytic);
		Assert.assertEquals(a, analytic);
		
		//Query Tests
		
		Assert.assertEquals(userService.readAll().size(), 1);
		Assert.assertEquals(contactsService.readAll().size(), 2);
		Assert.assertEquals(landService.readAll().size(), 2);
		Assert.assertEquals(fwService.readAll().size(), 1);
		Assert.assertEquals(mustService.readAll().size(), 1);
		Assert.assertEquals(grapeService.readAll().size(), 2);
		Assert.assertEquals(analyticService.readAll().size(), 1);
		Assert.assertEquals(wineService.readAll().size(), 1);
		Assert.assertEquals(saleService.readAll().size(), 1);
		
		Assert.assertEquals(contactsService.getAllFieldWorkDone(person1).size(), 1);
		Assert.assertEquals(contactsService.getAllFieldWorkDone(person2).size(), 0);
		
		Assert.assertEquals(contactsService.getAllLandsOwned(person1).size(), 1);
		Assert.assertEquals(contactsService.getAllLandsOwned(person2).size(), 1);
		
		Assert.assertEquals(contactsService.getAllSales(person1).size(), 0);
		Assert.assertEquals(contactsService.getAllSales(person2).size(), 1);
		
		Assert.assertEquals(contactsService.getAllWinesBottled(person1).size(), 1);
		Assert.assertEquals(contactsService.getAllWinesBottled(person2).size(), 0);
		
		Assert.assertEquals(mustService.getAllAnalytics(must).size(), 1);
		Assert.assertEquals(mustService.getAllWineMade(must).size(), 1);
		
		Assert.assertEquals(landService.readAllFieldWorkDone(land1).size(), 1);
		Assert.assertEquals(landService.readAllPlanetOn(land1).size(), 1);
		Assert.assertEquals(landService.readAllPlanetOn(land2).size(), 1);
		
		Assert.assertEquals(wineService.getAllSales(wine).size(), 1);
		
		
		// Delete tests
		
		saleService.delete(sale);
		wineService.delete(wine);
		analyticService.delete(analytic);
		mustService.delete(must);
		grapeService.delete(grape1);
		grapeService.delete(grape2);
		fwService.delete(fieldwork);
		landService.delete(land1);
		landService.delete(land2);
		userService.delete(user);
		contactsService.delete(person2);
		
		Assert.assertEquals(userService.readAll().size(), 0);
		Assert.assertEquals(contactsService.readAll().size(), 0);
		Assert.assertEquals(landService.readAll().size(), 0);
		Assert.assertEquals(fwService.readAll().size(), 0);
		Assert.assertEquals(mustService.readAll().size(), 0);
		Assert.assertEquals(grapeService.readAll().size(), 0);
		Assert.assertEquals(analyticService.readAll().size(), 0);
		Assert.assertEquals(wineService.readAll().size(), 0);
		Assert.assertEquals(saleService.readAll().size(), 0);
		
		
		
	}
}
