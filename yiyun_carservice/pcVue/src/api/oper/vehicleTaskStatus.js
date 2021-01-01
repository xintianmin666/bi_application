import request from '@/utils/request'

// 查询车辆运行记录列表
export function listVehicleTaskStatus(query) {
  return request({
    url: '/oper/vehicleTaskStatus/list',
    method: 'get',
    params: query
  })
}

// 查询驾驶员排班记录列表
export function driverList(query) {
  return request({
    url: '/oper/vehicleTaskStatus/driverList',
    method: 'get',
    params: query
  })
}
// 查询车辆排班记录列表
export function carList(query){
  return request({
    url: '/oper/vehicleTaskStatus/carList',
    method: 'get',
    params: query
  })
}

// 查询车辆运行记录详细
export function getVehicleTaskStatus(vehicleTaskStatusId) {
  return request({
    url: '/oper/vehicleTaskStatus/' + vehicleTaskStatusId,
    method: 'get'
  })
}

// 新增车辆运行记录
export function addVehicleTaskStatus(data) {
  return request({
    url: '/oper/vehicleTaskStatus',
    method: 'post',
    data: data
  })
}

// 修改车辆运行记录
export function updateVehicleTaskStatus(data) {
  return request({
    url: '/oper/vehicleTaskStatus',
    method: 'put',
    data: data
  })
}

// 删除车辆运行记录
export function delVehicleTaskStatus(vehicleTaskStatusId) {
  return request({
    url: '/oper/vehicleTaskStatus/' + vehicleTaskStatusId,
    method: 'delete'
  })
}

// 导出车辆运行记录
export function exportVehicleTaskStatus(query) {
  return request({
    url: '/oper/vehicleTaskStatus/export',
    method: 'get',
    params: query
  })


}
