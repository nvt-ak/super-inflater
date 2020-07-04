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
  this.body = pathOr({}, ['body'], record)
  this.params = pathOr({}, ['params'], record)
  this.url = pathOr(null, ['url'], record)
  this.timeup = pathOr({ response: 60000, deadline: 90000 }, ['timeup'], record)
  this.files = pathOr([], ['files'], record)
}

IRModel.prototype = {
  fix: function(key, value) {
    return assocPath([key], value, this)
  },
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
  field: function(body) {
    const { headers, timeup, os, url, files } = this
    if (os === 'android') {
      return RNInflate.multiPost(url, headers, body, files)
    }
    var req = iRequest
    .post(url)
    .set(headers)
    .timeout(timeup)
    .use(prefix)
    files.map((file, i)=> {
      req.attach(`file ${i}`, file.uri)
    })
    return req.field('dataSet', body)
    
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
  getInflate: function(url) {
    const { headers, params, os, timeup } = this

    if (os === 'android') {
      return RNInflate.get(url, headers, params)
    }

    return iRequest
      .get(url)
      .set(headers)
      .use(prefix)
      .timeout(timeup)
      .query(params)
  },
  submitInflate: function(url) {
    const { headers, body, os, timeup } = this

    if (os === 'android') {
      return RNInflate.post(url, headers, body)
    }

    return iRequest
      .post(url)
      .set(headers)
      .timeout(timeup)
      .use(prefix)
      .send(body)
  },
  putInflate: function(url) {
    const { headers, body, os, timeup } = this

    if (os === 'android') {
      return RNInflate.put(url, headers, body)
    }

    return iRequest
      .put(url)
      .set(headers)
      .timeout(timeup)
      .use(prefix)
      .send(body)
  },
  deleteInflate: function(url) {
    const { headers, os, timeup } = this

    if (os === 'android') {
      return RNInflate.delete(url, headers)
    }

    return iRequest
      .delete(url)
      .set(headers)
      .timeout(timeup)
      .use(prefix)
  }
}

export default construct(IRModel)