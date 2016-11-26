package cn.kanejin.olla.sample.service;

import cn.kanejin.olla.response.ServiceResult;

/**
 * @author Kane Jin
 */
public interface HelloService {
    ServiceResult<String> sayHello(String name);
}
