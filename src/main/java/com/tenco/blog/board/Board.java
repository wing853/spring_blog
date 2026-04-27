package com.tenco.blog.board;

import com.tenco.blog.util.MyDateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
// @Entity: JPA가 이 클래스를 데이터베이스 테이블과 매핑하는 객체로 인식하게 설정
// @Entity가 있어야 JPA가 관리
@Entity
@Table(name = "board_tb")
@NoArgsConstructor // 기본 생성자 (필수)
@AllArgsConstructor // 전체 멤버 변수를 넣을 수 있는 생성자
@Builder // 빌더 패턴
public class Board {

    // @id: 이 필드가 기본키임을 설정 함
    @Id
    // IDENTITY 전략: 데이터베이스에 기본 AUTO_INCREMENT 기능 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String title;
    private String content;

    // @CreationTimestamp: 하이버네이트가 제공하는 어노테이션
    // 특정하나의 엔티티가 저장 될 때 현재 시간을 자동으로 저장하는 설정
    // now() 명시할 필요 없음
    // PC--=>DB(자동 날짜 주입)
    @CreationTimestamp
    private Timestamp createdAt;

    public String getTime() {
        return MyDateUtil.timestampFormat(createdAt);
    }
}