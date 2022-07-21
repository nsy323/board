package org.nsy.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nsy.board.dto.BoardDTO;
import org.nsy.board.dto.PageRequestDTO;
import org.nsy.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 목록 조회
     * @param pageRequestDTO
     * @param model
     */
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list.........."+ pageRequestDTO);

        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }

    /**
     * 게시글 등록 화면으로 이동
     */
    @GetMapping("register")
    public void register(){
        log.info("register get........");
    }

    /**
     * 게시글 등록
     * @param boardDTO
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes){

        log.info("boardDTO....."+ boardDTO);

        Long bno = boardService.register(boardDTO);

        log.info(bno);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    /**
     * 게시물 상세보기
     * @param pageRequestDTO
     * @param bno
     * @param model
     */
    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model){

        log.info("bno : " + bno);

        BoardDTO boardDTO = boardService.get(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
    }

    /**
     * 게시물 삭제
     * @param bno
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes){
        log.info("bno : " + bno);

        boardService.removeWithReplies(bno);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    /**
     * 게시물 수정
     * @param dto
     * @param requestDTO
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/modify")
    public String modify(BoardDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("post modify...............................");

        log.info("dto : " + dto);

        boardService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        redirectAttributes.addFlashAttribute("msg", dto.getBno());
        
        return "redirect:/board/list";
    }


}
