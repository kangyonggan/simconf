package com.kangyonggan.app.simconf.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Table(name = "conf")
@Data
public class Conf implements Serializable {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 项目代码
     */
    @Column(name = "proj_code")
    private String projCode;

    /**
     * 环境
     */
    private String env;

    /**
     * 名
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建人
     */
    @Column(name = "create_username")
    private String createUsername;

    /**
     * 逻辑删除:{0:未删除, 1:已删除}
     */
    @Column(name = "is_deleted")
    private Byte isDeleted;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

}