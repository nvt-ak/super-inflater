
# react-native-inflate

## Getting started

`$ npm install react-native-inflate --save`
OR
`$ yarn add react-native-inflate --save`

### Mostly automatic installation

`$ react-native link react-native-inflate`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-inflate` and add `RNInflate.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNInflate.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNInflatePackage;` to the imports at the top of the file
  - Add `new RNInflatePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
    ```
    include ':react-native-inflate'
    project(':react-native-inflate').projectDir = new File(rootProject.projectDir,  '../node_modules/react-native-inflate/android')
    ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
    ```
      compile project(':react-native-inflate')
    ```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNInflate.sln` in `node_modules/react-native-inflate/windows/RNInflate.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Inflate.RNInflate;` to the usings at the top of the file
  - Add `new RNInflatePackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
### Import
```javascript
    import RNInflate from 'react-native-inflate';
```
### Headers, Params & Body
* Use `set, query, send` function to set a header, param, body or use `set, query, send` many time to set multiple headers, params, body.
```javascript
RNInflate.
    set('Content-Type', 'application/json').
    set('Authorization', 'Bearer ****').
    query('mobile', '0985****').
    query('email', 'john@techfox.io')
```
* Use `timeout` to set timeout for the request
```javascript
RNInflate.
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
2. Inflate request only is `get request` and you can use `inflate` function.
```javascript
RNInflate.
    set('Content-Type', 'application/json').
    query('lang', 'vi-VN').
    get(url).then(res => {
      return res.body
    }).catch(err => {
      console.log('err', err)
    })
```
* OR with `inflate` request, required `platform` because of `Android` is special
```javascript
RNInflate.
    platform(Platform.OS).
    set('Content-Type', 'application/json').
    query('lang', 'vi-VN').
    inflate(url).then(res => {
      return res
    }).catch(err => {
      console.log('err', err)
    })
```
