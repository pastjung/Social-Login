# Social-Login

> Initial written at october 12, 2024 <br/>
> last updated at: october 24, 2024


## Current: ver. 1.0.1<br/>
>* ver 1.0.0.
>   * Init: 프로젝트 세팅 ( React + Spring Boot )
>   * CORS 설정
>* ver 1.0.1.
>   * React-App: 로그인 페이지, 홈 페이지 생성
>   * Springboot-App: 카카오, 네이버, 구글 소셜로그인 구현
>* ver 1.0.2. ( 2024.11.12 )
>   * 소셜로그인 로직 수정: `/connect` 연결 페이지를 사용해 accessToken을 메모리에 저장
>       * `home`의 URL에 붙어있던 accessToken 쿼리 스트링 값 제거

# 1. 프로그램 (프로젝트) 설명

- 본 프로젝트의 운영체제는 Linux OS를 기반으로 작성되었습니다.
- 본 프로젝트는 CORS 설정의 경우 Backend에서 설정하는 것이 바람직하다고 생각하여 Spring Boot 프로젝트에서 설정을 했습니다.

# 2. Prerequisite

- 본 프로젝트는 Docker를 사용하므로 `.env.template` 파일을 참고하여 `.env` 파일에 환경 변수값을 작성해주세요.
    - root, react-app, springboot-app 총 3가지 파일을 모두 작성해주세요.
        - `root/.env` : 로컬 환경에서 docker-compose.yml 파일을 실행시키기 위해 필요한 환경 변수 파일입니다.
        - `react-app/.env` : React 애플리케이션 환경을 실행시키기 위해 필요한 환경 변수 파일입니다.
        - `springboot-app/.env` : Springboot 애플리케이션 환경을 실행시키기 위해 필요한 환경 변수 파일입니다.
    - `HOST_PORT` : 외부에서 컨테이너의 애플리케이션에 접근하는데 사용하는 포트 ( 노출되도 괜찮은 포트 )
    - `SERVER_PORT` : 애플리케이션이 컨테이너 내에서 통신하는 포트 ( 노출되면 안되는 포트 )
    - Vite에서는 보안이 필요한 환경변수의 유출을 막기 위해서 `VITE_`으로 시작하지 않는 환경변수는 무시되기 때문에 `VITE_SPRINGBOOT_HOST_PORT`가 필요합니다.
- 본 프로젝트는 Spring Boot를 사용하므로 `springboot-app/src/main/resources/application.yml.template` 파일을 사용하여 `application.yml` 파일을 생성해주세요. ( 그대로 생성하면 됩니다. )

# 3. 구동 방법

## 3.1. 프로젝트 실행

본 프로젝트는 Docker Compose를 사용하므로 이를 실행시켜주세요.

```shell
(sudo) docker compose up (--build)
```

## 3.2 프로젝트 종료

본 프로젝트는 Docker Compose를 사용하므로 이를 실행시켜주세요.

```shell
(sudo) docker compose down (-v)
```

# 4. 디렉토리 및 파일 설명
```
/Project-Template
├─ .env.template
├─ .gitattributes
├─ .gitignore
├─ docker-compose.yml
├─ docs
│  ├─ PULL_REQUEST_TEMPLATE.md
│  └─ README.md
├─ react-app
│  ├─ .env.template
│  ├─ .gitignore
│  ├─ README.md
│  ├─ dockerfile
│  ├─ dockerfile.dev
│  ├─ eslint.config.js
│  ├─ index.html
│  ├─ package.json
│  ├─ public
│  │  └─ vite.svg
│  ├─ src
│  │  ├─ App.css
│  │  ├─ App.jsx
│  │  ├─ assets
│  │  │  └─ react.svg
│  │  ├─ index.css
│  │  ├─ main.jsx
│  │  └─ oauth
│  │     ├─ HomePage.jsx
│  │     └─ LoginPage.jsx
│  └─ vite.config.js
└─ springboot-app
   ├─ .env.template
   ├─ .gitignore
   ├─ build.gradle
   ├─ dockerfile
   ├─ dockerfile.dev
   ├─ gradle
   │  └─ wrapper
   │     ├─ gradle-wrapper.jar
   │     └─ gradle-wrapper.properties
   ├─ gradlew
   ├─ gradlew.bat
   ├─ settings.gradle
   └─ src
      ├─ main
      │  ├─ java
      │  │  └─ com
      │  │     └─ inha
      │  │        └─ springbootapp
      │  │           ├─ SpringbootAppApplication.java
      │  │           ├─ domain
      │  │           │  ├─ oauth
      │  │           │  │  ├─ controller
      │  │           │  │  │  └─ AuthController.java
      │  │           │  │  ├─ dto
      │  │           │  │  │  └─ SocialLoginUserDto.java
      │  │           │  │  ├─ service
      │  │           │  │  │  └─ AuthService.java
      │  │           │  │  └─ util
      │  │           │  │     ├─ AuthUtil.java
      │  │           │  │     ├─ GoogleAuthUtil.java
      │  │           │  │     ├─ KakaoAuthUtil.java
      │  │           │  │     └─ NaverAuthUtil.java
      │  │           │  ├─ ping
      │  │           │  │  └─ HelloController.java
      │  │           │  └─ user
      │  │           │     ├─ controller
      │  │           │     │  └─ UserController.java
      │  │           │     ├─ entity
      │  │           │     │  └─ User.java
      │  │           │     ├─ repository
      │  │           │     │  └─ UserRepository.java
      │  │           │     └─ service
      │  │           │        └─ UserService.java
      │  │           └─ global
      │  │              ├─ config
      │  │              │  ├─ GlobalCorsConfig.java
      │  │              │  ├─ RestTemplateConfig.java
      │  │              │  └─ WebSecurityConfig.java
      │  │              └─ util
      │  │                 ├─ CookieUtils.java
      │  │                 └─ JwtUtil.java
      │  └─ resources
      │     ├─ application.properties.template
      │     └─ application.yml.temlplate
      └─ test
         └─ java
            └─ com
               └─ inha
                  └─ springbootapp
                     └─ SpringbootAppApplicationTests.java
```
- 참고: https://woochanleee.github.io/project-tree-generator/