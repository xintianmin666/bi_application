import request from '@/utils/request'

// 查询调度订单列表
export function listDispatchOrder(query) {
  return request({
    url: '/.oper/dispatchOrder/list',
    method: 'get',
    params: query
  })
}

// 查询调度订单详细
export function getDispatchOrder(dispatchOrderId) {
  return request({
    url: '/.oper/dispatchOrder/' + dispatchOrderId,
    method: 'get'
  })
}

// 新增调度订单
export function addDispatchOrder(data) {
  return request({
    url: '/.oper/dispatchOrder',
    method: 'post',
    data: data
  })
}

// 修改调度订单
export function updateDispatchOrder(data) {
  return request({
    url: '/.oper/dispatchOrder',
    method: 'put',
    data: data
  })
}

// 删除调度订单
export function delDispatchOrder(dispatchOrderId) {
  return request({
    url: '/.oper/dispatchOrder/' + dispatchOrderId,
    method: 'delete'
  })
}

// 导出调度订单
export function exportDispatchOrder(query) {
  return request({
    url: '/.oper/dispatchOrder/export',
    method: 'get',
    params: query
  })
}