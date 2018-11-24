import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&\n" +
            "api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
    Call<JSONResponse> getJSON();
}