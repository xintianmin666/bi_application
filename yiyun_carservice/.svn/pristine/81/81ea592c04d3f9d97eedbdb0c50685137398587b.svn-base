import request from '@/utils/request'

// 查询营运城市信息列表
export function listCity(query) {
  return request({
    url: '/oper/city/list',
    method: 'get',
    params: query
  })
}

// 查询营运城市信息详细
export function getCity(operCityId) {
  return request({
    url: '/oper/city/' + operCityId,
    method: 'get'
  })
}

// 新增营运城市信息
export function addCity(data) {
  return request({
    url: '/oper/city',
    method: 'post',
    data: data
  })
}

// 修改营运城市信息
export function updateCity(data) {
  return request({
    url: '/oper/city',
    method: 'put',
    data: data
  })
}

// 删除营运城市信息
export function delCity(operCityId) {
  return request({
    url: '/oper/city/' + operCityId,
    method: 'delete'
  })
}

// 导出营运城市信息
export function exportCity(query) {
  return request({
    url: '/oper/city/export',
    method: 'get',
    params: query
  })
}
  // 导出营运城市信息
  export function changeIsvaliable(operCityId,isvaliable) {
    const data = {
      operCityId,
      isvaliable
    }
    return request({
      url: '/oper/city/changeIsvaliable',
      method: 'post',
      data: data
    })
  }
