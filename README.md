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

 ## ✅ Update ( ~ 23.07.30 ) 
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


  

 ## ✅ Info
  ### 이광훈 ☺️
  #### hun053@naver.com
  #### https://github.com/hunirin

