import request from '@/utils/request'

// 查询订单退单规则列表
export function listOrderRefundRules(query) {
  return request({
    url: '/oper/orderRefundRules/list',
    method: 'get',
    params: query
  })
}

// 查询订单退单规则详细
export function getOrderRefundRules(id) {
  return request({
    url: '/oper/orderRefundRules/' + id,
    method: 'get'
  })
}

// 新增订单退单规则
export function addOrderRefundRules(data) {
  return request({
    url: '/oper/orderRefundRules',
    method: 'post',
    data: data
  })
}

// 修改订单退单规则
export function updateOrderRefundRules(data) {
  return request({
    url: '/oper/orderRefundRules',
    method: 'put',
    data: data
  })
}

// 删除订单退单规则
export function delOrderRefundRules(id) {
  return request({
    url: '/oper/orderRefundRules/' + id,
    method: 'delete'
  })
}

// 导出订单退单规则
export function exportOrderRefundRules(query) {
  return request({
    url: '/oper/orderRefundRules/export',
    method: 'get',
    params: query
  })
}