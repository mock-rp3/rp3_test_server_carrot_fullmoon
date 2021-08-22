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

### 2021-08-17 진행상황
+ certbot 이슈 해결 (50% - 여러 방법 실행해봤으나 해결 안 됨)

### 2021-08-18 진행상황
+ 특정 유저 조회 API 개발
+ 유저 닉네임 검색 API 개발
+ 로그인 API 수정 (아이디 비밀번호 로그인 형식 -> 전화번호 로그인 형식)
+ 유저 정보 수정 API
+ 유저 정보 삭제 API

### 2021-08-19 진행상황
+ 특정 유저 조회 API 회원 전용으로 수정
+ 전체 중고 상품 조회 API (메인 페이지) 개발 
+ 중고 상품 제목으로 검색 API 개발 (일부 검색어로 검색 가능하게 함)

### 2021-08-20 진행상황
+ 상품 디테일 조회 API 개발
+ 상품 등록 API 개발 (70%)

### 2021-08-21 진행상황
+ 상품 등록 API 개발 (75%)
+ 상품 게시글 삭제 API 개발

### 2021-08-22 진행상황
+ 상품 등록 API 개발
+ 상품 정보 수정 API 개발
+ dev/prod 서브도메인 서버 구축
+ SSL 적용

### 2021-08-23 진행상황
+ AWS 서버 배포
+ 게시자 판매 상품 조회 API


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
1. ```/etc/nginx/sites-available```에 서버 블록 만들지 않고 따로 파일을 만들어 설정 -> 해결 안 됨

### certbot 적용 실패 이슈 (2021.08.16)
certbot 적용시 실패하는 이슈가 발생 
> 해결 과정 
1. 기존 repository로 certbot 저장소를 만드는 방식에서 install 방식으로 업데이트 됨 -> apt-get으로 certbot 설치
2. ```[emerg] bind() to 0.0.0.0:80 failed (98: Address already in use)``` 이슈 발생
   -> 80포트를 이미 사용 중이라 서버 구동이 불가능한 이슈로 ubuntu 20.04에서 종종 나타나는 현상 -> 
3. default sites-available file에서
   ```listen [::]:80 default_server;```를 ```listen [::]:80 ipv6only=on default_server;```로 변경,
   ipv6 주소 아이피만 사용하도록 함 -> 해결 안 됨
4. webroot가 아닌 standalone 방식으로 authentication 취득하기로 결정
5. standalone 방식 선택 -> ssl 적용 성공 로그 뜸, 그러나 서버 접속 시 ```ERR_CONNECTION_REFUSED```, 사이트에 연결할 수 없음. 
6. 인바운드 규칙에 HTTPS 443 포트 추가 -> 해결 안 됨 
7. ```sudo /etc/init.d/apache2 stop```로 Apache 2 중지 시켜서 해결 -> 502 Bad Gateway 발생
8. 에러 로그에서 ```*20 connect() failed (111: Connection refused) while connecting to upstream, client: 39.17.3.115, server: www.devjiyoon.shop, request: "GET / HTTP/1.1", upstream: "http://127.0.0.1:9000/", host: "dev.devjiyoon.shop"```
      로그 확인
9. 로컬 저장소의 git 히스토리 삭제 및 초기화 -> 해결 안 됨
10. ```The repository 'http://ppa.launchpad.net/certbot/certbot/ubuntu focal Release' does not have a Release file.```
   로그 발견, certbot과 관련된 repository가 depricated되었음 -> ```sudo apt-add-repository -r ppa:certbot/certbot```로 ppa repository 삭제 -> 로그 더 이상 뜨지 않음
11. nginx와 apache의 충돌 문제일 수도 있다고 판단, apache2 웹서버 삭제 -> 해결 안 됨 
12. php-fpm 재설치 -> 메인 서버는 작동하나 SSL 적용 안 됨, dev/prod 서버는 502 에러 발생하나 SSL 적용됨

### 상품 제목으로 부분 검색 이슈 (2021.08.19)
상품 제목으로 검색 시 부분 검색어로 검색이 안 되고 전체 일치해야 검색되는 이슈
```AND title LIKE '%포스터%'``` 쿼리로 실행했을 시 작동하나 dao 내에 해당 쿼리 입력하면 적용 안 됨
> 해결 과정 
1. 파라미터를 object로 설정 -> 해결 안 됨
2. 작은따옴표 입력하면 문자열로 인식해서 내부의 파라미터를 인식 못 하므로 이를 해결하려 함. 다음은 시도한 방법들.
```‘%’?‘%’ ```, ```'%'||:?||'%'```, ```'%'||?||'%'```, ```'%:?%'```, ```"%" + ? + "%"```, ```'%:?%'```
3. concat 함수 활용, ```concat('%',?,'%')```으로 입력 (해결)
   

### 상품 디테일 페이지에서 여러 이미지 불러오기 이슈 (2021.08.19)
상품 디테일 페이지에서 이미지를 여러 개 불러오지 못하는 이슈
> 해결 과정
1. 이미지와 디테일 페이지 정보를 각각 불러와 리스트로 저장한 뒤 두 개의 리스트를 합쳐 리턴하여 보여줌 (해결)

### 상품 등록 페이지에서 이미지와 정보를 각각 다른 테이블에 Insert 해야하는 이슈 (2021.08.20)
1. 유저가 저장한 지역 정보를 받아와 상품 게시글의 지역 정보에 저장해야 함
2. 상품 등록 페이지에서 이미지와 정보를 각각 다른 테이블에 넣어야 함
> 해결 과정
1. 지역 정보를 받아오는 쿼리로 값을 받아와 이를 지역 정보에 저장하는 쿼리에 파라미터로 사용 (해결)
2. 상품 정보를 등록하는 쿼리를 먼저 실행한 후 ```select last_insert_id()```쿼리로 마지막으로 생성된 상품 정보의 pk 값을 
이미지 정보를 보내는 쿼리에 파라미터로 사용 (해결)
> 남은 과제
1. 리스트 맵을 활용하여 여러 이미지를 한 번에 받아볼 것
2. productIdx 값을 제대로 리턴하도록 해볼 것