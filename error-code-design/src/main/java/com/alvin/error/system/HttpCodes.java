package com.alvin.error.system;

import com.alvin.error.api.ErrorCode;
import com.alvin.error.manager.ErrorCodeManager;

import lombok.Getter;

/**
 * copy from org.springframework.http.HttpStatus
 *
 * @author sofn
 * @version 1.0 Created at: 2022-03-09 16:16
 */
@Getter
public enum HttpCodes implements ErrorCode {

    CONTINUE(100, "Continue"),
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),
    PROCESSING(102, "Processing"),
    CHECKPOINT(103, "Checkpoint"),
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
    NO_CONTENT(204, "No Content"),
    RESET_CONTENT(205, "Reset Content"),
    PARTIAL_CONTENT(206, "Partial Content"),
    MULTI_STATUS(207, "Multi-Status"),
    ALREADY_REPORTED(208, "Already Reported"),
    IM_USED(226, "IM Used"),
    MULTIPLE_CHOICES(300, "Multiple Choices"),
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    // @Deprecated
    // MOVED_TEMPORARILY(302, "Moved Temporarily"),
    SEE_OTHER(303, "See Other"),
    NOT_MODIFIED(304, "Not Modified"),
    /**
     * @deprecated
     */
    @Deprecated
    USE_PROXY(305, "Use Proxy"),
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    PERMANENT_REDIRECT(308, "Permanent Redirect"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    PAYMENT_REQUIRED(402, "Payment Required"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
    REQUEST_TIMEOUT(408, "Request Timeout"),
    CONFLICT(409, "Conflict"),
    GONE(410, "Gone"),
    LENGTH_REQUIRED(411, "Length Required"),
    PRECONDITION_FAILED(412, "Precondition Failed"),
    PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
    // @Deprecated
    // REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),
    URI_TOO_LONG(414, "URI Too Long"),
    // @Deprecated
    // REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested range not satisfiable"),
    EXPECTATION_FAILED(417, "Expectation Failed"),
    I_AM_A_TEAPOT(418, "I'm a teapot"),
    /**
     * @deprecated
     */
    @Deprecated
    INSUFFICIENT_SPACE_ON_RESOURCE(419, "Insufficient Space On Resource"),
    /**
     * @deprecated
     */
    @Deprecated
    METHOD_FAILURE(420, "Method Failure"),
    /**
     * @deprecated
     */
    @Deprecated
    DESTINATION_LOCKED(421, "Destination Locked"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    LOCKED(423, "Locked"),
    FAILED_DEPENDENCY(424, "Failed Dependency"),
    UPGRADE_REQUIRED(426, "Upgrade Required"),
    PRECONDITION_REQUIRED(428, "Precondition Required"),
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported"),
    VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),
    INSUFFICIENT_STORAGE(507, "Insufficient Storage"),
    LOOP_DETECTED(508, "Loop Detected"),
    BANDWIDTH_LIMIT_EXCEEDED(509, "Bandwidth Limit Exceeded"),
    NOT_EXTENDED(510, "Not Extended"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required");

    private final int status;
    private final String msg;

    HttpCodes(int status, String msg) {
        this.status = status;
        this.msg = msg;
        ErrorCodeManager.register(SystemProjectModule.INSTANCE, this);
    }



    public Series series() {
        return Series.valueOf(this);
    }

    public boolean is1xxInformational() {
        return this.series() == Series.INFORMATIONAL;
    }

    public boolean is2xxSuccessful() {
        return this.series() == Series.SUCCESSFUL;
    }

    public boolean is3xxRedirection() {
        return this.series() == Series.REDIRECTION;
    }

    public boolean is4xxClientError() {
        return this.series() == Series.CLIENT_ERROR;
    }

    public boolean is5xxServerError() {
        return this.series() == Series.SERVER_ERROR;
    }

    public boolean isError() {
        return this.is4xxClientError() || this.is5xxServerError();
    }

    public String toString() {
        return Integer.toString(this.status);
    }

    public static HttpCodes valueOf(int statusCode) {
        HttpCodes status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        } else {
            return status;
        }
    }

    public static HttpCodes resolve(int statusCode) {
        HttpCodes[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            HttpCodes status = var1[var3];
            if (status.status == statusCode) {
                return status;
            }
        }

        return null;
    }

    @Override
    public int getNodeNum() {
        return this.status;
    }


    public enum Series {
        INFORMATIONAL(1),
        SUCCESSFUL(2),
        REDIRECTION(3),
        CLIENT_ERROR(4),
        SERVER_ERROR(5);

        private final int value;

        Series(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }

        public static Series valueOf(HttpCodes status) {
            return valueOf(status.status);
        }

        public static Series valueOf(int statusCode) {
            int seriesCode = statusCode / 100;
            Series[] var2 = values();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Series series = var2[var4];
                if (series.value == seriesCode) {
                    return series;
                }
            }

            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        }
    }

}
