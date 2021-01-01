import request from '@/utils/request'

// 查询计价规则列表
export function listPriceRules(query) {
  return request({
    url: '/oper/priceRules/list',
    method: 'get',
    params: query
  })
}

// 查询计价规则详细
export function getPriceRules(id) {
  return request({
    url: '/oper/priceRules/' + id,
    method: 'get'
  })
}

// 新增计价规则
export function addPriceRules(data) {
  return request({
    url: '/oper/priceRules',
    method: 'post',
    data: data
  })
}

// 修改计价规则
export function updatePriceRules(data) {
  return request({
    url: '/oper/priceRules',
    method: 'put',
    data: data
  })
}

// 删除计价规则
export function delPriceRules(id) {
  return request({
    url: '/oper/priceRules/' + id,
    method: 'delete'
  })
}

// 导出计价规则
export function exportPriceRules(query) {
  return request({
    url: '/oper/priceRules/export',
    method: 'get',
    params: query
  })
}