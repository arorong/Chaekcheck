package com.chaekchk.log;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chaekchk.log.vo.BookVO;

@Service
public interface BookService {
	List<BookVO> searchBooks(String bkeyword) throws Exception;
}
