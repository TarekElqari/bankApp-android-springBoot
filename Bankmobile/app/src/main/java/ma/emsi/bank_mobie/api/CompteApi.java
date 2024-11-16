package ma.emsi.bank_mobie.api;

import ma.emsi.bank_mobie.models.Compte;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CompteApi {
    @GET("api/comptes")
    Call<List<Compte>> getAllComptes(@Header("Accept") String format);

    @GET("api/comptes/{id}")
    Call<Compte> getCompteById(@Path("id") Long id, @Header("Accept") String format);

    @POST("api/comptes")
    Call<Compte> createCompte(@Body Compte compte, @Header("Content-Type") String format);

    @PUT("api/comptes/{id}")
    Call<Compte> updateCompte(@Path("id") Long id, @Body Compte compte, @Header("Content-Type") String format);

    @DELETE("api/comptes/{id}")
    Call<Void> deleteCompte(@Path("id") Long id);
}
