package com.nlisttest;

import android.app.Application;
import android.util.Log;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainPackageConfig;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.nlisttest.nlist.ReactNListPackage;
import com.nlisttest.nlist.ViewCloner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import static com.facebook.common.util.ByteConstants.MB;

public class MainApplication extends Application implements ReactApplication, RequestListener {

    final static String TAG="Hello";
    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            HashSet<RequestListener> requestListeners = new HashSet<>();
            requestListeners.add(MainApplication.this);
            ImagePipelineConfig frescoConfig = ImagePipelineConfig
                    .newBuilder(MainApplication.this)
                    .setDownsampleEnabled(true)
                    .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(MainApplication.this).setMaxCacheSize(300*MB).setMaxCacheSizeOnLowDiskSpace(10*MB).build())
                    .setBitmapMemoryCacheParamsSupplier(new Supplier<MemoryCacheParams>() {
                        @Override
                        public MemoryCacheParams get() {
                            return new MemoryCacheParams(100 * MB, 1000, 10 * MB, 20, 5 * MB);
                        }
                    })
                    .setRequestListeners(requestListeners)

                    .build();

            FLog.setMinimumLoggingLevel(FLog.VERBOSE);

            MainPackageConfig appConfig = new MainPackageConfig
                    .Builder()
                    .setFrescoConfig(frescoConfig)
                    .build();

            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(appConfig),
                    new ReactNListPackage(mReactNativeHost)
            );
        }

        @Override
        protected String getJSMainModuleName() {
            return "index";
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("NList_bench", "Start: " + System.currentTimeMillis());

        SoLoader.init(this, /* native exopackage */ false);
    }

    @Override
    public void onRequestStart(ImageRequest request, Object callerContext, String requestId, boolean isPrefetch) {
        Log.d(TAG, "onRequestStart() called with: request = [" + request + "], callerContext = [" + callerContext + "], requestId = [" + requestId + "], isPrefetch = [" + isPrefetch + "]");
    }

    @Override
    public void onRequestSuccess(ImageRequest request, String requestId, boolean isPrefetch) {
        Log.d(TAG, "onRequestSuccess() called with: request = [" + request + "], requestId = [" + requestId + "], isPrefetch = [" + isPrefetch + "]");
    }

    @Override
    public void onRequestFailure(ImageRequest request, String requestId, Throwable throwable, boolean isPrefetch) {
        Log.d(TAG, "onRequestFailure() called with: request = [" + request + "], requestId = [" + requestId + "], throwable = [" + throwable + "], isPrefetch = [" + isPrefetch + "]");
    }

    @Override
    public void onRequestCancellation(String requestId) {
        Log.d(TAG, "onRequestCancellation() called with: requestId = [" + requestId + "]");
    }

    @Override
    public void onProducerStart(String requestId, String producerName) {
        Log.d(TAG, "onProducerStart() called with: requestId = [" + requestId + "], producerName = [" + producerName + "]");
    }

    @Override
    public void onProducerEvent(String requestId, String producerName, String eventName) {
        Log.d(TAG, "onProducerEvent() called with: requestId = [" + requestId + "], producerName = [" + producerName + "], eventName = [" + eventName + "]");
    }

    @Override
    public void onProducerFinishWithSuccess(String requestId, String producerName, @Nullable Map<String, String> extraMap) {
        Log.d(TAG, "onProducerFinishWithSuccess() called with: requestId = [" + requestId + "], producerName = [" + producerName + "], extraMap = [" + extraMap + "]");
    }

    @Override
    public void onProducerFinishWithFailure(String requestId, String producerName, Throwable t, @Nullable Map<String, String> extraMap) {
        Log.d(TAG, "onProducerFinishWithFailure() called with: requestId = [" + requestId + "], producerName = [" + producerName + "], t = [" + t + "], extraMap = [" + extraMap + "]");
    }

    @Override
    public void onProducerFinishWithCancellation(String requestId, String producerName, @Nullable Map<String, String> extraMap) {
        Log.d(TAG, "onProducerFinishWithCancellation() called with: requestId = [" + requestId + "], producerName = [" + producerName + "], extraMap = [" + extraMap + "]");
    }

    @Override
    public void onUltimateProducerReached(String requestId, String producerName, boolean successful) {
        Log.d(TAG, "onUltimateProducerReached() called with: requestId = [" + requestId + "], producerName = [" + producerName + "], successful = [" + successful + "]");
    }

    @Override
    public boolean requiresExtraMap(String requestId) {
        return false;
    }
}
