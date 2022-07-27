package com.user.contact.info.handler;

import com.user.contact.info.dto.ErrorDto;
import com.user.contact.info.exceptions.UserIdValidationException;
import com.user.contact.info.exceptions.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)

    public List<String> generateUserNotFoundException(UserNotFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserIdValidationException.class)

    public ResponseEntity<ErrorDto> generateUserIDsNotFoundException(UserIdValidationException ex) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(ex.getMessage());
        errorDto.setStatus("404");
        errorDto.setTime(new Date().toString());
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDto> generateNotFoundException(NoSuchElementException ex) {
        ErrorDto errorDTO = new ErrorDto();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setStatus("404");
        errorDTO.setTime(new Date().toString());
        return new ResponseEntity<ErrorDto>(errorDTO, HttpStatus.NOT_FOUND);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public Map<String, String> handleDuplicatePhoneNo(HttpServletRequest req, DataIntegrityViolationException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Duplicate phone no ","Please enter unique phone no");
        return errors;
    }
}
