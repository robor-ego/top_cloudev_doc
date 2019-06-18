package top.cloudev.doc.service.impl;

import top.cloudev.doc.dao.CategoryRepository;
import top.cloudev.doc.domain.Category;
import top.cloudev.doc.dto.CategoryDTO;
import top.cloudev.doc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 领域类 Category(文档分类) 的服务实现层
 * Created by Mac.Manon on 2018/04/04
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * 保存数据
     * @param category 文档分类
     * @param request
     * @return 返回Category实体
     */
    @Override
    //@Transactional(rollbackFor={IllegalArgumentException.class}) //事务回滚：指定特定异常(如：throw new IllegalArgumentException)时，数据回滚
    @CachePut(value = "category", key = "#category.categoryId")//@CachePut缓存新增的或更新的数据到缓存，其中缓存名称为category，数据的key是category的categoryId
    public Category save(Category category, HttpServletRequest request) throws Exception {

        if(category.getCategoryId()==null){
            category.setCreatorUserId(Long.valueOf(request.getParameter("operator")));
            return categoryRepository.save(category);
        }else{
            Category c = this.findOne(category);

            if(request.getParameterValues("projectId") != null && !category.getProjectId().equals(c.getProjectId()))
                c.setProjectId(category.getProjectId());

            if(request.getParameterValues("name") != null && !category.getName().equals(c.getName()))
                c.setName(category.getName());

            if(request.getParameterValues("sequence") != null && !category.getSequence().equals(c.getSequence()))
                c.setSequence(category.getSequence());

            c.setLastModificationTime(new Date());
            c.setLastModifierUserId(Long.valueOf(request.getParameter("operator")));

            return categoryRepository.saveAndFlush(c);
        }
    }

    /**
     * 查找符合条件的一条数据
     * @param category 文档分类
     * @return 返回Category实体
     */
    @Override
    @Cacheable(value = "category", key = "#category.categoryId")//缓存key为category的categoryId数据到缓存category中
    public Category findOne(Category category) {
        return categoryRepository.findOne(category.getCategoryId());
    }

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    @Override
    public Page<Category> getPageData(CategoryDTO dto, Pageable pageable){
        if(dto.getKeyword() != null) {
            String keyword = dto.getKeyword().trim();
            return categoryRepository.findByNameContainingAndIsDeletedFalseAllIgnoringCase(keyword, pageable);
        }

        return categoryRepository.findByIsDeletedFalse(pageable);
    }

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param category 文档分类
     * @param request
     */
    @Override
    @CacheEvict(value = "category", key = "#category.categoryId")//清除key为category的categoryId数据缓存
    public void remove(Category category, HttpServletRequest request) throws Exception {
        Category c = this.findOne(category);

        c.setIsDeleted(true);
        c.setDeletionTime(new Date());
        c.setDeleterUserId(Long.valueOf(request.getParameter("operator")));

        categoryRepository.saveAndFlush(c);
    }

}