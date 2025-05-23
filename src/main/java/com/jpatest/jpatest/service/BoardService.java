package com.jpatest.jpatest.service;


import com.jpatest.jpatest.dto.BoardDto;
import com.jpatest.jpatest.dto.BoardListDto;
import com.jpatest.jpatest.dto.CommentDto;
import com.jpatest.jpatest.entity.Board;
import com.jpatest.jpatest.entity.Comment;
import com.jpatest.jpatest.repository.BoardRepository;
import com.jpatest.jpatest.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    // @Autowired  또는 lombok의 @RequiredArgsConstructor 를 사용하여
    //  주입 한다.   주입하기전에  반드시 빈등록 되어야한다.
    // 빈 - @Service, @Repository, @Bean 등등


    public BoardDto getBoard(int boardId){

        Board board = boardRepository.findById(boardId).get();

        List<Comment> commentList = commentRepository.findByBoardId(boardId);

        List<CommentDto> commentDtos = new ArrayList<>();
        for(Comment comment : commentList){
            CommentDto commentDto = CommentDto.from(comment);
            commentDtos.add(commentDto);
        }

        BoardDto boardDto = BoardDto.of(board,commentDtos);
        return boardDto;
    }



    // 게시글 10개 가져와서 목록 출력하기 위한 메서드
    public Page<BoardListDto> getBoardList(Pageable pageable){
        List<BoardListDto> boardListDtos = new ArrayList<>();

        // 레포지토리를 통해 테이블에서 조회하기 - pageable을 이용하여  10개 가져오기
        // pageable 값설정은  control에서 한다.
        List<Board> boards = boardRepository.findAllByOrderByIdDesc(pageable);

        // 전체 게시글 갯수
        Long  total = boardRepository.count();

        // entity - > dto
        for(Board board : boards){
            BoardListDto boardListDto = BoardListDto.from(board);
            boardListDtos.add(boardListDto);
        }

        return new PageImpl<>(boardListDtos, pageable, total);
    }
}
