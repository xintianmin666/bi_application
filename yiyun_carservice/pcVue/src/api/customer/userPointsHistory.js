import request from '@/utils/request'

// 查询积分变动明细列表
export function listUserPointsHistory(query) {
  return request({
    url: '/customer/userPointsHistory/list',
    method: 'get',
    params: query
  })
}

// 查询积分变动明细详细
export function getUserPointsHistory(id) {
  return request({
    url: '/customer/userPointsHistory/' + id,
    method: 'get'
  })
}

// 新增积分变动明细
export function addUserPointsHistory(data) {
  return request({
    url: '/customer/userPointsHistory',
    method: 'post',
    data: data
  })
}

// 修改积分变动明细
export function updateUserPointsHistory(data) {
  return request({
    url: '/customer/userPointsHistory',
    method: 'put',
    data: data
  })
}

// 删除积分变动明细
export function delUserPointsHistory(id) {
  return request({
    url: '/customer/userPointsHistory/' + id,
    method: 'delete'
  })
}

// 导出积分变动明细
export function exportUserPointsHistory(query) {
  return request({
    url: '/customer/userPointsHistory/export',
    method: 'get',
    params: query
  })
}