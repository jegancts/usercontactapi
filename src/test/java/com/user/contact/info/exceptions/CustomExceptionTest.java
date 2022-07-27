package com.user.contact.info.exceptions;

import com.user.contact.info.controller.UserContactInfoController;
import com.user.contact.info.service.UserContactInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import java.util.NoSuchElementException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CustomExceptionTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserContactInfoService userContactInfoService;

    @Autowired
    private UserContactInfoController controller;

    @Test
    public void shouldReturn400ResponseGetCall() throws Exception {
        this.mockMvc.perform(get("/user/1L"))
                .andExpect(status().is4xxClientError());
    }


    //test  case for custom exception : UserNotFoundException
    @Test
    public void shouldThrowUserNotFoundExceptionTest() {

        when(userContactInfoService.getUserContactInfoById(1L))
                .thenThrow( new UserNotFoundException("user details not found in the database for Id : " + 1L));
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> controller.getUserContactInfoById(1L))
                .withMessageContaining("user details not found in the database for Id");
    }

    //test  case for custom exception : UserNotFoundException
    @Test
    public void shouldThrowUserNotFoundExceptionForAllUsers() {

        when(userContactInfoService.getAllUserContactInfo())
                .thenThrow( new UserNotFoundException("no user details found in the database"));
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> controller.getAllUserContactInfo())
                .withMessageContaining("no user details found in the database");
    }

    //test  case for custom exception : UserNotFoundException
    @Test
    public void shouldThrowUNoSuchElementException() {
        when(userContactInfoService.getUserContactInfoByIds("1,2,3"))
                .thenThrow( new NoSuchElementException("user details for the requested id's not present in database"));
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> controller.getUserContactInfoByIds("1,2,3"))
                .withMessageContaining("user details for the requested id's not present in database");
    }

    //test  case for custom exception : UserIdValidationException
    @Test
    public void shouldThrowUserIdValidationException() {

        when(userContactInfoService.getUserContactInfoByIds("a,b,c"))
                .thenThrow( new UserIdValidationException("please enter valid ids with comma sepereted eg: 1,2,3"));
        assertThatExceptionOfType(UserIdValidationException.class)
                .isThrownBy(() -> controller.getUserContactInfoByIds("a,b,c"))
                .withMessageContaining("please enter valid ids with comma sepereted eg: 1,2,3");
    }

}
