import request from '@/utils/request'

// 查询地区信息列表
export function listArea(query) {
  return request({
    url: '/oper/area/list',
    method: 'get',
    params: query
  })
}

// 查询地区信息详细
export function getArea(id) {
  return request({
    url: '/oper/area/' + id,
    method: 'get'
  })
}

// 新增地区信息
export function addArea(data) {
  return request({
    url: '/oper/area',
    method: 'post',
    data: data
  })
}

// 修改地区信息
export function updateArea(data) {
  return request({
    url: '/oper/area',
    method: 'put',
    data: data
  })
}

// 删除地区信息
export function delArea(id) {
  return request({
    url: '/oper/area/' + id,
    method: 'delete'
  })
}

// 导出地区信息
export function exportArea(query) {
  return request({
    url: '/oper/area/export',
    method: 'get',
    params: query
  })
}

// 地区下拉树结构
export function areaTreeselect() {
  return request({
    url: '/oper/area/treeselect',
    method: 'get'
  })
}
