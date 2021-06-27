package vn.onltest.model.response.success;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingResultResponse<T> extends AbstractResultResponse<T> {
    private PageInfo pageInfo;
    public PagingResultResponse(int errorCode, T data, PageInfo pageInfo) {
        super(errorCode, data);
        this.pageInfo = pageInfo;
    }
}

