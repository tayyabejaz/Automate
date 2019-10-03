package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.innovidio.androidbootstrap.entity.models.Feed;

import java.util.List;

@Dao

public abstract class  FeedDao extends BaseDao<Feed>{

    @Insert
    public abstract Long insertFeed(Feed note);

    @Query("SELECT * FROM Feed ORDER BY id desc")
    public abstract LiveData<List<Feed>> fetchAllFeed();


    @Query("SELECT * FROM Feed WHERE category =:catagory")
    public abstract LiveData<Feed> getFeed(int catagory);


    @Update
    public abstract void updateFeed(Feed note);


    @Delete
    public abstract void deleteFeed(Feed note);
}
