package com.epam.spring.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.service.IAuditoriumService;

@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
public class AuditoriumServiceTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private IAuditoriumService auditoriumService;
	
	//expected bunch of auditorium
	private Collection<Auditorium> expectedBunchOfAuditoriums;
	
	//expected auditorium
	private Auditorium smallAuditorium;
	private Auditorium largeAuditorium;
	
	//expected attribute of small auditorium
	private final static String NAME_OF_SMALL_AUDITORIUM = "small";
	private final static int NUMBER_OF_SEATS_SMALL_AUDITORIUM = 50;
	private final static String VIP_SEATS_OF_SMALL_AUDITORIUM = "5,6,7";
	
	//expected attribute of large auditorium
	private final static String NAME_OF_LARGE_AUDITORIUM = "large";
	private final static int NUMBER_OF_SEATS_LARGE_AUDITORIUM = 60;
	private final static String VIP_SEATS_OF_LARGE_AUDITORIUM = "5,6,7,8,9,10";
	
	@BeforeClass
	public void initAuditoriumsTest() {
		smallAuditorium = new Auditorium();
		smallAuditorium.setName(NAME_OF_SMALL_AUDITORIUM);
		smallAuditorium.setNumberOfSeats(NUMBER_OF_SEATS_SMALL_AUDITORIUM);
		smallAuditorium.setVipSeats(getVipSeats(VIP_SEATS_OF_SMALL_AUDITORIUM));
		
		largeAuditorium = new Auditorium();
		largeAuditorium.setName(NAME_OF_LARGE_AUDITORIUM);
		largeAuditorium.setNumberOfSeats(NUMBER_OF_SEATS_LARGE_AUDITORIUM);
		largeAuditorium.setVipSeats(getVipSeats(VIP_SEATS_OF_LARGE_AUDITORIUM));
	}
	
	@BeforeClass(dependsOnMethods = "initAuditoriumsTest")
	public void initBunchOfAuditoriumsTest() {
		expectedBunchOfAuditoriums = new ArrayList<>();
		expectedBunchOfAuditoriums.add(smallAuditorium);
		expectedBunchOfAuditoriums.add(largeAuditorium);
	}
	
	@Test(description = "getAll()")
	public void getAllAuditoriumTest() {
		Collection<Auditorium> bunchOfAuditorium = auditoriumService.getAll();
		Assert.assertTrue(bunchOfAuditorium.size() == expectedBunchOfAuditoriums.size());		
	}
	
	@Test(description = "getByName()")
	public void getByName() {
		Auditorium auditoriumSmallActual = auditoriumService.getByName(NAME_OF_SMALL_AUDITORIUM);
		Auditorium auditoriumLargeActual = auditoriumService.getByName(NAME_OF_LARGE_AUDITORIUM);
		Assert.assertEquals(auditoriumSmallActual, smallAuditorium);
		Assert.assertEquals(auditoriumLargeActual, largeAuditorium);
	}
	
	private Set<Long> getVipSeats(String vipSeatsOfSmallAuditorium) {
		Set<Long> bunchOfVipSeats = new HashSet<>();
		List<String> vipSeats = Arrays.asList(vipSeatsOfSmallAuditorium.split(","));
		for (String vipSeat : vipSeats) {
			bunchOfVipSeats.add(Long.valueOf(vipSeat.trim()));
		}
		return bunchOfVipSeats;
	}
	
}
