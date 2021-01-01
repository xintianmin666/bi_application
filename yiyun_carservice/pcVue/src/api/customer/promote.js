import request from '@/utils/request'

// 查询推广人员列表
export function listPromote(query) {
  return request({
    url: '/customer/promote/list',
    method: 'get',
    params: query
  })
}

// 查询推广人员详细
export function getPromote(id) {
  return request({
    url: '/customer/promote/' + id,
    method: 'get'
  })
}

// 新增推广人员
export function addPromote(data) {
  return request({
    url: '/customer/promote',
    method: 'post',
    data: data
  })
}

// 修改推广人员
export function updatePromote(data) {
  return request({
    url: '/customer/promote',
    method: 'put',
    data: data
  })
}

// 删除推广人员
export function delPromote(id) {
  return request({
    url: '/customer/promote/' + id,
    method: 'delete'
  })
}

// 导出推广人员
export function exportPromote(query) {
  return request({
    url: '/customer/promote/export',
    method: 'get',
    params: query
  })
}