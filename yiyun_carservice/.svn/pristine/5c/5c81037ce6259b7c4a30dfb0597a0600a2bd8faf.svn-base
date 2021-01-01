import request from '@/utils/request'

// 查询出租车订单列表
export function listTaxiOrder(query) {
  return request({
    url: '/order/rentalOrder/list',
    method: 'get',
    params: query
  })
}

// 查询出租车订单详细
export function getTaxiOrder(id) {
  return request({
    url: '/order/rentalOrder/' + id,
    method: 'get'
  })
}

// 新增出租车订单
export function addTaxiOrder(data) {
  return request({
    url: '/order/rentalOrder',
    method: 'post',
    data: data
  })
}

// 修改出租车订单
export function updateTaxiOrder(data) {
  return request({
    url: '/order/rentalOrder',
    method: 'put',
    data: data
  })
}

// 删除出租车订单
export function delTaxiOrder(id) {
  return request({
    url: '/order/rentalOrder/' + id,
    method: 'delete'
  })
}

// 导出出租车订单
export function exportTaxiOrder(query) {
  return request({
    url: '/order/rentalOrder/export',
    method: 'get',
    params: query
  })
}

// 查询可用车辆列表
export function getCanUseCarList(query) {
  return request({
    url: '/order/rentalOrder/getCanUseCarList',
    method: 'get',
    params: query
  })
}

//查询可用班次列表
export function getCanUseBusList(query) {
  return request({
    url: '/order/rentalOrder/getCanUseBusList',
    method: 'get',
    params: query
  })
}

export function getCanUseDriverList(query) {
  return request({
    url: '/order/rentalOrder/getCanUseDriverList',
    method: 'get',
    params: query
  })
}

// 修改出租车订单
export function updateDriver(data) {
  return request({
    url: '/order/rentalOrder/updateDriver',
    method: 'post',
    data: data
  })
}

export function deptAll(query) {
  return request({
    url: '/order/rentalOrder/deptAll',
    method: 'get',
    params: query
  })
}

export function vehicleTypeAll(query) {
  return request({
    url: '/order/rentalOrder/vehicleTypeAll',
    method: 'get',
    params: query
  })
}

export function productCodeAll(query) {
  return request({
    url: '/order/rentalOrder/productCodeAll',
    method: 'get',
    params: query
  })
}

export function getWaitlist(query) {
  return request({
    url: '/order/rentalOrder/getWaitlist',
    method: 'get',
    params: query
  })
}

// 删除调度单
export function delBusId(data) {
  return request({
    url: '/order/rentalOrder/delBusId',
    method: 'post',
    data: data
  })
}

// 获取调度单乘客列表
export function getPassengerList(busId) {
  return request({
    url: '/order/rentalOrder/getPassengerList',
    method: 'get',
    params: {
      busId: busId
    }
  })
}

// 确定调度单信息
export function confirmBusData(passengerList, ids, driverId) {
  return request({
    url: '/order/rentalOrder/confirmBusData',
    method: 'post',
    data: {
      passengerList: passengerList,
      ids: ids,
      driverId: driverId
    }
  })
}

// 重新计算
export function recalculate(passengerList, beginTime, endTime) {
  return request({
    url: '/order/rentalOrder/recalculate',
    method: 'post',
    data: {
      passengerList: passengerList,
      beginTime: beginTime,
      endTime: endTime
    }
  })
}

export function confirmNewShift(data) {
  return request({
    url: '/order/rentalOrder/confirmNewShift',
    method: 'post',
    data: data
  })
}

export function getbusOrderlist(query) {
  return request({
    url: '/order/rentalOrder/getbusOrderlist',
    method: 'get',
    params: query
  })
}


export function getCanUseOrderList(query) {
  return request({
    url: '/order/rentalOrder/getCanUseOrderList',
    method: 'get',
    params: query
  })
}



