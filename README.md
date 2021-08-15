# 라프3 라이징테스트 당근마켓 B

## 💡 Introduction

> 라이징 프로그래머 3 교육 프로그램에서 진행하는 실전 프로젝트에서 개발한 당근마켓 클론 버전의 개발 일지입니다. 
B팀의 서버 개발자로 참여했습니다.

## 🖋 Planning
### 2021-08-14 회의
https://docs.google.com/document/d/1iK6TaV5BXOXLn7x_N5avh8IvKkrzLpgXpGJPgib4gC4/edit?usp=sharing

클라이언트 개발자와의 기획 회의에 따라 프로젝트 진행 기간인 2주간 유저 회원가입 및 로그인, 상품 조회 및 등록, 검색, 카테고리, 채팅, 동네인증 등의 기능을 개발키로 했습니다.
1주차 피드백(2021.08.17)까지 작업할 기능은 다음과 같습니다.
+ AWS EC2 인스턴스 구축
+ AWS RDS 데이터베이스 구축
+ API 명세서 설계
+ ERD 설계
+ dev/prod 서브도메인 서버 구축
+ SSL 적용
+ 회원가입/ 로그인 API 개발
+ 자동로그인 적용
+ 메인화면 게시글 조회 API 개발

## 👩🏻‍💻 Progress
### 2021-08-14 진행상황
+ 개발 기획안 작성 및 제출
+ Github 연동
+ gitignore 파일 생성 (Android, Android Studio)

### 2021-08-15 진행상황
+ AWS EC2 인스턴스 구축
+ AWS RDS 데이터베이스 구축
+ API 명세서 설계 (20% - 유저 API와 게시글 API 등 기본적인 명세 설계)
+ ERD 설계 (10% - User Table 설계)
+ dev/prod 서브도메인 서버 구축 (70% - 구축했으나 502 이슈 발생)
+ SSL 적용 (60% - 적용했으나 502 이슈 발생)

## ⚠️ Issues
### Nginx 서버 502 이슈 (2021.08.15)
서버에서 502 bad gateway 에러 발생
> 해결을 위해 시행한 방법들
1. Nginx와 php-fpm 재시작 -> 해결되지 않음. nginx 문제없이 running 되고 있는 것 확인

