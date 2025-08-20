뉴스피드 프로젝트📝 프로젝트 소개이 프로젝트는 사용자 간의 소통과 정보 공유를 목적으로 하는 뉴스피드 웹 애플리케이션입니다. 사용자들은 회원가입 및 로그인을 통해 자신의 계정을 만들고, 자유롭게 글을 작성하며 다른 사용자들의 글에 댓글을 남길 수 있습니다. 또한, 팔로우 기능을 통해 관심 있는 사용자들의 소식을 실시간으로 받아볼 수 있습니다.🚀 주요 기능회원가입: 새로운 사용자가 계정을 생성할 수 있습니다.로그인: 등록된 사용자가 로그인하여 서비스를 이용하며, 세션 기반 인증을 통해 사용자 상태를 관리합니다.글쓰기: 사용자가 새로운 게시물을 작성하고 공유할 수 있습니다.댓글 남기기: 다른 사용자의 게시물에 댓글을 달아 소통할 수 있습니다.팔로우 기능: 특정 사용자를 팔로우하여 해당 사용자의 활동(새 글 작성 등)을 받아볼 수 있습니다.사용자 정보 관리: 닉네임, 비밀번호 등 개인 정보를 수정할 수 있습니다.🛠️ 기술 스택이 프로젝트는 다음과 같은 기술 스택으로 개발되었습니다:백엔드Java 17: 프로젝트의 주 개발 언어입니다.Spring Boot 3.4: 빠르고 쉽게 독립 실행형, 프로덕션 등급의 Spring 기반 애플리케이션을 만들 수 있도록 도와주는 프레임워크입니다.Spring Data JPA: Java Persistence API를 사용하여 관계형 데이터를 객체 지향적으로 다룰 수 있도록 지원합니다.Gradle (또는 Maven): 프로젝트 빌드 및 의존성 관리 도구입니다.BCrypt: 비밀번호 암호화를 위해 at.favre.lib.crypto.bcrypt 라이브러리를 사용합니다.Jakarta Validation: API 요청 데이터의 유효성 검사를 위해 사용되며, **커스텀 유효성 검사 어노테이션 (@ValidEmail, @ValidNickname, @ValidPassword)**을 포함합니다.아키텍처/패턴RESTful API: 클라이언트-서버 간 통신을 위한 API 설계 방식입니다.세션 기반 인증: 사용자 로그인 상태를 유지하기 위해 HTTP 세션을 활용합니다.Interceptor: 커스텀 LoginInterceptor를 통해 로그인 여부를 검증하고 요청을 처리합니다.Soft Delete: 엔티티 삭제 시 실제 데이터를 지우지 않고 deletedAt 필드를 업데이트하는 방식을 사용하여 데이터 복구 가능성을 유지합니다.글로벌 예외 처리: GlobalException 및 RestControllerAdvice를 통해 애플리케이션 전반의 예외를 중앙에서 처리하고 표준화된 에러 응답을 제공합니다.⚙️ 설치 및 실행 방법이 프로젝트를 로컬 환경에서 설정하고 실행하는 방법입니다.1. 전제 조건Java Development Kit (JDK) 17 이상Gradle 또는 Maven: 프로젝트 빌드 도구 (프로젝트의 build.gradle 또는 pom.xml 파일에 따라 다름)데이터베이스: 예를 들어 H2, MySQL, PostgreSQL 등 (사용할 데이터베이스에 대한 드라이버를 build.gradle 또는 pom.xml에 추가해야 합니다.)2. 프로젝트 클론GitHub에서 프로젝트 레포지토리를 클론합니다.git clone [프로젝트_레포지토리_URL_여기에_입력]
cd [클론된_프로젝트_디렉토리_이름]
3. 데이터베이스 설정src/main/resources/application.properties 또는 src/main/resources/application.yml 파일에서 데이터베이스 연결 정보를 설정합니다.application.properties 예시:spring.datasource.url=jdbc:h2:mem:newsfeeddb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update # 개발 환경에서는 'update', 프로덕션 환경에서는 'none' 또는 'validate'를 권장합니다.
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
application.yml 예시:spring:
  datasource:
    url: jdbc:h2:mem:newsfeeddb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: update # 개발 환경에서는 'update', 프로덕션 환경에서는 'none' 또는 'validate'를 권장합니다.
    show-sql: true
    properties:
      hibernate:
        format_sql: true
참고: 위 예시는 H2 인메모리 데이터베이스를 사용한 설정입니다. MySQL, PostgreSQL 등 다른 데이터베이스를 사용하는 경우 해당 데이터베이스에 맞는 URL, 드라이버, 사용자 이름, 비밀번호로 변경해야 합니다.4. 프로젝트 빌드프로젝트 빌드 도구에 따라 다음 명령 중 하나를 실행합니다.Gradle 사용 시:./gradlew clean build
Maven 사용 시:mvn clean install
5. 애플리케이션 실행빌드가 성공하면, JAR 파일을 실행하여 애플리케이션을 시작할 수 있습니다.JAR 파일 실행 (빌드 디렉토리에서):java -jar build/libs/[프로젝트_JAR_파일_이름].jar # Gradle 빌드 결과
또는java -jar target/[프로젝트_JAR_파일_이름].jar # Maven 빌드 결과
Spring Boot 개발 환경에서 직접 실행 (Gradle 또는 Maven):./gradlew bootRun # Gradle 사용 시
또는mvn spring-boot:run # Maven 사용 시
애플리케이션이 성공적으로 시작되면, 일반적으로 http://localhost:8080 (기본 포트)에서 접근할 수 있습니다.🤝 기여자[당신의 이름 또는 GitHub 닉네임]📄 라이선스이 프로젝트는 [선택한 라이선스, 예: MIT License]에 따라 배포됩니다. 자세한 내용은 LICENSE 파일을 참조하십시오.
