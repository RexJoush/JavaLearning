package com.joush.mybatis.cfg;

/**
 *  用于封装执行的 sql 语句，和结果类型的全类名
 */
public class Mapper {

    private String queryString; // sql
    private String resultType; // 全类名

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
