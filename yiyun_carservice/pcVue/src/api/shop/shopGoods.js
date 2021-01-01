import request from '@/utils/request'

// 查询店铺产品列表
export function listShopGoods(query) {
  return request({
    url: '/shop/shopGoods/list',
    method: 'get',
    params: query
  })
}

// 查询店铺产品详细
export function getShopGoods(id) {
  return request({
    url: '/shop/shopGoods/' + id,
    method: 'get'
  })
}

// 新增店铺产品
export function addShopGoods(data) {
  return request({
    url: '/shop/shopGoods',
    method: 'post',
    data: data
  })
}

// 修改店铺产品
export function updateShopGoods(data) {
  return request({
    url: '/shop/shopGoods',
    method: 'put',
    data: data
  })
}

// 删除店铺产品
export function delShopGoods(id) {
  return request({
    url: '/shop/shopGoods/' + id,
    method: 'delete'
  })
}

// 导出店铺产品
export function exportShopGoods(query) {
  return request({
    url: '/shop/shopGoods/export',
    method: 'get',
    params: query
  })
}