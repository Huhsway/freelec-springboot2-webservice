package com.jojoldu.book.springboot.service;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsListResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor // @Autowired가 없는 것이 어색하게 느껴질 수 있다. 스프링에서는 Bean을 주입하는 방식이 @Autowired , setter, 생성자 3가지가 있는데 이중 가장 권장하는 방식이 생성자로 주입받는 방식임
// 그러면 생성자는 어디 있을까 의문이 드는데 바로 RequiredArgsConstructor에서 해결해줌 final이 선언된 모든 필드를 인자값으로 하는 생성자를 롬복의 @RequiredArgsConstructor가 대신 생성해준다.
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) { // 신기하게 update 기능에서 데이터베이스에 쿼리를 날리는 부분이 없다. 이게 가능한 이유는 JPA의 영속성 컨텍스트 때문이다.
        // 영속성 컨텍스트란, 엔티티를 영구 저장하는 환경이다. 일종의 논리적 개념이라고 보며 JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈린다.
        // 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영한다. 즉 Entity 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요가 없다. 이 개념을 더티 체킹이라 한다.
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // (readOnly = true)를 해주면 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회속도가 개선되기 때문에 등록,수정,삭제 기능이 전혀없는 서비스 메소드에서 사용하는 것을 추천
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // 람다식임 .map(posts -> new PostsListResponseDto(posts))와 같다.
                .collect(Collectors.toList());
    } // postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto 변환 -> List로 반환하는 메소드이다.

    @Transactional
    public void delete (Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

                postsRepository.delete(posts); // JpaRepository에서 이미 delete 메소드를 지원하고 있으니 이를 활용한다. 엔티티 파라미터로 삭제할 수도 있고 deleteById 메소드를 이용하면 id로 삭제할 수도 있다.
                                               // 존재하는 Posts인지 확인을 위해 엔티티조회 후 그대로 삭제
    }

}