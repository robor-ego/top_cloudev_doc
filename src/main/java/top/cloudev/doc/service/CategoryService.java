package top.cloudev.doc.service;

import top.cloudev.doc.domain.Category;
import top.cloudev.doc.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;

/**
 * 领域类 Category(文档分类) 的服务接口层
 * Created by Mac.Manon on 2018/04/04
 */

public interface CategoryService {

    /**
     * 保存数据
     * @param category 文档分类
     * @param request
     * @return 返回Category实体
     */
    Category save(Category category, HttpServletRequest request) throws Exception;

    /**
     * 查找符合条件的一条数据
     * @param category 文档分类
     * @return 返回Category实体
     */
    Category findOne(Category category);

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    Page<Category> getPageData(CategoryDTO dto, Pageable pageable);

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param category 文档分类
     * @param request
     */
    void remove(Category category, HttpServletRequest request) throws Exception;

}