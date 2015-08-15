# android-plugins-annotations

LICENSE: GPL-3.0

use for make separately distributed .APK - plugins for android application

# description

for example, we have application Alpha and we want to allow users enanche it's functionality, but do not want add new permissions to app Aplha
one solution - make a new application Beta, and implement new functions with that.

so, here a solution:

1. declare abstract class as plugin interface (good idea: declare it in shared between Alpha and Beta module). Theoretically, supported all java primitive types, and JSONObject

```java
@PluginInterface
public abstract class SamplePlugin implements IPluginInterface {
    public abstract boolean methodA(int arg0, int arg2, String arg3) throws RemoteException; // if we need handle RemoteException manually
    public abstract JSONObject methodB(String url) throws JSONException; // throws required, if arguments contains or return type are JSONObject
}
```

2. add 'android-plugin-annotations-compiler' java preprocessor to gradle dependencies:

2.1 first, we need to use 'android-apt' gradle plugin, so declare it in project dependencies

```gradle
buildscript {
    ...
    dependencies {
        ...
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.6'
        ...
    }
}

```

2.2 add <b>apply plugin: 'com.neenbedankt.android-apt'</b> to build.gradle of application module

2.3 add annotations library and annotation processor to module 'dependencies' section
```gradle
dependencies {
    ...
    compile 'su.whs:android-plugins-annotations:0.1.0'          // annotations library
    apt 'su.whs:android-plugins-annotations-compiler:0.1.0'     // java annotations processor
}
```
2.4 configure 'android-apt' plugin - add new section to build.gradle

```gradle
apt {
   processor "su.whs.plugins.core.PluginsAnnotationProcessor"
    // if you only want to run the processors above
   disableDiscovery false
}
```

3 annotate at least one class, where you wants to use plugins (usually - derived from android.os.Application)

```java
@ImportPluginInterfaces(list = {
        // --- comma-separated list of imported plugin interfaces full qualified names ---
        "com.example.SamplePlugin"
})
```

4 add service declaration to android manifest into <application> section
```xml
<service android:name=".WHSPluginsHostService" android:enabled="true" android:exported="true" android:process=":my_process">
    <intent-filter>
        <action android:name="<application.package.id>.WHSPluginsHostService"/>
    </intent-filter>
</service>
```

5 build project, and special class will be generated: PluginsManager. You may use PluginsManager.getInstance() to retreieve plugins list for each interface class

for example (code from real project):

```java
PluginsManager pm = PluginsManager.getInstance();
List<FeedProvider> providers = (List<FeedProvider>) pm.get(FeedProvider.class);
FeedProvider f0 = providers.get(0);
try {
    JSONObject article = f0.getArticle("test-article-url");
    onArticleReceived(article)l
} catch (RemoteException e) {
    e.printStackTrace();
} catch (JSONException e) {
    e.printStackTrace();
}
```

6 if PluginsManager does not generate - check build.gradle configurations, try clean and rebuild project

7 create distributable Plugin (Beta-APK) 

    THIS SECTION NOT WRITTEN YET


