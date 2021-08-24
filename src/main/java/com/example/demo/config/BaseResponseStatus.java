package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    SUCCESS_DELETE_USER(true, 1001, "탈퇴에 성공하였습니다."),
    SUCCESS_UPDATE_USER(true, 1002, "회원 정보 수정에 성공하였습니다."),
    SUCCESS_DELETE_PRODUCT(true, 1003, "중고 판매글 삭제에 성공하였습니다."),
    SUCCESS_UPDATE_PRODUCT(true, 1004, "중고 판매글 수정에 성공하였습니다."),
    SUCCESS_UPDATE_VIEW(true, 1005, "게시글 조회수가 증가하였습니다."),
    SUCCESS_PULL_PRODUCT(true, 1006, "중고 판매글 끌올에 성공하였습니다."),
    SUCCESS_WISH_PRODUCT(true, 1007, "중고 판매글 관심 설정에 성공하였습니다."),
    SUCCESS_REVOKE_WISH_PRODUCT(true, 1008, "중고 판매글 관심 해제에 성공하였습니다."),
    SUCCESS_UPDATE_PRODUCT_STATUS(true, 1009, "중고 판매글 상태 변경에 성공하였습니다."),
    SUCCESS_DELETE_COMMUNITY(true, 1010, "동네생활 게시글 삭제에 성공하였습니다."),
    SUCCESS_UPDATE_COMMUNITY(true, 1011, "동네생활 게시글 수정에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_PHONE_NUMBER(false, 2015, "전화번호를 입력해주세요."),
    INVALID_PHONE_NUMBER(false, 2016, "전화번호 형식을 확인해주세요."),
    POST_USERS_EXISTS_PHONE_NUMBER(false,2017,"중복된 전화번호입니다."),
    POST_USERS_EMPTY_NICKNAME(false, 2018, "닉네임을 입력해주세요."),


    // [POST] /logIn
    LOGIN_USERS_EMPTY_PHONE_NUMBER(false, 2020, "전화번호를 입력해주세요."),
    LOGIN_USERS_EMPTY_PASSWORD(false, 2021, "password를 입력해주세요."),
    LOGIN_USERS_DELETED(false, 2022, "탈퇴한 유저입니다."),
    LOGIN_USERS_NOT_JOIN(false, 2023, "가입되지 않은 전화번호입니다."),

    // [PATCH] /users
    PATCH_USERS_EMPTY_NICKNAME(false, 2030, "닉네임을 입력해주세요."),
    PATCH_USERS_EMPTY_PROFILE_URL(false, 2031, "프로필 이미지를 입력해주세요."),

    // [DELETE] /users/{userInfoIdx}
    EMPTY_CLOSING_ACCOUNT_REASON(false, 2040, "계정 삭제 이유를 선택해주세요."),

    PATCH_NOT_MATCH_USER(false, 2041, "판매글을 작성한 유저가 아닙니다."),
    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userInfoIdx}
    MODIFY_FAIL_USER(false,4014,"유저 정보 수정 실패"),
    MODIFY_FAIL_PRODUCT(false,4015,"중고 판매글 정보 수정 실패"),
    MODIFY_FAIL_COMMUNITY(false,4016,"동네생활 게시글 정보 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    //[DELETE] /users/{userInfoIdx}/delete
    DELETE_FAIL_USER(false,4020,"유저 정보 삭제 실패"),
    DELETE_FAIL_PRODUCT(false,4021,"상품 게시글 정보 삭제 실패"),

    MODIFY_FAIL_VIEW_COUNT(false,4030,"게시글 조회수 증가 실패"),
    MODIFY_FAIL_PULL_PRODUCT(false,4031,"게시글 조회수 증가 실패"),
    MODIFY_FAIL_WISH(false,4032,"게시글 관심 설정 실패");

    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
