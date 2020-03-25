package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository <Posts,Long> { // Posts 클래스로 Database를 접근하게 해준다. ,DB Layer 접근자를 만드는거 <Entity 클래스, PK 타입> 을 상속하면 기본적인 CRUD 메소드가 자동으로 생성됨
// Entity 클래스인 Posts와 꼭 붙어있어야 한다.
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") // SpringDataJpa에서 제공하는 기본 메소드 말고 이렇게 가독성이 좋은 쿼리문으로 해도된다.
    List<Posts> findAllDesc();
}
