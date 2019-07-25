
# super-inflater

## Getting started

* `$ npm install super-inflater --save`
OR
* `$ yarn add super-inflater --save`

### Mostly automatic installation

`$ react-native link super-inflater`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `super-inflater` and add `SuperInflater.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libSuperInflater.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.SuperInflaterPackage;` to the imports at the top of the file
  - Add `new SuperInflaterPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
    ```
    include ':super-inflater'
    project(':super-inflater').projectDir = new File(rootProject.projectDir,  '../node_modules/super-inflater/android')
    ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
    ```
      compile project(':super-inflater')
    ```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `SuperInflater.sln` in `node_modules/super-inflater/windows/SuperInflater.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Inflate.SuperInflater;` to the usings at the top of the file
  - Add `new SuperInflaterPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
### Import
```javascript
import SuperInflater from 'super-inflater';
```
### Headers, Params & Body
* Use `set, query, send` function to set a header, param, body or use `set, query, send` many time to set multiple headers, params, body.
```javascript
SuperInflater.
  set('Content-Type', 'application/json').
  set('Authorization', 'Bearer ****').
  query('mobile', '0985****').
  query('email', 'john@techfox.io')
```
* Use `timeout` to set timeout for the request
```javascript
SuperInflater.
  .timeout({
    response: 30000,
    deadline: 60000
  })
  set('Content-Type', 'application/json').
  set('Authorization', 'Bearer ****').
  query('mobile', '0985****').
  query('email', 'john@techfox.io')
```
### Request
1. Can use normal request, there are all of `get`, `post`, `put`, `delete`.
2. Inflate request, there are all of `getInflate`, `submitInflate`, `putInflate`, `destroyInflate`
* When using normal request:
```javascript
SuperInflater.
  set('Content-Type', 'application/json').
  query('lang', 'vi-VN').
  get(url).then(res => {
    return res.body
  }).catch(err => {
    console.log('err', err)
  })
```
* OR with `inflate` request, required `platform` because of `Android` is special

* For `get` request using `getInflate`
```javascript
SuperInflater.
  platform(Platform.OS).
  set('Content-Type', 'application/json').
  query('lang', 'vi-VN').
  getInflate(url).then(res => {
    return res
  }).catch(err => {
    console.log('err', err)
  })
```

* For `post` request using `submitInflate`
* with `fix` for json body
```javascript
const body = `{
  username: "${username}",
  password: "${password}"
}`

SuperInflater.
  platform(Platform.OS).
  set('Content-Type', 'application/json').
  fix(body).
  submitInflate(url).then(res => {
    return res
  }).catch(err => {
    console.log('err', err)
  })
```
* with `send` for `object` body
* with `fix` for json body
```javascript
const body = {
  username: "${username}",
  password: "${password}"
}

SuperInflater.
  platform(Platform.OS).
  set('Content-Type', 'application/json').
  send(body).
  send('new-password', 'john@techfox.io').
  putInflate(url).then(res => {
    return res
  }).catch(err => {
    console.log('err', err)
  })
```