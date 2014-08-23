package org.furygames.furyshimp.android;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


import org.furygames.furyshimp.FuryShimp;
import org.furygames.furyshimp.IActivityRequestHandler;


public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler  {

    private static final String AD_UNIT_ID_BANNER = "ca-app-pub-7100532092912370/2262336040";
    private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-7100532092912370/2262336040";

    protected AdView adView;
    protected View gameView;
    private InterstitialAd interstitialAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
      cfg.useAccelerometer = false;
      cfg.useCompass = false;

      // Do the stuff that initialize() would do for you
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

      RelativeLayout layout = new RelativeLayout(this);
      RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
      layout.setLayoutParams(params);

      AdView admobView = createAdView();
      layout.addView(admobView);
      View gameView = createGameView(cfg);
      layout.addView(gameView);

      setContentView(layout);
      startAdvertising(admobView);
      
      interstitialAd = new InterstitialAd(this);
      interstitialAd.setAdUnitId(AD_UNIT_ID_INTERSTITIAL);
      interstitialAd.setAdListener(new AdListener() {
        @Override
        public void onAdLoaded() {
          
        }
        @Override
        public void onAdClosed() {
          
        }
      });
    }

    private AdView createAdView() {
      adView = new AdView(this);
      adView.setAdSize(AdSize.SMART_BANNER);
      adView.setAdUnitId(AD_UNIT_ID_BANNER);
      adView.setId(12345); // this is an arbitrary id, allows for relative positioning in createGameView()
      RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
      params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
      params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
      adView.setLayoutParams(params);
      adView.setBackgroundColor(Color.BLACK);
      return adView;
    }

    private View createGameView(AndroidApplicationConfiguration cfg) {
      gameView = initializeForView(new FuryShimp(this), cfg);
      RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
      params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
      params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
      params.addRule(RelativeLayout.BELOW, adView.getId());
      gameView.setLayoutParams(params);
      return gameView;
    }

    private void startAdvertising(AdView adView) {
      AdRequest adRequest = new AdRequest.Builder().build();
      adView.loadAd(adRequest);
    }

    @Override
    public void showOrLoadInterstital() {
        runOnUiThread(new Runnable() {
          public void run() {
            if (interstitialAd.isLoaded()) {
              interstitialAd.show();
            }else {
              AdRequest interstitialRequest = new AdRequest.Builder().build();
              interstitialAd.loadAd(interstitialRequest);
            }
          }
        });
    }

    @Override
    public void onResume() {
      super.onResume();
      if (adView != null) adView.resume();
    }

    @Override
    public void onPause() {
      if (adView != null) adView.pause();
      super.onPause();
    }

    @Override
    public void onDestroy() {
      if (adView != null) adView.destroy();
      super.onDestroy();
    }
}