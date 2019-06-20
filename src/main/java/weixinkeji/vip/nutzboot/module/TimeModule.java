package weixinkeji.vip.nutzboot.module;


import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import weixinkeji.vip.jweb.power.ann.JWPCode;
import weixinkeji.vip.jweb.power.ann.JWPSession;

@Api("time")
@At("/time")
@JWPSession
@IocBean(create="init", depose="depose")
public class TimeModule extends Fathor{
    
    @Inject
    protected PropertiesProxy conf;
    
    @JWPCode("select")
    @ApiOperation(value = "获取当前毫秒数", notes = "服务器端的时间", httpMethod="GET", response=Long.class)
//    @At({"now","now1"})
//    @At({"/now","/now1"})
    @Ok("raw")
    @Override
    public long now() {
        return System.currentTimeMillis();
    }
    
    @At({"/forjsp"})
//    @Ok("jsp:/index.jsp")
    @Ok("jsp:${Object==null?'/index.jsp':'abc'}")
//    @Ok("re:jsp:/my/adf/index.jsp")
//    @Ok("jsp:abc")
    public Object now2() {
        return System.currentTimeMillis();
    }
    
//    
    public void init() {}
    public void depose() {}

}
