package xin.stxkfzx.noshy.util;

public class PageCalculator {
    /**
     * 页数和行数的转换
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
