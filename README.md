# product-search 프로젝트

## 실행 방법

API 서버 실행
```shell
# 1. 프로젝트 빌드
./gradlew clean build

# 2. redis 로컬 실행
docker-compose up -d

# 3. API 서버 실행
./gradlew bootRun
```

테스트 실행
```shell
# 테스트 코드 실행
./gradlew test

# api 문서

## 1.  테스트 & 문서 생성
./gradlew copyApiDoc

## 2. 프로젝트 빌드
./gradlew build -x test

## 3. API 서버 실행
docker-compose up -d
./gradlew bootRun

## 4. API 문서 확인
## http://localhost:8080/static/swagger-ui.html
```

## 프로젝트 설명

### 아키텍처



### 기능

- 기타 추가 정보
  - 조회 정책 관련
  - 캐싱 관련(key 설계, TTL 등)

### 기술 선택 이유

- jpa 대신 exposed 사용한 이유?
    

