import request from '@/utils/request'

// 查询计算公式列表
export function listPriceFormula(query) {
  return request({
    url: '/oper/priceFormula/list',
    method: 'get',
    params: query
  })
}

// 查询计算公式详细
export function getPriceFormula(id) {
  return request({
    url: '/oper/priceFormula/' + id,
    method: 'get'
  })
}

// 新增计算公式
export function addPriceFormula(data) {
  return request({
    url: '/oper/priceFormula',
    method: 'post',
    data: data
  })
}

// 修改计算公式
export function updatePriceFormula(data) {
  return request({
    url: '/oper/priceFormula',
    method: 'put',
    data: data
  })
}

// 删除计算公式
export function delPriceFormula(id) {
  return request({
    url: '/oper/priceFormula/' + id,
    method: 'delete'
  })
}

// 导出计算公式
export function exportPriceFormula(query) {
  return request({
    url: '/oper/priceFormula/export',
    method: 'get',
    params: query
  })
}