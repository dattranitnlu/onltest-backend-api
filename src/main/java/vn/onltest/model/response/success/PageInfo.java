package vn.onltest.model.response.success;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageInfo {
    private int currentPage;
    private long itemsPerPage;
    private long totalElements;
    private int totalPages;

    public PageInfo(int currentPage, long itemsPerPage, long totalElements, int totalPages) {
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}

