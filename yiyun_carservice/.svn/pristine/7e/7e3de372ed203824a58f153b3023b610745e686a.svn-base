import request from '@/utils/request'

// 查询C端用户信息列表
export function listCUserInfo(query) {
  return request({
    url: '/customer/cUserInfo/list',
    method: 'get',
    params: query
  })
}

// 查询C端用户信息详细
export function getCUserInfo(id) {
  return request({
    url: '/customer/cUserInfo/' + id,
    method: 'get'
  })
}

// 新增C端用户信息
export function addCUserInfo(data) {
  return request({
    url: '/customer/cUserInfo',
    method: 'post',
    data: data
  })
}

// 修改C端用户信息
export function updateCUserInfo(data) {
  return request({
    url: '/customer/cUserInfo',
    method: 'put',
    data: data
  })
}

// 删除C端用户信息
export function delCUserInfo(id) {
  return request({
    url: '/customer/cUserInfo/' + id,
    method: 'delete'
  })
}

// 导出C端用户信息
export function exportCUserInfo(query) {
  return request({
    url: '/customer/cUserInfo/export',
    method: 'get',
    params: query
  })
}