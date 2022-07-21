package org.nsy.board.service;

import org.junit.jupiter.api.Test;
import org.nsy.board.dto.BoardDTO;
import org.nsy.board.dto.PageRequestDTO;
import org.nsy.board.dto.PageResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        BoardDTO dto = BoardDTO.builder()
                .title("test title.....")
                .content("test content.......")
                .writerEmail("user55@aaa.com")
                .build();

        Long bno = boardService.register(dto);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for(BoardDTO boardDTO : result.getDtoList()){
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet(){
        Long bno = 100L;

        BoardDTO boardDTO = boardService.get(bno);

        System.out.println(boardDTO);
    }

    /**
     * 게시글 삭제
     */
    @Test
    public void testRemove(){
        Long bno = 9L;

        boardService.removeWithReplies(bno);
    }

    /**
     * 게시글 수정
     */
    @Test
    public void testModify(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(100L)
                .title("제목 변경합니다.")
                .content("내용 변경합니다.")
                .build();

        boardService.modify(boardDTO);
    }
}
