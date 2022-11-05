package com.ry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ry.constants.SystemConstants;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.AddArticleDto;
import com.ry.domain.dto.ArticleListDto;
import com.ry.domain.dto.UpdateArticleDto;
import com.ry.domain.entity.Article;
import com.ry.domain.entity.ArticleTag;
import com.ry.domain.entity.Category;
import com.ry.domain.entity.Tag;
import com.ry.domain.vo.*;
import com.ry.mapper.ArticleMapper;
import com.ry.service.ArticleService;
import com.ry.service.ArticleTagService;
import com.ry.service.CategoryService;
import com.ry.utils.BeanCopyUtils;
import com.ry.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired(required = false)
    private ArticleMapper articleMapper;

    /**
     * 前台-查询热门文章 封装成ResponseResult返回
     * @return
     */
    @Override
    @Transactional
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        // 必须是正式文章，不能是草稿
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按照浏览量进行排序（降序排序）
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多只查询10条 两种方法：1.limit限制 2.分页取第一页每页10条
        // 这里采取的是第二种方法
        Page<Article> page = new Page(SystemConstants.HOTARTICLE_CURRENT,SystemConstants.HOTARTICLE_SIZE);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();
//        // VO根据业务需要进行封装
//        List<HotArticleVo> articleVos =new ArrayList<>();
//        // bean拷贝
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        // 从 redis中获取浏览量
        articleVos.forEach(hotArticleVo -> {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_VIEWCOUNT_KEY, hotArticleVo.getId().toString());
            hotArticleVo.setViewCount(Long.valueOf(viewCount));
        });
        return ResponseResult.okResult(articleVos);
    }

    @Override
    @Transactional
    // 查询文章列表 封装成ResponseResult返回
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果有categoryId就要查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        // 必须是正式文章，不能是草稿
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 置顶文章需要在前 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        // 文章列表按照发表时间降序排列
        lambdaQueryWrapper.orderByDesc(Article::getCreateTime);
        // 分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);
        List<Article> articles = page.getRecords();
        // 查询categoryName article表中是没有的
        // 使用for循环的方式
//        for(Article article : articles){
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        // 使用stream流的方式
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        // 封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        // 从 redis中获取浏览量
        articleListVos.forEach(articleListVo -> {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_VIEWCOUNT_KEY, articleListVo.getId().toString());
            articleListVo.setViewCount(Long.valueOf(viewCount));
        });
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    // 查询文章详情
    public ResponseResult getArticleDetail(Long id) {
        // 根据id查询文章
        Article article =getById(id);
        // 从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_VIEWCOUNT_KEY, id.toString());
        article.setViewCount(viewCount.longValue());
        // 转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // 根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        // 封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        // 更新redis中对应id的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.REDIS_VIEWCOUNT_KEY,id.toString(),SystemConstants.VIEWCOUNT_INCREASE_VALUE);
        return ResponseResult.okResult();
    }

    /**
     * 后台文章管理-写博文
     * @param addArticleDto 新增文章Dto
     * @return
     */
    @Override
    public ResponseResult addArticle(AddArticleDto addArticleDto) {
        // 添加博客
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);

        //添加 博客和标签的关联
        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    /**
     * 后台文章管理-返回文章列表 查询文章 支持模糊查询
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param articleListDto 查询数据
     * @return
     */
    @Override
    public ResponseResult pageArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        // 进行模糊查询
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(articleListDto.getTitle()),Article::getTitle,articleListDto.getTitle());
        queryWrapper.like(StringUtils.hasText(articleListDto.getSummary()),Article::getSummary,articleListDto.getSummary());
        // 分页
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        // 封装返回
        List<Article> articles = page.getRecords();
        List<ArticleManagementListVo> articleManagementListVos = BeanCopyUtils.copyBeanList(articles, ArticleManagementListVo.class);
        PageVo pageVo = new PageVo(articleManagementListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 后台文章管理-修改文章 根据id查询文章详情
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleById(Long id) {
        // 获取tags
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,id);
        List<ArticleTag> articleTags = articleTagService.list(queryWrapper);
        List<Long> tags = articleTags.stream()
                .map(articleTag -> articleTag.getTagId())
                .collect(Collectors.toList());
        // 进行封装返回
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        articleVo.setTags(tags);
        return ResponseResult.okResult(articleVo);
    }

    /**
     * 后台文章管理-修改文章 更新文章
     * @param updateArticleDto 更新文章dto
     * @return
     */
    @Override
    @Transactional
    public ResponseResult updateArticle(UpdateArticleDto updateArticleDto) {
        // 更新博客
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        updateById(article);

        //更新 博客和标签的关联
        Long articleId = updateArticleDto.getId();
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,articleId);
        articleTagService.remove(queryWrapper);
        List<ArticleTag> articleTags = updateArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    /**
     * 后台文章管理-删除文章 根据文章id删除文章
     * @param id 文章id
     * @return
     */
    @Override
    public ResponseResult deleteArticleById(Long id) {
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,id);
        updateWrapper.set(Article::getDelFlag,SystemConstants.DELETE_YES);
        update(articleMapper.selectById(id),updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 前台-获取文章总浏览量
     * @return
     */
    @Override
    public ResponseResult getViewCountTotal() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = list(queryWrapper);
        AtomicInteger viewcount = new AtomicInteger();
        articles.forEach(article -> {
            Integer cacheMapValue = redisCache.getCacheMapValue(SystemConstants.REDIS_VIEWCOUNT_KEY, article.getId().toString());
            viewcount.addAndGet(cacheMapValue);
        });
        int total = viewcount.get();
        return ResponseResult.okResult(total);
    }

    /**
     * 前台-搜索-获取文章索引
     * @return
     */
    @Override
    public ResponseResult getArticleIndex() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = list(queryWrapper);
        List<ArticleIndexVo> articleIndexVos = BeanCopyUtils.copyBeanList(articles, ArticleIndexVo.class);
        return ResponseResult.okResult(articleIndexVos);
    }

    /**
     * 后台-文章管理-批量删除文章
     * @param ids
     * @return
     */
    @Override
    public ResponseResult removeArticles(List<Long> ids) {
        ids.forEach(id ->{
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article::getId,id)
                    .set(Article::getDelFlag,SystemConstants.DELETE_YES);
            update(getById(id),updateWrapper);
        });
        return ResponseResult.okResult();
    }

}
