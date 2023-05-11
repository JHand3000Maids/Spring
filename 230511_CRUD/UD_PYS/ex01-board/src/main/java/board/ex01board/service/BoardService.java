package board.ex01board.service;

import board.ex01board.dto.BoardDTO;
import board.ex01board.entity.BoardEntity;
import board.ex01board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO -> Entity 변환 (Entity class)
// Entity -> DTO 변환 (DTO class)
// controller 부터 호출 -> DTO로 넘겨받음, repo로 넘길때 Entity로 넘겨준다 ...

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository; // 생성자 주입 방식
    // 1. CREATE====================================================
    // save 메서드 정의
    public void save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity); // 매개변수를 entity 클래스 타입으로 받는다. return도 entity type
    }
    // 2. READ===================================================
    // list
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll(); // 1. list형태의 entity 가져온다
        List<BoardDTO> boardDTOList = new ArrayList<>(); // 2.DTO객체로 옮겨 담아서 controller로 return
        // 3. entity -> DTO 객체로 옮겨 담는다, 반복문을 돌려서 꺼내오기
        for (BoardEntity boardEntity: boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList; // 리스트 return
    }

    // detail
    @Transactional // JPA에서 제공하는 메서드 외에 별도로 추가되는 메서드 사용하는 경우, 영속성 컨텐츠 처리
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findByID(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) { // 있으면
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity); // DTO로 변환해서 return
            return boardDTO;
        } else {
            return null;
        }
    }

    // 3. UPDATE==================================================
    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntitiy(boardDTO); // entity로 변환
        boardRepository.save(boardEntity);
        return findByID(boardDTO.getId());
    }

    // 4. DELETE==================================================
    public void delete(Long id) {
        boardRepository.deleteById(id); // 삭제만 처리
    }
}
