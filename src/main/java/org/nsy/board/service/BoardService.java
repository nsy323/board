package org.nsy.board.service;

import org.nsy.board.dto.BoardDTO;
import org.nsy.board.dto.PageRequestDTO;
import org.nsy.board.dto.PageResultDTO;
import org.nsy.board.entity.Board;
import org.nsy.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);    //게시글 등록

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO dto);      // 목록처리
    
    BoardDTO get(Long bno);     //게시물조회

    default Board dtoToEntity(BoardDTO dto){

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())      //Long형으로 나오므로 int로 처리
                .build();

        return boardDTO;
    }

    /**
     * 게시글 삭제
     * @param bno
     */
    void removeWithReplies(Long bno);

    /**
     * 게시글 수정
     * @param boardDTO
     */
    void modify(BoardDTO boardDTO);

}
