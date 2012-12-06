package hu.pte.schafferg.cellarManager.services;



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
import hu.pte.schafferg.cellarManager.util.FieldWorkType;
import hu.pte.schafferg.cellarManager.util.WineSweetness;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class InitTestData {
	
	@Autowired
	MongoTemplate mongotemplate;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	StandardPasswordEncoder sde;
	String appTimeZone;
	
	
	public void init() throws ParseException{
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
		
		sdf.setTimeZone(TimeZone.getTimeZone(appTimeZone));
		
		
		Date dJohn = sdf.parse("1962-07-07");
		Role johnRole = new Role(UUID.randomUUID().toString(), 1);
		Person pJohn = new Person(UUID.randomUUID().toString(), "John", "Johnson", 7754, "Bóly", "Dasdas", "scsc@ji.hu", dJohn, "+36-20/451-1581", true);
		User john = new User(UUID.randomUUID().toString(), "john123", sde.encode("john123"), johnRole, pJohn);
		
		Date dPeter = sdf.parse("1952-01-05");
		Role peterRole = new Role(UUID.randomUUID().toString(), 1);
		Person pPeter = new Person(UUID.randomUUID().toString(), "Peter", "Petersen", 7758, "Bólyadsa", "Daadssdas", "adsadasd@adsd.hu", dPeter, "+36-20/451-1582", true);
		User peter = new User(UUID.randomUUID().toString(), "peter123", sde.encode("peter123"), peterRole, pPeter);
		
		Date dGabe = sdf.parse("1972-05-05");
		Role gabeRole = new Role(UUID.randomUUID().toString(), 2);
		Person pGabe = new Person(UUID.randomUUID().toString(), "Gabe", "Johnson", 8546, "Bsadsdóly", "Dasadasdsa", "dsda@adda.hu", dGabe, "+36-20/451-1582", true);
		User gabe = new User(UUID.randomUUID().toString(), "gabe123", sde.encode("gabe123"), gabeRole, pGabe);
		
		Date dSteve = sdf.parse("1982-10-05");
		Role steveRole = new Role(UUID.randomUUID().toString(), 2);
		Person pSteve = new Person(UUID.randomUUID().toString(), "Steve", "Johnson", 4584, "Basdsady", "Dasdas", "adssd@hu.ju", dSteve, "+36-20/451-1583", true);
		User steve = new User(UUID.randomUUID().toString(), "stv123", sde.encode("stv123"), steveRole, pSteve);
		
		Date dJani = sdf.parse("1972-02-14");
		Role janiRole = new Role(UUID.randomUUID().toString(), 2);
		Person pJani = new Person(UUID.randomUUID().toString(), "Jani", "Johnson", 1245, "Bewrerwóly", "Dadsdarewrs", "adas@ad.com", dJani, "+36-20/451-1584", true);
		User jani = new User(UUID.randomUUID().toString(), "jani123", sde.encode("jani123"), janiRole, pJani);
		
		Date dTomi = sdf.parse("1987-09-14");
		Role tomiRole = new Role(UUID.randomUUID().toString(), 2);
		Person pTomi = new Person(UUID.randomUUID().toString(), "Tom", "Johnson", 7236, "dady", "sgdgss", "dsad@adasd.hu", dTomi, "+36-20/451-1585", true);
		User tomi = new User(UUID.randomUUID().toString(), "tomi123", sde.encode("tomi123"), tomiRole, pTomi);
		
		mongotemplate.insert(pJohn, "person");
		mongotemplate.insert(pPeter, "person");
		mongotemplate.insert(pSteve, "person");
		mongotemplate.insert(pGabe, "person");
		mongotemplate.insert(pJani, "person");
		mongotemplate.insert(pTomi, "person");
		
		mongotemplate.insert(johnRole, "role");
		mongotemplate.insert(peterRole, "role");
		mongotemplate.insert(steveRole, "role");
		mongotemplate.insert(gabeRole, "role");
		mongotemplate.insert(janiRole, "role");
		mongotemplate.insert(tomiRole, "role");
		
		mongotemplate.insert(tomi, "user");
		mongotemplate.insert(jani, "user");
		mongotemplate.insert(steve, "user");
		mongotemplate.insert(gabe, "user");
		mongotemplate.insert(peter, "user");
		mongotemplate.insert(john, "user");
		
		Date dTomas = sdf.parse("1986-10-14");
		Person pTomas = new Person(UUID.randomUUID().toString(), "Tomas", "Johnsasson", 8236, "dasady", "sgdsddgss", "dsad@dasdadasd.hu", dTomas, "+36-20/451-1555", false);
		Date dPetra = sdf.parse("1986-11-14");
		Person pPetra = new Person(UUID.randomUUID().toString(), "Petra", "Johnsasson", 8236, "djkjhsady", "gjhgggss", "dsasddgrtgsd.hu", dPetra, "+36-20/451-1785", false);
		Date dJuli = sdf.parse("1985-10-14");
		Person pJuli = new Person(UUID.randomUUID().toString(), "Juli", "Johnadsson", 8236, "dasdsagrfgsady", "sgdsddgss", "dsad@kljksd.hu", dJuli, "+36-20/451-3555", false);
		
		mongotemplate.insert(pTomas, "person");
		mongotemplate.insert(pPetra, "person");
		mongotemplate.insert(pJuli, "person");
		
		Land landT = new Land(UUID.randomUUID().toString(), "458", "Bóly", 25000, pTomas);
		Land landP = new Land(UUID.randomUUID().toString(), "389", "Pécs", 50000, pPetra);
		Land landS = new Land(UUID.randomUUID().toString(), "222", "Pécs", 36000, pSteve);
		
		mongotemplate.insert(landT, "land");
		mongotemplate.insert(landP, "land");
		mongotemplate.insert(landS, "land");
		
		Date dfwJ = sdf.parse("2012-07-07");
		FieldWork fwT = new FieldWork(UUID.randomUUID().toString(), pTomas, dfwJ, FieldWorkType.INSECTSPRAY, landT);
		Date dfwS = sdf.parse("2012-06-17");
		FieldWork fwS = new FieldWork(UUID.randomUUID().toString(), pSteve, dfwS, FieldWorkType.DUNGWORK, landS);
		
		mongotemplate.insert(fwT, "fieldWork");
		mongotemplate.insert(fwS, "fieldWork");
		
		Date dGT = sdf.parse("2012-08-08");
		Grape gT = new Grape(UUID.randomUUID().toString(), "Olaszrizling", 150, dGT, landT);
		Date dGP = sdf.parse("2012-07-07");
		Grape gP = new Grape(UUID.randomUUID().toString(), "Királylányka", 185, dGP, landP);
		
		mongotemplate.insert(gT, "grape");
		mongotemplate.insert(gP, "grape");
		
		GrapeMust gmT = new GrapeMust(UUID.randomUUID().toString(), gT, 250, 15, 21,true, 3, false);
		GrapeMust gmP = new GrapeMust(UUID.randomUUID().toString(), gP, 400, 25, 20, false, 0, true);
		
		mongotemplate.insert(gmT, "grapeMust");
		mongotemplate.insert(gmP, "grapeMust");
		
		Date at1d = sdf.parse("2012-10-09");
		Analytic aT1 = new Analytic(UUID.randomUUID().toString(), at1d, 1548.0, 1458.0, 1478.0, 1748.0, gmT);
		Date at2d = sdf.parse("2012-11-09");
		Analytic aT2 = new Analytic(UUID.randomUUID().toString(), at2d, 1540.0, 1450.0, 1470.0, 1740.0, gmT);
		Date apd = sdf.parse("2012-07-09");
		Analytic aP = new Analytic(UUID.randomUUID().toString(), apd, 1369.0, 1236.0, 1852.0, 1745.0, gmP);
		
		mongotemplate.save(aT1, "analytic");
		mongotemplate.save(aT2, "analytic");
		mongotemplate.save(aP, "analytic");
		
		Wine wT = new Wine(UUID.randomUUID().toString(), "A4T59", 17.9, WineSweetness.MEDIUMDRY, gmT, pSteve, 25);
		Wine wP = new Wine(UUID.randomUUID().toString(), "B5C5U4", 20.4, WineSweetness.SWEET, gmP, pGabe, 15);
		
		mongotemplate.save(wT, "wine");
		mongotemplate.save(wP, "wine");
		
		Sale sT1 = new Sale(UUID.randomUUID().toString(), pPetra, wT, 10, "ALOK489");
		Sale sT2 = new Sale(UUID.randomUUID().toString(), pJuli, wT, 5, "A89IOP");
		Sale sW1 = new Sale(UUID.randomUUID().toString(), pTomas, wP, 16, "A48JK9");
		Sale sW2 = new Sale(UUID.randomUUID().toString(), pJani, wP, 9, "BZ954T");
		
		mongotemplate.save(sT1, "sale");
		mongotemplate.save(sT2, "sale");
		mongotemplate.save(sW1, "sale");
		mongotemplate.save(sW2, "sale");
	}


	public MongoTemplate getMongotemplate() {
		return mongotemplate;
	}


	public void setMongotemplate(MongoTemplate mongotemplate) {
		this.mongotemplate = mongotemplate;
	}


	public SimpleDateFormat getSdf() {
		return sdf;
	}


	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}


	public String getAppTimeZone() {
		return appTimeZone;
	}


	public void setAppTimeZone(String appTimeZone) {
		this.appTimeZone = appTimeZone;
	}
	
	
	
	

}
