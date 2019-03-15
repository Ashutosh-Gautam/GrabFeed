# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.billing.IInAppBillingService
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.content.Context {
    public void *(android.view.View);
    public void *(android.view.MenuItem);
}

# commented by me
#-libraryjars libs

# The official support library.
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
-keep class android.support.v7.widget.SearchView { *; }
-keep class com.viewpagerindicator.** { *; }
-keep interface com.viewpagerindicator.** { *; }

#-dontwarn com.amazonaws.**
#-dontwarn okio.**
#-dontwarn com.squareup.**
#-dontwarn com.zendesk.**
#-dontwarn com.viewpagerindicator.LinePageIndicator
#-dontwarn retrofit2.**
#-dontwarn rx.**
#-dontwarn uk.co.senab.**
#-dontwarn com.octo.**

-keep class giftv.** { *; }
#-dontwarn org.xmlpull.v1.**
#-dontwarn okhttp3.internal.huc.HttpURLConnectionImpl
#-dontwarn okhttp3.**

#-dontwarn retrofit.**
#-dontwarn retrofit.appengine.UrlFetchClient
#-keep class retrofit.** { *; }
#-keepclasseswithmembers class * {
#    @retrofit.http.* <methods>;
#}

-keepattributes Signature

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**

-dontwarn sun.misc.**
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }

-dontwarn com.squareup.haha.guava.**
-dontwarn com.squareup.haha.perflib.**
-dontwarn com.squareup.haha.trove.**
-keep class com.squareup.haha.** { *; }
-keep class org.eclipse.mat.** { *; }

-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

-renamesourcefileattribute SourceFile
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-keepclassmembers class grab.com.newsfeed.data.models.** {
  *;
}

-keep public class * extends java.lang.Exception

-keep class com.bluelinelabs.logansquare.** { *; }
-keep @com.bluelinelabs.logansquare.annotation.JsonObject class *
-keep class **$$JsonObjectMapper { *; }


-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
}
-keep class **$Properties

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder

-ignorewarnings

-optimizations !method/marking/static,!method/removal/parameter,!code/removal/advanced


-keepnames class grab.com.newsfeed.data.model.cache.LimitCacheSizeGlideModule
