package com.uta.picartstudiosandep.waterfallcallerledscreen;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;


public class NativeBannerIntegrations {


    public static String ad_Banner_unit_1 = "ca-app-pub-3940256099942544/2247696110";
    public static String ad_Banner_unit_2 = "ca-app-pub-3940256099942544/2247696110";
    public static String ad_Banner_unit_3 = "ca-app-pub-3940256099942544/2247696110";


//--------------------- Start ------------ of ------------- Native Ads Integrations---------------- Start -------------

    private UnifiedNativeAd nativeAd;


    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     */

    public void nativeAdMobCalled(final Activity activity, String native_Ads_Unit_ID, final int native_banner_ID, final int native_layout_ID, final Boolean isForNativeBigBanner) {

        AdLoader.Builder builder = new AdLoader.Builder(activity, native_Ads_Unit_ID);

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.

            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                // You must call destroy on old ads when you are done with them,
                // otherwise you will have a memory leak.
                if (nativeAd != null) {
                    nativeAd.destroy();
                }

                nativeAd = unifiedNativeAd;

                RelativeLayout relativeLayout = activity.findViewById(native_banner_ID);

                UnifiedNativeAdView adView = (UnifiedNativeAdView) activity.getLayoutInflater().inflate(native_layout_ID, null);
                if (isForNativeBigBanner)
                    populateUnifiedNativeAdView(unifiedNativeAd, adView);
                else
                    populateUnifiedNativeAdViewSmallBanner(unifiedNativeAd, adView);


                relativeLayout.removeAllViews();
                relativeLayout.addView(adView);
            }

        });


        videoNativeIntegration(builder);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                //  Toast.makeText(context, "Failed to load native ad: " + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());


    }



    /**
     * Populates a {@link UnifiedNativeAdView} object with data from a given
     * {@link UnifiedNativeAd}.
     *
     * @param nativeAd the object containing the ad's assets
     * @param adView   the view to be populated
     */
    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.


        // Set other ad assets.

        HeadingIntegration(adView, R.id.ad_headline);
        SubHeadingIntegration(adView, R.id.ad_advertiser);
        CallToActionIntegration(adView, R.id.ad_call_to_action);
        AppIconIntegration(adView, R.id.ad_app_icon);


        ImageIntegration(adView, R.id.ad_media);
        BodyIntegration(adView, R.id.ad_body);
        PriceIntegration(adView, R.id.ad_price);
        PlayStoreIntegration(adView, R.id.ad_store);
        StarRatingIntegration(adView, R.id.ad_stars);


        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.


        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd);


        videoNativeControlSetUp();

    }


    /**
     * Populates a {@link UnifiedNativeAdView} object with data from a given
     * {@link UnifiedNativeAd}.
     *
     * @param nativeAd the object containing the ad's assets
     * @param adView   the view to be populated
     */
    private void populateUnifiedNativeAdViewSmallBanner(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.


        // Set other ad assets.

        HeadingIntegration(adView, R.id.ad_headline);
        SubHeadingIntegration(adView, R.id.ad_advertiser);
        CallToActionIntegration(adView, R.id.ad_call_to_action);
        AppIconIntegration(adView, R.id.ad_app_icon);


        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.


        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd);

    }


    private void AppIconIntegration(UnifiedNativeAdView adView, int viewID) {

        adView.setIconView(adView.findViewById(viewID));

        AppIconControlSetUp(adView);
    }

    private void AppIconControlSetUp(UnifiedNativeAdView adView) {
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
    }

    private void AdChoicesLogoIntegration(UnifiedNativeAdView adView, int viewID) {
    }

    private void AdChoicesLogoControlSetUp(UnifiedNativeAdView adView) {
    }

    private void HeadingIntegration(UnifiedNativeAdView adView, int viewID) {

        adView.setHeadlineView(adView.findViewById(viewID));
        HeadingControlSetUp(adView);
    }

    private void HeadingControlSetUp(UnifiedNativeAdView adView) {
        // The headline is guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
    }


    private void SubHeadingIntegration(UnifiedNativeAdView adView, int viewID) {
        adView.setAdvertiserView(adView.findViewById(viewID));
        SubHeadingControlSetUp(adView);
    }

    private void SubHeadingControlSetUp(UnifiedNativeAdView adView) {
        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
    }

    private void PriceIntegration(UnifiedNativeAdView adView, int viewID) {

        adView.setPriceView(adView.findViewById(viewID));                            // Is used to display price of any item made for product from Advertiser
        PriceControlSetUp(adView);
    }

    private void PriceControlSetUp(UnifiedNativeAdView adView) {
        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }
    }

    private void BodyIntegration(UnifiedNativeAdView adView, int viewID) {
        adView.setBodyView(adView.findViewById(viewID));                              // Is used for diaplsy Ads Content body, if it has
        BodyControlSetUp(adView);
    }

    private void BodyControlSetUp(UnifiedNativeAdView adView) {
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
    }

    private void CallToActionIntegration(UnifiedNativeAdView adView, int viewID) {

        adView.setCallToActionView(adView.findViewById(viewID));
        CallToActionControlSetUp(adView);
    }

    private void CallToActionControlSetUp(UnifiedNativeAdView adView) {
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
    }

    private void ImageIntegration(UnifiedNativeAdView adView, int viewID) {

        MediaView mediaView = adView.findViewById(viewID);                         //  Media View or ImageView To Hold Ads Images
        adView.setMediaView(mediaView);                                                   // This is used to display Image of Advertisement


        ImageControlSetUp(adView);
    }

    private void ImageControlSetUp(UnifiedNativeAdView adView) {

    }

    private void PlayStoreIntegration(UnifiedNativeAdView adView, int viewID) {

        adView.setStoreView(adView.findViewById(viewID));                            //  Is used to display Ads store rating or reference link
        PlayStoreControlSetUp(adView);
    }

    private void PlayStoreControlSetUp(UnifiedNativeAdView adView) {
        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }
    }

    private void StarRatingIntegration(UnifiedNativeAdView adView, int viewID) {
        adView.setStarRatingView(adView.findViewById(viewID)); //R.id.ad_stars

        StarRatingControlSetUp(adView);
    }

    private void StarRatingControlSetUp(UnifiedNativeAdView adView) {
        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }
    }


    private void videoNativeIntegration(AdLoader.Builder builder) {

        // This code is used to mute the video if it is about to display.

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(true)
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);
    }

    private void videoNativeControlSetUp() {

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.


        VideoController vc = nativeAd.getVideoController();


        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        }

    }


//--------------------- END ------------ of ------------- Native Ads Integrations------------------- EnD --------------

}