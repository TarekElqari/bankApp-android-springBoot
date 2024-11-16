package ma.emsi.bank_mobie.config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jaxb.JaxbConverterFactory;

public class RetrofitClient {
    private static Retrofit jsonRetrofit = null;
    private static Retrofit xmlRetrofit = null;
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    public static Retrofit getJsonClient() {
        if (jsonRetrofit == null) {
            jsonRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return jsonRetrofit;
    }

    // JAXB Retrofit instance for XML
    public static Retrofit getXmlClient() {
        if (xmlRetrofit == null) {
            xmlRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JaxbConverterFactory.create())  // Using JAXB converter
                    .build();
        }
        return xmlRetrofit;
    }
}
