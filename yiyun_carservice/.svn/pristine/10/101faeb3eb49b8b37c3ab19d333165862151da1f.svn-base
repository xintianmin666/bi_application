import request from '@/utils/request'

// 查询erp车辆列表
export function listErpCar(query) {
  return request({
    url: '/oper/erpCar/list',
    method: 'get',
    params: query
  })
}

// 查询erp车辆详细
export function getErpCar(fId) {
  return request({
    url: '/oper/erpCar/' + fId,
    method: 'get'
  })
}

// 新增erp车辆
export function addErpCar(data) {
  return request({
    url: '/oper/erpCar',
    method: 'post',
    data: data
  })
}

// 修改erp车辆
export function updateErpCar(data) {
  return request({
    url: '/oper/erpCar',
    method: 'put',
    data: data
  })
}

// 删除erp车辆
export function delErpCar(fId) {
  return request({
    url: '/oper/erpCar/' + fId,
    method: 'delete'
  })
}

// 导出erp车辆
export function exportErpCar(query) {
  return request({
    url: '/oper/erpCar/export',
    method: 'get',
    params: query
  })
}