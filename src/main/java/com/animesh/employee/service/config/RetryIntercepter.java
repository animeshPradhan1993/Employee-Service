package com.animesh.employee.service.config;


import jakarta.validation.constraints.NotNull;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RetryIntercepter implements Interceptor {

    @Override
    public @NotNull Response intercept(Chain chain) throws IOException {
        //log.error("Making request for the first time.");
        var request = chain.request();
        Response response = null;
        boolean responseOK = false;
        byte tryCount = 0;
        while (!responseOK && tryCount < 3) {
            try {
                Thread.sleep(5000 * tryCount);
                response = chain.proceed(request);

                responseOK = response.isSuccessful();

            } catch (Exception e) {

            } finally {
                assert response != null;
                response.close();
                tryCount++;
            }
        }

        return response != null ? response : chain.proceed(request);
    }
}