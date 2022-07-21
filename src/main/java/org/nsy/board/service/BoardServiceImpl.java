package org.nsy.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nsy.board.dto.BoardDTO;
import org.nsy.board.dto.PageRequestDTO;
import org.nsy.board.dto.PageResultDTO;
import org.nsy.board.entity.Board;
import org.nsy.board.entity.Member;
import org.nsy.board.repository.BoardRepository;
import org.nsy.board.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository repository;       //자동주입 final
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);

        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO dto) {
        log.info(dto);

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member) en[1], (Long) en[2]));

//        Page<Object[]> result = repository.getBoardWithReplyCount(dto.getPageable(Sort.by("bno").descending()));

        Page<Object[]> result = repository.searchPage( dto.getType(), dto.getKeyword(), dto.getPageable(Sort.by("bno").descending()) );

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;

        return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    /**
     * 게시글 삭제(댓글포함)
     *
     * @param bno
     */
    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);   //댓글삭제
        repository.deleteById(bno);    //게시글삭제
    }

    /**
     * 게시글 수정
     *
     * @param boardDTO
     */
    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = repository.getOne(boardDTO.getBno());

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        repository.save(board);

    }


}
