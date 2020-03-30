package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter // 롬복 라이브러리의 어노테이션 클래스 내 모든 필드의 Getter 메소드를 자동생성
@NoArgsConstructor // 롬복 라이브러리의 어노테이션 기본 생성자 자동 추가 public Posts() {}와 같은 효과
@Entity // 필자는 어노테이션 순서를 주요 어노테이션을 클래스에 가깝게 둔다. @Entity는 JPA의 어노테이션이고 위의 두개는 롬복의 어노테이션이다. 코틀린등의 새 언어로 변환시 롬복은 필요없기 때문에 지우기 더 편함
// Entity는 테이블과 링크될 클래스임을 나타냄, 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭 ex) SalesManager.java -> sales_manager table
public class Posts extends BaseTimeEntity { // 실제 DB테이블과 매칭될 클래스이며 Entity 클래스

    @Id // 해당 테이블의 PK필드를 나타낸다. (primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 규칙을 나타낸다. 스프링부터 2.0 에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 된다.
    // 왠만하면 Entity의 PK는 Long 타입의 Auto_increment를 추천한다. (MySQL 기준으로 이렇게 하면 bigint 타입이 된다.) 주민등록번호와 같이 비즈니스상 유니크 키나, 여러 키를 조합한 복합키로 PK를 잡을 경우 난감한 상황이 종종 발생
    // 스프링부트 2.0과 1.5 버전의 차이는 https://jojoldu.tistory.com/295 에 정리했으니 참고
    private Long id;

    @Column(length = 500, nullable = false) // 테이블의 칼럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 된다. 사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용한다.
    // 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나 (ex:title), 타입을 TEXT로 변경하고 싶거나(ex:content)등의 경우에 사용된다.
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 롬복 라이브러리의 어노테이션 해당 클래스의 빌더 패턴 클래스를 생성, 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
