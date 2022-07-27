package com.user.contact.info.controller;
import com.user.contact.info.dto.UserInfoDto;
import com.user.contact.info.entity.UserContactInfo;
import com.user.contact.info.service.UserContactInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@Api(tags = {"UserContactInfo"})
@SwaggerDefinition(tags = {@Tag(name = "UserContactInfoController", description = "User Contact Info REST API")})
@AllArgsConstructor
public class UserContactInfoController {

    private  static  final Logger logger = LoggerFactory.getLogger(UserContactInfoController.class);

    @Autowired
    private UserContactInfoService userContactInfoService;


    @PostMapping(value="/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveUserContractInfo(@Valid @RequestBody UserContactInfo userContractInfo){
        logger.info("User  details - {}", userContractInfo);
        return userContactInfoService.saveUserContactInfo(userContractInfo);
    }

    @GetMapping(value = "/user/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserContactInfoByIds(@RequestParam ("ids") String ids)  {
        return userContactInfoService.getUserContactInfoByIds(ids);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllUserContactInfo() {
        return userContactInfoService.getAllUserContactInfo();
    }

    @GetMapping(value="/user/{id}")
    public ResponseEntity getUserContactInfoById(@PathVariable long id) {
        return userContactInfoService.getUserContactInfoById(id);
    }

    @DeleteMapping (value="/user/{id}")
    public ResponseEntity deleteUserContactInfoById(@PathVariable long id) {
       return userContactInfoService.deleteUserContactInfoById(id);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity updateUserContactInfoById(@Valid @RequestBody UserInfoDto userInfoDto , @PathVariable long id)
    {
        return userContactInfoService.updateUserContactInfoById(id,userInfoDto);
    }

    @DeleteMapping (value="/users")
    public ResponseEntity deleteAllUserContactInfo() {
        return userContactInfoService.deleteAllUserContactInfo();
    }

}
