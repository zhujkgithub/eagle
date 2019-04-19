package com.soaring.eagle.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.soaring.eagle.common.shiro.entity.IUser;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/21
 * Time: 13:09
 */
@TableName("sys_user")
public class SysUser extends Model<SysUser> implements IUser {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 登录名
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 证件号码
     */
    @TableField("id_card")
    private String idCard;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 生日
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 头像
     */
    private String photo;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 最后登录IP
     */
    @TableField("last_login_ip")
    private String lastLoginIp;
    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
    /**
     * 登录次数
     */
    @TableField("login_count")
    private Long loginCount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人
     */
    @TableField("create_user")
    private Long createUser;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 修改人
     */
    @TableField("update_user")
    private String updateUser;
    /**
     * 最后修改时间
     */
    @TableField("update_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 是否锁定，0：无限制，1：锁定
     */
    private Integer locked;
    /**
     * 是否展示 0：显示 1：隐藏
     */
    private Integer disabled;
    /**
     * 是否删除：0：可用，1：删除
     */
    private Integer deleted;
    /**
     * 审核状态：0审核通过  1禁用  2待审核
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 用户级别：只能管理比自己级别小的
     */
    private Integer rank;
    /**
     * 登录错误时间
     */
    @TableField("error_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date errorTime;
    /**
     * 登录错误ip
     */
    @TableField("error_ip")
    private String errorIp;
    /**
     * 登录错误次数
     */
    @TableField("error_count")
    private Long errorCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    @Override
    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    @Override
    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    public String getErrorIp() {
        return errorIp;
    }

    public void setErrorIp(String errorIp) {
        this.errorIp = errorIp;
    }

    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                ", id=" + id +
                ", username=" + username +
                ", password=" + password +
                ", nickname=" + nickname +
                ", realname=" + realname +
                ", idCard=" + idCard +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", age=" + age +
                ", mobile=" + mobile +
                ", telephone=" + telephone +
                ", photo=" + photo +
                ", email=" + email +
                ", lastLoginIp=" + lastLoginIp +
                ", lastLoginTime=" + lastLoginTime +
                ", loginCount=" + loginCount +
                ", remark=" + remark +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                ", locked=" + locked +
                ", disabled=" + disabled +
                ", deleted=" + deleted +
                ", status=" + status +
                ", sort=" + sort +
                ", rank=" + rank +
                ", errorTime=" + errorTime +
                ", errorIp=" + errorIp +
                ", errorCount=" + errorCount +
                "}";
    }
}
