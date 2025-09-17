package com.chaekchk.log.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chaekchk.log.BookService;
import com.chaekchk.log.impl.dao.BookDAO;
import com.chaekchk.log.vo.BookVO;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDAO bookDAO;

    private static final String BASE_URL = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?";

    public static String buildUrl(String searchWord) throws Exception {
        StringBuilder sb = new StringBuilder(BASE_URL);
        sb.append("ttbkey=").append("ttbhope_oo2158001"); 
        sb.append("&Query=").append(URLEncoder.encode(searchWord, "UTF-8"));
        sb.append("&QueryType=Keyword");
        sb.append("&MaxResults=10");
        sb.append("&start=1");
        sb.append("&SearchTarget=Book");
        sb.append("&output=xml");
        sb.append("&Version=20131101"); 

        return sb.toString();
    }

    @Override
    public List<BookVO> searchBooks(String bkeyword) throws Exception {
        List<BookVO> results = new ArrayList<>();

        try {
             // 1) DB 검색
            List<BookVO> dbBooks = bookDAO.searchBooks(bkeyword);
            if (dbBooks != null) {
                results.addAll(dbBooks);
            }

            // 2) API 검색
            String url = buildUrl(bkeyword);
            System.out.println("알라딘 API 호출 URL: " + url);
            AladinOpenAPIHandler handler = new AladinOpenAPIHandler();
            handler.parseXml(url);
            List<BookVO> apiBooks = handler.getItems();
            if (apiBooks != null) {
                results.addAll(apiBooks);
            }
        } catch (Exception e) {
            // 로그 추가 권장
            System.err.println("도서 검색 중 오류 발생: " + e.getMessage());
            throw e;
        }

        return results;
    }
}

