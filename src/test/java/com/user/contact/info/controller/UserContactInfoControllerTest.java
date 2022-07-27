package com.user.contact.info.controller;
import com.user.contact.info.dto.AddressDto;
import com.user.contact.info.dto.UserInfoDto;
import com.user.contact.info.entity.Address;
import com.user.contact.info.entity.UserContactInfo;
import com.user.contact.info.repository.UserContactInfoRepository;
import com.user.contact.info.service.UserContactInfoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.powermock.api.mockito.PowerMockito.doNothing;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserContactInfoControllerTest {

    @Autowired
    private UserContactInfoController controller;

    @Autowired
    private UserContactInfoService userContactInfoService;


    @MockBean
    private UserContactInfoRepository userContactInfoRepository;

    List<UserContactInfo> listOfUserContactInfo = null;
    UserContactInfo userContactInfo = null;
    UserInfoDto userInfoDto = null;

    @Before
    public void setup() {
        listOfUserContactInfo = buildUserContactInfo();
        userContactInfo = buildUserContactInfoForFindById();
        userInfoDto = buildUserInfoDto();

    }

    //get all user contact info test case
    @Test
    public void shouldReturnAllUserContactInfoTest() {
        PowerMockito.when(userContactInfoRepository.findAll()).thenReturn(listOfUserContactInfo);
        ResponseEntity responseEntity = controller.getAllUserContactInfo();
        Assert.assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        ArrayList arrayList = (ArrayList) responseEntity.getBody();
        assertThat(((ArrayList) responseEntity.getBody()).size()).isEqualTo(2);

    }

    //get user contact info by id test case
    @Test
    public void shouldReturnUserContactInfoByIdTest() {
        PowerMockito.when(userContactInfoRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(userContactInfo));
        ResponseEntity responseEntity = controller.getUserContactInfoById(1L);
        Assert.assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        UserContactInfo userContactInfo = (UserContactInfo) responseEntity.getBody();
        assertThat(userContactInfo.getFirstName()).isEqualTo("FirstName");
        assertThat(userContactInfo.getPhoneNo()).isEqualTo("1234567890");
        assertThat(userContactInfo.getAddress().getPostCode()).isEqualTo("XXZ123");

    }


    //get user contact info by ids (1,2,3 etc) test case
    @Test
    public void shouldReturnUserDetailsByIdsTest() {
        PowerMockito.when(userContactInfoRepository.findAllById(Arrays.asList(1L,2L))).thenReturn(listOfUserContactInfo);
        ResponseEntity responseEntity = controller.getUserContactInfoByIds("1,2");
        Assert.assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        ArrayList arrayList = (ArrayList) responseEntity.getBody();
        assertThat(((ArrayList) responseEntity.getBody()).size()).isEqualTo(2);

    }

    //delete user contact info by ids test case
    @Test
    public void shouldDeleteUserContactInfoByIdTest() {
        PowerMockito.when(userContactInfoRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(userContactInfo));
        doNothing().when(userContactInfoRepository).deleteById(1L);
        ResponseEntity responseEntity = controller.deleteUserContactInfoById(1L);
        Assert.assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }

    //save user contact info test case
    @Test
    public void shouldSaveUserContactInfoTest() {
        PowerMockito.when(userContactInfoRepository.save(userContactInfo)).thenReturn(userContactInfo);
        ResponseEntity responseEntity = controller.saveUserContractInfo(userContactInfo);
        Assert.assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }

    //update user contact info by id test case
    @Test
    public void shouldUpdateUserContactInfoByIdTest() {
        PowerMockito.when(userContactInfoRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(userContactInfo));
        PowerMockito.when(userContactInfoRepository.save(userContactInfo)).thenReturn(userContactInfo);
        ResponseEntity responseEntity = controller.updateUserContactInfoById(userInfoDto,1L);
        Assert.assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();

        UserContactInfo userContactInfo = (UserContactInfo) responseEntity.getBody();
        assertThat(userContactInfo.getFirstName()).isEqualTo("UpdatedFirstName");
    }


    public  List<UserContactInfo> buildUserContactInfo() {

        List<UserContactInfo> listOffUser = new ArrayList<>();
        UserContactInfo userContactInfo = new UserContactInfo();

        userContactInfo.setId(1L);
        userContactInfo.setFirstName("FirstName");
        userContactInfo.setLastName("LastName");
        userContactInfo.setPhoneNo("1234567890");
        Address address = new Address();
        address.setId(1L);
        address.setDoorNo("30A");
        address.setStreetName("Street Name");
        address.setPostCode("XXZ123");
        userContactInfo.setAddress(address);


        UserContactInfo userContractInfo1 = new UserContactInfo();
        userContractInfo1.setId(2L);
        userContractInfo1.setFirstName("FirstName1");
        userContractInfo1.setLastName("LastName1");
        userContractInfo1.setPhoneNo("1234567891");
        Address address1 = new Address();
        address1.setId(2L);
        address1.setDoorNo("30B");
        address1.setStreetName("Street Name1");
        address1.setPostCode("XXZ121");
        userContractInfo1.setAddress(address1);

        listOffUser.add(userContactInfo);
        listOffUser.add(userContractInfo1);

        return listOffUser;

    }

    public UserContactInfo buildUserContactInfoForFindById() {

        UserContactInfo userContactInfo = new UserContactInfo();
        userContactInfo.setId(1L);
        userContactInfo.setFirstName("FirstName");
        userContactInfo.setLastName("LastName");
        userContactInfo.setPhoneNo("1234567890");
        Address address = new Address();
        address.setId(1L);
        address.setDoorNo("30A");
        address.setStreetName("Street Name");
        address.setPostCode("XXZ123");
        userContactInfo.setAddress(address);

        return userContactInfo;

    }

    public  UserInfoDto buildUserInfoDto() {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setFirstName("UpdatedFirstName");
        userInfoDto.setLastName("UpdatedLastName");
        AddressDto addressDto = new AddressDto();
        addressDto.setDoorNo("U30A");
        addressDto.setStreetName("Updated Street Name");
        addressDto.setPostCode("UXXZ123");
        userInfoDto.setAddress(addressDto);
        return userInfoDto;

    }

}
