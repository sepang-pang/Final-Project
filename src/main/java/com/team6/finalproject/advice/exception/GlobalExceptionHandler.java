package com.team6.finalproject.advice.exception;

import com.team6.finalproject.advice.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<RestApiException> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    // 특정 작업을 수행할 권한이 없을 때
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<RestApiException> accessDeniedException(AccessDeniedException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    // 존재하지 않는 상황
    @ExceptionHandler({NotExistResourceException.class})
    public ResponseEntity<RestApiException> notExistResourceException(NotExistResourceException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    // 본인의 게시글/댓글 에 좋아요 불가
    @ExceptionHandler({SelfLikeNotAllowedException.class})
    public ResponseEntity<RestApiException> selfLikeNotAllowedException(SelfLikeNotAllowedException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    // 좋아요를 누르지 않았을 때
    @ExceptionHandler({NotLikedYetException.class})
    public ResponseEntity<RestApiException> notLikedYetException(NotLikedYetException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    // 본인의 댓글/게시글/좋아요 가 아닐 때
    @ExceptionHandler({NotOwnedByUserException.class})
    public ResponseEntity<RestApiException> notOwnedByUserException(NotOwnedByUserException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    // 이름 중복
    @ExceptionHandler({DuplicateNameException.class})
    public ResponseEntity<RestApiException> duplicateException(DuplicateNameException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    // 중복되는 행위
    @ExceptionHandler({DuplicateActionException.class})
    public ResponseEntity<RestApiException> duplicateActionException(DuplicateActionException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    // 정원이 가득 찼을 때
    @ExceptionHandler({CapacityFullException.class})
    public ResponseEntity<RestApiException> cuplicateActionException(CapacityFullException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    // 나이 범위가 잘못되었을 때
    @ExceptionHandler({InvalidAgeRangeException.class})
    public ResponseEntity<RestApiException> invalidAgeRangeException(InvalidAgeRangeException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }

}
