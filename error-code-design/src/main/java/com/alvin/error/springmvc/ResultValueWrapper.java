package com.alvin.error.springmvc;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alvin.error.response.CustomResult;
import com.alvin.error.response.Result;
import com.alvin.error.system.SystemErrorCodes;

/**
 * @author sofn
 * @version 2019-07-11 16:44
 */
@Order(0)
@RestControllerAdvice
public class ResultValueWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result) {
            return body;
        }

        if (body instanceof CustomResult) {
            return ((CustomResult<?>) body).getData();
        }

        if (StringUtils.equals(((ServletServerHttpRequest) request).getServletRequest().getServletPath(), "/error")) {
            return Result.error(SystemErrorCodes.SYSTEM_ERROR.getCode(), body.toString());
        } else {
            return Result.success(body);
        }
    }
}
