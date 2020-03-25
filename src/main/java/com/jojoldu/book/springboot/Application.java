package com.jojoldu.book.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication // 이 설정으로 인해 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성 모두 자동으로 설정된다.
// 특히 @SpringBootApplication이 있는 위치부터 설정을 읽어가기 떄문에 이 클래스는 항상 프로젝트의 최상단에 위치해야만 한다.
public class Application { // 이 클래스가 이제 메인 클래스가 된다.
    public static void main(String[] args){
        SpringApplication.run(Application.class, args); // 이 코드로 인해 내장 web application server를 실행 항상 서버에 톰캣을 설치할 필요가 없게 된다.
        // 내장 WAS를 사용하는 이유는 언제 어디서나 같은 환경에서 스프링 부트를 배포할 수 있기 떄문
    }
}
