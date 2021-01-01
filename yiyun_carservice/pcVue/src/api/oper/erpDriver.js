import request from '@/utils/request'

// 查询erp驾驶员列表
export function listErpDriver(query) {
  return request({
    url: '/erpDriver/erpDriver/list',
    method: 'get',
    params: query
  })
}

// 查询erp驾驶员详细
export function getErpDriver(fDriverid) {
  return request({
    url: '/erpDriver/erpDriver/' + fDriverid,
    method: 'get'
  })
}

// 新增erp驾驶员
export function addErpDriver(data) {
  return request({
    url: '/erpDriver/erpDriver',
    method: 'post',
    data: data
  })
}

// 修改erp驾驶员
export function updateErpDriver(data) {
  return request({
    url: '/erpDriver/erpDriver',
    method: 'put',
    data: data
  })
}

// 删除erp驾驶员
export function delErpDriver(fDriverid) {
  return request({
    url: '/erpDriver/erpDriver/' + fDriverid,
    method: 'delete'
  })
}

// 导出erp驾驶员
export function exportErpDriver(query) {
  return request({
    url: '/erpDriver/erpDriver/export',
    method: 'get',
    params: query
  })
}