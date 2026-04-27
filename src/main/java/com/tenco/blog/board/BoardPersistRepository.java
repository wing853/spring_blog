package com.tenco.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardPersistRepository {
    // JPA 핵심 인터페이스
    // 영속성 컨텐트를 관리하고 엔티티의 생면주기를 제어
    private final EntityManager em;

    // 게시글 작성
    @Transactional
    public Board save(Board board) {
        // 1. 매개 변수로 받은 board는 비영속 상태
        // 아직 영속성 컨텍스트에 관리 되고 있지 않은 상태
        // 아직 데이터베이스와 연관없는 순수 JAVA 객체

        em.persist(board); // insert 처리 완료
        // 2. board 객체를 영속성 컨텍스트에 넣어 둠(SQL 저장소에 등록 - 쓰기 지연)
        // 영속성 컨텍스트에 들어가더라도 아직 DB에 실제 insert한 상태는 아님

        // 3. 트랜잭션 커밋 시점에 실제 DB에 접근해서 insert 구문이 수행 된다

        // 4. board 객체의 id 변수값을 1차 캐쉬에 map 구조로 보관 되어 짐
        // 1차 캐쉬에 들어간 영속상태로 변경된 Object를 리턴한다.

        return board;
    }

    // JPQL을 사용한 게시글 목록 조회
    public List<Board> findAll(){

        // JPQL: Entity 객체를 대상으로 하는 객체지향 쿼리
        // Board는 Entity 클래스명, b는 별칭
        // 주의! 테이블명이 아닌 클래스명(엔티티명) 사용
        String jpql = """
                SELECT b FROM Board b ORDER BY b.createdAt DESC
                """;
        List<Board> boardList = em.createQuery(jpql,Board.class).getResultList();
        return boardList;
    }

    // 게시글 상세보기 요청(조회 - 필수값 기본키로 조회)
    public Board findById(Integer id) {

        // 영속성 컨텍스트를 활용
        // 1. 엔티티 매니저에서 제공하는 메서드를 활용 방법
        //Board board = em.find(Board.class,id);

        // 2. JPQL 문법으로 Board를 조회하는 방법
        String jpql = """
                SELECT b FROM Board b WHERE b.id = :id
                """;

        return em.createQuery(jpql,Board.class)
                .setParameter("id", id)
                .getSingleResult();

    }

}
