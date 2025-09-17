package com.chaekchk.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chaekchk.log.BookService;
import com.chaekchk.log.impl.BookServiceImpl;
import com.chaekchk.log.vo.BookVO;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    
    @Autowired
    private BookServiceImpl bookServiceImpl;

 // 도서 검색
    @GetMapping("/searchBook.log")
    public String search(@RequestParam(required=false) String bkeyword, Model model) throws Exception {
        System.out.println("도서 검색 시작");

        // DB 결과
        List<BookVO> dbBooks = bookService.searchBooks(bkeyword);

        // API 결과
        List<BookVO> apiBooks = bookServiceImpl.searchBooks(bkeyword);

        // 두 리스트 합치기
        List<BookVO> mergedBooks = new ArrayList<>();
        mergedBooks.addAll(dbBooks);
        mergedBooks.addAll(apiBooks);

        // 모델에 합친 리스트만 담기
        model.addAttribute("books", mergedBooks);

        return "book/bookSearchList"; // ViewResolver가 WEB-INF/book/bookSearchList.jsp로 변환
    }

    
    // 추가: searchBook.log 엔드포인트
//    @GetMapping("/searchBook.log")
//    public String searchBookLog(@RequestParam(required=false) String searchBar, Model model) throws Exception {
//        return search(searchBar, model); // 기존 search 메서드 재활용
//    }
}
