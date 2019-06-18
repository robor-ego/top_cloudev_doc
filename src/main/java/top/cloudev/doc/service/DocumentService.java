package top.cloudev.doc.service;

import top.cloudev.doc.domain.Document;
import top.cloudev.doc.dto.DocumentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;

/**
 * 领域类 Document(文档) 的服务接口层
 * Created by Mac.Manon on 2018/04/04
 */

public interface DocumentService {

    /**
     * 保存数据
     * @param document 文档
     * @param request
     * @return 返回Document实体
     */
    Document save(Document document, HttpServletRequest request) throws Exception;

    /**
     * 查找符合条件的一条数据
     * @param document 文档
     * @return 返回Document实体
     */
    Document findOne(Document document);

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    Page<Document> getPageData(DocumentDTO dto, Pageable pageable);

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param document 文档
     * @param request
     */
    void remove(Document document, HttpServletRequest request) throws Exception;

}