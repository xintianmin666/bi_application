import request from '@/utils/request'

// 查询城市站点列表
export function listSite(query) {
  return request({
    url: '/oper/site/list',
    method: 'get',
    params: query
  })
}
export function listAllSite(query) {
  return request({
    url: '/oper/site/listAll',
    method: 'get',
    params: query
  })
}


// 查询城市站点详细
export function getSite(siteId) {
  return request({
    url: '/oper/site/' + siteId,
    method: 'get'
  })
}

// 新增城市站点
export function addSite(data) {
  return request({
    url: '/oper/site',
    method: 'post',
    data: data
  })
}

// 修改城市站点
export function updateSite(data) {
  return request({
    url: '/oper/site',
    method: 'put',
    data: data
  })
}

// 删除城市站点
export function delSite(siteId) {
  return request({
    url: '/oper/site/' + siteId,
    method: 'delete'
  })
}

// 导出城市站点
export function exportSite(query) {
  return request({
    url: '/oper/site/export',
    method: 'get',
    params: query
  })
}
