package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Form;
import com.innovidio.androidbootstrap.entity.FormWithMaintenance;
import com.innovidio.androidbootstrap.repository.FormRepository;

import java.util.List;

import javax.inject.Inject;

public class FormViewModel extends ViewModel {
    @Inject
    FormRepository formRepository;
    @Inject
    public FormViewModel(FormRepository formRepository) {
        this.formRepository = formRepository ;
    }

    public LiveData<List<Form>> getAllForms(){
        return this.formRepository.getAllForms();
    }

    public LiveData<List<Form>> getAllFormsByCarId(int carId){
        return this.formRepository.getAllFormByCardId(carId);
    }

    public LiveData<Form> getFormById(int id){
        return this.formRepository.getFormById(id);
    }

    public LiveData<Form> getLastForm(){
        return this.formRepository.getRecentForm();
    }


    public LiveData<Boolean> addForm(Form form){
        return this.formRepository.addForm(form);
    }

    public void deleteMaintenanceService(Form form){
        this.formRepository.deleteForm(form);
    }

    public void updateMaintenanceService(Form form){
        this.formRepository.updateForm(form);
    }

//    public void insertFormWithMaintenance(FormWithMaintenance form){
//        this.formRepository.insertFormWithMaintenance(form);
//    }

    public LiveData<List<FormWithMaintenance>> getAllFormWithMaintenance() {
        return formRepository.getAllFormWithMaintenance();
    }

    public LiveData<FormWithMaintenance> getFormWithMaintenance(int id) {
        return formRepository.getFormWithMaintenance(id);
    }

}