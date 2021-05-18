# Music player demo
[![Run on Ainize](https://ainize.ai/images/run_on_ainize_button.svg)](https://ainize.web.app/redirect?git_repo=https://github.com/youngsuno/music-player)


## Framework, Libraries
* **Spring boot** 
* **Spring data jpa** 
* **Spring jpa elasticsearch + [spring data jest](https://github.com/VanRoy/spring-data-jest):** elasicsearch 검색 엔진 연동  
* **[springdoc](https://springdoc.github.io/springdoc-openapi-demos/):** Open API 3 , swagger-ui
* **[problem-spring-web](https://github.com/zalando/problem-spring-web):** json 에러 핸들링
* **[mapstruct](https://mapstruct.org/):**  매핑
* **[jib](https://github.com/GoogleContainerTools/jib):** 도커 이미지 빌드 

## 접속 정보 

* **실행**
    ```
    ./gradlew bootRun
    ```
    

* **스웨거**

    ```
    localhost:8080/swagger-ui.html
    ```

* **H2 Console**

    ```
    localhost:8080/h2-console (id: user)
    ```


##  배포 환경

* **SERVER**
 
    `AWS EC2 docker`
    
* **DB**

    `AWS RDS PostgreSQL 11`    

* **검색엔진**
   
    `AWS ELASTICSEARCH SERVICE 6.8`


