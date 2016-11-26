package cn.kanejin.olla.server.interceptor;

import cn.kanejin.olla.response.ServiceResult;

public interface ServiceCacheStorage {
    ServiceResult<?> getResult(String key);

    void putResult(String key, ServiceResult<?> result);
}
