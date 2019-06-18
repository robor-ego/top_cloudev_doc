package top.cloudev.doc.service.impl;

import top.cloudev.doc.dao.DocumentRepository;
import top.cloudev.doc.domain.Document;
import top.cloudev.doc.dto.DocumentDTO;
import top.cloudev.doc.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 领域类 Document(文档) 的服务实现层
 * Created by Mac.Manon on 2018/04/04
 */

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    /**
     * 保存数据
     * @param document 文档
     * @param request
     * @return 返回Document实体
     */
    @Override
    //@Transactional(rollbackFor={IllegalArgumentException.class}) //事务回滚：指定特定异常(如：throw new IllegalArgumentException)时，数据回滚
    @CachePut(value = "document", key = "#document.documentId")//@CachePut缓存新增的或更新的数据到缓存，其中缓存名称为document，数据的key是document的documentId
    public Document save(Document document, HttpServletRequest request) throws Exception {

        if(document.getDocumentId()==null){
            document.setCreatorUserId(Long.valueOf(request.getParameter("operator")));
            return documentRepository.save(document);
        }else{
            Document d = this.findOne(document);

            if(request.getParameterValues("categoryId") != null && !document.getCategoryId().equals(d.getCategoryId()))
                d.setCategoryId(document.getCategoryId());

            if(request.getParameterValues("name") != null && !document.getName().equals(d.getName()))
                d.setName(document.getName());

            if(request.getParameterValues("docType") != null && !document.getDocType().equals(d.getDocType()))
                d.setDocType(document.getDocType());

            if(request.getParameterValues("url") != null && !document.getUrl().equals(d.getUrl()))
                d.setUrl(document.getUrl());

            if(request.getParameterValues("memo") != null && !document.getMemo().equals(d.getMemo()))
                d.setMemo(document.getMemo());

            if(request.getParameterValues("accessory") != null && !document.getAccessory().equals(d.getAccessory()))
                d.setAccessory(document.getAccessory());

            d.setLastModificationTime(new Date());
            d.setLastModifierUserId(Long.valueOf(request.getParameter("operator")));

            return documentRepository.saveAndFlush(d);
        }
    }

    /**
     * 查找符合条件的一条数据
     * @param document 文档
     * @return 返回Document实体
     */
    @Override
    @Cacheable(value = "document", key = "#document.documentId")//缓存key为document的documentId数据到缓存document中
    public Document findOne(Document document) {
        return documentRepository.findOne(document.getDocumentId());
    }

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    @Override
    public Page<Document> getPageData(DocumentDTO dto, Pageable pageable){
        if(dto.getKeyword() != null) {
            String keyword = dto.getKeyword().trim();
            Specification<Document> specification=new Specification<Document>() {
                @Override
                public Predicate toPredicate(Root<Document> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate name=cb.like(cb.upper(root.get("name")), "%" + keyword.toUpperCase() + "%");
                    Predicate url=cb.like(cb.upper(root.get("url")), "%" + keyword.toUpperCase() + "%");
                    Predicate memo=cb.like(cb.upper(root.get("memo")), "%" + keyword.toUpperCase() + "%");
                    Predicate accessory=cb.like(cb.upper(root.get("accessory")), "%" + keyword.toUpperCase() + "%");
                    Predicate isDeleted=cb.equal(root.get("isDeleted").as(Boolean.class), false);
                    Predicate p = cb.and(isDeleted,cb.or(name, url, memo, accessory));

                    return p;
                }
            };
            return documentRepository.findAll(specification,pageable);
        }

        if(dto.getName() != null || dto.getUrl() != null || dto.getMemo() != null || dto.getAccessory() != null){
            return documentRepository.findByNameContainingAndUrlContainingAndMemoContainingAndAccessoryContainingAndIsDeletedFalseAllIgnoringCase(dto.getName().trim(), dto.getUrl().trim(), dto.getMemo().trim(), dto.getAccessory().trim(), pageable);
        }

        return documentRepository.findByIsDeletedFalse(pageable);
    }

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param document 文档
     * @param request
     */
    @Override
    @CacheEvict(value = "document", key = "#document.documentId")//清除key为document的documentId数据缓存
    public void remove(Document document, HttpServletRequest request) throws Exception {
        Document d = this.findOne(document);

        d.setIsDeleted(true);
        d.setDeletionTime(new Date());
        d.setDeleterUserId(Long.valueOf(request.getParameter("operator")));

        documentRepository.saveAndFlush(d);
    }

}