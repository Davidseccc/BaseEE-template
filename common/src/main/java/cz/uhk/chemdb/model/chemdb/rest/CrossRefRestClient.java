package cz.uhk.chemdb.model.chemdb.rest;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import cz.uhk.chemdb.utils.DateFormats;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by vlado on 22/05/2017.
 */
@Dependent
public class CrossRefRestClient {

    private static final String BASE_API_URL = "https://api.crossref.org/";


    @Inject
    private DateFormats dateFormats;

    private CrossRefAPIService restService;

    @PostConstruct
    private void init() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        httpClient.addInterceptor(httpLoggingInterceptor);

//        for testing purposes only
//        httpClient.addInterceptor(new FakeInterceptor());

        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsString(), dateFormats.getFitbitDateTimeFormat()))
                .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, t, jsonDeserializationContext) -> LocalTime.parse(json.getAsString(), dateFormats.getFitbitTimeFormat()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, t, jsonDeserializationContext) -> LocalDate.parse(json.getAsString(), dateFormats.getFitbitDateFormat()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .client(httpClient.build())
                .build();

        restService = retrofit.create(CrossRefAPIService.class);
    }

    public CrossRefAPIService getService() {
        return restService;
    }

    public interface CrossRefAPIService {
        @GET("/works/{doi}")
        Call<CrossRefWorkMessage> getWork(@Path("doi") String doi);

    }

}