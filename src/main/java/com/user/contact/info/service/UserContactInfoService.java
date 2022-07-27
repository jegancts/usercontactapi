package com.user.contact.info.service;

import com.user.contact.info.dto.UserInfoDto;
import com.user.contact.info.entity.UserContactInfo;
import org.springframework.http.ResponseEntity;

public interface UserContactInfoService {
    ResponseEntity saveUserContactInfo(UserContactInfo userContractInfo);
    ResponseEntity getUserContactInfoById(long id);
    ResponseEntity deleteUserContactInfoById(long id);

    ResponseEntity deleteAllUserContactInfo();

    ResponseEntity getUserContactInfoByIds(String ids);
    ResponseEntity getAllUserContactInfo();
    ResponseEntity updateUserContactInfoById(long id, UserInfoDto userInfoDto);
}
