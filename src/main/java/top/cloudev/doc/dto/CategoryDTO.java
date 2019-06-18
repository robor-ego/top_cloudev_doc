package top.cloudev.doc.dto;

import java.io.Serializable;

/**
 * Category(文档分类) 的DTO数据传输对象
 * TODO 可根据搜索时的实际需要调整字段，删除不适合用作搜索的字段
 * Created by Mac.Manon on 2018/04/04
 */

public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关键字(标准查询)
     */
    private String keyword;

    /**
     * 分类名称
     */
    private String name;

    /**
     *空构造函数
     *
     */
    public CategoryDTO(){
    }

    /**
     *带参构造函数
     *
     */
    public CategoryDTO(String keyword, String name){
        this.keyword = keyword;
        this.name = name;
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

}