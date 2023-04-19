package com.tms.springjdbc.advantage.paging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Paging implements Serializable {

    private static final long serialVersionUID = 1L;

    private long total = 0;

    private long page = 1;

    private long rows = 20;

    private long totalPage = 1;

    private long start = 0;

    private boolean isSort = true;

    private String[] sorts;

    private int sortIndex = 0;

    private boolean desc = true;

    private List<Object> params = new ArrayList<Object>();

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;

        if (0 != total && 0 != rows) {
            totalPage = total / rows;
            if (total % rows != 0) {
                totalPage++;
            }
        }

    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getRows() {
        return rows;
    }

    public void setRows(long rows) {
        this.rows = rows;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public abstract String[] getSorts();

    public void setSorts(String[] sorts) {
        this.sorts = sorts;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public boolean isSort() {
        return isSort;
    }

    public void setSort(boolean isSort) {
        this.isSort = isSort;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public long getStart() {
        return (page - 1) * rows;
    }

    public void setStart(long start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "Paging [total=" + total + ", page=" + page + ", rows=" + rows + ", totalPage=" + totalPage + ", sorts="
                + Arrays.toString(sorts) + ", desc=" + desc + ", params=" + params + "]";
    }

}