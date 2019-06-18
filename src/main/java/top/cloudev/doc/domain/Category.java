package top.cloudev.doc.domain;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Category(文档分类) 的领域层
 * Created by Mac.Manon on 2018/04/04
 */

@Entity
@DynamicUpdate(true)
@Table(name="tbl_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface CheckCreate{};
    public interface CheckModify{};

    /**
     * 文档分类ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long categoryId;

    /**
     * 文档分类所属的项目
     */
    //@Range(min=value,max=value, groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "project_id")
    private Long projectId;

    /**
     * 分类名称
     */
    @NotBlank(groups={CheckCreate.class, CheckModify.class})
    @Length(min = 1, max = 50, groups={CheckCreate.class, CheckModify.class})
    //@Pattern(regexp = "", groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "name", length = 50)
    private String name = "";

    /**
     * 排序
     */
    //@Range(min=value,max=value, groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "sequence")
    private Integer sequence;

    /**
     * 创建时间
     */
    @Column(nullable = false, name = "creation_time", updatable=false)
    private Date creationTime = new Date();

    /**
     * 创建者
     */
    @Column(nullable = false, name = "creator_user_id", updatable=false)
    private long creatorUserId;

    /**
     * 最近修改时间
     */
    @Column(name = "last_modification_time")
    private Date lastModificationTime;

    /**
     * 最近修改者
     */
    @Column(name = "last_modifier_user_id")
    private long lastModifierUserId;

    /**
     * 已删除
     */
    @Column(nullable = false, name = "is_deleted")
    private Boolean isDeleted = false;

    /**
     * 删除时间
     */
    @Column(name = "deletion_time")
    private Date deletionTime;

    /**
     * 删除者
     */
    @Column(name = "deleter_user_id")
    private long deleterUserId;

    /**
     *TODO 请将数据表的名称及字段名称加入到国际化语言包中:
     TableName.category=\u6587\u6863\u5206\u7c7b
     FieldName.category.categoryId=\u6587\u6863\u5206\u7c7bID
     FieldName.category.projectId=\u6587\u6863\u5206\u7c7b\u6240\u5c5e\u7684\u9879\u76ee
     FieldName.category.name=\u5206\u7c7b\u540d\u79f0
     FieldName.category.sequence=\u6392\u5e8f
     *
     *
     *Tip:
     *如果后续加入引用类型字段，可考虑使用@Valid注解；
     *如果后续加入Collection、Map和数组类型字段，可考虑使用@Size(max, min)注解；
     */

    /**
     *空构造函数
     *
     */
    public Category(){
        super();
    }

    /**
     *带参构造函数
     *
     */
    public Category(Long projectId,String name,Integer sequence,Date creationTime,long creatorUserId,Date lastModificationTime,long lastModifierUserId,Boolean isDeleted,Date deletionTime,long deleterUserId){
        super();
        this.projectId = projectId;
        this.name = name;
        this.sequence = sequence;
        this.creationTime = creationTime;
        this.creatorUserId = creatorUserId;
        this.lastModificationTime = lastModificationTime;
        this.lastModifierUserId = lastModifierUserId;
        this.isDeleted = isDeleted;
        this.deleterUserId = deleterUserId;
        this.deletionTime = deletionTime;
    }

    /**
     *Getter,Setter
     *
     */
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public Date getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(Date lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public long getLastModifierUserId() {
        return lastModifierUserId;
    }

    public void setLastModifierUserId(long lastModifierUserId) {
        this.lastModifierUserId = lastModifierUserId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(Date deletionTime) {
        this.deletionTime = deletionTime;
    }

    public long getDeleterUserId() {
        return deleterUserId;
    }

    public void setDeleterUserId(long deleterUserId) {
        this.deleterUserId = deleterUserId;
    }

    @Override
    public String toString() {
        return "Category [categoryId=" + categoryId + "," + "projectId=" + projectId + "," + "name=" + name + "," + "sequence=" + sequence + ",creationTime=" + creationTime + ",creatorUserId=" + creatorUserId + ",lastModificationTime=" + lastModificationTime + ",lastModifierUserId=" + lastModifierUserId + ",isDeleted=" + isDeleted + ",deletionTime=" + deletionTime + ",deleterUserId=" + deleterUserId + "]";
    }
}