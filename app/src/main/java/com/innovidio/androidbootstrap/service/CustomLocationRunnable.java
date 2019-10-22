package com.innovidio.androidbootstrap.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.dashboard.SpeedDashboardActivity;


public final class CustomLocationRunnable implements Runnable {
    private long startTime;
    private long endTime;
    private SpeedDashboardActivity activity = null;
    private FloatingViewService floatingViewService = null;
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;
    private Listener listener = null;

    public CustomLocationRunnable(FloatingViewService floatingWindow) {
        this.floatingViewService = floatingWindow;
        if (this.activity != null) {
            this.activity = null;
        }
        m10108a(this.floatingViewService);
    }

    static  void startFloatService(CustomLocationRunnable customLocationRunnable) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("Dummy", "dummy");
            Constants.CONTEXT.startService(new Intent(Constants.CONTEXT,FloatingViewService.class));
            // customLocationRunnable.floatingViewService.m10042a(FloatingViewService.class, bundle);
        } catch (Exception e) {
            Log.e("DigiHUD", "Floating window update error: " + e.toString());
        }
    }

    @SuppressLint("MissingPermission")
    private void m10108a(Context context) {
        try {
            if (context instanceof SpeedDashboardActivity) {
                this.locationManager = (LocationManager) this.activity.getSystemService(Context.LOCATION_SERVICE);
            } else {
                this.locationManager = (LocationManager) this.floatingViewService.getSystemService(Context.LOCATION_SERVICE);
            }
            this.locationListener = new CustomLocationClass(this);
            if (!this.locationManager.isProviderEnabled("gps")) {
               /* if (this.activity != null) {
                    this.activity.m10020c();
                } else*/
                if (this.floatingViewService != null) {
                    //this.floatingViewService.m10095o();
                }
            }
            this.listener = new CustomGpsStatus(this);
            this.locationManager.requestLocationUpdates("gps", 500, 0.0f, this.locationListener);
            this.locationManager.addGpsStatusListener(this.listener);
        } catch (Exception e) {
        }
    }

    public final void run() {
        while (!Thread.interrupted()) {
            try {
                this.startTime = System.nanoTime();
                if (this.activity != null) {
//                    this.activity.m10023f();
                }
                this.endTime = System.nanoTime();
                Thread.sleep(1000 - ((this.endTime - this.startTime) / 1000000));
            } catch (InterruptedException e) {
                if (!(this.locationListener == null || this.locationManager == null)) {
                    this.locationManager.removeUpdates(this.locationListener);
                    if (this.listener != null) {
                        this.locationManager.removeGpsStatusListener(this.listener);
                        this.listener = null;
                    }
                    this.locationManager = null;
                    this.locationListener = null;
                }
                if (this.activity != null) {
                    this.activity = null;
                }
                if (this.floatingViewService != null) {
                    this.floatingViewService = null;
                    return;
                }
                return;
            } catch (Exception e2) {
            }
        }
    }

    final class CustomLocationClass implements LocationListener {
        final CustomLocationRunnable customLocationRunnableLocl;

        CustomLocationClass(CustomLocationRunnable customLocationRunnable) {
            this.customLocationRunnableLocl = customLocationRunnable;
        }

        public final void onLocationChanged(Location location) {
            try {
             /*   if (this.customLocationRunnable.activity != null && location != null) {
                    this.customLocationRunnable.activity.m10017a(location);
                } else*/
                if (this.customLocationRunnableLocl.floatingViewService != null) {
                    this.customLocationRunnableLocl.floatingViewService.setFloatingWindowSSpeed(location);
                    CustomLocationRunnable.startFloatService(this.customLocationRunnableLocl);
                }
            } catch (Exception e) {
            }
        }

        public final void onProviderDisabled(String str) {
            try {
               /* if (this.customLocationRunnable.activity != null) {
                    this.customLocationRunnable.activity.m10019b();
                } else */
                if (this.customLocationRunnableLocl.floatingViewService != null) {
                    //  this.customLocationRunnable.floatingViewService.m10093m();
                }
            } catch (Exception e) {
            }
        }

        public final void onProviderEnabled(String str) {
            try {
              /*  if (this.customLocationRunnable.activity != null) {
                    this.customLocationRunnable.activity.m10015a();
                } else*/
                if (this.customLocationRunnableLocl.floatingViewService != null) {
                    //this.customLocationRunnable.floatingViewService.m10094n();
                }
            } catch (Exception e) {
            }
        }

        public final void onStatusChanged(String str, int i, Bundle bundle) {
        }
    }

    final class CustomGpsStatus implements Listener {
        final  CustomLocationRunnable customLocationRunnableGps;

        CustomGpsStatus(CustomLocationRunnable customLocationRunnable) {
            this.customLocationRunnableGps = customLocationRunnable;
        }

        public final void onGpsStatusChanged(int i) {
            try {
                @SuppressLint("MissingPermission") GpsStatus gpsStatus = this.customLocationRunnableGps.locationManager.getGpsStatus(null);
              /*  if (this.customLocationRunnable.activity != null) {
                    this.customLocationRunnable.activity.m10016a(gpsStatus, i);
                } else */
                if (this.customLocationRunnableGps.floatingViewService != null) {
                    // this.customLocationRunnable.floatingViewService.m10075a(gpsStatus);
                }
            } catch (Exception e) {
            }
        }
    }
}
