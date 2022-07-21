package org.nsy.board.service;

import org.nsy.board.dto.ReplyDTO;
import org.nsy.board.entity.Board;
import org.nsy.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    /**
     * 댓글 등록
     * @param replyDTO
     * @return
     */
    Long register(ReplyDTO replyDTO);

    /**
     * 특정 게시물의 댓글 목록 조회
     * @param bno
     * @return
     */
    List<ReplyDTO> getList(Long bno);

    /**
     * 댓글 수정
     * @param replyDTO
     */
    void modify(ReplyDTO replyDTO);

    /**
     * 댓글 삭제
     * @param rno
     */
    void remove(Long rno);

    /**
     * ReplyDTO를 Reply객체로 변환, Board객체의 처리가 수반됨
     * @param replyDTO
     * @return
     */
    default Reply dtoToEntity(ReplyDTO replyDTO){
        Board board = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();

        return reply;
    }

    /**
     *
     * @param reply
     * @return
     */
    default ReplyDTO entityToDTO(Reply reply){

        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();

        return replyDTO;
    }
}
