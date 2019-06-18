package top.cloudev.doc.dao;

import top.cloudev.doc.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 领域类 Category(文档分类) 的DAO Repository接口层
 * Created by Mac.Manon on 2018/04/04
 */

//@RepositoryRestResource(path = "newpath")
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Page<Category> findByIsDeletedFalse(Pageable pageable);

    // 标准查询
    Page<Category> findByNameContainingAndIsDeletedFalseAllIgnoringCase(String name, Pageable pageable);

}