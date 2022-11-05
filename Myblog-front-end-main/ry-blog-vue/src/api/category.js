import request from '@/utils/request'

// 查询分类列表
export function getCategoryList() {
    return request({
        url: '/category/getCategoryList',
        headers: {
          isToken: false
        },
        method: 'get'
    })
}

 //获取分类总数
export function getCategoryTotal() {
    return request({
      url: '/category/categoryTotal',
      headers: {
        isToken: false
      },
      method: 'get'
    })
}
