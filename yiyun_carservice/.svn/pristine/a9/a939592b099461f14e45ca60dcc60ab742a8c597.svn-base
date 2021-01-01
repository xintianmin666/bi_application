import request from '@/utils/request'

// 查询产品站点列表
export function listProductSite(query) {
  return request({
    url: '/oper/productSite/list',
    method: 'get',
    params: query
  })
}

// 查询产品价格列表
export function listByProductId(productId) {
  return request({
    url: '/oper/productSite/listByProductId',
    method: 'get',
    params: {
      tProductId:productId
    }
  })
}

// 查询产品站点详细
export function getProductSite(productSiteId) {
  return request({
    url: '/oper/productSite/' + productSiteId,
    method: 'get'
  })
}

// 新增产品站点
export function addProductSite(siteList,productId) {
  return request({
    url: '/oper/productSite',
    method: 'post',
    data: {
      productSiteList:siteList,
      tProductId:productId
    }
  })
}

// 修改产品站点
export function updateProductSite(data) {
  return request({
    url: '/oper/productSite',
    method: 'put',
    data: data
  })
}

// 删除产品站点
export function delProductSite(productSiteId) {
  return request({
    url: '/oper/productSite/' + productSiteId,
    method: 'delete'
  })
}

// 导出产品站点
export function exportProductSite(query) {
  return request({
    url: '/oper/productSite/export',
    method: 'get',
    params: query
  })
}
