package com.user.contact.info.service;

import com.user.contact.info.repository.UserContactInfoRepository;
import com.user.contact.info.dto.UserInfoDto;
import com.user.contact.info.entity.UserContactInfo;
import com.user.contact.info.exceptions.UserIdValidationException;
import com.user.contact.info.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserContactInfoServiceImpl implements UserContactInfoService {

    @Autowired
    private UserContactInfoRepository userContactDetailsRepository;


    @Override
    public ResponseEntity saveUserContactInfo(UserContactInfo userContactInfo) {
        return new ResponseEntity(userContactDetailsRepository.save(userContactInfo), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity getUserContactInfoById(long id) {

        Optional<UserContactInfo> userDetails = userContactDetailsRepository.findById(id);
        if(!userDetails.isEmpty())
            return new ResponseEntity(userDetails.get(), HttpStatus.OK);
        throw new UserNotFoundException("user details not found in the database for Id : " + id);
    }

    @Override
    public ResponseEntity getAllUserContactInfo() {
        List<UserContactInfo> getAllUsers = userContactDetailsRepository.findAll();
        if (!getAllUsers.isEmpty())
            return new ResponseEntity<>(getAllUsers, HttpStatus.OK);
        throw new UserNotFoundException("no user details found in the database");
    }


    @Override
    public ResponseEntity deleteUserContactInfoById(long id) {

        UserContactInfo userContractInfo = userContactDetailsRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + id));
        userContactDetailsRepository.deleteById(id);
        return new ResponseEntity<>("User Detail has been deleted for Id : "+id,HttpStatus.OK);

    }

    @Override
    public ResponseEntity deleteAllUserContactInfo() {
        userContactDetailsRepository.deleteAll();
        return new ResponseEntity<>("All users contact information deleted",HttpStatus.OK);
    }


    @Override
    public ResponseEntity getUserContactInfoByIds(String ids) {
        Matcher matcher = Pattern.compile("[0-9]+(,[0-9]+)+").matcher(ids);
        if(!matcher.matches())
         throw new UserIdValidationException("please enter valid ids with comma sepereted eg: 1,2,3");

        List<Long> listIds = Stream.of(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
        List<UserContactInfo>  listOfUserDetails = userContactDetailsRepository.findAllById(listIds);
        if(listOfUserDetails.isEmpty())
        throw new NoSuchElementException("user details for the requested id's not present in database : " + listIds);
        else
            return new ResponseEntity<>(listOfUserDetails ,HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateUserContactInfoById(long id, UserInfoDto userInfoDto ) {

        Optional<UserContactInfo> userContactInfo = userContactDetailsRepository.findById(id);
        if(!userContactInfo.isEmpty()) {
           UserContactInfo userContactInfoUpdate = userContactInfo.get();

            userContactInfoUpdate.setFirstName(userInfoDto.getFirstName());
            userContactInfoUpdate.setLastName(userInfoDto.getLastName());
            userContactInfoUpdate.getAddress().setDoorNo(userInfoDto.getAddress().getDoorNo());
            userContactInfoUpdate.getAddress().setStreetName(userInfoDto.getAddress().getStreetName());
            userContactInfoUpdate.getAddress().setPostCode(userInfoDto.getAddress().getPostCode());
            return  new ResponseEntity(userContactDetailsRepository.save(userContactInfoUpdate), HttpStatus.OK);
        }
        else {
        throw new UserNotFoundException("user details not found in the database for Id : " + id); }
    }

}
