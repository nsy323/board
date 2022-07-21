package org.nsy.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nsy.board.dto.ReplyDTO;
import org.nsy.board.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;    //자동주입을 위한 final

    /**
     * 댓글목록조회
     * @param bno
     * @return
     */
    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno){

        log.info("bno : " + bno);

        return new ResponseEntity<>( replyService.getList(bno), HttpStatus.OK );
    }

    /**
     * 댓글 등록
     * @param replyDTO
     * @return
     */
    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){

        log.info(replyDTO);

        Long rno = replyService.register(replyDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     * @param rno
     * @return
     */
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno){

        log.info("rno : " + rno);

        replyService.remove(rno);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /**
     * 댓글 수정
     * @param replyDTO
     * @return
     */
    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO){

        log.info(replyDTO);

        replyService.modify(replyDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
