package cn.kanejin.olla.sample.service.impl;

import cn.kanejin.olla.AbstractService;
import cn.kanejin.olla.response.ServiceResult;
import cn.kanejin.olla.response.SuccessResult;
import cn.kanejin.olla.sample.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author Kane Jin
 */
@Service("helloService")
public class HelloServiceImpl extends AbstractService implements HelloService {

    public ServiceResult<String> sayHello(String name) {
        return new SuccessResult<String>("Hello " + name + "!");
    }
}
