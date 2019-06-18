package top.cloudev.doc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import top.cloudev.doc.domain.Document;
import top.cloudev.doc.dto.DocumentDTO;
import top.cloudev.doc.dao.DocumentRepository;
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
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
 * 领域类 Document(文档) 的单元测试代码
 * Created by Mac.Manon on 2018/04/04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional// 使用@Transactional注解，确保每次测试后的数据将会被回滚
public class DocumentControllerTest {
    @Autowired
    DocumentRepository documentRepository;

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    // 默认初始化的一条文档数据
    private Document d1;

    // 期望返回的数据
    private String expectData;

    // 实际调用返回的结果
    private String responseData;

    // 列表查询传参
    private DocumentDTO dto;

    // 期望获得的结果数量
    private Long expectResultCount;

    // 使用JUnit的@Before注解可在测试开始前进行一些初始化的工作
    @Before
    public void setUp() throws JsonProcessingException {
        /**---------------------测试用例赋值开始---------------------**/
        //TODO 参考实际业务中新增数据所提供的参数，基于"最少字段和数据正确的原则"，将下面的null值换为测试参数
        d1 = new Document();
        d1.setCategoryId(null);
        d1.setName(null);
        d1.setDocType(null);
        d1.setUrl(null);
        d1.setMemo(null);
        d1.setAccessory(null);
        d1.setCreatorUserId(1);
        documentRepository.save(d1);
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
        Document d2 = new Document();
        d2.setCategoryId(null);
        d2.setName(null);
        d2.setDocType(null);
        d2.setUrl(null);
        d2.setMemo(null);
        d2.setAccessory(null);
        d2.setCreatorUserId(2);
        //提示：构造"修改过的数据"时需要给"最近修改时间"和"最近修改者"赋值
        //d2.setLastModificationTime(new Date());
        //d2.setLastModifierUserId(1);
        //提示：构造"非物理删除的数据"时需要给"已删除"、"删除时间"和"删除者"赋值
        //d2.setIsDeleted(true);
        //d2.setDeletionTime(new Date());
        //d2.setDeleterUserId(1);
        documentRepository.save(d2);
        /**---------------------测试用例赋值结束---------------------**/


        /**
         * 测试无搜索列表
         */

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Pageable pageable=new PageRequest(0,10, Sort.Direction.DESC,"documentId");
        // 期望获得的结果数量(默认有两个测试用例，所以值应为"2L"，如果新增了更多测试用例，请相应设定这个值)
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        Page<Document> pagedata = documentRepository.findByIsDeletedFalse(pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/document/list")
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
                .andExpect(jsonPath("$.dto.url").isEmpty())
                .andExpect(jsonPath("$.dto.memo").isEmpty())
                .andExpect(jsonPath("$.dto.accessory").isEmpty())
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
        dto = new DocumentDTO();
        dto.setKeyword(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"documentId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        String keyword = dto.getKeyword().trim();

        // 直接通过dao层接口方法获得期望的数据
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
        pagedata = documentRepository.findAll(specification,pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/document/list")
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
                .andExpect(jsonPath("$.dto.url").isEmpty())
                .andExpect(jsonPath("$.dto.memo").isEmpty())
                .andExpect(jsonPath("$.dto.accessory").isEmpty())
                .andReturn();

        // 提取返回结果中的列表数据及翻页信息
        responseData = JsonPath.read(mvcResult.getResponse().getContentAsString(),"$.pagedata").toString();

        System.out.println("=============标准查询期望结果：" + expectData);
        System.out.println("=============标准查询实际返回：" + responseData);

        Assert.assertEquals("错误，标准查询返回数据与期望结果有差异",expectData,responseData);




        /**
         * 测试高级查询
         */

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        dto = new DocumentDTO();
        dto.setName(null);
        dto.setUrl(null);
        dto.setMemo(null);
        dto.setAccessory(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"documentId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        pagedata = documentRepository.findByNameContainingAndUrlContainingAndMemoContainingAndAccessoryContainingAndIsDeletedFalseAllIgnoringCase(dto.getName().trim(), dto.getUrl().trim(), dto.getMemo().trim(), dto.getAccessory().trim(), pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();
        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/document/list")
                                .param("name",dto.getName())
                                .param("url",dto.getUrl())
                                .param("memo",dto.getMemo())
                                .param("accessory",dto.getAccessory())
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.pagedata.totalElements").value(expectResultCount))
                .andExpect(jsonPath("$.dto.keyword").isEmpty())
                .andExpect(jsonPath("$.dto.name").value(dto.getName()))
                .andExpect(jsonPath("$.dto.url").value(dto.getUrl()))
                .andExpect(jsonPath("$.dto.memo").value(dto.getMemo()))
                .andExpect(jsonPath("$.dto.accessory").value(dto.getAccessory()))
                .andReturn();

        // 提取返回结果中的列表数据及翻页信息
        responseData = JsonPath.read(mvcResult.getResponse().getContentAsString(),"$.pagedata").toString();

        System.out.println("=============高级查询期望结果：" + expectData);
        System.out.println("=============高级查询实际返回：" + responseData);

        Assert.assertEquals("错误，高级查询返回数据与期望结果有差异",expectData,responseData);

    }


    /**
     * 测试新增文档:Post请求/document/create
     * 测试修改文档:Post请求/document/modify
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {
        /**
         * 测试新增文档
         */

         //TODO 列出新增文档测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Document document = new Document();
        document.setCategoryId(null);
        document.setName(null);
        document.setDocType(null);
        document.setUrl(null);
        document.setMemo(null);
        document.setAccessory(null);

        Long operator = null;
        Long id = 4L;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/document/create")
                                .param("categoryId",document.getCategoryId().toString())
                                .param("name",document.getName())
                                .param("docType",document.getDocType().toString())
                                .param("url",document.getUrl())
                                .param("memo",document.getMemo())
                                .param("accessory",document.getAccessory())
                                .param("operator",operator.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"document"
                .andExpect(content().string(containsString("document")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.document.documentId").value(id))
                .andExpect(jsonPath("$.document.categoryId").value(document.getCategoryId()))
                .andExpect(jsonPath("$.document.name").value(document.getName()))
                .andExpect(jsonPath("$.document.docType").value(document.getDocType()))
                .andExpect(jsonPath("$.document.url").value(document.getUrl()))
                .andExpect(jsonPath("$.document.memo").value(document.getMemo()))
                .andExpect(jsonPath("$.document.accessory").value(document.getAccessory()))
                .andExpect(jsonPath("$.document.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.document.creatorUserId").value(operator))
                .andExpect(jsonPath("$.document.lastModificationTime").isEmpty())
                .andExpect(jsonPath("$.document.lastModifierUserId").value(0))
                .andExpect(jsonPath("$.document.isDeleted").value(false))
                .andExpect(jsonPath("$.document.deletionTime").isEmpty())
                .andExpect(jsonPath("$.document.deleterUserId").value(0))
                .andReturn();


        /**
         * 测试修改文档
         */

         //TODO 列出修改文档测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        document = new Document();
        document.setDocumentId(id);
        document.setCategoryId(null);
        document.setName(null);
        document.setDocType(null);
        document.setUrl(null);
        document.setMemo(null);
        document.setAccessory(null);

        Long operator2 = null;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/document/modify")
                        .param("documentId",id.toString())
                        .param("categoryId",document.getCategoryId().toString())
                        .param("name",document.getName())
                        .param("docType",document.getDocType().toString())
                        .param("url",document.getUrl())
                        .param("memo",document.getMemo())
                        .param("accessory",document.getAccessory())
                        .param("operator",operator2.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"document"
                .andExpect(content().string(containsString("document")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.document.documentId").value(id))
                .andExpect(jsonPath("$.document.categoryId").value(document.getCategoryId()))
                .andExpect(jsonPath("$.document.name").value(document.getName()))
                .andExpect(jsonPath("$.document.docType").value(document.getDocType()))
                .andExpect(jsonPath("$.document.url").value(document.getUrl()))
                .andExpect(jsonPath("$.document.memo").value(document.getMemo()))
                .andExpect(jsonPath("$.document.accessory").value(document.getAccessory()))
                .andExpect(jsonPath("$.document.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.document.creatorUserId").value(operator))
                .andExpect(jsonPath("$.document.lastModificationTime").isNotEmpty())
                .andExpect(jsonPath("$.document.lastModifierUserId").value(operator2))
                .andExpect(jsonPath("$.document.isDeleted").value(false))
                .andExpect(jsonPath("$.document.deletionTime").isEmpty())
                .andExpect(jsonPath("$.document.deleterUserId").value(0))
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
                        MockMvcRequestBuilders.get("/document/view/{id}",id)
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"document"
                .andExpect(content().string(containsString("document")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.document.documentId").value(id))
                .andExpect(jsonPath("$.document.categoryId").value(d1.getCategoryId()))
                .andExpect(jsonPath("$.document.name").value(d1.getName()))
                .andExpect(jsonPath("$.document.docType").value(d1.getDocType()))
                .andExpect(jsonPath("$.document.url").value(d1.getUrl()))
                .andExpect(jsonPath("$.document.memo").value(d1.getMemo()))
                .andExpect(jsonPath("$.document.accessory").value(d1.getAccessory()))
                .andExpect(jsonPath("$.document.creationTime").value(d1.getCreationTime()))
                .andExpect(jsonPath("$.document.creatorUserId").value(d1.getCreatorUserId()))
                .andExpect(jsonPath("$.document.lastModificationTime").value(d1.getLastModificationTime()))
                .andExpect(jsonPath("$.document.lastModifierUserId").value(d1.getLastModifierUserId()))
                .andExpect(jsonPath("$.document.isDeleted").value(d1.getIsDeleted()))
                .andExpect(jsonPath("$.document.deletionTime").value(d1.getDeletionTime()))
                .andExpect(jsonPath("$.document.deleterUserId").value(d1.getDeleterUserId()))
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
                        MockMvcRequestBuilders.get("/document/delete/{id}",id)
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
        Document document = documentRepository.findOne(id);
        Assert.assertNotNull(document);
        Assert.assertEquals("错误，正确结果应该是true",true,document.getIsDeleted());
    }

}
