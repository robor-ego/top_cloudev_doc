package top.cloudev.doc.dao;

import top.cloudev.doc.domain.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 领域类 Document(文档) 的DAO Repository接口层
 * Created by Mac.Manon on 2018/04/04
 */

//@RepositoryRestResource(path = "newpath")
public interface DocumentRepository extends JpaRepository<Document,Long>, JpaSpecificationExecutor {

    Page<Document> findByIsDeletedFalse(Pageable pageable);

    //TODO:请根据实际需要调整方法名的构造 高级查询
    Page<Document> findByNameContainingAndUrlContainingAndMemoContainingAndAccessoryContainingAndIsDeletedFalseAllIgnoringCase(String name, String url, String memo, String accessory, Pageable pageable);

}