import request from '@/utils/request'

// 查询车辆类型列表
export function listVehicleType(query) {
  return request({
    url: '/oper/vehicleType/list',
    method: 'get',
    params: query
  })
}

// 查询车辆类型详细
export function getVehicleType(vcehicleTypeId) {
  return request({
    url: '/oper/vehicleType/' + vcehicleTypeId,
    method: 'get'
  })
}

// 新增车辆类型
export function addVehicleType(data) {
  return request({
    url: '/oper/vehicleType',
    method: 'post',
    data: data
  })
}

// 修改车辆类型
export function updateVehicleType(data) {
  return request({
    url: '/oper/vehicleType',
    method: 'put',
    data: data
  })
}

// 删除车辆类型
export function delVehicleType(vcehicleTypeId) {
  return request({
    url: '/oper/vehicleType/' + vcehicleTypeId,
    method: 'delete'
  })
}

// 导出车辆类型
export function exportVehicleType(query) {
  return request({
    url: '/oper/vehicleType/export',
    method: 'get',
    params: query
  })
}