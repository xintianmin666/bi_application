import request from '@/utils/request'

// 查询包拼车订单价格明细列表
export function listOrderPrice(query) {
  return request({
    url: '/order/orderPrice/list',
    method: 'get',
    params: query
  })
}

export function listByOrderCode(orderCode) {
  return request({
    url: '/order/orderPrice/listByOrderCode',
    method: 'get',
    params: {
      orderCode: orderCode
    }
  })
}

// 查询包拼车订单价格明细详细
export function getOrderPrice(id) {
  return request({
    url: '/order/orderPrice/' + id,
    method: 'get'
  })
}

// 新增包拼车订单价格明细
export function addOrderPrice(priceList, orderCode) {
  return request({
    url: '/order/orderPrice',
    method: 'post',
    data: {
      orderPriceList: priceList,
      orderCode: orderCode
    }
  })
}

// 修改包拼车订单价格明细
export function updateOrderPrice(data) {
  return request({
    url: '/order/orderPrice',
    method: 'put',
    data: data
  })
}

// 删除包拼车订单价格明细
export function delOrderPrice(id) {
  return request({
    url: '/order/orderPrice/' + id,
    method: 'delete'
  })
}

// 导出包拼车订单价格明细
export function exportOrderPrice(query) {
  return request({
    url: '/order/orderPrice/export',
    method: 'get',
    params: query
  })
}
