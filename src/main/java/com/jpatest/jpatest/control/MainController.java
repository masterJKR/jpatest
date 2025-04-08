package com.jpatest.jpatest.control;

import com.jpatest.jpatest.dto.MemberDto;
import com.jpatest.jpatest.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private MemberService memberService;


    @GetMapping("/")
    public String home(){

        return "index";
    }

    @GetMapping("/member/signUp")
    public String signUp(Model model){

        // 회원가입 페이지 에서 작성한 내용을 Dto에 담는다.
        model.addAttribute("memberDto", new MemberDto() );

        return "signUp";
    }

    @PostMapping("/member/signUp")
    public String signUp(MemberDto memberDto, Model model){

        memberService.insert( memberDto );

        return "redirect:/";
    }

    @GetMapping("/member/signIn")
    public String singIn(Model model){

        model.addAttribute("memberDto", new MemberDto());
        return "signIn";
    }

    @PostMapping("/member/signIn")
    public String signIn(MemberDto memberDto , HttpSession session){

        boolean isSuccess = memberService.select(memberDto);
        if( isSuccess ) //아이디 비번 일치하면 true
            session.setAttribute("user", memberDto.getMemberId());

        return "redirect:/";
    }

    // 게시판 목록 페이지 띄우기
    @GetMapping("/board/index/{page}")
    public String boardHome(@PathVariable("page") int pageNum,
                            Model model){
        // BoardService 클래스의 메서드를 호출하여  10개의 게시글 을 가져온다.
        // 한 페이지에 10개씩 출력할 것이다.
        // 페이징을 위해 필요한 값도 가져올것이다.(전체 게시글 )


        return "boardList";
    }


    // 게시판 상세 보기



}

//  entity , dto  ->  repository -> service -> control







