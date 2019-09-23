# 인터넷뱅킹 이용 현황 정보 제공 API 프로젝트

개발 프레임워크 : Spring Boot + Security + MyBatis + H2

Spring MVC 패턴을 사용하여 RESTful API로 백엔드를 구현 하였으며, Persistent Layer는 MyBatis를 사용했습니다.
추가로 JWT 인증 부분은 Spring Security를 활용하여 구현하였습니다.

문제 해결 전략

* 테이블은 아래와 같이 구성
  * 디바이스를 관리하는 device
    * device 테이블은 id, name으로 구성되어 있고, id는 auto increment 전략을 사용
  * 기간별 이용률을 관리하는 usage_rate
  	* usage_rate 테이블은 year, rate 필드로 구성
  * 기간별 기계별 비율을 관리하는 device_usage_rate
    * device_usage_rate 테이블은 year, device_id, rate 필드로 구성
    * device_usage_rate 테이블은 device 테이블과 다대일 단방향 매핑이 됨
* API
  * 인터넷뱅킹 서비스 접속 기기 목록을 출력하는 API
    * /api/devices
  * 각 년도별로 인터넷뱅킹을 가장 많이 이용하는 접속기기를 출력하는 API
    * /api/device-usage-rates/most-connected-devices
  * 특정 년도를 입력받아 그 해에 인터넷뱅킹에 가장 많이 접속하는 기기 이름을 출력하는 API
    * /api/device-usage-rates/most-connected-devices/{deviceId}
  * 디바이스 아이디를 입력받아 인터넷뱅킹에 접속 비율이 가장 많은 해를 출력하는 API
    * /api/device-usage-rates/most-connected-year
  * 인터넷뱅킹 접속 기기 ID 를 입력받아 2019 년도 인터넷뱅킹 접속 비율을 예측하는 API
    * /api/device-usage-rates/predictions/{year}/{deviceId}
* 추가제약사항
  * 10000 TPS
    * redis 캐시를 사용하여 DB와의 연결을 최소화함
  * 인증을 위한 JWT - signup, signin, refresh token 기능 구현
    * spring security의 jdbc를 기본으로 사용함
    * jjwt를 사용해 jwt token을 생성/유효성 체크함
    * session 전략을 stateless로 하되, 요청시마다 token을 체크해 사용자를 판단함


## Prerequisites

JDK 1.8 이상

## Test

```
1. UnitTest : ./gradlew test
2. IntegrationTest : ./gradlew integrationTest
3. WholeTest : ./gradlew wholeTest
```

## Build

```
./gradlew build
```

## Run

빌드를 수행한 이후에

```
cd ./build/libs
mkdir data
cp [filepath] ./data/
java -jar ib-usage-info-service-1.0.0.RELEASE.jar
OR
cd ./build/libs
java -jar -Dib.pre.loader.data.path='[filepath]' ib-usage-info-service-1.0.0.RELEASE.jar
```

혹은 프로젝트 루트 폴더에서

```
./gradlew bootRun
```

브라우저에서 접속

```
http://localhost:8080
```

## Authors

Chanung Yun(cu.sonar@gmail.com)

## License

This project is licensed under the GPLv3 License

## Dependencies

* Spring Boot & Security 2.1.8.RELEASE
* Lombok 1.18.8
* H2 1.4.199
* io.jsonwebtoken.jjwt 0.9.1
