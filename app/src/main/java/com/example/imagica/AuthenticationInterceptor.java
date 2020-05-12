package com.example.imagica;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {

    AuthenticationInterceptor() {
    }

    //Getting Access FGrom Unsplash Api
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder().addHeader("Authorization", "Client-ID " + AceessKey_Constants.UNSPLASH_ACCESS_KEY)
                .build();
        return chain.proceed(request);
    }
}
