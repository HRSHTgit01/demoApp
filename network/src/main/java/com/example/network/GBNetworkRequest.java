package com.example.network;

//import com.gwynniebee.gbcloset.common.network.NetworkProfiler;
//import platform.caastle.com.caastleinfa.config.CaastleConfig;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Use this class to create network request
 *
 * @author Vivek
 * @version 0.03
 * @since 10/2/16
 */
public class GBNetworkRequest {
    /**
     * Enum to specify the thread priority
     */
    public enum RequestThreadPriority {
        URGENT,
        HIGH,
        MEDIUM,
        LOW
    }
    public static enum GBRequestType {
        GET,
        PUT,
        POST,
        DELETE;

        private GBRequestType() {
        }
    }

    public static final String PARAMETER_TYPE_CLUSTER = "cluster";

    /**
     * Constant to define app platform
     */
    public static final String APP_PLATFORM = "android";

    /**
     * Constant to define app clustor(eg. app.android or app.ios)
     */

    public static final String APP_CLUSTER = "app.android";
    public static int requestCount;
    public int requestIndex = 25;
    private String mBaseUrl;    // base url (could also be consider as url as whole, if not using query parameters)
    private String mPostData;   // data to be posted
    private Map<String, String> mQueryParameters; // map to store key value pairs for query parameters
    private GBRequestType type = GBRequestType.GET;   // request type
    private String mContentType; // content-type
    private boolean mIsEnableCache;   // boolean to decide whether to cache the response for this particular request or not
    private boolean mIsUrlEncodingEnabled = true; // to encode url(query parameters) while making request or not
    private RequestThreadPriority mThreadPriority = RequestThreadPriority.MEDIUM;  // thread priority for this request
    private int mNumberOfRetryAttempts;   // by default no retry attempt will be made
    private int mRequestTimeout = 15000;
    private boolean mDisableAuthentication;
    private boolean mFetchSequential;
    private boolean mPostOnly;
    private  String mProfileData =  null;

    public boolean isFetchSequential() {
        return mFetchSequential;
    }

    public void setFetchSequential(boolean mFetchSequential) {
        this.mFetchSequential = mFetchSequential;
    }

//    public boolean isPostOnly() {
//        return type != GBHttpClient.GBRequestType.GET && mPostOnly;
//    }

    public void setPostOnly(boolean mPostOnly) {
        this.mPostOnly = mPostOnly;
    }

    private static final String SOURCE_EVENT_ID = "sourceEventId";

    /**
     * Constructor for GBNetworkRequest.
     *
     * @param baseUrl - {String} the base url to hit.
     */
    public GBNetworkRequest(String baseUrl) {
        setBaseUrl(baseUrl);
        requestIndex = requestCount;
        requestCount++;
    }

    @Deprecated
    public GBNetworkRequest() {
        requestIndex = requestCount;
        requestCount++;
    }

    public void setSourceEventId(String sourceEventId) {
        if (sourceEventId != null && sourceEventId.length() > 0) {
            addParameter(SOURCE_EVENT_ID,sourceEventId);
        }
    }

    /**
     * Set priority of this request. Based on priority, network service attempt to execute this request early or at normal pace
     *
     * @param priority - could be LOW, MEDIUM, HIGH
     */
    public void setRequestThreadPriority(RequestThreadPriority priority) {
        mThreadPriority = priority;
    }

    /**
     * Returns thread priority of this request
     *
     * @return - HIGH, MEDIUM, or LOW
     */
    public RequestThreadPriority getRequestThreadPriority() {
        return mThreadPriority;
    }

    /**
     * Set whether to encode query parameters of url at the time of making request or not.
     *
     * @param isUrlEncodingEnabled - set true to encode query parameters of url
     */
    public void setIsUrlEncodingEnabled(boolean isUrlEncodingEnabled) {
        this.mIsUrlEncodingEnabled = isUrlEncodingEnabled;
    }

    /**
     * get content type for this request
     *
     * @return - value could be application/json, text, etc.
     */
    public String getContentType() {
        return mContentType;
    }

    /**
     * Set content type of this request
     *
     * @param mContentType - could be application/json, text, etc
     */
    public void setContentType(String mContentType) {
        this.mContentType = mContentType;
    }

    /**
     * Returns base url of this request
     *
     * @return - base url of this request
     */
    public String getBaseUrl() {
        return mBaseUrl;
    }

    /**
     * Set base url for this request
     *
     * @param baseUrl - base url of this request, could be treated as a complete url, if there is not query parameters set using setQueryParameters, addQueryParameter
     */
    public void setBaseUrl(String baseUrl) {
        this.mBaseUrl = baseUrl;
        String uuid = "";
        if (mBaseUrl.contains("{UUID}")) {
            mBaseUrl = mBaseUrl.replace("{UUID}",uuid);
        } else if (mBaseUrl.contains("{uuid}")) {
            mBaseUrl = mBaseUrl.replace("{uuid}",uuid);
        }
    }

    /**
     * Get data to be posted
     *
     * @return - post data
     */
    public String getPostData() {
        return mPostData;
    }

    /**
     * Set post data of this request
     *
     * @param mPostData - post data
     */
    public void setPostData(String mPostData) {
        this.mPostData = mPostData;
    }

    /**
     * Returns Map of query parameters
     *
     * @return - Map of query parameters
     */
    public Map<String, String> getQueryParameters() {
        return mQueryParameters;
    }

    /**
     * Set query parameters as a Map instance
     *
     * @param mQueryParameters - query parameters as a Map instance
     */
    public void setQueryParameters(Map<String, String> mQueryParameters) {
        if (this.mQueryParameters != null && mQueryParameters != null) {
            this.mQueryParameters.putAll(mQueryParameters);
        }
        this.mQueryParameters = mQueryParameters;
    }

    /**
     * Add parameters as a string value pair
     *
     * @param key   - query parameter key
     * @param value - query parameter value
     */
    public void addParameter(String key,String value) {
        if (mQueryParameters == null) {
            mQueryParameters = new HashMap<>();
        }
        if (key != null && value != null) {
            mQueryParameters.put(key, value);
        }
    }

    /**
     * Returns Request type
     *
     * @return - GET,PUT,POST
     */
    public GBRequestType getType() {
        return type;
    }

    /**
     * Returns type of this request
     *
     * @param type - GET, POST,PUT
     */
    public void setType(GBRequestType type) {
        this.type = type;
    }

    /**
     * Returns encoded url
     *
     * @return - encoded url
     */
    public String getEncodedUrlWithQueryParameters() {
        try {
            StringBuilder result = new StringBuilder(mBaseUrl);
            if (mQueryParameters != null) {
                boolean first = false;
                if (!mBaseUrl.contains("?")) {
                    result.append("?");
                    first = true;
                }
                for (String key : mQueryParameters.keySet()) {
                    if (first) {
                        first = false;
                    } else {
                        result.append("&");
                    }
                    result.append(mIsUrlEncodingEnabled ? URLEncoder.encode(key,"UTF-8") : key);
                    result.append("=");
                    result.append(mIsUrlEncodingEnabled ? URLEncoder.encode(mQueryParameters.get(key),"UTF-8") : mQueryParameters.get(key));
                }
                return result.toString();
            }
        } catch (UnsupportedEncodingException ex) {

        }
        return mBaseUrl;
    }

    /**
     * Get Post Query
     *
     * @param params
     * @return
     * @throws IOException
     */
    public String getPOSTQuery(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (String key : params.keySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(mIsUrlEncodingEnabled ? URLEncoder.encode(key,"UTF-8") : key);
            result.append("=");
            result.append(mIsUrlEncodingEnabled ? URLEncoder.encode(params.get(key),"UTF-8") : params.get(key));

        }

        return result.toString();
    }

    /**
     * Set cache enable to enable NetworkService to get response from Cache if available or fetch from network and cache it
     *
     * @param isEnable
     */
    public void setCacheEnabled(boolean isEnable) {
        this.mIsEnableCache = isEnable;
    }

    /**
     * Returns true if cache is enabled
     *
     * @return- true if cache is enabled
     */
    public boolean isCacheEnabled() {
        return mIsEnableCache;
    }

    public void setNumberOfRetryAttempts(int attempts) {
        mNumberOfRetryAttempts = attempts;
    }

    public int getNumberOfRetryAttempts() {
        return mNumberOfRetryAttempts;
    }

    /**
     * Set request time out (read timeout)
     *
     * @param timeout - timeout in  milliseconds
     */
    public void setRequestTimeout(int timeout) {
        mRequestTimeout = timeout;
    }

    public int getRequestTimeout() {
        return mRequestTimeout;
    }

//    public void setProfileData(NetworkProfiler.ProfileData profileData) {
//        mProfileData = profileData;
//    }

//    public NetworkProfiler.ProfileData getProfileData() {
//        if (mProfileData == null) {
//            mProfileData = new NetworkProfiler.ProfileData();
//        }
//        return mProfileData;
//    }

    public void disableAuthentication() {
        mDisableAuthentication = true;
    }

    public boolean isAuthenticationEnabled() {
        return !mDisableAuthentication;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof GBNetworkRequest)) return false;
//
//        GBNetworkRequest request = (GBNetworkRequest) o;
//
//        if (mBaseUrl != null ? !mBaseUrl.equals(request.mBaseUrl) : request.mBaseUrl != null)
//            return false;
//        if (mPostData != null ? !mPostData.equals(request.mPostData) : request.mPostData != null)
//            return false;
//        if (mQueryParameters != null ? !mQueryParameters.equals(request.mQueryParameters) : request.mQueryParameters != null)
//            return false;
//        if (type != request.type) return false;
//        return !(mContentType != null ? !mContentType.equals(request.mContentType) : request.mContentType != null);
//
//    }

    @Override
    public int hashCode() {
        int result = mBaseUrl != null ? mBaseUrl.hashCode() : 0;
        result = 31 * result + (mPostData != null ? mPostData.hashCode() : 0);
        result = 31 * result + (mQueryParameters != null ? mQueryParameters.hashCode() : 0);
//        result = 31 * result + (type != null ? type.hashCode() : 0);
//        result = 31 * result + (mContentType != null ? mContentType.hashCode() : 0);
        return result;
    }
}
