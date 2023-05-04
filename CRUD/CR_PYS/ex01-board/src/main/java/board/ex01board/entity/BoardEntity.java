package board.ex01board.entity;

import board.ex01board.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// Entity -> DB 테이블 역할을 하는 클래스(service와 repository에서만 사용)
@Entity
@Getter
@Setter
@Table(name = "board_table") // 특정 테이블 이름 따로 작성
public class BoardEntity extends BaseEntity { // BaseEntity를 상속
    @Id // pk 컬럼 지정 -> 필수!!
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(length = 20, nullable = false) // 컬럼 크기 = 20, not null,
    private String boardWriter;

    @Column // default값 : 크기 255, null 가능
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    // DTO -> Entity 변환 (Entity class)
    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        return boardEntity;
    }

}

