package com.herokuapp.swype;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 *@version 1.0
 *@since 1.0
 *
 * The following code fixes a bug with the Retrofit library where the default JSON converter cannot handle empty JSON
 * https://github.com/square/retrofit/issues/1554
 */

class NullOnEmptyConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody body) throws IOException {
                if (body.contentLength() == 0) return null;
                return delegate.convert(body);
            }
        };
    }
}
