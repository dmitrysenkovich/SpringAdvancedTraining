package com.epam.spring.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.spring.core.app.AppConfig;
import com.epam.spring.core.constants.TestConstants;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IUserService;

@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

	private static final String TEST_USER_FIRST_NAME = "Aleh";
	private static final String TEST_USER_LAST_NAME = "Struneuski";
	private static final String TEST_USER_EMAIL = "aleh_struneuski@epam.com";
	
	@Autowired
	private IUserService userService;
	private User expectedUser;
	
	@BeforeClass
	public void initTest() {
		expectedUser = new User();
		expectedUser.setFirstName(TEST_USER_FIRST_NAME);
		expectedUser.setLastName(TEST_USER_LAST_NAME);
		expectedUser.setEmail(TEST_USER_EMAIL);
	}
	
	@Test(groups = TestConstants.GROUP_USER_SAVE, description = "save() and getById()")
	public void userServiceSaveTest(){
		User persistedUser = userService.save(expectedUser);		

		Assert.assertTrue(userService.getAll().size() == 1);
		Assert.assertEquals(expectedUser, userService.getUserByEmail(TEST_USER_EMAIL));
		Assert.assertEquals(persistedUser.getId(), userService.getUserByEmail(TEST_USER_EMAIL).getId());
	}
		
}
