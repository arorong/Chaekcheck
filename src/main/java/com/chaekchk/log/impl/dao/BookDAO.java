package com.chaekchk.log.impl.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chaekchk.log.vo.BookVO;

@Repository
public class BookDAO {
    
    @Autowired
    private SqlSessionTemplate mybatis;
    
    //도서 검색
    public List<BookVO> searchBooks(String bkeyword) {
        return mybatis.selectList("BookDAO.searchBooks", bkeyword);
    }
}