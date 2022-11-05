package com.ry.runner;

import com.ry.constants.SystemConstants;
import com.ry.domain.entity.Article;
import com.ry.mapper.ArticleMapper;
import com.ry.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired(required = false)
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 查询博客信息 id viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        // 存储到redis中
        redisCache.setCacheMap(SystemConstants.REDIS_VIEWCOUNT_KEY,viewCountMap);
    }
}
