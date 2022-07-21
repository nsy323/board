package org.nsy.board.service;

import lombok.RequiredArgsConstructor;
import org.nsy.board.dto.ReplyDTO;
import org.nsy.board.entity.Board;
import org.nsy.board.entity.Reply;
import org.nsy.board.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    /**
     * 댓글 등록
     * @param replyDTO
     * @return
     */
    @Override
    public Long register(ReplyDTO replyDTO) {

        Reply reply = dtoToEntity(replyDTO);
        
        replyRepository.save(reply);
                
        return reply.getRno();
    }

    /**
     * 게시물 목록 조회
     * @param bno
     * @return
     */
    @Override
    public List<ReplyDTO> getList(Long bno) {
        List<Reply> result = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());        
        
        return result.stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());
    }

    /**
     * 댓글 수정
     * @param replyDTO
     */
    @Override
    public void modify(ReplyDTO replyDTO) {

        Reply reply = dtoToEntity(replyDTO);
        
        replyRepository.save(reply);
    }

    /**
     * 댓글 삭제
     * @param rno
     */
    @Override
    public void remove(Long rno) {

        replyRepository.deleteById(rno);
    }
}
