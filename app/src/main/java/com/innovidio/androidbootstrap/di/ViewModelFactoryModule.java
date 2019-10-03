package com.innovidio.androidbootstrap.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.innovidio.androidbootstrap.viewmodel.MyViewModel;
import com.innovidio.androidbootstrap.di.viewmodel.ViewModelKey;
import com.innovidio.androidbootstrap.di.viewmodel.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);


    @Binds
    @IntoMap
    @ViewModelKey(MyViewModel.class)
    abstract ViewModel bindAuthViewModel( MyViewModel myViewModel);
}