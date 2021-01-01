import request from '@/utils/request'


// 查询产品价格列表
export function listProductPrice(query) {
  return request({
    url: '/oper/productPrice/list',
    method: 'get',
    params: query
  })
}

// 查询产品价格列表
export function listByProductId(productId) {
  return request({
    url: '/oper/productPrice/listByProductId',
    method: 'get',
    params: {
      productId:productId
    }
  })
}

// 查询产品价格详细
export function getProductPrice(productPriceId) {
  return request({
    url: '/oper/productPrice/' + productPriceId,
    method: 'get'
  })
}

// 新增产品价格
export function addProductPrice(priceList,productId) {
  return request({
    url: '/oper/productPrice',
    method: 'post',
    data: {
      productPriceList:priceList,
      productId:productId
    }
  })
}

// 修改产品价格
export function updateProductPrice(data) {
  return request({
    url: '/oper/productPrice',
    method: 'put',
    data: data
  })
}

// 删除产品价格
export function delProductPrice(productPriceId) {
  return request({
    url: '/oper/productPrice/' + productPriceId,
    method: 'delete'
  })
}

// 导出产品价格
export function exportProductPrice(query) {
  return request({
    url: '/oper/productPrice/export',
    method: 'get',
    params: query
  })
}
