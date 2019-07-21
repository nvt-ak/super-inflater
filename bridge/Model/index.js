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
  this.params = pathOr([], ['params'], record)
  this.url = pathOr(null, ['url'], record)
  this.response = pathOr(30000, ['response'], record)
  this.deadline = pathOr(60000, ['deadline'], record)
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
    const { deadline, response } = timeout

    return assocPath(['response'], response, this)
      .assocPath(['deadline'], deadline, this)
  },
  get: function(url) {
    const { headers, params, response, deadline } = this

    return iRequest
      .get(url)
      .set(headers)
      .timeout({
        response: response, // Wait 5 seconds for the server to start sending,
        deadline: deadline // but allow 1 minute for the file to finish loading.
      })
      .use(prefix)
      .query(params)
  },
  post: function(url) {
    const { headers, body, response, deadline } = this

    return iRequest
      .post(url)
      .set(headers)
      .timeout({
        response: response, // Wait 5 seconds for the server to start sending,
        deadline: deadline // but allow 1 minute for the file to finish loading.
      })
      .use(prefix)
      .send(body)
  },
  put: function(url) {
    const { headers, body, response, deadline } = this

    return iRequest
      .put(url)
      .set(headers)
      .timeout({
        response: response, // Wait 5 seconds for the server to start sending,
        deadline: deadline // but allow 1 minute for the file to finish loading.
      })
      .use(prefix)
      .send(body)
  },
  delete: function(url) {
    const { headers, response, deadline } = this

    return iRequest
      .delete(url)
      .set(headers)
      .timeout({
        response: response, // Wait 5 seconds for the server to start sending,
        deadline: deadline // but allow 1 minute for the file to finish loading.
      })
      .use(prefix)
  },
  inflate: function(url) {
    const { headers, params, platform } = this

    if (platform === 'android') {
      return RNInflate.getRequest(url)
    }

    return request
      .get(url)
      .set(headers)
      .prefix(prefix)
      .query(params)
  }
}

export default construct(IRModel)