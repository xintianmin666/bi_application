import request from '@/utils/request'

// 查询退单规则列表
export function listRefundRule(query) {
  return request({
    url: '/oper/refundRule/list',
    method: 'get',
    params: query
  })
}

// 查询退单规则详细
export function getRefundRule(refundRuleId) {
  return request({
    url: '/oper/refundRule/' + refundRuleId,
    method: 'get'
  })
}

// 新增退单规则
export function addRefundRule(data) {
  return request({
    url: '/oper/refundRule',
    method: 'post',
    data: data
  })
}

// 修改退单规则
export function updateRefundRule(data) {
  return request({
    url: '/oper/refundRule',
    method: 'put',
    data: data
  })
}

// 删除退单规则
export function delRefundRule(refundRuleId) {
  return request({
    url: '/oper/refundRule/' + refundRuleId,
    method: 'delete'
  })
}

// 导出退单规则
export function exportRefundRule(query) {
  return request({
    url: '/oper/refundRule/export',
    method: 'get',
    params: query
  })
}