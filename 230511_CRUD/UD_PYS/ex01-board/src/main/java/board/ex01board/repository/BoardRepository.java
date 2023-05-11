package board.ex01board.repository;

import board.ex01board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> { // 상속<entity 클래스, pk type>
    // 조회수 증가
    // update board_table set board_hits=board_hits+1 where id=?
    @Modifying // update or delete를 실행할때 필수로 붙이기
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id") // entity를 기준으로 query 작성,  nativeQuery = true는 실제 db query사용
    void updateHits(@Param("id") Long id);
}
