🌐 Project_1_LeeGwanghun
 =============
♻️ 멋사마켓_고도화
 --------------

## ✅ Project Infomation
#### 🔺 개발 기간: 23. 07. 26 ~ 23. 08. 02
#### 🔺 소개
   > 사용자가 중고 물품을 자유롭게 올리고, 댓글을 통해 소통하며, 최종적으로 구매 제안에 대하여 수락할 수 있는 형태의 중고 거래 플랫폼
#### 🔺 주요 기능
   - 이전 멋사마켓에서 Spring Security를 이용해 회원가입 및 로그인을 구현
   - 로그인을 통해 JWT를 발급받아 인증된 상태에서 마켓 기능을 이용가능하게 구현

 ## ✅ Project Guide
  #### 🔺 요구사항
    • IntelliJ 
      - sdk : graalvm-ce-19 java version "19.0.2"
      - Language level: 17 - Sealed types, always-stric floating-point semantics
    • Postman 2.1
  #### 🔺 설치 방법
    $ git clone https://github.com/likelion-backend-5th/MiniProject_Basic_LeeGwanghun
  #### 🔺 테스트 방법
1. 프로젝트를 실행 
2. ```Project_1.postman_collection.json``` 파일을 Postman으로 불러옴
3. ```POST /users/register```로 회원가입을 함
4. ```POST /token/issue```로 JWT를 발급받음
5. 이후 ```Params```에 username, password를 입력, ```Auth```에서 Bearer Token에 token에 JWT를 넣고 사용

 ## ✅ Update ( 1일차 ~ 3일차 ) 
   ( 🔹 추가 / 🔸 수정 )
### 📂 config 
     🔹 PasswordEncoderConfig : 비밀번호를 암호화하기위한 config
     🔹 WebSecurityConfig : URL로 오는 요청에 대해 필터링하는 보안 config
### 📂 controller
     🔸 CommentController, ItemController, ProposalController
        : @RequestParam을 통해 username과 password를 받도록 수정,
          READ 기능엔 "/read"를 추가해 누구나 접근할 수 있도록 수정
     🔹 TokenController : 가입된 유저 정보를 입력하면 token(JWT)를 발급
     🔹 UserController : username과 password, password-check를 받아 회원가입을 시켜줌
### 📂 dto
     🔸 CommentDto, ItemDto, ProposalDto
        : writer와 password를 삭제하고 username이 저장될 수 있도록 수정,
     🔹 CustomUserDetails : UserDetails 인터페이스를 통해 유저 정보를 저장
### 📂 entity ( 연결 관계는 ERD 참고 )
     🔹 AuthorityEntity : 권한의 정보를 저장하는 entity
     🔹 RoleEntity : 역할의 정보를 저장하는 entity
     🔹 UserEntity : 유저의 정보를 저장하는 entity
     🔸 CommentEntity, ItemEntity, ProposalEntity
        : writer, password 삭제하고 username 추가
### 📂 jwt
     🔹 JwtRequestDto : JWT 발급에 필요한 유저 정보를 저장
     🔹 JwtTokenDto : token(JWT)를 저장
     🔹 JwtTokenFilter : 사용자의 JWT를 해석하고, 사용자가 인증된 상태인지 확인
     🔹 JwtTokenUtils : JWT 관련 기능들을 넣어놓음
### 📂 repository
     🔹 AuthorityRepository, RoleRepository, UserRepository 추가
### 📂 service
     🔹 JpaUserDetailsManager : Spring Security Filter에서 필요한 사용자 정보를 활용
     🔸 CommentService, ItemService, ProposalService
        : 인증된 상태의 유저로 로그인해야 기능을 사용할 수 있게 수정,
          READ 기능은 "/read"를 추가해 누구나 접근할 수 있게 수정

## ✅ Update ( 4일차 ~ 6일차 )

### 💡 _로그인 하지않고는 다른 기능을 이용할 수 없음 (회원가입은 가능)_

### 📄 HTML + CSS
     🔹 로그인 : 회원 가입을 통해 가입된 정보로 로그인
                +) 정보를 입력하지 않을 시 애니메이션 추가
                👉 http://localhost:8080/view/login
     🔹 회원가입 : 기입된 정보로 회원 등록
                +) 정보를 입력하지 않을 시 애니메이션 추가
                (폰번호, 이메일, 주소는 필수 아님)
                👉 http://localhost:8080/view/register
     🔹 홈 화면 : 로그인 시 뜨는 첫 화면, 물품 등록, 물품 조회, 로그아웃 버튼이 있음
                +) 로그아웃 시 로그인 화면으로 돌아감
                👉 http://localhost:8080/view/home
     🔹 물품 등록 : 물품을 등록하는 화면
                +) 제목, 최소 가격, 내용을 입력할 수 있음
                👉 http://localhost:8080/view/write
     🔹 물품 조회 (전체) : 등록된 물품들을 전부 보는 화면
                +) 번호, 제목, 작성자, 상태만 표시됨
                +) 물품의 번호 혹은 제목 클릭시 물품으로 이동
                👉 http://localhost:8080/view/itemList
     🔹 물품 조회 (개별) : 등록된 물품을 보는 화면
                +) 번호, 제목, 최소가격, 상태, 내용까지 표시됨
                +) 물품의 번호 혹은 제목 클릭시 물품으로 이동
                👉 http://localhost:8080/view/itemOne/{id}
     🔹 물품 삭제 : 물품(개별)에서 삭제버튼을 누르면 물품 삭제
                +) 물품의 번호 혹은 제목 클릭시 물품으로 이동
                👉 http://localhost:8080/view/itemOne/{id}
### 📂 config
     🔸 WebSecurity : formLogin을 사용하도록 수정
### 📂 controller
     🔹 ViewController : HTML을 통해 필요한 데이터에 따라 새로운 엔드포인트 생성


  

 ## ✅ Info
  ### 이광훈 ☺️
  #### hun053@naver.com
  #### https://github.com/hunirin

