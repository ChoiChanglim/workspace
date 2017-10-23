package com.nhn.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Paging {
	private static int pageBlockCount = 10;
	private int totalCount;
	private int nowPage;
	private int rowCount;
	private int totalPage;
	private int begin;
	private int end;
	private String qstr;

	/**
	 * custom
	 */
	private String method;


	private ArrayList<Integer> pageBlock;


	public String getMORE(){
		StringBuffer html = new StringBuffer();
		if(this.totalPage > this.nowPage){
			html.append("<div class='pagingMore'>");
			html.append("<span class='pagingMoreBtn' page='"+this.nowPage+"'>더보기</span>");
			html.append("</div>");
		}
		return html.toString();
	}

	public String getHTML(){
		StringBuffer html = new StringBuffer();


		String ahref = "";

		html.append("<div class='paging'>");


		pageBlock = calcPageBlock();
		String endClass="";
		for(int i=0;i<pageBlock.size();i++){
			int pageNums = pageBlock.get(i);
			if(this.method.equals("GET")){
				qstr = RequestUtil.qstrUpdate("page", pageNums, this.qstr);
				ahref = "?"+qstr;
			}else{
				ahref="javascript:paging("+pageNums+");";
			}



			if(pageNums==nowPage){
				html.append("	<strong class='on'>"+pageNums+"</strong>");
			}else{
				html.append("	<a href='"+ahref+"'>"+pageNums+"</a>");
			}
		}
		if(this.nowPage > 1){
            int prevPage= this.nowPage-1;
            if(this.method.equals("GET")){
                qstr = RequestUtil.qstrUpdate("page", prevPage, this.qstr);
                ahref = "?"+qstr;
            }else{
                ahref="javascript:paging("+prevPage+");";

            }
            html.append("   <a href='"+ahref+"' class='btn_prev'>◀ 이전</a>");
        }else{
            html.append("   <a href='#' class='btn_prev'>◀ 이전</a>");
        }


		if(this.totalPage > this.nowPage){
			int nextPage= this.nowPage+1;
			if(this.method.equals("GET")){
				qstr = RequestUtil.qstrUpdate("page", nextPage, this.qstr);
				ahref = "?"+qstr;
			}else{
				ahref="javascript:paging("+nextPage+");";
			}
			html.append(" <a href='"+ahref+"' class='btn_next'>다음 ▶</a>");
		}else{
		    html.append(" <a href='#' class='btn_next'>다음 ▶</a>");
		}


		html.append("</div>");

		return html.toString();
	}

	/**
	 * 우파루랜드 팬아트 참여자목록
	 * @return
	 */
	public String getWooparooLandHTML(){
        StringBuffer html = new StringBuffer();


        String ahref = "";

        html.append("<ul class='pagination'>");


        pageBlock = calcPageBlock();
        String endClass="";

        if(this.nowPage > 1){
            int prevPage= this.nowPage-1;
            if(this.method.equals("GET")){
                qstr = RequestUtil.qstrUpdate("page", prevPage, this.qstr);
                ahref = "?"+qstr;
            }else{
                ahref="javascript:paging("+prevPage+");";

            }
            html.append("   <li><a href='"+ahref+"' class='btn_prev'>◀ 이전</a></li>");
        }else{
            html.append("   <li><a href='#' class='btn_prev'>◀ 이전</a></li>");
        }

        for(int i=0;i<pageBlock.size();i++){
            html.append("<li>");
            int pageNums = pageBlock.get(i);
            if(this.method.equals("GET")){
                qstr = RequestUtil.qstrUpdate("page", pageNums, this.qstr);
                ahref = "?"+qstr;
            }else{
                ahref="javascript:paging("+pageNums+");";
            }



            if(pageNums==nowPage){
                html.append("   <a><strong class='on'>"+pageNums+"</strong></a>");
            }else{
                html.append("   <a href='"+ahref+"'>"+pageNums+"</a>");
            }
            html.append("</li>");
        }



        if(this.totalPage > this.nowPage){
            int nextPage= this.nowPage+1;
            if(this.method.equals("GET")){
                qstr = RequestUtil.qstrUpdate("page", nextPage, this.qstr);
                ahref = "?"+qstr;
            }else{
                ahref="javascript:paging("+nextPage+");";
            }
            html.append(" <li><a href='"+ahref+"' class='btn_next'>다음 ▶</a></li>");
        }else{
            html.append(" <li><a href='#' class='btn_next'>다음 ▶</a></li>");
        }


        html.append("</ul>");

        return html.toString();
    }


	/**
	 * 히원 일본 마이쿠폰
	 * @return
	 */
	public String getJpHTML(){
        StringBuffer html = new StringBuffer();


        String ahref = "";

        html.append("<div class='paging'>");
        html.append("   <span class='paging_inner'>");


        pageBlock = calcPageBlock();
        String endClass="";
        for(int i=0;i<pageBlock.size();i++){
            int pageNums = pageBlock.get(i);
            if(this.method.equals("GET")){
                qstr = RequestUtil.qstrUpdate("page", pageNums, this.qstr);
                ahref = "?"+qstr;
            }else{
                ahref="javascript:paging("+pageNums+");";
            }



            if(pageNums==nowPage){
                html.append("   <strong class='on'>"+pageNums+"</strong>");
            }else{
                html.append("   <a href='"+ahref+"'>"+pageNums+"</a>");
            }
        }
        if(this.nowPage > 1){
            int prevPage= this.nowPage-1;
            if(this.method.equals("GET")){
                qstr = RequestUtil.qstrUpdate("page", prevPage, this.qstr);
                ahref = "?"+qstr;
            }else{
                ahref="javascript:paging("+prevPage+");";

            }
            html.append("   <a href='"+ahref+"' class='btn_prev'><span>前へ</span></a>");
        }else{
            html.append("   <a href='#' class='btn_prev'><span>前へ</span></a>");
        }


        if(this.totalPage > this.nowPage){
            int nextPage= this.nowPage+1;
            if(this.method.equals("GET")){
                qstr = RequestUtil.qstrUpdate("page", nextPage, this.qstr);
                ahref = "?"+qstr;
            }else{
                ahref="javascript:paging("+nextPage+");";
            }
            html.append(" <a href='"+ahref+"' class='btn_next'><span>次へ</span></a>");
        }else{
            html.append(" <a href='#' class='btn_next'><span>次へ</span></a>");
        }


        html.append("   </span>");
        html.append("</div>");

        return html.toString();
    }

	public ArrayList<Integer> calcPageBlock(){
		ArrayList<Integer> pb = new ArrayList<Integer>();
		int start = this.nowPage-((int)Math.ceil(pageBlockCount/2) - 1);
		if(start < 1){
			start = 1;
		}
		int end = this.totalPage;
		for(int i=0;i<pageBlockCount;i++){
			int index = start+i;

			if(index > end){
				index = start-(pageBlockCount-i);

			}
			if(index > 0){
				pb.add(index);
			}
		}

		Collections.sort(pb);
		setPageBlock(pb);
		return pb;
	}

	public Paging(int totalCount, String nowPage, int rowCount, String qstr) {
		super();
		this.totalCount = totalCount;
		this.nowPage = StringPageToInt(nowPage);
		this.rowCount = rowCount;
		int totalPage = (totalCount - 1) / rowCount + 1;
		this.totalPage=totalPage;
		this.method = "GET";

		if(qstr==null || qstr==""){
			qstr = "page=1";
		}
		this.qstr = qstr;

		begin = (this.nowPage - 1) * rowCount;
		end = rowCount * this.nowPage - 1;
		if(end > totalCount-1){
			end = totalCount-1;
		}
		setBegin(begin);
		setEnd(end);
	}

	/**
	 * GM툴 로그 페이징
	 * @param totalCount
	 * @param rowCount
	 * @param info
	 */
	public Paging(int totalCount, String rowCount, String nowPage) {
		super();
		if(rowCount==null){
			rowCount="100";
		}
		String stripCommerRow = rowCount.replaceAll("[,]", "");
		int iRowCount = new Integer(stripCommerRow);

		this.totalCount = totalCount;
		this.nowPage = StringPageToInt(nowPage);
		this.rowCount = iRowCount;
		int totalPage = (totalCount - 1) / iRowCount + 1;
		this.totalPage=totalPage;
		this.method = "POST";

		begin = (this.nowPage - 1) * iRowCount;
		end = iRowCount * this.nowPage - 1;
		if(end > totalCount-1){
			end = totalCount-1;
		}
		setBegin(begin);
		setEnd(end);
	}

	public int StringPageToInt(String nowPageString){
		int nowPageInt = 1;

		if(nowPageString==null || nowPageString==""){
			return nowPageInt;
		}


		Pattern integerPattern = Pattern.compile("^[0-9]*$");
		Matcher matchsInteger = integerPattern.matcher(nowPageString);
		boolean isInteger = matchsInteger.matches();
		//System.out.println(isInteger);
		if(isInteger==false){
			return nowPageInt;
		}

		nowPageInt = new Integer(nowPageString);


		if(nowPageInt < 1){
			nowPageInt = 1;
		}
		return nowPageInt;
	}
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}


	public int getRowCount() {
		return rowCount;
	}


	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}


	public ArrayList<Integer> getPageBlock() {
		return pageBlock;
	}


	public void setPageBlock(ArrayList<Integer> pageBlock) {
		this.pageBlock = pageBlock;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public String getQstr() {
		return qstr;
	}
	public void setQstr(String qstr) {
		this.qstr = qstr;
	}

	public static int getPageBlockCount() {
		return pageBlockCount;
	}

}
