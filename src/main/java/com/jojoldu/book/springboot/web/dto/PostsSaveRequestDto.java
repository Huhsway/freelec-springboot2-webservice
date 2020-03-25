package com.jojoldu.book.springboot.web.dto;

// Entity와 거의 유사한 형태임에도 Dto 클래스를 추가로 성성함
// 절대로 Entity 클래스를 Request/Response 클래스로 사용해서는 안된다. 엔티티 클래스는 db와 맞닿은 핵심클래스이기 때문에 조심히 다뤄야함
// 그래서 Request와 Response용 Dto는 View를 위한 클래스라 정말 자주 변경이 필요하기 때문에 실제로 Controllerㅇ서 결괏값으로 여러 테이블을 조인해서 줘야 할 경우가 빈번하므로 Entity 클래스만으로 표현하기 어려운 경우가 많다.
// 꼭 Entity 클래스와 Controller에서 쓸 Dto는 분리해서 사용해야 한다.

import com.jojoldu.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
