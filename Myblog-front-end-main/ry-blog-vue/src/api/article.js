import request from '@/utils/request'

// 查询文章列表
export function articleList(query) {
    return request({
        url: '/article/articleList',
        method: 'get',
        headers: {
          isToken: false
        },
        params: query
    })
}

//查询最热文章
export function hotArticleList() {
    return request({
        url: '/article/hotArticleList',
        headers: {
          isToken: false
        },
        method: 'get'
    })
}

//获取文章详情
export function getArticle(articleId) {
    return request({
        url: '/article/' + articleId,
        headers: {
          isToken: false
        },
        method: 'get'
    })
}

//更新文章浏览量
export function updateViewCount(articleId) {
    return request({
        url: '/article/updateViewCount/' + articleId,
        headers: {
          isToken: false
        },
        method: 'put'
    })
}

//获取文章总数
export function getArticleTotal() {
    return request({
      url: '/article/articleTotal',
      headers: {
        isToken: false
      },
      method: 'get'
    })
}

// 获取所有文章总浏览量
export function getViewCountTotal() {
    return request({
      url: '/article/viewCountTotal',
      headers: {
        isToken: false
      },
      method: 'get'
    })
}

// 获取文章目录索引
export function getArticleIndex() {
  return request({
    url: '/article/articleIndex',
    headers: {
      isToken: false
    },
    method: 'get'
  })
}
