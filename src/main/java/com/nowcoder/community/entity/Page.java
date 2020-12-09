package com.nowcoder.community.entity;

/*
封装分页相关的信息
 */
public class Page {
    //当前页码
    private int current =1;
    //显示上限
    private int limit =10;
    //数据总数，用于计算总的页数
    private int rows;
    //查询路径（用于复用分页的链接）
    private String path;

    public int getCurrent() {

        return current;
    }

    public void setCurrent(int current) {
        //防止用户将当前页码乱写
        if(current>=1){
            this.current = current;
        }

    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit >=1 && limit <=100){
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows>=0){
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /*
    获取当前页的起始值
    0 9
    10 19
    获取0，10，20等值
     */
    public int getOffset() {
        return (current-1)*limit;
    }

    /*
    获取总的页数，显示页码，做边界判断
     */
    public int getTotal() {
        if(rows % limit == 0){
            return rows / limit;
        }else {
            return rows / limit + 1;
        }
    }


    /*
    从第几页到第几页
    获取起始页码
     */
    public int getFrom() {
        int from = current -2;
        return from < 1 ? 1:from;

    }

    /*
    获取结束页码
     */
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }
}
