# π€ λ―Έλ νλ‘μ νΈ BackEnd π€

## νλ‘μ νΈ μ€λͺ
π· λμ μ¬νκΈ°λ‘ λ° μΆμ΅λ€μ μ¬μ§κ³Ό κΈλ‘ μ¨λ²μ μ μ₯ν΄λ³΄μ πΎ


https://www.youtube.com/watch?v=EirO3gxfXeU

![image](https://user-images.githubusercontent.com/102216495/198182564-0b2f506f-fd66-4de1-81c6-56bf6669f465.png)

<br>



## π μ£ΌμκΈ°λ₯
**1. ν΄λ μμ±**
> ν΄λλͺ, νκ·Έ, λ μ§ μ λ³΄ κΈ°μ


> νκ·Έ : νκ·Έμλ ₯ ν ENTER ν΄λ¦­ μ, λ¬Έμμ΄ μ΅μλ¨ '#'μλκΈ°μ


> λ μ§ : λ¬λ ₯μΊλ¦°λ κΈ°λ₯μΌλ‘ μνλ λ μ§μ ν
<br>

**2. κ²μμ°½μ ν΄λλͺ λ° νκ·Έ μλ ₯ μ, ν΄λΉνλ ν΄λλ¦¬μ€νΈ λμ΄**
> ν΄λλͺ, νκ·Έμ΄λ¦ κ΅¬λΆμμ΄ κ²μλͺμ ν¬ν¨λ ν΄λλ€μ μ μ²΄ μ‘°νλ¨(λΉ λ¬Έμμ΄μ λͺ¨λ μ‘°ν)


> ν΄λ λ μ§ μ λ³΄ κΈ°μ€, μ΅μ μμΌλ‘ μ λ ¬(ν΄λ λ°μ΄ν° μμ±μκ° κΈ°μ€X)
<br>

**3. λ©μΈνμ΄μ§ λ΄, μ μ²΄ μ΄μ©μ ν« ν€μλ λ° κ°μΈ ν« ν€μλ λΈμΆ**
> μ μ²΄ μ΄μ©μ ν« ν€μλ : DBλ΄ κ°μ₯ λ§μ΄ μλ ₯λ νκ·Έ TOP5 μμλλ‘ μ λ ¬


> κ°μΈ ν« ν€μλ : κ°μΈμ΄ κ°μ₯ λ§μ΄ μλ ₯ν νκ·Έ TOP5 κ°μκΉμ§ μ λ ¬

<details>
<summary> ν« ν€μλ λΈμΆμμ
</summary>
<div markdown="1">


μ μ²΄ μ΄μ©μ ν« ν€μλ μμ)<br>
1μ #μ¬ν<br>
2μ #μ μ£Όλ<br>
3μ #νκ°<br>
4μ #νλΌμ°<br>
5μ #μ§λ¦¬μ°<br>

κ°μΈ ν« ν€μλ μμ)<br>
#κ°μ : 2<br>
#μ΄μΉμ° : 1<br>    
    
    
</div>
</details>
<br>

**4. μ¬μ§ μΆκ° λ° μ­μ **
> μΆκ° : μνλ μ¬μ§νμΌ μλ‘λ


> μ­μ  : μνλ μ¬μ§ μ²΄ν¬ ν μ­μ λ²νΌ ν΄λ¦­ (μ¬λ¬μ₯ μ­μ κ°λ₯)


**5. ν΄λμ­μ **
> μμ±λ ν΄λνμ΄μ§ μ‘°ν μ, μ­μ νκΈ° λ²νΌμμ (ν΄λ¦­ μ ν΄λμ­μ )


## β API μ€κ³
https://www.notion.so/API-ef3f57ceb5fb4caca08b7526c20ff71d

## π ERD
![image](https://user-images.githubusercontent.com/102216495/198051814-6ac6029b-94e9-478a-87cf-4ce144fee9ba.png)


## π νΈλ¬λΈμν

<details>
<summary>1. λ©μΈνμ΄μ§μμ μΈκΈ° νκ·Έ top5, λ΄ νκ·Έ top5 λ³΄μ¬μ£ΌκΈ°
</summary>
<div markdown="1">  
    
    
    π νκ·Έ λ¬Έμμ΄μμ .split("#")
    
    μ νλ©΄ μ²« νκ·Έμ ββκ° λ¦¬μ€νΈμ μ μ₯λμ΄ λΉ κ°μ΄ μλ νκ·Έ λ¬Έμμ΄μ 
    
    .substring(1)μ λ¨Όμ νκ³  .split("#")μ νλ€.
    
    top5 νκ·Έλ₯Ό κ°μ Έμ¬ λ λ¦¬μ€νΈμμ .subList(0, 5) λ₯Ό
    
    νλλ° νκ·Έ μκ° 5κ° λ―Έλ§μ΄λ©΄ μ€λ₯κ° λ¨κΈ° λλ¬Έμ νκ·Έκ° 5κ° μ΄μμΌ λλ§ μ 5κ°λ‘ μλ₯΄κ³  μλ κ²½μ° μ μ²΄ νκ·Έ λ³΄μ¬μ£ΌκΈ°

</div>
</details>



<details>
<summary>2. ν ν° μ¬λ°ν
</summary>
<div markdown="1">
    
    
    π access ν ν° λ§λ£ μ Refresh ν ν° μ¬λ°ν ν  λ ν€λλ‘ λ°μ Refreshν ν°κ³Ό λλΉμ μ μ₯λ refreshν ν°μ λΉκ΅ν΄μΌ 
    
    νλλ° refreshν ν°μμ μμ Bearer λΆλΆμ λΊ ν ν°κ°κ³Ό λλΉμ μλ refresh ν ν° κ°μ λΉκ΅νμ¬ μΌμΉνμ§ μμ μ€λ₯κ° λ°μνλ€.
    
   
</div>
</details>


<details>
<summary>3. μ¬μ§ μ­μ μ²΄ν¬λ¦¬μ€νΈ λ°μμ€λ κ° λ°μ΄ν° μ²΄ν¬
</summary>
<div markdown="1">    
    
    
    π μ¬μ§ μ­μ  νλΌλ―Έν° κ° μμ νλ λ°©λ² @RequestParamμ 
    
    (comma)λ‘ κ΅¬λΆνμ¬ λ°μ΄ν°λ₯Ό μμ²­νλ©΄ μλμΌλ‘ λ°μ΄ν°λ₯Ό 
    
    (comma)κ΅¬λΆνμ¬ Listννλ‘ λ°μ΄ν°λ₯Ό λ³ννλ€. 
    
    μμ) http://localhost:8080/folder/3?photoId=1,2
</div>
</details>

<details>
<summary>4. μλ²μΈ‘ Corsνμ© μ΅μ λ―ΈμΆκ°λ‘ μΈν ν΄λΌμ΄μΈνΈ μ κ·Όμ ν λ°μ </summary>
<div markdown="1">     
    
    
    πν΄λΌμ΄μΈνΈμΈ‘ μ£Όμ κΈ°μμ ν΅ν΄ origin κ²μ¦ ν΅κ³Όμμμ΄ νμνμ.
    ν΄λΌμ΄μΈνΈμΈ‘ λλ©μΈμ λν μ κ·ΌκΆνμ μλμ κ°μ μ½λλ‘ μΉμΈνμ¬ λ¬Έμ ν΄κ²° μλ£ν¨.
    
    
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

    μ°Έμ‘° λΈλ‘κ·Έ
    1. https://stackoverflow.com/questions/37897523/axios-get-access-to-response-header-fields
    2. https://evan-moon.github.io/2020/05/21/about-cors/
    3. https://wonit.tistory.com/572

</div>
</details>


<details>
<summary>5. μλ²λ°°ν¬ μ, ν΄λΌμ΄μΈνΈμΈ‘ H2 DataBaseμ κ·Όμ ν λ¬Έμ λ°μ</summary>
<div markdown="1">       
    
    
    πμλ²μΈ‘μμ H2 μΉμ κ·Όμ νμ©ν΄μ€μΌνλ κ²μΌλ‘ νμμλ£. μλμ κ°μ μ½λλ‘ λ¬Έμ ν΄κ²° μλ£ν¨.

```java
spring.h2.console.settings.web-allow-others=true
```
    μ°Έμ‘° λΈλ‘κ·Έ : https://www.appsloveworld.com/springboot/100/84/h2-database-console-errors-with-sorry-remote-connections-weballowothers-are

</div>
</details>

<details>
<summary>6. κ²μμ΄ μ‘°ν μ ν΄λλͺκ³Ό νκ·Έ λμ μ‘°ν
</summary>
<div markdown="1">    
    
    
    π JPAλ©μλ μΏΌλ¦¬ μ¬μ© μ, λΆνμν λ‘μ§μ΄ μ¦κ°νμμ.
    
    μ΄λ₯Ό ν΄μνκΈ° μν΄ Querydsl κΈ°μ μ μ μ©νμ¬ μΏΌλ¦¬ μ±λ₯ μ΅μ νλ₯Ό μ§ννμμ
    
</div>
</details>


### BackEnd νμ κΉνλΈ
π©βπ» [μ€μμ](https://github.com/Suyoung225) π§βπ» [μ΄μΉμ°](https://github.com/iswoos) π¨βπ» [μ΄λμ¬](https://github.com/Pdongjaelee)
