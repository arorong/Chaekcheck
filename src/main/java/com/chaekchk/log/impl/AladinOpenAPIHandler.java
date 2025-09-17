package com.chaekchk.log.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.ParserAdapter;

import com.chaekchk.log.vo.BookVO;

/**
 * 알라딘 Open API SAX 파서 핸들러
 */
@Service
public class AladinOpenAPIHandler extends DefaultHandler {
    
	// 필드명을 Java 컨벤션에 맞게 수정 (소문자로 시작)
    private List<BookVO> books;
    private BookVO currentItem;
    private boolean inItemElement = false;
    private String tempValue;
    
    public AladinOpenAPIHandler() {
    	books = new ArrayList<BookVO>();
    }
    
    @Override
    public void startElement(String namespace, String localName, String qName, Attributes atts) 
            throws SAXException {
        
        if (localName.equals("item")) { //item : xml 태그명
            currentItem = new BookVO();
            inItemElement = true;
        } else if (inItemElement && isTargetElement(qName)) {
            tempValue = ""; // 각 element 시작 시 초기화
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (tempValue != null) {
            tempValue = tempValue + new String(ch, start, length);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName) 
            throws SAXException {
            
        if (inItemElement) {
            if (localName.equals("item")) {
                if (currentItem != null) {
                	books.add(currentItem);
                }
                currentItem = null;
                inItemElement = false;
            } else if (currentItem != null && tempValue != null) {
                // 모든 필드 처리
                setItemField(qName, tempValue.trim());
            }
        }
        tempValue = null; // element 종료 시 초기화
    }
    
    /**
     * Item 객체의 필드에 값 설정
     */
    private void setItemField(String fieldName, String value) {
        if (value == null || value.isEmpty()) {
            value = ""; // null 대신 빈 문자열
        }
        
        switch (fieldName) {
            case "title":
                currentItem.setTitle(value);
                break;
            case "author":
                currentItem.setAuthor(value);
                break;
            case "pubDate":
                // LocalDate 파싱 수정
                try {
                    if (!value.isEmpty()) {
                        // 날짜 형식에 맞게 파싱 (예: 2023-01-01)
                        LocalDate date = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        currentItem.setPubdate(date);
                    }
                } catch (DateTimeParseException e) {
                    System.err.println("날짜 파싱 오류: " + value);
                    // 파싱 실패 시 null로 설정
                    currentItem.setPubdate(null);
                }
                break;
            case "description":
                currentItem.setDescription(value);
                break;
            case "isbn":
                currentItem.setIsbn(value);
                break;
            case "cover":
                currentItem.setCover(value);
                break;
            case "publisher":
                currentItem.setPublisher(value);
                break;
            default:
                // 알 수 없는 필드는 무시
                break;
        }
    }
    
    /**
     * 파싱 대상 element인지 확인
     */
    private boolean isTargetElement(String localName) {
        return localName.equals("title") || localName.equals("link") || 
               localName.equals("author") || localName.equals("pubDate") ||
               localName.equals("description") || localName.equals("isbn") ||
               localName.equals("cover") || localName.equals("publisher");
    }
    
    /**
     * XML URL 파싱 실행 (HTML 태그 정제 포함)
     */
    public void parseXml(String xmlUrl) throws Exception {
        try {
            // 1. URL에서 XML을 문자열로 읽기
            URL url = new URL(xmlUrl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();

            // 2. 깨지는 HTML 태그 정제
            String rawXml = sb.toString();
            rawXml = rawXml.replaceAll("<hr>", "<hr/>");
            rawXml = rawXml.replaceAll("<br>", "<br/>");
            rawXml = rawXml.replaceAll("<br />", "<br/>"); // 혹시 공백 버전도 정리
            rawXml = rawXml.replaceAll("&", "&amp;");      // XML에서 & 기호 문제 방지

            // 3. SAXParser 준비
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            ParserAdapter pa = new ParserAdapter(sp.getParser());
            pa.setContentHandler(this);

            // 4. 정제된 문자열을 InputSource로 변환 후 파싱
            InputSource is = new InputSource(new StringReader(rawXml));
            pa.parse(is);

        } catch (Exception e) {
            System.err.println("XML 파싱 중 오류 발생: " + e.getMessage());
            throw new Exception("알라딘 API XML 파싱 실패", e);
        }
    }

    
    /**
     * 파싱된 Item 목록 반환 (getter 메서드)
     */
    public List<BookVO> getItems() {
        return new ArrayList<>(books); // 방어적 복사
    }
    
    /**
     * 파싱된 아이템 개수 반환
     */
    public int getItemCount() {
        return books != null ? books.size() : 0;
    }
    
    /**
     * 파싱 결과 초기화
     */
    public void clear() {
        if (books != null) {
        	books.clear();
        }
        currentItem = null;
        inItemElement = false;
        tempValue = null;
    }
}