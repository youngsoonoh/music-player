# FLO CHALLENGER  

FLO CHALLENGER API SERVER

> [SWAGGER API](http://ec2-15-164-251-108.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui.html) - 스웨거 API 
>

## Framework, Libraries
* **Spring boot** 
* **Spring data jpa** 
* **Spring jpa elasticsearch + [spring data jest](https://github.com/VanRoy/spring-data-jest):** elasicsearch 검색 엔진 연동  
* **[springdoc](https://springdoc.github.io/springdoc-openapi-demos/):** Open API 3 , swagger-ui
* **[problem-spring-web](https://github.com/zalando/problem-spring-web):** json 에러 핸들링
* **[mapstruct](https://mapstruct.org/):**  매핑
* **[jib](https://github.com/GoogleContainerTools/jib):** 도커 이미지 빌드 

## BUILD

* **도커 ([docker-compose](docker/docker-compose.yml))**

    ```
    gradlew -Pprod jibDockerBuild
    ```


##  배포 환경

* **SERVER**
 
    `AWS EC2 docker`
    
* **DB**

    `AWS RDS PostgreSQL 11`    

* **검색엔진**
   
    `AWS ELASTICSEARCH SERVICE 6.8`


