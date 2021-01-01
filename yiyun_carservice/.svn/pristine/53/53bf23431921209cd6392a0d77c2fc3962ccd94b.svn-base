import request from '@/utils/request'

// 查询出租车订单列表
export function listTaxiOrder(query) {
  return request({
    url: '/order/taxiOrder/list',
    method: 'get',
    params: query
  })
}

// 查询出租车订单详细
export function getTaxiOrder(id) {
  return request({
    url: '/order/taxiOrder/' + id,
    method: 'get'
  })
}

// 新增出租车订单
export function addTaxiOrder(data) {
  return request({
    url: '/order/taxiOrder',
    method: 'post',
    data: data
  })
}

// 修改出租车订单
export function updateTaxiOrder(data) {
  return request({
    url: '/order/taxiOrder',
    method: 'put',
    data: data
  })
}

// 删除出租车订单
export function delTaxiOrder(id) {
  return request({
    url: '/order/taxiOrder/' + id,
    method: 'delete'
  })
}

// 导出出租车订单
export function exportTaxiOrder(query) {
  return request({
    url: '/order/taxiOrder/export',
    method: 'get',
    params: query
  })
}