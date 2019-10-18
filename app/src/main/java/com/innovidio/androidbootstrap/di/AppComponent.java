package com.innovidio.androidbootstrap.di;

import android.app.Application;


import com.innovidio.androidbootstrap.BaseApplication;
import com.innovidio.androidbootstrap.di.module.ActivityBuilderModule;
import com.innovidio.androidbootstrap.di.module.AppModule;
import com.innovidio.androidbootstrap.di.module.DBModule;
import com.innovidio.androidbootstrap.di.module.FragmentBuilderModule;
import com.innovidio.androidbootstrap.di.module.NetworkModule;
import com.innovidio.androidbootstrap.di.module.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuilderModule.class,
                FragmentBuilderModule.class,
                ViewModelFactoryModule.class,
                AppModule.class,
                DBModule.class,
                NetworkModule.class
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder applicattion(Application application);

        AppComponent build();
    }
}
