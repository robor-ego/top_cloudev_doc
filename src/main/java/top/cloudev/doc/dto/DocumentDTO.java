package top.cloudev.doc.dto;

import java.io.Serializable;

/**
 * Document(文档) 的DTO数据传输对象
 * TODO 可根据搜索时的实际需要调整字段，删除不适合用作搜索的字段
 * Created by Mac.Manon on 2018/04/04
 */

public class DocumentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关键字(标准查询)
     */
    private String keyword;

    /**
     * 文档名称
     */
    private String name;

    /**
     * 文档访问网址
     */
    private String url;

    /**
     * 文档描述
     */
    private String memo;

    /**
     * 附件
     */
    private String accessory;

    /**
     *空构造函数
     *
     */
    public DocumentDTO(){
    }

    /**
     *带参构造函数
     *
     */
    public DocumentDTO(String keyword, String name, String url, String memo, String accessory){
        this.keyword = keyword;
        this.name = name;
        this.url = url;
        this.memo = memo;
        this.accessory = accessory;
    }

    /**
     *Getter,Setter
     *
     */
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

}