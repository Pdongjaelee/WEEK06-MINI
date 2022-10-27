# 🤖 미니 프로젝트 BackEnd 🤖

## 프로젝트 설명
📷 나의 여행기록 및 추억들을 사진과 글로 앨범에 저장해보자 💾


![image](https://user-images.githubusercontent.com/102216495/198182564-0b2f506f-fd66-4de1-81c6-56bf6669f465.png)

<br>



## 🌟 주요기능
**1. 폴더 생성**
> 폴더명, 태그, 날짜 정보 기입


> 태그 : 태그입력 후 ENTER 클릭 시, 문자열 최앞단 '#'자동기입


> 날짜 : 달력캘린더 기능으로 원하는 날짜선택
<br>

**2. 검색창에 폴더명 및 태그 입력 시, 해당하는 폴더리스트 나열**
> 폴더명, 태그이름 구분없이 검색명에 포함된 폴더들은 전체 조회됨(빈 문자열은 모두 조회)


> 폴더 날짜 정보 기준, 최신순으로 정렬(폴더 데이터 생성시간 기준X)
<br>

**3. 메인페이지 내, 전체 이용자 핫 키워드 및 개인 핫 키워드 노출**
> 전체 이용자 핫 키워드 : DB내 가장 많이 입력된 태그 TOP5 순서대로 정렬


> 개인 핫 키워드 : 개인이 가장 많이 입력한 태그 TOP5 개수까지 정렬

<details>
<summary> 핫 키워드 노출예시
</summary>
<div markdown="1">


전체 이용자 핫 키워드 예시)<br>
1위 #여행<br>
2위 #제주도<br>
3위 #한강<br>
4위 #한라산<br>
5위 #지리산<br>

개인 핫 키워드 예시)<br>
#가을 : 2<br>
#이승우 : 1<br>    
    
    
</div>
</details>
<br>

**4. 사진 추가 및 삭제**
> 추가 : 원하는 사진파일 업로드


> 삭제 : 원하는 사진 체크 후 삭제버튼 클릭 (여러장 삭제가능)


**5. 폴더삭제**
> 생성된 폴더페이지 조회 시, 삭제하기 버튼있음 (클릭 시 폴더삭제)


## ⚙ API 설계
https://www.notion.so/API-ef3f57ceb5fb4caca08b7526c20ff71d

## 🔐 ERD
![image](https://user-images.githubusercontent.com/102216495/198051814-6ac6029b-94e9-478a-87cf-4ce144fee9ba.png)


## 🚀 트러블슈팅

<details>
<summary>1. 메인페이지에서 인기 태그 top5, 내 태그 top5 보여주기
</summary>
<div markdown="1">  
    
    
    👌 태그 문자열에서 .split("#")
    
    을 하면 첫 태그에 “”가 리스트에 저장되어 빈 값이 아닌 태그 문자열에 
    
    .substring(1)을 먼저하고 .split("#")을 했다.
    
    top5 태그를 가져올 때 리스트에서 .subList(0, 5) 를
    
    했는데 태그 수가 5개 미만이면 오류가 뜨기 때문에 태그가 5개 이상일 때만 앞 5개로 자르고 아닌 경우 전체 태그 보여주기

</div>
</details>



<details>
<summary>2. 토큰 재발행
</summary>
<div markdown="1">
    
    
    👌 access 토큰 만료 시 Refresh 토큰 재발행 할 때 헤더로 받은 Refresh토큰과 디비에 저장된 refresh토큰을 비교해야 
    
    하는데 refresh토큰에서 앞에 Bearer 부분을 뺀 토큰값과 디비에 있는 refresh 토큰 값을 비교하여 일치하지 않아 오류가 발생했다.
    
   
</div>
</details>


<details>
<summary>3. 사진 삭제체크리스트 받아오는 값 데이터 체크
</summary>
<div markdown="1">    
    
    
    👌 사진 삭제 파라미터 값 수신하는 방법 @RequestParam은 
    
    (comma)로 구분하여 데이터를 요청하면 자동으로 데이터를 
    
    (comma)구분하여 List형태로 데이터를 변환한다. 
    
    예시) http://localhost:8080/folder/3?photoId=1,2
</div>
</details>

<details>
<summary>4. 서버측 Cors허용 옵션 미추가로 인한 클라이언트 접근제한 발생 </summary>
<div markdown="1">     
    
    
    👌클라이언트측 주소 기입을 통해 origin 검증 통과작업이 필요했음.
    클라이언트측 도메인에 대한 접근권한을 아래와 같은 코드로 승인하여 문제해결 완료함.
    
    
```java
@Bean
CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    configuration.setAllowedOrigins(Arrays.asList(FRONT_END_SERVER));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization"));
    
    // This allow us to expose the headers
    configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
            "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
} 
```

    참조 블로그
    1. https://stackoverflow.com/questions/37897523/axios-get-access-to-response-header-fields
    2. https://evan-moon.github.io/2020/05/21/about-cors/
    3. https://wonit.tistory.com/572

</div>
</details>


<details>
<summary>5. 서버배포 시, 클라이언트측 H2 DataBase접근제한 문제발생</summary>
<div markdown="1">       
    
    
    👌서버측에서 H2 웹접근을 허용해줘야하는 것으로 파악완료. 아래와 같은 코드로 문제해결 완료함.

```java
spring.h2.console.settings.web-allow-others=true
```
    참조 블로그 : https://www.appsloveworld.com/springboot/100/84/h2-database-console-errors-with-sorry-remote-connections-weballowothers-are

</div>
</details>

<details>
<summary>6. 검색어 조회 시 폴더명과 태그 동시 조회
</summary>
<div markdown="1">    
    
    
    👌 JPA메소드 쿼리 사용 시, 불필요한 로직이 증가하였음.
    
    이를 해소하기 위해 Querydsl 기술을 적용하여 쿼리 성능 최적화를 진행하였음
    
</div>
</details>


### BackEnd 팀원 깃허브
👩‍💻 [윤수영](https://github.com/Suyoung225) 🧑‍💻 [이승우](https://github.com/iswoos) 👨‍💻 [이동재](https://github.com/Pdongjaelee)
