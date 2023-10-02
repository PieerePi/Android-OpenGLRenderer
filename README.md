# 项目练习 - Android使用OpenGL ES渲染png文件（libpng解码）

《音视频开发进展指南-基于Android与iOS平台的实践》书中的[Android-OpenGLRenderer](https://github.com/zhanxiaokai/Android-OpenGLRenderer)项目，使用Android Studio Dolphin和MSYS2环境。

有一些修改，具体如下，

- 1.png文件打包在APK中，使用时拷贝到应用专属存储空间，暂时没有让jni直接读资源文件

- 1.png是透明的，渲染时底色由蓝色调整为白色
