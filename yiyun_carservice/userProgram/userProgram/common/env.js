import _ from 'lodash'
import defaultConfig from './config.default.js'
import globalConfig from '@/config.js'

export const config = path => {
  const dfVal = _.get(defaultConfig, path)
  return _.get(globalConfig, path, dfVal)
}
// 添加版本号，用以展示  07-11添加
const apiRootUrl = globalConfig.apiRoot
const apiRootProvider = (...args) => {
  if (typeof args[0] === 'string') {
    return apiRootUrl + args[0]
  }

  const [group, ...rest] = args
  return apiRootUrl + group.reduce((result, frag, index) => result.concat(frag).concat(rest[index] || ''), '')
}
apiRootProvider[Symbol.toPrimitive] = () => apiRootUrl
export const apiRoot = apiRootProvider
// 添加版本号，用以展示  07-11添加

export const webRoot = globalConfig.webRoot

export default { apiRoot, config, webRoot }
