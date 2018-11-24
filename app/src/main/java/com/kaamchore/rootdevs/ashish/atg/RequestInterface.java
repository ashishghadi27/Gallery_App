package com.kaamchore.rootdevs.ashish.atg;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestInterface {

    @GET("services/rest/?method=flickr.photos.getRecent&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
    Call<Imagelist> getJSON();
}
