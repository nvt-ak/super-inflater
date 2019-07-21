import { pathOr, append, assocPath, construct, merge } from 'ramda'
import { NativeModules } from 'react-native'
import iRequest from 'superagent'
const { RNInflate } = NativeModules;
const prefix = require('superagent-prefix')('/static')

function flexibleMerge(record, key, value) {
  return typeof(key) === 'object' ?  merge(record, key) : merge(record, { [key]: value })
}

function IRModel(record) {
  this.platform = pathOr('android', ['platform'], record)
  this.headers = pathOr({}, ['headers'], record)
  this.body = pathOr([], ['body'], record)
  this.params = pathOr({}, ['params'], record)
  this.url = pathOr(null, ['url'], record)
  this.timeout = pathOr({ response: 30000, deadline: 60000 }, ['timeout'], record)
}

IRModel.prototype = {
  set: function(key, value) {
    const headers = flexibleMerge(this.headers, key, value)
    return assocPath(['headers'], headers, this)
  },
  platform: function(value) {
    return assocPath(['platform'], value, this)
  },
  query: function(key, value) {
    const params = flexibleMerge(this.params, key, value)

    return assocPath(['params'], params, this)
  },
  send: function(key, value) {
    const body = flexibleMerge(this.body, key, value)

    return assocPath(['body'], body, this)
  },
  timeout: function(timeout) {
    return assocPath(['timeout'], timeout, this)
  },
  get: function(url) {
    const { headers, params, timeout } = this

    return iRequest
      .get(url)
      .set(headers)
      .timeout(timeout)
      .use(prefix)
      .query(params)
  },
  post: function(url) {
    const { headers, body, timeout } = this

    return iRequest
      .post(url)
      .set(headers)
      .timeout(timeout)
      .use(prefix)
      .send(body)
  },
  put: function(url) {
    const { headers, body, timeout } = this

    return iRequest
      .put(url)
      .set(headers)
      .timeout(timeout)
      .use(prefix)
      .send(body)
  },
  delete: function(url) {
    const { headers, timeout } = this

    return iRequest
      .delete(url)
      .set(headers)
      .timeout(timeout)
      .use(prefix)
  },
  inflate: function(url) {
    const { headers, params, platform, timeout } = this

    if (platform === 'android') {
      return RNInflate.getRequest(url, headers, params)
    }

    return request
      .get(url)
      .set(headers)
      .prefix(prefix)
      .timeout(timeout)
      .query(params)
  }
}

export default construct(IRModel)