package com.epam.spring.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.spring.core.constants.TestConstants;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IUserService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserServiceTest extends AbstractTestNGSpringContextTests {

	private static final Long TEST_ID = 1l;
	private static final String TEST_USER_FIRST_NAME = "Aleh";
	private static final String TEST_USER_LAST_NAME = "Struneuski";
	private static final String TEST_USER_EMAIL = "aleh_struneuski@epam.com";
	
	@Autowired
	private IUserService userService;
	private User expectedUser;
	
	@BeforeClass
	public void initTest() {
		expectedUser = new User();
		expectedUser.setId(TEST_ID);
		expectedUser.setFirstName(TEST_USER_FIRST_NAME);
		expectedUser.setLastName(TEST_USER_LAST_NAME);
		expectedUser.setEmail(TEST_USER_EMAIL);
	}
	
	@Test(groups = TestConstants.GROUP_USER_SAVE, description = "save() and getById()")
	public void userServiceSaveTest(){
		userService.save(expectedUser);		

		Assert.assertTrue(userService.getAll().size() == 1);
		Assert.assertEquals(expectedUser, userService.getById(TEST_ID));
	}
		
}
