package org.nsy.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {       //DTO와 Entity타입이라는 의미

    //DTO 리스트
    private List<DTO> dtoList;

    //총페이지번호
    private int totalPage;

    //현재페이지 번호
    private int page;

    //목록 사이즈
    private int size;

    //시작페이지번호, 끝 페이지 번호
    private int start, end;

    //이전, 다음
    private boolean prev, next;

    //페이지 번호 목록
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){ //Function<EN, DTO>는 엔터티 객체들을 DTO로 변환해 주는 기능

        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1;   //0부터 시작해서 1부터 시작
        this.size = pageable.getPageSize();

        //temp end page
        int tempEnd = (int)Math.ceil(page/10.0) * 10;

        start = tempEnd - 9;

        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.range(start, end).boxed().collect(Collectors.toList());
    }
}
