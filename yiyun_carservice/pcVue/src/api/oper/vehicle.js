import request from '@/utils/request'

// 查询车辆信息列表
export function listVehicle(query) {
  return request({
    url: '/oper/vehicle/list',
    method: 'get',
    params: query
  })
}

export function listCanUseVehicle(query) {
  return request({
    url: '/oper/vehicle/listCanUse',
    method: 'get',
    params: query
  })
}

// 查询车辆信息详细
export function getVehicle(vehicleId) {
  return request({
    url: '/oper/vehicle/' + vehicleId,
    method: 'get'
  })
}

// 新增车辆信息
export function addVehicle(data) {
  return request({
    url: '/oper/vehicle',
    method: 'post',
    data: data
  })
}

// 修改车辆信息
export function updateVehicle(data) {
  return request({
    url: '/oper/vehicle',
    method: 'put',
    data: data
  })
}

// 删除车辆信息
export function delVehicle(vehicleId) {
  return request({
    url: '/oper/vehicle/' + vehicleId,
    method: 'delete'
  })
}

// 导出车辆信息
export function exportVehicle(query) {
  return request({
    url: '/oper/vehicle/export',
    method: 'get',
    params: query
  })
}

// 获取车辆类型
export function getCarType(query) {
  return request({
    url: '/oper/vehicle/getCarType',
    method: 'get',
    params: query
  })
}
