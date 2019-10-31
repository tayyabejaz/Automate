package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.innovidio.androidbootstrap.entity.Form;
import com.innovidio.androidbootstrap.entity.FormWithMaintenance;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public abstract class FormDao extends BaseDao<Form>{

    @Query("SELECT * FROM Form")
    public abstract LiveData<List<Form>> getAllFormsMaintenance();

    @Query("SELECT * FROM Form WHERE id =:id")
    public abstract LiveData<Form> getFormMaintenanceById(int id);

    @Query("SELECT * FROM Form WHERE carId=:carId")
    public abstract LiveData<List<Form>> getAllFormByCardId(int carId);

    @Query("SELECT * FROM Form  ORDER BY id DESC LIMIT 1")
    public abstract  LiveData<Form> getRecentForm();

    @Query("SELECT * FROM Form")
    @Transaction
    public abstract LiveData<List<FormWithMaintenance>> getAllFormWithMaintenance();

    @Query("SELECT * FROM Form WHERE id=:formId")
    @Transaction
    public abstract LiveData<FormWithMaintenance> getFormWithMaintenance(int formId);


//    @Insert(onConflict = REPLACE)
//    @Transaction
//    public abstract long insertFormWithMaintenance(FormWithMaintenance formWithMaintenance);
}
