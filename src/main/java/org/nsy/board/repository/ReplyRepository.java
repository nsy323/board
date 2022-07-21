package org.nsy.board.repository;

import lombok.extern.log4j.Log4j2;
import org.nsy.board.entity.Board;
import org.nsy.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    /**
     * 댓글 삭제
     * @param bno
     */
    @Modifying
    @Query("delete from Reply r where r.board.bno = :bno ")
    void deleteByBno(Long bno);

    /**
     * 댓글목록 조회
     * @param board
     * @return
     */
    List<Reply> getRepliesByBoardOrderByRno(Board board);
}
