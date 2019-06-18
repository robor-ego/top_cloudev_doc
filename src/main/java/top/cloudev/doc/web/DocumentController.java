package top.cloudev.doc.web;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.cloudev.doc.domain.Document;
import top.cloudev.doc.dto.DocumentDTO;
import top.cloudev.doc.service.DocumentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 领域类 Document(文档) 的控制器层
 * Created by Mac.Manon on 2018/04/04
 */
@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    /**
     * 提交新增文档的请求
     * POST: /document/create
     * @param document
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/create")
    public Map<String, Object> create(@Validated(Document.CheckCreate.class) Document document, BindingResult result, HttpServletRequest request){
        return save(document, result, request);
    }

    /**
     * 提交修改文档的请求
     * POST: /document/modify
     * @param document
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Map<String, Object> modify(@Validated(Document.CheckModify.class) Document document, BindingResult result, HttpServletRequest request){
        return save(document, result, request);
    }

    /**
     * 保存文档数据
     * @param document
     * @param result
     * @param request
     * @return
     */
    private Map<String, Object> save(Document document, BindingResult result, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();

        if (result.hasErrors()) {
            map.put("formErrors", result.getAllErrors());
            return map;
        }

        try {
            document = documentService.save(document, request);
            map.put("document",documentService.findOne(document));
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }

        return map;
    }

    /**
     * 显示一条文档的信息详情
     * GET: /document/view/9
     * @param document
     * @return
     */
    @GetMapping("/view/{documentId}")
    public Map<String, Object> findById(@PathVariable("documentId") Document document){
        Map<String, Object> map = Maps.newHashMap();
        map.put("document",documentService.findOne(document));
        return map;
    }

    /**
     * 文档
     * GET: /document/list
     * @param pageable
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> list(@PageableDefault(sort = { "documentId" }, direction = Sort.Direction.DESC) Pageable pageable, DocumentDTO dto){
        Map<String, Object> map = Maps.newHashMap();

        Page<Document> pagedata = documentService.getPageData(dto,pageable);
        map.put("dto",dto);
        map.put("pagedata",pagedata);

        return map;
    }

    /**
     * 删除文档
     * GET: /document/delete/9?operator=1
     * @param document
     * @param request
     * @return
     */
    @GetMapping("/delete/{documentId}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("documentId") Document document, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();
        try {
            documentService.remove(document, request);
            map.put("result","success");
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }
        return map;
    }

}