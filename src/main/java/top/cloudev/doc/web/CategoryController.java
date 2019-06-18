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
import top.cloudev.doc.domain.Category;
import top.cloudev.doc.dto.CategoryDTO;
import top.cloudev.doc.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 领域类 Category(文档分类) 的控制器层
 * Created by Mac.Manon on 2018/04/04
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * 提交新增文档分类的请求
     * POST: /category/create
     * @param category
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/create")
    public Map<String, Object> create(@Validated(Category.CheckCreate.class) Category category, BindingResult result, HttpServletRequest request){
        return save(category, result, request);
    }

    /**
     * 提交修改文档分类的请求
     * POST: /category/modify
     * @param category
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Map<String, Object> modify(@Validated(Category.CheckModify.class) Category category, BindingResult result, HttpServletRequest request){
        return save(category, result, request);
    }

    /**
     * 保存文档分类数据
     * @param category
     * @param result
     * @param request
     * @return
     */
    private Map<String, Object> save(Category category, BindingResult result, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();

        if (result.hasErrors()) {
            map.put("formErrors", result.getAllErrors());
            return map;
        }

        try {
            category = categoryService.save(category, request);
            map.put("category",categoryService.findOne(category));
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }

        return map;
    }

    /**
     * 显示一条文档分类的信息详情
     * GET: /category/view/9
     * @param category
     * @return
     */
    @GetMapping("/view/{categoryId}")
    public Map<String, Object> findById(@PathVariable("categoryId") Category category){
        Map<String, Object> map = Maps.newHashMap();
        map.put("category",categoryService.findOne(category));
        return map;
    }

    /**
     * 文档分类
     * GET: /category/list
     * @param pageable
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> list(@PageableDefault(sort = { "categoryId" }, direction = Sort.Direction.DESC) Pageable pageable, CategoryDTO dto){
        Map<String, Object> map = Maps.newHashMap();

        Page<Category> pagedata = categoryService.getPageData(dto,pageable);
        map.put("dto",dto);
        map.put("pagedata",pagedata);

        return map;
    }

    /**
     * 删除文档分类
     * GET: /category/delete/9?operator=1
     * @param category
     * @param request
     * @return
     */
    @GetMapping("/delete/{categoryId}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("categoryId") Category category, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();
        try {
            categoryService.remove(category, request);
            map.put("result","success");
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }
        return map;
    }

}