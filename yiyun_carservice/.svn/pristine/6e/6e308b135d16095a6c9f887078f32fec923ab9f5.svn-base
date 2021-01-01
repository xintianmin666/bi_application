import request from '@/utils/request'

// 查询店铺商户列表
export function listShop(query) {
  return request({
    url: '/shop/shop/list',
    method: 'get',
    params: query
  })
}

// 查询店铺商户详细
export function getShop(id) {
  return request({
    url: '/shop/shop/' + id,
    method: 'get'
  })
}

// 新增店铺商户
export function addShop(data) {
  return request({
    url: '/shop/shop',
    method: 'post',
    data: data
  })
}

// 修改店铺商户
export function updateShop(data) {
  return request({
    url: '/shop/shop',
    method: 'put',
    data: data
  })
}

// 删除店铺商户
export function delShop(id) {
  return request({
    url: '/shop/shop/' + id,
    method: 'delete'
  })
}

// 导出店铺商户
export function exportShop(query) {
  return request({
    url: '/shop/shop/export',
    method: 'get',
    params: query
  })
}