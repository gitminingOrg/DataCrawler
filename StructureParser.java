
From edf3791e6ebcff6e8e98c1dfed3ca3f83c0edca4 Mon Sep 17 00:00:00 2001
From: WonderCsabo <kozakcsabi@gmail.com>
Date: Fri, 29 Aug 2014 19:29:04 +0200
Subject: [PATCH 1/5] Add checkstyle

---
 .../annotations/AfterTextChange.java               |   6 +-
 .../androidannotations/annotations/AfterViews.java |   5 +-
 .../androidannotations/annotations/Background.java |  26 ++-
 .../org/androidannotations/annotations/EView.java  |   6 +-
 .../org/androidannotations/annotations/Extra.java  |  13 +-
 .../annotations/FragmentArg.java                   |   8 +-
 .../androidannotations/annotations/FromHtml.java   |   8 +-
 .../annotations/HttpsClient.java                   |   2 +-
 .../androidannotations/annotations/NoTitle.java    |   8 +-
 .../annotations/OnActivityResult.java              |  39 ++--
 .../androidannotations/annotations/OrmLiteDao.java |   6 +
 .../annotations/SeekBarTouchStart.java             |  11 +-
 .../annotations/SeekBarTouchStop.java              |  11 +-
 .../annotations/SupposeBackground.java             |  42 ++--
 .../annotations/SupposeUiThread.java               |  16 +-
 .../org/androidannotations/annotations/Touch.java  |  10 +-
 .../org/androidannotations/annotations/Trace.java  |   6 +-
 .../androidannotations/annotations/UiThread.java   |   7 +-
 .../annotations/res/AnimationRes.java              |   4 +-
 .../annotations/res/BooleanRes.java                |   4 +-
 .../annotations/res/ColorRes.java                  |   4 +-
 .../annotations/res/ColorStateListRes.java         |   4 +-
 .../annotations/res/DimensionPixelOffsetRes.java   |   4 +-
 .../annotations/res/DimensionPixelSizeRes.java     |   4 +-
 .../annotations/res/DimensionRes.java              |   4 +-
 .../annotations/res/DrawableRes.java               |   4 +-
 .../annotations/res/HtmlRes.java                   |   4 +-
 .../annotations/res/IntArrayRes.java               |   4 +-
 .../annotations/res/IntegerRes.java                |   4 +-
 .../annotations/res/LayoutRes.java                 |   4 +-
 .../annotations/res/MovieRes.java                  |   4 +-
 .../annotations/res/StringArrayRes.java            |   4 +-
 .../annotations/res/StringRes.java                 |   4 +-
 .../annotations/res/TextArrayRes.java              |   4 +-
 .../annotations/res/TextRes.java                   |   4 +-
 .../annotations/rest/Accept.java                   |   5 +-
 .../annotations/rest/Options.java                  |   3 +-
 .../annotations/rest/RequiresAuthentication.java   |  10 +-
 .../annotations/rest/RequiresCookie.java           |   7 +-
 .../annotations/rest/RequiresCookieInUrl.java      |   2 +-
 .../annotations/rest/RequiresHeader.java           |   5 +-
 .../androidannotations/annotations/rest/Rest.java  |  11 +-
 .../annotations/rest/SetsCookie.java               |   2 +-
 .../sharedpreferences/DefaultBoolean.java          |   4 +-
 .../sharedpreferences/DefaultFloat.java            |   4 +-
 .../annotations/sharedpreferences/DefaultInt.java  |   4 +-
 .../annotations/sharedpreferences/DefaultLong.java |   4 +-
 .../annotations/sharedpreferences/DefaultRes.java  |   4 +-
 .../sharedpreferences/DefaultString.java           |   4 +-
 .../annotations/sharedpreferences/Pref.java        |   6 +-
 .../annotations/sharedpreferences/SharedPref.java  |   4 +-
 .../androidannotations/api/BackgroundExecutor.java | 122 ++++++-----
 .../androidannotations/api/SdkVersionHelper.java   |   6 +-
 .../org/androidannotations/api/ViewServer.java     |  43 ++--
 .../api/rest/RestClientHeaders.java                |  40 ++--
 .../api/roboguice/RoboGuiceHelper.java             |   3 +
 .../sharedpreferences/SharedPreferencesCompat.java |  12 +-
 .../java/com/sun/codemodel/JSuperWildcard.java     |   6 +-
 .../AndroidAnnotationProcessor.java                |   1 -
 .../exception/ProcessingException.java             |   2 +-
 .../handler/AnnotationHandlers.java                |   2 +-
 .../androidannotations/handler/BeanHandler.java    |  21 +-
 .../handler/CheckedChangeHandler.java              |  22 +-
 .../handler/EActivityHandler.java                  |  16 +-
 .../handler/EApplicationHandler.java               |   8 +-
 .../androidannotations/handler/EBeanHandler.java   |  10 +-
 .../handler/EFragmentHandler.java                  |  21 +-
 .../handler/EIntentServiceHandler.java             |  34 +--
 .../handler/EProviderHandler.java                  |   8 +-
 .../handler/EReceiverHandler.java                  |   8 +-
 .../handler/EServiceHandler.java                   |   8 +-
 .../handler/EViewGroupHandler.java                 |  11 +-
 .../androidannotations/handler/EViewHandler.java   |   8 +-
 .../handler/EditorActionHandler.java               |  25 ++-
 .../androidannotations/handler/ExtraHandler.java   |  52 ++---
 .../handler/FocusChangeHandler.java                |  10 +-
 .../handler/FragmentArgHandler.java                |  27 ++-
 .../handler/IgnoredWhenDetachedHandler.java        |  22 +-
 .../handler/InstanceStateHandler.java              |  25 +--
 .../handler/ItemLongClickHandler.java              |  25 ++-
 .../handler/ItemSelectHandler.java                 |  24 ++-
 .../handler/OnActivityResultHandler.java           |  36 ++--
 .../androidannotations/handler/PrefHandler.java    |   6 +-
 .../handler/ReceiverActionHandler.java             |   3 +-
 .../handler/ReceiverHandler.java                   |   3 +-
 .../handler/RootContextHandler.java                |  79 +++++++
 .../handler/RootContextHanlder.java                |  79 -------
 .../handler/ServiceActionHandler.java              | 239 +++++++++++----------
 .../handler/SharedPrefHandler.java                 |   2 +-
 .../handler/SupposeBackgroundHandler.java          |  18 +-
 .../handler/SupposeUiThreadHandler.java            |  12 +-
 .../androidannotations/handler/TouchHandler.java   |  23 +-
 .../androidannotations/handler/TraceHandler.java   |  12 +-
 .../handler/UiThreadHandler.java                   |  20 +-
 .../handler/ViewByIdHandler.java                   |  15 +-
 .../handler/ViewsByIdHandler.java                  |  30 +--
 .../handler/WakeLockHandler.java                   |   4 +-
 .../handler/rest/DeleteHandler.java                |   6 +-
 .../handler/rest/HeadHandler.java                  |   8 +-
 .../handler/rest/OptionsHandler.java               |   8 +-
 .../handler/rest/PutHandler.java                   |   2 +-
 .../handler/rest/RestHandler.java                  |  30 +--
 .../handler/rest/RestMethodHandler.java            |  13 +-
 .../handler/rest/RestServiceHandler.java           |  44 ++--
 .../helper/APTCodeModelHelper.java                 |  73 ++++---
 .../helper/ActionBarSherlockHelper.java            |   5 +-
 .../helper/ActivityIntentBuilder.java              |  16 +-
 .../androidannotations/helper/AndroidManifest.java |   4 +
 .../helper/AnnotationArrayParamExtractor.java      |  13 +-
 .../helper/AnnotationHelper.java                   |  38 ++--
 .../helper/AnnotationParamExtractor.java           |  20 +-
 .../androidannotations/helper/BundleHelper.java    |  73 +++----
 .../org/androidannotations/helper/CaseHelper.java  |  15 +-
 .../org/androidannotations/helper/FileHelper.java  |   3 +
 .../androidannotations/helper/IntentBuilder.java   |  38 ++--
 .../androidannotations/helper/OptionsHelper.java   |   2 +-
 .../helper/RestAnnotationHelper.java               |  45 ++--
 .../helper/ServiceIntentBuilder.java               |   3 +-
 .../androidannotations/helper/ValidatorHelper.java |   4 +-
 .../helper/ValidatorParameterHelper.java           |  23 +-
 .../androidannotations/holder/EActivityHolder.java |  85 ++++----
 .../org/androidannotations/holder/EBeanHolder.java |  11 +-
 .../holder/EComponentHolder.java                   |   8 +-
 .../holder/EComponentWithViewSupportHolder.java    |  36 +++-
 .../androidannotations/holder/EFragmentHolder.java |  49 ++---
 .../holder/EIntentServiceHolder.java               |  80 +++----
 .../androidannotations/holder/EServiceHolder.java  |  33 +--
 .../org/androidannotations/holder/EViewHolder.java |   6 +-
 .../androidannotations/holder/FoundViewHolder.java |   6 +-
 .../holder/HasIntentBuilder.java                   |   9 +-
 .../holder/OnActivityResultHolder.java             |  14 +-
 .../holder/ReceiverRegistrationHolder.java         |  20 +-
 .../androidannotations/holder/RoboGuiceHolder.java |   3 +
 .../holder/SharedPrefHolder.java                   |  13 +-
 .../androidannotations/logger/LoggerContext.java   |  10 +-
 .../androidannotations/logger/LoggerFactory.java   |   3 +
 .../logger/appender/FileAppender.java              |  17 +-
 .../androidannotations/process/ModelProcessor.java |  27 ++-
 .../androidannotations/process/ProcessHolder.java  |   3 +
 .../androidannotations/rclass/IRInnerClass.java    |   2 +-
 .../rclass/ManifestPackageExtractor.java           |   4 +-
 .../androidannotations/ebean/SomeGenericBean.java  |   4 +-
 .../eviewgroup/SomeGenericViewGroup.java           |   5 +-
 .../eviewgroup/SomeGenericViewGroupExt.java        |   3 +-
 .../receiver/ActivityWithInvalidRegisterAt.java    |   3 +-
 .../receiver/ActivityWithTwoSameNameMethod.java    |   5 +-
 .../receiver/ActivityWithValidReceiver.java        |   5 +-
 .../receiver/FragmentWithValidReceiver.java        |   5 +-
 .../receiver/ReceiverRegistrationTest.java         |   4 +-
 .../receiver/ServiceWithInvalidReceiver.java       |   5 +-
 .../receiver/ServiceWithValidReceiver.java         |   5 +-
 AndroidAnnotations/checkstyle-checks.xml           | 117 ++++++++++
 AndroidAnnotations/functional-test-1-5/pom.xml     |   5 +
 .../actionbarsherlock/app/SherlockFragment.java    |  67 +++---
 .../main/java/com/actionbarsherlock/view/Menu.java |   4 +-
 .../test15/ActivityWithGenerics.java               |  17 +-
 .../test15/AwaitingResultActivity.java             |  17 +-
 .../test15/AwaitingResultFragment.java             |  26 +--
 .../org/androidannotations/test15/CustomData.java  |  17 +-
 .../test15/FragmentArguments.java                  |   5 +-
 .../test15/ItemClicksHandledActivity.java          |  23 +-
 .../test15/MultiFindViewActivity.java              |   9 +-
 .../test15/ebean/ThreadControlledBean.java         |   8 +-
 .../test15/efragment/MyFragment.java               |  21 +-
 .../test15/efragment/MyListFragment.java           |  12 +-
 .../eintentservice/IntentServiceHandledAction.java |   2 +-
 .../test15/eprovider/MyProvider.java               |  20 +-
 .../test15/eservice/MyService.java                 |  16 +-
 .../test15/eviewgroup/CustomFrameLayout.java       |  30 +--
 .../eviewgroup/CustomFrameLayoutActivity.java      |   8 +-
 .../ActivityWithInnerEnhancedClasses.java          |  10 +-
 .../innerclasses/BeanWithInnerEnhancedClasses.java |   5 +-
 .../instancestate/MyGenericParcelableBean.java     |  24 ++-
 .../instancestate/MyGenericSerializableBean.java   |  19 +-
 .../test15/instancestate/MyParcelableBean.java     |  20 +-
 .../test15/instancestate/MySerializableBean.java   |  20 +-
 .../instancestate/SaveInstanceStateActivity.java   |  14 +-
 .../test15/menu/OptionsMenuActivity.java           |  18 +-
 .../test15/menu/OptionsMenuFragment.java           |  12 +-
 .../test15/menu/OptionsMenuSubActivity.java        |   3 +
 .../test15/menu/OptionsMenuSupportFragment.java    |  12 +-
 .../test15/ormlite/OrmLiteBean.java                |   4 -
 .../test15/prefs/InnerPrefs.java                   |   2 +-
 .../androidannotations/test15/res/ResActivity.java |  14 +-
 .../org/androidannotations/test15/rest/Event.java  |  20 +-
 .../test15/rest/HttpMethodsService.java            |   3 +-
 .../test15/roboguice/MapActivityWithRoboGuice.java |   4 -
 .../test15/roboguice/SampleRoboApplication.java    |   3 +-
 .../test15/sherlock/MySherlockActivity.java        |  22 +-
 .../test15/sherlock/MySherlockFragment.java        |  11 +-
 .../test15/trace/TracedActivity.java               |  12 +-
 .../test15/ThreadActivityTest.java                 |  93 ++++----
 .../test15/eintentservice/MyIntentServiceTest.java |   4 +-
 .../test15/menu/OptionsMenuActivityTest.java       |   2 +-
 .../test15/sherlock/MySherlockActivityTest.java    |   2 +-
 AndroidAnnotations/pom.xml                         |  38 +++-
 196 files changed, 1903 insertions(+), 1461 deletions(-)
 create mode 100644 AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/RootContextHandler.java
 delete mode 100644 AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/RootContextHanlder.java
 create mode 100644 AndroidAnnotations/checkstyle-checks.xml

diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterTextChange.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterTextChange.java
index e06a828..f448bb0 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterTextChange.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterTextChange.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations;
 
-import android.text.Editable;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -25,8 +23,8 @@
 /**
  * <p>
  * This annotation is intended to be used on methods to receive events defined
- * by {@link android.text.TextWatcher#afterTextChanged(Editable s)} after the
- * text is changed on the targeted TextView or subclass of TextView.
+ * by {@link android.text.TextWatcher#afterTextChanged(android.text.Editable s)}
+ * after the text is changed on the targeted TextView or subclass of TextView.
  * </p>
  * <p>
  * The annotation value should be one or several R.id.* fields that refers to
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterViews.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterViews.java
index 9d3fd54..b2939af 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterViews.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterViews.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations;
 
-import android.app.Activity;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -25,7 +23,8 @@
 /**
  * <p>
  * Methods annotated with @{@link AfterViews} will be called after
- * {@link Activity#setContentView(int)} is called by the generated activity.
+ * {@link android.app.Activity#setContentView(int) setContentView(int)} is
+ * called by the generated activity.
  * </p>
  * <p>
  * This occurs AFTER <code>setContentView(View)</code> which is called at the
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Background.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Background.java
index b3f63f7..e59a2c2 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Background.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Background.java
@@ -19,8 +19,6 @@
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
-import java.util.concurrent.Future;
-import java.util.concurrent.ScheduledThreadPoolExecutor;
 
 /**
  * <p>
@@ -30,7 +28,9 @@
  * The annotated method MUST return void and MAY contain parameters.
  * </p>
  * <p>
- * The generated code is based on {@link org.androidannotations.api.BackgroundExecutor} methods.
+ * The generated code is based on
+ * {@link org.androidannotations.api.BackgroundExecutor BackgroundExecutor}
+ * methods.
  * </p>
  * 
  * <h2>Cancellation</h2>
@@ -66,20 +66,25 @@
  * <b>Note</b>: Cancellation may or may not be successful. If the task wasn't
  * executed yet, it will be removed from the pool. But it could fail if task has
  * already completed, has already been cancelled, or could not be cancelled for
- * some other reason. See {@link Future#cancel(boolean)} for more information.
+ * some other reason. See {@link java.util.concurrent.Future#cancel(boolean)
+ * Future#cancel(boolean)} for more information.
  * </p>
  * 
  * <h2>Execution flow</h2>
  * <p>
- * By default, all tasks will be put in a {@link ScheduledThreadPoolExecutor}
- * with a core pool size of <code>2 * numberOfCpu</code>. Which means that
- * background methods will be executed in <b>PARALLEL</b>. You can change this
- * by calling <code>BackgroundExecutor.setExecutor(...)</code>.
+ * By default, all tasks will be put in a
+ * {@link java.util.concurrent.ScheduledThreadPoolExecutor
+ * ScheduledThreadPoolExecutor} with a core pool size of
+ * <code>2 * numberOfCpu</code>. Which means that background methods will be
+ * executed in <b>PARALLEL</b>. You can change this by calling
+ * <code>BackgroundExecutor.setExecutor(...)</code>.
  * </p>
  * <p>
  * If you want execute ALL background methods SEQUENTIALLY, the best way is to
- * change the executor of {@link org.androidannotations.api.BackgroundExecutor} to a
- * {@link ScheduledThreadPoolExecutor} with a core pool size of <code>1</code>.
+ * change the executor of {@link org.androidannotations.api.BackgroundExecutor
+ * BackgroundExecutor} to a
+ * {@link java.util.concurrent.ScheduledThreadPoolExecutor
+ * ScheduledThreadPoolExecutor} with a core pool size of <code>1</code>.
  * </p>
  * <p>
  * If you want execute some background methods SEQUENTIALLY, you should simply
@@ -146,6 +151,7 @@
  * <b>Example</b> :
  *
  * <blockquote>
+ * 
  * <pre>
  * &#064;EBean
  * public class MyBean {
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EView.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EView.java
index 004cc80..99b08a5 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EView.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EView.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations;
 
-import android.view.ViewGroup;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -24,7 +22,7 @@
 
 /**
  * <p>
- * Should be used on {@link android.view.View} classes to enable usage of
+ * Should be used on {@link android.view.View View} classes to enable usage of
  * AndroidAnnotations.
  * </p>
  * <p>
@@ -62,7 +60,7 @@
  * 
  * @see AfterInject
  * @see AfterViews
- * @see ViewGroup
+ * @see android.view.ViewGroup
  * @see <a
  *      href="http://developer.android.com/guide/topics/ui/custom-components.html"
  *      >How to build a custom component.</a>
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Extra.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Extra.java
index 5b33539..80b3b5b 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Extra.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Extra.java
@@ -15,10 +15,6 @@
  */
 package org.androidannotations.annotations;
 
-import android.app.Activity;
-import android.os.Parcelable;
-
-import java.io.Serializable;
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -26,8 +22,9 @@
 
 /**
  * <p>
- * Use on any native, {@link Parcelable} or {@link Serializable} field in an
- * {@link EActivity} annotated class to bind it with Android's extra.
+ * Use on any native, {@link android.os.Parcelable Parcelable} or
+ * {@link java.io.Serializable Serializable} field in an {@link EActivity}
+ * annotated class to bind it with Android's extra.
  * </p>
  * <p>
  * The annotation value is the key used for extra. If not set, the field name
@@ -42,8 +39,8 @@
  * annotated method.
  * </p>
  * <p>
- * Calling {@link Activity#setIntent(android.content.Intent)} will automatically
- * update the annotated extras.
+ * Calling {@link android.app.Activity#setIntent(android.content.Intent)
+ * Activity#setIntent(Intent)} will automatically update the annotated extras.
  * </p>
  * <blockquote>
  * 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentArg.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentArg.java
index ad9b3b3..6b17c13 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentArg.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentArg.java
@@ -15,9 +15,6 @@
  */
 package org.androidannotations.annotations;
 
-import android.os.Parcelable;
-
-import java.io.Serializable;
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -25,8 +22,9 @@
 
 /**
  * <p>
- * Use on any native, {@link Parcelable} or {@link Serializable} field in an
- * {@link EFragment} annotated class to bind it with Android's arguments.
+ * Use on any native, {@link android.os.Parcelable Parcelable} or
+ * {@link java.io.Serializable Serializable} field in an {@link EFragment}
+ * annotated class to bind it with Android's arguments.
  * </p>
  * <p>
  * The annotation value is the key used for argument. If not set, the field name
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FromHtml.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FromHtml.java
index 7259093..3839765 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FromHtml.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FromHtml.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations;
 
-import org.androidannotations.annotations.res.HtmlRes;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -24,8 +22,8 @@
 
 /**
  * <p>
- * Use on a {@link android.widget.TextView} field or a
- * {@link android.widget.TextView} subclass field annotated with
+ * Use on a {@link android.widget.TextView TextView} field or a
+ * {@link android.widget.TextView TextView} subclass field annotated with
  * {@link ViewById} to inject text as HTML.
  * </p>
  * <p>
@@ -55,7 +53,7 @@
  * </blockquote>
  * 
  * @see ViewById
- * @see HtmlRes
+ * @see org.androidannotations.annotations.res.HtmlRes
  */
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/HttpsClient.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/HttpsClient.java
index 88fc10e..d32a707 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/HttpsClient.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/HttpsClient.java
@@ -89,7 +89,7 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface HttpsClient {
-	public static final String DEFAULT_PASSWD = "changeit";
+	String DEFAULT_PASSWD = "changeit";
 
 	int trustStore() default ResId.DEFAULT_VALUE;
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/NoTitle.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/NoTitle.java
index f665378..2d7e240 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/NoTitle.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/NoTitle.java
@@ -24,12 +24,12 @@
  * <p>
  * Should be used on {@link EActivity} classes that must have no title.
  * </p>
- * <p>
- * <b>Note:</b> This annotation has been deprecated. Please use
- * <code>&#064;WindowFeature(Window.FEATURE_NO_TITLE})</code> instead
- * </p>
  * 
  * @see WindowFeature
+ * 
+ * @deprecated Please use
+ *             <code>&#064;WindowFeature(Window.FEATURE_NO_TITLE})</code>
+ *             instead
  */
 @Deprecated
 @Retention(RetentionPolicy.CLASS)
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OnActivityResult.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OnActivityResult.java
index e9797c0..de12ea8 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OnActivityResult.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OnActivityResult.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations;
 
-import android.content.Intent;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -26,9 +24,9 @@
  * <p>
  * This annotation is intended to be used on methods to receive results from a
  * previously started activity using
- * {@link android.app.Activity#startActivityForResult(Intent, int)} or the
- * generated <code>IntentBuilder.startActivityForResult()</code> method of the
- * activity.
+ * {@link android.app.Activity#startActivityForResult(android.content.Intent, int)
+ * Activity#startActivityForResult(Intent, int)} or the generated
+ * <code>IntentBuilder.startActivityForResult()</code> method of the activity.
  * </p>
  * <p>
  * The annotation value must be an integer constant that represents the
@@ -38,10 +36,13 @@
  * The method may have multiple parameter :
  * </p>
  * <ul>
- * <li>A {@link android.content.Intent} that contains data</li>
- * <li>An <code>int</code> or an {@link java.lang.Integer} to get the resultCode</li>
- * <li>Any native, {@link android.os.Parcelable} or {@link java.io.Serializable} parameter
- * annotated with {@link org.androidannotations.annotations.OnActivityResult.Extra} to get an object put in the extras of the intent.</li>
+ * <li>A {@link android.content.Intent Intent} that contains data</li>
+ * <li>An <code>int</code> or an {@link java.lang.Integer Integer} to get the
+ * resultCode</li>
+ * <li>Any native, {@link android.os.Parcelable Parcelable} or
+ * {@link java.io.Serializable Serializable} parameter annotated with
+ * {@link org.androidannotations.annotations.OnActivityResult.Extra
+ * OnActivityResult.Extra} to get an object put in the extras of the intent.</li>
  * </ul>
  *
  * <blockquote>
@@ -69,8 +70,8 @@
  * </blockquote>
  * 
  * @see EActivity
- * @see android.app.Activity#startActivityForResult(Intent, int)
- * @see android.app.Activity#onActivityResult(int, int, Intent)
+ * @see android.app.Activity#startActivityForResult(android.content.Intent, int)
+ * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
  */
 
 @Retention(RetentionPolicy.CLASS)
@@ -81,12 +82,13 @@
 
 	/**
 	 * <p>
-	 * Use on any native, {@link android.os.Parcelable} or {@link java.io.Serializable} parameter of an
-	 * {@link OnActivityResult} annotated method to bind it with the value from the Intent.
+	 * Use on any native, {@link android.os.Parcelable} or
+	 * {@link java.io.Serializable} parameter of an {@link OnActivityResult}
+	 * annotated method to bind it with the value from the Intent.
 	 * </p>
 	 * <p>
-	 * The annotation value is the key used for the result data. If not set, the field name
-	 * will be used as the key.
+	 * The annotation value is the key used for the result data. If not set, the
+	 * field name will be used as the key.
 	 * </p>
 	 *
 	 * <blockquote>
@@ -97,11 +99,11 @@
 	 * &#064;OnActivityResult(REQUEST_CODE)
 	 * void onResult(int resultCode, Intent data, <b>@Extra String value</b>) {
 	 * }
-	 *
+	 * 
 	 * &#064;OnActivityResult(REQUEST_CODE)
 	 * void onResult(int resultCode, <b>@Extra(value = "key") String value</b>) {
 	 * }
-	 *
+	 * 
 	 * &#064;OnActivityResult(REQUEST_CODE)
 	 * void onResult(<b>@Extra String strVal</b>, <b>@Extra int intVal</b>) {
 	 * }
@@ -109,7 +111,8 @@
 	 *
 	 * </blockquote>
 	 *
-	 * @see android.app.Activity#onActivityResult(int, int, Intent)
+	 * @see android.app.Activity#onActivityResult(int, int,
+	 *      android.content.Intent)
 	 * @see OnActivityResult
 	 */
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OrmLiteDao.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OrmLiteDao.java
index 8782685..dee6596 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OrmLiteDao.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OrmLiteDao.java
@@ -69,6 +69,12 @@
 
 	Class<?> helper();
 
+	/**
+	 * 
+	 * @deprecated Since <b>3.1</b> the model class is inferred from the type of
+	 *             the annotated field. This parameter will be removed in a
+	 *             future version.
+	 */
 	@Deprecated
 	Class<?> model() default Void.class;
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStart.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStart.java
index 5814d64..60f33d0 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStart.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStart.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations;
 
-import android.widget.SeekBar;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -26,8 +24,9 @@
  * <p>
  * This annotation is intended to be used on methods to receive events defined
  * by
- * {@link android.widget.SeekBar.OnSeekBarChangeListener#onStartTrackingTouch(SeekBar seekBar)}
- * when the user begins to move the cursor of the targeted SeekBar.
+ * {@link android.widget.SeekBar.OnSeekBarChangeListener#onStartTrackingTouch(android.widget.SeekBar)
+ * SeekBar.OnSeekBarChangeListener#onStartTrackingTouch(SeekBar)} when the user
+ * begins to move the cursor of the targeted SeekBar.
  * </p>
  * <p>
  * The annotation value should be one or several R.id.* fields that refers to an
@@ -38,8 +37,8 @@
  * The method MAY have one parameter :
  * </p>
  * <ul>
- * <li>A {@link android.widget.SeekBar} parameter to determine which view has
- * targeted this event</li>
+ * <li>A {@link android.widget.SeekBar SeekBar} parameter to determine which
+ * view has targeted this event</li>
  * </ul>
  *
  * <blockquote>
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStop.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStop.java
index aef06df..8b696f4 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStop.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStop.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations;
 
-import android.widget.SeekBar;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -26,8 +24,9 @@
  * <p>
  * This annotation is intended to be used on methods to receive events defined
  * by
- * {@link android.widget.SeekBar.OnSeekBarChangeListener#onStopTrackingTouch(SeekBar seekBar)}
- * when the user has finished to move the cursor of the targeted SeekBar.
+ * {@link android.widget.SeekBar.OnSeekBarChangeListener#onStopTrackingTouch(android.widget.SeekBar)
+ * SeekBar.OnSeekBarChangeListener#onStopTrackingTouch(SeekBar)} when the user
+ * has finished to move the cursor of the targeted SeekBar.
  * </p>
  * <p>
  * The annotation value should be one or several R.id.* fields that refers to an
@@ -38,8 +37,8 @@
  * The method MAY have one parameter :
  * </p>
  * <ul>
- * <li>A {@link android.widget.SeekBar} parameter to determine which view has
- * targeted this event</li>
+ * <li>A {@link android.widget.SeekBar SeekBar} parameter to determine which
+ * view has targeted this event</li>
  * </ul>
  *
  * <blockquote>
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeBackground.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeBackground.java
index d53eb41..31c690a 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeBackground.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeBackground.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations;
 
-import org.androidannotations.api.BackgroundExecutor;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -24,44 +22,48 @@
 
 /**
  * <p>
- * Ensures that the method is called from the background thread with (optionally) restrictions by allowed serials.
- * If it is not called from a supposed background thread, then {@link IllegalStateException}
- * will be thrown (by default).
+ * Ensures that the method is called from the background thread with
+ * (optionally) restrictions by allowed serials. If it is not called from a
+ * supposed background thread, then {@link IllegalStateException} will be thrown
+ * (by default).
  * </p>
  * <blockquote> <b>Example</b> :
  *
  * <pre>
  * &#064;EBean
  * public class MyBean {
- *
+ * 
  * 	&#064;SupposeBackground
  * 	boolean someMethodThatShouldNotBeCalledFromUiThread() {
- * 		//if this method will be called from the UI-thread an exception will be thrown
- *    }
- *
- * 	&#064;SupposeBackground(serial = {"serial1", "serial2"})
+ * 		// if this method will be called from the UI-thread an exception will be
+ * 		// thrown
+ * 	}
+ * 
+ * 	&#064;SupposeBackground(serial = { &quot;serial1&quot;, &quot;serial2&quot; })
  * 	boolean someMethodThatShouldBeCalledFromSerial1OrSerial2() {
- * 		//if this method will be called from another thread then a background thread with a
- * 		//serial "serial1" or "serial2", an exception will be thrown
- *    }
- *
+ * 		// if this method will be called from another thread then a background
+ * 		// thread with a
+ * 		// serial &quot;serial1&quot; or &quot;serial2&quot;, an exception will be thrown
+ * 	}
+ * 
  * }
  * </pre>
+ * 
  * </blockquote>
  *
- * @see BackgroundExecutor#setWrongThreadListener(BackgroundExecutor.WrongThreadListener)
- * @see BackgroundExecutor#DEFAULT_WRONG_THREAD_LISTENER
- * @see BackgroundExecutor#checkBgThread(String...)
+ * @see org.androidannotations.api.BackgroundExecutor#setWrongThreadListener(org.androidannotations.api.BackgroundExecutor.WrongThreadListener)
+ * @see org.androidannotations.api.BackgroundExecutor#DEFAULT_WRONG_THREAD_LISTENER
+ * @see org.androidannotations.api.BackgroundExecutor#checkBgThread(String...)
  */
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface SupposeBackground {
 
 	/**
-	 * @return Allowed serials to restrict a calling thread. If it is an empty list, 
-	 * then any background thread is allowed.
+	 * @return Allowed serials to restrict a calling thread. If it is an empty
+	 *         list, then any background thread is allowed.
 	 *
-	 * @see BackgroundExecutor#checkBgThread(String...)
+	 * @see org.androidannotations.api.BackgroundExecutor#checkBgThread(String...)
 	 */
 	String[] serial() default {};
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeUiThread.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeUiThread.java
index 286557e..786c0ed 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeUiThread.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeUiThread.java
@@ -15,9 +15,6 @@
  */
 package org.androidannotations.annotations;
 
-import org.androidannotations.api.BackgroundExecutor;
-
-import java.lang.IllegalStateException;
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -32,19 +29,20 @@
  * <pre>
  * &#064;EBean
  * public class MyBean {
- *
+ * 
  * 	&#064;SupposeUiThread
  * 	boolean someMethodThatShouldBeCalledOnlyFromUiThread() {
- * 		//if this method will be called from a background thread an exception will be thrown
- *    }
+ * 		// if this method will be called from a background thread an exception
+ * 		// will be thrown
+ * 	}
  * }
  * </pre>
  *
  * </blockquote>
  *
- * @see BackgroundExecutor#setWrongThreadListener(BackgroundExecutor.WrongThreadListener)
- * @see BackgroundExecutor#DEFAULT_WRONG_THREAD_LISTENER
- * @see BackgroundExecutor#checkUiThread()
+ * @see org.androidannotations.api.BackgroundExecutor#setWrongThreadListener(org.androidannotations.api.BackgroundExecutor.WrongThreadListener)
+ * @see org.androidannotations.api.BackgroundExecutor#DEFAULT_WRONG_THREAD_LISTENER
+ * @see org.androidannotations.api.BackgroundExecutor#checkUiThread()
  */
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Touch.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Touch.java
index 23228ad..15f7c71 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Touch.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Touch.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations;
 
-import android.view.MotionEvent;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -26,8 +24,9 @@
  * <p>
  * This annotation is intended to be used on methods to receive events defined
  * by
- * {@link android.view.View.OnTouchListener#onTouch(android.view.View, MotionEvent)}
- * when the view has been touched by the user.
+ * {@link android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
+ * View.OnTouchListener#onTouch(View, MotionEvent)} when the view has been
+ * touched by the user.
  * </p>
  * <p>
  * The annotation value should be one or several of R.id.* fields. If not set,
@@ -42,7 +41,8 @@
  * The method MAY have one or two parameters:
  * </p>
  * <ul>
- * <li>A {@link android.view.View} parameter to know which view has been clicked</li>
+ * <li>A {@link android.view.View} parameter to know which view has been clicked
+ * </li>
  * <li>A {@link android.view.MotionEvent} parameter</li>
  * </ul>
  * <blockquote>
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Trace.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Trace.java
index 62f8436..357cfe7 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Trace.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Trace.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations;
 
-import android.util.Log;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import android.util.Log;
+
 /**
  * <p>
  * This annotation is intended to be used on methods to log at runtime the
@@ -72,7 +72,7 @@
 @Target(ElementType.METHOD)
 public @interface Trace {
 
-	public static final String DEFAULT_TAG = "NO_TAG";
+	String DEFAULT_TAG = "NO_TAG";
 
 	String tag() default DEFAULT_TAG;
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/UiThread.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/UiThread.java
index d94412f..8d462ce 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/UiThread.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/UiThread.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations;
 
-import android.os.Handler;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -90,7 +88,7 @@
  * </blockquote>
  * 
  * @see Background
- * @see Handler
+ * @see android.os.Handler
  */
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
@@ -103,7 +101,8 @@
 	 * using the handler. The default value is ENQUEUE, which will always call
 	 * the handler.
 	 * 
-	 * @return whether the method should be posted or executed if it's in the UI thread
+	 * @return whether the method should be posted or executed if it's in the UI
+	 *         thread
 	 */
 	Propagation propagation() default Propagation.ENQUEUE;
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/AnimationRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/AnimationRes.java
index aeaab65..c844959 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/AnimationRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/AnimationRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link android.content.res.XmlResourceParser} fields in any enhanced
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/BooleanRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/BooleanRes.java
index bf6e90b..8a4ceed 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/BooleanRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/BooleanRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link java.lang.Boolean} or <code>boolean</code> fields in any
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorRes.java
index 03dee4c..f72b18f 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link java.lang.Integer} or <code>int</code> fields in any enhanced
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorStateListRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorStateListRes.java
index 2306529..332e161 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorStateListRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorStateListRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link android.content.res.ColorStateList} fields in any enhanced
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelOffsetRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelOffsetRes.java
index fe6fda8..3ec4af1 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelOffsetRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelOffsetRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link java.lang.Integer} or <code>int</code> fields in any enhanced
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelSizeRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelSizeRes.java
index 0123744..d78ee23 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelSizeRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelSizeRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link java.lang.Integer} or <code>int</code> fields in any enhanced
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionRes.java
index ea3ebd9..2461ac7 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link java.lang.Float} or <code>float</code> fields in any enhanced
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DrawableRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DrawableRes.java
index eff163b..8e23731 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DrawableRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DrawableRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link android.graphics.drawable.Drawable} fields in any enhanced
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/HtmlRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/HtmlRes.java
index ece4d32..378a777 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/HtmlRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/HtmlRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link java.lang.CharSequence} or {@link android.text.Spanned} fields
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntArrayRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntArrayRes.java
index ceeae8a..b886468 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntArrayRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntArrayRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link java.lang.Integer} or <code>int</code> array fields in any
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntegerRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntegerRes.java
index 63ee477..3f2aea6 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntegerRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntegerRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link java.lang.Integer} or <code>int</code> fields in any enhanced
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/LayoutRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/LayoutRes.java
index ac86bde..b818c67 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/LayoutRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/LayoutRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link android.content.res.XmlResourceParser} fields in any enhanced
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/MovieRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/MovieRes.java
index a20b19d..c3a36a6 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/MovieRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/MovieRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link android.graphics.Movie} fields in any enhanced classes that
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringArrayRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringArrayRes.java
index 4ab64e2..f191d98 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringArrayRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringArrayRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link java.lang.String} array fields in any enhanced classes that
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringRes.java
index 6fa4879..f663845 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link java.lang.String} fields in any enhanced classes that should be
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextArrayRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextArrayRes.java
index 3aa3bb6..c80e71c 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextArrayRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextArrayRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link java.lang.CharSequence} array fields in any enhanced classes
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextRes.java
index 7a92017..0e2f3ad 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.res;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on {@link java.lang.CharSequence} fields in any enhanced classes that
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Accept.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Accept.java
index 59748a7..a1fd7bf 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Accept.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Accept.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations.rest;
 
-import org.androidannotations.api.rest.MediaType;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -30,7 +28,8 @@
  * <p>
  * The annotation {@link #value()} is mandatory and define the <a
  * href="https://en.wikipedia.org/wiki/Internet_media_type">media type</a> to
- * accept. We provide a {@link MediaType} class to help you.
+ * accept. We provide a {@link org.androidannotations.api.rest.MediaType
+ * MediaType} class to help you.
  * </p>
  * <blockquote>
  * 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Options.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Options.java
index aad4ebf..d5c8e3b 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Options.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Options.java
@@ -19,7 +19,6 @@
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
-import java.util.Set;
 
 /**
  * <p>
@@ -28,7 +27,7 @@
  * </p>
  * <p>
  * This annotation as the same constraints as {@link Get} but it MUST return a
- * {@link Set} of {@link org.springframework.http.HttpMethod}
+ * {@link java.util.Set} of {@link org.springframework.http.HttpMethod}
  * </p>
  * <blockquote>
  * 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresAuthentication.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresAuthentication.java
index 8a194ed..d25e7c0 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresAuthentication.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresAuthentication.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations.rest;
 
-import org.androidannotations.api.rest.RestClientHeaders;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -29,9 +27,11 @@
  * </p>
  * <p>
  * To set the current authentication object to use, you MUST either let your
- * RestClient interface extends of {@link RestClientHeaders} or add the
- * following method to your interface :
- * <code>void setAuthentication(org.springframework.http.HttpAuthentication auth)</code>.
+ * RestClient interface extends of
+ * {@link org.androidannotations.api.rest.RestClientHeaders RestClientHeaders}
+ * or add the following method to your interface :
+ * <code>void setAuthentication(org.springframework.http.HttpAuthentication auth)</code>
+ * .
  * </p>
  * <p>
  * You can also add this specific method for <a
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookie.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookie.java
index c9481eb..3df7e2c 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookie.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookie.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations.rest;
 
-import org.androidannotations.api.rest.RestClientHeaders;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -33,7 +31,8 @@
  * </p>
  * <p>
  * To set a cookie's value you MUST either let your RestClient interface extends
- * of {@link RestClientHeaders} or add the following method to your interface :
+ * of {@link org.androidannotations.api.rest.RestClientHeaders
+ * RestClientHeaders} or add the following method to your interface :
  * <code>void setCookie(String name, String value)</code>.
  * </p>
  * <p>
@@ -82,5 +81,5 @@
 @Retention(RetentionPolicy.CLASS)
 @Target({ ElementType.METHOD, ElementType.TYPE })
 public @interface RequiresCookie {
-	public String[] value();
+	String[] value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookieInUrl.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookieInUrl.java
index 0eddc78..16a70dc 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookieInUrl.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookieInUrl.java
@@ -37,5 +37,5 @@
 @Retention(RetentionPolicy.CLASS)
 @Target({ ElementType.METHOD, ElementType.TYPE })
 public @interface RequiresCookieInUrl {
-	public String[] value();
+	String[] value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresHeader.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresHeader.java
index ebb1c5f..88ad0ef 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresHeader.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresHeader.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations.rest;
 
-import org.androidannotations.api.rest.RestClientHeaders;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -33,7 +31,8 @@
  * </p>
  * <p>
  * To set a header's value you MUST either let your RestClient interface extends
- * of {@link RestClientHeaders} or add the following method to your interface :
+ * of {@link org.androidannotations.api.rest.RestClientHeaders
+ * RestClientHeaders} or add the following method to your interface :
  * <code>void setHeader(String name, String value)</code>.
  * </p>
  * <p>
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Rest.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Rest.java
index 7aeea6a..f1ab516 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Rest.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Rest.java
@@ -15,10 +15,6 @@
  */
 package org.androidannotations.annotations.rest;
 
-import org.androidannotations.api.rest.RestClientHeaders;
-import org.androidannotations.api.rest.RestClientRootUrl;
-import org.androidannotations.api.rest.RestClientSupport;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -199,9 +195,9 @@
  * 
  * 
  * @see RestService
- * @see RestClientSupport
- * @see RestClientRootUrl
- * @see RestClientHeaders
+ * @see org.androidannotations.api.rest.RestClientSupport
+ * @see org.androidannotations.api.rest.RestClientRootUrl
+ * @see org.androidannotations.api.rest.RestClientHeaders
  */
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.TYPE)
@@ -211,5 +207,6 @@
 	Class<?>[] converters();
 
 	Class<?>[] interceptors() default {};
+
 	Class<?> requestFactory() default Void.class;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/SetsCookie.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/SetsCookie.java
index e8192d8..03afdc5 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/SetsCookie.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/SetsCookie.java
@@ -62,5 +62,5 @@
 @Target({ ElementType.METHOD, ElementType.TYPE })
 public @interface SetsCookie {
 
-	public String[] value();
+	String[] value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultBoolean.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultBoolean.java
index 09312ee..a0d466d 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultBoolean.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultBoolean.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.sharedpreferences;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on methods in {@link SharedPref} annotated class to specified the default
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultFloat.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultFloat.java
index ca4cd41..cd4b56a 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultFloat.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultFloat.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.sharedpreferences;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on methods in {@link SharedPref} annotated class to specified the default
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultInt.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultInt.java
index b2a1229..12bc2d8 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultInt.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultInt.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.sharedpreferences;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on methods in {@link SharedPref} annotated class to specified the default
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultLong.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultLong.java
index f054a76..a1bde5a 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultLong.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultLong.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.sharedpreferences;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on methods in {@link SharedPref} annotated class to specified the default
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultRes.java
index c864cf6..cb5ee53 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultRes.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.sharedpreferences;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on methods in {@link SharedPref} annotated class to specified the default
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultString.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultString.java
index 83bcb1b..cc60797 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultString.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultString.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.sharedpreferences;
 
-import org.androidannotations.annotations.ResId;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import org.androidannotations.annotations.ResId;
+
 /**
  * <p>
  * Use on methods in {@link SharedPref} annotated class to specified the default
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/Pref.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/Pref.java
index 23fbb01..f13e380 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/Pref.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/Pref.java
@@ -15,8 +15,6 @@
  */
 package org.androidannotations.annotations.sharedpreferences;
 
-import org.androidannotations.api.sharedpreferences.SharedPreferencesHelper;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
@@ -28,7 +26,9 @@
  * </p>
  * <p>
  * The field MUST be of a type that is generated using {@link SharedPref} and
- * therefore extends {@link SharedPreferencesHelper}.
+ * therefore extends
+ * {@link org.androidannotations.api.sharedpreferences.SharedPreferencesHelper
+ * SharedPreferencesHelper}.
  * </p>
  * <p>
  * <b>Note:</b> To prevent you from any building issues, you should use fully
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/SharedPref.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/SharedPref.java
index 36df999..7f82c1f 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/SharedPref.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/SharedPref.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.annotations.sharedpreferences;
 
-import android.content.Context;
-
 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;
 
+import android.content.Context;
+
 /**
  * <p>
  * Apply @{@link SharedPref} on an interface to create a SharedPreference helper
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/BackgroundExecutor.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/BackgroundExecutor.java
index 7511790..ef038dc 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/BackgroundExecutor.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/BackgroundExecutor.java
@@ -28,23 +28,21 @@
 
 import android.os.Looper;
 import android.util.Log;
-import org.androidannotations.annotations.SupposeBackground;
-import org.androidannotations.annotations.SupposeUiThread;
 
 public class BackgroundExecutor {
 
 	private static final String TAG = "BackgroundExecutor";
 
-	public static Executor DEFAULT_EXECUTOR = Executors.newScheduledThreadPool(2 * Runtime.getRuntime().availableProcessors());
+	public static final Executor DEFAULT_EXECUTOR = Executors.newScheduledThreadPool(2 * Runtime.getRuntime().availableProcessors());
 	private static Executor executor = DEFAULT_EXECUTOR;
 
 	/**
-	 * The default invocation handler for wrong thread execution.
-	 * It just throws {@link IllegalStateException} with explanation what is going wrong.
+	 * The default invocation handler for wrong thread execution. It just throws
+	 * {@link IllegalStateException} with explanation what is going wrong.
 	 *
 	 * @see #setWrongThreadListener(BackgroundExecutor.WrongThreadListener)
-	 * @see SupposeBackground
-	 * @see SupposeUiThread
+	 * @see org.androidannotations.annotations.SupposeBackground
+	 * @see org.androidannotations.annotations.SupposeUiThread
 	 */
 	public static final WrongThreadListener DEFAULT_WRONG_THREAD_LISTENER = new WrongThreadListener() {
 		@Override
@@ -71,8 +69,11 @@ public void onWrongBgSerial(String currentSerial, String... expectedSerials) {
 
 	private static WrongThreadListener wrongThreadListener = DEFAULT_WRONG_THREAD_LISTENER;
 
-	private static final List<Task> tasks = new ArrayList<Task>();
-	private static final ThreadLocal<String> currentSerial = new ThreadLocal<String>();
+	private static final List<Task> TASKS = new ArrayList<Task>();
+	private static final ThreadLocal<String> CURRENT_SERIAL = new ThreadLocal<String>();
+
+	private BackgroundExecutor() {
+	}
 
 	/**
 	 * Execute a runnable after the given delay.
@@ -131,7 +132,7 @@ public static synchronized void execute(Task task) {
 		if (task.id != null || task.serial != null) {
 			/* keep task */
 			task.future = future;
-			tasks.add(task);
+			TASKS.add(task);
 		}
 	}
 
@@ -224,10 +225,11 @@ public static void setExecutor(Executor executor) {
 	}
 
 	/**
-	 * Changes the default {@link WrongThreadListener}.
-	 * To restore the default one use {@link #DEFAULT_WRONG_THREAD_LISTENER}.
+	 * Changes the default {@link WrongThreadListener}. To restore the default
+	 * one use {@link #DEFAULT_WRONG_THREAD_LISTENER}.
 	 *
-	 * @param listener the new {@link WrongThreadListener}
+	 * @param listener
+	 *            the new {@link WrongThreadListener}
 	 */
 	public static void setWrongThreadListener(WrongThreadListener listener) {
 		wrongThreadListener = listener;
@@ -244,8 +246,8 @@ public static void setWrongThreadListener(WrongThreadListener listener) {
 	 *            complete
 	 */
 	public static synchronized void cancelAll(String id, boolean mayInterruptIfRunning) {
-		for (int i = tasks.size() - 1; i >= 0; i--) {
-			Task task = tasks.get(i);
+		for (int i = TASKS.size() - 1; i >= 0; i--) {
+			Task task = TASKS.get(i);
 			if (id.equals(task.id)) {
 				if (task.future != null) {
 					task.future.cancel(mayInterruptIfRunning);
@@ -261,7 +263,7 @@ public static synchronized void cancelAll(String id, boolean mayInterruptIfRunni
 					Log.w(TAG, "A task with id " + task.id + " cannot be cancelled (the executor set does not support it)");
 				} else {
 					/* this task has not been submitted to the executor */
-					tasks.remove(i);
+					TASKS.remove(i);
 				}
 			}
 		}
@@ -269,7 +271,8 @@ public static synchronized void cancelAll(String id, boolean mayInterruptIfRunni
 
 	/**
 	 * Checks if the current thread is UI thread and notifies
-	 * {@link BackgroundExecutor.WrongThreadListener#onUiExpected()} if it doesn't.
+	 * {@link BackgroundExecutor.WrongThreadListener#onUiExpected()} if it
+	 * doesn't.
 	 */
 	public static void checkUiThread() {
 		if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
@@ -278,17 +281,21 @@ public static void checkUiThread() {
 	}
 
 	/**
-	 * Checks if the current thread is a background thread and, optionally, restricts it
-	 * with passed serials. If no serials passed and current thread is the UI thread, then
-	 * {@link WrongThreadListener#onBgExpected(String...)} will be called.
-	 * If the current thread is not UI and serials list is empty, then this method just returns.
-	 * Otherwise, if the method was called not during {@link Task} execution or the task has no
-	 * serial, then the {@link WrongThreadListener#onWrongBgSerial(String, String...)} will be called
-	 * with null for the first parameter. If task has a serial but passed serials don't contain that,
-	 * then {@link WrongThreadListener#onWrongBgSerial(String, String...)} will be called with
-	 * the task's serial for the first parameter.
+	 * Checks if the current thread is a background thread and, optionally,
+	 * restricts it with passed serials. If no serials passed and current thread
+	 * is the UI thread, then
+	 * {@link WrongThreadListener#onBgExpected(String...)} will be called. If
+	 * the current thread is not UI and serials list is empty, then this method
+	 * just returns. Otherwise, if the method was called not during {@link Task}
+	 * execution or the task has no serial, then the
+	 * {@link WrongThreadListener#onWrongBgSerial(String, String...)} will be
+	 * called with null for the first parameter. If task has a serial but passed
+	 * serials don't contain that, then
+	 * {@link WrongThreadListener#onWrongBgSerial(String, String...)} will be
+	 * called with the task's serial for the first parameter.
 	 *
-	 * @param serials (optional) list of allowed serials
+	 * @param serials
+	 *            (optional) list of allowed serials
 	 */
 	public static void checkBgThread(String... serials) {
 		if (serials.length == 0) {
@@ -297,7 +304,7 @@ public static void checkBgThread(String... serials) {
 			}
 			return;
 		}
-		String current = currentSerial.get();
+		String current = CURRENT_SERIAL.get();
 		if (current == null) {
 			wrongThreadListener.onWrongBgSerial(null, serials);
 			return;
@@ -314,12 +321,13 @@ public static void checkBgThread(String... serials) {
 	 * Indicates whether a task with the specified <code>serial</code> has been
 	 * submitted to the executor.
 	 *
-	 * @param serial the serial queue
+	 * @param serial
+	 *            the serial queue
 	 * @return <code>true</code> if such a task has been submitted,
-	 * <code>false</code> otherwise
+	 *         <code>false</code> otherwise
 	 */
 	private static boolean hasSerialRunning(String serial) {
-		for (Task task : tasks) {
+		for (Task task : TASKS) {
 			if (task.executionAsked && serial.equals(task.serial)) {
 				return true;
 			}
@@ -336,10 +344,10 @@ private static boolean hasSerialRunning(String serial) {
 	 * @return task if found, <code>null</code> otherwise
 	 */
 	private static Task take(String serial) {
-		int len = tasks.size();
+		int len = TASKS.size();
 		for (int i = 0; i < len; i++) {
-			if (serial.equals(tasks.get(i).serial)) {
-				return tasks.remove(i);
+			if (serial.equals(TASKS.get(i).serial)) {
+				return TASKS.remove(i);
 			}
 		}
 		return null;
@@ -389,7 +397,7 @@ public void run() {
 			}
 
 			try {
-				currentSerial.set(serial);
+				CURRENT_SERIAL.set(serial);
 				execute();
 			} finally {
 				/* handle next tasks */
@@ -404,10 +412,10 @@ private void postExecute() {
 				/* nothing to do */
 				return;
 			}
-			currentSerial.set(null);
+			CURRENT_SERIAL.set(null);
 			synchronized (BackgroundExecutor.class) {
 				/* execution complete */
-				tasks.remove(this);
+				TASKS.remove(this);
 
 				if (serial != null) {
 					Task next = take(serial);
@@ -426,45 +434,51 @@ private void postExecute() {
 	}
 
 	/**
-	 * A callback interface to be notified when a method invocation is expected from another thread.
+	 * A callback interface to be notified when a method invocation is expected
+	 * from another thread.
 	 *
 	 * @see #setWrongThreadListener(WrongThreadListener)
 	 * @see #checkUiThread()
 	 * @see #checkBgThread(String...)
-	 * @see SupposeUiThread
-	 * @see SupposeBackground
+	 * @see org.androidannotations.annotations.SupposeUiThread
+	 * @see org.androidannotations.annotations.SupposeBackground
 	 */
-	public static interface WrongThreadListener {
+	public interface WrongThreadListener {
 
 		/**
-		 * Will be called, if the method is supposed to be called from the UI-thread, but was called from a background
-		 * thread.
+		 * Will be called, if the method is supposed to be called from the
+		 * UI-thread, but was called from a background thread.
 		 *
-		 * @see SupposeUiThread
+		 * @see org.androidannotations.annotations.SupposeUiThread
 		 * @see #setWrongThreadListener(WrongThreadListener)
 		 * @see #DEFAULT_WRONG_THREAD_LISTENER
 		 */
 		void onUiExpected();
 
 		/**
-		 * Will be called, if the method is supposed to be called from a background thread, but was called from the
-		 * UI-thread.
+		 * Will be called, if the method is supposed to be called from a
+		 * background thread, but was called from the UI-thread.
 		 *
-		 * @param expectedSerials a list of allowed serials. If any background thread is allowed the list will be empty.
-		 * @see SupposeBackground
+		 * @param expectedSerials
+		 *            a list of allowed serials. If any background thread is
+		 *            allowed the list will be empty.
+		 * @see org.androidannotations.annotations.SupposeBackground
 		 * @see #setWrongThreadListener(WrongThreadListener)
 		 * @see #DEFAULT_WRONG_THREAD_LISTENER
 		 */
 		void onBgExpected(String... expectedSerials);
 
 		/**
-		 * Will be called, if the method is supposed to be called from a background thread with one of
-		 * {@code expectedSerials}, but was called from a {@code currentSerial}. {@code currentSerial} will be null,
-		 * if it is called from a background thread without a serial.
+		 * Will be called, if the method is supposed to be called from a
+		 * background thread with one of {@code expectedSerials}, but was called
+		 * from a {@code currentSerial}. {@code currentSerial} will be null, if
+		 * it is called from a background thread without a serial.
 		 *
-		 * @param currentSerial   the serial of caller thread or null if there is no serial
-		 * @param expectedSerials a list of allowed serials
-		 * @see SupposeBackground
+		 * @param currentSerial
+		 *            the serial of caller thread or null if there is no serial
+		 * @param expectedSerials
+		 *            a list of allowed serials
+		 * @see org.androidannotations.annotations.SupposeBackground
 		 * @see #setWrongThreadListener(WrongThreadListener)
 		 * @see #DEFAULT_WRONG_THREAD_LISTENER
 		 */
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/SdkVersionHelper.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/SdkVersionHelper.java
index 229ac56..7e65920 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/SdkVersionHelper.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/SdkVersionHelper.java
@@ -20,9 +20,13 @@
 
 public class SdkVersionHelper {
 
+	private SdkVersionHelper() {
+	}
+
 	public static int getSdkInt() {
-		if (Build.VERSION.RELEASE.startsWith("1.5"))
+		if (Build.VERSION.RELEASE.startsWith("1.5")) {
 			return 3;
+		}
 
 		return HelperInternal.getSdkIntInternal();
 	}
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/ViewServer.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/ViewServer.java
index 7aaadc7..380e562 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/ViewServer.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/ViewServer.java
@@ -15,28 +15,34 @@
  */
 package org.androidannotations.api;
 
-import android.app.Activity;
-import android.content.Context;
-import android.content.pm.ApplicationInfo;
-import android.os.Build;
-import android.text.TextUtils;
-import android.util.Log;
-import android.view.View;
-import android.view.ViewDebug;
-
-import java.io.*;
+import java.io.BufferedReader;
+import java.io.BufferedWriter;
+import java.io.IOException;
+import java.io.InputStreamReader;
+import java.io.OutputStream;
+import java.io.OutputStreamWriter;
 import java.lang.reflect.Method;
 import java.net.InetAddress;
 import java.net.ServerSocket;
 import java.net.Socket;
 import java.util.HashMap;
 import java.util.List;
+import java.util.Map;
 import java.util.Map.Entry;
 import java.util.concurrent.CopyOnWriteArrayList;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import java.util.concurrent.locks.ReentrantReadWriteLock;
 
+import android.app.Activity;
+import android.content.Context;
+import android.content.pm.ApplicationInfo;
+import android.os.Build;
+import android.text.TextUtils;
+import android.util.Log;
+import android.view.View;
+import android.view.ViewDebug;
+
 /**
  * <p>
  * This class can be used to enable the use of HierarchyViewer inside an
@@ -139,7 +145,7 @@
 
 	private final List<WindowListener> mListeners = new CopyOnWriteArrayList<ViewServer.WindowListener>();
 
-	private final HashMap<View, String> mWindows = new HashMap<View, String>();
+	private final Map<View, String> mWindows = new HashMap<View, String>();
 	private final ReentrantReadWriteLock mWindowsLock = new ReentrantReadWriteLock();
 
 	private View mFocusedWindow;
@@ -209,8 +215,7 @@ private ViewServer(int port) {
 	 *             If the server cannot be created.
 	 * 
 	 * @see #stop()
-	 * @see #isRunning()
-	 * see WindowManagerService#startViewServer(int)
+	 * @see #isRunning() see WindowManagerService#startViewServer(int)
 	 */
 	public boolean start() throws IOException {
 		if (mThread != null) {
@@ -231,8 +236,7 @@ public boolean start() throws IOException {
 	 *         the server wasn't started.
 	 * 
 	 * @see #start()
-	 * @see #isRunning()
-	 * see WindowManagerService#stopViewServer()
+	 * @see #isRunning() see WindowManagerService#stopViewServer()
 	 */
 	public boolean stop() {
 		if (mThread != null) {
@@ -280,8 +284,7 @@ public boolean stop() {
 	 * @return True if the server is running, false otherwise.
 	 * 
 	 * @see #start()
-	 * @see #stop()
-	 * see WindowManagerService#isViewServerRunning()
+	 * @see #stop() see WindowManagerService#isViewServerRunning()
 	 */
 	public boolean isRunning() {
 		return mThread != null && mThread.isAlive();
@@ -322,8 +325,10 @@ public void removeWindow(Activity activity) {
 	/**
 	 * Invoke this method to register a new view hierarchy.
 	 * 
-	 * @param view A view that belongs to the view hierarchy/window to register
-	 * @param name The name of the view hierarchy/window to register
+	 * @param view
+	 *            A view that belongs to the view hierarchy/window to register
+	 * @param name
+	 *            The name of the view hierarchy/window to register
 	 * 
 	 * @see #removeWindow(View)
 	 */
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/rest/RestClientHeaders.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/rest/RestClientHeaders.java
index d0d2790..2eda8df 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/rest/RestClientHeaders.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/rest/RestClientHeaders.java
@@ -26,7 +26,8 @@
 	/**
 	 * Gets a cookie by name.
 	 * 
-	 * @param name Name of the cookie
+	 * @param name
+	 *            Name of the cookie
 	 * @return the cookie value.
 	 */
 	String getCookie(String name);
@@ -34,15 +35,18 @@
 	/**
 	 * Sets a cookie by name.
 	 * 
-	 * @param name Name of the cookie
-	 * @param value Value of the cookie
+	 * @param name
+	 *            Name of the cookie
+	 * @param value
+	 *            Value of the cookie
 	 */
 	void setCookie(String name, String value);
 
 	/**
 	 * Gets a header by name.
 	 * 
-	 * @param name Name of the header
+	 * @param name
+	 *            Name of the header
 	 * @return the header value.
 	 */
 	String getHeader(String name);
@@ -50,30 +54,36 @@
 	/**
 	 * Sets a header by name.
 	 * 
-	 * @param name Name of the header
-	 * @param value Value of the header
+	 * @param name
+	 *            Name of the header
+	 * @param value
+	 *            Value of the header
 	 */
 	void setHeader(String name, String value);
 
 	/**
 	 * Sets the authentication object.
 	 * 
-	 * @param auth Authentication data
+	 * @param auth
+	 *            Authentication data
 	 */
 	void setAuthentication(HttpAuthentication auth);
 
 	/**
 	 * Sets the basic authentication user/password.
 	 * 
-	 * @param user Name of the user
-	 * @param password Password of the user
+	 * @param user
+	 *            Name of the user
+	 * @param password
+	 *            Password of the user
 	 */
 	void setHttpBasicAuth(String user, String password);
 
-    /**
-     * Sets the Authorization: Bearer header as documented in RFC6750
-     *
-     * @param token Token used for authentication
-     */
-    void setBearerAuth(String token);
+	/**
+	 * Sets the Authorization: Bearer header as documented in RFC6750
+	 *
+	 * @param token
+	 *            Token used for authentication
+	 */
+	void setBearerAuth(String token);
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/roboguice/RoboGuiceHelper.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/roboguice/RoboGuiceHelper.java
index 45621f6..f2a1cee 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/roboguice/RoboGuiceHelper.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/roboguice/RoboGuiceHelper.java
@@ -24,6 +24,9 @@
  */
 public class RoboGuiceHelper {
 
+	private RoboGuiceHelper() {
+	}
+
 	public static void callInjectViews(Object activity) {
 		try {
 			Class<?> viewMembersInjectorClass = Class.forName("roboguice.inject.ViewListener$ViewMembersInjector");
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/sharedpreferences/SharedPreferencesCompat.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/sharedpreferences/SharedPreferencesCompat.java
index bc8d2c8..6ce8fb1 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/sharedpreferences/SharedPreferencesCompat.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/api/sharedpreferences/SharedPreferencesCompat.java
@@ -30,13 +30,13 @@
 	private SharedPreferencesCompat() {
 	}
 
-	private static final Method sApplyMethod = findMethod(SharedPreferences.Editor.class, "apply");
-	private static final Method sGetStringSetMethod = findMethod(SharedPreferences.class, "getStringSet", String.class, Set.class);
-	private static final Method sPutStringSetMethod = findMethod(SharedPreferences.Editor.class, "putStringSet", String.class, Set.class);
+	private static final Method APPLY_METHOD = findMethod(SharedPreferences.Editor.class, "apply");
+	private static final Method GET_STRING_SET_METHOD = findMethod(SharedPreferences.class, "getStringSet", String.class, Set.class);
+	private static final Method PUT_STRING_SET_METHOD = findMethod(SharedPreferences.Editor.class, "putStringSet", String.class, Set.class);
 
 	public static void apply(SharedPreferences.Editor editor) {
 		try {
-			invoke(sApplyMethod, editor);
+			invoke(APPLY_METHOD, editor);
 			return;
 		} catch (NoSuchMethodException e) {
 			editor.commit();
@@ -45,7 +45,7 @@ public static void apply(SharedPreferences.Editor editor) {
 
 	public static Set<String> getStringSet(SharedPreferences preferences, String key, Set<String> defValues) {
 		try {
-			return invoke(sGetStringSetMethod, preferences, key, defValues);
+			return invoke(GET_STRING_SET_METHOD, preferences, key, defValues);
 		} catch (NoSuchMethodException e) {
 			String serializedSet = preferences.getString(key, null);
 			return SetXmlSerializer.deserialize(serializedSet);
@@ -54,7 +54,7 @@ public static void apply(SharedPreferences.Editor editor) {
 
 	public static void putStringSet(SharedPreferences.Editor editor, String key, Set<String> values) {
 		try {
-			invoke(sPutStringSetMethod, editor, key, values);
+			invoke(PUT_STRING_SET_METHOD, editor, key, values);
 		} catch (NoSuchMethodException e1) {
 			editor.putString(key, SetXmlSerializer.serialize(values));
 		}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/com/sun/codemodel/JSuperWildcard.java b/AndroidAnnotations/androidannotations/src/main/java/com/sun/codemodel/JSuperWildcard.java
index 8fbf7f3..9c4fe01 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/com/sun/codemodel/JSuperWildcard.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/com/sun/codemodel/JSuperWildcard.java
@@ -45,6 +45,8 @@ public String fullName() {
 		return "? super " + bound.fullName();
 	}
 
+	// CHECKSTYLE:OFF
+
 	@Override
 	public JPackage _package() {
 		return null;
@@ -52,7 +54,7 @@ public JPackage _package() {
 
 	/**
 	 * Returns the class bound of this variable.
-	 * 
+	 *
 	 * <p>
 	 * If no bound is given, this method returns {@link Object}.
 	 */
@@ -73,6 +75,8 @@ public JClass _extends() {
 		return bound._implements();
 	}
 
+	// CHECKSTYLE:ON
+
 	@Override
 	public boolean isInterface() {
 		return false;
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/AndroidAnnotationProcessor.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/AndroidAnnotationProcessor.java
index 8bc40b4..6f9e870 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/AndroidAnnotationProcessor.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/AndroidAnnotationProcessor.java
@@ -25,7 +25,6 @@
 import javax.annotation.processing.AbstractProcessor;
 import javax.annotation.processing.ProcessingEnvironment;
 import javax.annotation.processing.RoundEnvironment;
-import javax.annotation.processing.SupportedSourceVersion;
 import javax.lang.model.SourceVersion;
 import javax.lang.model.element.Element;
 import javax.lang.model.element.TypeElement;
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/exception/ProcessingException.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/exception/ProcessingException.java
index c22d4dc..4277a1b 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/exception/ProcessingException.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/exception/ProcessingException.java
@@ -21,7 +21,7 @@
 
 	private static final long serialVersionUID = -1282996599471872615L;
 
-	private Element element;
+	private final Element element;
 
 	public ProcessingException(Throwable cause, Element element) {
 		super(cause);
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/AnnotationHandlers.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/AnnotationHandlers.java
index 55a99f3..cd0c452 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/AnnotationHandlers.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/AnnotationHandlers.java
@@ -100,7 +100,7 @@ public AnnotationHandlers(ProcessingEnvironment processingEnvironment) {
 		add(new FullscreenHandler(processingEnvironment));
 		add(new RestServiceHandler(processingEnvironment));
 		add(new OrmLiteDaoHandler(processingEnvironment));
-		add(new RootContextHanlder(processingEnvironment));
+		add(new RootContextHandler(processingEnvironment));
 		add(new NonConfigurationInstanceHandler(processingEnvironment));
 		add(new BeanHandler(processingEnvironment));
 		add(new ExtraHandler(processingEnvironment));
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/BeanHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/BeanHandler.java
index 5766365..444dc92 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/BeanHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/BeanHandler.java
@@ -15,10 +15,13 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.JBlock;
-import com.sun.codemodel.JClass;
-import com.sun.codemodel.JFieldRef;
-import com.sun.codemodel.JInvocation;
+import static com.sun.codemodel.JExpr._null;
+import static com.sun.codemodel.JExpr.ref;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.type.TypeMirror;
+
 import org.androidannotations.annotations.Bean;
 import org.androidannotations.annotations.EBean;
 import org.androidannotations.annotations.NonConfigurationInstance;
@@ -28,12 +31,10 @@
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.process.IsValid;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.type.TypeMirror;
-
-import static com.sun.codemodel.JExpr._null;
-import static com.sun.codemodel.JExpr.ref;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JFieldRef;
+import com.sun.codemodel.JInvocation;
 
 public class BeanHandler extends BaseAnnotationHandler<EComponentHolder> {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/CheckedChangeHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/CheckedChangeHandler.java
index 06f48bd..6def887 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/CheckedChangeHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/CheckedChangeHandler.java
@@ -15,13 +15,7 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.*;
-
-import org.androidannotations.annotations.CheckedChange;
-import org.androidannotations.helper.CanonicalNameConstants;
-import org.androidannotations.holder.EComponentWithViewSupportHolder;
-import org.androidannotations.model.AnnotationElements;
-import org.androidannotations.process.IsValid;
+import java.util.List;
 
 import javax.annotation.processing.ProcessingEnvironment;
 import javax.lang.model.element.Element;
@@ -30,7 +24,19 @@
 import javax.lang.model.type.TypeKind;
 import javax.lang.model.type.TypeMirror;
 
-import java.util.List;
+import org.androidannotations.annotations.CheckedChange;
+import org.androidannotations.helper.CanonicalNameConstants;
+import org.androidannotations.holder.EComponentWithViewSupportHolder;
+import org.androidannotations.model.AnnotationElements;
+import org.androidannotations.process.IsValid;
+
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JDefinedClass;
+import com.sun.codemodel.JInvocation;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JMod;
+import com.sun.codemodel.JVar;
 
 public class CheckedChangeHandler extends AbstractListenerHandler {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EActivityHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EActivityHandler.java
index 1297141..c4b91d5 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EActivityHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EActivityHandler.java
@@ -15,9 +15,12 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.JBlock;
-import com.sun.codemodel.JFieldRef;
-import com.sun.codemodel.JMethod;
+import java.util.List;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.TypeElement;
+
 import org.androidannotations.annotations.EActivity;
 import org.androidannotations.helper.AnnotationHelper;
 import org.androidannotations.helper.IdValidatorHelper;
@@ -27,10 +30,9 @@
 import org.androidannotations.process.ProcessHolder;
 import org.androidannotations.rclass.IRClass;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.TypeElement;
-import java.util.List;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JFieldRef;
+import com.sun.codemodel.JMethod;
 
 public class EActivityHandler extends BaseGeneratingAnnotationHandler<EActivityHolder> {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EApplicationHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EApplicationHandler.java
index 3a72428..c1fc7cc 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EApplicationHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EApplicationHandler.java
@@ -15,16 +15,16 @@
  */
 package org.androidannotations.handler;
 
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.TypeElement;
+
 import org.androidannotations.annotations.EApplication;
 import org.androidannotations.holder.EApplicationHolder;
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.process.IsValid;
 import org.androidannotations.process.ProcessHolder;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.TypeElement;
-
 public class EApplicationHandler extends BaseGeneratingAnnotationHandler<EApplicationHolder> {
 
 	public EApplicationHandler(ProcessingEnvironment processingEnvironment) {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EBeanHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EBeanHandler.java
index 898992c..45b3747 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EBeanHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EBeanHandler.java
@@ -15,16 +15,16 @@
  */
 package org.androidannotations.handler;
 
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.TypeElement;
+
 import org.androidannotations.annotations.EBean;
 import org.androidannotations.holder.EBeanHolder;
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.process.IsValid;
 import org.androidannotations.process.ProcessHolder;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.TypeElement;
-
 public class EBeanHandler extends BaseGeneratingAnnotationHandler<EBeanHolder> {
 
 	public EBeanHandler(ProcessingEnvironment processingEnvironment) {
@@ -54,7 +54,7 @@ public void process(Element element, EBeanHolder holder) {
 		holder.createFactoryMethod(hasSingletonScope);
 
 		if (!hasSingletonScope) {
-            holder.invokeInitInConstructor();
+			holder.invokeInitInConstructor();
 			holder.createRebindMethod();
 		}
 	}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EFragmentHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EFragmentHandler.java
index b47b458..40424d6 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EFragmentHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EFragmentHandler.java
@@ -15,10 +15,13 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.JBlock;
-import com.sun.codemodel.JFieldRef;
-import com.sun.codemodel.JFieldVar;
-import com.sun.codemodel.JVar;
+import static com.sun.codemodel.JExpr.FALSE;
+import static com.sun.codemodel.JExpr._null;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.TypeElement;
+
 import org.androidannotations.annotations.EFragment;
 import org.androidannotations.helper.IdAnnotationHelper;
 import org.androidannotations.helper.IdValidatorHelper;
@@ -28,12 +31,10 @@
 import org.androidannotations.process.ProcessHolder;
 import org.androidannotations.rclass.IRClass;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.TypeElement;
-
-import static com.sun.codemodel.JExpr.FALSE;
-import static com.sun.codemodel.JExpr._null;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JFieldRef;
+import com.sun.codemodel.JFieldVar;
+import com.sun.codemodel.JVar;
 
 public class EFragmentHandler extends BaseGeneratingAnnotationHandler<EFragmentHolder> {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EIntentServiceHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EIntentServiceHandler.java
index 96eed57..e94f728 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EIntentServiceHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EIntentServiceHandler.java
@@ -28,29 +28,29 @@
 
 public class EIntentServiceHandler extends BaseAnnotationHandler<EIntentServiceHolder> implements GeneratingAnnotationHandler<EIntentServiceHolder> {
 
-    public EIntentServiceHandler(ProcessingEnvironment processingEnvironment) {
-        super(EIntentService.class, processingEnvironment);
-    }
+	public EIntentServiceHandler(ProcessingEnvironment processingEnvironment) {
+		super(EIntentService.class, processingEnvironment);
+	}
 
-    @Override
-    public EIntentServiceHolder createGeneratedClassHolder(ProcessHolder processHolder, TypeElement annotatedElement) throws Exception {
-        return new EIntentServiceHolder(processHolder, annotatedElement, androidManifest);
-    }
+	@Override
+	public EIntentServiceHolder createGeneratedClassHolder(ProcessHolder processHolder, TypeElement annotatedElement) throws Exception {
+		return new EIntentServiceHolder(processHolder, annotatedElement, androidManifest);
+	}
 
-    @Override
-    public void validate(Element element, AnnotationElements validatedElements, IsValid valid) {
-        validatorHelper.extendsIntentService(element, valid);
+	@Override
+	public void validate(Element element, AnnotationElements validatedElements, IsValid valid) {
+		validatorHelper.extendsIntentService(element, valid);
 
-        validatorHelper.hasNotMultipleAnnotatedMethodWithSameName(element, valid, ServiceAction.class);
+		validatorHelper.hasNotMultipleAnnotatedMethodWithSameName(element, valid, ServiceAction.class);
 
-        validatorHelper.isNotFinal(element, valid);
+		validatorHelper.isNotFinal(element, valid);
 
-        validatorHelper.componentRegistered(element, androidManifest, valid);
-    }
+		validatorHelper.componentRegistered(element, androidManifest, valid);
+	}
 
-    @Override
-    public void process(Element element, EIntentServiceHolder holder) {
+	@Override
+	public void process(Element element, EIntentServiceHolder holder) {
 		/* Do nothing */
-    }
+	}
 
 }
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EProviderHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EProviderHandler.java
index a0d9504..b6a821f 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EProviderHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EProviderHandler.java
@@ -15,16 +15,16 @@
  */
 package org.androidannotations.handler;
 
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.TypeElement;
+
 import org.androidannotations.annotations.EProvider;
 import org.androidannotations.holder.EProviderHolder;
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.process.IsValid;
 import org.androidannotations.process.ProcessHolder;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.TypeElement;
-
 public class EProviderHandler extends BaseGeneratingAnnotationHandler<EProviderHolder> {
 
 	public EProviderHandler(ProcessingEnvironment processingEnvironment) {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EReceiverHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EReceiverHandler.java
index ee838cf..43ba8c3 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EReceiverHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EReceiverHandler.java
@@ -15,16 +15,16 @@
  */
 package org.androidannotations.handler;
 
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.TypeElement;
+
 import org.androidannotations.annotations.EReceiver;
 import org.androidannotations.holder.EReceiverHolder;
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.process.IsValid;
 import org.androidannotations.process.ProcessHolder;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.TypeElement;
-
 public class EReceiverHandler extends BaseGeneratingAnnotationHandler<EReceiverHolder> {
 
 	public EReceiverHandler(ProcessingEnvironment processingEnvironment) {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EServiceHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EServiceHandler.java
index a350305..6f7011d 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EServiceHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EServiceHandler.java
@@ -15,16 +15,16 @@
  */
 package org.androidannotations.handler;
 
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.TypeElement;
+
 import org.androidannotations.annotations.EService;
 import org.androidannotations.holder.EServiceHolder;
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.process.IsValid;
 import org.androidannotations.process.ProcessHolder;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.TypeElement;
-
 public class EServiceHandler extends BaseGeneratingAnnotationHandler<EServiceHolder> {
 
 	public EServiceHandler(ProcessingEnvironment processingEnvironment) {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EViewGroupHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EViewGroupHandler.java
index 5e42377..7f7cdd6 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EViewGroupHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EViewGroupHandler.java
@@ -15,8 +15,10 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.JExpr;
-import com.sun.codemodel.JFieldRef;
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.TypeElement;
+
 import org.androidannotations.annotations.EViewGroup;
 import org.androidannotations.helper.AndroidManifest;
 import org.androidannotations.helper.IdAnnotationHelper;
@@ -28,9 +30,8 @@
 import org.androidannotations.process.ProcessHolder;
 import org.androidannotations.rclass.IRClass;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.TypeElement;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JFieldRef;
 
 public class EViewGroupHandler extends BaseGeneratingAnnotationHandler<EViewGroupHolder> {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EViewHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EViewHandler.java
index 46d1dd8..befd3b7 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EViewHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EViewHandler.java
@@ -15,16 +15,16 @@
  */
 package org.androidannotations.handler;
 
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.TypeElement;
+
 import org.androidannotations.annotations.EView;
 import org.androidannotations.holder.EViewHolder;
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.process.IsValid;
 import org.androidannotations.process.ProcessHolder;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.TypeElement;
-
 public class EViewHandler extends BaseGeneratingAnnotationHandler<EViewHolder> {
 
 	public EViewHandler(ProcessingEnvironment processingEnvironment) {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EditorActionHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EditorActionHandler.java
index b19c89e..c4443f5 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EditorActionHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/EditorActionHandler.java
@@ -15,13 +15,7 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.*;
-
-import org.androidannotations.annotations.EditorAction;
-import org.androidannotations.helper.CanonicalNameConstants;
-import org.androidannotations.holder.EComponentWithViewSupportHolder;
-import org.androidannotations.model.AnnotationElements;
-import org.androidannotations.process.IsValid;
+import java.util.List;
 
 import javax.annotation.processing.ProcessingEnvironment;
 import javax.lang.model.element.Element;
@@ -30,7 +24,20 @@
 import javax.lang.model.type.TypeKind;
 import javax.lang.model.type.TypeMirror;
 
-import java.util.List;
+import org.androidannotations.annotations.EditorAction;
+import org.androidannotations.helper.CanonicalNameConstants;
+import org.androidannotations.holder.EComponentWithViewSupportHolder;
+import org.androidannotations.model.AnnotationElements;
+import org.androidannotations.process.IsValid;
+
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JDefinedClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JInvocation;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JMod;
+import com.sun.codemodel.JVar;
 
 public class EditorActionHandler extends AbstractListenerHandler {
 
@@ -79,7 +86,7 @@ protected void processParameters(EComponentWithViewSupportHolder holder, JMethod
 				call.arg(textView);
 			} else if (paramClassQualifiedName.equals(CanonicalNameConstants.INTEGER) || paramClassQualifiedName.equals(codeModel().INT.fullName())) {
 				call.arg(actionId);
-			} else if(paramClassQualifiedName.equals(CanonicalNameConstants.KEY_EVENT)) {
+			} else if (paramClassQualifiedName.equals(CanonicalNameConstants.KEY_EVENT)) {
 				call.arg(event);
 			}
 		}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ExtraHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ExtraHandler.java
index a115e01..6b90263 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ExtraHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ExtraHandler.java
@@ -15,14 +15,16 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.JBlock;
-import com.sun.codemodel.JClass;
-import com.sun.codemodel.JExpr;
-import com.sun.codemodel.JExpression;
-import com.sun.codemodel.JFieldRef;
-import com.sun.codemodel.JFieldVar;
-import com.sun.codemodel.JMethod;
-import com.sun.codemodel.JVar;
+import static com.sun.codemodel.JExpr.invoke;
+import static com.sun.codemodel.JExpr.lit;
+import static com.sun.codemodel.JMod.FINAL;
+import static com.sun.codemodel.JMod.PUBLIC;
+import static com.sun.codemodel.JMod.STATIC;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.type.TypeMirror;
+
 import org.androidannotations.annotations.Extra;
 import org.androidannotations.helper.APTCodeModelHelper;
 import org.androidannotations.helper.AnnotationHelper;
@@ -33,15 +35,14 @@
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.process.IsValid;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.type.TypeMirror;
-
-import static com.sun.codemodel.JExpr.invoke;
-import static com.sun.codemodel.JExpr.lit;
-import static com.sun.codemodel.JMod.FINAL;
-import static com.sun.codemodel.JMod.PUBLIC;
-import static com.sun.codemodel.JMod.STATIC;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JFieldRef;
+import com.sun.codemodel.JFieldVar;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JVar;
 
 public class ExtraHandler extends BaseAnnotationHandler<HasExtras> {
 
@@ -77,17 +78,18 @@ public void process(Element element, HasExtras holder) {
 		JFieldVar extraKeyStaticField = createStaticExtraField(holder, extraKey, fieldName);
 		injectExtraInComponent(element, holder, extraKeyStaticField, fieldName);
 
-		if (holder instanceof HasIntentBuilder)
+		if (holder instanceof HasIntentBuilder) {
 			createIntentInjectionMethod(element, (HasIntentBuilder) holder, extraKeyStaticField, fieldName);
+		}
 	}
 
 	private JFieldVar createStaticExtraField(HasExtras holder, String extraKey, String fieldName) {
-        String staticFieldName = CaseHelper.camelCaseToUpperSnakeCase(null, fieldName, "Extra");
-        JFieldVar staticExtraField = holder.getGeneratedClass().fields().get(staticFieldName);
-        if (staticExtraField == null) {
-            staticExtraField = holder.getGeneratedClass().field(PUBLIC | STATIC | FINAL, classes().STRING, staticFieldName, lit(extraKey));
-        }
-        return staticExtraField;
+		String staticFieldName = CaseHelper.camelCaseToUpperSnakeCase(null, fieldName, "Extra");
+		JFieldVar staticExtraField = holder.getGeneratedClass().fields().get(staticFieldName);
+		if (staticExtraField == null) {
+			staticExtraField = holder.getGeneratedClass().field(PUBLIC | STATIC | FINAL, classes().STRING, staticFieldName, lit(extraKey));
+		}
+		return staticExtraField;
 	}
 
 	private void injectExtraInComponent(Element element, HasExtras hasExtras, JFieldVar extraKeyStaticField, String fieldName) {
@@ -108,6 +110,6 @@ private void injectExtraInComponent(Element element, HasExtras hasExtras, JField
 	}
 
 	private void createIntentInjectionMethod(Element element, HasIntentBuilder holder, JFieldVar extraKeyStaticField, String fieldName) {
-        holder.getIntentBuilder().getPutExtraMethod(element.asType(), fieldName, extraKeyStaticField);
+		holder.getIntentBuilder().getPutExtraMethod(element.asType(), fieldName, extraKeyStaticField);
 	}
 }
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/FocusChangeHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/FocusChangeHandler.java
index acaeee9..5de5301 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/FocusChangeHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/FocusChangeHandler.java
@@ -15,10 +15,7 @@
  */
 package org.androidannotations.handler;
 
-import org.androidannotations.annotations.FocusChange;
-import org.androidannotations.helper.CanonicalNameConstants;
-import org.androidannotations.model.AnnotationElements;
-import org.androidannotations.process.IsValid;
+import java.util.List;
 
 import javax.annotation.processing.ProcessingEnvironment;
 import javax.lang.model.element.Element;
@@ -26,9 +23,12 @@
 import javax.lang.model.element.VariableElement;
 import javax.lang.model.type.TypeKind;
 import javax.lang.model.type.TypeMirror;
-import java.util.List;
 
+import org.androidannotations.annotations.FocusChange;
+import org.androidannotations.helper.CanonicalNameConstants;
 import org.androidannotations.holder.EComponentWithViewSupportHolder;
+import org.androidannotations.model.AnnotationElements;
+import org.androidannotations.process.IsValid;
 
 import com.sun.codemodel.JBlock;
 import com.sun.codemodel.JClass;
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/FragmentArgHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/FragmentArgHandler.java
index a802206..53ac3e9 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/FragmentArgHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/FragmentArgHandler.java
@@ -15,7 +15,16 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.*;
+import static com.sun.codemodel.JExpr._this;
+import static com.sun.codemodel.JExpr.lit;
+import static com.sun.codemodel.JMod.FINAL;
+import static com.sun.codemodel.JMod.PUBLIC;
+import static com.sun.codemodel.JMod.STATIC;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.type.TypeMirror;
+
 import org.androidannotations.annotations.FragmentArg;
 import org.androidannotations.helper.APTCodeModelHelper;
 import org.androidannotations.helper.AnnotationHelper;
@@ -25,13 +34,15 @@
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.process.IsValid;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.type.TypeMirror;
-
-import static com.sun.codemodel.JExpr._this;
-import static com.sun.codemodel.JExpr.lit;
-import static com.sun.codemodel.JMod.*;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JDefinedClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JFieldRef;
+import com.sun.codemodel.JFieldVar;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JVar;
 
 public class FragmentArgHandler extends BaseAnnotationHandler<EFragmentHolder> {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/IgnoredWhenDetachedHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/IgnoredWhenDetachedHandler.java
index ede6542..597cea5 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/IgnoredWhenDetachedHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/IgnoredWhenDetachedHandler.java
@@ -15,20 +15,21 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.JBlock;
-import com.sun.codemodel.JMethod;
+import static com.sun.codemodel.JExpr._null;
+import static com.sun.codemodel.JExpr.invoke;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.ExecutableElement;
+
 import org.androidannotations.annotations.IgnoredWhenDetached;
 import org.androidannotations.helper.APTCodeModelHelper;
-import org.androidannotations.holder.EComponentHolder;
 import org.androidannotations.holder.EFragmentHolder;
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.process.IsValid;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.ExecutableElement;
-
-import static com.sun.codemodel.JExpr.*;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JMethod;
 
 public class IgnoredWhenDetachedHandler extends BaseAnnotationHandler<EFragmentHolder> {
 
@@ -52,9 +53,6 @@ public void process(Element element, EFragmentHolder holder) throws Exception {
 		JMethod delegatingMethod = codeModelHelper.overrideAnnotatedMethod(executableElement, holder);
 		JBlock previousMethodBody = codeModelHelper.removeBody(delegatingMethod);
 
-
-		delegatingMethod.body()
-				._if(invoke(holder.getGeneratedClass().staticRef("this"), "getActivity").ne(_null()))
-				._then().add(previousMethodBody);
+		delegatingMethod.body()._if(invoke(holder.getGeneratedClass().staticRef("this"), "getActivity").ne(_null()))._then().add(previousMethodBody);
 	}
 }
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/InstanceStateHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/InstanceStateHandler.java
index 4f77f45..8109e35 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/InstanceStateHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/InstanceStateHandler.java
@@ -15,13 +15,12 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.JBlock;
-import com.sun.codemodel.JClass;
-import com.sun.codemodel.JExpr;
-import com.sun.codemodel.JExpression;
-import com.sun.codemodel.JFieldRef;
-import com.sun.codemodel.JMethod;
-import com.sun.codemodel.JVar;
+import static com.sun.codemodel.JExpr.ref;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.type.TypeMirror;
+
 import org.androidannotations.annotations.InstanceState;
 import org.androidannotations.helper.APTCodeModelHelper;
 import org.androidannotations.helper.AnnotationHelper;
@@ -30,11 +29,13 @@
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.process.IsValid;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.type.TypeMirror;
-
-import static com.sun.codemodel.JExpr.ref;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JFieldRef;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JVar;
 
 public class InstanceStateHandler extends BaseAnnotationHandler<HasInstanceState> {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ItemLongClickHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ItemLongClickHandler.java
index 1515eff..6e87e7c 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ItemLongClickHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ItemLongClickHandler.java
@@ -15,13 +15,10 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.*;
+import static com.sun.codemodel.JExpr.cast;
+import static com.sun.codemodel.JExpr.invoke;
 
-import org.androidannotations.annotations.ItemLongClick;
-import org.androidannotations.helper.APTCodeModelHelper;
-import org.androidannotations.holder.EComponentWithViewSupportHolder;
-import org.androidannotations.model.AnnotationElements;
-import org.androidannotations.process.IsValid;
+import java.util.List;
 
 import javax.annotation.processing.ProcessingEnvironment;
 import javax.lang.model.element.Element;
@@ -30,10 +27,20 @@
 import javax.lang.model.type.TypeKind;
 import javax.lang.model.type.TypeMirror;
 
-import java.util.List;
+import org.androidannotations.annotations.ItemLongClick;
+import org.androidannotations.helper.APTCodeModelHelper;
+import org.androidannotations.holder.EComponentWithViewSupportHolder;
+import org.androidannotations.model.AnnotationElements;
+import org.androidannotations.process.IsValid;
 
-import static com.sun.codemodel.JExpr.cast;
-import static com.sun.codemodel.JExpr.invoke;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JDefinedClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JInvocation;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JMod;
+import com.sun.codemodel.JVar;
 
 public class ItemLongClickHandler extends AbstractListenerHandler {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ItemSelectHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ItemSelectHandler.java
index dcd192f..ba8a305 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ItemSelectHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ItemSelectHandler.java
@@ -15,12 +15,11 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.*;
+import static com.sun.codemodel.JExpr._null;
+import static com.sun.codemodel.JExpr.invoke;
+import static com.sun.codemodel.JExpr.lit;
 
-import org.androidannotations.annotations.ItemSelect;
-import org.androidannotations.holder.EComponentWithViewSupportHolder;
-import org.androidannotations.model.AnnotationElements;
-import org.androidannotations.process.IsValid;
+import java.util.List;
 
 import javax.annotation.processing.ProcessingEnvironment;
 import javax.lang.model.element.Element;
@@ -29,9 +28,20 @@
 import javax.lang.model.type.TypeKind;
 import javax.lang.model.type.TypeMirror;
 
-import java.util.List;
+import org.androidannotations.annotations.ItemSelect;
+import org.androidannotations.holder.EComponentWithViewSupportHolder;
+import org.androidannotations.model.AnnotationElements;
+import org.androidannotations.process.IsValid;
 
-import static com.sun.codemodel.JExpr.*;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JDefinedClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JInvocation;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JMod;
+import com.sun.codemodel.JVar;
 
 public class ItemSelectHandler extends AbstractListenerHandler {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/OnActivityResultHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/OnActivityResultHandler.java
index 0dada0f..9c2fcb5 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/OnActivityResultHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/OnActivityResultHandler.java
@@ -15,17 +15,10 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.JBlock;
-import com.sun.codemodel.JExpr;
-import com.sun.codemodel.JExpression;
-import com.sun.codemodel.JInvocation;
-import com.sun.codemodel.JOp;
-import com.sun.codemodel.JVar;
-import org.androidannotations.annotations.OnActivityResult;
-import org.androidannotations.helper.CanonicalNameConstants;
-import org.androidannotations.holder.HasOnActivityResult;
-import org.androidannotations.model.AnnotationElements;
-import org.androidannotations.process.IsValid;
+import static com.sun.codemodel.JExpr._new;
+import static com.sun.codemodel.JExpr._null;
+
+import java.util.List;
 
 import javax.annotation.processing.ProcessingEnvironment;
 import javax.lang.model.element.Element;
@@ -33,10 +26,19 @@
 import javax.lang.model.element.VariableElement;
 import javax.lang.model.type.TypeKind;
 import javax.lang.model.type.TypeMirror;
-import java.util.List;
 
-import static com.sun.codemodel.JExpr._new;
-import static com.sun.codemodel.JExpr._null;
+import org.androidannotations.annotations.OnActivityResult;
+import org.androidannotations.helper.CanonicalNameConstants;
+import org.androidannotations.holder.HasOnActivityResult;
+import org.androidannotations.model.AnnotationElements;
+import org.androidannotations.process.IsValid;
+
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JInvocation;
+import com.sun.codemodel.JOp;
+import com.sun.codemodel.JVar;
 
 public class OnActivityResultHandler extends BaseAnnotationHandler<HasOnActivityResult> {
 
@@ -89,16 +91,14 @@ public void process(Element element, HasOnActivityResult holder) throws Exceptio
 			TypeMirror parameterType = parameter.asType();
 			if (parameter.getAnnotation(OnActivityResult.Extra.class) != null) {
 				if (extras == null) {
-					extras = onResultBlock.decl(classes().BUNDLE, "extras_",
-							JOp.cond(intent.ne(_null()).cand(intent.invoke("getExtras").ne(_null())),
-									intent.invoke("getExtras"), _new(classes().BUNDLE)));
+					extras = onResultBlock.decl(classes().BUNDLE, "extras_", JOp.cond(intent.ne(_null()).cand(intent.invoke("getExtras").ne(_null())), intent.invoke("getExtras"), _new(classes().BUNDLE)));
 				}
 				JExpression extraParameter = extraHandler.getExtraValue(parameter, extras, onResultBlock, holder);
 				onResultInvocation.arg(extraParameter);
 			} else if (CanonicalNameConstants.INTENT.equals(parameterType.toString())) {
 				onResultInvocation.arg(intent);
 			} else if (parameterType.getKind().equals(TypeKind.INT) //
-			        || CanonicalNameConstants.INTEGER.equals(parameterType.toString())) {
+					|| CanonicalNameConstants.INTEGER.equals(parameterType.toString())) {
 				onResultInvocation.arg(holder.getOnActivityResultResultCodeParam());
 			}
 		}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/PrefHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/PrefHandler.java
index 86b15f4..8f63a7f 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/PrefHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/PrefHandler.java
@@ -21,7 +21,6 @@
 import javax.lang.model.element.Element;
 import javax.lang.model.type.TypeMirror;
 
-import com.sun.codemodel.JClass;
 import org.androidannotations.annotations.sharedpreferences.Pref;
 import org.androidannotations.annotations.sharedpreferences.SharedPref;
 import org.androidannotations.holder.EComponentHolder;
@@ -30,6 +29,7 @@
 import org.androidannotations.process.IsValid;
 
 import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
 import com.sun.codemodel.JExpr;
 import com.sun.codemodel.JFieldRef;
 
@@ -58,10 +58,10 @@ public void process(Element element, EComponentHolder holder) {
 		String elementTypeName = fieldTypeMirror.toString();
 		int index = elementTypeName.lastIndexOf(".");
 		if (index != -1) {
-			elementTypeName = elementTypeName.substring(index+1);
+			elementTypeName = elementTypeName.substring(index + 1);
 		}
 
-		Set <? extends Element> sharedPrefElements = validatedModel.getRootAnnotatedElements(SharedPref.class.getName());
+		Set<? extends Element> sharedPrefElements = validatedModel.getRootAnnotatedElements(SharedPref.class.getName());
 		for (Element sharedPrefElement : sharedPrefElements) {
 			GeneratedClassHolder sharedPrefHolder = processHolder.getGeneratedClassHolder(sharedPrefElement);
 			String sharedPrefName = sharedPrefHolder.getGeneratedClass().name();
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ReceiverActionHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ReceiverActionHandler.java
index b94eabd..c772afd 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ReceiverActionHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ReceiverActionHandler.java
@@ -132,7 +132,8 @@ private void addActionInOnReceive(EReceiverHolder holder, ExecutableElement exec
 				callActionInvocation.arg(intent);
 			} else if (param.getAnnotation(ReceiverAction.Extra.class) != null) {
 				if (extras == null) {
-					extras = callActionBlock.decl(classes().BUNDLE, "extras_", JOp.cond(intent.invoke("getExtras").ne(_null()), intent.invoke("getExtras"), _new(classes().BUNDLE)));
+					extras = callActionBlock.decl(classes().BUNDLE, "extras_", JOp.cond(intent.invoke("getExtras") //
+							.ne(_null()), intent.invoke("getExtras"), _new(classes().BUNDLE)));
 				}
 				callActionInvocation.arg(extraHandler.getExtraValue(param, extras, callActionBlock, holder));
 			}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ReceiverHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ReceiverHandler.java
index 14fdae0..6430bec 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ReceiverHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ReceiverHandler.java
@@ -128,7 +128,8 @@ private JFieldVar createReceiverField(HasReceiverRegistration holder, String rec
 	}
 
 	private void registerAndUnregisterReceiver(HasReceiverRegistration holder, Receiver.RegisterAt registerAt, JFieldVar intentFilterField, JFieldVar receiverField, boolean local) {
-		JBlock registerBlock = null, unregisterBlock = null;
+		JBlock registerBlock = null;
+		JBlock unregisterBlock = null;
 		switch (registerAt) {
 		case OnCreateOnDestroy:
 			registerBlock = holder.getOnCreateAfterSuperBlock();
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/RootContextHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/RootContextHandler.java
new file mode 100644
index 0000000..008fc1d
--- /dev/null
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/RootContextHandler.java
@@ -0,0 +1,79 @@
+/**
+ * Copyright (C) 2010-2014 eBusiness Information, Excilys Group
+ *
+ * Licensed under the Apache License, Version 2.0 (the "License"); you may not
+ * use this file except in compliance with the License. You may obtain a copy of
+ * the License at
+ *
+ * http://www.apache.org/licenses/LICENSE-2.0
+ *
+ * Unless required by applicable law or agreed To in writing, software
+ * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
+ * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
+ * License for the specific language governing permissions and limitations under
+ * the License.
+ */
+package org.androidannotations.handler;
+
+import static com.sun.codemodel.JExpr.cast;
+import static com.sun.codemodel.JExpr.lit;
+import static com.sun.codemodel.JExpr.ref;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.type.TypeMirror;
+
+import org.androidannotations.annotations.RootContext;
+import org.androidannotations.helper.CanonicalNameConstants;
+import org.androidannotations.holder.EBeanHolder;
+import org.androidannotations.model.AnnotationElements;
+import org.androidannotations.process.IsValid;
+
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JConditional;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JInvocation;
+
+public class RootContextHandler extends BaseAnnotationHandler<EBeanHolder> {
+
+	public RootContextHandler(ProcessingEnvironment processingEnvironment) {
+		super(RootContext.class, processingEnvironment);
+	}
+
+	@Override
+	public void validate(Element element, AnnotationElements validatedElements, IsValid valid) {
+		validatorHelper.enclosingElementHasEBeanAnnotation(element, validatedElements, valid);
+
+		validatorHelper.extendsContext(element, valid);
+
+		validatorHelper.isNotPrivate(element, valid);
+	}
+
+	@Override
+	public void process(Element element, EBeanHolder holder) {
+		String fieldName = element.getSimpleName().toString();
+
+		TypeMirror elementType = element.asType();
+		String typeQualifiedName = elementType.toString();
+
+		JBlock body = holder.getInitBody();
+		JExpression contextRef = holder.getContextRef();
+
+		if (CanonicalNameConstants.CONTEXT.equals(typeQualifiedName)) {
+			body.assign(ref(fieldName), contextRef);
+		} else {
+			JClass extendingContextClass = holder.refClass(typeQualifiedName);
+			JConditional cond = body._if(holder.getContextRef()._instanceof(extendingContextClass));
+			cond._then() //
+					.assign(ref(fieldName), cast(extendingContextClass, holder.getContextRef()));
+
+			JInvocation warningInvoke = holder.classes().LOG.staticInvoke("w");
+			warningInvoke.arg(holder.getGeneratedClass().name());
+			JExpression expr = lit("Due to Context class ").plus(holder.getContextRef().invoke("getClass").invoke("getSimpleName")).plus(lit(", the @RootContext " + extendingContextClass.name() + " won't be populated"));
+			warningInvoke.arg(expr);
+			cond._else() //
+					.add(warningInvoke);
+		}
+	}
+}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/RootContextHanlder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/RootContextHanlder.java
deleted file mode 100644
index cc94104..0000000
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/RootContextHanlder.java
+++ /dev/null
@@ -1,79 +0,0 @@
-/**
- * Copyright (C) 2010-2014 eBusiness Information, Excilys Group
- *
- * Licensed under the Apache License, Version 2.0 (the "License"); you may not
- * use this file except in compliance with the License. You may obtain a copy of
- * the License at
- *
- * http://www.apache.org/licenses/LICENSE-2.0
- *
- * Unless required by applicable law or agreed To in writing, software
- * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
- * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
- * License for the specific language governing permissions and limitations under
- * the License.
- */
-package org.androidannotations.handler;
-
-import static com.sun.codemodel.JExpr.cast;
-import static com.sun.codemodel.JExpr.lit;
-import static com.sun.codemodel.JExpr.ref;
-
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.type.TypeMirror;
-
-import org.androidannotations.annotations.RootContext;
-import org.androidannotations.helper.CanonicalNameConstants;
-import org.androidannotations.holder.EBeanHolder;
-import org.androidannotations.model.AnnotationElements;
-import org.androidannotations.process.IsValid;
-
-import com.sun.codemodel.JBlock;
-import com.sun.codemodel.JClass;
-import com.sun.codemodel.JConditional;
-import com.sun.codemodel.JExpression;
-import com.sun.codemodel.JInvocation;
-
-public class RootContextHanlder extends BaseAnnotationHandler<EBeanHolder> {
-
-	public RootContextHanlder(ProcessingEnvironment processingEnvironment) {
-		super(RootContext.class, processingEnvironment);
-	}
-
-	@Override
-	public void validate(Element element, AnnotationElements validatedElements, IsValid valid) {
-		validatorHelper.enclosingElementHasEBeanAnnotation(element, validatedElements, valid);
-
-		validatorHelper.extendsContext(element, valid);
-
-		validatorHelper.isNotPrivate(element, valid);
-	}
-
-	@Override
-	public void process(Element element, EBeanHolder holder) {
-		String fieldName = element.getSimpleName().toString();
-
-		TypeMirror elementType = element.asType();
-		String typeQualifiedName = elementType.toString();
-
-		JBlock body = holder.getInitBody();
-		JExpression contextRef = holder.getContextRef();
-
-		if (CanonicalNameConstants.CONTEXT.equals(typeQualifiedName)) {
-			body.assign(ref(fieldName), contextRef);
-		} else {
-            JClass extendingContextClass = holder.refClass(typeQualifiedName);
-            JConditional cond = body._if(holder.getContextRef()._instanceof(extendingContextClass));
-            cond._then() //
-                    .assign(ref(fieldName), cast(extendingContextClass, holder.getContextRef()));
-
-            JInvocation warningInvoke = holder.classes().LOG.staticInvoke("w");
-            warningInvoke.arg(holder.getGeneratedClass().name());
-            JExpression expr = lit("Due to Context class ").plus(holder.getContextRef().invoke("getClass").invoke("getSimpleName")).plus(lit(", the @RootContext " + extendingContextClass.name() + " won't be populated"));
-            warningInvoke.arg(expr);
-            cond._else() //
-                    .add(warningInvoke);
-		}
-	}
-}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ServiceActionHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ServiceActionHandler.java
index ee3c3f4..6b87bc5 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ServiceActionHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ServiceActionHandler.java
@@ -15,7 +15,19 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.*;
+import static com.sun.codemodel.JExpr._null;
+import static com.sun.codemodel.JExpr.lit;
+import static com.sun.codemodel.JMod.FINAL;
+import static com.sun.codemodel.JMod.PUBLIC;
+import static com.sun.codemodel.JMod.STATIC;
+
+import java.util.List;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.ExecutableElement;
+import javax.lang.model.element.VariableElement;
+
 import org.androidannotations.annotations.ServiceAction;
 import org.androidannotations.helper.APTCodeModelHelper;
 import org.androidannotations.helper.AnnotationHelper;
@@ -25,120 +37,119 @@
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.process.IsValid;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.ExecutableElement;
-import javax.lang.model.element.VariableElement;
-import java.util.List;
-
-import static com.sun.codemodel.JExpr._null;
-import static com.sun.codemodel.JExpr.lit;
-import static com.sun.codemodel.JMod.*;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JFieldVar;
+import com.sun.codemodel.JInvocation;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JVar;
 
 public class ServiceActionHandler extends BaseAnnotationHandler<EIntentServiceHolder> {
 
-    private final APTCodeModelHelper codeModelHelper = new APTCodeModelHelper();
-    private AnnotationHelper annotationHelper;
-
-    public ServiceActionHandler(ProcessingEnvironment processingEnvironment) {
-        super(ServiceAction.class, processingEnvironment);
-        annotationHelper = new AnnotationHelper(processingEnvironment);
-    }
-
-    @Override
-    protected void validate(Element element, AnnotationElements validatedElements, IsValid valid) {
-
-        validatorHelper.enclosingElementHasEIntentService(element, validatedElements, valid);
-
-        validatorHelper.returnTypeIsVoid((ExecutableElement) element, valid);
-
-        validatorHelper.isNotPrivate(element, valid);
-    }
-
-    @Override
-    public void process(Element element, EIntentServiceHolder holder) throws Exception {
-
-        ExecutableElement executableElement = (ExecutableElement) element;
-        String methodName = element.getSimpleName().toString();
-
-        ServiceAction annotation = element.getAnnotation(ServiceAction.class);
-        String extraKey = annotation.value();
-        if (extraKey.isEmpty()) {
-            extraKey = methodName;
-        }
-
-        JFieldVar actionKeyField = createStaticActionField(holder, extraKey, methodName);
-        addActionInOnHandleIntent(holder, executableElement, methodName, actionKeyField);
-        addActionToIntentBuilder(holder, executableElement, methodName, actionKeyField);
-    }
-
-    private JFieldVar createStaticActionField(EIntentServiceHolder holder, String extraKey, String methodName) {
-        String staticFieldName = CaseHelper.camelCaseToUpperSnakeCase("action", methodName, null);
-        return holder.getGeneratedClass().field(PUBLIC | STATIC | FINAL, classes().STRING, staticFieldName, lit(extraKey));
-    }
-
-    private void addActionInOnHandleIntent(EIntentServiceHolder holder, ExecutableElement executableElement, String methodName, JFieldVar actionKeyField) {
-	    JMethod onHandleIntentMethod = holder.getOnHandleIntentMethod();
-
-        // If action match, call the method
-        JInvocation actionCondition = actionKeyField.invoke("equals").arg(holder.getOnHandleIntentIntentAction());
-        JBlock callActionBlock = holder.getOnHandleIntentBody()._if(actionCondition)._then();
-        JInvocation callActionInvocation = JExpr._super().invoke(methodName);
-
-        // For each method params, we get back value from extras and put it
-        // in super calls
-        List<? extends VariableElement> methodParameters = executableElement.getParameters();
-        if (methodParameters.size() > 0) {
-            // Extras
-	        JVar intent = holder.getOnHandleIntentIntent();
-            JVar extras = callActionBlock.decl(classes().BUNDLE, "extras");
-            extras.init(intent.invoke("getExtras"));
-            callActionBlock = callActionBlock._if(extras.ne(_null()))._then();
-
-            // Extras params
-            for (VariableElement param : methodParameters) {
-                String paramName = param.getSimpleName().toString();
-                String extraParamName = paramName + "Extra";
-                JFieldVar paramVar = getStaticExtraField(holder, paramName);
-                JClass extraParamClass = codeModelHelper.typeMirrorToJClass(param.asType(), holder);
-
-                BundleHelper bundleHelper = new BundleHelper(annotationHelper, param.asType());
-                JExpression getExtraExpression = bundleHelper.getExpressionToRestoreFromIntentOrBundle(extraParamClass, intent, extras, paramVar, onHandleIntentMethod);
-
-                JVar extraField = callActionBlock.decl(extraParamClass, extraParamName, getExtraExpression);
-                callActionInvocation.arg(extraField);
-            }
-        }
-        callActionBlock.add(callActionInvocation);
-        callActionBlock._return();
-    }
-
-    private void addActionToIntentBuilder(EIntentServiceHolder holder, ExecutableElement executableElement, String methodName, JFieldVar actionKeyField) {
-        JMethod method = holder.getIntentBuilderClass().method(PUBLIC, holder.getIntentBuilderClass(), methodName);
-        JBlock body = method.body();
-
-        // setAction
-        body.invoke("action").arg(actionKeyField);
-
-        for (VariableElement param : executableElement.getParameters()) {
-            String paramName = param.getSimpleName().toString();
-            JClass parameterClass = codeModelHelper.typeMirrorToJClass(param.asType(), holder);
-
-            JFieldVar paramVar = getStaticExtraField(holder, paramName);
-            JVar methodParam = method.param(parameterClass, paramName);
-
-            JInvocation putExtraInvocation = holder.getIntentBuilder().getSuperPutExtraInvocation(param.asType(), methodParam, paramVar);
-            body.add(putExtraInvocation);
-        }
-        body._return(JExpr._this());
-    }
-
-    private JFieldVar getStaticExtraField(EIntentServiceHolder holder, String extraName) {
-        String staticFieldName = CaseHelper.camelCaseToUpperSnakeCase(null, extraName, "Extra");
-        JFieldVar staticExtraField = holder.getGeneratedClass().fields().get(staticFieldName);
-        if (staticExtraField == null) {
-            staticExtraField = holder.getGeneratedClass().field(PUBLIC | STATIC | FINAL, classes().STRING, staticFieldName, lit(extraName));
-        }
-        return staticExtraField;
-    }
+	private final APTCodeModelHelper codeModelHelper = new APTCodeModelHelper();
+	private AnnotationHelper annotationHelper;
+
+	public ServiceActionHandler(ProcessingEnvironment processingEnvironment) {
+		super(ServiceAction.class, processingEnvironment);
+		annotationHelper = new AnnotationHelper(processingEnvironment);
+	}
+
+	@Override
+	protected void validate(Element element, AnnotationElements validatedElements, IsValid valid) {
+
+		validatorHelper.enclosingElementHasEIntentService(element, validatedElements, valid);
+
+		validatorHelper.returnTypeIsVoid((ExecutableElement) element, valid);
+
+		validatorHelper.isNotPrivate(element, valid);
+	}
+
+	@Override
+	public void process(Element element, EIntentServiceHolder holder) throws Exception {
+
+		ExecutableElement executableElement = (ExecutableElement) element;
+		String methodName = element.getSimpleName().toString();
+
+		ServiceAction annotation = element.getAnnotation(ServiceAction.class);
+		String extraKey = annotation.value();
+		if (extraKey.isEmpty()) {
+			extraKey = methodName;
+		}
+
+		JFieldVar actionKeyField = createStaticActionField(holder, extraKey, methodName);
+		addActionInOnHandleIntent(holder, executableElement, methodName, actionKeyField);
+		addActionToIntentBuilder(holder, executableElement, methodName, actionKeyField);
+	}
+
+	private JFieldVar createStaticActionField(EIntentServiceHolder holder, String extraKey, String methodName) {
+		String staticFieldName = CaseHelper.camelCaseToUpperSnakeCase("action", methodName, null);
+		return holder.getGeneratedClass().field(PUBLIC | STATIC | FINAL, classes().STRING, staticFieldName, lit(extraKey));
+	}
+
+	private void addActionInOnHandleIntent(EIntentServiceHolder holder, ExecutableElement executableElement, String methodName, JFieldVar actionKeyField) {
+		JMethod onHandleIntentMethod = holder.getOnHandleIntentMethod();
+
+		// If action match, call the method
+		JInvocation actionCondition = actionKeyField.invoke("equals").arg(holder.getOnHandleIntentIntentAction());
+		JBlock callActionBlock = holder.getOnHandleIntentBody()._if(actionCondition)._then();
+		JInvocation callActionInvocation = JExpr._super().invoke(methodName);
+
+		// For each method params, we get back value from extras and put it
+		// in super calls
+		List<? extends VariableElement> methodParameters = executableElement.getParameters();
+		if (methodParameters.size() > 0) {
+			// Extras
+			JVar intent = holder.getOnHandleIntentIntent();
+			JVar extras = callActionBlock.decl(classes().BUNDLE, "extras");
+			extras.init(intent.invoke("getExtras"));
+			callActionBlock = callActionBlock._if(extras.ne(_null()))._then();
+
+			// Extras params
+			for (VariableElement param : methodParameters) {
+				String paramName = param.getSimpleName().toString();
+				String extraParamName = paramName + "Extra";
+				JFieldVar paramVar = getStaticExtraField(holder, paramName);
+				JClass extraParamClass = codeModelHelper.typeMirrorToJClass(param.asType(), holder);
+
+				BundleHelper bundleHelper = new BundleHelper(annotationHelper, param.asType());
+				JExpression getExtraExpression = bundleHelper.getExpressionToRestoreFromIntentOrBundle(extraParamClass, intent, extras, paramVar, onHandleIntentMethod);
+
+				JVar extraField = callActionBlock.decl(extraParamClass, extraParamName, getExtraExpression);
+				callActionInvocation.arg(extraField);
+			}
+		}
+		callActionBlock.add(callActionInvocation);
+		callActionBlock._return();
+	}
+
+	private void addActionToIntentBuilder(EIntentServiceHolder holder, ExecutableElement executableElement, String methodName, JFieldVar actionKeyField) {
+		JMethod method = holder.getIntentBuilderClass().method(PUBLIC, holder.getIntentBuilderClass(), methodName);
+		JBlock body = method.body();
+
+		// setAction
+		body.invoke("action").arg(actionKeyField);
+
+		for (VariableElement param : executableElement.getParameters()) {
+			String paramName = param.getSimpleName().toString();
+			JClass parameterClass = codeModelHelper.typeMirrorToJClass(param.asType(), holder);
+
+			JFieldVar paramVar = getStaticExtraField(holder, paramName);
+			JVar methodParam = method.param(parameterClass, paramName);
+
+			JMethod putExtraMethod = holder.getIntentBuilder().getPutExtraMethod(param.asType(), paramName, paramVar);
+			body.invoke(putExtraMethod).arg(methodParam);
+		}
+		body._return(JExpr._this());
+	}
+
+	private JFieldVar getStaticExtraField(EIntentServiceHolder holder, String extraName) {
+		String staticFieldName = CaseHelper.camelCaseToUpperSnakeCase(null, extraName, "Extra");
+		JFieldVar staticExtraField = holder.getGeneratedClass().fields().get(staticFieldName);
+		if (staticExtraField == null) {
+			staticExtraField = holder.getGeneratedClass().field(PUBLIC | STATIC | FINAL, classes().STRING, staticFieldName, lit(extraName));
+		}
+		return staticExtraField;
+	}
 }
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/SharedPrefHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/SharedPrefHandler.java
index 17204d5..090b4e4 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/SharedPrefHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/SharedPrefHandler.java
@@ -99,7 +99,7 @@
 			put("boolean", new DefaultPrefInfo<Boolean>(DefaultBoolean.class, BooleanPrefField.class, IRClass.Res.BOOL, false, "booleanField"));
 			put("float", new DefaultPrefInfo<Float>(DefaultFloat.class, FloatPrefField.class, IRClass.Res.INTEGER, 0f, "floatField"));
 			put("int", new DefaultPrefInfo<Integer>(DefaultInt.class, IntPrefField.class, IRClass.Res.INTEGER, 0, "intField"));
-			put("long", new DefaultPrefInfo<Long>(DefaultLong.class, LongPrefField.class, IRClass.Res.INTEGER, 0l, "longField"));
+			put("long", new DefaultPrefInfo<Long>(DefaultLong.class, LongPrefField.class, IRClass.Res.INTEGER, 0L, "longField"));
 			put(CanonicalNameConstants.STRING, new DefaultPrefInfo<String>(DefaultString.class, StringPrefField.class, IRClass.Res.STRING, "", "stringField"));
 			put(CanonicalNameConstants.STRING_SET, new DefaultPrefInfo<Set<String>>(DefaultStringSet.class, StringSetPrefField.class, null, null, "stringSetField"));
 		}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/SupposeBackgroundHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/SupposeBackgroundHandler.java
index 6efc06a..dc542e3 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/SupposeBackgroundHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/SupposeBackgroundHandler.java
@@ -15,21 +15,21 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.JBlock;
-import com.sun.codemodel.JClass;
-import com.sun.codemodel.JInvocation;
-import com.sun.codemodel.JMethod;
+import static com.sun.codemodel.JExpr.lit;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.ExecutableElement;
 
 import org.androidannotations.annotations.SupposeBackground;
 import org.androidannotations.api.BackgroundExecutor;
 import org.androidannotations.helper.APTCodeModelHelper;
 import org.androidannotations.holder.EComponentHolder;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.ExecutableElement;
-
-import static com.sun.codemodel.JExpr.lit;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JInvocation;
+import com.sun.codemodel.JMethod;
 
 public class SupposeBackgroundHandler extends SupposeThreadHandler {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/SupposeUiThreadHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/SupposeUiThreadHandler.java
index eda0cfe..9851700 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/SupposeUiThreadHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/SupposeUiThreadHandler.java
@@ -15,18 +15,18 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.JBlock;
-import com.sun.codemodel.JClass;
-import com.sun.codemodel.JMethod;
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.ExecutableElement;
 
 import org.androidannotations.annotations.SupposeUiThread;
 import org.androidannotations.api.BackgroundExecutor;
 import org.androidannotations.helper.APTCodeModelHelper;
 import org.androidannotations.holder.EComponentHolder;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.ExecutableElement;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JMethod;
 
 public class SupposeUiThreadHandler extends SupposeThreadHandler {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/TouchHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/TouchHandler.java
index 42f9755..6d33883 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/TouchHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/TouchHandler.java
@@ -15,12 +15,7 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.*;
-import org.androidannotations.annotations.Touch;
-import org.androidannotations.helper.CanonicalNameConstants;
-import org.androidannotations.holder.EComponentWithViewSupportHolder;
-import org.androidannotations.model.AnnotationElements;
-import org.androidannotations.process.IsValid;
+import java.util.List;
 
 import javax.annotation.processing.ProcessingEnvironment;
 import javax.lang.model.element.Element;
@@ -28,7 +23,21 @@
 import javax.lang.model.element.VariableElement;
 import javax.lang.model.type.TypeKind;
 import javax.lang.model.type.TypeMirror;
-import java.util.List;
+
+import org.androidannotations.annotations.Touch;
+import org.androidannotations.helper.CanonicalNameConstants;
+import org.androidannotations.holder.EComponentWithViewSupportHolder;
+import org.androidannotations.model.AnnotationElements;
+import org.androidannotations.process.IsValid;
+
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JDefinedClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JInvocation;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JMod;
+import com.sun.codemodel.JVar;
 
 public class TouchHandler extends AbstractListenerHandler {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/TraceHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/TraceHandler.java
index 6dd7695..8d5f758 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/TraceHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/TraceHandler.java
@@ -83,7 +83,7 @@ public void process(Element element, EComponentHolder holder) throws Exception {
 		JConditional ifStatement = methodBody._if(isLoggableInvocation);
 
 		JInvocation currentTimeInvoke = classes().SYSTEM.staticInvoke("currentTimeMillis");
-		JBlock _thenBody = ifStatement._then();
+		JBlock thenBody = ifStatement._then();
 
 		// Log In
 		String logMethodName = logMethodNameFromLevel(level);
@@ -91,19 +91,19 @@ public void process(Element element, EComponentHolder holder) throws Exception {
 		logEnterInvoke.arg(tag);
 
 		logEnterInvoke.arg(getEnterMessage(method, executableElement));
-		_thenBody.add(logEnterInvoke);
-		JVar startDeclaration = _thenBody.decl(codeModel().LONG, "start", currentTimeInvoke);
+		thenBody.add(logEnterInvoke);
+		JVar startDeclaration = thenBody.decl(codeModel().LONG, "start", currentTimeInvoke);
 
 		JTryBlock tryBlock;
 
 		JVar result = null;
 		if (method.type().fullName().equals("void")) {
-			tryBlock = _thenBody._try();
+			tryBlock = thenBody._try();
 			tryBlock.body().add(previousMethodBody);
 		} else {
 			JInvocation superCall = codeModelHelper.getSuperCall(holder, method);
-			result = _thenBody.decl(refClass(Object.class), "result", JExpr._null());
-			tryBlock = _thenBody._try();
+			result = thenBody.decl(refClass(Object.class), "result", JExpr._null());
+			tryBlock = thenBody._try();
 			tryBlock.body().assign(result, superCall);
 			tryBlock.body()._return(JExpr.cast(boxify(method.type()), result));
 		}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/UiThreadHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/UiThreadHandler.java
index bc47ec8..4db3a79 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/UiThreadHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/UiThreadHandler.java
@@ -15,17 +15,25 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.*;
-import org.androidannotations.annotations.UiThread;
-import org.androidannotations.helper.APTCodeModelHelper;
-import org.androidannotations.holder.EComponentHolder;
+import static com.sun.codemodel.JExpr._new;
+import static com.sun.codemodel.JExpr.lit;
 
 import javax.annotation.processing.ProcessingEnvironment;
 import javax.lang.model.element.Element;
 import javax.lang.model.element.ExecutableElement;
 
-import static com.sun.codemodel.JExpr._new;
-import static com.sun.codemodel.JExpr.lit;
+import org.androidannotations.annotations.UiThread;
+import org.androidannotations.helper.APTCodeModelHelper;
+import org.androidannotations.holder.EComponentHolder;
+
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JClassAlreadyExistsException;
+import com.sun.codemodel.JConditional;
+import com.sun.codemodel.JDefinedClass;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JOp;
 
 public class UiThreadHandler extends AbstractRunnableHandler {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ViewByIdHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ViewByIdHandler.java
index 3b78a64..20dfccc 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ViewByIdHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ViewByIdHandler.java
@@ -15,8 +15,12 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.JClass;
-import com.sun.codemodel.JFieldRef;
+import static com.sun.codemodel.JExpr.ref;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.type.TypeMirror;
+
 import org.androidannotations.annotations.ViewById;
 import org.androidannotations.helper.AndroidManifest;
 import org.androidannotations.helper.IdAnnotationHelper;
@@ -27,11 +31,8 @@
 import org.androidannotations.process.IsValid;
 import org.androidannotations.rclass.IRClass;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.type.TypeMirror;
-
-import static com.sun.codemodel.JExpr.ref;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JFieldRef;
 
 public class ViewByIdHandler extends BaseAnnotationHandler<EComponentWithViewSupportHolder> {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ViewsByIdHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ViewsByIdHandler.java
index 153c31d..5ce740a 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ViewsByIdHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/ViewsByIdHandler.java
@@ -15,10 +15,23 @@
  */
 package org.androidannotations.handler;
 
-import com.sun.codemodel.JClass;
-import com.sun.codemodel.JFieldRef;
+import static com.sun.codemodel.JExpr._new;
+import static com.sun.codemodel.JExpr.ref;
+
+import java.util.List;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.TypeElement;
+import javax.lang.model.type.DeclaredType;
+import javax.lang.model.type.TypeMirror;
+
 import org.androidannotations.annotations.ViewsById;
-import org.androidannotations.helper.*;
+import org.androidannotations.helper.APTCodeModelHelper;
+import org.androidannotations.helper.AndroidManifest;
+import org.androidannotations.helper.CanonicalNameConstants;
+import org.androidannotations.helper.IdAnnotationHelper;
+import org.androidannotations.helper.IdValidatorHelper;
 import org.androidannotations.holder.EComponentWithViewSupportHolder;
 import org.androidannotations.holder.FoundViewHolder;
 import org.androidannotations.model.AndroidSystemServices;
@@ -26,15 +39,8 @@
 import org.androidannotations.process.IsValid;
 import org.androidannotations.rclass.IRClass;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.TypeElement;
-import javax.lang.model.type.DeclaredType;
-import javax.lang.model.type.TypeMirror;
-import java.util.List;
-
-import static com.sun.codemodel.JExpr._new;
-import static com.sun.codemodel.JExpr.ref;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JFieldRef;
 
 public class ViewsByIdHandler extends BaseAnnotationHandler<EComponentWithViewSupportHolder> {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/WakeLockHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/WakeLockHandler.java
index 8ff81c2..009c2d2 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/WakeLockHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/WakeLockHandler.java
@@ -92,8 +92,8 @@ public void process(Element element, EComponentHolder holder) {
 		tryBlock.body().add(previousMethodBody);
 
 		JBlock finallyBlock = tryBlock._finally();
-		JConditional _if = finallyBlock._if(wakeLock.ne(JExpr._null()));
-		_if._then().add(wakeLock.invoke("release"));
+		JConditional ifStatement = finallyBlock._if(wakeLock.ne(JExpr._null()));
+		ifStatement._then().add(wakeLock.invoke("release"));
 	}
 
 	private String extractTag(Element element) {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/DeleteHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/DeleteHandler.java
index b0bb614..7914ff7 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/DeleteHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/DeleteHandler.java
@@ -33,10 +33,10 @@ public DeleteHandler(ProcessingEnvironment processingEnvironment) {
 	public void validate(Element element, AnnotationElements validatedElements, IsValid valid) {
 		super.validate(element, validatedElements, valid);
 
-        validatorHelper.doesNotReturnPrimitive((ExecutableElement) element, valid);
+		validatorHelper.doesNotReturnPrimitive((ExecutableElement) element, valid);
 
-        restAnnotationHelper.urlVariableNamesExistInParametersAndHasOnlyOneMoreParameter((ExecutableElement) element, valid);
-    }
+		restAnnotationHelper.urlVariableNamesExistInParametersAndHasOnlyOneMoreParameter((ExecutableElement) element, valid);
+	}
 
 	@Override
 	protected String getUrlSuffix(Element element) {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/HeadHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/HeadHandler.java
index a8a104a..f5a9cb6 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/HeadHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/HeadHandler.java
@@ -49,10 +49,10 @@ protected String getUrlSuffix(Element element) {
 		return annotation.value();
 	}
 
-    @Override
-    protected JExpression getResponseClass(Element element, RestHolder holder) {
-        return JExpr._null();
-    }
+	@Override
+	protected JExpression getResponseClass(Element element, RestHolder holder) {
+		return JExpr._null();
+	}
 
 	@Override
 	protected JExpression addResultCallMethod(JExpression exchangeCall, JClass methodReturnClass) {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/OptionsHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/OptionsHandler.java
index 2d5b46f..7553003 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/OptionsHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/OptionsHandler.java
@@ -49,10 +49,10 @@ protected String getUrlSuffix(Element element) {
 		return annotation.value();
 	}
 
-    @Override
-    protected JExpression getResponseClass(Element element, RestHolder holder) {
-        return JExpr._null();
-    }
+	@Override
+	protected JExpression getResponseClass(Element element, RestHolder holder) {
+		return JExpr._null();
+	}
 
 	@Override
 	protected JExpression addResultCallMethod(JExpression exchangeCall, JClass methodReturnClass) {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/PutHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/PutHandler.java
index fc75b05..249cc3f 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/PutHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/PutHandler.java
@@ -33,7 +33,7 @@ public PutHandler(ProcessingEnvironment processingEnvironment) {
 	public void validate(Element element, AnnotationElements validatedElements, IsValid valid) {
 		super.validate(element, validatedElements, valid);
 
-        validatorHelper.doesNotReturnPrimitive((ExecutableElement) element, valid);
+		validatorHelper.doesNotReturnPrimitive((ExecutableElement) element, valid);
 
 		restAnnotationHelper.urlVariableNamesExistInParametersAndHasOnlyOneMoreParameter((ExecutableElement) element, valid);
 	}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/RestHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/RestHandler.java
index a7b8a3b..f746ab9 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/RestHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/RestHandler.java
@@ -15,10 +15,19 @@
  */
 package org.androidannotations.handler.rest;
 
-import com.sun.codemodel.JBlock;
-import com.sun.codemodel.JClass;
-import com.sun.codemodel.JFieldVar;
-import com.sun.codemodel.JInvocation;
+import static com.sun.codemodel.JExpr._new;
+import static com.sun.codemodel.JExpr.invoke;
+import static com.sun.codemodel.JExpr.lit;
+import static org.androidannotations.helper.CanonicalNameConstants.ARRAYLIST;
+import static org.androidannotations.helper.CanonicalNameConstants.CLIENT_HTTP_REQUEST_INTERCEPTOR;
+
+import java.util.List;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.TypeElement;
+import javax.lang.model.type.DeclaredType;
+
 import org.androidannotations.annotations.rest.Rest;
 import org.androidannotations.handler.BaseGeneratingAnnotationHandler;
 import org.androidannotations.helper.APTCodeModelHelper;
@@ -28,15 +37,10 @@
 import org.androidannotations.process.IsValid;
 import org.androidannotations.process.ProcessHolder;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.TypeElement;
-import javax.lang.model.type.DeclaredType;
-import java.util.List;
-
-import static com.sun.codemodel.JExpr.*;
-import static org.androidannotations.helper.CanonicalNameConstants.ARRAYLIST;
-import static org.androidannotations.helper.CanonicalNameConstants.CLIENT_HTTP_REQUEST_INTERCEPTOR;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JFieldVar;
+import com.sun.codemodel.JInvocation;
 
 public class RestHandler extends BaseGeneratingAnnotationHandler<RestHolder> {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/RestMethodHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/RestMethodHandler.java
index acc9944..8868d15 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/RestMethodHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/RestMethodHandler.java
@@ -17,6 +17,7 @@
 
 import java.util.List;
 import java.util.Locale;
+import java.util.SortedMap;
 import java.util.TreeMap;
 
 import javax.annotation.processing.ProcessingEnvironment;
@@ -79,7 +80,7 @@ public void process(Element element, RestHolder holder) {
 		// Creating method signature
 		JMethod method = holder.getGeneratedClass().method(JMod.PUBLIC, methodReturnClass, methodName);
 		method.annotate(Override.class);
-		TreeMap<String, JVar> params = addMethodParams(executableElement, holder, method);
+		SortedMap<String, JVar> params = addMethodParams(executableElement, holder, method);
 		JBlock methodBody = new JBlock(false, false);
 
 		// RestTemplate exchange() method call
@@ -108,9 +109,9 @@ protected JClass getMethodReturnClass(Element element, RestHolder holder) {
 		return codeModelHelper.typeMirrorToJClass(executableElement.getReturnType(), holder);
 	}
 
-	protected TreeMap<String, JVar> addMethodParams(ExecutableElement executableElement, RestHolder restHolder, JMethod method) {
+	protected SortedMap<String, JVar> addMethodParams(ExecutableElement executableElement, RestHolder restHolder, JMethod method) {
 		List<? extends VariableElement> params = executableElement.getParameters();
-		TreeMap<String, JVar> methodParams = new TreeMap<String, JVar>();
+		SortedMap<String, JVar> methodParams = new TreeMap<String, JVar>();
 		for (VariableElement parameter : params) {
 			String paramName = parameter.getSimpleName().toString();
 			String paramType = parameter.asType().toString();
@@ -145,7 +146,7 @@ protected JExpression getHttpMethod() {
 		return httpMethod.staticRef(restMethodInCapitalLetters);
 	}
 
-	protected JExpression getRequestEntity(ExecutableElement element, RestHolder holder, JBlock methodBody, TreeMap<String, JVar> params) {
+	protected JExpression getRequestEntity(ExecutableElement element, RestHolder holder, JBlock methodBody, SortedMap<String, JVar> params) {
 		JVar httpHeaders = restAnnotationHelper.declareHttpHeaders(element, holder, methodBody);
 		JVar entitySentToServer = restAnnotationHelper.getEntitySentToServer(element, params);
 		return restAnnotationHelper.declareHttpEntity(processHolder, methodBody, entitySentToServer, httpHeaders);
@@ -155,7 +156,7 @@ protected JExpression getResponseClass(Element element, RestHolder holder) {
 		return restAnnotationHelper.getResponseClass(element, holder);
 	}
 
-	protected JExpression getUrlVariables(Element element, RestHolder holder, JBlock methodBody, TreeMap<String, JVar> params) {
+	protected JExpression getUrlVariables(Element element, RestHolder holder, JBlock methodBody, SortedMap<String, JVar> params) {
 		return restAnnotationHelper.declareUrlVariables((ExecutableElement) element, holder, methodBody, params);
 	}
 
@@ -217,7 +218,7 @@ private JExpression setCookies(ExecutableElement executableElement, RestHolder r
 
 	/**
 	 * Adds the try/catch around the rest execution code.
-	 * 
+	 *
 	 * If an exception is caught, it will first check if the handler is set. If
 	 * the handler is set, it will call the handler and return null (or nothing
 	 * if void). If the handler isn't set, it will re-throw the exception so
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/RestServiceHandler.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/RestServiceHandler.java
index 8025f71..df8207e 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/RestServiceHandler.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/handler/rest/RestServiceHandler.java
@@ -19,9 +19,6 @@
 import javax.lang.model.element.Element;
 import javax.lang.model.type.TypeMirror;
 
-import com.sun.codemodel.JBlock;
-import com.sun.codemodel.JExpr;
-import com.sun.codemodel.JFieldRef;
 import org.androidannotations.annotations.rest.Rest;
 import org.androidannotations.annotations.rest.RestService;
 import org.androidannotations.handler.BaseAnnotationHandler;
@@ -30,35 +27,38 @@
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.process.IsValid;
 
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JFieldRef;
 
 public class RestServiceHandler extends BaseAnnotationHandler<EComponentHolder> {
 
-    public RestServiceHandler(ProcessingEnvironment processingEnvironment) {
-        super(RestService.class, processingEnvironment);
-    }
+	public RestServiceHandler(ProcessingEnvironment processingEnvironment) {
+		super(RestService.class, processingEnvironment);
+	}
 
-    @Override
-    public void validate(Element element, AnnotationElements validatedElements, IsValid valid) {
-        validatorHelper.enclosingElementHasEnhancedComponentAnnotation(element, validatedElements, valid);
+	@Override
+	public void validate(Element element, AnnotationElements validatedElements, IsValid valid) {
+		validatorHelper.enclosingElementHasEnhancedComponentAnnotation(element, validatedElements, valid);
 
-        validatorHelper.isNotPrivate(element, valid);
+		validatorHelper.isNotPrivate(element, valid);
 
-        validatorHelper.typeHasAnnotation(Rest.class, element, valid);
-    }
+		validatorHelper.typeHasAnnotation(Rest.class, element, valid);
+	}
 
-    @Override
-    public void process(Element element, EComponentHolder holder) {
-        String fieldName = element.getSimpleName().toString();
+	@Override
+	public void process(Element element, EComponentHolder holder) {
+		String fieldName = element.getSimpleName().toString();
 
-        TypeMirror fieldTypeMirror = element.asType();
-        String interfaceName = fieldTypeMirror.toString();
+		TypeMirror fieldTypeMirror = element.asType();
+		String interfaceName = fieldTypeMirror.toString();
 
-        String generatedClassName = interfaceName + ModelConstants.GENERATION_SUFFIX;
+		String generatedClassName = interfaceName + ModelConstants.GENERATION_SUFFIX;
 
-        JBlock methodBody = holder.getInitBody();
+		JBlock methodBody = holder.getInitBody();
 
-        JFieldRef field = JExpr.ref(fieldName);
+		JFieldRef field = JExpr.ref(fieldName);
 
-        methodBody.assign(field, JExpr._new(refClass(generatedClassName)).arg(holder.getContextRef()));
-    }
+		methodBody.assign(field, JExpr._new(refClass(generatedClassName)).arg(holder.getContextRef()));
+	}
 }
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/APTCodeModelHelper.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/APTCodeModelHelper.java
index 54660ae..9c7582c 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/APTCodeModelHelper.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/APTCodeModelHelper.java
@@ -15,6 +15,40 @@
  */
 package org.androidannotations.helper;
 
+import static com.sun.codemodel.JExpr._new;
+import static com.sun.codemodel.JExpr.lit;
+import static org.androidannotations.helper.ModelConstants.GENERATION_SUFFIX;
+
+import java.io.StringWriter;
+import java.lang.annotation.Annotation;
+import java.lang.reflect.Field;
+import java.util.ArrayList;
+import java.util.Collections;
+import java.util.HashMap;
+import java.util.LinkedHashMap;
+import java.util.List;
+import java.util.Map;
+
+import javax.lang.model.element.AnnotationMirror;
+import javax.lang.model.element.AnnotationValue;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.ExecutableElement;
+import javax.lang.model.element.TypeElement;
+import javax.lang.model.element.TypeParameterElement;
+import javax.lang.model.element.VariableElement;
+import javax.lang.model.type.ArrayType;
+import javax.lang.model.type.DeclaredType;
+import javax.lang.model.type.TypeKind;
+import javax.lang.model.type.TypeMirror;
+import javax.lang.model.type.WildcardType;
+import javax.lang.model.util.ElementFilter;
+import javax.lang.model.util.Types;
+
+import org.androidannotations.annotations.EBean;
+import org.androidannotations.holder.EBeanHolder;
+import org.androidannotations.holder.EComponentHolder;
+import org.androidannotations.holder.GeneratedClassHolder;
+
 import com.sun.codemodel.JAnnotatable;
 import com.sun.codemodel.JAnnotationUse;
 import com.sun.codemodel.JBlock;
@@ -32,38 +66,6 @@
 import com.sun.codemodel.JSuperWildcard;
 import com.sun.codemodel.JType;
 import com.sun.codemodel.JVar;
-import org.androidannotations.annotations.EBean;
-import org.androidannotations.holder.EBeanHolder;
-import org.androidannotations.holder.EComponentHolder;
-import org.androidannotations.holder.GeneratedClassHolder;
-
-import javax.lang.model.element.AnnotationMirror;
-import javax.lang.model.element.AnnotationValue;
-import javax.lang.model.element.Element;
-import javax.lang.model.element.ExecutableElement;
-import javax.lang.model.element.TypeElement;
-import javax.lang.model.element.TypeParameterElement;
-import javax.lang.model.element.VariableElement;
-import javax.lang.model.type.ArrayType;
-import javax.lang.model.type.DeclaredType;
-import javax.lang.model.type.TypeKind;
-import javax.lang.model.type.TypeMirror;
-import javax.lang.model.type.WildcardType;
-import javax.lang.model.util.ElementFilter;
-import javax.lang.model.util.Types;
-import java.io.StringWriter;
-import java.lang.annotation.Annotation;
-import java.lang.reflect.Field;
-import java.util.ArrayList;
-import java.util.Collections;
-import java.util.HashMap;
-import java.util.LinkedHashMap;
-import java.util.List;
-import java.util.Map;
-
-import static com.sun.codemodel.JExpr._new;
-import static com.sun.codemodel.JExpr.lit;
-import static org.androidannotations.helper.ModelConstants.GENERATION_SUFFIX;
 
 public class APTCodeModelHelper {
 
@@ -224,6 +226,8 @@ private JMethod findAlreadyGeneratedMethod(ExecutableElement executableElement,
 		JDefinedClass definedClass = holder.getGeneratedClass();
 		String methodName = executableElement.getSimpleName().toString();
 		List<? extends VariableElement> parameters = executableElement.getParameters();
+		// CHECKSTYLE:OFF
+		// TODO: refactor the nasty label jump
 		method: for (JMethod method : definedClass.methods()) {
 			if (method.name().equals(methodName) && method.params().size() == parameters.size()) {
 				int i = 0;
@@ -237,6 +241,7 @@ private JMethod findAlreadyGeneratedMethod(ExecutableElement executableElement,
 				return method;
 			}
 		}
+		// CHECKSTYLE:ON
 		return null;
 	}
 
@@ -402,7 +407,7 @@ public JDefinedClass createDelegatingAnonymousRunnableClass(EComponentHolder hol
 	/**
 	 * Gets all of the methods of the class and includes the methods of any
 	 * implemented interfaces.
-	 * 
+	 *
 	 * @param typeElement
 	 * @return full list of methods.
 	 */
@@ -508,7 +513,7 @@ public JExpression litObject(Object o) {
 		}
 	}
 
-	//TODO it would be nice to cache the result map for better performance
+	// TODO it would be nice to cache the result map for better performance
 	public TypeMirror getActualType(Element element, GeneratedClassHolder holder) {
 		Types types = holder.processingEnvironment().getTypeUtils();
 		DeclaredType typeMirror = (DeclaredType) element.getEnclosingElement().asType();
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ActionBarSherlockHelper.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ActionBarSherlockHelper.java
index 8ea066c..d848b22 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ActionBarSherlockHelper.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ActionBarSherlockHelper.java
@@ -44,13 +44,14 @@ public boolean usesActionBarSherlock(EComponentHolder holder) {
 	 * types
 	 */
 	public boolean usesActionBarSherlock(TypeElement typeElement) {
-		TypeMirror superType;
-		while (!((superType = typeElement.getSuperclass()) instanceof NoType)) {
+		TypeMirror superType = typeElement.getSuperclass();
+		while (!(superType instanceof NoType)) {
 			typeElement = (TypeElement) ((DeclaredType) superType).asElement();
 			String qName = typeElement.getQualifiedName().toString();
 			if (qName.startsWith("com.actionbarsherlock.app")) {
 				return true;
 			}
+			superType = typeElement.getSuperclass();
 		}
 		return false;
 	}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ActivityIntentBuilder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ActivityIntentBuilder.java
index 990235b..95cedd9 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ActivityIntentBuilder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ActivityIntentBuilder.java
@@ -15,15 +15,25 @@
  */
 package org.androidannotations.helper;
 
-import com.sun.codemodel.*;
-import org.androidannotations.holder.HasIntentBuilder;
-
 import static com.sun.codemodel.JExpr._new;
 import static com.sun.codemodel.JExpr._super;
 import static com.sun.codemodel.JMod.PRIVATE;
 import static com.sun.codemodel.JMod.PUBLIC;
 import static com.sun.codemodel.JMod.STATIC;
 
+import org.androidannotations.holder.HasIntentBuilder;
+
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JClassAlreadyExistsException;
+import com.sun.codemodel.JConditional;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JFieldVar;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JMod;
+import com.sun.codemodel.JVar;
+
 public class ActivityIntentBuilder extends IntentBuilder {
 
 	private static final int MIN_SDK_WITH_FRAGMENT_SUPPORT = 11;
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AndroidManifest.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AndroidManifest.java
index 768dbca..cb75f4b 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AndroidManifest.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AndroidManifest.java
@@ -35,6 +35,8 @@ public String toString() {
 		return "AndroidManifest [applicationPackage=" + applicationPackage + ", componentQualifiedNames=" + componentQualifiedNames + ", permissionQualifiedNames=" + permissionQualifiedNames + ", applicationClassName=" + applicationClassName + ", libraryProject=" + libraryProject + ", debugabble=" + debugabble + ", minSdkVersion=" + minSdkVersion + ", maxSdkVersion=" + maxSdkVersion + ", targetSdkVersion=" + targetSdkVersion + "]";
 	}
 
+	// CHECKSTYLE:OFF
+
 	public static AndroidManifest createManifest(String applicationPackage, String applicationClassName, List<String> componentQualifiedNames, List<String> permissionQualifiedNames, int minSdkVersion, int maxSdkVersion, int targetSdkVersion, boolean debugabble) {
 		return new AndroidManifest(false, applicationPackage, applicationClassName, componentQualifiedNames, permissionQualifiedNames, minSdkVersion, maxSdkVersion, targetSdkVersion, debugabble);
 	}
@@ -55,6 +57,8 @@ private AndroidManifest(boolean libraryProject, String applicationPackage, Strin
 		debugabble = debuggable;
 	}
 
+	// CHECKSTYLE:ON
+
 	public String getApplicationPackage() {
 		return applicationPackage;
 	}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AnnotationArrayParamExtractor.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AnnotationArrayParamExtractor.java
index 61ab1f4..14dd5ba 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AnnotationArrayParamExtractor.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AnnotationArrayParamExtractor.java
@@ -15,17 +15,18 @@
  */
 package org.androidannotations.helper;
 
-import com.sun.codemodel.JAnnotationArrayMember;
-import com.sun.codemodel.JClass;
-import com.sun.codemodel.JExpr;
-import com.sun.codemodel.JExpression;
-import org.androidannotations.holder.GeneratedClassHolder;
-
 import javax.lang.model.element.AnnotationMirror;
 import javax.lang.model.element.VariableElement;
 import javax.lang.model.type.TypeMirror;
 import javax.lang.model.util.SimpleAnnotationValueVisitor6;
 
+import org.androidannotations.holder.GeneratedClassHolder;
+
+import com.sun.codemodel.JAnnotationArrayMember;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JExpression;
+
 public class AnnotationArrayParamExtractor extends SimpleAnnotationValueVisitor6<Void, JAnnotationArrayMember> {
 
 	private GeneratedClassHolder holder;
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AnnotationHelper.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AnnotationHelper.java
index 7e96f6d..dbf8a62 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AnnotationHelper.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AnnotationHelper.java
@@ -15,16 +15,15 @@
  */
 package org.androidannotations.helper;
 
-import com.sun.codemodel.JFieldRef;
-import org.androidannotations.annotations.OnActivityResult;
-import org.androidannotations.annotations.OptionsItem;
-import org.androidannotations.annotations.ResId;
-import org.androidannotations.logger.Level;
-import org.androidannotations.logger.Logger;
-import org.androidannotations.logger.LoggerFactory;
-import org.androidannotations.process.ProcessHolder;
-import org.androidannotations.rclass.IRInnerClass;
-import org.androidannotations.rclass.RInnerClass;
+import static org.androidannotations.helper.ModelConstants.GENERATION_SUFFIX;
+import static org.androidannotations.helper.ModelConstants.VALID_ENHANCED_COMPONENT_ANNOTATIONS;
+
+import java.lang.annotation.Annotation;
+import java.lang.reflect.InvocationTargetException;
+import java.lang.reflect.Method;
+import java.util.ArrayList;
+import java.util.List;
+import java.util.Map;
 
 import javax.annotation.processing.ProcessingEnvironment;
 import javax.lang.model.element.AnnotationMirror;
@@ -39,15 +38,18 @@
 import javax.lang.model.type.TypeMirror;
 import javax.lang.model.util.Elements;
 import javax.lang.model.util.Types;
-import java.lang.annotation.Annotation;
-import java.lang.reflect.InvocationTargetException;
-import java.lang.reflect.Method;
-import java.util.ArrayList;
-import java.util.List;
-import java.util.Map;
 
-import static org.androidannotations.helper.ModelConstants.GENERATION_SUFFIX;
-import static org.androidannotations.helper.ModelConstants.VALID_ENHANCED_COMPONENT_ANNOTATIONS;
+import org.androidannotations.annotations.OnActivityResult;
+import org.androidannotations.annotations.OptionsItem;
+import org.androidannotations.annotations.ResId;
+import org.androidannotations.logger.Level;
+import org.androidannotations.logger.Logger;
+import org.androidannotations.logger.LoggerFactory;
+import org.androidannotations.process.ProcessHolder;
+import org.androidannotations.rclass.IRInnerClass;
+import org.androidannotations.rclass.RInnerClass;
+
+import com.sun.codemodel.JFieldRef;
 
 public class AnnotationHelper {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AnnotationParamExtractor.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AnnotationParamExtractor.java
index 48f24dd..5c84dbd 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AnnotationParamExtractor.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/AnnotationParamExtractor.java
@@ -15,8 +15,10 @@
  */
 package org.androidannotations.helper;
 
-import com.sun.codemodel.*;
-import org.androidannotations.holder.GeneratedClassHolder;
+import java.lang.reflect.Constructor;
+import java.lang.reflect.Method;
+import java.util.List;
+import java.util.Map;
 
 import javax.lang.model.element.AnnotationMirror;
 import javax.lang.model.element.AnnotationValue;
@@ -24,10 +26,15 @@
 import javax.lang.model.element.VariableElement;
 import javax.lang.model.type.TypeMirror;
 import javax.lang.model.util.SimpleAnnotationValueVisitor6;
-import java.lang.reflect.Constructor;
-import java.lang.reflect.Method;
-import java.util.List;
-import java.util.Map;
+
+import org.androidannotations.holder.GeneratedClassHolder;
+
+import com.sun.codemodel.JAnnotationArrayMember;
+import com.sun.codemodel.JAnnotationUse;
+import com.sun.codemodel.JAnnotationValue;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JExpression;
 
 public class AnnotationParamExtractor extends SimpleAnnotationValueVisitor6<Void, String> {
 
@@ -123,7 +130,6 @@ public Void visitType(TypeMirror t, String p) {
 	}
 
 	@Override
-	@SuppressWarnings("unchecked")
 	public Void visitAnnotation(AnnotationMirror a, String p) {
 		try {
 			JClass annotationJClass = helper.typeMirrorToJClass(a.getAnnotationType(), holder);
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/BundleHelper.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/BundleHelper.java
index 6b1da58..95e5713 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/BundleHelper.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/BundleHelper.java
@@ -15,61 +15,62 @@
  */
 package org.androidannotations.helper;
 
-import com.sun.codemodel.JClass;
-import com.sun.codemodel.JExpr;
-import com.sun.codemodel.JExpression;
-import com.sun.codemodel.JMethod;
+import static org.androidannotations.helper.CanonicalNameConstants.BUNDLE;
+import static org.androidannotations.helper.CanonicalNameConstants.CHAR_SEQUENCE;
+import static org.androidannotations.helper.CanonicalNameConstants.STRING;
+
+import java.util.HashMap;
+import java.util.List;
+import java.util.Map;
 
 import javax.lang.model.element.TypeElement;
 import javax.lang.model.type.ArrayType;
 import javax.lang.model.type.DeclaredType;
 import javax.lang.model.type.TypeKind;
 import javax.lang.model.type.TypeMirror;
-import java.util.HashMap;
-import java.util.List;
-import java.util.Map;
 
-import static org.androidannotations.helper.CanonicalNameConstants.BUNDLE;
-import static org.androidannotations.helper.CanonicalNameConstants.CHAR_SEQUENCE;
-import static org.androidannotations.helper.CanonicalNameConstants.STRING;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JMethod;
 
 public class BundleHelper {
-	public static final Map<String, String> methodSuffixNameByTypeName = new HashMap<String, String>();
+	public static final Map<String, String> METHOD_SUFFIX_BY_TYPE_NAME = new HashMap<String, String>();
 
 	static {
 
-		methodSuffixNameByTypeName.put(BUNDLE, "Bundle");
+		METHOD_SUFFIX_BY_TYPE_NAME.put(BUNDLE, "Bundle");
 
-		methodSuffixNameByTypeName.put("boolean", "Boolean");
-		methodSuffixNameByTypeName.put("boolean[]", "BooleanArray");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("boolean", "Boolean");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("boolean[]", "BooleanArray");
 
-		methodSuffixNameByTypeName.put("byte", "Byte");
-		methodSuffixNameByTypeName.put("byte[]", "ByteArray");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("byte", "Byte");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("byte[]", "ByteArray");
 
-		methodSuffixNameByTypeName.put("char", "Char");
-		methodSuffixNameByTypeName.put("char[]", "CharArray");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("char", "Char");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("char[]", "CharArray");
 
-		methodSuffixNameByTypeName.put(CHAR_SEQUENCE, "CharSequence");
+		METHOD_SUFFIX_BY_TYPE_NAME.put(CHAR_SEQUENCE, "CharSequence");
 
-		methodSuffixNameByTypeName.put("double", "Double");
-		methodSuffixNameByTypeName.put("double[]", "DoubleArray");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("double", "Double");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("double[]", "DoubleArray");
 
-		methodSuffixNameByTypeName.put("float", "Float");
-		methodSuffixNameByTypeName.put("float[]", "FloatArray");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("float", "Float");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("float[]", "FloatArray");
 
-		methodSuffixNameByTypeName.put("int", "Int");
-		methodSuffixNameByTypeName.put("int[]", "IntArray");
-		methodSuffixNameByTypeName.put("java.util.ArrayList<java.lang.Integer>", "IntegerArrayList");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("int", "Int");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("int[]", "IntArray");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("java.util.ArrayList<java.lang.Integer>", "IntegerArrayList");
 
-		methodSuffixNameByTypeName.put("long", "Long");
-		methodSuffixNameByTypeName.put("long[]", "LongArray");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("long", "Long");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("long[]", "LongArray");
 
-		methodSuffixNameByTypeName.put("short", "Short");
-		methodSuffixNameByTypeName.put("short[]", "ShortArray");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("short", "Short");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("short[]", "ShortArray");
 
-		methodSuffixNameByTypeName.put(STRING, "String");
-		methodSuffixNameByTypeName.put("java.lang.String[]", "StringArray");
-		methodSuffixNameByTypeName.put("java.util.ArrayList<java.lang.String>", "StringArrayList");
+		METHOD_SUFFIX_BY_TYPE_NAME.put(STRING, "String");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("java.lang.String[]", "StringArray");
+		METHOD_SUFFIX_BY_TYPE_NAME.put("java.util.ArrayList<java.lang.String>", "StringArrayList");
 	}
 
 	private AnnotationHelper annotationHelper;
@@ -90,10 +91,10 @@ public BundleHelper(AnnotationHelper helper, TypeMirror element) {
 		String typeString = element.toString();
 		TypeElement elementType = annotationHelper.typeElementFromQualifiedName(typeString);
 
-		if (methodSuffixNameByTypeName.containsKey(typeString)) {
+		if (METHOD_SUFFIX_BY_TYPE_NAME.containsKey(typeString)) {
 
-			methodNameToSave = "put" + methodSuffixNameByTypeName.get(typeString);
-			methodNameToRestore = "get" + methodSuffixNameByTypeName.get(typeString);
+			methodNameToSave = "put" + METHOD_SUFFIX_BY_TYPE_NAME.get(typeString);
+			methodNameToRestore = "get" + METHOD_SUFFIX_BY_TYPE_NAME.get(typeString);
 
 		} else if (element.getKind() == TypeKind.ARRAY) {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/CaseHelper.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/CaseHelper.java
index db83b58..31c32bf 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/CaseHelper.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/CaseHelper.java
@@ -22,12 +22,15 @@
 
 public class CaseHelper {
 
-	private static final Pattern pattern = Pattern.compile("([A-Z]|[a-z])[a-z]*");
+	private static final Pattern PATTERN = Pattern.compile("([A-Z]|[a-z])[a-z]*");
+
+	private CaseHelper() {
+	}
 
 	public static String camelCaseToSnakeCase(String camelCase) {
 
 		List<String> tokens = new ArrayList<String>();
-		Matcher matcher = pattern.matcher(camelCase);
+		Matcher matcher = PATTERN.matcher(camelCase);
 		String acronym = "";
 		while (matcher.find()) {
 			String found = matcher.group();
@@ -61,14 +64,16 @@ public static String camelCaseToUpperSnakeCase(String camelCase) {
 	}
 
 	public static String lowerCaseFirst(String string) {
-		if (string.length() < 2)
+		if (string.length() < 2) {
 			return string.toLowerCase();
+		}
+
 		String first = string.substring(0, 1).toLowerCase();
 		String end = string.substring(1, string.length());
 		return first + end;
-    }
+	}
 
-    public static String camelCaseToUpperSnakeCase(String prefix, String camelCase, String suffix) {
+	public static String camelCaseToUpperSnakeCase(String prefix, String camelCase, String suffix) {
 		if (prefix != null && !camelCase.startsWith(prefix)) {
 			camelCase = prefix + "_" + camelCase;
 		}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/FileHelper.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/FileHelper.java
index 97989a4..16c37eb 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/FileHelper.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/FileHelper.java
@@ -27,6 +27,9 @@
 
 public class FileHelper {
 
+	private FileHelper() {
+	}
+
 	public static Option<File> findRootProject(ProcessingEnvironment processingEnv) {
 		Option<FileHolder> rootProjectHolder = findRootProjectHolder(processingEnv);
 		if (rootProjectHolder.isAbsent()) {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/IntentBuilder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/IntentBuilder.java
index 85dfdab..a53dab3 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/IntentBuilder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/IntentBuilder.java
@@ -15,6 +15,26 @@
  */
 package org.androidannotations.helper;
 
+import static com.sun.codemodel.JExpr._new;
+import static com.sun.codemodel.JExpr._super;
+import static com.sun.codemodel.JExpr.cast;
+import static com.sun.codemodel.JExpr.ref;
+import static com.sun.codemodel.JMod.PUBLIC;
+import static com.sun.codemodel.JMod.STATIC;
+import static org.androidannotations.helper.CanonicalNameConstants.PARCELABLE;
+import static org.androidannotations.helper.CanonicalNameConstants.SERIALIZABLE;
+import static org.androidannotations.helper.CanonicalNameConstants.STRING;
+
+import java.util.HashMap;
+import java.util.Map;
+
+import javax.lang.model.type.TypeKind;
+import javax.lang.model.type.TypeMirror;
+import javax.lang.model.util.Elements;
+import javax.lang.model.util.Types;
+
+import org.androidannotations.holder.HasIntentBuilder;
+
 import com.sun.codemodel.JClass;
 import com.sun.codemodel.JClassAlreadyExistsException;
 import com.sun.codemodel.JDefinedClass;
@@ -25,24 +45,6 @@
 import com.sun.codemodel.JMethod;
 import com.sun.codemodel.JMod;
 import com.sun.codemodel.JVar;
-import org.androidannotations.holder.HasIntentBuilder;
-
-import javax.lang.model.type.TypeKind;
-import javax.lang.model.type.TypeMirror;
-import javax.lang.model.util.Elements;
-import javax.lang.model.util.Types;
-import java.util.HashMap;
-import java.util.Map;
-
-import static com.sun.codemodel.JExpr._new;
-import static com.sun.codemodel.JExpr._super;
-import static com.sun.codemodel.JExpr.cast;
-import static com.sun.codemodel.JExpr.ref;
-import static com.sun.codemodel.JMod.PUBLIC;
-import static com.sun.codemodel.JMod.STATIC;
-import static org.androidannotations.helper.CanonicalNameConstants.PARCELABLE;
-import static org.androidannotations.helper.CanonicalNameConstants.SERIALIZABLE;
-import static org.androidannotations.helper.CanonicalNameConstants.STRING;
 
 public abstract class IntentBuilder {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/OptionsHelper.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/OptionsHelper.java
index cbf5885..29e1602 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/OptionsHelper.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/OptionsHelper.java
@@ -59,7 +59,7 @@ public OptionsHelper(ProcessingEnvironment processingEnvironment) {
 	}
 
 	public static Set<String> getOptionsConstants() {
-		TreeSet<String> set = new TreeSet<String>();
+		Set<String> set = new TreeSet<String>();
 		for (Option optionEnum : Option.values()) {
 			set.add(optionEnum.getKey());
 		}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/RestAnnotationHelper.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/RestAnnotationHelper.java
index ba4c68d..8af8ee4 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/RestAnnotationHelper.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/RestAnnotationHelper.java
@@ -16,13 +16,11 @@
 package org.androidannotations.helper;
 
 import java.util.ArrayList;
-import java.util.Collection;
 import java.util.HashSet;
 import java.util.LinkedHashMap;
 import java.util.List;
-import java.util.Map;
 import java.util.Set;
-import java.util.TreeMap;
+import java.util.SortedMap;
 import java.util.TreeSet;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
@@ -140,7 +138,7 @@ public void urlVariableNamesExistInParametersAndHasOnlyOneMoreParameter(Executab
 		return variableNames;
 	}
 
-	public JVar declareUrlVariables(ExecutableElement element, RestHolder holder, JBlock methodBody, TreeMap<String, JVar> methodParams) {
+	public JVar declareUrlVariables(ExecutableElement element, RestHolder holder, JBlock methodBody, SortedMap<String, JVar> methodParams) {
 		Set<String> urlVariables = extractUrlVariableNames(element);
 
 		// cookies in url?
@@ -295,15 +293,15 @@ public JVar declareHttpHeaders(ExecutableElement executableElement, RestHolder h
 		return httpHeadersVar;
 	}
 
-    public JVar getEntitySentToServer(ExecutableElement element, TreeMap<String, JVar> params) {
-        Set<String> urlVariables = extractUrlVariableNames(element);
-        for (String paramName : params.keySet()) {
-            if (!urlVariables.contains(paramName)) {
-                return params.get(paramName);
-            }
-        }
-        return null;
-    }
+	public JVar getEntitySentToServer(ExecutableElement element, SortedMap<String, JVar> params) {
+		Set<String> urlVariables = extractUrlVariableNames(element);
+		for (String paramName : params.keySet()) {
+			if (!urlVariables.contains(paramName)) {
+				return params.get(paramName);
+			}
+		}
+		return null;
+	}
 
 	public JExpression declareHttpEntity(ProcessHolder holder, JBlock body, JVar entitySentToServer, JVar httpHeaders) {
 		JType entityType = holder.refClass(Object.class);
@@ -376,16 +374,16 @@ public JClass retrieveResponseClass(TypeMirror returnType, RestHolder holder) {
 	 * <li>The type is a generics and enclosing type is an interface I&lt;T&gt;
 	 * : Looking the inheritance tree, then</li>
 	 * <ol>
-	 * <li>One of the parent is a {@link Map} : Generate a subclass of
-	 * {@link LinkedHashMap}&lt;T&gt; one and return it</li>
+	 * <li>One of the parent is a {@link java.util.Map Map} : Generate a
+	 * subclass of {@link LinkedHashMap}&lt;T&gt; one and return it</li>
 	 * <li>One of the parent is a {@link Set} : Generate a subclass of
 	 * {@link TreeSet}&lt;T&gt; one and return it</li>
-	 * <li>One of the parent is a {@link Collection} : Generate a subclass of
-	 * {@link ArrayList}&lt;T&gt; one and return it</li>
+	 * <li>One of the parent is a {@link java.util.Collection Collection} :
+	 * Generate a subclass of {@link ArrayList}&lt;T&gt; one and return it</li>
 	 * <li>Return {@link Object} definition</li>
 	 * </ol>
 	 * </ul>
-	 * 
+	 *
 	 */
 	private JClass resolveResponseClass(TypeMirror expectedType, RestHolder holder) {
 		// is a class or an interface
@@ -419,8 +417,8 @@ private JClass resolveResponseClass(TypeMirror expectedType, RestHolder holder)
 
 	/**
 	 * Recursive method used to find if one of the grand-parent of the
-	 * <code>enclosingJClass</code> is {@link Map}, {@link Set} or
-	 * {@link Collection}.
+	 * <code>enclosingJClass</code> is {@link java.util.Map Map}, {@link Set} or
+	 * {@link java.util.Collection Collection}.
 	 */
 	private JClass retrieveDecoratedResponseClass(DeclaredType declaredType, TypeElement typeElement, RestHolder holder) {
 		String classTypeBaseName = typeElement.toString();
@@ -448,15 +446,16 @@ private JClass retrieveDecoratedResponseClass(DeclaredType declaredType, TypeEle
 			String decoratedClassNameSuffix = "";
 			JClass decoratedSuperClass = holder.refClass(decoratedClassName);
 			for (TypeMirror typeArgument : declaredType.getTypeArguments()) {
+				TypeMirror actualTypeArgument = typeArgument;
 				if (typeArgument instanceof WildcardType) {
 					WildcardType wildcardType = (WildcardType) typeArgument;
 					if (wildcardType.getExtendsBound() != null) {
-						typeArgument = wildcardType.getExtendsBound();
+						actualTypeArgument = wildcardType.getExtendsBound();
 					} else if (wildcardType.getSuperBound() != null) {
-						typeArgument = wildcardType.getSuperBound();
+						actualTypeArgument = wildcardType.getSuperBound();
 					}
 				}
-				JClass narrowJClass = codeModelHelper.typeMirrorToJClass(typeArgument, holder);
+				JClass narrowJClass = codeModelHelper.typeMirrorToJClass(actualTypeArgument, holder);
 				decoratedSuperClass = decoratedSuperClass.narrow(narrowJClass);
 				decoratedClassNameSuffix += plainName(narrowJClass);
 			}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ServiceIntentBuilder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ServiceIntentBuilder.java
index e9bb6e8..c1909e9 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ServiceIntentBuilder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ServiceIntentBuilder.java
@@ -15,9 +15,10 @@
  */
 package org.androidannotations.helper;
 
-import com.sun.codemodel.JClass;
 import org.androidannotations.holder.HasIntentBuilder;
 
+import com.sun.codemodel.JClass;
+
 public class ServiceIntentBuilder extends IntentBuilder {
 
 	public ServiceIntentBuilder(HasIntentBuilder holder, AndroidManifest androidManifest) {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ValidatorHelper.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ValidatorHelper.java
index 5c07df9..4918cee 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ValidatorHelper.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ValidatorHelper.java
@@ -266,7 +266,7 @@ public void enclosingElementHasAndroidAnnotation(Element element, AnnotationElem
 	}
 
 	private void hasClassAnnotation(Element reportElement, Element element, AnnotationElements validatedElements, Class<? extends Annotation> validAnnotation, IsValid valid) {
-		ArrayList<Class<? extends Annotation>> validAnnotations = new ArrayList<Class<? extends Annotation>>();
+		List<Class<? extends Annotation>> validAnnotations = new ArrayList<Class<? extends Annotation>>();
 		validAnnotations.add(validAnnotation);
 		hasOneOfClassAnnotations(reportElement, element, validatedElements, validAnnotations, valid);
 	}
@@ -1074,7 +1074,7 @@ private TypeElement getArrayEnclosingType(String typeString) {
 	}
 
 	private boolean isKnownBundleCompatibleType(String type) {
-		return BundleHelper.methodSuffixNameByTypeName.containsKey(type);
+		return BundleHelper.METHOD_SUFFIX_BY_TYPE_NAME.containsKey(type);
 	}
 
 	public void componentRegistered(Element element, AndroidManifest androidManifest, IsValid valid) {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ValidatorParameterHelper.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ValidatorParameterHelper.java
index 27ad59e..90809a2 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ValidatorParameterHelper.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/helper/ValidatorParameterHelper.java
@@ -35,8 +35,7 @@
 public class ValidatorParameterHelper {
 
 	private static final List<String> ANDROID_SHERLOCK_MENU_ITEM_QUALIFIED_NAMES = asList(CanonicalNameConstants.MENU_ITEM, CanonicalNameConstants.SHERLOCK_MENU_ITEM);
-	private static final List<String> EDITOR_ACTION_ALLOWED_PARAMETER_TYPES = asList(CanonicalNameConstants.TEXT_VIEW,
-		CanonicalNameConstants.INTEGER, "int", CanonicalNameConstants.KEY_EVENT);
+	private static final List<String> EDITOR_ACTION_ALLOWED_PARAMETER_TYPES = asList(CanonicalNameConstants.TEXT_VIEW, CanonicalNameConstants.INTEGER, "int", CanonicalNameConstants.KEY_EVENT);
 
 	protected final TargetAnnotationHelper annotationHelper;
 
@@ -138,7 +137,7 @@ private void hasZeroOrOneParameterOfType(String typeCanonicalName, ExecutableEle
 			String parameterType = parameter.asType().toString();
 			if (parameterType.equals(typeCanonicalName)) {
 				if (parameterOfTypeFound) {
-					annotationHelper.printAnnotationError(executableElement, "You can declare only one parameter of type "+typeCanonicalName);
+					annotationHelper.printAnnotationError(executableElement, "You can declare only one parameter of type " + typeCanonicalName);
 					valid.invalidate();
 				}
 				parameterOfTypeFound = true;
@@ -151,7 +150,7 @@ private void hasZeroOrOneParameterOfPrimitiveType(String typeCanonicalName, Type
 		for (VariableElement parameter : executableElement.getParameters()) {
 			if (parameter.asType().getKind() == typeKind || parameter.asType().toString().equals(typeCanonicalName)) {
 				if (parameterOfTypeFound) {
-					annotationHelper.printAnnotationError(executableElement, "You can declare only one parameter of type "+typeKind.name()+" or "+typeCanonicalName);
+					annotationHelper.printAnnotationError(executableElement, "You can declare only one parameter of type " + typeKind.name() + " or " + typeCanonicalName);
 					valid.invalidate();
 				}
 				parameterOfTypeFound = true;
@@ -160,17 +159,17 @@ private void hasZeroOrOneParameterOfPrimitiveType(String typeCanonicalName, Type
 	}
 
 	public void hasNoOtherParameterThanCompoundButtonOrBoolean(ExecutableElement executableElement, IsValid valid) {
-		String[] types = new String[]{CanonicalNameConstants.COMPOUND_BUTTON, CanonicalNameConstants.BOOLEAN, "boolean"};
+		String[] types = new String[] { CanonicalNameConstants.COMPOUND_BUTTON, CanonicalNameConstants.BOOLEAN, "boolean" };
 		hasNotOtherParameterThanTypes(types, executableElement, valid);
 	}
 
 	public void hasNoOtherParameterThanMotionEventOrView(ExecutableElement executableElement, IsValid valid) {
-		String[] types = new String[]{CanonicalNameConstants.MOTION_EVENT, CanonicalNameConstants.VIEW};
+		String[] types = new String[] { CanonicalNameConstants.MOTION_EVENT, CanonicalNameConstants.VIEW };
 		hasNotOtherParameterThanTypes(types, executableElement, valid);
 	}
 
 	public void hasNoOtherParameterThanViewOrBoolean(ExecutableElement executableElement, IsValid valid) {
-		String[] types = new String[]{CanonicalNameConstants.VIEW, CanonicalNameConstants.BOOLEAN, "boolean"};
+		String[] types = new String[] { CanonicalNameConstants.VIEW, CanonicalNameConstants.BOOLEAN, "boolean" };
 		hasNotOtherParameterThanTypes(types, executableElement, valid);
 	}
 
@@ -179,7 +178,7 @@ private void hasNotOtherParameterThanTypes(String[] typesCanonicalNames, Executa
 		for (VariableElement parameter : executableElement.getParameters()) {
 			String parameterType = parameter.asType().toString();
 			if (!types.contains(parameterType)) {
-				annotationHelper.printAnnotationError(executableElement, "You can declare only parameters of type "+Arrays.toString(typesCanonicalNames));
+				annotationHelper.printAnnotationError(executableElement, "You can declare only parameters of type " + Arrays.toString(typesCanonicalNames));
 				valid.invalidate();
 			}
 		}
@@ -191,12 +190,12 @@ public void hasNoOtherParameterThanContextOrIntentOrReceiverExtraAnnotated(Execu
 	}
 
 	public void hasNoOtherParameterThanContextOrIntentOrReceiverActionExtraAnnotated(ExecutableElement executableElement, IsValid valid) {
-		String[] types = new String[] {CanonicalNameConstants.CONTEXT, CanonicalNameConstants.INTENT};
+		String[] types = new String[] { CanonicalNameConstants.CONTEXT, CanonicalNameConstants.INTENT };
 		hasNotOtherParameterThanTypesOrAnnotatedWith(types, ReceiverAction.Extra.class, executableElement, valid);
 	}
 
 	public void hasNoOtherParameterThanIntentOrIntOrOnActivityResultExtraAnnotated(ExecutableElement executableElement, IsValid valid) {
-		String[] types = new String[] {CanonicalNameConstants.INTENT, CanonicalNameConstants.INTEGER, "int"};
+		String[] types = new String[] { CanonicalNameConstants.INTENT, CanonicalNameConstants.INTEGER, "int" };
 		hasNotOtherParameterThanTypesOrAnnotatedWith(types, OnActivityResult.Extra.class, executableElement, valid);
 	}
 
@@ -205,7 +204,7 @@ public void hasNotOtherParameterThanTypesOrAnnotatedWith(String[] typesCanonical
 		for (VariableElement parameter : executableElement.getParameters()) {
 			String parameterType = parameter.asType().toString();
 			if (!types.contains(parameterType) && parameter.getAnnotation(annotationClass) == null) {
-				annotationHelper.printAnnotationError(executableElement, "You can declare only parameters of type "+Arrays.toString(typesCanonicalNames)+" or parameters annotated with @"+annotationClass.getCanonicalName());
+				annotationHelper.printAnnotationError(executableElement, "You can declare only parameters of type " + Arrays.toString(typesCanonicalNames) + " or parameters annotated with @" + annotationClass.getCanonicalName());
 				valid.invalidate();
 			}
 		}
@@ -259,7 +258,7 @@ public void hasAtMostOneSpecificParameter(ExecutableElement executableElement, L
 			if (qualifiedNames.contains(parameter.asType().toString())) {
 				if (hasOneMatchingParameter) {
 					valid.invalidate();
-					annotationHelper.printAnnotationError(executableElement, "%s can't have more than one parameter of type "+parameter.asType().toString());
+					annotationHelper.printAnnotationError(executableElement, "%s can't have more than one parameter of type " + parameter.asType().toString());
 				} else {
 					hasOneMatchingParameter = true;
 				}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EActivityHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EActivityHolder.java
index 22c979a..0c86249 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EActivityHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EActivityHolder.java
@@ -15,28 +15,17 @@
  */
 package org.androidannotations.holder;
 
-import com.sun.codemodel.JBlock;
-import com.sun.codemodel.JClass;
-import com.sun.codemodel.JClassAlreadyExistsException;
-import com.sun.codemodel.JDefinedClass;
-import com.sun.codemodel.JExpr;
-import com.sun.codemodel.JExpression;
-import com.sun.codemodel.JFieldVar;
-import com.sun.codemodel.JInvocation;
-import com.sun.codemodel.JMethod;
-import com.sun.codemodel.JMod;
-import com.sun.codemodel.JType;
-import com.sun.codemodel.JVar;
-
-import org.androidannotations.api.SdkVersionHelper;
-import org.androidannotations.helper.ActionBarSherlockHelper;
-import org.androidannotations.helper.ActivityIntentBuilder;
-import org.androidannotations.helper.AndroidManifest;
-import org.androidannotations.helper.AnnotationHelper;
-import org.androidannotations.helper.CanonicalNameConstants;
-import org.androidannotations.helper.IntentBuilder;
-import org.androidannotations.helper.OrmLiteHelper;
-import org.androidannotations.process.ProcessHolder;
+import static com.sun.codemodel.JExpr.FALSE;
+import static com.sun.codemodel.JExpr.TRUE;
+import static com.sun.codemodel.JExpr._new;
+import static com.sun.codemodel.JExpr._null;
+import static com.sun.codemodel.JExpr._super;
+import static com.sun.codemodel.JExpr._this;
+import static com.sun.codemodel.JExpr.cast;
+import static com.sun.codemodel.JExpr.invoke;
+import static com.sun.codemodel.JMod.PRIVATE;
+import static com.sun.codemodel.JMod.PUBLIC;
+import static org.androidannotations.helper.ModelConstants.GENERATION_SUFFIX;
 
 import java.util.ArrayList;
 import java.util.List;
@@ -49,17 +38,28 @@
 import javax.lang.model.type.TypeMirror;
 import javax.lang.model.util.ElementFilter;
 
-import static com.sun.codemodel.JExpr.FALSE;
-import static com.sun.codemodel.JExpr.TRUE;
-import static com.sun.codemodel.JExpr._new;
-import static com.sun.codemodel.JExpr._null;
-import static com.sun.codemodel.JExpr._super;
-import static com.sun.codemodel.JExpr._this;
-import static com.sun.codemodel.JExpr.cast;
-import static com.sun.codemodel.JExpr.invoke;
-import static com.sun.codemodel.JMod.PRIVATE;
-import static com.sun.codemodel.JMod.PUBLIC;
-import static org.androidannotations.helper.ModelConstants.GENERATION_SUFFIX;
+import org.androidannotations.api.SdkVersionHelper;
+import org.androidannotations.helper.ActionBarSherlockHelper;
+import org.androidannotations.helper.ActivityIntentBuilder;
+import org.androidannotations.helper.AndroidManifest;
+import org.androidannotations.helper.AnnotationHelper;
+import org.androidannotations.helper.CanonicalNameConstants;
+import org.androidannotations.helper.IntentBuilder;
+import org.androidannotations.helper.OrmLiteHelper;
+import org.androidannotations.process.ProcessHolder;
+
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JClassAlreadyExistsException;
+import com.sun.codemodel.JDefinedClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JFieldVar;
+import com.sun.codemodel.JInvocation;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JMod;
+import com.sun.codemodel.JType;
+import com.sun.codemodel.JVar;
 
 public class EActivityHolder extends EComponentWithViewSupportHolder implements HasIntentBuilder, HasExtras, HasInstanceState, HasOptionsMenu, HasOnActivityResult, HasReceiverRegistration {
 
@@ -148,6 +148,8 @@ protected void setOnCreate() {
 		viewNotifierHelper.resetPreviousNotifier(onCreateBody, previousNotifier);
 	}
 
+	// CHECKSTYLE:OFF
+
 	protected void setOnStart() {
 		JMethod method = generatedClass.method(JMod.PUBLIC, codeModel().VOID, "onStart");
 		method.annotate(Override.class);
@@ -339,7 +341,7 @@ private JMethod setContentViewMethod(JType[] paramTypes, String[] paramNames) {
 		JMethod method = generatedClass.method(JMod.PUBLIC, codeModel().VOID, "setContentView");
 		method.annotate(Override.class);
 
-		ArrayList<JVar> params = new ArrayList<JVar>();
+		List<JVar> params = new ArrayList<JVar>();
 		for (int i = 0; i < paramTypes.length; i++) {
 			JVar param = method.param(paramTypes[i], paramNames[i]);
 			params.add(param);
@@ -375,16 +377,15 @@ private void handleBackPressed() {
 
 			onKeyDownBody._if( //
 					sdkInt.lt(JExpr.lit(5)) //
-					.cand(keyCodeParam.eq(keyEventClass.staticRef("KEYCODE_BACK"))) //
-					.cand(eventParam.invoke("getRepeatCount").eq(JExpr.lit(0)))) //
+							.cand(keyCodeParam.eq(keyEventClass.staticRef("KEYCODE_BACK"))) //
+							.cand(eventParam.invoke("getRepeatCount").eq(JExpr.lit(0)))) //
 					._then() //
 					.invoke("onBackPressed");
 
 			onKeyDownBody._return( //
 					JExpr._super().invoke(onKeyDownMethod) //
-					.arg(keyCodeParam) //
-					.arg(eventParam));
-
+							.arg(keyCodeParam) //
+							.arg(eventParam));
 		}
 	}
 
@@ -412,8 +413,7 @@ private boolean isCustomOnBackPressedMethod(ExecutableElement method) {
 				&& method.getThrownTypes().size() == 0 //
 				&& method.getModifiers().contains(Modifier.PUBLIC) //
 				&& method.getReturnType().getKind().equals(TypeKind.VOID) //
-				&& method.getParameters().size() == 0 //
-				;
+				&& method.getParameters().size() == 0;
 	}
 
 	@Override
@@ -457,9 +457,10 @@ protected void setContentViewListenerField() {
 
 	protected void setScopeField() {
 		getRoboGuiceHolder().scope = getGeneratedClass().field(JMod.PRIVATE, classes().CONTEXT_SCOPE, "scope_");
-
 	}
 
+	// CHECKSTYLE:ON
+
 	@Override
 	public JMethod getInjectExtrasMethod() {
 		if (injectExtrasMethod == null) {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EBeanHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EBeanHolder.java
index 9830acd..60f455b 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EBeanHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EBeanHolder.java
@@ -71,14 +71,15 @@ protected void setContextRef() {
 		contextRef = getContextField();
 	}
 
+	@Override
 	protected void setInit() {
 		init = generatedClass.method(PRIVATE, processHolder.codeModel().VOID, "init_");
 	}
 
-    public void invokeInitInConstructor() {
-        JBlock constructorBody = constructor.body();
-        constructorBody.invoke(getInit());
-    }
+	public void invokeInitInConstructor() {
+		JBlock constructorBody = constructor.body();
+		constructorBody.invoke(getInit());
+	}
 
 	public void createFactoryMethod(boolean hasSingletonScope) {
 
@@ -100,7 +101,7 @@ public void createFactoryMethod(boolean hasSingletonScope) {
 					._then();
 			JVar previousNotifier = viewNotifierHelper.replacePreviousNotifierWithNull(creationBlock);
 			creationBlock.assign(instanceField, _new(generatedClass).arg(factoryMethodContextParam.invoke("getApplicationContext")));
-            creationBlock.invoke(instanceField, getInit());
+			creationBlock.invoke(instanceField, getInit());
 			viewNotifierHelper.resetPreviousNotifier(creationBlock, previousNotifier);
 
 			factoryMethodBody._return(instanceField);
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EComponentHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EComponentHolder.java
index 6faf5eb..0a6a5a9 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EComponentHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EComponentHolder.java
@@ -41,7 +41,7 @@
 
 public abstract class EComponentHolder extends BaseGeneratedClassHolder {
 
-    private static final String METHOD_MAIN_LOOPER = "getMainLooper";
+	private static final String METHOD_MAIN_LOOPER = "getMainLooper";
 
 	protected JExpression contextRef;
 	protected JMethod init;
@@ -133,8 +133,8 @@ public JVar getHandler() {
 
 	private void setHandler() {
 		JClass handlerClass = classes().HANDLER;
-        JClass looperClass = classes().LOOPER;
-        JInvocation arg = JExpr._new(handlerClass).arg(looperClass.staticInvoke(METHOD_MAIN_LOOPER));
-        handler = generatedClass.field(JMod.PRIVATE, handlerClass, "handler_", arg);
+		JClass looperClass = classes().LOOPER;
+		JInvocation arg = JExpr._new(handlerClass).arg(looperClass.staticInvoke(METHOD_MAIN_LOOPER));
+		handler = generatedClass.field(JMod.PRIVATE, handlerClass, "handler_", arg);
 	}
 }
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EComponentWithViewSupportHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EComponentWithViewSupportHolder.java
index 51c7c25..6482634 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EComponentWithViewSupportHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EComponentWithViewSupportHolder.java
@@ -15,20 +15,36 @@
  */
 package org.androidannotations.holder;
 
-import com.sun.codemodel.*;
+import static com.sun.codemodel.JExpr._new;
+import static com.sun.codemodel.JExpr._null;
+import static com.sun.codemodel.JExpr._this;
+import static com.sun.codemodel.JExpr.cast;
+import static com.sun.codemodel.JExpr.invoke;
+import static com.sun.codemodel.JMod.FINAL;
+import static com.sun.codemodel.JMod.PRIVATE;
+import static com.sun.codemodel.JMod.PUBLIC;
+
+import java.util.HashMap;
+import java.util.Map;
+
+import javax.lang.model.element.TypeElement;
+import javax.lang.model.type.TypeMirror;
+
 import org.androidannotations.api.view.HasViews;
 import org.androidannotations.api.view.OnViewChangedListener;
 import org.androidannotations.api.view.OnViewChangedNotifier;
 import org.androidannotations.helper.ViewNotifierHelper;
 import org.androidannotations.process.ProcessHolder;
 
-import javax.lang.model.element.TypeElement;
-import javax.lang.model.type.TypeMirror;
-import java.util.HashMap;
-import java.util.Map;
-
-import static com.sun.codemodel.JExpr.*;
-import static com.sun.codemodel.JMod.*;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JDefinedClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JFieldRef;
+import com.sun.codemodel.JInvocation;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JVar;
 
 public abstract class EComponentWithViewSupportHolder extends EComponentHolder {
 
@@ -41,8 +57,8 @@
 	protected JMethod findSupportFragmentById;
 	protected JMethod findNativeFragmentByTag;
 	protected JMethod findSupportFragmentByTag;
-	private HashMap<String, TextWatcherHolder> textWatcherHolders = new HashMap<String, TextWatcherHolder>();
-	private HashMap<String, OnSeekBarChangeListenerHolder> onSeekBarChangeListenerHolders = new HashMap<String, OnSeekBarChangeListenerHolder>();
+	private Map<String, TextWatcherHolder> textWatcherHolders = new HashMap<String, TextWatcherHolder>();
+	private Map<String, OnSeekBarChangeListenerHolder> onSeekBarChangeListenerHolders = new HashMap<String, OnSeekBarChangeListenerHolder>();
 
 	public EComponentWithViewSupportHolder(ProcessHolder processHolder, TypeElement annotatedElement) throws Exception {
 		super(processHolder, annotatedElement);
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EFragmentHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EFragmentHolder.java
index ca3ef7e..9572a91 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EFragmentHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EFragmentHolder.java
@@ -15,6 +15,29 @@
  */
 package org.androidannotations.holder;
 
+import static com.sun.codemodel.JExpr.FALSE;
+import static com.sun.codemodel.JExpr.TRUE;
+import static com.sun.codemodel.JExpr._new;
+import static com.sun.codemodel.JExpr._null;
+import static com.sun.codemodel.JExpr._super;
+import static com.sun.codemodel.JExpr.invoke;
+import static com.sun.codemodel.JExpr.ref;
+import static com.sun.codemodel.JMod.PRIVATE;
+import static com.sun.codemodel.JMod.PUBLIC;
+import static com.sun.codemodel.JMod.STATIC;
+
+import java.util.ArrayList;
+import java.util.List;
+
+import javax.lang.model.element.TypeElement;
+import javax.lang.model.type.TypeMirror;
+
+import org.androidannotations.annotations.EFragment;
+import org.androidannotations.helper.ActionBarSherlockHelper;
+import org.androidannotations.helper.AnnotationHelper;
+import org.androidannotations.helper.OrmLiteHelper;
+import org.androidannotations.process.ProcessHolder;
+
 import com.sun.codemodel.JBlock;
 import com.sun.codemodel.JClass;
 import com.sun.codemodel.JClassAlreadyExistsException;
@@ -28,29 +51,6 @@
 import com.sun.codemodel.JTypeVar;
 import com.sun.codemodel.JVar;
 
-import org.androidannotations.annotations.EFragment;
-import org.androidannotations.helper.ActionBarSherlockHelper;
-import org.androidannotations.helper.AnnotationHelper;
-import org.androidannotations.helper.OrmLiteHelper;
-import org.androidannotations.process.ProcessHolder;
-
-import java.util.ArrayList;
-import java.util.List;
-
-import javax.lang.model.element.TypeElement;
-import javax.lang.model.type.TypeMirror;
-
-import static com.sun.codemodel.JExpr.FALSE;
-import static com.sun.codemodel.JExpr.TRUE;
-import static com.sun.codemodel.JExpr._new;
-import static com.sun.codemodel.JExpr._null;
-import static com.sun.codemodel.JExpr._super;
-import static com.sun.codemodel.JExpr.invoke;
-import static com.sun.codemodel.JExpr.ref;
-import static com.sun.codemodel.JMod.PRIVATE;
-import static com.sun.codemodel.JMod.PUBLIC;
-import static com.sun.codemodel.JMod.STATIC;
-
 public class EFragmentHolder extends EComponentWithViewSupportHolder implements HasInstanceState, HasOptionsMenu, HasOnActivityResult, HasReceiverRegistration {
 
 	private JFieldVar contentView;
@@ -256,8 +256,9 @@ private void setOnCreateView() {
 
 		JBlock body = onCreateView.body();
 
-		if (!forceInjection)
+		if (!forceInjection) {
 			body.assign(contentView, _super().invoke(onCreateView).arg(inflater).arg(container).arg(savedInstanceState));
+		}
 
 		setContentViewBlock = body.block();
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EIntentServiceHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EIntentServiceHolder.java
index 774239f..c64ccae 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EIntentServiceHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EIntentServiceHolder.java
@@ -30,50 +30,50 @@
 
 public class EIntentServiceHolder extends EServiceHolder {
 
-    private JVar onHandleIntentIntent;
-    private JMethod onHandleIntentMethod;
-    private JBlock onHandleIntentBody;
-    private JVar onHandleIntentIntentAction;
+	private JVar onHandleIntentIntent;
+	private JMethod onHandleIntentMethod;
+	private JBlock onHandleIntentBody;
+	private JVar onHandleIntentIntentAction;
 
-    public EIntentServiceHolder(ProcessHolder processHolder, TypeElement annotatedElement, AndroidManifest androidManifest) throws Exception {
-        super(processHolder, annotatedElement, androidManifest);
-    }
+	public EIntentServiceHolder(ProcessHolder processHolder, TypeElement annotatedElement, AndroidManifest androidManifest) throws Exception {
+		super(processHolder, annotatedElement, androidManifest);
+	}
 
-    public JVar getOnHandleIntentIntent() {
-        if (onHandleIntentIntent == null) {
-            createOnHandleIntent();
-        }
-        return onHandleIntentIntent;
-    }
+	public JVar getOnHandleIntentIntent() {
+		if (onHandleIntentIntent == null) {
+			createOnHandleIntent();
+		}
+		return onHandleIntentIntent;
+	}
 
-    public JMethod getOnHandleIntentMethod() {
-        if (onHandleIntentMethod == null) {
-            createOnHandleIntent();
-        }
-        return onHandleIntentMethod;
-    }
+	public JMethod getOnHandleIntentMethod() {
+		if (onHandleIntentMethod == null) {
+			createOnHandleIntent();
+		}
+		return onHandleIntentMethod;
+	}
 
-    public JBlock getOnHandleIntentBody() {
-        if (onHandleIntentBody == null) {
-            createOnHandleIntent();
-        }
-        return onHandleIntentBody;
-    }
+	public JBlock getOnHandleIntentBody() {
+		if (onHandleIntentBody == null) {
+			createOnHandleIntent();
+		}
+		return onHandleIntentBody;
+	}
 
-    public JVar getOnHandleIntentIntentAction() {
-        if (onHandleIntentIntentAction == null) {
-            createOnHandleIntent();
-        }
-        return onHandleIntentIntentAction;
-    }
+	public JVar getOnHandleIntentIntentAction() {
+		if (onHandleIntentIntentAction == null) {
+			createOnHandleIntent();
+		}
+		return onHandleIntentIntentAction;
+	}
 
-    private void createOnHandleIntent() {
-        onHandleIntentMethod = generatedClass.method(PUBLIC, codeModel().VOID, "onHandleIntent");
-        onHandleIntentIntent = onHandleIntentMethod.param(classes().INTENT, "intent");
-        onHandleIntentMethod.annotate(Override.class);
-        onHandleIntentBody = onHandleIntentMethod.body();
-        codeModelHelper.callSuperMethod(onHandleIntentMethod, this, onHandleIntentBody);
-        JInvocation getActionInvocation = JExpr.invoke(onHandleIntentIntent, "getAction");
-        onHandleIntentIntentAction = onHandleIntentBody.decl(classes().STRING, "action", getActionInvocation);
-    }
+	private void createOnHandleIntent() {
+		onHandleIntentMethod = generatedClass.method(PUBLIC, codeModel().VOID, "onHandleIntent");
+		onHandleIntentIntent = onHandleIntentMethod.param(classes().INTENT, "intent");
+		onHandleIntentMethod.annotate(Override.class);
+		onHandleIntentBody = onHandleIntentMethod.body();
+		codeModelHelper.callSuperMethod(onHandleIntentMethod, this, onHandleIntentBody);
+		JInvocation getActionInvocation = JExpr.invoke(onHandleIntentIntent, "getAction");
+		onHandleIntentIntentAction = onHandleIntentBody.decl(classes().STRING, "action", getActionInvocation);
+	}
 }
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EServiceHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EServiceHolder.java
index eb0ebf6..b420384 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EServiceHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EServiceHolder.java
@@ -15,23 +15,28 @@
  */
 package org.androidannotations.holder;
 
-import com.sun.codemodel.*;
+import static com.sun.codemodel.JExpr._this;
+import static com.sun.codemodel.JMod.PRIVATE;
+import static com.sun.codemodel.JMod.PUBLIC;
+
+import javax.lang.model.element.TypeElement;
+
 import org.androidannotations.helper.AndroidManifest;
 import org.androidannotations.helper.IntentBuilder;
 import org.androidannotations.helper.OrmLiteHelper;
 import org.androidannotations.helper.ServiceIntentBuilder;
 import org.androidannotations.process.ProcessHolder;
 
-import javax.lang.model.element.TypeElement;
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JDefinedClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JFieldVar;
+import com.sun.codemodel.JMethod;
 import javax.lang.model.type.TypeMirror;
 
-import static com.sun.codemodel.JExpr._this;
-import static com.sun.codemodel.JMod.PRIVATE;
-import static com.sun.codemodel.JMod.PUBLIC;
-
 public class EServiceHolder extends EComponentHolder implements HasIntentBuilder, HasReceiverRegistration {
 
-    private ServiceIntentBuilder intentBuilder;
+	private ServiceIntentBuilder intentBuilder;
 	private JDefinedClass intentBuilderClass;
 	private ReceiverRegistrationHolder receiverRegistrationHolder;
 	private JBlock onDestroyBeforeSuperBlock;
@@ -39,16 +44,16 @@
 	public EServiceHolder(ProcessHolder processHolder, TypeElement annotatedElement, AndroidManifest androidManifest) throws Exception {
 		super(processHolder, annotatedElement);
 		receiverRegistrationHolder = new ReceiverRegistrationHolder(this);
-        intentBuilder = new ServiceIntentBuilder(this, androidManifest);
-        intentBuilder.build();
+		intentBuilder = new ServiceIntentBuilder(this, androidManifest);
+		intentBuilder.build();
 	}
 
-    @Override
-    public IntentBuilder getIntentBuilder() {
-        return intentBuilder;
-    }
+	@Override
+	public IntentBuilder getIntentBuilder() {
+		return intentBuilder;
+	}
 
-    @Override
+	@Override
 	protected void setContextRef() {
 		contextRef = _this();
 	}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EViewHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EViewHolder.java
index a0111ee..c710a5b 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EViewHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/EViewHolder.java
@@ -50,15 +50,13 @@
 			+ "The mAlreadyInflated_ hack is needed because of an Android bug\n" // +
 			+ "which leads to infinite calls of onFinishInflate()\n" //
 			+ "when inflating a layout with a parent and using\n" //
-			+ "the <merge /> tag." //
-	;
+			+ "the <merge /> tag.";
 
 	private static final String SUPPRESS_WARNING_COMMENT = "" //
 			+ "We use @SuppressWarning here because our java code\n" //
 			+ "generator doesn't know that there is no need\n" //
 			+ "to import OnXXXListeners from View as we already\n" //
-			+ "are in a View." //
-	;
+			+ "are in a View.";
 
 	protected JBlock initBody;
 	protected JMethod onFinishInflate;
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/FoundViewHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/FoundViewHolder.java
index e115d35..a9ae23b 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/FoundViewHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/FoundViewHolder.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.holder;
 
+import static com.sun.codemodel.JExpr._null;
+import static com.sun.codemodel.JExpr.cast;
+
 import com.sun.codemodel.JBlock;
 import com.sun.codemodel.JClass;
 import com.sun.codemodel.JExpression;
 
-import static com.sun.codemodel.JExpr._null;
-import static com.sun.codemodel.JExpr.cast;
-
 public class FoundViewHolder {
 
 	private GeneratedClassHolder holder;
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/HasIntentBuilder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/HasIntentBuilder.java
index 1d5edcf..dba4aba 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/HasIntentBuilder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/HasIntentBuilder.java
@@ -15,14 +15,15 @@
  */
 package org.androidannotations.holder;
 
-import com.sun.codemodel.JDefinedClass;
 import org.androidannotations.helper.IntentBuilder;
 
+import com.sun.codemodel.JDefinedClass;
+
 public interface HasIntentBuilder extends GeneratedClassHolder {
 
-    public IntentBuilder getIntentBuilder();
+	IntentBuilder getIntentBuilder();
 
-	public void setIntentBuilderClass(JDefinedClass intentBuilderClass);
+	void setIntentBuilderClass(JDefinedClass intentBuilderClass);
 
-	public JDefinedClass getIntentBuilderClass();
+	JDefinedClass getIntentBuilderClass();
 }
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/OnActivityResultHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/OnActivityResultHolder.java
index 3900e57..5fefd24 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/OnActivityResultHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/OnActivityResultHolder.java
@@ -15,6 +15,13 @@
  */
 package org.androidannotations.holder;
 
+import static com.sun.codemodel.JExpr._super;
+
+import java.util.HashMap;
+import java.util.Map;
+
+import org.androidannotations.process.ProcessHolder;
+
 import com.sun.codemodel.JBlock;
 import com.sun.codemodel.JCase;
 import com.sun.codemodel.JCodeModel;
@@ -23,11 +30,6 @@
 import com.sun.codemodel.JMod;
 import com.sun.codemodel.JSwitch;
 import com.sun.codemodel.JVar;
-import org.androidannotations.process.ProcessHolder;
-
-import java.util.HashMap;
-
-import static com.sun.codemodel.JExpr._super;
 
 public class OnActivityResultHolder {
 
@@ -38,7 +40,7 @@
 	private JVar requestCodeParam;
 	private JVar dataParam;
 	private JVar resultCodeParam;
-	private HashMap<Integer, JBlock> caseBlocks = new HashMap<Integer, JBlock>();
+	private Map<Integer, JBlock> caseBlocks = new HashMap<Integer, JBlock>();
 
 	public OnActivityResultHolder(EComponentHolder holder) {
 		this.holder = holder;
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/ReceiverRegistrationHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/ReceiverRegistrationHolder.java
index d787d55..2f7616f 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/ReceiverRegistrationHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/ReceiverRegistrationHolder.java
@@ -15,16 +15,24 @@
  */
 package org.androidannotations.holder;
 
-import com.sun.codemodel.*;
-import org.androidannotations.helper.ModelConstants;
-import org.androidannotations.process.ProcessHolder;
-
-import java.util.*;
-
 import static com.sun.codemodel.JExpr._new;
 import static com.sun.codemodel.JMod.FINAL;
 import static com.sun.codemodel.JMod.PRIVATE;
 
+import java.util.Arrays;
+import java.util.HashMap;
+import java.util.HashSet;
+import java.util.Map;
+import java.util.Set;
+
+import org.androidannotations.helper.ModelConstants;
+import org.androidannotations.process.ProcessHolder;
+
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JDefinedClass;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JFieldVar;
+
 public class ReceiverRegistrationHolder {
 
 	private EComponentHolder holder;
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/RoboGuiceHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/RoboGuiceHolder.java
index 615c64b..a6e8a53 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/RoboGuiceHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/RoboGuiceHolder.java
@@ -23,6 +23,9 @@
 public class RoboGuiceHolder {
 
 	private EActivityHolder holder;
+
+	// TODO access for these fields should be refactored
+
 	protected JFieldVar scopedObjects;
 	protected JFieldVar scope;
 	protected JFieldVar eventManager;
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/SharedPrefHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/SharedPrefHolder.java
index 36f9b98..38d3f2c 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/SharedPrefHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/holder/SharedPrefHolder.java
@@ -15,7 +15,6 @@
  */
 package org.androidannotations.holder;
 
-import com.sun.codemodel.*;
 import static com.sun.codemodel.JMod.FINAL;
 import static com.sun.codemodel.JMod.PUBLIC;
 import static com.sun.codemodel.JMod.STATIC;
@@ -38,6 +37,18 @@
 import org.androidannotations.helper.ModelConstants;
 import org.androidannotations.process.ProcessHolder;
 
+import com.sun.codemodel.JBlock;
+import com.sun.codemodel.JClass;
+import com.sun.codemodel.JClassAlreadyExistsException;
+import com.sun.codemodel.JDefinedClass;
+import com.sun.codemodel.JExpr;
+import com.sun.codemodel.JExpression;
+import com.sun.codemodel.JFieldVar;
+import com.sun.codemodel.JInvocation;
+import com.sun.codemodel.JMethod;
+import com.sun.codemodel.JMod;
+import com.sun.codemodel.JVar;
+
 public class SharedPrefHolder extends BaseGeneratedClassHolder {
 
 	private static class EditorFieldHolder {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/logger/LoggerContext.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/logger/LoggerContext.java
index 4b2f7dc..0bdfbce 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/logger/LoggerContext.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/logger/LoggerContext.java
@@ -31,21 +31,21 @@
 
 public class LoggerContext {
 
-	private static LoggerContext INSTANCE = null;
+	private static LoggerContext instance = null;
 	private static final Level DEFAULT_LEVEL = Level.DEBUG;
 
 	private Level currentLevel = DEFAULT_LEVEL;
 	private List<Appender> appenders = new ArrayList<Appender>();
 
 	public static LoggerContext getInstance() {
-		if (INSTANCE == null) {
+		if (instance == null) {
 			synchronized (LoggerContext.class) {
-				if (INSTANCE == null) {
-					INSTANCE = new LoggerContext();
+				if (instance == null) {
+					instance = new LoggerContext();
 				}
 			}
 		}
-		return INSTANCE;
+		return instance;
 	}
 
 	LoggerContext() {
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/logger/LoggerFactory.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/logger/LoggerFactory.java
index 9513668..91447f7 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/logger/LoggerFactory.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/logger/LoggerFactory.java
@@ -22,6 +22,9 @@
 
 	private static final Map<String, Logger> LOGGER_CACHE = new HashMap<String, Logger>();
 
+	private LoggerFactory() {
+	}
+
 	public static Logger getLogger(Class<?> clazz) {
 		return getLogger(simpleLoggerName(clazz));
 	}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/logger/appender/FileAppender.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/logger/appender/FileAppender.java
index c16b9ef..6b87bf0 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/logger/appender/FileAppender.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/logger/appender/FileAppender.java
@@ -15,20 +15,21 @@
  */
 package org.androidannotations.logger.appender;
 
-import org.androidannotations.helper.FileHelper;
-import org.androidannotations.logger.Level;
-import org.androidannotations.logger.LoggerContext;
-import org.androidannotations.logger.formatter.FormatterFull;
+import java.io.File;
+import java.io.FileNotFoundException;
+import java.io.FileOutputStream;
+import java.io.IOException;
 
 import javax.annotation.processing.Messager;
 import javax.annotation.processing.ProcessingEnvironment;
 import javax.lang.model.element.AnnotationMirror;
 import javax.lang.model.element.Element;
 import javax.tools.Diagnostic.Kind;
-import java.io.File;
-import java.io.FileNotFoundException;
-import java.io.FileOutputStream;
-import java.io.IOException;
+
+import org.androidannotations.helper.FileHelper;
+import org.androidannotations.logger.Level;
+import org.androidannotations.logger.LoggerContext;
+import org.androidannotations.logger.formatter.FormatterFull;
 
 public class FileAppender extends Appender {
 
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/process/ModelProcessor.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/process/ModelProcessor.java
index ae35123..68f7bbc 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/process/ModelProcessor.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/process/ModelProcessor.java
@@ -15,7 +15,15 @@
  */
 package org.androidannotations.process;
 
-import com.sun.codemodel.JCodeModel;
+import java.util.Set;
+
+import javax.annotation.processing.ProcessingEnvironment;
+import javax.lang.model.element.Element;
+import javax.lang.model.element.ElementKind;
+import javax.lang.model.element.Modifier;
+import javax.lang.model.element.NestingKind;
+import javax.lang.model.element.TypeElement;
+
 import org.androidannotations.exception.ProcessingException;
 import org.androidannotations.handler.AnnotationHandler;
 import org.androidannotations.handler.AnnotationHandlers;
@@ -26,10 +34,7 @@
 import org.androidannotations.model.AnnotationElements;
 import org.androidannotations.model.AnnotationElements.AnnotatedAndRootElements;
 
-import javax.annotation.processing.ProcessingEnvironment;
-import javax.lang.model.element.*;
-import java.util.Set;
-
+import com.sun.codemodel.JCodeModel;
 
 public class ModelProcessor {
 
@@ -69,10 +74,14 @@ public ProcessResult process(AnnotationElements validatedModel) throws Processin
 		LOGGER.info("Processing root elements");
 
 		/*
-		 * We generate top classes then inner classes, then inner classes of inner classes, etc...
-		 * until there is no more classes to generate.
+		 * We generate top classes then inner classes, then inner classes of
+		 * inner classes, etc... until there is no more classes to generate.
 		 */
-		while (generateElements(validatedModel,processHolder));
+		while (generateElements(validatedModel, processHolder)) {
+			// CHECKSTYLE:OFF
+			;
+			// CHECKSTYLE:ON
+		}
 
 		LOGGER.info("Processing enclosed elements");
 
@@ -179,7 +188,7 @@ private boolean generateElements(AnnotationElements validatedModel, ProcessHolde
 							generatingAnnotationHandler.process(annotatedElement, generatedClassHolder);
 						}
 					}
-				}  else {
+				} else {
 					LOGGER.trace("Skip element {} because it's abstract", annotatedElement);
 				}
 			}
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/process/ProcessHolder.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/process/ProcessHolder.java
index c9be761..d371029 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/process/ProcessHolder.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/process/ProcessHolder.java
@@ -41,6 +41,7 @@
 
 public class ProcessHolder {
 
+	// CHECKSTYLE:OFF
 	public class Classes {
 
 		/*
@@ -184,6 +185,8 @@
 		public final JClass NESTED_RUNTIME_EXCEPTION = refClass(CanonicalNameConstants.NESTED_RUNTIME_EXCEPTION);
 	}
 
+	// CHECKSTYLE:ON
+
 	private final Map<Element, GeneratedClassHolder> generatedClassHolders = new HashMap<Element, GeneratedClassHolder>();
 
 	private final ProcessingEnvironment processingEnvironment;
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/rclass/IRInnerClass.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/rclass/IRInnerClass.java
index 6a3dd18..68edc16 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/rclass/IRInnerClass.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/rclass/IRInnerClass.java
@@ -33,6 +33,6 @@
 
 	JFieldRef getIdStaticRef(String name, ProcessHolder holder);
 
-	final IRInnerClass EMPTY_R_INNER_CLASS = new RInnerClass(null);
+	IRInnerClass EMPTY_R_INNER_CLASS = new RInnerClass(null);
 
 }
diff --git a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/rclass/ManifestPackageExtractor.java b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/rclass/ManifestPackageExtractor.java
index 15bf75c..b8f92a6 100644
--- a/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/rclass/ManifestPackageExtractor.java
+++ b/AndroidAnnotations/androidannotations/src/main/java/org/androidannotations/rclass/ManifestPackageExtractor.java
@@ -20,13 +20,13 @@
 
 public class ManifestPackageExtractor {
 
-	private static final Pattern pattern = Pattern.compile("package\\s*=\\s*\"([^\"]+)\"");
+	private static final Pattern PATTERN = Pattern.compile("package\\s*=\\s*\"([^\"]+)\"");
 	private Matcher matcher;
 	private boolean matches;
 
 	public ManifestPackageExtractor(String manifestLine) {
 		if (manifestLine != null) {
-			matcher = pattern.matcher(manifestLine);
+			matcher = PATTERN.matcher(manifestLine);
 			matches = matcher.find();
 		} else {
 			matches = false;
diff --git a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/ebean/SomeGenericBean.java b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/ebean/SomeGenericBean.java
index b16e608..267e824 100644
--- a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/ebean/SomeGenericBean.java
+++ b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/ebean/SomeGenericBean.java
@@ -15,12 +15,12 @@
  */
 package org.androidannotations.ebean;
 
+import java.util.List;
+
 import org.androidannotations.annotations.Background;
 import org.androidannotations.annotations.EBean;
 import org.androidannotations.annotations.UiThread;
 
-import java.util.List;
-
 @EBean
 public class SomeGenericBean<T> {
 
diff --git a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/eviewgroup/SomeGenericViewGroup.java b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/eviewgroup/SomeGenericViewGroup.java
index 34731ae..6186dc4 100644
--- a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/eviewgroup/SomeGenericViewGroup.java
+++ b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/eviewgroup/SomeGenericViewGroup.java
@@ -15,11 +15,12 @@
  */
 package org.androidannotations.eviewgroup;
 
-import android.content.Context;
-import android.widget.FrameLayout;
 import org.androidannotations.annotations.EViewGroup;
 import org.androidannotations.annotations.UiThread;
 
+import android.content.Context;
+import android.widget.FrameLayout;
+
 @EViewGroup
 public class SomeGenericViewGroup<T extends CharSequence> extends FrameLayout {
 
diff --git a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/eviewgroup/SomeGenericViewGroupExt.java b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/eviewgroup/SomeGenericViewGroupExt.java
index 942f9a2..19aa22a 100644
--- a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/eviewgroup/SomeGenericViewGroupExt.java
+++ b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/eviewgroup/SomeGenericViewGroupExt.java
@@ -15,10 +15,11 @@
  */
 package org.androidannotations.eviewgroup;
 
-import android.content.Context;
 import org.androidannotations.annotations.EViewGroup;
 import org.androidannotations.annotations.UiThread;
 
+import android.content.Context;
+
 @EViewGroup
 public class SomeGenericViewGroupExt extends SomeGenericViewGroup<String> {
 
diff --git a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ActivityWithInvalidRegisterAt.java b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ActivityWithInvalidRegisterAt.java
index 64547fd..a63a496 100644
--- a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ActivityWithInvalidRegisterAt.java
+++ b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ActivityWithInvalidRegisterAt.java
@@ -15,10 +15,11 @@
  */
 package org.androidannotations.receiver;
 
-import android.app.Activity;
 import org.androidannotations.annotations.EActivity;
 import org.androidannotations.annotations.Receiver;
 
+import android.app.Activity;
+
 @EActivity
 public class ActivityWithInvalidRegisterAt extends Activity {
 
diff --git a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ActivityWithTwoSameNameMethod.java b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ActivityWithTwoSameNameMethod.java
index c59a643..0e23c82 100644
--- a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ActivityWithTwoSameNameMethod.java
+++ b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ActivityWithTwoSameNameMethod.java
@@ -15,11 +15,12 @@
  */
 package org.androidannotations.receiver;
 
-import android.app.Activity;
-import android.content.Intent;
 import org.androidannotations.annotations.EActivity;
 import org.androidannotations.annotations.Receiver;
 
+import android.app.Activity;
+import android.content.Intent;
+
 @EActivity
 public class ActivityWithTwoSameNameMethod extends Activity {
 
diff --git a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ActivityWithValidReceiver.java b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ActivityWithValidReceiver.java
index 3041c22..2f64896 100644
--- a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ActivityWithValidReceiver.java
+++ b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ActivityWithValidReceiver.java
@@ -15,11 +15,12 @@
  */
 package org.androidannotations.receiver;
 
-import android.app.Activity;
-import android.content.Intent;
 import org.androidannotations.annotations.EActivity;
 import org.androidannotations.annotations.Receiver;
 
+import android.app.Activity;
+import android.content.Intent;
+
 @EActivity
 public class ActivityWithValidReceiver extends Activity {
 
diff --git a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/FragmentWithValidReceiver.java b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/FragmentWithValidReceiver.java
index 2bbc47c..0af7f54 100644
--- a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/FragmentWithValidReceiver.java
+++ b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/FragmentWithValidReceiver.java
@@ -15,11 +15,12 @@
  */
 package org.androidannotations.receiver;
 
-import android.app.Fragment;
-import android.content.Intent;
 import org.androidannotations.annotations.EFragment;
 import org.androidannotations.annotations.Receiver;
 
+import android.app.Fragment;
+import android.content.Intent;
+
 @EFragment
 public class FragmentWithValidReceiver extends Fragment {
 
diff --git a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ReceiverRegistrationTest.java b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ReceiverRegistrationTest.java
index 9e900b0..fba9a26 100644
--- a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ReceiverRegistrationTest.java
+++ b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ReceiverRegistrationTest.java
@@ -15,13 +15,13 @@
  */
 package org.androidannotations.receiver;
 
+import java.io.IOException;
+
 import org.androidannotations.AndroidAnnotationProcessor;
 import org.androidannotations.utils.AAProcessorTestHelper;
 import org.junit.Before;
 import org.junit.Test;
 
-import java.io.IOException;
-
 public class ReceiverRegistrationTest extends AAProcessorTestHelper {
 
 	@Before
diff --git a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ServiceWithInvalidReceiver.java b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ServiceWithInvalidReceiver.java
index f1ef55c..d2462ce 100644
--- a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ServiceWithInvalidReceiver.java
+++ b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ServiceWithInvalidReceiver.java
@@ -15,11 +15,12 @@
  */
 package org.androidannotations.receiver;
 
+import org.androidannotations.annotations.EService;
+import org.androidannotations.annotations.Receiver;
+
 import android.app.Service;
 import android.content.Intent;
 import android.os.IBinder;
-import org.androidannotations.annotations.EService;
-import org.androidannotations.annotations.Receiver;
 
 @EService
 public class ServiceWithInvalidReceiver extends Service {
diff --git a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ServiceWithValidReceiver.java b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ServiceWithValidReceiver.java
index 4b7a1ee..1147075 100644
--- a/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ServiceWithValidReceiver.java
+++ b/AndroidAnnotations/androidannotations/src/test/java/org/androidannotations/receiver/ServiceWithValidReceiver.java
@@ -15,11 +15,12 @@
  */
 package org.androidannotations.receiver;
 
+import org.androidannotations.annotations.EService;
+import org.androidannotations.annotations.Receiver;
+
 import android.app.Service;
 import android.content.Intent;
 import android.os.IBinder;
-import org.androidannotations.annotations.EService;
-import org.androidannotations.annotations.Receiver;
 
 @EService
 public class ServiceWithValidReceiver extends Service {
diff --git a/AndroidAnnotations/checkstyle-checks.xml b/AndroidAnnotations/checkstyle-checks.xml
new file mode 100644
index 0000000..cf3d012
--- /dev/null
+++ b/AndroidAnnotations/checkstyle-checks.xml
@@ -0,0 +1,117 @@
+<?xml version="1.0" encoding="UTF-8"?>
+<!DOCTYPE module PUBLIC "-//Puppy Crawl//DTD Check Configuration 1.3//EN" "http://www.puppycrawl.com/dtds/configuration_1_3.dtd">
+
+<module name="Checker">
+	<property name="severity" value="error" />
+	<module name="TreeWalker">
+		<property name="tabWidth" value="4" />
+		<module name="FileContentsHolder" />
+		<module name="AnnotationUseStyle" />
+		<module name="ClassTypeParameterName" />
+		<module name="ConstantName" />
+		<module name="LocalVariableName" />
+		<module name="MemberName" />
+		<module name="MethodName" />
+		<module name="MethodTypeParameterName" />
+		<module name="PackageName" />
+		<module name="ParameterName" />
+		<module name="StaticVariableName" />
+		<module name="TypeName" />
+		<module name="MissingDeprecated" />
+		<module name="MissingOverride" />
+		<module name="AvoidStarImport" />
+		<module name="IllegalImport" />
+		<!-- unfortunetaly could not synchronize with eclipse -->
+		<!-- <module name="ImportOrder"> <property name="separated" value="true"/> 
+			<property name="groups" value="java,javax,/com\..*/,/org\..*/,android"/> 
+			<property name="option" value="top"/> </module> -->
+		<module name="RedundantImport" />
+		<module name="UnusedImports" />
+
+		<module name="MethodLength" />
+		<module name="ParameterNumber" />
+		<module name="OuterTypeNumber" />
+		<module name="MethodCount" />
+		<module name="EmptyForInitializerPad" />
+		<module name="EmptyForIteratorPad" />
+		<module name="NoWhitespaceAfter">
+			<property name="tokens"
+				value="BNOT, DEC, DOT, INC, LNOT, UNARY_MINUS, UNARY_PLUS" />
+		</module>
+		<module name="NoWhitespaceBefore" />
+		<module name="OperatorWrap" />
+		<module name="MethodParamPad" />
+		<module name="ParenPad" />
+		<module name="TypecastParenPad" />
+		<module name="WhitespaceAfter" />
+		<module name="WhitespaceAround">
+			<property name="tokens"
+				value="ASSIGN, BAND, BAND_ASSIGN, BOR, BOR_ASSIGN, BSR, BSR_ASSIGN, BXOR, BXOR_ASSIGN, COLON, DIV, DIV_ASSIGN, DO_WHILE, EQUAL, GE, GT, LAND, LCURLY, LE, LITERAL_ASSERT, LITERAL_CATCH, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, LITERAL_IF, LITERAL_RETURN, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, LOR, LT, MINUS, MINUS_ASSIGN, MOD, MOD_ASSIGN, NOT_EQUAL, PLUS, PLUS_ASSIGN, QUESTION, SL, SLIST, SL_ASSIGN, SR, SR_ASSIGN, STAR, STAR_ASSIGN, TYPE_EXTENSION_AND" />
+		</module>
+		<module name="RedundantModifier" />
+		<module name="AvoidNestedBlocks">
+			<property name="allowInSwitchCase" value="true" />
+		</module>
+		<module name="LeftCurly">
+			<property name="maxLineLength" value="130" />
+		</module>
+		<module name="NeedBraces" />
+		<module name="RightCurly" />
+		<module name="CovariantEquals" />
+		<module name="DefaultComesLast" />
+		<module name="EmptyStatement" />
+		<module name="EqualsHashCode" />
+		<module name="FallThrough" />
+		<module name="IllegalThrows" />
+		<module name="IllegalType">
+			<property name="illegalClassNames	"
+				value="java.util.GregorianCalendar, java.util.Hashtable, java.util.HashSet, java.util.HashMap, java.util.LinkedList, java.util.LinkedHashMap, java.util.LinkedHashSet, java.util.TreeSet, java.util.TreeMap, java.util.Vector" />
+		</module>
+		<module name="InnerAssignment" />
+		<module name="JUnitTestCase" />
+		<module name="ModifiedControlVariable" />
+		<module name="MultipleVariableDeclarations" />
+		<module name="NestedForDepth">
+			<property name="max" value="2" />
+		</module>
+		<module name="NestedIfDepth"> <!-- this is too high, we should refactor those parts -->
+			<property name="max" value="5" />
+		</module>
+		<module name="NestedTryDepth" />
+		<module name="NoClone" />
+		<module name="NoFinalizer" />
+		<module name="PackageDeclaration" />
+		<module name="SimplifyBooleanExpression" />
+		<module name="SimplifyBooleanReturn" />
+		<module name="StringLiteralEquality" />
+		<module name="ArrayTrailingComma" />
+		<module name="UnnecessaryParentheses" />
+		<module name="OneStatementPerLine" />
+		<module name="HideUtilityClassConstructor" />
+		<module name="InterfaceIsType" />
+		<module name="MutableException" />
+		<module name="ThrowsCount">
+			<property name="max" value="2" />
+		</module>
+		<!-- we should really enable the following -->
+		<!-- <module name="VisibilityModifier"> <property name="packageAllowed" 
+			value="true"/> <property name="protectedAllowed" value="true"/> </module> -->
+		<module name="ArrayTypeStyle" />
+		<module name="Indentation">
+			<property name="caseIndent" value="0" />
+		</module>
+
+		<module name="RegexpSinglelineJava">
+			<property name="format" value="^\t* +\t*\S" />
+			<property name="message"
+				value="Line has leading space characters; indentation should be performed with tabs only." />
+			<property name="ignoreComments" value="true" />
+		</module>
+		<module name="UpperEll" />
+		<module name="OuterTypeFilename" />
+		<module name="OuterTypeNumber" />
+	</module>
+	<module name="FileLength" />
+	<module name="SuppressionCommentFilter">
+	</module>
+</module>
diff --git a/AndroidAnnotations/functional-test-1-5/pom.xml b/AndroidAnnotations/functional-test-1-5/pom.xml
index bd5d12f..c1aa32c 100644
--- a/AndroidAnnotations/functional-test-1-5/pom.xml
+++ b/AndroidAnnotations/functional-test-1-5/pom.xml
@@ -15,6 +15,11 @@
 	<properties>
 		<jackson-version>1.9.11</jackson-version>
 		<simple-version>2.7.1</simple-version>
+		<spring-android-version>1.0.0.RELEASE</spring-android-version>
+		<jackson-version>1.9.6</jackson-version>
+		<simple-version>2.4.1</simple-version>
+		<android-rome-version>1.0.0-r2</android-rome-version>
+		<checkstyle.excludes>**/R.java,**/BuildConfig.java,</checkstyle.excludes> 
 	</properties>
 	
 	<dependencies>
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/com/actionbarsherlock/app/SherlockFragment.java b/AndroidAnnotations/functional-test-1-5/src/main/java/com/actionbarsherlock/app/SherlockFragment.java
index 8ad350c..c1e7d9e 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/com/actionbarsherlock/app/SherlockFragment.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/com/actionbarsherlock/app/SherlockFragment.java
@@ -21,38 +21,37 @@
 import com.actionbarsherlock.view.MenuInflater;
 import com.actionbarsherlock.view.MenuItem;
 
-public class SherlockFragment extends Fragment{
-
-	
-	  @Override
-	    public final void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater inflater) {
-	        onCreateOptionsMenu((Menu) null, null);
-	    }
-
-	    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
-	    }
-	    
-	    @Override
-	    public final void onPrepareOptionsMenu(android.view.Menu menu) {
-	        onPrepareOptionsMenu((Menu) null);
-	    }
-
-	    public void onPrepareOptionsMenu(Menu menu) {
-	    }
-
-	    @Override
-	    public final boolean onOptionsItemSelected(final android.view.MenuItem item) {
-	        return onOptionsItemSelected(new MenuItem() {
-				
-				@Override
-				public int getItemId() {
-					return item.getItemId();
-				}
-			});
-	    }
-
-	    public boolean onOptionsItemSelected(MenuItem item) {
-	        return false;
-	    }
-	
+public class SherlockFragment extends Fragment {
+
+	@Override
+	public final void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater inflater) {
+		onCreateOptionsMenu((Menu) null, null);
+	}
+
+	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
+	}
+
+	@Override
+	public final void onPrepareOptionsMenu(android.view.Menu menu) {
+		onPrepareOptionsMenu((Menu) null);
+	}
+
+	public void onPrepareOptionsMenu(Menu menu) {
+	}
+
+	@Override
+	public final boolean onOptionsItemSelected(final android.view.MenuItem item) {
+		return onOptionsItemSelected(new MenuItem() {
+
+			@Override
+			public int getItemId() {
+				return item.getItemId();
+			}
+		});
+	}
+
+	public boolean onOptionsItemSelected(MenuItem item) {
+		return false;
+	}
+
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/com/actionbarsherlock/view/Menu.java b/AndroidAnnotations/functional-test-1-5/src/main/java/com/actionbarsherlock/view/Menu.java
index e0197f1..7b41bd4 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/com/actionbarsherlock/view/Menu.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/com/actionbarsherlock/view/Menu.java
@@ -17,6 +17,6 @@
 
 public interface Menu {
 
-	public MenuItem findItem(int id);
-	
+	MenuItem findItem(int id);
+
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ActivityWithGenerics.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ActivityWithGenerics.java
index f73a683..7a12483 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ActivityWithGenerics.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ActivityWithGenerics.java
@@ -26,22 +26,23 @@
 @EActivity
 public class ActivityWithGenerics extends Activity {
 
-    //@UiThread
-    //<T, S extends Number & List<String>> void emptyUiMethod(T param, S param2) {
-    //}
+	// @UiThread
+	// <T, S extends Number & List<String>> void emptyUiMethod(T param, S
+	// param2) {
+	// }
 
-    @UiThread
-    <T, S extends Number> void emptyUiMethod(List<? extends T> param, List<? super S> param2) {
-    }
+	@UiThread
+	<T, S extends Number> void emptyUiMethod(List<? extends T> param, List<? super S> param2) {
+	}
 
 	@UiThread
 	<T, S extends Number> void emptyUiMethod(T param) {
 
 	}
-	
+
 	@Background
 	<T, S extends Number> void emptyBackgroundMethod(T param) {
-		
+
 	}
 
 	@Background
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/AwaitingResultActivity.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/AwaitingResultActivity.java
index ef55ce7..aab6599 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/AwaitingResultActivity.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/AwaitingResultActivity.java
@@ -15,13 +15,14 @@
  */
 package org.androidannotations.test15;
 
-import android.app.Activity;
-import android.content.Intent;
-import android.net.Uri;
+import java.util.ArrayList;
+
 import org.androidannotations.annotations.EActivity;
 import org.androidannotations.annotations.OnActivityResult;
 
-import java.util.ArrayList;
+import android.app.Activity;
+import android.content.Intent;
+import android.net.Uri;
 
 @EActivity(R.layout.views_injected)
 public class AwaitingResultActivity extends Activity {
@@ -68,10 +69,12 @@ void onResultWithIntegerResultCode(Integer resultCodeInteger) {
 		onResultWithIntegerResultCodeCalled = true;
 	}
 
+	// CHECKSTYLE:OFF
+
 	@OnActivityResult(FORTH_REQUEST)
-	void onResultWithResultExtra(int resultCode, @OnActivityResult.Extra("value") int i, @OnActivityResult.Extra String s,
-	                             @OnActivityResult.Extra Uri uri, @OnActivityResult.Extra ArrayList<Uri> uris,
-	                             @OnActivityResult.Extra String[] strings) {
+	void onResultWithResultExtra(int resultCode, @OnActivityResult.Extra("value") int i, @OnActivityResult.Extra String s, @OnActivityResult.Extra Uri uri, @OnActivityResult.Extra ArrayList<Uri> uris, @OnActivityResult.Extra String[] strings) {
 		onResultWithResultExtraCodeCalled = true;
 	}
+
+	// CHECKSTYLE:ON
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/AwaitingResultFragment.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/AwaitingResultFragment.java
index daf0642..9e36d03 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/AwaitingResultFragment.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/AwaitingResultFragment.java
@@ -15,12 +15,14 @@
  */
 package org.androidannotations.test15;
 
-import android.support.v4.app.Fragment;
-import android.content.Intent;
-import android.net.Uri;
+import java.util.ArrayList;
+
 import org.androidannotations.annotations.EFragment;
 import org.androidannotations.annotations.OnActivityResult;
-import java.util.ArrayList;
+
+import android.content.Intent;
+import android.net.Uri;
+import android.support.v4.app.Fragment;
 
 @EFragment(R.layout.views_injected)
 public class AwaitingResultFragment extends Fragment {
@@ -30,12 +32,12 @@
 	private static final int THIRD_REQUEST = 33;
 	private static final int FORTH_REQUEST = 44;
 
-	boolean	onResultCalled = false;
-	boolean	onResultWithDataCalled = false;
-	boolean	onActivityResultWithResultCodeAndDataCalled = false;
-	boolean	onActivityResultWithDataAndResultCodeCalled = false;
-	boolean	onResultWithIntResultCodeCalled = false;
-	boolean	onResultWithIntegerResultCodeCalled = false;
+	boolean onResultCalled = false;
+	boolean onResultWithDataCalled = false;
+	boolean onActivityResultWithResultCodeAndDataCalled = false;
+	boolean onActivityResultWithDataAndResultCodeCalled = false;
+	boolean onResultWithIntResultCodeCalled = false;
+	boolean onResultWithIntegerResultCodeCalled = false;
 
 	@OnActivityResult(FIRST_REQUEST)
 	void onResult() {
@@ -68,8 +70,6 @@ void onResultWithIntegerResultCode(Integer resultCodeInteger) {
 	}
 
 	@OnActivityResult(FORTH_REQUEST)
-	void onResultWithResultExtra(int resultCode, @OnActivityResult.Extra("value") int i, @OnActivityResult.Extra String s,
-	                             @OnActivityResult.Extra Uri uri, @OnActivityResult.Extra ArrayList<Uri> uris,
-	                             @OnActivityResult.Extra String[] strings) {
+	void onResultWithResultExtra(int resultCode, @OnActivityResult.Extra("value") int i, @OnActivityResult.Extra String s, @OnActivityResult.Extra Uri uri, @OnActivityResult.Extra ArrayList<Uri> uris, @OnActivityResult.Extra String[] strings) {
 	}
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/CustomData.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/CustomData.java
index 1068df6..1dfcf03 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/CustomData.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/CustomData.java
@@ -31,24 +31,29 @@ public CustomData(String id) {
 	public int hashCode() {
 		final int prime = 31;
 		int result = 1;
-		result = prime * result + ((id == null) ? 0 : id.hashCode());
+		result = prime * result + (id == null ? 0 : id.hashCode());
 		return result;
 	}
 
 	@Override
 	public boolean equals(Object obj) {
-		if (this == obj)
+		if (this == obj) {
 			return true;
-		if (obj == null)
+		}
+		if (obj == null) {
 			return false;
-		if (getClass() != obj.getClass())
+		}
+		if (getClass() != obj.getClass()) {
 			return false;
+		}
 		CustomData other = (CustomData) obj;
 		if (id == null) {
-			if (other.id != null)
+			if (other.id != null) {
 				return false;
-		} else if (!id.equals(other.id))
+			}
+		} else if (!id.equals(other.id)) {
 			return false;
+		}
 		return true;
 	}
 
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/FragmentArguments.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/FragmentArguments.java
index 0ebee7d..4c7cd46 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/FragmentArguments.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/FragmentArguments.java
@@ -32,7 +32,7 @@
 public class FragmentArguments extends Fragment {
 
 	@FragmentArg("test")
-	Long nullWrappedLong = 42l;
+	Long nullWrappedLong = 42L;
 
 	@FragmentArg
 	boolean myBoolean;
@@ -178,8 +178,7 @@ void afterInject() {
 	}
 
 	static {
-		FragmentArguments_.builder().myBundle(null).myCharSequence(null)
-				.build();
+		FragmentArguments_.builder().myBundle(null).myCharSequence(null).build();
 	}
 
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ItemClicksHandledActivity.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ItemClicksHandledActivity.java
index 2e4529e..f44460f 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ItemClicksHandledActivity.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ItemClicksHandledActivity.java
@@ -15,14 +15,20 @@
  */
 package org.androidannotations.test15;
 
+import java.util.ArrayList;
+import java.util.List;
+
+import org.androidannotations.annotations.AfterViews;
+import org.androidannotations.annotations.EActivity;
+import org.androidannotations.annotations.ItemClick;
+import org.androidannotations.annotations.ItemLongClick;
+import org.androidannotations.annotations.ItemSelect;
+import org.androidannotations.annotations.ViewById;
+
 import android.app.Activity;
 import android.widget.ArrayAdapter;
 import android.widget.ListView;
 import android.widget.Spinner;
-import org.androidannotations.annotations.*;
-
-import java.util.ArrayList;
-import java.util.List;
 
 @EActivity(R.layout.item_clicks_handled)
 public class ItemClicksHandledActivity extends Activity {
@@ -67,8 +73,7 @@
 
 	@AfterViews
 	void initView() {
-		adapter = ArrayAdapter.createFromResource(this, R.array.planets_array,
-				R.layout.simple_spinner_item);
+		adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, R.layout.simple_spinner_item);
 		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
 		parametrizedAdapter = new ArrayAdapter<ArrayList<String>>(this, android.R.layout.simple_list_item_1, stringLists());
@@ -97,7 +102,7 @@ public void listView() {
 	public void listViewWithArgument(String selectedItem) {
 		listViewWithArgumentSelectedItem = selectedItem;
 	}
-	
+
 	@ItemClick(R.id.listViewWithArgumentWithParameterType)
 	protected void listViewWithArgumentWithParameterType(ArrayList<String> item) {
 		listViewParametrizedItemClicked = true;
@@ -133,10 +138,10 @@ void listViewWithPositionItemSelected(boolean selected, int position) {
 	void listViewWithOneParamItemSelected(boolean selected) {
 		listViewWithOneParamItemSelected = selected;
 	}
-	
+
 	@ItemSelect(R.id.listView)
 	void onItemSelected(boolean selected, int position) {
-		
+
 	}
 
 	@ItemLongClick
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/MultiFindViewActivity.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/MultiFindViewActivity.java
index 74cdea9..f89aee4 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/MultiFindViewActivity.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/MultiFindViewActivity.java
@@ -15,11 +15,16 @@
  */
 package org.androidannotations.test15;
 
+import org.androidannotations.annotations.Click;
+import org.androidannotations.annotations.EActivity;
+import org.androidannotations.annotations.FocusChange;
+import org.androidannotations.annotations.LongClick;
+import org.androidannotations.annotations.ViewById;
+
 import android.app.Activity;
 import android.view.View;
 import android.widget.Button;
 import android.widget.TextView;
-import org.androidannotations.annotations.*;
 
 @EActivity(R.layout.views_injected)
 public class MultiFindViewActivity extends Activity {
@@ -33,7 +38,7 @@
 	@ViewById(R.id.myButton)
 	Button button;
 
-	@Click({ R.id.my_text_view, R.id.myButton})
+	@Click({ R.id.my_text_view, R.id.myButton })
 	void viewClicked() {
 
 	}
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ebean/ThreadControlledBean.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ebean/ThreadControlledBean.java
index d7ac208..8b87025 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ebean/ThreadControlledBean.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ebean/ThreadControlledBean.java
@@ -15,7 +15,11 @@
  */
 package org.androidannotations.test15.ebean;
 
-import org.androidannotations.annotations.*;
+import org.androidannotations.annotations.Background;
+import org.androidannotations.annotations.EBean;
+import org.androidannotations.annotations.SupposeBackground;
+import org.androidannotations.annotations.SupposeUiThread;
+import org.androidannotations.annotations.UiThread;
 
 @EBean
 public class ThreadControlledBean {
@@ -31,7 +35,7 @@ public void uiSupposed() {
 	public void backgroundSupposed() {
 	}
 
-	@SupposeBackground(serial = {SERIAL1, SERIAL2})
+	@SupposeBackground(serial = { SERIAL1, SERIAL2 })
 	public void serialBackgroundSupposed() {
 	}
 
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/efragment/MyFragment.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/efragment/MyFragment.java
index 279c4a3..80e1c28 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/efragment/MyFragment.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/efragment/MyFragment.java
@@ -15,10 +15,6 @@
  */
 package org.androidannotations.test15.efragment;
 
-import android.app.ActivityManager;
-import android.database.sqlite.SQLiteDatabase;
-import android.widget.TextView;
-
 import org.androidannotations.annotations.AfterInject;
 import org.androidannotations.annotations.AfterViews;
 import org.androidannotations.annotations.App;
@@ -32,25 +28,28 @@
 import org.androidannotations.annotations.ViewById;
 import org.androidannotations.test15.R;
 import org.androidannotations.test15.ormlite.DatabaseHelper;
-import org.androidannotations.test15.ormlite.User;
 import org.androidannotations.test15.ormlite.UserDao;
 import org.androidannotations.test15.roboguice.SampleRoboApplication;
 
+import android.app.ActivityManager;
+import android.database.sqlite.SQLiteDatabase;
+import android.widget.TextView;
+
 @EFragment(R.layout.injected)
 public class MyFragment extends AbstractFragment {
-	
+
 	@ViewById
 	TextView myTextView;
 
 	@App
 	SampleRoboApplication customApplication;
-	
+
 	@SystemService
 	ActivityManager activityManager;
 
 	@OrmLiteDao(helper = DatabaseHelper.class)
 	UserDao userDao;
-	
+
 	@Click
 	void myButton() {
 	}
@@ -62,7 +61,7 @@ void uiThread() {
 
 	@Trace
 	void trace() {
-		
+
 	}
 
 	@Transactional
@@ -73,10 +72,10 @@ void successfulTransaction(SQLiteDatabase db) {
 	void calledAfterInjection() {
 
 	}
-	
+
 	@AfterViews
 	void calledAfterViewInjection() {
 
 	}
-	
+
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/efragment/MyListFragment.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/efragment/MyListFragment.java
index c31204a..cd48b26 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/efragment/MyListFragment.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/efragment/MyListFragment.java
@@ -31,18 +31,18 @@
 @EFragment(R.layout.list_fragment)
 public class MyListFragment extends ListFragment {
 
-	boolean	listItemClicked = false;
+	boolean listItemClicked = false;
 
-	@ViewById(value=android.R.id.list)
+	@ViewById(value = android.R.id.list)
 	ListView list;
 
 	boolean didExecute;
 
 	@Override
-    public void onActivityCreated(Bundle savedInstanceState) {
-        super.onActivityCreated(savedInstanceState);
+	public void onActivityCreated(Bundle savedInstanceState) {
+		super.onActivityCreated(savedInstanceState);
 
-        ArrayAdapter<CharSequence> adapter;
+		ArrayAdapter<CharSequence> adapter;
 
 		adapter = ArrayAdapter.createFromResource(getActivity(), R.array.planets_array, R.layout.simple_spinner_item);
 		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
@@ -83,7 +83,7 @@ void notIgnored() {
 
 	@ItemClick
 	void listItemClicked(String string) {
-		listItemClicked  = true;
+		listItemClicked = true;
 	}
 
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eintentservice/IntentServiceHandledAction.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eintentservice/IntentServiceHandledAction.java
index 51b2083..c29fd57 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eintentservice/IntentServiceHandledAction.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eintentservice/IntentServiceHandledAction.java
@@ -41,7 +41,7 @@ void myAction() {
 	}
 
 	@ServiceAction
-	void MyActionOneParam(String valueString) {
+	void myActionOneParam(String valueString) {
 		actionForTestHandled = valueString;
 	}
 
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eprovider/MyProvider.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eprovider/MyProvider.java
index 2078dab..8d90301 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eprovider/MyProvider.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eprovider/MyProvider.java
@@ -15,14 +15,6 @@
  */
 package org.androidannotations.test15.eprovider;
 
-import android.app.NotificationManager;
-import android.content.ContentProvider;
-import android.content.ContentValues;
-import android.database.Cursor;
-import android.net.Uri;
-import android.util.Log;
-import android.widget.Toast;
-
 import org.androidannotations.annotations.Background;
 import org.androidannotations.annotations.Bean;
 import org.androidannotations.annotations.EProvider;
@@ -34,10 +26,17 @@
 import org.androidannotations.test15.ebean.EnhancedClass;
 import org.androidannotations.test15.eservice.MyService;
 import org.androidannotations.test15.ormlite.DatabaseHelper;
-import org.androidannotations.test15.ormlite.User;
 import org.androidannotations.test15.ormlite.UserDao;
 import org.androidannotations.test15.prefs.SomePrefs_;
 
+import android.app.NotificationManager;
+import android.content.ContentProvider;
+import android.content.ContentValues;
+import android.database.Cursor;
+import android.net.Uri;
+import android.util.Log;
+import android.widget.Toast;
+
 @EProvider
 public class MyProvider extends ContentProvider {
 
@@ -46,7 +45,7 @@
 
 	@Bean
 	EnhancedClass dependency;
-	
+
 	@OrmLiteDao(helper = DatabaseHelper.class)
 	UserDao userDao;
 
@@ -89,7 +88,6 @@ void showToast() {
 		Toast.makeText(getContext().getApplicationContext(), "Hello World!", Toast.LENGTH_LONG).show();
 	}
 
-
 	@Trace
 	@Background
 	void workInBackground() {
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eservice/MyService.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eservice/MyService.java
index 1d69803..322366e 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eservice/MyService.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eservice/MyService.java
@@ -15,12 +15,6 @@
  */
 package org.androidannotations.test15.eservice;
 
-import android.app.IntentService;
-import android.app.NotificationManager;
-import android.content.Intent;
-import android.util.Log;
-import android.widget.Toast;
-
 import org.androidannotations.annotations.Background;
 import org.androidannotations.annotations.Bean;
 import org.androidannotations.annotations.EService;
@@ -30,9 +24,14 @@
 import org.androidannotations.annotations.UiThread;
 import org.androidannotations.test15.ebean.EnhancedClass;
 import org.androidannotations.test15.ormlite.DatabaseHelper;
-import org.androidannotations.test15.ormlite.User;
 import org.androidannotations.test15.ormlite.UserDao;
 
+import android.app.IntentService;
+import android.app.NotificationManager;
+import android.content.Intent;
+import android.util.Log;
+import android.widget.Toast;
+
 @EService
 public class MyService extends IntentService {
 
@@ -60,8 +59,7 @@ protected void onHandleIntent(Intent intent) {
 	@Trace
 	@UiThread
 	void showToast() {
-		Toast.makeText(getApplicationContext(), "Hello World!",
-				Toast.LENGTH_LONG).show();
+		Toast.makeText(getApplicationContext(), "Hello World!", Toast.LENGTH_LONG).show();
 	}
 
 	@Trace
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eviewgroup/CustomFrameLayout.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eviewgroup/CustomFrameLayout.java
index 5d3aa05..36b0c72 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eviewgroup/CustomFrameLayout.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eviewgroup/CustomFrameLayout.java
@@ -15,13 +15,6 @@
  */
 package org.androidannotations.test15.eviewgroup;
 
-import android.content.Context;
-import android.util.AttributeSet;
-import android.view.MotionEvent;
-import android.view.animation.Animation;
-import android.widget.FrameLayout;
-import android.widget.TextView;
-
 import org.androidannotations.annotations.AfterViews;
 import org.androidannotations.annotations.Background;
 import org.androidannotations.annotations.Click;
@@ -35,6 +28,13 @@
 import org.androidannotations.annotations.res.StringRes;
 import org.androidannotations.test15.R;
 
+import android.content.Context;
+import android.util.AttributeSet;
+import android.view.MotionEvent;
+import android.view.animation.Animation;
+import android.widget.FrameLayout;
+import android.widget.TextView;
+
 @EViewGroup(R.layout.component)
 public class CustomFrameLayout extends FrameLayout {
 
@@ -43,26 +43,26 @@
 
 	@ViewById
 	protected TextView subtitle;
-	
+
 	@StringRes(R.string.app_name)
 	protected String res;
-	
+
 	@AnimationRes(R.anim.fadein)
 	protected Animation anim;
 
 	public CustomFrameLayout(Context context, int i) {
 		super(context);
 	}
-	
+
 	public CustomFrameLayout(Context context, AttributeSet attrs) {
 		super(context, attrs);
 	}
 
 	@Trace
 	@AfterViews
-	protected void afterViews(){
+	protected void afterViews() {
 	}
-	
+
 	@Click
 	protected void title() {
 	}
@@ -76,11 +76,11 @@ protected void titleTouched(MotionEvent e) {
 	}
 
 	@Background
-	protected void someBackgroundTask(){
+	protected void someBackgroundTask() {
 	}
-	
+
 	@UiThread
-	protected void someUIThreadTask(){
+	protected void someUIThreadTask() {
 	}
 
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eviewgroup/CustomFrameLayoutActivity.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eviewgroup/CustomFrameLayoutActivity.java
index 9c14def..7566f98 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eviewgroup/CustomFrameLayoutActivity.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/eviewgroup/CustomFrameLayoutActivity.java
@@ -15,19 +15,19 @@
  */
 package org.androidannotations.test15.eviewgroup;
 
-import android.app.Activity;
-
 import org.androidannotations.annotations.AfterViews;
 import org.androidannotations.annotations.EActivity;
 import org.androidannotations.test15.R;
 
+import android.app.Activity;
+
 @EActivity(R.layout.main)
 public class CustomFrameLayoutActivity extends Activity {
-	
+
 	private CustomFrameLayout layout;
 
 	@AfterViews
-	public void afterViews(){
+	public void afterViews() {
 		layout = CustomFrameLayout_.build(this, 2);
 		layout.tv.setText("Plop");
 	}
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/innerclasses/ActivityWithInnerEnhancedClasses.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/innerclasses/ActivityWithInnerEnhancedClasses.java
index 4a2d97a..c67bad1 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/innerclasses/ActivityWithInnerEnhancedClasses.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/innerclasses/ActivityWithInnerEnhancedClasses.java
@@ -15,13 +15,18 @@
  */
 package org.androidannotations.test15.innerclasses;
 
+import org.androidannotations.annotations.EActivity;
+import org.androidannotations.annotations.EBean;
+import org.androidannotations.annotations.EFragment;
+import org.androidannotations.annotations.EViewGroup;
+import org.androidannotations.annotations.ViewById;
+import org.androidannotations.test15.R;
+
 import android.app.Activity;
 import android.app.Fragment;
 import android.content.Context;
 import android.widget.LinearLayout;
 import android.widget.TextView;
-import org.androidannotations.annotations.*;
-import org.androidannotations.test15.R;
 
 @EActivity(R.layout.views_injected)
 public class ActivityWithInnerEnhancedClasses extends Activity {
@@ -35,7 +40,6 @@
 		@ViewById(R.id.injected_text_view)
 		TextView textView;
 
-
 		public InnerViewGroup(Context context) {
 			super(context);
 		}
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/innerclasses/BeanWithInnerEnhancedClasses.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/innerclasses/BeanWithInnerEnhancedClasses.java
index b1f2583..070d3ca 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/innerclasses/BeanWithInnerEnhancedClasses.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/innerclasses/BeanWithInnerEnhancedClasses.java
@@ -40,12 +40,13 @@
 	@EBean
 	public static class InnerEnhancedBean {
 
-		@StringRes(R.string.hello) String hello;
+		@StringRes(R.string.hello)
+		String hello;
 
 	}
 
 	@SharedPref
-	public static interface InnerPrefs {
+	public interface InnerPrefs {
 
 		@DefaultInt(12)
 		int intValue();
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MyGenericParcelableBean.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MyGenericParcelableBean.java
index 87fedc0..07195d4 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MyGenericParcelableBean.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MyGenericParcelableBean.java
@@ -18,45 +18,51 @@
 import android.os.Parcel;
 import android.os.Parcelable;
 
-public class MyGenericParcelableBean <T> implements Parcelable {
+public class MyGenericParcelableBean<T> implements Parcelable {
 
 	private final T t;
 
 	public MyGenericParcelableBean(T t) {
 		this.t = t;
 	}
-	
+
 	@Override
 	public int describeContents() {
 		return 0;
 	}
 
 	@Override
-	public void writeToParcel(Parcel dest, int flags) {}
+	public void writeToParcel(Parcel dest, int flags) {
+	}
 
 	@Override
 	public int hashCode() {
 		final int prime = 31;
 		int result = 1;
-		result = prime * result + ((t == null) ? 0 : t.hashCode());
+		result = prime * result + (t == null ? 0 : t.hashCode());
 		return result;
 	}
 
 	@Override
 	public boolean equals(Object obj) {
-		if (this == obj)
+		if (this == obj) {
 			return true;
-		if (obj == null)
+		}
+		if (obj == null) {
 			return false;
-		if (getClass() != obj.getClass())
+		}
+		if (getClass() != obj.getClass()) {
 			return false;
+		}
 		@SuppressWarnings("rawtypes")
 		MyGenericParcelableBean other = (MyGenericParcelableBean) obj;
 		if (t == null) {
-			if (other.t != null)
+			if (other.t != null) {
 				return false;
-		} else if (!t.equals(other.t))
+			}
+		} else if (!t.equals(other.t)) {
 			return false;
+		}
 		return true;
 	}
 
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MyGenericSerializableBean.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MyGenericSerializableBean.java
index 6ca5bac..3493bbe 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MyGenericSerializableBean.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MyGenericSerializableBean.java
@@ -31,29 +31,32 @@ public MyGenericSerializableBean(T t) {
 	public int hashCode() {
 		final int prime = 31;
 		int result = 1;
-		result = prime * result + ((t == null) ? 0 : t.hashCode());
+		result = prime * result + (t == null ? 0 : t.hashCode());
 		return result;
 	}
 
 	@Override
 	public boolean equals(Object obj) {
-		if (this == obj)
+		if (this == obj) {
 			return true;
-		if (obj == null)
+		}
+		if (obj == null) {
 			return false;
-		if (getClass() != obj.getClass())
+		}
+		if (getClass() != obj.getClass()) {
 			return false;
+		}
 
 		@SuppressWarnings("unchecked")
 		MyGenericSerializableBean<T> other = (MyGenericSerializableBean<T>) obj;
 		if (t == null) {
-			if (other.t != null)
+			if (other.t != null) {
 				return false;
-		} else if (!t.equals(other.t))
+			}
+		} else if (!t.equals(other.t)) {
 			return false;
+		}
 		return true;
 	}
 
-
-	
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MyParcelableBean.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MyParcelableBean.java
index c2892d4..02bc16a 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MyParcelableBean.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MyParcelableBean.java
@@ -37,16 +37,28 @@ public void writeToParcel(Parcel dest, int flags) {
 	}
 
 	@Override
+	public int hashCode() {
+		final int prime = 31;
+		int result = 1;
+		result = prime * result + x;
+		return result;
+	}
+
+	@Override
 	public boolean equals(Object obj) {
-		if (this == obj)
+		if (this == obj) {
 			return true;
-		if (obj == null)
+		}
+		if (obj == null) {
 			return false;
-		if (getClass() != obj.getClass())
+		}
+		if (getClass() != obj.getClass()) {
 			return false;
+		}
 		MyParcelableBean other = (MyParcelableBean) obj;
-		if (x != other.x)
+		if (x != other.x) {
 			return false;
+		}
 		return true;
 	}
 
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MySerializableBean.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MySerializableBean.java
index 9414ffa..e0c9fd0 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MySerializableBean.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/MySerializableBean.java
@@ -28,16 +28,28 @@ public MySerializableBean(int x) {
 	}
 
 	@Override
+	public int hashCode() {
+		final int prime = 31;
+		int result = 1;
+		result = prime * result + x;
+		return result;
+	}
+
+	@Override
 	public boolean equals(Object obj) {
-		if (this == obj)
+		if (this == obj) {
 			return true;
-		if (obj == null)
+		}
+		if (obj == null) {
 			return false;
-		if (getClass() != obj.getClass())
+		}
+		if (getClass() != obj.getClass()) {
 			return false;
+		}
 		MySerializableBean other = (MySerializableBean) obj;
-		if (x != other.x)
+		if (x != other.x) {
 			return false;
+		}
 		return true;
 	}
 
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/SaveInstanceStateActivity.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/SaveInstanceStateActivity.java
index 3e6c966..14dd7ff 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/SaveInstanceStateActivity.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/instancestate/SaveInstanceStateActivity.java
@@ -17,18 +17,18 @@
 
 import java.util.ArrayList;
 
-import android.app.Activity;
-import android.os.Bundle;
-
 import org.androidannotations.annotations.EActivity;
 import org.androidannotations.annotations.InstanceState;
 import org.androidannotations.test15.R;
 
+import android.app.Activity;
+import android.os.Bundle;
+
 @EActivity(R.layout.main)
 public class SaveInstanceStateActivity extends Activity {
 
 	@InstanceState
-	Long nullWrappedLong = 42l;
+	Long nullWrappedLong = 42L;
 
 	@InstanceState
 	boolean myBoolean;
@@ -167,13 +167,13 @@
 
 	@InstanceState
 	MyGenericParcelableBean<Integer>[] myGenericParcelableBeanArray;
-	
+
 	@InstanceState
 	ArrayList<MyParcelableBean> myParcelableBeanArrayList;
-	
+
 	@InstanceState
 	ArrayList<MyGenericParcelableBean<Integer>> myGenericParcelableBeanArrayList;
-	
+
 	@InstanceState
 	ArrayList<MySerializableBean> mySerializableBeanArrayList;
 
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuActivity.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuActivity.java
index 8d8245f..d7fc49c 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuActivity.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuActivity.java
@@ -15,23 +15,27 @@
  */
 package org.androidannotations.test15.menu;
 
-import android.app.Activity;
-import android.os.Bundle;
-import android.view.MenuItem;
-
 import org.androidannotations.annotations.EActivity;
 import org.androidannotations.annotations.OptionsItem;
 import org.androidannotations.annotations.OptionsMenu;
 import org.androidannotations.annotations.OptionsMenuItem;
 import org.androidannotations.test15.R;
 
+import android.app.Activity;
+import android.os.Bundle;
+import android.view.MenuItem;
+
 @EActivity
 @OptionsMenu({ R.menu.my_menu, R.menu.my_menu2 })
 public class OptionsMenuActivity extends Activity {
 
+	// CHECKSTYLE:OFF
+
 	@OptionsMenuItem
 	MenuItem menu_refresh;
 
+	// CHECKSTYLE:ON
+
 	@OptionsMenuItem(R.id.menu_search)
 	MenuItem aMenuById;
 
@@ -40,7 +44,7 @@
 
 	boolean menuRefreshSelected;
 	boolean multipleMenuItems;
-	boolean menu_add;
+	boolean menuAdd;
 
 	@Override
 	public void onCreate(Bundle savedInstanceState) {
@@ -59,8 +63,10 @@ boolean multipleMenuItems() {
 	}
 
 	@OptionsItem
+	// CHECKSTYLE:OFF
 	void menu_add(MenuItem item) {
-		menu_add = true;
+		// CHECKSTYLE:ON
+		menuAdd = true;
 	}
 
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuFragment.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuFragment.java
index 930511d..4b229ef 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuFragment.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuFragment.java
@@ -15,22 +15,26 @@
  */
 package org.androidannotations.test15.menu;
 
-import android.app.Fragment;
-import android.view.MenuItem;
-
 import org.androidannotations.annotations.EFragment;
 import org.androidannotations.annotations.OptionsItem;
 import org.androidannotations.annotations.OptionsMenu;
 import org.androidannotations.annotations.OptionsMenuItem;
 import org.androidannotations.test15.R;
 
+import android.app.Fragment;
+import android.view.MenuItem;
+
 @EFragment
 @OptionsMenu({ R.menu.my_menu, R.menu.my_menu2 })
 public class OptionsMenuFragment extends Fragment {
 
+	// CHECKSTYLE:OFF
+
 	@OptionsMenuItem
 	MenuItem menu_refresh;
 
+	// CHECKSTYLE:ON
+
 	@OptionsMenuItem(R.id.menu_search)
 	MenuItem aMenuById;
 
@@ -47,7 +51,9 @@ boolean multipleMenuItems() {
 	}
 
 	@OptionsItem
+	// CHECKSTYLE:OFF
 	void menu_add(MenuItem item) {
+		// CHECKSTYLE:ON
 	}
 
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuSubActivity.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuSubActivity.java
index 5134149..a5011db 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuSubActivity.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuSubActivity.java
@@ -26,7 +26,10 @@
 @OptionsMenu(R.menu.my_menu3)
 public class OptionsMenuSubActivity extends OptionsMenuActivity {
 
+	// CHECKSTYLE:OFF
+
 	@OptionsMenuItem
 	MenuItem menu_settings;
 
+	// CHECKSTYLE:ON
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuSupportFragment.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuSupportFragment.java
index 3f08d7a..d619c98 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuSupportFragment.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/menu/OptionsMenuSupportFragment.java
@@ -15,22 +15,26 @@
  */
 package org.androidannotations.test15.menu;
 
-import android.support.v4.app.Fragment;
-import android.view.MenuItem;
-
 import org.androidannotations.annotations.EFragment;
 import org.androidannotations.annotations.OptionsItem;
 import org.androidannotations.annotations.OptionsMenu;
 import org.androidannotations.annotations.OptionsMenuItem;
 import org.androidannotations.test15.R;
 
+import android.support.v4.app.Fragment;
+import android.view.MenuItem;
+
 @EFragment
 @OptionsMenu({ R.menu.my_menu, R.menu.my_menu2 })
 public class OptionsMenuSupportFragment extends Fragment {
 
+	// CHECKSTYLE:OFF
+
 	@OptionsMenuItem
 	MenuItem menu_refresh;
 
+	// CHECKSTYLE:ON
+
 	@OptionsItem
 	void menuRefreshSelected() {
 	}
@@ -41,7 +45,9 @@ boolean multipleMenuItems() {
 	}
 
 	@OptionsItem
+	// CHECKSTYLE:OFF
 	void menu_add(MenuItem item) {
+		// CHECKSTYLE:ON
 	}
 
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ormlite/OrmLiteBean.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ormlite/OrmLiteBean.java
index 30bf1dd..9d0981e 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ormlite/OrmLiteBean.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/ormlite/OrmLiteBean.java
@@ -18,10 +18,6 @@
 import org.androidannotations.annotations.EBean;
 import org.androidannotations.annotations.OrmLiteDao;
 
-import org.androidannotations.test15.ormlite.User;
-import org.androidannotations.test15.ormlite.UserDao;
-import org.androidannotations.test15.ormlite.DatabaseHelper;
-
 @EBean
 public class OrmLiteBean {
 
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/prefs/InnerPrefs.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/prefs/InnerPrefs.java
index 88e1e05..a3feb34 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/prefs/InnerPrefs.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/prefs/InnerPrefs.java
@@ -22,7 +22,7 @@
 public class InnerPrefs {
 
 	@SharedPref
-	public static interface InnerSharedPrefs {
+	public interface InnerSharedPrefs {
 
 	}
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/res/ResActivity.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/res/ResActivity.java
index 697b5be..a4f81c7 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/res/ResActivity.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/res/ResActivity.java
@@ -15,19 +15,21 @@
  */
 package org.androidannotations.test15.res;
 
-import android.app.Activity;
-import android.content.res.XmlResourceParser;
-import android.text.Spanned;
-import android.view.animation.Animation;
-
 import org.androidannotations.annotations.EActivity;
 import org.androidannotations.annotations.res.HtmlRes;
 import org.androidannotations.annotations.res.StringRes;
 import org.androidannotations.test15.R;
 
+import android.app.Activity;
+import android.content.res.XmlResourceParser;
+import android.text.Spanned;
+import android.view.animation.Animation;
+
 @EActivity(R.layout.main)
 public class ResActivity extends Activity {
 
+	// CHECKSTYLE:OFF
+
 	// @AnimationRes
 	XmlResourceParser fade_in;
 
@@ -45,4 +47,6 @@
 
 	@HtmlRes(R.string.hello_html)
 	CharSequence htmlInjected;
+
+	// CHECKSTYLE:ON
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/rest/Event.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/rest/Event.java
index 69af7f4..89994db 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/rest/Event.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/rest/Event.java
@@ -33,26 +33,32 @@ public int hashCode() {
 		final int prime = 31;
 		int result = 1;
 		result = prime * result + id;
-		result = prime * result + ((name == null) ? 0 : name.hashCode());
+		result = prime * result + (name == null ? 0 : name.hashCode());
 		return result;
 	}
 
 	@Override
 	public boolean equals(Object obj) {
-		if (this == obj)
+		if (this == obj) {
 			return true;
-		if (obj == null)
+		}
+		if (obj == null) {
 			return false;
-		if (getClass() != obj.getClass())
+		}
+		if (getClass() != obj.getClass()) {
 			return false;
+		}
 		Event other = (Event) obj;
-		if (id != other.id)
+		if (id != other.id) {
 			return false;
+		}
 		if (name == null) {
-			if (other.name != null)
+			if (other.name != null) {
 				return false;
-		} else if (!name.equals(other.name))
+			}
+		} else if (!name.equals(other.name)) {
 			return false;
+		}
 		return true;
 	}
 
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/rest/HttpMethodsService.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/rest/HttpMethodsService.java
index 4dd8e01..a82fd9f 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/rest/HttpMethodsService.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/rest/HttpMethodsService.java
@@ -25,7 +25,6 @@
 import org.androidannotations.annotations.rest.Put;
 import org.androidannotations.annotations.rest.Rest;
 import org.androidannotations.api.rest.RestClientErrorHandling;
-import org.androidannotations.api.rest.RestErrorHandler;
 import org.springframework.http.HttpHeaders;
 import org.springframework.http.HttpMethod;
 import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
@@ -52,7 +51,7 @@
 
 	@Put("/put/")
 	void put();
-	
+
 	void setRestTemplate(RestTemplate restTemplate);
 
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/roboguice/MapActivityWithRoboGuice.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/roboguice/MapActivityWithRoboGuice.java
index ccfb182..6716c17 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/roboguice/MapActivityWithRoboGuice.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/roboguice/MapActivityWithRoboGuice.java
@@ -15,13 +15,9 @@
  */
 package org.androidannotations.test15.roboguice;
 
-import android.app.Activity;
-
 import org.androidannotations.annotations.EActivity;
-import org.androidannotations.annotations.RoboGuice;
 
 import roboguice.activity.RoboActivity;
-import roboguice.activity.RoboMapActivity;
 
 //Commented because Maps is not Open Source
 @EActivity
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/roboguice/SampleRoboApplication.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/roboguice/SampleRoboApplication.java
index ff82872..37253e6 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/roboguice/SampleRoboApplication.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/roboguice/SampleRoboApplication.java
@@ -20,14 +20,13 @@
 import org.androidannotations.annotations.OrmLiteDao;
 import org.androidannotations.test15.ebean.EmptyDependency;
 import org.androidannotations.test15.ormlite.DatabaseHelper;
-import org.androidannotations.test15.ormlite.User;
 import org.androidannotations.test15.ormlite.UserDao;
 
 import android.app.Application;
 
 @EApplication
 public class SampleRoboApplication extends Application {
-	
+
 	@Bean
 	public EmptyDependency someDependency;
 
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/sherlock/MySherlockActivity.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/sherlock/MySherlockActivity.java
index 2789828..6294bd1 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/sherlock/MySherlockActivity.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/sherlock/MySherlockActivity.java
@@ -15,27 +15,31 @@
  */
 package org.androidannotations.test15.sherlock;
 
-
-
-import android.os.Bundle;
-import com.actionbarsherlock.app.SherlockActivity;
-import com.actionbarsherlock.view.MenuItem;
 import org.androidannotations.annotations.EActivity;
 import org.androidannotations.annotations.OptionsItem;
 import org.androidannotations.annotations.OptionsMenu;
 import org.androidannotations.annotations.OptionsMenuItem;
 import org.androidannotations.test15.R;
 
+import android.os.Bundle;
+
+import com.actionbarsherlock.app.SherlockActivity;
+import com.actionbarsherlock.view.MenuItem;
+
 @EActivity
 @OptionsMenu(R.menu.my_menu)
 public class MySherlockActivity extends SherlockActivity {
 
+	// CHECKSTYLE:OFF
+
 	@OptionsMenuItem
 	MenuItem menu_refresh;
 
+	// CHECKSTYLE:ON
+
 	boolean menuRefreshSelected;
 	boolean multipleMenuItems;
-	boolean menu_add;
+	boolean menuAdd;
 
 	@Override
 	public void onCreate(Bundle savedInstanceState) {
@@ -54,8 +58,10 @@ boolean multipleMenuItems() {
 	}
 
 	@OptionsItem
+	// CHECKSTYLE:OFF
 	void menu_add(MenuItem item) {
-		menu_add = true;
+		// CHECKSTYLE:ON
+		menuAdd = true;
 	}
-	
+
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/sherlock/MySherlockFragment.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/sherlock/MySherlockFragment.java
index 8559ada..16a973e 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/sherlock/MySherlockFragment.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/sherlock/MySherlockFragment.java
@@ -15,19 +15,18 @@
  */
 package org.androidannotations.test15.sherlock;
 
-
-import com.actionbarsherlock.app.SherlockFragment;
-import com.actionbarsherlock.view.MenuItem;
 import org.androidannotations.annotations.EFragment;
 import org.androidannotations.annotations.OptionsItem;
 import org.androidannotations.annotations.OptionsMenu;
 import org.androidannotations.test15.R;
 
+import com.actionbarsherlock.app.SherlockFragment;
+import com.actionbarsherlock.view.MenuItem;
+
 @EFragment
 @OptionsMenu(R.menu.my_menu)
 public class MySherlockFragment extends SherlockFragment {
 
-
 	@OptionsItem
 	void menuRefreshSelected() {
 	}
@@ -38,7 +37,9 @@ boolean multipleMenuItems() {
 	}
 
 	@OptionsItem
+	// CHECKSTYLE:OFF
 	void menu_add(MenuItem item) {
+		// CHECKSTYLE:ON
 	}
-	
+
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/trace/TracedActivity.java b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/trace/TracedActivity.java
index b81313b..78c3985 100644
--- a/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/trace/TracedActivity.java
+++ b/AndroidAnnotations/functional-test-1-5/src/main/java/org/androidannotations/test15/trace/TracedActivity.java
@@ -15,7 +15,6 @@
  */
 package org.androidannotations.test15.trace;
 
-
 import java.io.IOException;
 import java.util.List;
 import java.util.Map;
@@ -36,7 +35,7 @@
 
 @EActivity
 public class TracedActivity extends Activity {
-	
+
 	public boolean tracedMethodCalled = false;
 	public boolean voidTracedMethodCalled = false;
 	public boolean voidTracedMethodDebugCalled = false;
@@ -54,15 +53,13 @@ public void onCreate(Bundle savedInstanceState) {
 	}
 
 	@Trace
-	Object tracedMethod(List<Map<String, List<Set<Void>>>> param1, Void param2)
-			throws IOException {
+	Object tracedMethod(List<Map<String, List<Set<Void>>>> param1, Void param2) throws IOException {
 		tracedMethodCalled = true;
 		return null;
 	}
 
 	@Trace
-	void voidTracedMethod(List<Map<String, List<Set<Void>>>> param1, Void param2)
-			throws IOException {
+	void voidTracedMethod(List<Map<String, List<Set<Void>>>> param1, Void param2) throws IOException {
 		voidTracedMethodCalled = true;
 	}
 
@@ -126,8 +123,7 @@ void mixedTransactionalMethod(SQLiteDatabase db) {
 	}
 
 	@Trace
-	void tracedUsingArrayParameters(MySerializableBean[] array,
-			MySerializableBean[][] multiDimArray) {
+	void tracedUsingArrayParameters(MySerializableBean[] array, MySerializableBean[][] multiDimArray) {
 	}
 
 	@Trace
diff --git a/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/ThreadActivityTest.java b/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/ThreadActivityTest.java
index e42859f..058ecef 100644
--- a/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/ThreadActivityTest.java
+++ b/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/ThreadActivityTest.java
@@ -15,9 +15,9 @@
  */
 package org.androidannotations.test15;
 
+import static org.fest.reflect.core.Reflection.staticField;
 import static org.mockito.Mockito.mock;
 import static org.mockito.Mockito.verify;
-import static org.fest.reflect.core.Reflection.staticField;
 
 import java.lang.reflect.Field;
 import java.util.ArrayList;
@@ -37,7 +37,7 @@
 import org.junit.Before;
 import org.junit.Test;
 import org.junit.runner.RunWith;
-import org.mockito.Mockito;
+import org.mockito.Matchers;
 import org.mockito.internal.util.MockUtil;
 import org.robolectric.Robolectric;
 import org.robolectric.RobolectricTestRunner;
@@ -66,7 +66,7 @@ public void setup() {
 	public void after() throws InterruptedException {
 		Thread.setDefaultUncaughtExceptionHandler(defaultExceptionHandler);
 
-		List<String> tasks = staticField("tasks") //
+		List<String> tasks = staticField("TASKS") //
 				.ofType(new TypeRef<List<String>>() {
 				}) //
 				.in(BackgroundExecutor.class) //
@@ -74,7 +74,7 @@ public void after() throws InterruptedException {
 
 		tasks.clear();
 
-		ThreadLocal<String> currentSerial = staticField("currentSerial") //
+		ThreadLocal<String> currentSerial = staticField("CURRENT_SERIAL") //
 				.ofType(new TypeRef<ThreadLocal<String>>() {
 				}) //
 				.in(BackgroundExecutor.class) //
@@ -109,17 +109,17 @@ public void backgroundDelegatesToExecutor() {
 
 		activity.emptyBackgroundMethod();
 
-		verify(executor).execute(Mockito.<Runnable> any());
+		verify(executor).execute(Matchers.<Runnable> any());
 	}
 
 	/**
 	 * Verify that non-serialized background tasks <strong>are not</strong>
 	 * serialized (ensure that serial feature does not force all background
 	 * tasks to be serialized).
-	 * 
+	 *
 	 * Start several requests which add an item to a list in background, without
 	 * "@Background" serial attribute enabled.
-	 * 
+	 *
 	 * Once all tasks have completed execution, verify that the items in the
 	 * list are not ordered (with very little false-negative probability).
 	 */
@@ -131,8 +131,7 @@ public void parallelBackgroundTasks() {
 		/* set an executor with 4 threads */
 		BackgroundExecutor.setExecutor(Executors.newFixedThreadPool(4));
 
-		List<Integer> list = Collections
-				.synchronizedList(new ArrayList<Integer>());
+		List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());
 
 		/* sem.acquire() will be unlocked exactly after NB_ADD releases */
 		Semaphore sem = new Semaphore(1 - NB_ADD);
@@ -151,10 +150,8 @@ public void parallelBackgroundTasks() {
 
 		try {
 			/* wait for all tasks to be completed */
-			boolean acquired = sem.tryAcquire(MAX_WAITING_TIME,
-					TimeUnit.MILLISECONDS);
-			Assert.assertTrue(
-					"Requested tasks should have completed execution", acquired);
+			boolean acquired = sem.tryAcquire(MAX_WAITING_TIME, TimeUnit.MILLISECONDS);
+			Assert.assertTrue("Requested tasks should have completed execution", acquired);
 
 			/*
 			 * verify that list items are in the wrong order (the probability it
@@ -166,18 +163,17 @@ public void parallelBackgroundTasks() {
 			}
 			Assert.assertFalse("Items should not be in order", rightOrder);
 		} catch (InterruptedException e) {
-			Assert.assertFalse("Testing thread should never be interrupted",
-					true);
+			Assert.assertFalse("Testing thread should never be interrupted", true);
 		}
 	}
 
 	/**
 	 * Verify that serialized background tasks are correctly serialized.
-	 * 
+	 *
 	 * Start several requests which add an item to a list in background, with
 	 * "@Background" serial attribute enabled, so the requests must be executed
 	 * sequentially.
-	 * 
+	 *
 	 * Once all tasks have completed execution, verify that the items in the
 	 * list are ordered.
 	 */
@@ -193,8 +189,7 @@ public void serializedBackgroundTasks() {
 		 * the calls are serialized, but not necessarily on the same thread, so
 		 * we need to synchronize to avoid cache effects
 		 */
-		List<Integer> list = Collections
-				.synchronizedList(new ArrayList<Integer>());
+		List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());
 
 		/* sem.acquire() will be unlocked exactly after NB_ADD releases */
 		Semaphore sem = new Semaphore(1 - NB_ADD);
@@ -213,28 +208,24 @@ public void serializedBackgroundTasks() {
 
 		try {
 			/* wait for all tasks to be completed */
-			boolean acquired = sem.tryAcquire(MAX_WAITING_TIME,
-					TimeUnit.MILLISECONDS);
-			Assert.assertTrue(
-					"Requested tasks should have completed execution", acquired);
+			boolean acquired = sem.tryAcquire(MAX_WAITING_TIME, TimeUnit.MILLISECONDS);
+			Assert.assertTrue("Requested tasks should have completed execution", acquired);
 
 			for (int i = 0; i < NB_ADD; i++) {
-				Assert.assertEquals("Items must be in order", i,
-						(int) list.get(i));
+				Assert.assertEquals("Items must be in order", i, (int) list.get(i));
 			}
 		} catch (InterruptedException e) {
-			Assert.assertFalse("Testing thread should never be interrupted",
-					true);
+			Assert.assertFalse("Testing thread should never be interrupted", true);
 		}
 	}
 
 	/**
 	 * Verify that cancellable background tasks are correctly cancelled, and
 	 * others are not.
-	 * 
+	 *
 	 * Start several requests which add an item to a list in background, half
 	 * explicitly cancelled, half not cancelled.
-	 * 
+	 *
 	 * Once all tasks have completed execution, check if and only if the items
 	 * from the uncancelled tasks are in the list.
 	 */
@@ -250,8 +241,7 @@ public void cancellableBackgroundTasks() {
 		 * the calls are serialized, but not necessarily on the same thread, so
 		 * we need to synchronize to avoid cache effects
 		 */
-		List<Integer> list = Collections
-				.synchronizedList(new ArrayList<Integer>());
+		List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());
 
 		/* sem.acquire() will be unlocked exactly after NB_ADD releases */
 		Semaphore sem = new Semaphore(1 - NB_ADD);
@@ -272,21 +262,16 @@ public void cancellableBackgroundTasks() {
 
 		try {
 			/* wait for all non cancelled tasks to be completed */
-			boolean acquired = sem.tryAcquire(MAX_WAITING_TIME,
-					TimeUnit.MILLISECONDS);
-			Assert.assertTrue(
-					"Requested tasks should have completed execution", acquired);
+			boolean acquired = sem.tryAcquire(MAX_WAITING_TIME, TimeUnit.MILLISECONDS);
+			Assert.assertTrue("Requested tasks should have completed execution", acquired);
 
-			Assert.assertEquals("Only uncancelled tasks must have added items",
-					list.size(), NB_ADD);
+			Assert.assertEquals("Only uncancelled tasks must have added items", list.size(), NB_ADD);
 
 			for (int i = 0; i < NB_ADD; i++) {
-				Assert.assertTrue("Items must be only from uncancelled tasks",
-						i < NB_ADD);
+				Assert.assertTrue("Items must be only from uncancelled tasks", i < NB_ADD);
 			}
 		} catch (InterruptedException e) {
-			Assert.assertFalse("Testing thread should never be interrupted",
-					true);
+			Assert.assertFalse("Testing thread should never be interrupted", true);
 		}
 	}
 
@@ -302,8 +287,7 @@ public void cancellableSerializedBackgroundTasks() {
 		 * the calls are serialized, but not necessarily on the same thread, so
 		 * we need to synchronize to avoid cache effects
 		 */
-		List<Integer> list = Collections
-				.synchronizedList(new ArrayList<Integer>());
+		List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());
 
 		/* sem.acquire() will be unlocked exactly after NB_ADD releases */
 		Semaphore sem = new Semaphore(1 - NB_ADD);
@@ -324,25 +308,20 @@ public void cancellableSerializedBackgroundTasks() {
 
 		try {
 			/* wait for all non cancelled tasks to be completed */
-			boolean acquired = sem.tryAcquire(MAX_WAITING_TIME,
-					TimeUnit.MILLISECONDS);
-			Assert.assertTrue(
-					"Requested tasks should have completed execution", acquired);
+			boolean acquired = sem.tryAcquire(MAX_WAITING_TIME, TimeUnit.MILLISECONDS);
+			Assert.assertTrue("Requested tasks should have completed execution", acquired);
 
 			/* cancel all tasks with id "to_cancel_2" */
 			BackgroundExecutor.cancelAll("to_cancel_2", true);
 
-			Assert.assertEquals("Only uncancelled tasks must have added items",
-					list.size(), NB_ADD);
+			Assert.assertEquals("Only uncancelled tasks must have added items", list.size(), NB_ADD);
 
 			for (int i = 0; i < NB_ADD; i++) {
-				Assert.assertTrue("Items must be only from uncancelled tasks",
-						i < NB_ADD);
+				Assert.assertTrue("Items must be only from uncancelled tasks", i < NB_ADD);
 			}
 
 		} catch (InterruptedException e) {
-			Assert.assertFalse("Testing thread should never be interrupted",
-					true);
+			Assert.assertFalse("Testing thread should never be interrupted", true);
 		}
 	}
 
@@ -382,7 +361,7 @@ public void run() {
 		synchronized (threadActivityHolder) {
 			do {
 				threadActivityHolder.wait();
-			} while(threadActivityHolder[0] == null);
+			} while (threadActivityHolder[0] == null);
 		}
 
 		Field handlerField = ThreadActivity_.class.getDeclaredField("handler_");
@@ -396,7 +375,7 @@ public void run() {
 	public void propagateExceptionToGlobalExceptionHandler() {
 		/* set an executor with 4 threads */
 		BackgroundExecutor.setExecutor(Executors.newFixedThreadPool(4));
-		
+
 		// Prepare lock on which we'll wait for the
 		// background exception handler to catch the exception
 		final Object LOCK = new Object();
@@ -420,8 +399,8 @@ public void uncaughtException(Thread thread, Throwable ex) {
 	}
 
 	/**
-	 * Call wait() on the given object with the specified timeout.
-	 * Avoid boilerplate code like synchronized or try..catch.
+	 * Call wait() on the given object with the specified timeout. Avoid
+	 * boilerplate code like synchronized or try..catch.
 	 */
 	private void waitOn(Object lock, long timeout) {
 		synchronized (lock) {
diff --git a/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/eintentservice/MyIntentServiceTest.java b/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/eintentservice/MyIntentServiceTest.java
index ea5f6ef..c3229e7 100644
--- a/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/eintentservice/MyIntentServiceTest.java
+++ b/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/eintentservice/MyIntentServiceTest.java
@@ -35,14 +35,14 @@ public void testAction() {
 		// Simulate call to intent builder and retrieve the configured Intent
 		EmptyActivityWithoutLayout context = new EmptyActivityWithoutLayout_();
 		Intent intent = IntentServiceHandledAction_.intent(context) //
-				.MyActionOneParam("test") //
+				.myActionOneParam("test") //
 				.get();
 
 		// Simulate the creation of IntentService by Android
 		IntentServiceHandledAction intentServiceHandledAction = new IntentServiceHandledAction_();
 		intentServiceHandledAction.onHandleIntent(intent);
 
-		assertThat(IntentServiceHandledAction_.actionForTestHandled).isEqualTo("test");
+		assertThat(IntentServiceHandledAction.actionForTestHandled).isEqualTo("test");
 	}
 
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/menu/OptionsMenuActivityTest.java b/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/menu/OptionsMenuActivityTest.java
index dffc6d9..18e3664 100644
--- a/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/menu/OptionsMenuActivityTest.java
+++ b/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/menu/OptionsMenuActivityTest.java
@@ -76,7 +76,7 @@ public void defaultIdUnderscore() {
 
 		activity.onOptionsItemSelected(item);
 
-		assertThat(activity.menu_add).isTrue();
+		assertThat(activity.menuAdd).isTrue();
 	}
 
 }
diff --git a/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/sherlock/MySherlockActivityTest.java b/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/sherlock/MySherlockActivityTest.java
index ea0ff69..f5bfcd1 100644
--- a/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/sherlock/MySherlockActivityTest.java
+++ b/AndroidAnnotations/functional-test-1-5/src/test/java/org/androidannotations/test15/sherlock/MySherlockActivityTest.java
@@ -76,7 +76,7 @@ public void defaultIdUnderscore() {
 
 		activity.onOptionsItemSelected(item);
 
-		assertThat(activity.menu_add).isTrue();
+		assertThat(activity.menuAdd).isTrue();
 	}
 
 }
diff --git a/AndroidAnnotations/pom.xml b/AndroidAnnotations/pom.xml
index 43e15db..10d457b 100644
--- a/AndroidAnnotations/pom.xml
+++ b/AndroidAnnotations/pom.xml
@@ -245,14 +245,23 @@
 					<artifactId>maven-deploy-plugin</artifactId>
 					<version>2.8.2</version>
 				</plugin>
+				<plugin>
+					<groupId>org.apache.maven.plugins</groupId>
+					<artifactId>maven-checkstyle-plugin</artifactId>
+					<version>2.13</version>
+					<configuration>
+						<configLocation>checkstyle-checks.xml</configLocation>
+						<consoleOutput>true</consoleOutput>
+					</configuration>
+				</plugin>
 			</plugins>
 		</pluginManagement>
 		<plugins>
 			<plugin>
 				<!-- Checks for license headers. Use "mvn license:format" to update files. -->
-				<groupId>com.mycila.maven-license-plugin</groupId>
-				<artifactId>maven-license-plugin</artifactId>
-				<version>1.9.0</version>
+                <groupId>com.mycila.maven-license-plugin</groupId>
+                <artifactId>maven-license-plugin</artifactId>
+                <version>1.9.0</version>
 				<configuration>
 					<header>src/etc/header.txt</header>
 					<excludes>
@@ -288,6 +297,17 @@
 					</execution>
 				</executions>
 			</plugin>
+			<plugin>
+				<artifactId>maven-checkstyle-plugin</artifactId>
+				<executions>
+					<execution>
+						<phase>process-sources</phase>
+						<goals>
+							<goal>check</goal>
+						</goals>
+					</execution>
+				</executions>
+			</plugin>
 		</plugins>
 	</build>
 
@@ -328,5 +348,17 @@
 		</profile>
 	</profiles>
 
+	<reporting>
+		<plugins>
+			<plugin>
+				<groupId>org.apache.maven.plugins</groupId>
+				<artifactId>maven-checkstyle-plugin</artifactId>
+				<version>2.12.1</version>
+				<configuration>
+					<configLocation>checkstyle-checks.xml</configLocation>
+				</configuration>
+			</plugin>
+		</plugins>
+	</reporting>
 
 </project>

From bc99f075af1858110d91e96bb28845f5b359c37b Mon Sep 17 00:00:00 2001
From: WonderCsabo <kozakcsabi@gmail.com>
Date: Sun, 19 Oct 2014 21:45:13 +0200
Subject: [PATCH 2/5] Update license plugin

---
 AndroidAnnotations/androidannotations-api/pom.xml  | 17 ++++++++++++++++
 .../androidannotations-bundle/pom.xml              | 17 ++++++++++++++++
 .../androidannotations-with-codemodel/pom.xml      | 17 ++++++++++++++++
 AndroidAnnotations/androidannotations/pom.xml      | 17 ++++++++++++++++
 AndroidAnnotations/checkstyle-checks.xml           | 17 ++++++++++++++++
 AndroidAnnotations/functional-test-1-5/pom.xml     | 17 ++++++++++++++++
 AndroidAnnotations/pom.xml                         | 23 +++++++++++++++++++---
 7 files changed, 122 insertions(+), 3 deletions(-)

diff --git a/AndroidAnnotations/androidannotations-api/pom.xml b/AndroidAnnotations/androidannotations-api/pom.xml
index 5d76fd4..abcbd9e 100644
--- a/AndroidAnnotations/androidannotations-api/pom.xml
+++ b/AndroidAnnotations/androidannotations-api/pom.xml
@@ -1,4 +1,21 @@
 <?xml version="1.0" encoding="UTF-8"?>
+<!--
+
+    Copyright (C) 2010-2014 eBusiness Information, Excilys Group
+
+    Licensed under the Apache License, Version 2.0 (the "License"); you may not
+    use this file except in compliance with the License. You may obtain a copy of
+    the License at
+
+    http://www.apache.org/licenses/LICENSE-2.0
+
+    Unless required by applicable law or agreed To in writing, software
+    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
+    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
+    License for the specific language governing permissions and limitations under
+    the License.
+
+-->
 <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
 	<modelVersion>4.0.0</modelVersion>
 
diff --git a/AndroidAnnotations/androidannotations-bundle/pom.xml b/AndroidAnnotations/androidannotations-bundle/pom.xml
index 5f5e71a..a30987b 100644
--- a/AndroidAnnotations/androidannotations-bundle/pom.xml
+++ b/AndroidAnnotations/androidannotations-bundle/pom.xml
@@ -1,4 +1,21 @@
 <?xml version="1.0" encoding="UTF-8"?>
+<!--
+
+    Copyright (C) 2010-2014 eBusiness Information, Excilys Group
+
+    Licensed under the Apache License, Version 2.0 (the "License"); you may not
+    use this file except in compliance with the License. You may obtain a copy of
+    the License at
+
+    http://www.apache.org/licenses/LICENSE-2.0
+
+    Unless required by applicable law or agreed To in writing, software
+    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
+    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
+    License for the specific language governing permissions and limitations under
+    the License.
+
+-->
 <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
 	<modelVersion>4.0.0</modelVersion>
 
diff --git a/AndroidAnnotations/androidannotations-with-codemodel/pom.xml b/AndroidAnnotations/androidannotations-with-codemodel/pom.xml
index 3bdeb79..5cada7d 100644
--- a/AndroidAnnotations/androidannotations-with-codemodel/pom.xml
+++ b/AndroidAnnotations/androidannotations-with-codemodel/pom.xml
@@ -1,4 +1,21 @@
 <?xml version="1.0" encoding="UTF-8"?>
+<!--
+
+    Copyright (C) 2010-2014 eBusiness Information, Excilys Group
+
+    Licensed under the Apache License, Version 2.0 (the "License"); you may not
+    use this file except in compliance with the License. You may obtain a copy of
+    the License at
+
+    http://www.apache.org/licenses/LICENSE-2.0
+
+    Unless required by applicable law or agreed To in writing, software
+    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
+    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
+    License for the specific language governing permissions and limitations under
+    the License.
+
+-->
 <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
 	<modelVersion>4.0.0</modelVersion>
 
diff --git a/AndroidAnnotations/androidannotations/pom.xml b/AndroidAnnotations/androidannotations/pom.xml
index 1b3a74c..8d62c50 100644
--- a/AndroidAnnotations/androidannotations/pom.xml
+++ b/AndroidAnnotations/androidannotations/pom.xml
@@ -1,4 +1,21 @@
 <?xml version="1.0" encoding="UTF-8"?>
+<!--
+
+    Copyright (C) 2010-2014 eBusiness Information, Excilys Group
+
+    Licensed under the Apache License, Version 2.0 (the "License"); you may not
+    use this file except in compliance with the License. You may obtain a copy of
+    the License at
+
+    http://www.apache.org/licenses/LICENSE-2.0
+
+    Unless required by applicable law or agreed To in writing, software
+    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
+    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
+    License for the specific language governing permissions and limitations under
+    the License.
+
+-->
 <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
     <modelVersion>4.0.0</modelVersion>
 
diff --git a/AndroidAnnotations/checkstyle-checks.xml b/AndroidAnnotations/checkstyle-checks.xml
index cf3d012..86c7a75 100644
--- a/AndroidAnnotations/checkstyle-checks.xml
+++ b/AndroidAnnotations/checkstyle-checks.xml
@@ -1,4 +1,21 @@
 <?xml version="1.0" encoding="UTF-8"?>
+<!--
+
+    Copyright (C) 2010-2014 eBusiness Information, Excilys Group
+
+    Licensed under the Apache License, Version 2.0 (the "License"); you may not
+    use this file except in compliance with the License. You may obtain a copy of
+    the License at
+
+    http://www.apache.org/licenses/LICENSE-2.0
+
+    Unless required by applicable law or agreed To in writing, software
+    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
+    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
+    License for the specific language governing permissions and limitations under
+    the License.
+
+-->
 <!DOCTYPE module PUBLIC "-//Puppy Crawl//DTD Check Configuration 1.3//EN" "http://www.puppycrawl.com/dtds/configuration_1_3.dtd">
 
 <module name="Checker">
diff --git a/AndroidAnnotations/functional-test-1-5/pom.xml b/AndroidAnnotations/functional-test-1-5/pom.xml
index c1aa32c..e0a2bc9 100644
--- a/AndroidAnnotations/functional-test-1-5/pom.xml
+++ b/AndroidAnnotations/functional-test-1-5/pom.xml
@@ -1,4 +1,21 @@
 <?xml version="1.0" encoding="UTF-8"?>
+<!--
+
+    Copyright (C) 2010-2014 eBusiness Information, Excilys Group
+
+    Licensed under the Apache License, Version 2.0 (the "License"); you may not
+    use this file except in compliance with the License. You may obtain a copy of
+    the License at
+
+    http://www.apache.org/licenses/LICENSE-2.0
+
+    Unless required by applicable law or agreed To in writing, software
+    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
+    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
+    License for the specific language governing permissions and limitations under
+    the License.
+
+-->
 <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
 	<modelVersion>4.0.0</modelVersion>
 
diff --git a/AndroidAnnotations/pom.xml b/AndroidAnnotations/pom.xml
index 10d457b..dd8d0e7 100644
--- a/AndroidAnnotations/pom.xml
+++ b/AndroidAnnotations/pom.xml
@@ -1,4 +1,21 @@
 <?xml version="1.0" encoding="UTF-8"?>
+<!--
+
+    Copyright (C) 2010-2014 eBusiness Information, Excilys Group
+
+    Licensed under the Apache License, Version 2.0 (the "License"); you may not
+    use this file except in compliance with the License. You may obtain a copy of
+    the License at
+
+    http://www.apache.org/licenses/LICENSE-2.0
+
+    Unless required by applicable law or agreed To in writing, software
+    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
+    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
+    License for the specific language governing permissions and limitations under
+    the License.
+
+-->
 <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
 	<modelVersion>4.0.0</modelVersion>
 
@@ -259,9 +276,9 @@
 		<plugins>
 			<plugin>
 				<!-- Checks for license headers. Use "mvn license:format" to update files. -->
-                <groupId>com.mycila.maven-license-plugin</groupId>
-                <artifactId>maven-license-plugin</artifactId>
-                <version>1.9.0</version>
+                <groupId>com.mycila</groupId>
+                <artifactId>license-maven-plugin</artifactId>
+                <version>2.6</version>
 				<configuration>
 					<header>src/etc/header.txt</header>
 					<excludes>

From f82dc918a1f2565a016244db932d2834621e2fec Mon Sep 17 00:00:00 2001
From: WonderCsabo <kozakcsabi@gmail.com>
Date: Mon, 20 Oct 2014 17:48:46 +0200
Subject: [PATCH 3/5] Check JavaDoc with Checkstyle for all annotations

---
 .../annotations/AfterTextChange.java               |  8 +++++
 .../androidannotations/annotations/Background.java |  4 ---
 .../org/androidannotations/annotations/Bean.java   |  4 +++
 .../annotations/BeforeTextChange.java              |  6 ++++
 .../annotations/CheckedChange.java                 | 10 +++++-
 .../org/androidannotations/annotations/Click.java  |  9 ++++-
 .../annotations/CustomTitle.java                   |  4 +++
 .../androidannotations/annotations/EActivity.java  |  7 ++++
 .../org/androidannotations/annotations/EBean.java  | 12 +++++--
 .../androidannotations/annotations/EFragment.java  |  7 ++++
 .../androidannotations/annotations/EViewGroup.java |  7 ++++
 .../annotations/EditorAction.java                  | 14 +++++---
 .../org/androidannotations/annotations/Extra.java  |  3 ++
 .../annotations/FocusChange.java                   |  9 +++--
 .../annotations/FragmentArg.java                   |  3 ++
 .../annotations/FragmentById.java                  | 12 +++++--
 .../annotations/FragmentByTag.java                 |  9 +++--
 .../androidannotations/annotations/FromHtml.java   |  7 ++++
 .../annotations/HttpsClient.java                   | 25 +++++++++++++
 .../androidannotations/annotations/ItemClick.java  | 11 ++++--
 .../annotations/ItemLongClick.java                 | 11 ++++--
 .../androidannotations/annotations/ItemSelect.java | 11 ++++--
 .../androidannotations/annotations/LongClick.java  |  7 ++++
 .../annotations/OnActivityResult.java              |  7 ++++
 .../annotations/OptionsItem.java                   |  6 ++++
 .../annotations/OptionsMenu.java                   |  7 ++++
 .../annotations/OptionsMenuItem.java               | 11 ++++--
 .../androidannotations/annotations/OrmLiteDao.java |  4 +++
 .../androidannotations/annotations/Receiver.java   | 34 ++++++++++++++++--
 .../annotations/ReceiverAction.java                | 16 ++++++---
 .../org/androidannotations/annotations/ResId.java  | 10 ++++++
 .../androidannotations/annotations/RoboGuice.java  |  2 --
 .../annotations/SeekBarProgressChange.java         |  6 ++++
 .../annotations/SeekBarTouchStart.java             |  6 ++++
 .../annotations/SeekBarTouchStop.java              |  6 ++++
 .../annotations/ServiceAction.java                 |  2 --
 .../annotations/SupposeBackground.java             |  4 +--
 .../androidannotations/annotations/TextChange.java |  6 ++++
 .../org/androidannotations/annotations/Touch.java  |  7 ++++
 .../org/androidannotations/annotations/Trace.java  | 11 +++++-
 .../androidannotations/annotations/UiThread.java   | 28 ++++++++++-----
 .../androidannotations/annotations/ViewById.java   |  7 ++++
 .../androidannotations/annotations/ViewsById.java  | 32 ++++++++++-------
 .../androidannotations/annotations/WakeLock.java   | 41 ++++++++++++++++++----
 .../annotations/WindowFeature.java                 |  4 ++-
 .../annotations/res/AnimationRes.java              |  7 ++++
 .../annotations/res/BooleanRes.java                |  7 ++++
 .../annotations/res/ColorRes.java                  |  7 ++++
 .../annotations/res/ColorStateListRes.java         |  8 +++++
 .../annotations/res/DimensionPixelOffsetRes.java   |  8 +++++
 .../annotations/res/DimensionPixelSizeRes.java     |  8 +++++
 .../annotations/res/DimensionRes.java              |  7 ++++
 .../annotations/res/DrawableRes.java               |  7 ++++
 .../annotations/res/HtmlRes.java                   |  7 ++++
 .../annotations/res/IntArrayRes.java               |  7 ++++
 .../annotations/res/IntegerRes.java                |  7 ++++
 .../annotations/res/LayoutRes.java                 |  7 ++++
 .../annotations/res/MovieRes.java                  |  7 ++++
 .../annotations/res/StringArrayRes.java            |  6 ++++
 .../annotations/res/StringRes.java                 |  6 ++++
 .../annotations/res/TextArrayRes.java              |  7 ++++
 .../annotations/res/TextRes.java                   |  7 ++++
 .../annotations/rest/Accept.java                   |  6 ++++
 .../annotations/rest/Delete.java                   |  4 +++
 .../androidannotations/annotations/rest/Get.java   |  4 +++
 .../androidannotations/annotations/rest/Head.java  |  4 +++
 .../annotations/rest/Options.java                  |  4 +++
 .../androidannotations/annotations/rest/Post.java  |  4 +++
 .../androidannotations/annotations/rest/Put.java   |  4 +++
 .../annotations/rest/RequiresCookie.java           |  4 +++
 .../annotations/rest/RequiresCookieInUrl.java      |  4 +++
 .../annotations/rest/RequiresHeader.java           |  4 +++
 .../androidannotations/annotations/rest/Rest.java  | 15 ++++++++
 .../annotations/rest/SetsCookie.java               |  3 ++
 .../sharedpreferences/DefaultBoolean.java          |  7 ++++
 .../sharedpreferences/DefaultFloat.java            |  7 ++++
 .../annotations/sharedpreferences/DefaultInt.java  |  7 ++++
 .../annotations/sharedpreferences/DefaultLong.java |  7 ++++
 .../annotations/sharedpreferences/DefaultRes.java  | 11 ++++++
 .../sharedpreferences/DefaultString.java           |  7 ++++
 .../sharedpreferences/DefaultStringSet.java        |  3 ++
 .../annotations/sharedpreferences/SharedPref.java  | 37 ++++++++++++++++++-
 AndroidAnnotations/checkstyle-checks.xml           |  7 ++--
 AndroidAnnotations/checkstyle-suppressions.xml     | 25 +++++++++++++
 AndroidAnnotations/pom.xml                         |  1 +
 85 files changed, 681 insertions(+), 75 deletions(-)
 create mode 100644 AndroidAnnotations/checkstyle-suppressions.xml

diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterTextChange.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterTextChange.java
index f448bb0..bc726c2 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterTextChange.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterTextChange.java
@@ -74,8 +74,16 @@
 @Target(ElementType.METHOD)
 public @interface AfterTextChange {
 
+	/**
+	 * The R.id.* fields which refer to the TextViews.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as a strings which refer to the TextViews.
+	 * 
+	 * @return
+	 */
 	String[] resName() default "";
 
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Background.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Background.java
index e59a2c2..2712949 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Background.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Background.java
@@ -181,15 +181,12 @@
 	 * boolean mayInterruptIfRunning = true;
 	 * BackgroundExecutor.cancelAll(&quot;my_background_id&quot;, mayInterruptIfRunning);
 	 * </pre>
-	 *
-	 * @return the identifier for task cancellation
 	 **/
 	String id() default "";
 
 	/**
 	 * Minimum delay, in milliseconds, before the background task is executed.
 	 *
-	 * @return the minimum delay before execution
 	 */
 	int delay() default 0;
 
@@ -199,7 +196,6 @@
 	 * All background tasks having the same <code>serial</code> will be executed
 	 * sequentially.
 	 *
-	 * @return the serial execution group
 	 **/
 	String serial() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Bean.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Bean.java
index b60a062..bf0a726 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Bean.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Bean.java
@@ -60,5 +60,9 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface Bean {
+
+	/**
+	 * The implementation class.
+	 */
 	Class<?> value() default Void.class;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/BeforeTextChange.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/BeforeTextChange.java
index 2b97910..66219aa 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/BeforeTextChange.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/BeforeTextChange.java
@@ -82,8 +82,14 @@
 @Target(ElementType.METHOD)
 public @interface BeforeTextChange {
 
+	/**
+	 * The R.id.* fields which refer to the TextViews.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as a strings which refer to the TextViews.
+	 */
 	String[] resName() default "";
 
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CheckedChange.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CheckedChange.java
index b1c7b29..2b39ab4 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CheckedChange.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CheckedChange.java
@@ -69,12 +69,20 @@
  * 
  * </blockquote>
  * 
- * @author Rostislav Chekan
  */
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface CheckedChange {
+
+	/**
+	 * The R.id.* fields which refer to the CompoundButtons or subclasses of
+	 * CompoundButton.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as strings which refer to the CompoundButtons or
+	 * subclasses of CompoundButton.
+	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Click.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Click.java
index b5390be..4a4423e 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Click.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Click.java
@@ -34,7 +34,8 @@
  * The method MAY have one parameter:
  * </p>
  * <ul>
- * <li>A {@link android.view.View} parameter to know which view has been clicked</li>
+ * <li>A {@link android.view.View} parameter to know which view has been clicked
+ * </li>
  * </ul>
  * <blockquote>
  * 
@@ -61,8 +62,14 @@
 @Target(ElementType.METHOD)
 public @interface Click {
 
+	/**
+	 * The R.id.* fields which refer to the Views.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as strings which refer to the Views.
+	 */
 	String[] resName() default "";
 
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CustomTitle.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CustomTitle.java
index 0688073..a9f638b 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CustomTitle.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CustomTitle.java
@@ -44,5 +44,9 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.TYPE)
 public @interface CustomTitle {
+
+	/**
+	 * R.layout.* field which refers the the title layout.
+	 */
 	int value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EActivity.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EActivity.java
index eba0f85..8bbb3e2 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EActivity.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EActivity.java
@@ -87,7 +87,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.TYPE)
 public @interface EActivity {
+
+	/**
+	 * The R.layout.* field which refer to the layout.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as a string which refer to the layout.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EBean.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EBean.java
index 2a81eea..c5c209a 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EBean.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EBean.java
@@ -41,8 +41,8 @@
  * Most annotations are supported in {@link EBean} classes, except the ones
  * related to extras. Views related annotations will only work if the bean was
  * injected in an activity with a layout containing the views you're dealing
- * with. If your bean needs a {@link android.content.Context} you can inject on by
- * using an {@link RootContext} annotated field.
+ * with. If your bean needs a {@link android.content.Context} you can inject on
+ * by using an {@link RootContext} annotated field.
  * </p>
  * <p>
  * Beans have two possible scopes : default or singleton. Default scope should
@@ -91,10 +91,13 @@
 @Target(ElementType.TYPE)
 public @interface EBean {
 
+	/**
+	 * The scope of an enhanced bean.
+	 */
 	public enum Scope {
 
 		/**
-		 * A new instance of the bean is created each time it is needed
+		 * A new instance of the bean is created each time it is needed.
 		 */
 		Default, //
 
@@ -105,6 +108,9 @@
 		Singleton, //
 	}
 
+	/**
+	 * The scope of the enhanced bean.
+	 */
 	Scope scope() default Scope.Default;
 
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EFragment.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EFragment.java
index 395fa97..f9b716b 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EFragment.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EFragment.java
@@ -108,8 +108,15 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.TYPE)
 public @interface EFragment {
+
+	/**
+	 * The R.layout.* field which refer to the layout.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as a string which refer to the layout.
+	 */
 	String resName() default "";
 
 	/**
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EViewGroup.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EViewGroup.java
index 4d58be2..fbb59d7 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EViewGroup.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EViewGroup.java
@@ -75,7 +75,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.TYPE)
 public @interface EViewGroup {
+
+	/**
+	 * The R.layout.* field which refer to the layout.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as a string which refer to the layout.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EditorAction.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EditorAction.java
index 9cfb53d..245a20f 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EditorAction.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EditorAction.java
@@ -57,22 +57,22 @@
  * void onEditorActionsOnHelloTextView(TextView hello, int actionId, KeyEvent keyEvent) {
  * 	// Something Here
  * }
- *
+ * 
  * &#064;EditorAction
  * void <b>helloTextView</b>EditorAction(TextView hello) {
  * 	// Something Here
  * }
- *
+ * 
  * &#064;EditorAction(<b>{R.id.editText, R.id.helloTextView}</b>)
  * void onEditorActionsOnSomeTextViews(TextView tv, int actionId) {
  * 	// Something Here
  * }
- *
+ * 
  * &#064;EditorAction(<b>R.id.helloTextView</b>)
  * void onEditorActionsOnHelloTextView() {
  * 	// Something Here
  * }
- *
+ * 
  * &#064;EditorAction(R.id.helloTextView)
  * <b>boolean</b> onEditorActionsOnHelloTextView() {
  * 	// Something Here
@@ -86,8 +86,14 @@
 @Target(ElementType.METHOD)
 public @interface EditorAction {
 
+	/**
+	 * The R.id.* fields which refer to the TextViews.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as a strings which refer to the TextViews.
+	 */
 	String[] resName() default "";
 
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Extra.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Extra.java
index 80b3b5b..bbd2b1f 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Extra.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Extra.java
@@ -80,5 +80,8 @@
 @Target(ElementType.FIELD)
 public @interface Extra {
 
+	/**
+	 * The key of the injected extra.
+	 */
 	String value() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FocusChange.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FocusChange.java
index b763251..903442d 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FocusChange.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FocusChange.java
@@ -68,13 +68,18 @@
  * </pre>
  * 
  * </blockquote>
- * 
- * @author Rostislav Chekan
  */
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface FocusChange {
+
+	/**
+	 * The R.id.* fields which refer to the Views.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as strings which refer to the Views.
+	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentArg.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentArg.java
index 6b17c13..4780f78 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentArg.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentArg.java
@@ -72,5 +72,8 @@
 @Target(ElementType.FIELD)
 public @interface FragmentArg {
 
+	/**
+	 * The key of the injected Fragment argument.
+	 */
 	String value() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentById.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentById.java
index 7dfed34..066d372 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentById.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentById.java
@@ -22,9 +22,8 @@
 
 /**
  * <p>
- * Use it on android.app.Fragment or
- * android.support.v4.app.Fragment fields in activity classes to
- * retrieve and inject a fragment.
+ * Use it on android.app.Fragment or android.support.v4.app.Fragment fields in
+ * activity classes to retrieve and inject a fragment.
  * </p>
  * <p>
  * The annotation value should be one of R.id.* fields. If not set, the field
@@ -72,7 +71,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface FragmentById {
+
+	/**
+	 * The R.id.* field which is the id of the Fragment.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name which refers to the id of the Fragment.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentByTag.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentByTag.java
index 47ccc7a..c936d9f 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentByTag.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentByTag.java
@@ -22,9 +22,8 @@
 
 /**
  * <p>
- * Use it on android.app.Fragment or
- * android.support.v4.app.Fragment fields in activity classes to
- * retrieve and inject a fragment.
+ * Use it on android.app.Fragment or android.support.v4.app.Fragment fields in
+ * activity classes to retrieve and inject a fragment.
  * </p>
  * <p>
  * The annotation value should be one of fragment tag. If not set, the field
@@ -73,5 +72,9 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface FragmentByTag {
+
+	/**
+	 * The tag of the Fragment.
+	 */
 	String value() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FromHtml.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FromHtml.java
index 3839765..d40e747 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FromHtml.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FromHtml.java
@@ -58,7 +58,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface FromHtml {
+
+	/**
+	 * The R.string.* field which refers to the html string resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name which refers to the html string resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/HttpsClient.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/HttpsClient.java
index d32a707..9d7638c 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/HttpsClient.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/HttpsClient.java
@@ -89,19 +89,44 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface HttpsClient {
+
+	/**
+	 * The default value of {@link #trustStorePwd()} and {@link #keyStorePwd()}.
+	 */
 	String DEFAULT_PASSWD = "changeit";
 
+	/**
+	 * The R.id.* field id which refers to the trust store file.
+	 */
 	int trustStore() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name which refers to the trust store file.
+	 */
 	String trustStoreResName() default "";
 
+	/**
+	 * The trust store password.
+	 */
 	String trustStorePwd() default DEFAULT_PASSWD;
 
+	/**
+	 * The R.id.* field id which refers to the key store file.
+	 */
 	int keyStore() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name which refers to the key store file.
+	 */
 	String keyStoreResName() default "";
 
+	/**
+	 * The key store password.
+	 */
 	String keyStorePwd() default DEFAULT_PASSWD;
 
+	/**
+	 * <b>true</b> if authorizes any TLS/SSL hostname, <b>false</b> otherwise.
+	 */
 	boolean allowAllHostnames() default true;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemClick.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemClick.java
index 6c8be37..9e75752 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemClick.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemClick.java
@@ -23,8 +23,8 @@
 /**
  * <p>
  * This annotation is intended to be used on methods to receive events defined
- * by {@link
- * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)}
+ * by
+ * {@link android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)}
  * when a list item has been clicked by the user.
  * </p>
  * <p>
@@ -68,7 +68,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface ItemClick {
+
+	/**
+	 * The R.id.* fields which refer to the AdapterViews.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as strings which refer to the AdapterViews.
+	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemLongClick.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemLongClick.java
index 34b897e..4bc1fd9 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemLongClick.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemLongClick.java
@@ -23,8 +23,8 @@
 /**
  * <p>
  * This annotation is intended to be used on methods to receive events defined
- * by {@link
- * android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android.widget.AdapterView, android.view.View, int, long)}
+ * by
+ * {@link android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android.widget.AdapterView, android.view.View, int, long)}
  * when a list item has been long clicked by the user.
  * </p>
  * <p>
@@ -68,7 +68,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface ItemLongClick {
+
+	/**
+	 * The R.id.* fields which refer to the AdapterViews.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as strings which refer to the AdapterViews.
+	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemSelect.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemSelect.java
index 9afa8d7..6b9fd16 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemSelect.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemSelect.java
@@ -23,8 +23,8 @@
 /**
  * <p>
  * This annotation is intended to be used on methods to receive events defined
- * by {@link
- * android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)}
+ * by
+ * {@link android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)}
  * when a list item has been selected by the user.
  * </p>
  * <p>
@@ -74,7 +74,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface ItemSelect {
+
+	/**
+	 * The R.id.* fields which refer to the AdapterViews.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as strings which refer to the AdapterViews.
+	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/LongClick.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/LongClick.java
index 9600cbb..88ad78a 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/LongClick.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/LongClick.java
@@ -73,7 +73,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface LongClick {
+
+	/**
+	 * The R.id.* fields which refer to the Views.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as strings which refer to the Views.
+	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OnActivityResult.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OnActivityResult.java
index de12ea8..0142a83 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OnActivityResult.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OnActivityResult.java
@@ -78,6 +78,9 @@
 @Target(ElementType.METHOD)
 public @interface OnActivityResult {
 
+	/**
+	 * The <b>requestCode</b> associated with the given result.
+	 */
 	int value();
 
 	/**
@@ -119,6 +122,10 @@
 	@Retention(RetentionPolicy.CLASS)
 	@Target(ElementType.PARAMETER)
 	public @interface Extra {
+
+		/**
+		 * They key of the result data.
+		 */
 		String value() default "";
 	}
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsItem.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsItem.java
index 7f61d68..105c57c 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsItem.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsItem.java
@@ -77,8 +77,14 @@
 @Target(ElementType.METHOD)
 public @interface OptionsItem {
 
+	/**
+	 * The R.id.* field which refers to the menu item.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name which refers to the menu item.
+	 */
 	String[] resName() default "";
 
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenu.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenu.java
index d491cd5..d8701fa 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenu.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenu.java
@@ -57,7 +57,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.TYPE)
 public @interface OptionsMenu {
+
+	/**
+	 * The R.menu.* fields which refers to the menus.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as strings which refers to the menus.
+	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenuItem.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenuItem.java
index 01782c7..c0f41f9 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenuItem.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenuItem.java
@@ -22,9 +22,8 @@
 
 /**
  * <p>
- * Use it on android.app.Fragment or
- * android.support.v4.app.Fragment fields in activity classes to inject
- * a menu item.
+ * Use it on android.app.Fragment or android.support.v4.app.Fragment fields in
+ * activity classes to inject a menu item.
  * </p>
  * <p>
  * The field MUST be of type {@link android.view.MenuItem} or
@@ -58,8 +57,14 @@
 @Target(ElementType.FIELD)
 public @interface OptionsMenuItem {
 
+	/**
+	 * The R.id.* fields which refers to the menu items.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as strings which refers to the menu items.
+	 */
 	String[] resName() default "";
 
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OrmLiteDao.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OrmLiteDao.java
index dee6596..67701fb 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OrmLiteDao.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OrmLiteDao.java
@@ -67,9 +67,13 @@
 @Target(ElementType.FIELD)
 public @interface OrmLiteDao {
 
+	/**
+	 * The class of the used database helper.
+	 */
 	Class<?> helper();
 
 	/**
+	 * The class of the model what this DAO can manage.
 	 * 
 	 * @deprecated Since <b>3.1</b> the model class is inferred from the type of
 	 *             the annotated field. This parameter will be removed in a
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Receiver.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Receiver.java
index 3ea69aa..526cd5d 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Receiver.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Receiver.java
@@ -86,16 +86,46 @@
 @Target(ElementType.METHOD)
 public @interface Receiver {
 
+	/**
+	 * The strings indicating the actions which will spark the method.
+	 */
 	String[] actions();
 
+	/**
+	 * The strings indicating the data schemes which should be handled.
+	 */
 	String[] dataSchemes() default {};
 
+	/**
+	 * The event pair when the receiver should be registered/unregistered.
+	 */
 	RegisterAt registerAt() default RegisterAt.OnCreateOnDestroy;
 
+	/**
+	 * <b>true</b>, if LocalBroadcastManager should be used.
+	 */
 	boolean local() default false;
 
+	/**
+	 * Represents event pairs for BroadcastReceiver registration/unregistration.
+	 */
 	public enum RegisterAt {
-		OnCreateOnDestroy, OnStartOnStop, OnResumeOnPause, OnAttachOnDetach
+		/**
+		 * Register in the onCreate method, unregister in the onDestroy method.
+		 */
+		OnCreateOnDestroy, //
+		/**
+		 * Register in the onStart method, unregister in the onStop method.
+		 */
+		OnStartOnStop, //
+		/**
+		 * Register in the onResume method, unregister in the onPause method.
+		 */
+		OnResumeOnPause, //
+		/**
+		 * Register in the onAttach method, unregister in the onDetach method.
+		 */
+		OnAttachOnDetach
 	}
 
 	/**
@@ -115,8 +145,6 @@
 		/**
 		 * Define the extra's name. If this parameter isn't set the annotated
 		 * parameter name will be used.
-		 *
-		 * @return the extra's name
 		 */
 		String value() default "";
 	}
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ReceiverAction.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ReceiverAction.java
index c953588..c4e4069 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ReceiverAction.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ReceiverAction.java
@@ -30,7 +30,15 @@
  * The class MAY contain several {@link ReceiverAction} annotated methods.
  * </p>
  * <p>
- * The method annotated with {@link ReceiverAction} may have as parameters :
+ * The method annotated with {@link ReceiverAction} may have as parameters : - A
+ * {@link android.content.Context} which will be the context given in
+ * {@code void onReceive(Context context, Intent intent)} - A
+ * {@link android.content.Intent} which will be the intent given in
+ * {@code void onReceive(Context context, Intent intent)} - Some any native,
+ * {@link android.os.Parcelable} or {@link java.io.Serializable} parameters
+ * annotated with {@link ReceiverAction.Extra} which will be the extra put in
+ * the intent. The key of this extra is the value of the annotation
+ * {@link ReceiverAction.Extra} if set or the name of the parameter.
  * </p>
  * <ul>
  * <li>A {@link android.content.Context} which will be the context given in
@@ -96,8 +104,6 @@
 	/**
 	 * Define a set of actions this method should handle. If this field isn't
 	 * set the annotated method name will be used.
-	 *
-	 * @return the actions
 	 */
 	String[] value() default {};
 
@@ -126,8 +132,8 @@
 		/**
 		 * Define the extra's name. If this parameter isn't set the annotated
 		 * parameter name will be used.
-		 *
-		 * @return the extra's name
+		 * 
+		 * @return the name of the extra
 		 */
 		String value() default "";
 	}
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ResId.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ResId.java
index 2843e5f..a0b740e 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ResId.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ResId.java
@@ -15,10 +15,20 @@
  */
 package org.androidannotations.annotations;
 
+/**
+ * This class holds the default resource id.
+ */
 public abstract class ResId {
 
+	/**
+	 * This value is used when the client does not defines the resource id for a
+	 * resource.
+	 */
 	public static final int DEFAULT_VALUE = -1;
 
+	/**
+	 * Utility classes should not be instantiated.
+	 */
 	private ResId() {
 	}
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/RoboGuice.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/RoboGuice.java
index 9dbb704..0f7a73b 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/RoboGuice.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/RoboGuice.java
@@ -68,8 +68,6 @@
 	/**
 	 * The RoboGuice listener classes to bind to this activity. Will add an
 	 * injected listener field to the subclass.
-	 *
-	 * @return The RoboGuice listener classes to bind to this activity
 	 */
 	Class<?>[] value() default {};
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarProgressChange.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarProgressChange.java
index adaf5a4..c31ec5c 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarProgressChange.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarProgressChange.java
@@ -82,8 +82,14 @@
 @Target(ElementType.METHOD)
 public @interface SeekBarProgressChange {
 
+	/**
+	 * The R.id.* fields which refer to the SeekBars.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as strings which refer to the SeekBars.
+	 */
 	String[] resName() default "";
 
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStart.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStart.java
index 60f33d0..7670cba 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStart.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStart.java
@@ -78,8 +78,14 @@
 @Target(ElementType.METHOD)
 public @interface SeekBarTouchStart {
 
+	/**
+	 * The R.id.* fields which refer to the SeekBars.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as strings which refer to the SeekBars.
+	 */
 	String[] resName() default "";
 
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStop.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStop.java
index 8b696f4..c71cffe 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStop.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStop.java
@@ -78,8 +78,14 @@
 @Target(ElementType.METHOD)
 public @interface SeekBarTouchStop {
 
+	/**
+	 * The R.id.* fields which refer to the SeekBars.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as strings which refer to the SeekBars.
+	 */
 	String[] resName() default "";
 
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ServiceAction.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ServiceAction.java
index 3803cfa..a2822fa 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ServiceAction.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ServiceAction.java
@@ -96,8 +96,6 @@
 	/**
 	 * Define the action's name. If this field isn't set the annotated method
 	 * name will be used.
-	 *
-	 * @return the action's name
 	 */
 	String value() default "";
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeBackground.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeBackground.java
index 31c690a..3686947 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeBackground.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeBackground.java
@@ -60,8 +60,8 @@
 public @interface SupposeBackground {
 
 	/**
-	 * @return Allowed serials to restrict a calling thread. If it is an empty
-	 *         list, then any background thread is allowed.
+	 * Allowed serials to restrict a calling thread. If it is an empty list,
+	 * then any background thread is allowed.
 	 *
 	 * @see org.androidannotations.api.BackgroundExecutor#checkBgThread(String...)
 	 */
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/TextChange.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/TextChange.java
index 0eb6269..5622594 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/TextChange.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/TextChange.java
@@ -81,8 +81,14 @@
 @Target(ElementType.METHOD)
 public @interface TextChange {
 
+	/**
+	 * The R.id.* fields which refer to the TextViews.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as a strings which refer to the TextViews.
+	 */
 	String[] resName() default "";
 
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Touch.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Touch.java
index 15f7c71..ed3ccd5 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Touch.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Touch.java
@@ -74,7 +74,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface Touch {
+
+	/**
+	 * The R.id.* fields which refer to the Views.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource names as strings which refer to the Views.
+	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Trace.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Trace.java
index 357cfe7..826550e 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Trace.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Trace.java
@@ -29,7 +29,7 @@
  * </p>
  * <p>
  * Since <i>AndroidAnnotations 3.1</i> log messages contain the method parameter
- * and return values
+ * and return values.
  * </p>
  * <p>
  * All annotation values are optional :
@@ -72,10 +72,19 @@
 @Target(ElementType.METHOD)
 public @interface Trace {
 
+	/**
+	 * The string indicating that no tag was given for the log message.
+	 */
 	String DEFAULT_TAG = "NO_TAG";
 
+	/**
+	 * The tag used for the log message.
+	 */
 	String tag() default DEFAULT_TAG;
 
+	/**
+	 * The log level used for the log message.
+	 */
 	int level() default Log.INFO;
 
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/UiThread.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/UiThread.java
index 8d462ce..fcb5a18 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/UiThread.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/UiThread.java
@@ -93,20 +93,32 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface UiThread {
+
+	/**
+	 * The delay of the execution in milliseconds.
+	 */
 	long delay() default 0;
 
 	/**
-	 * If propagation = REUSE, the method will check first if it is inside the
-	 * UI thread already. If so, it will directly call the method instead of
-	 * using the handler. The default value is ENQUEUE, which will always call
-	 * the handler.
-	 * 
-	 * @return whether the method should be posted or executed if it's in the UI
-	 *         thread
+	 * If propagation is {@link Propagation#REUSE}, the method will check first
+	 * if it is inside the UI thread already. If so, it will directly call the
+	 * method instead of using the handler. The default value is
+	 * {@link Propagation#ENQUEUE}, which will always call the handler.
 	 */
 	Propagation propagation() default Propagation.ENQUEUE;
 
+	/**
+	 * Indicates the propagation behavior of the UiThread annotated method.
+	 */
 	public enum Propagation {
-		ENQUEUE, REUSE
+
+		/**
+		 * The method will always call the Handler.
+		 */
+		ENQUEUE, //
+		/**
+		 * The method will check first if it is inside the UI thread already.
+		 */
+		REUSE
 	}
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewById.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewById.java
index f28a584..80cc5ba 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewById.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewById.java
@@ -63,7 +63,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface ViewById {
+
+	/**
+	 * The R.id.* field which refers to the injected View.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name which refers to the injected View.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewsById.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewsById.java
index 5cb959f..2973bcb 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewsById.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewsById.java
@@ -22,16 +22,18 @@
 
 /**
  * <p>
- * Use it on a {@link java.util.List} of {@link android.view.View} or {@link android.view.View} subtype
- * fields in a view related (ie {@link org.androidannotations.annotations.EActivity}, {@link org.androidannotations.annotations.EFragment},
+ * Use it on a {@link java.util.List} of {@link android.view.View} or
+ * {@link android.view.View} subtype fields in a view related (ie
+ * {@link org.androidannotations.annotations.EActivity},
+ * {@link org.androidannotations.annotations.EFragment},
  * {@link org.androidannotations.annotations.EViewGroup}, ...) annotated class.
  * </p>
  * <p>
  * The annotation value should be an array of R.id.* fields.
  * </p>
  * <p>
- * Your code related to injected views should go in an {@link org.androidannotations.annotations.AfterViews}
- * annotated method.
+ * Your code related to injected views should go in an
+ * {@link org.androidannotations.annotations.AfterViews} annotated method.
  * </p>
  * <blockquote>
  *
@@ -40,19 +42,19 @@
  * <pre>
  * &#064;EActivity(R.layout.main)
  * public class MyActivity extends Activity {
- *
+ * 
  * 	// Injects R.id.edit1 and R.id.edit2 into the List.
- * 	&#064;ViewsById({R.id.edit1, R.id.edit2})
+ * 	&#064;ViewsById({ R.id.edit1, R.id.edit2 })
  * 	List&lt;EditText&gt; myEditTexts;
- *
- * 	&#064;ViewsById({R.id.myTextView1, R.id.myOtherTextView})
+ * 
+ * 	&#064;ViewsById({ R.id.myTextView1, R.id.myOtherTextView })
  * 	List&lt;TextView&gt; textViews;
- *
+ * 
  * 	&#064;AfterViews
  * 	void updateTextWithDate() {
- * 	    for (TextView textView : textViews) {
- *   		textView.setText(&quot;Date: &quot; + new Date());
- *   	}
+ * 		for (TextView textView : textViews) {
+ * 			textView.setText(&quot;Date: &quot; + new Date());
+ * 		}
  * 	}
  * }
  * </pre>
@@ -65,7 +67,13 @@
 @Target(ElementType.FIELD)
 public @interface ViewsById {
 
+	/**
+	 * The array of R.id.* fields which refer to the injected Views.
+	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The array resource names which refer to the injected Views.
+	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WakeLock.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WakeLock.java
index 875af7a..45268e3 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WakeLock.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WakeLock.java
@@ -52,44 +52,73 @@
 @Target(ElementType.METHOD)
 public @interface WakeLock {
 
+	/**
+	 * Indicates the client did not give a tag.
+	 */
 	String DEFAULT_TAG = "NO_TAG";
 
+	/**
+	 * The tag of the WakeLock.
+	 * 
+	 * @see {@link android.os.PowerManager#newWakeLock(int, String)}
+	 */
 	String tag() default DEFAULT_TAG;
 
+	/**
+	 * The level of the created {@link android.os.PowerManager.WakeLock
+	 * WakeLock}.
+	 * 
+	 * @see {@link android.os.PowerManager#newWakeLock(int, String)}
+	 */
 	Level level() default Level.PARTIAL_WAKE_LOCK;
 
+	/**
+	 * The optional flags for the created
+	 * {@link android.os.PowerManager.WakeLock WakeLock}.
+	 * 
+	 * @see {@link android.os.PowerManager#newWakeLock(int, String)}
+	 */
 	Flag[] flags() default {};
 
+	/**
+	 * A convenience wrapper enum for the
+	 * {@link android.os.PowerManager.WakeLock WakeLock} level integer values.
+	 */
 	public enum Level {
 		/**
-		 * @see android.os.PowerManager#FULL_WAKE_LOCK
+		 * Represents {@link android.os.PowerManager#FULL_WAKE_LOCK}.
 		 */
 		FULL_WAKE_LOCK,
 
 		/**
-		 * @see android.os.PowerManager#PARTIAL_WAKE_LOCK
+		 * Represents {@link android.os.PowerManager#PARTIAL_WAKE_LOCK}.
 		 */
 		PARTIAL_WAKE_LOCK,
 
 		/**
-		 * @see android.os.PowerManager#SCREEN_BRIGHT_WAKE_LOCK
+		 * Represents {@link android.os.PowerManager#SCREEN_BRIGHT_WAKE_LOCK}.
 		 */
 		SCREEN_BRIGHT_WAKE_LOCK,
 
 		/**
-		 * @see android.os.PowerManager#SCREEN_DIM_WAKE_LOCK
+		 * Represents {@link android.os.PowerManager#SCREEN_DIM_WAKE_LOCK}.
 		 */
 		SCREEN_DIM_WAKE_LOCK;
 	}
 
+	/**
+	 * A convenience wrapper enum for the
+	 * {@link android.os.PowerManager.WakeLock WakeLock} flag integer values.
+	 */
 	public enum Flag {
+
 		/**
-		 * @see android.os.PowerManager#ACQUIRE_CAUSES_WAKEUP
+		 * Represents {@link android.os.PowerManager#ACQUIRE_CAUSES_WAKEUP}.
 		 */
 		ACQUIRE_CAUSES_WAKEUP,
 
 		/**
-		 * @see android.os.PowerManager#ON_AFTER_RELEASE
+		 * Represents {@link android.os.PowerManager#ON_AFTER_RELEASE}.
 		 */
 		ON_AFTER_RELEASE
 	}
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WindowFeature.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WindowFeature.java
index c0e56a2..720a7a1 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WindowFeature.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WindowFeature.java
@@ -51,6 +51,8 @@
 @Target(ElementType.TYPE)
 public @interface WindowFeature {
 
+	/**
+	 * An array of integers which are <code>Window.FEATURE_*</code> fields.
+	 */
 	int[] value();
-
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/AnimationRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/AnimationRes.java
index c844959..ad54416 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/AnimationRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/AnimationRes.java
@@ -35,7 +35,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface AnimationRes {
+
+	/**
+	 * The R.anim.* field which refers to the animation.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name which refers to the animation.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/BooleanRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/BooleanRes.java
index 8a4ceed..014c296 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/BooleanRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/BooleanRes.java
@@ -35,7 +35,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface BooleanRes {
+
+	/**
+	 * The R.boolean.* field which refers to the boolean resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as a string which refers to the boolean resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorRes.java
index f72b18f..2d2695e 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorRes.java
@@ -35,7 +35,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface ColorRes {
+
+	/**
+	 * The R.color.* field which refers to the color resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as a string which refers to the color resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorStateListRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorStateListRes.java
index 332e161..85d2fc5 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorStateListRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorStateListRes.java
@@ -35,7 +35,15 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface ColorStateListRes {
+
+	/**
+	 * The R.color.* field which refers to the ColorStateList resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as a string which refers to the ColorStateList
+	 * resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelOffsetRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelOffsetRes.java
index 3ec4af1..2f14148 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelOffsetRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelOffsetRes.java
@@ -36,7 +36,15 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface DimensionPixelOffsetRes {
+
+	/**
+	 * The R.dimen.* field which refers to the dimension pixel offset resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as string which refers to the dimension pixel offset
+	 * resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelSizeRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelSizeRes.java
index d78ee23..dd31005 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelSizeRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelSizeRes.java
@@ -36,7 +36,15 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface DimensionPixelSizeRes {
+
+	/**
+	 * The R.dimen.* field which refers to the dimension pixel size resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as string which refers to the dimension pixel size
+	 * resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionRes.java
index 2461ac7..dc4a3d7 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionRes.java
@@ -35,7 +35,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface DimensionRes {
+
+	/**
+	 * The resource name as string which refers to the dimension resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as string which refers to the dimension resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DrawableRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DrawableRes.java
index 8e23731..1fc1212 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DrawableRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DrawableRes.java
@@ -35,7 +35,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface DrawableRes {
+
+	/**
+	 * The R.id.* field which refers to the drawable resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as a string which refers to the drawable resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/HtmlRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/HtmlRes.java
index 378a777..acc9e27 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/HtmlRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/HtmlRes.java
@@ -36,7 +36,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface HtmlRes {
+
+	/**
+	 * The R.id.* field which refers to the HTML string resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as a string which refers to the HTML string resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntArrayRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntArrayRes.java
index b886468..b266e7f 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntArrayRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntArrayRes.java
@@ -36,7 +36,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface IntArrayRes {
+
+	/**
+	 * The R.array.* field which refers to the integer array resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name which refers to the integer array resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntegerRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntegerRes.java
index 3f2aea6..632b313 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntegerRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntegerRes.java
@@ -35,7 +35,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface IntegerRes {
+
+	/**
+	 * The R.integer.* field which refers to the integer resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as a string which refers to the integer resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/LayoutRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/LayoutRes.java
index b818c67..31c587a 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/LayoutRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/LayoutRes.java
@@ -35,7 +35,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface LayoutRes {
+
+	/**
+	 * The R.layout.* field which refers to the layout resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as a string which refers to the layout resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/MovieRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/MovieRes.java
index c3a36a6..162a4df 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/MovieRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/MovieRes.java
@@ -35,7 +35,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface MovieRes {
+
+	/**
+	 * The R.movie.* field which refers to the movie resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name which refers to the movie resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringArrayRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringArrayRes.java
index f191d98..386475f 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringArrayRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringArrayRes.java
@@ -36,7 +36,13 @@
 @Target(ElementType.FIELD)
 public @interface StringArrayRes {
 
+	/**
+	 * The R.array.* field which refers to the string array resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name which refers to the string array resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringRes.java
index f663845..4d03843 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringRes.java
@@ -36,7 +36,13 @@
 @Target(ElementType.FIELD)
 public @interface StringRes {
 
+	/**
+	 * The R.string.* field which refers to the string resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as a string which refers to the string resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextArrayRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextArrayRes.java
index c80e71c..197bc25 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextArrayRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextArrayRes.java
@@ -35,7 +35,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface TextArrayRes {
+
+	/**
+	 * The R.array.* field which refers to the text array resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name which refers to the text array resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextRes.java
index 0e2f3ad..2dc1fd4 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextRes.java
@@ -35,7 +35,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.FIELD)
 public @interface TextRes {
+
+	/**
+	 * The R.string.* field which refers to the text resource.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name as a string which refers to the text resource.
+	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Accept.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Accept.java
index a1fd7bf..ed24c1a 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Accept.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Accept.java
@@ -62,5 +62,11 @@
 @Retention(RetentionPolicy.CLASS)
 @Target({ ElementType.METHOD, ElementType.TYPE })
 public @interface Accept {
+
+	/**
+	 * The accepted media type.
+	 * 
+	 * @see {@link org.androidannotations.api.rest.MediaType MediaType}
+	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Delete.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Delete.java
index fe8a56e..32de743 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Delete.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Delete.java
@@ -56,5 +56,9 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface Delete {
+
+	/**
+	 * The URI or the full URL of the web service.
+	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Get.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Get.java
index aa5772c..08cbe66 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Get.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Get.java
@@ -75,5 +75,9 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface Get {
+
+	/**
+	 * The URI or the full URL of the web service.
+	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Head.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Head.java
index ab5c722..d2af95c 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Head.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Head.java
@@ -57,5 +57,9 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface Head {
+
+	/**
+	 * The URI or the full URL of the web service.
+	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Options.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Options.java
index d5c8e3b..e560988 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Options.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Options.java
@@ -57,5 +57,9 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface Options {
+
+	/**
+	 * The URI or the full URL of the web service.
+	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Post.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Post.java
index 541cc36..38030de 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Post.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Post.java
@@ -60,5 +60,9 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface Post {
+
+	/**
+	 * The URI or the full URL of the web service.
+	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Put.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Put.java
index 5099076..b02c134 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Put.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Put.java
@@ -56,5 +56,9 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface Put {
+
+	/**
+	 * The URI or the full URL of the web service.
+	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookie.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookie.java
index 3df7e2c..570aebf 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookie.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookie.java
@@ -81,5 +81,9 @@
 @Retention(RetentionPolicy.CLASS)
 @Target({ ElementType.METHOD, ElementType.TYPE })
 public @interface RequiresCookie {
+
+	/**
+	 * The names of the cookies.
+	 */
 	String[] value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookieInUrl.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookieInUrl.java
index 16a70dc..8f6cfb1 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookieInUrl.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookieInUrl.java
@@ -37,5 +37,9 @@
 @Retention(RetentionPolicy.CLASS)
 @Target({ ElementType.METHOD, ElementType.TYPE })
 public @interface RequiresCookieInUrl {
+
+	/**
+	 * The names of the cookies.
+	 */
 	String[] value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresHeader.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresHeader.java
index 88ad0ef..ebd2ece 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresHeader.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresHeader.java
@@ -81,5 +81,9 @@
 @Retention(RetentionPolicy.CLASS)
 @Target({ ElementType.METHOD, ElementType.TYPE })
 public @interface RequiresHeader {
+
+	/**
+	 * The names of the headers.
+	 */
 	String[] value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Rest.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Rest.java
index f1ab516..63769dc 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Rest.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Rest.java
@@ -202,11 +202,26 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.TYPE)
 public @interface Rest {
+
+	/**
+	 * The root url of the webservice.
+	 */
 	String rootUrl() default "";
 
+	/**
+	 * The classes of the converters which should be used to convert received
+	 * data into Java objects.
+	 */
 	Class<?>[] converters();
 
+	/**
+	 * The classes of interceptors which are used to do extra processing before
+	 * or after requests.
+	 */
 	Class<?>[] interceptors() default {};
 
+	/**
+	 * TODO .
+	 */
 	Class<?> requestFactory() default Void.class;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/SetsCookie.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/SetsCookie.java
index 03afdc5..c7feb1f 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/SetsCookie.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/SetsCookie.java
@@ -62,5 +62,8 @@
 @Target({ ElementType.METHOD, ElementType.TYPE })
 public @interface SetsCookie {
 
+	/**
+	 * The names of the cookies to be kept.
+	 */
 	String[] value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultBoolean.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultBoolean.java
index a0d466d..fe00636 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultBoolean.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultBoolean.java
@@ -39,7 +39,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface DefaultBoolean {
+
+	/**
+	 * The default value of the preference.
+	 */
 	boolean value();
 
+	/**
+	 * The R.string.* field which refers to the key of the preference.
+	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultFloat.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultFloat.java
index cd4b56a..ebaa9ee 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultFloat.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultFloat.java
@@ -39,7 +39,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface DefaultFloat {
+
+	/**
+	 * The default value of the preference.
+	 */
 	float value();
 
+	/**
+	 * The R.string.* field which refers to the key of the preference.
+	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultInt.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultInt.java
index 12bc2d8..8d93e8b 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultInt.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultInt.java
@@ -39,7 +39,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface DefaultInt {
+
+	/**
+	 * The default value of the preference.
+	 */
 	int value();
 
+	/**
+	 * The R.string.* field which refers to the key of the preference.
+	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultLong.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultLong.java
index a1bde5a..0201a94 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultLong.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultLong.java
@@ -39,7 +39,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface DefaultLong {
+
+	/**
+	 * The default value of the preference.
+	 */
 	long value();
 
+	/**
+	 * The R.string.* field which refers to the key of the preference.
+	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultRes.java
index cb5ee53..7aa9f86 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultRes.java
@@ -41,9 +41,20 @@
 @Target(ElementType.METHOD)
 public @interface DefaultRes {
 
+	/**
+	 * The R.id.* field which refers the the resource which is used as the
+	 * default value of the preference.
+	 */
 	int value() default ResId.DEFAULT_VALUE;
 
+	/**
+	 * The resource name which refers the the resource which is used as the
+	 * default value of the preference.
+	 */
 	String resName() default "";
 
+	/**
+	 * The R.string.* field which refers to the key of the preference.
+	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultString.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultString.java
index cc60797..12c22a6 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultString.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultString.java
@@ -39,7 +39,14 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.METHOD)
 public @interface DefaultString {
+
+	/**
+	 * The default value of the preference.
+	 */
 	String value();
 
+	/**
+	 * The R.string.* field which refers to the key of the preference.
+	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultStringSet.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultStringSet.java
index f0b81b6..648a8fe 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultStringSet.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultStringSet.java
@@ -50,5 +50,8 @@
 @Target(ElementType.METHOD)
 public @interface DefaultStringSet {
 
+	/**
+	 * The R.string.* field which refers to the key of the preference.
+	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/SharedPref.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/SharedPref.java
index 7f82c1f..8134931 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/SharedPref.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/SharedPref.java
@@ -70,11 +70,46 @@
 @Retention(RetentionPolicy.CLASS)
 @Target(ElementType.TYPE)
 public @interface SharedPref {
+
+	/**
+	 * Represents the scope of a SharedPreference.
+	 */
 	public enum Scope {
-		APPLICATION_DEFAULT, ACTIVITY, ACTIVITY_DEFAULT, UNIQUE;
+		/**
+		 * The default shared SharedPreference.
+		 */
+		APPLICATION_DEFAULT, //
+		/**
+		 * The name of the SharedPreference will contain the name of the
+		 * Activity and the name annotated interface.
+		 */
+		ACTIVITY, //
+
+		/**
+		 * The name of the SharedPreference will contain the name of the
+		 * Activity (also available through activity.getPreferences()).
+		 */
+		ACTIVITY_DEFAULT, //
+
+		/**
+		 * The name of the SharedPreference will be the name of the annotated
+		 * interface.
+		 */
+		UNIQUE;
 	}
 
+	/**
+	 * The scope of the preferences, this will change the name of the
+	 * SharedPreference.
+	 */
 	Scope value() default Scope.ACTIVITY;
 
+	/**
+	 * The operating mode.
+	 * 
+	 * @see Context#MODE_PRIVATE
+	 * @see Context#MODE_WORLD_READABLE
+	 * @see Context#MODE_WORLD_WRITEABLE
+	 */
 	int mode() default Context.MODE_PRIVATE;
 }
diff --git a/AndroidAnnotations/checkstyle-checks.xml b/AndroidAnnotations/checkstyle-checks.xml
index 86c7a75..4844672 100644
--- a/AndroidAnnotations/checkstyle-checks.xml
+++ b/AndroidAnnotations/checkstyle-checks.xml
@@ -127,8 +127,11 @@
 		<module name="UpperEll" />
 		<module name="OuterTypeFilename" />
 		<module name="OuterTypeNumber" />
+		<module name="JavadocType" />
+		<module name="JavadocMethod" />
+		<module name="JavadocVariable" />
+		<module name="JavadocStyle" />
 	</module>
 	<module name="FileLength" />
-	<module name="SuppressionCommentFilter">
-	</module>
+	<module name="SuppressionCommentFilter" />
 </module>
diff --git a/AndroidAnnotations/checkstyle-suppressions.xml b/AndroidAnnotations/checkstyle-suppressions.xml
new file mode 100644
index 0000000..bbccf22
--- /dev/null
+++ b/AndroidAnnotations/checkstyle-suppressions.xml
@@ -0,0 +1,25 @@
+<?xml version="1.0"?>
+<!--
+
+    Copyright (C) 2010-2014 eBusiness Information, Excilys Group
+
+    Licensed under the Apache License, Version 2.0 (the "License"); you may not
+    use this file except in compliance with the License. You may obtain a copy of
+    the License at
+
+    http://www.apache.org/licenses/LICENSE-2.0
+
+    Unless required by applicable law or agreed To in writing, software
+    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
+    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
+    License for the specific language governing permissions and limitations under
+    the License.
+
+-->
+<!DOCTYPE suppressions PUBLIC "-//Puppy Crawl//DTD Suppressions 1.1//EN"
+    "http://www.puppycrawl.com/dtds/suppressions_1_1.dtd">
+
+<suppressions>
+    <suppress files="org[\\/]androidannotations[\\/](?:[^\\/]+$|(?!annotations)[^\\/]+[\\/])" checks="JavadocType|JavadocMethod|JavadocVariable|JavadocStyle" />
+    <suppress files="com[\\/]" checks="JavadocType|JavadocMethod|JavadocVariable|JavadocStyle" />
+</suppressions>
\ No newline at end of file
diff --git a/AndroidAnnotations/pom.xml b/AndroidAnnotations/pom.xml
index dd8d0e7..a6e4328 100644
--- a/AndroidAnnotations/pom.xml
+++ b/AndroidAnnotations/pom.xml
@@ -268,6 +268,7 @@
 					<version>2.13</version>
 					<configuration>
 						<configLocation>checkstyle-checks.xml</configLocation>
+						<suppressionsLocation>checkstyle-suppressions.xml</suppressionsLocation>
 						<consoleOutput>true</consoleOutput>
 					</configuration>
 				</plugin>

From f643816642a45696900f76deb20ed2991154547a Mon Sep 17 00:00:00 2001
From: WonderCsabo <kozakcsabi@gmail.com>
Date: Mon, 20 Oct 2014 17:56:54 +0200
Subject: [PATCH 4/5] Move Checkstyle execution to a separate profile for
 Travis

It can be really annoying for contributors for always fixing the code
due to broken build by Checkstyle even before starting implementing a
feature. That's why the Checkstyle execution is moved to a separate
profile what will be used by Travis CI. Contributors are still
encouraged to run Checkstyle manually before pushing code, but Travis
will reject malformatted code in the PRs anyway.
---
 .travis.yml                |  2 +-
 AndroidAnnotations/pom.xml | 29 ++++++++++++++++++-----------
 2 files changed, 19 insertions(+), 12 deletions(-)

diff --git a/.travis.yml b/.travis.yml
index dc00c11..35db80c 100644
--- a/.travis.yml
+++ b/.travis.yml
@@ -22,7 +22,7 @@ before_script:
   - export TERM=dumb
 
 script:
-  - mvn -f AndroidAnnotations/pom.xml install
+  - mvn -f AndroidAnnotations/pom.xml install -P travis
   - mvn -f examples/maveneclipse/pom.xml install -Dandroidannotations.version=3.3-SNAPSHOT
   - ./examples/gradle/gradlew build --build-file examples/gradle/build.gradle
 
diff --git a/AndroidAnnotations/pom.xml b/AndroidAnnotations/pom.xml
index a6e4328..a94bd9f 100644
--- a/AndroidAnnotations/pom.xml
+++ b/AndroidAnnotations/pom.xml
@@ -315,17 +315,6 @@
 					</execution>
 				</executions>
 			</plugin>
-			<plugin>
-				<artifactId>maven-checkstyle-plugin</artifactId>
-				<executions>
-					<execution>
-						<phase>process-sources</phase>
-						<goals>
-							<goal>check</goal>
-						</goals>
-					</execution>
-				</executions>
-			</plugin>
 		</plugins>
 	</build>
 
@@ -364,6 +353,24 @@
 				</plugins>
 			</build>
 		</profile>
+		<profile>
+			<id>travis</id>
+			<build>
+				<plugins>
+					<plugin>
+						<artifactId>maven-checkstyle-plugin</artifactId>
+						<executions>
+							<execution>
+								<phase>process-sources</phase>
+								<goals>
+									<goal>check</goal>
+								</goals>
+							</execution>
+						</executions>
+					</plugin>
+				</plugins>
+			</build>
+		</profile>
 	</profiles>
 
 	<reporting>

From 11e5621be9e78164e5d802332effe6209881b2b0 Mon Sep 17 00:00:00 2001
From: WonderCsabo <kozakcsabi@gmail.com>
Date: Mon, 20 Oct 2014 20:43:48 +0200
Subject: [PATCH 5/5] Adding @return to all method JavaDoc

JDK8 JavaDoc needs @return on all non-void methods. Unfortunetaly
Checkstyle does not know about this
(http://sourceforge.net/p/checkstyle/bugs/543/), so we have to disable
the JavaDocMethod check for now.
---
 .../androidannotations/annotations/AfterTextChange.java |  4 +++-
 .../org/androidannotations/annotations/Background.java  |  4 ++++
 .../java/org/androidannotations/annotations/Bean.java   |  4 +++-
 .../annotations/BeforeTextChange.java                   |  4 ++++
 .../androidannotations/annotations/CheckedChange.java   |  4 ++++
 .../java/org/androidannotations/annotations/Click.java  |  4 ++++
 .../org/androidannotations/annotations/CustomTitle.java |  2 ++
 .../org/androidannotations/annotations/EActivity.java   |  4 ++++
 .../java/org/androidannotations/annotations/EBean.java  |  2 ++
 .../org/androidannotations/annotations/EFragment.java   |  4 ++++
 .../org/androidannotations/annotations/EViewGroup.java  |  4 ++++
 .../androidannotations/annotations/EditorAction.java    |  4 ++++
 .../java/org/androidannotations/annotations/Extra.java  |  2 ++
 .../org/androidannotations/annotations/FocusChange.java |  4 ++++
 .../org/androidannotations/annotations/FragmentArg.java |  2 ++
 .../androidannotations/annotations/FragmentById.java    |  4 ++++
 .../androidannotations/annotations/FragmentByTag.java   |  4 +++-
 .../org/androidannotations/annotations/FromHtml.java    |  4 ++++
 .../org/androidannotations/annotations/HttpsClient.java | 17 ++++++++++++++++-
 .../org/androidannotations/annotations/ItemClick.java   |  4 ++++
 .../androidannotations/annotations/ItemLongClick.java   |  4 ++++
 .../org/androidannotations/annotations/ItemSelect.java  |  4 ++++
 .../org/androidannotations/annotations/LongClick.java   |  4 ++++
 .../annotations/OnActivityResult.java                   |  4 ++++
 .../org/androidannotations/annotations/OptionsItem.java |  4 ++++
 .../org/androidannotations/annotations/OptionsMenu.java |  4 ++++
 .../androidannotations/annotations/OptionsMenuItem.java |  4 ++++
 .../org/androidannotations/annotations/OrmLiteDao.java  |  4 ++++
 .../org/androidannotations/annotations/Receiver.java    | 16 ++++++++++++++--
 .../androidannotations/annotations/ReceiverAction.java  | 16 +++++-----------
 .../org/androidannotations/annotations/RoboGuice.java   |  2 ++
 .../annotations/SeekBarProgressChange.java              |  4 ++++
 .../annotations/SeekBarTouchStart.java                  |  4 ++++
 .../annotations/SeekBarTouchStop.java                   |  4 ++++
 .../androidannotations/annotations/ServiceAction.java   |  2 ++
 .../annotations/SupposeBackground.java                  |  2 ++
 .../org/androidannotations/annotations/TextChange.java  |  4 ++++
 .../java/org/androidannotations/annotations/Touch.java  |  4 ++++
 .../java/org/androidannotations/annotations/Trace.java  |  4 ++++
 .../org/androidannotations/annotations/UiThread.java    |  6 ++++++
 .../org/androidannotations/annotations/ViewById.java    |  4 ++++
 .../org/androidannotations/annotations/ViewsById.java   |  4 ++++
 .../org/androidannotations/annotations/WakeLock.java    | 14 ++++++++++----
 .../androidannotations/annotations/WindowFeature.java   |  2 ++
 .../annotations/res/AnimationRes.java                   |  4 ++++
 .../androidannotations/annotations/res/BooleanRes.java  |  4 ++++
 .../androidannotations/annotations/res/ColorRes.java    |  4 ++++
 .../annotations/res/ColorStateListRes.java              |  4 ++++
 .../annotations/res/DimensionPixelOffsetRes.java        |  4 ++++
 .../annotations/res/DimensionPixelSizeRes.java          |  4 ++++
 .../annotations/res/DimensionRes.java                   |  4 ++++
 .../androidannotations/annotations/res/DrawableRes.java |  4 ++++
 .../org/androidannotations/annotations/res/HtmlRes.java |  4 ++++
 .../androidannotations/annotations/res/IntArrayRes.java |  4 ++++
 .../androidannotations/annotations/res/IntegerRes.java  |  4 ++++
 .../androidannotations/annotations/res/LayoutRes.java   |  4 ++++
 .../androidannotations/annotations/res/MovieRes.java    |  4 ++++
 .../annotations/res/StringArrayRes.java                 |  4 ++++
 .../androidannotations/annotations/res/StringRes.java   |  4 ++++
 .../annotations/res/TextArrayRes.java                   |  4 ++++
 .../org/androidannotations/annotations/res/TextRes.java |  4 ++++
 .../org/androidannotations/annotations/rest/Accept.java |  4 +++-
 .../org/androidannotations/annotations/rest/Delete.java |  2 ++
 .../org/androidannotations/annotations/rest/Get.java    |  2 ++
 .../org/androidannotations/annotations/rest/Head.java   |  2 ++
 .../androidannotations/annotations/rest/Options.java    |  2 ++
 .../org/androidannotations/annotations/rest/Post.java   |  2 ++
 .../org/androidannotations/annotations/rest/Put.java    |  2 ++
 .../annotations/rest/RequiresCookie.java                |  2 ++
 .../annotations/rest/RequiresCookieInUrl.java           |  2 ++
 .../annotations/rest/RequiresHeader.java                |  2 ++
 .../org/androidannotations/annotations/rest/Rest.java   | 10 +++++++++-
 .../androidannotations/annotations/rest/SetsCookie.java |  2 ++
 .../annotations/sharedpreferences/DefaultBoolean.java   |  4 ++++
 .../annotations/sharedpreferences/DefaultFloat.java     |  4 ++++
 .../annotations/sharedpreferences/DefaultInt.java       |  4 ++++
 .../annotations/sharedpreferences/DefaultLong.java      |  4 ++++
 .../annotations/sharedpreferences/DefaultRes.java       |  6 ++++++
 .../annotations/sharedpreferences/DefaultString.java    |  4 ++++
 .../annotations/sharedpreferences/DefaultStringSet.java |  2 ++
 .../annotations/sharedpreferences/SharedPref.java       |  4 ++++
 AndroidAnnotations/checkstyle-checks.xml                |  3 ++-
 82 files changed, 322 insertions(+), 24 deletions(-)

diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterTextChange.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterTextChange.java
index bc726c2..ffafd1a 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterTextChange.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/AfterTextChange.java
@@ -76,13 +76,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the TextViews.
+	 * 
+	 * @return the ids of the TextViews.
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as a strings which refer to the TextViews.
 	 * 
-	 * @return
+	 * @return the resource names of the TextViews.
 	 */
 	String[] resName() default "";
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Background.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Background.java
index 2712949..0500faf 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Background.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Background.java
@@ -181,12 +181,15 @@
 	 * boolean mayInterruptIfRunning = true;
 	 * BackgroundExecutor.cancelAll(&quot;my_background_id&quot;, mayInterruptIfRunning);
 	 * </pre>
+	 * 
+	 * @return the task id for cancellation
 	 **/
 	String id() default "";
 
 	/**
 	 * Minimum delay, in milliseconds, before the background task is executed.
 	 *
+	 * @return the delay of the execution
 	 */
 	int delay() default 0;
 
@@ -196,6 +199,7 @@
 	 * All background tasks having the same <code>serial</code> will be executed
 	 * sequentially.
 	 *
+	 * @return the serial execution group
 	 **/
 	String serial() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Bean.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Bean.java
index bf0a726..8489055 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Bean.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Bean.java
@@ -62,7 +62,9 @@
 public @interface Bean {
 
 	/**
-	 * The implementation class.
+	 * The implementation class of the injected bean.
+	 * 
+	 * @return the implementation class
 	 */
 	Class<?> value() default Void.class;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/BeforeTextChange.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/BeforeTextChange.java
index 66219aa..a757460 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/BeforeTextChange.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/BeforeTextChange.java
@@ -84,11 +84,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the TextViews.
+	 * 
+	 * @return the ids of the TextViews.
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as a strings which refer to the TextViews.
+	 * 
+	 * @return the resource names of the TextViews.
 	 */
 	String[] resName() default "";
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CheckedChange.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CheckedChange.java
index 2b39ab4..0f840af 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CheckedChange.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CheckedChange.java
@@ -77,12 +77,16 @@
 	/**
 	 * The R.id.* fields which refer to the CompoundButtons or subclasses of
 	 * CompoundButton.
+	 * 
+	 * @return the ids of the CompoundButtons
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as strings which refer to the CompoundButtons or
 	 * subclasses of CompoundButton.
+	 * 
+	 * @return the resource names of the CompoundButtons
 	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Click.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Click.java
index 4a4423e..e962862 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Click.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Click.java
@@ -64,11 +64,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the Views.
+	 * 
+	 * @return the ids of the Views
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as strings which refer to the Views.
+	 * 
+	 * @return the resource names of the Views
 	 */
 	String[] resName() default "";
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CustomTitle.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CustomTitle.java
index a9f638b..6191d47 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CustomTitle.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/CustomTitle.java
@@ -47,6 +47,8 @@
 
 	/**
 	 * R.layout.* field which refers the the title layout.
+	 * 
+	 * @return the id of the layout
 	 */
 	int value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EActivity.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EActivity.java
index 8bbb3e2..1ce2948 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EActivity.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EActivity.java
@@ -90,11 +90,15 @@
 
 	/**
 	 * The R.layout.* field which refer to the layout.
+	 * 
+	 * @return the id of the layout
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as a string which refer to the layout.
+	 * 
+	 * @return the resource name of the layout
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EBean.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EBean.java
index c5c209a..660ff1c 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EBean.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EBean.java
@@ -110,6 +110,8 @@
 
 	/**
 	 * The scope of the enhanced bean.
+	 * 
+	 * @return the scope of the bean.
 	 */
 	Scope scope() default Scope.Default;
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EFragment.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EFragment.java
index f9b716b..b891f95 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EFragment.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EFragment.java
@@ -111,11 +111,15 @@
 
 	/**
 	 * The R.layout.* field which refer to the layout.
+	 * 
+	 * @return the id of the layout
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as a string which refer to the layout.
+	 * 
+	 * @return the resource name of the layout
 	 */
 	String resName() default "";
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EViewGroup.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EViewGroup.java
index fbb59d7..76873c1 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EViewGroup.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EViewGroup.java
@@ -78,11 +78,15 @@
 
 	/**
 	 * The R.layout.* field which refer to the layout.
+	 * 
+	 * @return the id of the layout
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as a string which refer to the layout.
+	 * 
+	 * @return the resource name of the layout
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EditorAction.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EditorAction.java
index 245a20f..69fad8c 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EditorAction.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/EditorAction.java
@@ -88,11 +88,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the TextViews.
+	 * 
+	 * @return the ids of the TextViews
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as a strings which refer to the TextViews.
+	 * 
+	 * @return the resource names of the TextViews
 	 */
 	String[] resName() default "";
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Extra.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Extra.java
index bbd2b1f..fbb62bf 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Extra.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Extra.java
@@ -82,6 +82,8 @@
 
 	/**
 	 * The key of the injected extra.
+	 * 
+	 * @return the key of the extra
 	 */
 	String value() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FocusChange.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FocusChange.java
index 903442d..bcc9aa1 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FocusChange.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FocusChange.java
@@ -75,11 +75,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the Views.
+	 * 
+	 * @return the ids of the Views
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as strings which refer to the Views.
+	 * 
+	 * @return the resource names of the Views
 	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentArg.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentArg.java
index 4780f78..42ba072 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentArg.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentArg.java
@@ -74,6 +74,8 @@
 
 	/**
 	 * The key of the injected Fragment argument.
+	 * 
+	 * @return the key of the argument
 	 */
 	String value() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentById.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentById.java
index 066d372..470a08e 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentById.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentById.java
@@ -74,11 +74,15 @@
 
 	/**
 	 * The R.id.* field which is the id of the Fragment.
+	 * 
+	 * @return the id of the Fragment
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name which refers to the id of the Fragment.
+	 * 
+	 * @return the resource name of the Fragment
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentByTag.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentByTag.java
index c936d9f..ba8e57b 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentByTag.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FragmentByTag.java
@@ -74,7 +74,9 @@
 public @interface FragmentByTag {
 
 	/**
-	 * The tag of the Fragment.
+	 * The tag of the injected Fragment.
+	 * 
+	 * @return the tag of the Fragment
 	 */
 	String value() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FromHtml.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FromHtml.java
index d40e747..e26a760 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FromHtml.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/FromHtml.java
@@ -61,11 +61,15 @@
 
 	/**
 	 * The R.string.* field which refers to the html string resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name which refers to the html string resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/HttpsClient.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/HttpsClient.java
index 9d7638c..09eb6cb 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/HttpsClient.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/HttpsClient.java
@@ -97,36 +97,51 @@
 
 	/**
 	 * The R.id.* field id which refers to the trust store file.
+	 * 
+	 * @return the id of the trust store file
 	 */
 	int trustStore() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name which refers to the trust store file.
+	 * 
+	 * @return the resource name of the trust store file.
 	 */
 	String trustStoreResName() default "";
 
 	/**
 	 * The trust store password.
+	 * 
+	 * @return the trust store password
 	 */
 	String trustStorePwd() default DEFAULT_PASSWD;
 
 	/**
 	 * The R.id.* field id which refers to the key store file.
+	 * 
+	 * @return the id of the key store file
 	 */
 	int keyStore() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name which refers to the key store file.
+	 * 
+	 * @return the resource name of the key store file
 	 */
 	String keyStoreResName() default "";
 
 	/**
 	 * The key store password.
+	 * 
+	 * @return the key store password
 	 */
 	String keyStorePwd() default DEFAULT_PASSWD;
 
 	/**
-	 * <b>true</b> if authorizes any TLS/SSL hostname, <b>false</b> otherwise.
+	 * Whether to authorizes any TLS/SSL hostname.
+	 * 
+	 * @return <b>true</b> if authorizes any TLS/SSL hostname, <b>false</b>
+	 *         otherwise.
 	 */
 	boolean allowAllHostnames() default true;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemClick.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemClick.java
index 9e75752..535fd8f 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemClick.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemClick.java
@@ -71,11 +71,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the AdapterViews.
+	 * 
+	 * @return the ids of the AdapterViews
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as strings which refer to the AdapterViews.
+	 * 
+	 * @return the resource names of the AdapterViews
 	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemLongClick.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemLongClick.java
index 4bc1fd9..86bafbe 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemLongClick.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemLongClick.java
@@ -71,11 +71,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the AdapterViews.
+	 * 
+	 * @return the ids of the AdapterViews
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as strings which refer to the AdapterViews.
+	 * 
+	 * @return the resource names of the AdapterViews
 	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemSelect.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemSelect.java
index 6b9fd16..97650a6 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemSelect.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ItemSelect.java
@@ -77,11 +77,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the AdapterViews.
+	 * 
+	 * @return the ids of the AdapterViews
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as strings which refer to the AdapterViews.
+	 * 
+	 * @return the resource names of the AdapterViews
 	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/LongClick.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/LongClick.java
index 88ad78a..5dffc8c 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/LongClick.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/LongClick.java
@@ -76,11 +76,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the Views.
+	 * 
+	 * @return the ids of the Views
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as strings which refer to the Views.
+	 * 
+	 * @return the resource names of the Views
 	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OnActivityResult.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OnActivityResult.java
index 0142a83..d1f2296 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OnActivityResult.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OnActivityResult.java
@@ -80,6 +80,8 @@
 
 	/**
 	 * The <b>requestCode</b> associated with the given result.
+	 * 
+	 * @return the requestCode
 	 */
 	int value();
 
@@ -125,6 +127,8 @@
 
 		/**
 		 * They key of the result data.
+		 * 
+		 * @return the key
 		 */
 		String value() default "";
 	}
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsItem.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsItem.java
index 105c57c..061176d 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsItem.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsItem.java
@@ -79,11 +79,15 @@
 
 	/**
 	 * The R.id.* field which refers to the menu item.
+	 * 
+	 * @return the id of the menu item
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name which refers to the menu item.
+	 *
+	 * @return the resource name of the menu item
 	 */
 	String[] resName() default "";
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenu.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenu.java
index d8701fa..0b55c22 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenu.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenu.java
@@ -60,11 +60,15 @@
 
 	/**
 	 * The R.menu.* fields which refers to the menus.
+	 * 
+	 * @return the ids of the menus
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as strings which refers to the menus.
+	 * 
+	 * @return the resource names of the menus
 	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenuItem.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenuItem.java
index c0f41f9..7fc9d83 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenuItem.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OptionsMenuItem.java
@@ -59,11 +59,15 @@
 
 	/**
 	 * The R.id.* fields which refers to the menu items.
+	 * 
+	 * @return the ids of the menus
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as strings which refers to the menu items.
+	 * 
+	 * @return the resource names of the menus
 	 */
 	String[] resName() default "";
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OrmLiteDao.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OrmLiteDao.java
index 67701fb..7eef2b6 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OrmLiteDao.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/OrmLiteDao.java
@@ -69,6 +69,8 @@
 
 	/**
 	 * The class of the used database helper.
+	 * 
+	 * @return the helper class
 	 */
 	Class<?> helper();
 
@@ -78,6 +80,8 @@
 	 * @deprecated Since <b>3.1</b> the model class is inferred from the type of
 	 *             the annotated field. This parameter will be removed in a
 	 *             future version.
+	 * 
+	 * @return the model class
 	 */
 	@Deprecated
 	Class<?> model() default Void.class;
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Receiver.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Receiver.java
index 526cd5d..edfe859 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Receiver.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Receiver.java
@@ -88,21 +88,31 @@
 
 	/**
 	 * The strings indicating the actions which will spark the method.
+	 * 
+	 * @return the actions which will spark the method
 	 */
 	String[] actions();
 
 	/**
 	 * The strings indicating the data schemes which should be handled.
+	 * 
+	 * @return the data schemes which should be handled
 	 */
 	String[] dataSchemes() default {};
 
 	/**
 	 * The event pair when the receiver should be registered/unregistered.
+	 * 
+	 * @return the registration/unregistration point
 	 */
 	RegisterAt registerAt() default RegisterAt.OnCreateOnDestroy;
 
 	/**
-	 * <b>true</b>, if LocalBroadcastManager should be used.
+	 * Whether to use LocalBroadcastManager.
+	 * 
+	 * @return <b>true</b>, if LocalBroadcastManager should be used,
+	 *         <b>false</b> otherwise
+	 *
 	 */
 	boolean local() default false;
 
@@ -143,8 +153,10 @@
 	public @interface Extra {
 
 		/**
-		 * Define the extra's name. If this parameter isn't set the annotated
+		 * Defines the extra's name. If this parameter isn't set the annotated
 		 * parameter name will be used.
+		 * 
+		 * @return the name of the extra
 		 */
 		String value() default "";
 	}
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ReceiverAction.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ReceiverAction.java
index c4e4069..c953588 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ReceiverAction.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ReceiverAction.java
@@ -30,15 +30,7 @@
  * The class MAY contain several {@link ReceiverAction} annotated methods.
  * </p>
  * <p>
- * The method annotated with {@link ReceiverAction} may have as parameters : - A
- * {@link android.content.Context} which will be the context given in
- * {@code void onReceive(Context context, Intent intent)} - A
- * {@link android.content.Intent} which will be the intent given in
- * {@code void onReceive(Context context, Intent intent)} - Some any native,
- * {@link android.os.Parcelable} or {@link java.io.Serializable} parameters
- * annotated with {@link ReceiverAction.Extra} which will be the extra put in
- * the intent. The key of this extra is the value of the annotation
- * {@link ReceiverAction.Extra} if set or the name of the parameter.
+ * The method annotated with {@link ReceiverAction} may have as parameters :
  * </p>
  * <ul>
  * <li>A {@link android.content.Context} which will be the context given in
@@ -104,6 +96,8 @@
 	/**
 	 * Define a set of actions this method should handle. If this field isn't
 	 * set the annotated method name will be used.
+	 *
+	 * @return the actions
 	 */
 	String[] value() default {};
 
@@ -132,8 +126,8 @@
 		/**
 		 * Define the extra's name. If this parameter isn't set the annotated
 		 * parameter name will be used.
-		 * 
-		 * @return the name of the extra
+		 *
+		 * @return the extra's name
 		 */
 		String value() default "";
 	}
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/RoboGuice.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/RoboGuice.java
index 0f7a73b..4bccc58 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/RoboGuice.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/RoboGuice.java
@@ -68,6 +68,8 @@
 	/**
 	 * The RoboGuice listener classes to bind to this activity. Will add an
 	 * injected listener field to the subclass.
+	 * 
+	 * @return the RoboGuice listener classes
 	 */
 	Class<?>[] value() default {};
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarProgressChange.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarProgressChange.java
index c31ec5c..2d79b88 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarProgressChange.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarProgressChange.java
@@ -84,11 +84,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the SeekBars.
+	 * 
+	 * @return the ids of the SeekBars
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as strings which refer to the SeekBars.
+	 * 
+	 * @return the resource names of the SeekBars
 	 */
 	String[] resName() default "";
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStart.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStart.java
index 7670cba..3deb577 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStart.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStart.java
@@ -80,11 +80,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the SeekBars.
+	 * 
+	 * @return the ids of the SeekBars
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as strings which refer to the SeekBars.
+	 * 
+	 * @return the resource names of the SeekBars
 	 */
 	String[] resName() default "";
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStop.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStop.java
index c71cffe..e505a96 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStop.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SeekBarTouchStop.java
@@ -80,11 +80,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the SeekBars.
+	 * 
+	 * @return the ids of the SeekBars
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as strings which refer to the SeekBars.
+	 * 
+	 * @return the resource names of the SeekBars
 	 */
 	String[] resName() default "";
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ServiceAction.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ServiceAction.java
index a2822fa..e824ba7 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ServiceAction.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ServiceAction.java
@@ -96,6 +96,8 @@
 	/**
 	 * Define the action's name. If this field isn't set the annotated method
 	 * name will be used.
+	 * 
+	 * @return the name of the action
 	 */
 	String value() default "";
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeBackground.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeBackground.java
index 3686947..7d8867d 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeBackground.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/SupposeBackground.java
@@ -64,6 +64,8 @@
 	 * then any background thread is allowed.
 	 *
 	 * @see org.androidannotations.api.BackgroundExecutor#checkBgThread(String...)
+	 * 
+	 * @return the allowed serials
 	 */
 	String[] serial() default {};
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/TextChange.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/TextChange.java
index 5622594..7591589 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/TextChange.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/TextChange.java
@@ -83,11 +83,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the TextViews.
+	 * 
+	 * @return the ids of the TextViews.
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as a strings which refer to the TextViews.
+	 * 
+	 * @return the resource names of the TextViews.
 	 */
 	String[] resName() default "";
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Touch.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Touch.java
index ed3ccd5..8aed129 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Touch.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Touch.java
@@ -77,11 +77,15 @@
 
 	/**
 	 * The R.id.* fields which refer to the Views.
+	 * 
+	 * @return the ids of the Views
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource names as strings which refer to the Views.
+	 * 
+	 * @return the resource names of the Views
 	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Trace.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Trace.java
index 826550e..fe6447d 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Trace.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/Trace.java
@@ -79,11 +79,15 @@
 
 	/**
 	 * The tag used for the log message.
+	 * 
+	 * @return the tag of the message
 	 */
 	String tag() default DEFAULT_TAG;
 
 	/**
 	 * The log level used for the log message.
+	 * 
+	 * @return the logging level of the message
 	 */
 	int level() default Log.INFO;
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/UiThread.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/UiThread.java
index fcb5a18..dbf7430 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/UiThread.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/UiThread.java
@@ -96,6 +96,8 @@
 
 	/**
 	 * The delay of the execution in milliseconds.
+	 * 
+	 * @return the delay of the execution
 	 */
 	long delay() default 0;
 
@@ -104,6 +106,10 @@
 	 * if it is inside the UI thread already. If so, it will directly call the
 	 * method instead of using the handler. The default value is
 	 * {@link Propagation#ENQUEUE}, which will always call the handler.
+	 * 
+	 * @return {@link Propagation#ENQUEUE} to always call the handler,
+	 *         {@link Propagation#REUSE}, to check whether it is already on the
+	 *         UI thread
 	 */
 	Propagation propagation() default Propagation.ENQUEUE;
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewById.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewById.java
index 80cc5ba..1ff3938 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewById.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewById.java
@@ -66,11 +66,15 @@
 
 	/**
 	 * The R.id.* field which refers to the injected View.
+	 * 
+	 * @return the id of the View
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name which refers to the injected View.
+	 * 
+	 * @return the resource name of the View
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewsById.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewsById.java
index 2973bcb..c08ac80 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewsById.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/ViewsById.java
@@ -69,11 +69,15 @@
 
 	/**
 	 * The array of R.id.* fields which refer to the injected Views.
+	 * 
+	 * @return the ids of the Views
 	 */
 	int[] value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The array resource names which refer to the injected Views.
+	 * 
+	 * @return the resource names of the Views
 	 */
 	String[] resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WakeLock.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WakeLock.java
index 45268e3..662616a 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WakeLock.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WakeLock.java
@@ -58,9 +58,11 @@
 	String DEFAULT_TAG = "NO_TAG";
 
 	/**
-	 * The tag of the WakeLock.
+	 * The tag of the created WakeLock.
 	 * 
-	 * @see {@link android.os.PowerManager#newWakeLock(int, String)}
+	 * @see android.os.PowerManager#newWakeLock(int, String)
+	 * 
+	 * @return the tag of the WakeLock
 	 */
 	String tag() default DEFAULT_TAG;
 
@@ -68,7 +70,9 @@
 	 * The level of the created {@link android.os.PowerManager.WakeLock
 	 * WakeLock}.
 	 * 
-	 * @see {@link android.os.PowerManager#newWakeLock(int, String)}
+	 * @see android.os.PowerManager#newWakeLock(int, String)
+	 * 
+	 * @return the level of the WakeLock
 	 */
 	Level level() default Level.PARTIAL_WAKE_LOCK;
 
@@ -76,7 +80,9 @@
 	 * The optional flags for the created
 	 * {@link android.os.PowerManager.WakeLock WakeLock}.
 	 * 
-	 * @see {@link android.os.PowerManager#newWakeLock(int, String)}
+	 * @see android.os.PowerManager#newWakeLock(int, String)
+	 * 
+	 * @return the flags of the WakeLock
 	 */
 	Flag[] flags() default {};
 
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WindowFeature.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WindowFeature.java
index 720a7a1..a537de1 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WindowFeature.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/WindowFeature.java
@@ -53,6 +53,8 @@
 
 	/**
 	 * An array of integers which are <code>Window.FEATURE_*</code> fields.
+	 * 
+	 * @return the Window feature constants
 	 */
 	int[] value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/AnimationRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/AnimationRes.java
index ad54416..d8dc6f4 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/AnimationRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/AnimationRes.java
@@ -38,11 +38,15 @@
 
 	/**
 	 * The R.anim.* field which refers to the animation.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name which refers to the animation.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/BooleanRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/BooleanRes.java
index 014c296..8c18832 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/BooleanRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/BooleanRes.java
@@ -38,11 +38,15 @@
 
 	/**
 	 * The R.boolean.* field which refers to the boolean resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as a string which refers to the boolean resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorRes.java
index 2d2695e..211c84b 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorRes.java
@@ -38,11 +38,15 @@
 
 	/**
 	 * The R.color.* field which refers to the color resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as a string which refers to the color resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorStateListRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorStateListRes.java
index 85d2fc5..ca3db78 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorStateListRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/ColorStateListRes.java
@@ -38,12 +38,16 @@
 
 	/**
 	 * The R.color.* field which refers to the ColorStateList resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as a string which refers to the ColorStateList
 	 * resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelOffsetRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelOffsetRes.java
index 2f14148..1647dd5 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelOffsetRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelOffsetRes.java
@@ -39,12 +39,16 @@
 
 	/**
 	 * The R.dimen.* field which refers to the dimension pixel offset resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as string which refers to the dimension pixel offset
 	 * resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelSizeRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelSizeRes.java
index dd31005..d8f2053 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelSizeRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionPixelSizeRes.java
@@ -39,12 +39,16 @@
 
 	/**
 	 * The R.dimen.* field which refers to the dimension pixel size resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as string which refers to the dimension pixel size
 	 * resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionRes.java
index dc4a3d7..5d51935 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DimensionRes.java
@@ -38,11 +38,15 @@
 
 	/**
 	 * The resource name as string which refers to the dimension resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as string which refers to the dimension resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DrawableRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DrawableRes.java
index 1fc1212..f6f3442 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DrawableRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/DrawableRes.java
@@ -38,11 +38,15 @@
 
 	/**
 	 * The R.id.* field which refers to the drawable resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as a string which refers to the drawable resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/HtmlRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/HtmlRes.java
index acc9e27..a14b770 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/HtmlRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/HtmlRes.java
@@ -39,11 +39,15 @@
 
 	/**
 	 * The R.id.* field which refers to the HTML string resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as a string which refers to the HTML string resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntArrayRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntArrayRes.java
index b266e7f..8e652a9 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntArrayRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntArrayRes.java
@@ -39,11 +39,15 @@
 
 	/**
 	 * The R.array.* field which refers to the integer array resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name which refers to the integer array resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntegerRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntegerRes.java
index 632b313..c583c68 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntegerRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/IntegerRes.java
@@ -38,11 +38,15 @@
 
 	/**
 	 * The R.integer.* field which refers to the integer resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as a string which refers to the integer resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/LayoutRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/LayoutRes.java
index 31c587a..03b0f40 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/LayoutRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/LayoutRes.java
@@ -38,11 +38,15 @@
 
 	/**
 	 * The R.layout.* field which refers to the layout resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as a string which refers to the layout resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/MovieRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/MovieRes.java
index 162a4df..5262f33 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/MovieRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/MovieRes.java
@@ -38,11 +38,15 @@
 
 	/**
 	 * The R.movie.* field which refers to the movie resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name which refers to the movie resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringArrayRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringArrayRes.java
index 386475f..6f45434 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringArrayRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringArrayRes.java
@@ -38,11 +38,15 @@
 
 	/**
 	 * The R.array.* field which refers to the string array resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name which refers to the string array resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringRes.java
index 4d03843..fc13b2b 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/StringRes.java
@@ -38,11 +38,15 @@
 
 	/**
 	 * The R.string.* field which refers to the string resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as a string which refers to the string resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextArrayRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextArrayRes.java
index 197bc25..97a559b 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextArrayRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextArrayRes.java
@@ -38,11 +38,15 @@
 
 	/**
 	 * The R.array.* field which refers to the text array resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name which refers to the text array resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextRes.java
index 2dc1fd4..695117d 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/res/TextRes.java
@@ -38,11 +38,15 @@
 
 	/**
 	 * The R.string.* field which refers to the text resource.
+	 * 
+	 * @return the id of the resource
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name as a string which refers to the text resource.
+	 * 
+	 * @return the resource name of the resource
 	 */
 	String resName() default "";
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Accept.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Accept.java
index ed24c1a..47fa509 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Accept.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Accept.java
@@ -66,7 +66,9 @@
 	/**
 	 * The accepted media type.
 	 * 
-	 * @see {@link org.androidannotations.api.rest.MediaType MediaType}
+	 * @see org.androidannotations.api.rest.MediaType
+	 * 
+	 * @return the media type
 	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Delete.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Delete.java
index 32de743..e1f7f89 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Delete.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Delete.java
@@ -59,6 +59,8 @@
 
 	/**
 	 * The URI or the full URL of the web service.
+	 * 
+	 * @return the address of the web service
 	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Get.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Get.java
index 08cbe66..6096c7c 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Get.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Get.java
@@ -78,6 +78,8 @@
 
 	/**
 	 * The URI or the full URL of the web service.
+	 * 
+	 * @return the address of the web service
 	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Head.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Head.java
index d2af95c..ca43244 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Head.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Head.java
@@ -60,6 +60,8 @@
 
 	/**
 	 * The URI or the full URL of the web service.
+	 * 
+	 * @return the address of the web service
 	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Options.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Options.java
index e560988..a171544 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Options.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Options.java
@@ -60,6 +60,8 @@
 
 	/**
 	 * The URI or the full URL of the web service.
+	 * 
+	 * @return the address of the web service
 	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Post.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Post.java
index 38030de..ec049fe 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Post.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Post.java
@@ -63,6 +63,8 @@
 
 	/**
 	 * The URI or the full URL of the web service.
+	 * 
+	 * @return the address of the web service
 	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Put.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Put.java
index b02c134..d9b395a 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Put.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Put.java
@@ -59,6 +59,8 @@
 
 	/**
 	 * The URI or the full URL of the web service.
+	 * 
+	 * @return the address of the web service
 	 */
 	String value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookie.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookie.java
index 570aebf..11df53d 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookie.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookie.java
@@ -84,6 +84,8 @@
 
 	/**
 	 * The names of the cookies.
+	 * 
+	 * @return the cookie names
 	 */
 	String[] value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookieInUrl.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookieInUrl.java
index 8f6cfb1..363cca6 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookieInUrl.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresCookieInUrl.java
@@ -40,6 +40,8 @@
 
 	/**
 	 * The names of the cookies.
+	 * 
+	 * @return the cookie names
 	 */
 	String[] value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresHeader.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresHeader.java
index ebd2ece..a59080e 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresHeader.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/RequiresHeader.java
@@ -84,6 +84,8 @@
 
 	/**
 	 * The names of the headers.
+	 * 
+	 * @return the header names
 	 */
 	String[] value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Rest.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Rest.java
index 63769dc..f4a7766 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Rest.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/Rest.java
@@ -204,24 +204,32 @@
 public @interface Rest {
 
 	/**
-	 * The root url of the webservice.
+	 * The root url of the web service.
+	 * 
+	 * @return the root url of the web service
 	 */
 	String rootUrl() default "";
 
 	/**
 	 * The classes of the converters which should be used to convert received
 	 * data into Java objects.
+	 * 
+	 * @return the converter classes
 	 */
 	Class<?>[] converters();
 
 	/**
 	 * The classes of interceptors which are used to do extra processing before
 	 * or after requests.
+	 * 
+	 * @return the interceptor classes
 	 */
 	Class<?>[] interceptors() default {};
 
 	/**
 	 * TODO .
+	 * 
+	 * @return TODO
 	 */
 	Class<?> requestFactory() default Void.class;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/SetsCookie.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/SetsCookie.java
index c7feb1f..5862071 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/SetsCookie.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/rest/SetsCookie.java
@@ -64,6 +64,8 @@
 
 	/**
 	 * The names of the cookies to be kept.
+	 * 
+	 * @return the cookie names
 	 */
 	String[] value();
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultBoolean.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultBoolean.java
index fe00636..5328d5d 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultBoolean.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultBoolean.java
@@ -42,11 +42,15 @@
 
 	/**
 	 * The default value of the preference.
+	 * 
+	 * @return the default value
 	 */
 	boolean value();
 
 	/**
 	 * The R.string.* field which refers to the key of the preference.
+	 * 
+	 * @return the resource name of the preference key
 	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultFloat.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultFloat.java
index ebaa9ee..1119b98 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultFloat.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultFloat.java
@@ -42,11 +42,15 @@
 
 	/**
 	 * The default value of the preference.
+	 * 
+	 * @return the default value
 	 */
 	float value();
 
 	/**
 	 * The R.string.* field which refers to the key of the preference.
+	 * 
+	 * @return the resource name of the preference key
 	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultInt.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultInt.java
index 8d93e8b..db45c89 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultInt.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultInt.java
@@ -42,11 +42,15 @@
 
 	/**
 	 * The default value of the preference.
+	 * 
+	 * @return the default value
 	 */
 	int value();
 
 	/**
 	 * The R.string.* field which refers to the key of the preference.
+	 * 
+	 * @return the resource name of the preference key
 	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultLong.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultLong.java
index 0201a94..e430bb4 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultLong.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultLong.java
@@ -42,11 +42,15 @@
 
 	/**
 	 * The default value of the preference.
+	 * 
+	 * @return the default value
 	 */
 	long value();
 
 	/**
 	 * The R.string.* field which refers to the key of the preference.
+	 * 
+	 * @return the resource name of the preference key
 	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultRes.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultRes.java
index 7aa9f86..ccae606 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultRes.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultRes.java
@@ -44,17 +44,23 @@
 	/**
 	 * The R.id.* field which refers the the resource which is used as the
 	 * default value of the preference.
+	 * 
+	 * @return the default value
 	 */
 	int value() default ResId.DEFAULT_VALUE;
 
 	/**
 	 * The resource name which refers the the resource which is used as the
 	 * default value of the preference.
+	 * 
+	 * @return the resource name of the default value
 	 */
 	String resName() default "";
 
 	/**
 	 * The R.string.* field which refers to the key of the preference.
+	 * 
+	 * @return the resource name of the preference key
 	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultString.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultString.java
index 12c22a6..fb46c16 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultString.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultString.java
@@ -42,11 +42,15 @@
 
 	/**
 	 * The default value of the preference.
+	 * 
+	 * @return the default value
 	 */
 	String value();
 
 	/**
 	 * The R.string.* field which refers to the key of the preference.
+	 * 
+	 * @return the resource name of the preference key
 	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultStringSet.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultStringSet.java
index 648a8fe..21cc749 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultStringSet.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/DefaultStringSet.java
@@ -52,6 +52,8 @@
 
 	/**
 	 * The R.string.* field which refers to the key of the preference.
+	 * 
+	 * @return the resource name of the preference key
 	 */
 	int keyRes() default ResId.DEFAULT_VALUE;
 }
diff --git a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/SharedPref.java b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/SharedPref.java
index 8134931..276a0be 100644
--- a/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/SharedPref.java
+++ b/AndroidAnnotations/androidannotations-api/src/main/java/org/androidannotations/annotations/sharedpreferences/SharedPref.java
@@ -101,6 +101,8 @@
 	/**
 	 * The scope of the preferences, this will change the name of the
 	 * SharedPreference.
+	 * 
+	 * @return the scope of the preferences
 	 */
 	Scope value() default Scope.ACTIVITY;
 
@@ -110,6 +112,8 @@
 	 * @see Context#MODE_PRIVATE
 	 * @see Context#MODE_WORLD_READABLE
 	 * @see Context#MODE_WORLD_WRITEABLE
+	 * 
+	 * @return the operating mode
 	 */
 	int mode() default Context.MODE_PRIVATE;
 }
diff --git a/AndroidAnnotations/checkstyle-checks.xml b/AndroidAnnotations/checkstyle-checks.xml
index 4844672..11113c9 100644
--- a/AndroidAnnotations/checkstyle-checks.xml
+++ b/AndroidAnnotations/checkstyle-checks.xml
@@ -128,7 +128,8 @@
 		<module name="OuterTypeFilename" />
 		<module name="OuterTypeNumber" />
 		<module name="JavadocType" />
-		<module name="JavadocMethod" />
+		<!-- sourceforge.net/p/checkstyle/bugs/543/ -->
+        <!-- <module name="JavadocMethod" /> -->
 		<module name="JavadocVariable" />
 		<module name="JavadocStyle" />
 	</module>
