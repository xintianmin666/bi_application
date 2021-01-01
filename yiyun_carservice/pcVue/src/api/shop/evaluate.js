import request from '@/utils/request'

// 查询用户评价列表
export function listEvaluate(query) {
  return request({
    url: '/shop/evaluate/list',
    method: 'get',
    params: query
  })
}

// 查询用户评价详细
export function getEvaluate(id) {
  return request({
    url: '/shop/evaluate/' + id,
    method: 'get'
  })
}

// 新增用户评价
export function addEvaluate(data) {
  return request({
    url: '/shop/evaluate',
    method: 'post',
    data: data
  })
}

// 修改用户评价
export function updateEvaluate(data) {
  return request({
    url: '/shop/evaluate',
    method: 'put',
    data: data
  })
}

// 删除用户评价
export function delEvaluate(id) {
  return request({
    url: '/shop/evaluate/' + id,
    method: 'delete'
  })
}

// 导出用户评价
export function exportEvaluate(query) {
  return request({
    url: '/shop/evaluate/export',
    method: 'get',
    params: query
  })
}