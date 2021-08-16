# 라프3 라이징테스트 당근마켓 B

## 💡 Introduction

라이징 프로그래머 3 교육 프로그램에서 진행하는 실전 프로젝트에서 개발한 당근마켓 클론 버전의 개발 일지입니다. <br/>
2021.8.14부터 2021.8.27까지 B팀의 서버 개발자로 참여했습니다.

> 기획서 

https://docs.google.com/document/d/1iK6TaV5BXOXLn7x_N5avh8IvKkrzLpgXpGJPgib4gC4/edit?usp=sharing

> API 명세서

https://docs.google.com/spreadsheets/d/1AN8mMcLtqntN6YwmVyS9J1ptAL1yD-AK/edit#gid=446451871

> ERD 

URL : https://aquerytool.com/aquerymain/index/?rurl=b5559ea5-4879-437d-8209-0d7d4b3f3a84& <br/>
Password : hwy7f2
  
## 🖋 Meeting
### 2021-08-14 회의

클라이언트 개발자와의 기획 회의에 따라 프로젝트 진행 기간인 2주간 유저 회원가입 및 로그인, 상품 조회 및 등록, 검색, 카테고리, 채팅, 동네인증 등의 기능을 개발키로 했습니다. <br/>
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

### 2021-08-15 논의 

자동로그인 기능의 난이도가 높다 판단하여 개발순위를 뒤로 미루고 메인 화면 API를 먼저 개발키로 함.

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
+ 회원가입 API 개발
+ 로컬호스트 서버 외부 연결 (ngrok 사용)

### 2021-08-16 진행상황
+ 로그인 API 개발
+ ERD 설계 (Chat, Keyword, Product, Region, Wish Table 설계)
+ API 명세서 설계 (상품 게시글 API, 채팅 API, 동네생활 API 명세 설계)
+ 상품 조회 API 개발

## ⚠️ Issues
### Nginx 서버 502 이슈 (2021.08.15)
서버에서 502 bad gateway 에러 발생
> 해결 과정
1. Nginx와 php-fpm 재시작 -> 해결되지 않음. nginx 문제없이 running 되고 있는 것 확인
2. 기존 네임서버에 AWS Route 53 서비스만 연결했으나 가비아 전체 네임서버도 추가 -> Route 53 레코드 테스트는 통과하나 브라우저에서 접속시 ```ERR_CONNECTION_TIMED_OUT``` 에러 발생
-> 일정 시간 후 접속 가능해짐, 가비아 내부의 네임서버 적용 딜레이 문제로 보임 (해결)

### Github Repository not found 이슈 (2021.08.15)
github에 push 및 pull이 되지 않으며 repository를 찾지 못하는 이슈 발생
> 해결 과정
+ git fetch 명령어로 전체 내용 받아온 뒤 ```reset --hard```를 통해 복구 (해결)

### 서브 도메인 적용 이슈 (2021.08.16)
서브 도메인 접속 시 ```ERR_CONNECTION_TIMED_OUT``` 에러 발생 
> 해결 과정 

### certbot 적용 실패 이슈 (2021.08.16)
certbot 적용시 실패하는 이슈가 발생 
> 해결 과정 
1. 기존 repository로 certbot 저장소를 만드는 방식에서 install 방식으로 업데이트 됨 -> apt-get으로 certbot 설치
2. ```[emerg] bind() to 0.0.0.0:80 failed (98: Address already in use)``` 이슈 발생
<br/> 
   80포트를 이미 사용 중이라 서버 구동이 불가능한 이슈로 ubuntu 20.04에서 종종 나타나는 현상 -> 
   1. default sites-available file에서
   ```listen [::]:80 default_server;```를 ```listen [::]:80 ipv6only=on default_server;```로 변경,
   ipv6 주소 아이피만 사용하도록 함 -> 해결 안 됨
   2. webroot가 아닌 standalone 방식으로 authentication 취득하기로 결정
   3. standalone 방식 선택 -> ssl 적용 성공 로그 뜸, 그러나 서버 접속 시 ```ERR_CONNECTION_REFUSED```, 사이트에 연결할 수 없음. 
3. ```Certbot Error 13 Permission denied: ‘/etc/letsencrypt’``` 권한 이슈 -> sudo 명령어로 (해결)