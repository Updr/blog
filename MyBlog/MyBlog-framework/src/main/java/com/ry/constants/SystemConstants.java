package com.ry.constants;

public class SystemConstants {

    /**
     * 文章是正常发布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 1;

    /**
     * 文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 0;

    /**
     * 热门文章分页页数
     */
    public static final int HOTARTICLE_CURRENT = 1;

    /**
     * 热门文章每页数量
     */
    public static final int HOTARTICLE_SIZE = 10;

    /**
     * 分类是正常状态
     */
    public static final String CATEGORY_STATUS_NORMAL = "1";

    /**
     * 分类是禁用状态
     */
    public static final String  CATEGORY_STATUS_DRAFT = "0";

    /**
     * 友链审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * 友链审核未通过
     */
    public static final String  LINK_STATUS_DRAFT = "1";

    /**
     * 友链未审核
     */
    public static final String  LINK_STATUS_WAIT = "2";

    /**
     * 根评论的rootId
     */
    public static final int  COMMENT_ROOTID_ORIGINAL = -1;

    /**
     * 所回复的目标评论的userid
     */
    public static final int  COMMENT_TOCOMMENTUSERID_ORIGINAL = -1;

    /**
     * 评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * 评论类型为：友链评论
     */
    public static final String LINK_COMMENT = "1";

    /**
     * redis中文章浏览量的Key值
     */
    public static final String REDIS_VIEWCOUNT_KEY = "article:viewCount";

    /**
     * 文章增加的浏览量
     */
    public static final int VIEWCOUNT_INCREASE_VALUE = 1;

    /**
     * redis中前台登录用户信息的Key值
     */
    public static final String REDIS_USERINFO_KEY = "bloglogin:";

    /**
     * redis中后台登录用户信息的Key值
     */
    public static final String REDIS_USER_KEY = "adminlogin:";

    /**
     * 菜单类型为菜单
     */
    public static final String MENU_M = "M";

    /**
     * 菜单类型为按钮
     */
    public static final String MENU_F = "F";

    /**
     * 菜单是正常状态
     */
    public static final String MENU_STATUS_NORMAL = "1";

    /**
     * 菜单是禁用状态
     */
    public static final String MENU_STATUS_DRAFT = "0";

    /**
     * 删除状态
     */
    public static final int DELETE_YES = 1;

    /**
     * 未删除状态
     */
    public static final int DELETE_NO = 0;

    /**
     * 1表示是管理员，可以登录后台
     */
    public static final String ADMIN = "1";

    /**
     * 菜单根id
     */
    public static final Long MENU_PARENTID = 0L;

    /**
     * 用户是正常状态
     */
    public static final String USER_STATUS_NORMAL = "1";

    /**
     * 用户是禁用状态
     */
    public static final String USER_STATUS_DRAFT = "0";

    /**
     * 角色是正常状态
     */
    public static final String ROLE_STATUS_NORMAL = "1";

    /**
     * 角色是禁用状态
     */
    public static final String ROLE_STATUS_DRAFT = "0";
}
