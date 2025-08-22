# 📝 뉴스피드 프로젝트

## 📌 프로젝트 소개

이 프로젝트는 사용자 간의 소통과 정보 공유를 목적으로 하는 **뉴스피드 웹 애플리케이션**입니다.  
사용자들은 회원가입 및 로그인을 통해 자신의 계정을 만들고, 자유롭게 글을 작성하며  
다른 사용자들의 글에 댓글을 남길 수 있습니다.  
또한, **팔로우 기능**을 통해 관심 있는 사용자들의 소식을 실시간으로 받아볼 수 있습니다.

---

## 🚀 주요 기능

- **회원가입**: 새로운 사용자가 계정을 생성할 수 있습니다.
- **로그인**: 등록된 사용자가 로그인하여 서비스를 이용하며, 세션 기반 인증을 통해 사용자 상태를 관리합니다.
- **사용자 정보 관리**: 닉네임, 비밀번호 등 개인 정보를 수정할 수 있습니다.
- **글쓰기**: 사용자가 새로운 게시물을 작성하고 공유할 수 있습니다.
- **댓글 남기기**: 다른 사용자의 게시물에 댓글을 달아 소통할 수 있습니다.
- **팔로우 기능**: 특정 사용자를 팔로우하여 해당 사용자의 활동(새 글 작성 등)을 받아볼 수 있습니다.
- **좋아요 기능**: 특정 게시물 및 댓글을 좋아요 할 수 있습니다.
- **친구 기념일 조회 기능**: 다른 사용자와 친구가 된 날짜와 기념일을 조회할 수 있습니다.

---

## 🛠️ 기술 스택

### 🔙 백엔드

- **Java 17**: 프로젝트의 주 개발 언어
- **Spring Boot 3.4**: 독립 실행형, 프로덕션 등급의 Spring 기반 애플리케이션 개발 프레임워크
- **Spring Data JPA**: 관계형 데이터를 객체지향적으로 다루기 위한 JPA 사용
- **Gradle** (또는 **Maven**): 빌드 및 의존성 관리 도구
- **BCrypt**: `at.favre.lib.crypto.bcrypt` 라이브러리를 사용한 비밀번호 암호화
- **Jakarta Validation**: API 요청 데이터의 유효성 검사
  - 커스텀 유효성 검사 어노테이션: `@ValidEmail`, `@ValidNickname`, `@ValidPassword`

### 🧱 아키텍처 / 패턴

- **RESTful API**: 클라이언트-서버 간 통신을 위한 API 설계 방식
- **세션 기반 인증**: HTTP 세션을 활용한 로그인 상태 유지
- **Interceptor**: `LoginInterceptor`를 통해 로그인 여부 검증
- **Soft Delete**: `deletedAt` 필드를 통해 논리적 삭제 구현
- **글로벌 예외 처리**:
  - `GlobalException`
  - `@RestControllerAdvice`를 통한 예외의 중앙 처리 및 에러 응답 표준화

---

## API 명세서

### 사용자 (Users)

| 기능             | METHOD | DOMAIN  | ENDPOINT                                    | Request                     | Response                                                      | 상태코드 |
|------------------|--------|---------|---------------------------------------------|-----------------------------|---------------------------------------------------------------|----------|
| 회원가입         | POST   | users  | /signup              | nickname, email, password, confirmPassword                              | `void`                                                                   | 201      |
| 로그인           | POST   | users  | /login               | email, password                                                         | id                                                                       | 200      |
| 프로필 조회      | GET    | users  | /users/{userId}      |                                                                         | email, nickname, isFollowing                                             | 200      |
| 유저 수정        | PATCH  | users  | /users/{userId}      | newNickname, currentPassword, newPassword, confirmPassword              | id, nickname, updatedAt                                                  | 200      |
| 회원 탈퇴        | DELETE | users  | /users/{userId}      | currentPassword                                                         | `void`                                                                   | 204      |

---

### 게시글 (Boards)

| 기능             | METHOD | DOMAIN   | ENDPOINT                                    | Request                     | Response                                                      | 상태코드 |
|------------------|--------|----------|---------------------------------------------|-----------------------------|---------------------------------------------------------------|----------|
| 게시글 생성      | POST   | boards  | /boards                                                      | subject, content   | id, subject, content, userId, nickname, createdAt, updatedAt                              | 201      |
| 게시글 전체조회  | GET    | boards  | /boards?page=0&size=10&startDate=2025-08-20&endDate=2025-08-21| -                  | [ { id, subject, content, userId, nickname, createdAt, updatedAt } ]                      | 200      |
| 게시글 단건조회  | GET    | boards  | /boards/{boardId}                                             | -                  | id, subject, content, userId, nickname, comments:[{id, content, userId, nickname, createdAt, updatedAt }], createdAt, updatedAt | 200      |
| 게시글 수정      | PATCH  | boards  | /boards/{boardId}                                             | subject, content   | id, subject, content, userId, nickname, createdAt, updatedAt                              | 200      |
| 게시글 삭제      | DELETE | boards  | /boards/{boardId}                                             | -                  | `void`                                                                                    | 204      |

---

### 댓글 (Comments)

| 기능             | METHOD | DOMAIN   | ENDPOINT                                   | Request                      | Response                                                       | 상태코드 |
|------------------|--------|----------|--------------------------------------------|------------------------------|----------------------------------------------------------------|----------|
| 댓글 생성        | POST   | comments  | /boards/{boardId}/comments                  | content       | id, content, userId, nickname, createdAt, updatedAt                                      | 201      |
| 댓글 수정        | PATCH  | comments  | /boards/{boardId}/comments/{commentId}      | content       | id, content, userId, nickname, createdAt, updatedAt                                      | 200      |
| 댓글 삭제        | DELETE | comments  | /boards/{boardId}/comments/{commentId}      | -             | `void`                                                                                   | 204      |

---

### 팔로우 (Follows)

| 기능             | METHOD | DOMAIN     | ENDPOINT                                  | Request                      | Response                                                    | 상태코드 |
|------------------|--------|------------|-------------------------------------------|------------------------------|-------------------------------------------------------------|----------|
| 팔로우            | POST   | follows | /following/{userId}  | -  | `void`                                                                    | 201      |
| 언팔로우          | DELETE | follows | /following/{userId}  | -  | `void`                                                                    | 204      |
| 팔로잉&팔로워 목록 조회 | GET    | follows | /following           | - |  followingId: [{userId, nickname}] , followerId: [{userId, nickname}]      | 200      |
| 친구 기념일 조회 | GET    | follows | /friends/anniversaries     | - |  friendId, nickname, years, friendshipDate      | 200      |

---

### 좋아요 (Likes)

| 기능             | METHOD | DOMAIN     | ENDPOINT                                  | Request                      | Response                                                    | 상태코드 |
|------------------|--------|------------|-------------------------------------------|------------------------------|-------------------------------------------------------------|----------|
| 게시글 좋아요 토글 | POST   | likes | /boards/{boardId}/likes  | -  | liked, likeCount                                                                    | 200      |
| 댓글 좋아요 토글   | POST | likes | /comments/{commentId}/likes  | -  | liked, likeCount                                                                    | 200      |


---

### Error 응답 예시

```
{
    "status": 401,
    "code": "USER-006",
    "message": "비밀번호가 올바르지 않습니다.",
    "path": "/login",
    "timestamp": "2025-08-22T09:45:58.4236847",
    "data": null
}
```

---

## Quick Start

1) 저장소 클론
```
git clone https://github.com/NewsFeedProject-7/NewsFeedProject-7.git newsfeed
cd newsfeed
```

2) 로컬 설정 파일 생성
```
// src/main/resources/application-local.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/[your db name]
    username: [your name]
    password: [your password]
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        show_sql: true
        format_sql: true
```

