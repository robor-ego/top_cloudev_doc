package top.cloudev.doc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import top.cloudev.doc.domain.Category;
import top.cloudev.doc.dto.CategoryDTO;
import top.cloudev.doc.dao.CategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Locale;
import static top.cloudev.doc.DocApplicationTests.Obj2Json;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 领域类 Category(文档分类) 的单元测试代码
 * Created by Mac.Manon on 2018/04/04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional// 使用@Transactional注解，确保每次测试后的数据将会被回滚
public class CategoryControllerTest {
    @Autowired
    CategoryRepository categoryRepository;

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    // 默认初始化的一条文档分类数据
    private Category c1;

    // 期望返回的数据
    private String expectData;

    // 实际调用返回的结果
    private String responseData;

    // 列表查询传参
    private CategoryDTO dto;

    // 期望获得的结果数量
    private Long expectResultCount;

    // 使用JUnit的@Before注解可在测试开始前进行一些初始化的工作
    @Before
    public void setUp() throws JsonProcessingException {
        /**---------------------测试用例赋值开始---------------------**/
        //TODO 参考实际业务中新增数据所提供的参数，基于"最少字段和数据正确的原则"，将下面的null值换为测试参数
        c1 = new Category();
        c1.setProjectId(null);
        c1.setName(null);
        c1.setSequence(null);
        c1.setCreatorUserId(1);
        categoryRepository.save(c1);
        /**---------------------测试用例赋值结束---------------------**/

        // 获取mockMvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    /**
     * 测试列表
     * @throws Exception
     */
    @Test
    public void testList() throws Exception {

        //TODO 建议借鉴下面的测试用例赋值模版构造更多数据以充分测试"无搜索列表"、"标准查询"和"高级查询"的表现

        //提示：构建"新增数据"提示：根据新增数据时客户端实际能提供的参数，依据"最少字段和数据正确的原则"构建
        //提示：构建"修改过的数据"提示：根据修改数据时客户端实际能提供的参数构建
        //提示：可以构建"非物理删除的数据"
        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Category c2 = new Category();
        c2.setProjectId(null);
        c2.setName(null);
        c2.setSequence(null);
        c2.setCreatorUserId(2);
        //提示：构造"修改过的数据"时需要给"最近修改时间"和"最近修改者"赋值
        //c2.setLastModificationTime(new Date());
        //c2.setLastModifierUserId(1);
        //提示：构造"非物理删除的数据"时需要给"已删除"、"删除时间"和"删除者"赋值
        //c2.setIsDeleted(true);
        //c2.setDeletionTime(new Date());
        //c2.setDeleterUserId(1);
        categoryRepository.save(c2);
        /**---------------------测试用例赋值结束---------------------**/


        /**
         * 测试无搜索列表
         */

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Pageable pageable=new PageRequest(0,10, Sort.Direction.DESC,"categoryId");
        // 期望获得的结果数量(默认有两个测试用例，所以值应为"2L"，如果新增了更多测试用例，请相应设定这个值)
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        Page<Category> pagedata = categoryRepository.findByIsDeletedFalse(pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/category/list")
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.pagedata.totalElements").value(expectResultCount))
                .andExpect(jsonPath("$.dto.keyword").isEmpty())
                .andExpect(jsonPath("$.dto.name").isEmpty())
                .andReturn();

        // 提取返回结果中的列表数据及翻页信息
        responseData = JsonPath.read(mvcResult.getResponse().getContentAsString(),"$.pagedata").toString();

        System.out.println("=============无搜索列表期望结果：" + expectData);
        System.out.println("=============无搜索列表实际返回：" + responseData);

        Assert.assertEquals("错误，无搜索列表返回数据与期望结果有差异",expectData,responseData);




        /**
         * 测试标准查询
         */

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        dto = new CategoryDTO();
        dto.setKeyword(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"categoryId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        String keyword = dto.getKeyword().trim();

        // 直接通过dao层接口方法获得期望的数据
        pagedata = categoryRepository.findByNameContainingAndIsDeletedFalseAllIgnoringCase(keyword, pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/category/list")
                                .param("keyword",dto.getKeyword())
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.pagedata.totalElements").value(expectResultCount))
                .andExpect(jsonPath("$.dto.keyword").value(dto.getKeyword()))
                .andExpect(jsonPath("$.dto.name").isEmpty())
                .andReturn();

        // 提取返回结果中的列表数据及翻页信息
        responseData = JsonPath.read(mvcResult.getResponse().getContentAsString(),"$.pagedata").toString();

        System.out.println("=============标准查询期望结果：" + expectData);
        System.out.println("=============标准查询实际返回：" + responseData);

        Assert.assertEquals("错误，标准查询返回数据与期望结果有差异",expectData,responseData);




    }


    /**
     * 测试新增文档分类:Post请求/category/create
     * 测试修改文档分类:Post请求/category/modify
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {
        /**
         * 测试新增文档分类
         */

         //TODO 列出新增文档分类测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Category category = new Category();
        category.setProjectId(null);
        category.setName(null);
        category.setSequence(null);

        Long operator = null;
        Long id = 4L;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/category/create")
                                .param("projectId",category.getProjectId().toString())
                                .param("name",category.getName())
                                .param("sequence",category.getSequence().toString())
                                .param("operator",operator.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"category"
                .andExpect(content().string(containsString("category")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.category.categoryId").value(id))
                .andExpect(jsonPath("$.category.projectId").value(category.getProjectId()))
                .andExpect(jsonPath("$.category.name").value(category.getName()))
                .andExpect(jsonPath("$.category.sequence").value(category.getSequence()))
                .andExpect(jsonPath("$.category.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.category.creatorUserId").value(operator))
                .andExpect(jsonPath("$.category.lastModificationTime").isEmpty())
                .andExpect(jsonPath("$.category.lastModifierUserId").value(0))
                .andExpect(jsonPath("$.category.isDeleted").value(false))
                .andExpect(jsonPath("$.category.deletionTime").isEmpty())
                .andExpect(jsonPath("$.category.deleterUserId").value(0))
                .andReturn();


        /**
         * 测试修改文档分类
         */

         //TODO 列出修改文档分类测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        category = new Category();
        category.setCategoryId(id);
        category.setProjectId(null);
        category.setName(null);
        category.setSequence(null);

        Long operator2 = null;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/category/modify")
                        .param("categoryId",id.toString())
                        .param("projectId",category.getProjectId().toString())
                        .param("name",category.getName())
                        .param("sequence",category.getSequence().toString())
                        .param("operator",operator2.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"category"
                .andExpect(content().string(containsString("category")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.category.categoryId").value(id))
                .andExpect(jsonPath("$.category.projectId").value(category.getProjectId()))
                .andExpect(jsonPath("$.category.name").value(category.getName()))
                .andExpect(jsonPath("$.category.sequence").value(category.getSequence()))
                .andExpect(jsonPath("$.category.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.category.creatorUserId").value(operator))
                .andExpect(jsonPath("$.category.lastModificationTime").isNotEmpty())
                .andExpect(jsonPath("$.category.lastModifierUserId").value(operator2))
                .andExpect(jsonPath("$.category.isDeleted").value(false))
                .andExpect(jsonPath("$.category.deletionTime").isEmpty())
                .andExpect(jsonPath("$.category.deleterUserId").value(0))
                .andReturn();
    }


    /**
     * 测试查询详情
     * @throws Exception
     */
    @Test
    public void testView() throws Exception
    {
        //TODO 下面id的值由testView方法执行时总共由testList、testSave和testView方法执行几次插入数据表决定当前的主键ID值
        Long id = 5L;

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/category/view/{id}",id)
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"category"
                .andExpect(content().string(containsString("category")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.category.categoryId").value(id))
                .andExpect(jsonPath("$.category.projectId").value(c1.getProjectId()))
                .andExpect(jsonPath("$.category.name").value(c1.getName()))
                .andExpect(jsonPath("$.category.sequence").value(c1.getSequence()))
                .andExpect(jsonPath("$.category.creationTime").value(c1.getCreationTime()))
                .andExpect(jsonPath("$.category.creatorUserId").value(c1.getCreatorUserId()))
                .andExpect(jsonPath("$.category.lastModificationTime").value(c1.getLastModificationTime()))
                .andExpect(jsonPath("$.category.lastModifierUserId").value(c1.getLastModifierUserId()))
                .andExpect(jsonPath("$.category.isDeleted").value(c1.getIsDeleted()))
                .andExpect(jsonPath("$.category.deletionTime").value(c1.getDeletionTime()))
                .andExpect(jsonPath("$.category.deleterUserId").value(c1.getDeleterUserId()))
                .andReturn();
    }


    /**
     * 测试删除
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception
    {
        //TODO 下面id的值由testView方法执行时总共由testList、testSave、testView和testDelete方法执行几次插入数据表决定当前的主键ID值
        Long id = 6L;

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/category/delete/{id}",id)
                                .param("operator","2")
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.result").value("success"))
                .andReturn();

        // 验证数据库是否已经删除
        Category category = categoryRepository.findOne(id);
        Assert.assertNotNull(category);
        Assert.assertEquals("错误，正确结果应该是true",true,category.getIsDeleted());
    }

}
