package controller.model;

/**
 * @description designed for page turning&display information
 * @create 2020-05-02-21-53
 **/
public class PageInfo {

    private int pageNo;

    private int size;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public PageInfo(int pageNo, int size) {
        this.pageNo = pageNo;
        this.size = size;
    }
}
