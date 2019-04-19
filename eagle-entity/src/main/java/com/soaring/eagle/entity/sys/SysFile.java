package com.soaring.eagle.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-03-29
 */
@TableName("sys_file")
public class SysFile extends Model<SysFile> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一索引Id
     */
    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 文件名称
     */
    @NotNull(message = "文件名称不能为空")
    private String name;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 类型
     */
    private String type;
    /**
     * 文件存储路径
     */
    private String path;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysFile{" +
                ", id=" + id +
                ", name=" + name +
                ", filename=" + filename +
                ", type=" + type +
                ", path=" + path +
                ", size=" + size +
                ", createTime=" + createTime +
                "}";
    }
}
