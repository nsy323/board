package org.nsy.board.repository;

import org.junit.jupiter.api.Test;
import org.nsy.board.entity.Board;
import org.nsy.board.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void insertBoard(){

        IntStream.rangeClosed(101,200).forEach(i-> {
            Member member = Member.builder().email("user"+i+"@aaa.com").build();

            Board board = Board.builder()
                    .title("Title...."+ i)
                    .content("Content..." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    @Test
    @Transactional
    public void testRead1(){
        Optional<Board> result = boardRepository.findById(100L);

        Board board = result.get();

        System.out.println(board);
        System.out.println("writer : " + board.getWriter());
    }

    @Test
    public void testReadWithWriter(){
        Object result = boardRepository.getBoradWriter(100L);

        Object[] arr = (Object[]) result;

        System.out.println("--------------------------------");

        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testBoardWithReply(){
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for(Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;

            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3(){
        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    /**
     * querydsl 동작확인
     */
    @Test
    public void testSearch1(){
        boardRepository.search1();
    }

    @Test
    public void testSearchPage(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending().and(Sort.by("content").ascending()));

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
    }
}
