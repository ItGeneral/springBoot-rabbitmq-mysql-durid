package com.kindergarten.common;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *分页基本信息
 * 
 * @author mashengwen
 * @date 2015-1-15
 */
@XmlRootElement
@JsonIgnoreProperties({"startRow","endRow"}) 
public class Pagination implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 6452434771972569248L;

	/** 当前页数, 与jquery grid命名一致 */
    private int page = 1;

    /** 每页显示记录数, 与jquery grid命名一致 */
    private int rows = 10;

    /** 总记录数, 与jquery grid命名一致 */
    private int records;

    /** 总页数, 与jquery grid命名一致 */
    private int total = 1;

    /** 排序属性名, 与jquery grid命名一致 */
    private String sidx;

    /** 排序desc及asc标志, 与jquery grid命名一致 */
    private String sord;

    private boolean search = false;

    public Pagination() {
    }
    
    /**
     * 自定义行数Pagination构造函数
     * @param page			当前页
     * @param customRows	自定义行数
     */
    public Pagination(int page, int customRows)
    {
    	this.setPage(page);
    	this.setRowsByCustom(customRows);
    }
    
    /**
     * @param page
     * @param rows
     * @param sidx
     * @param sord
     */
    public Pagination(int page, int rows, String sidx, String sord) {
    	setPage(page);
        setRows(rows);
        setSidx(sidx);
        setSord(sord);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page < 1) {
            page = 1;
        }
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows < 0) {
            rows = 10;
        }
        if (rows > 50000){
            rows = 50000;
        }
        this.rows = rows;
    }
    
    public void setRowsByCustom(int customRows)
    {
    	this.rows = customRows;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public void countRecords(int records) {
        if (records < 0) {
            records = 0;
        }
        this.records = records;
        if (records % rows == 0) {
            total = records / rows;
        } else {
            total = records / rows + 1;
        }
        if (page > total) {
            page = total;
        } else if (page < 0) {
            page = 1;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }


    public int getStartRow() {
        return (this.page - 1) * this.rows + 1;
    }

    public int getEndRow() {
        int last = this.page * this.rows;
        return this.records > 0 && last > this.records ? this.records : last;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
    	if(search) {
    		this.page = 1;
    	}
    	this.search = search;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + page +
                ", rows=" + rows +
                ", records=" + records +
                ", total=" + total +
                ", sidx='" + sidx + '\'' +
                ", sord='" + sord + '\'' +
                ", search=" + search +
                '}';
    }
}
