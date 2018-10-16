package com.arthurfmg.mycomics.rest.services;

import com.arthurfmg.mycomics.rest.model.ComicVineIssueModel;
import com.arthurfmg.mycomics.rest.model.ComicVineModel;
import com.arthurfmg.mycomics.rest.model.ComicVineResult;
import com.arthurfmg.mycomics.rest.model.VolumeModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;

/**
 * Created by phesto on 11/18/2016.
 */

public class ComicVineService {
    private static IComicVineService REST_CLIENT;
    private static final String API_KEY = "39adb872d507b54eb900925fdb56c56d714832db";
    private static final String FORMAT = "json";
    private static final String VOLUME_TYPE="4050";
    private static final String ISSUE_TYPE="4000";
    private static final String NAME_FILTER = "name:";
    private static final String VOLUME_FILTER = "volume:";
    private static final String NUMBER_FILTER = "issue_number:";
    private static final String SORT = "date_last_updated:desc";
    private static final String FILTER_DELIMITER = ",";


    public Call<ComicVineResult<ArrayList<VolumeModel>>> findVolumeByName(String name) {
        IComicVineService comicVineAPI = IComicVineService.retrofit.create(IComicVineService.class);
        return comicVineAPI.getVolumeByName(API_KEY, FORMAT, NAME_FILTER + name,getFieldList(VolumeModel.class), SORT);
    }

    public Call<ComicVineResult<ArrayList<VolumeModel>>> findVolumeById(Long id) {
        IComicVineService comicVineAPI = IComicVineService.retrofit.create(IComicVineService.class);
        String filter = "id:" + id;
        return comicVineAPI.getVolumeById(VOLUME_TYPE+"-"+id,API_KEY, FORMAT, filter, getFieldList(VolumeModel.class));
    }

    public Call<ComicVineResult<ArrayList<ComicVineIssueModel>>> findIssueByNameAndNumber(String name, Integer number) {
        IComicVineService comicVineAPI = IComicVineService.retrofit.create(IComicVineService.class);
        String filter = NAME_FILTER+name;//+FILTER_DELIMITER+NUMBER_FILTER+number;
        return comicVineAPI.getIssueByNameAndNumber(API_KEY, FORMAT, filter);
    }

    public Call<ComicVineResult<ArrayList<ComicVineIssueModel>>> findIssueByVolume(String volume, String offset) {
        IComicVineService comicVineAPI = IComicVineService.retrofit.create(IComicVineService.class);
        String filter = VOLUME_FILTER + volume;
        return comicVineAPI.getIssueByVolume(API_KEY, FORMAT, filter, offset);
    }

    public Call<ComicVineResult<ComicVineIssueModel>> findIssueById(Long id) {
        IComicVineService comicVineAPI = IComicVineService.retrofit.create(IComicVineService.class);
        return comicVineAPI.getIssueById(ISSUE_TYPE+"-"+id,API_KEY, FORMAT);
    }

    private <T extends ComicVineModel> String getFieldList(Class<T> type){
        List<Field> fields = new ArrayList<>();
        fields = getAllFields(fields, type);
        StringBuilder fieldList = new StringBuilder();
        for(Field f : fields){
            fieldList.append(f.getName()).append(FILTER_DELIMITER);
        }
        fieldList = fieldList.deleteCharAt(fieldList.length() - 1);
        return fieldList.toString();
    }

    private static  List<Field>  getAllFields( List<Field> fields,Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }
        return fields;
    }
}
