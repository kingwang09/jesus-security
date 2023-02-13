# jesus-security
- 스프링 시큐리티를 학습한다.

## 사용자 관리
- UserDetailsService
  - JPA를 적용하여 회원 관리 기능 추가
    - Entity: User, Role
  - 관련 세부 작업 내용은 아래 이슈를 확인한다.
    - 작업 내용(Commit): https://github.com/kingwang09/jesus-security/issues/1
    - 코드 내용(PR): https://github.com/kingwang09/jesus-security/pull/2

## PasswordEncoder
### 1) Pbkdf2PasswordEncoder
- PBKDF2 암호화 방식을 이용한다.
  - 반복횟수 인수만큼 HMAC를 수행하는 단순하고 느린 해싱함수.
- `PasswordEncoder encoder = new Pbkdf2PasswordEncoder();`
- `PasswordEncoder encoder = new Pbkdf2PasswordEncoder("secret-key");`
- `PasswordEncoder encoder = new Pbkdf2PasswordEncoder("secret-key", 185000, 256); //secret-key, 암호인코딩의 반복횟수, 해시크기`

### 2) BcryptPasswordEncoder
- 강력 해싱 함수
- `PasswordEncoder encoder = new BCryptPasswordEncoder();`
- `PasswordEncoder encoder = new BCryptPasswordEncoder(4); //강도계수`

### 3) ScryptPasswordEncoder
- `PasswordEncoder encoder = new SCryptPasswordEncoder();`

### 4) DelegatingPasswordEncoder
- 패스워드 인코드 프록시
- `PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();`
- `{encoder-key}hashing`
- ex) "{bcrypt}$2a$10$xn3LI/asdfsad"

### 5) KeyGenerator
#### 5-1) String KeyGenerator
#### 5-2) Byte[] KeyGenerator
## 6) Encrypt & Decrypt

https://vitess.io/docs/15.0/overview/whatisvitess/
