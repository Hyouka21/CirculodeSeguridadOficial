package com.sosa.circulodeseguridadoficial.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sosa.circulodeseguridadoficial.entidades.Grupo;
import com.sosa.circulodeseguridadoficial.entidades.LocalizacionUsuario;
import com.sosa.circulodeseguridadoficial.entidades.Subscripcion;
import com.sosa.circulodeseguridadoficial.entidades.UsuarioDto;
import com.sosa.circulodeseguridadoficial.entidades.dto.CrearGrupos;
import com.sosa.circulodeseguridadoficial.entidades.dto.CrearLocalizacion;
import com.sosa.circulodeseguridadoficial.entidades.dto.EditarSubscripcionDto;
import com.sosa.circulodeseguridadoficial.entidades.dto.IdentificadorDto;

import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class ApiClient {
    public static final String UrlBase="https://192.169.1.4:45455/";
    private static PostInterface postInterface;

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    public static PostInterface getMyApiClient(){

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(UrlBase)
                .client(getUnsafeOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        postInterface=retrofit.create(PostInterface.class);

        return postInterface;
    }


    public interface PostInterface{
        @POST("gruposusuarios/editarsubscripcion")
        Call<Integer> editarSubscripcion(@Header("Authorization") String token, @Body EditarSubscripcionDto editarSubscripcionDto);
        @POST("gruposusuarios/obtenersubcripciones")
        Call<List<Subscripcion>> obtenerSubscripciones(@Header("Authorization") String token, @Body IdentificadorDto identificadorDto);
        @POST("localizacion/miLocalizacion")
        Call<Integer> enviarLocalizacion(@Header("Authorization") String token, @Body CrearLocalizacion crearLocalizacion);
        @GET("grupo/obtener")
        Call<List<Grupo>> obtenerGrupos(@Header("Authorization") String token);
        @GET("grupo/obtenerAdmin")
        Call<List<Grupo>> obtenerGruposAdmin(@Header("Authorization") String token);
        @POST("localizacion/obtenerLocalizacion")
        Call<List<LocalizacionUsuario>> obtenerLocalizaciones(@Header("Authorization") String token, @Body IdentificadorDto identificadorDto);
        @POST("grupo/crear")
        Call<Integer> crearGrupo(@Header("Authorization") String token,@Body CrearGrupos crearGrupo);
        @GET("usuarios/obtenerUsuario")
        Call<UsuarioDto> obtenerUsuario(@Header("Authorization") String token);
        @FormUrlEncoded
        @POST("usuarios/login")
        Call<String> login(@Field("email") String email, @Field("clave") String clave);





    }
}