package com.innovidio.androidbootstrap.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.innovidio.androidbootstrap.db.dao.FormDao;
import com.innovidio.androidbootstrap.db.dao.MaintenanceDao;
import com.innovidio.androidbootstrap.entity.Form;
import com.innovidio.androidbootstrap.entity.FormWithMaintenance;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.MaintenanceWithAlarms;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FormRepository {

    FormDao formDao;

    @Inject
    public FormRepository(FormDao formDao){
        this.formDao =  formDao;
    }

    public LiveData<List<Form>> getAllForms(){
        return this.formDao.getAllFormsMaintenance();
    }


    public void addForm(Form form){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                formDao.insert(form);
                return null;
            }
        }.execute();
    }

    public void deleteForm(Form form){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                formDao.delete(form);
                return null;
            }
        }.execute();
    }

    public void updateForm(Form form){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                formDao.update(form);
                return null;
            }
        }.execute();
    }

    public LiveData<Form> getFormById(int id){
        return this.formDao.getFormMaintenanceById(id);
    }

    public LiveData<List<Form>> getAllFormByCardId(int carId){
        return this.formDao.getAllFormByCardId(carId);
    }


    public LiveData<Form> getRecentForm(){
        return this.formDao.getRecentForm();
    }

//    public void insertFormWithMaintenance(FormWithMaintenance formWithMaintenance){
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                formDao.insertFormWithMaintenance(formWithMaintenance);
//                return null;
//            }
//        }.execute();
//    }


    public LiveData<List<FormWithMaintenance>> getAllFormWithMaintenance() {
        return formDao.getAllFormWithMaintenance();
    }

    public LiveData<FormWithMaintenance> getFormWithMaintenance(int id) {
        return formDao.getFormWithMaintenance(id);
    }

}


