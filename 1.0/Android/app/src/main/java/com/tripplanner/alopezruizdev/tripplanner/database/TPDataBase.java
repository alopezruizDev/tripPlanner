//package com.tripplanner.alopezruizdev.tripplanner.database;
//
//import android.arch.persistence.room.Database;
//import android.arch.persistence.room.RoomDatabase;
//import android.arch.persistence.room.TypeConverters;
//
//import com.tripplanner.alopezruizdev.tripplanner.database.converter.DateConverter;
//import com.tripplanner.alopezruizdev.tripplanner.database.dao.PlaceDao;
//import com.tripplanner.alopezruizdev.tripplanner.database.entity.Place;
//
//@Database(entities = {Place.class}, version = 1)
//@TypeConverters(DateConverter.class)
//public abstract class TPDataBase extends RoomDatabase {
//
//    private static volatile TPDataBase INSTANCE;
//    public abstract PlaceDao placeDao();
//}
