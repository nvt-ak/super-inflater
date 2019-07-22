import { pathOr, append, assocPath, construct, merge } from 'ramda'
import { NativeModules } from 'react-native'
import iRequest from 'superagent'
const { RNInflate } = NativeModules;
const prefix = require('superagent-prefix')('/static')

function flexibleMerge(record, key, value) {
  return typeof(key) === 'object' ?  merge(record, key) : merge(record, { [key]: value })
}

function IRModel(record) {
  this.os = pathOr('android', ['os'], record)
  this.headers = pathOr({}, ['headers'], record)
  this.body = pathOr([], ['body'], record)
  this.params = pathOr({}, ['params'], record)
  this.url = pathOr(null, ['url'], record)
  this.timeup = pathOr({ response: 30000, deadline: 60000 }, ['timeup'], record)
}

IRModel.prototype = {
  set: function(key, value) {
    const headers = flexibleMerge(this.headers, key, value)
    return assocPath(['headers'], headers, this)
  },
  platform: function(value) {
    return assocPath(['os'], value, this)
  },
  query: function(key, value) {
    const params = flexibleMerge(this.params, key, value)

    return assocPath(['params'], params, this)
  },
  send: function(key, value) {
    const body = flexibleMerge(this.body, key, value)

    return assocPath(['body'], body, this)
  },
  timeout: function(value) {
    return assocPath(['timeup'], value, this)
  },
  get: function(url) {
    const { headers, params, timeup } = this

    return iRequest
      .get(url)
      .set(headers)
      .timeout(timeup)
      .use(prefix)
      .query(params)
  },
  post: function(url) {
    const { headers, body, timeup } = this

    return iRequest
      .post(url)
      .set(headers)
      .timeout(timeup)
      .use(prefix)
      .send(body)
  },
  put: function(url) {
    const { headers, body, timeup } = this

    return iRequest
      .put(url)
      .set(headers)
      .timeout(timeup)
      .use(prefix)
      .send(body)
  },
  delete: function(url) {
    const { headers, timeup } = this

    return iRequest
      .delete(url)
      .set(headers)
      .timeout(timeup)
      .use(prefix)
  },
  inflate: function(url) {
    const { headers, params, os, timeup } = this

    if (os === 'android') {
      return RNInflate.getRequest(url, headers, params)
    }

    return request
      .get(url)
      .set(headers)
      .prefix(prefix)
      .timeout(timeup)
      .query(params)
  }
}

export default construct(IRModel)