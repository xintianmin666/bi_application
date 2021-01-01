import request from '@/utils/request'

// 查询订单列表
export function listCorder(query) {
  return request({
    url: '/business/corder/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getCorder(orderCode) {
  return request({
    url: '/business/corder/' + orderCode,
    method: 'get'
  })
}

// 新增订单
export function addCorder(data) {
  return request({
    url: '/business/corder',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateCorder(data) {
  return request({
    url: '/business/corder',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delCorder(id) {
  return request({
    url: '/business/corder/' + id,
    method: 'delete'
  })
}

// 导出订单
export function exportCorder(query) {
  return request({
    url: '/business/corder/export',
    method: 'get',
    params: query
  })
}

export function jieDan(query){
  return request({
    url: '/business/corder/jieDan',
    method: 'post',
    params: query
  })
}
