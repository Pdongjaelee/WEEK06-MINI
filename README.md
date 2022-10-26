# 💻 미니 프로젝트 BackEnd


## 📂 프로젝트 설명





## 🌟 주요기능
1. 폴더 생성


2. 폴더 내 사진 추가/삭제 및 태그수정





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

    참조문서
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
    [참조 블로그] (https://www.appsloveworld.com/springboot/100/84/h2-database-console-errors-with-sorry-remote-connections-weballowothers-are)

</div>
</details>

### BackEnd 팀원 깃허브
👩‍💻 [윤수영](https://github.com/Suyoung225) 🧑‍💻 [이승우](https://github.com/iswoos) 👨‍💻 [이동재](https://github.com/Pdongjaelee)
