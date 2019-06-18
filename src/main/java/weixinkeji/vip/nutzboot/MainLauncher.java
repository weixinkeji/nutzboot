package weixinkeji.vip.nutzboot;

import org.nutz.boot.NbApp;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.*;
import org.nutz.mvc.annotation.*;

import weixinkeji.vip.jweb.power.ann.JWPCommon;
import weixinkeji.vip.nutzboot.bean.User;
import org.nutz.dao.Dao;


@IocBean(create="init", depose="depose")
public class MainLauncher {
    
    @Inject
    protected PropertiesProxy conf;
    @Inject
    protected Dao dao;
    @JWPCommon
    @At("/")
    @Ok("->:/index.html")
    public void index() {}
    
    public void init() {
        // NB自身初始化完成后会调用这个方法
        dao.create(User.class, false);
        if (dao.count(User.class) == 0) {
            User user = new User();
            user.setName("wendal");
            user.setAge(18);
            user.setLocation("广州");
            dao.insert(user);
         }
    }
    public void depose() {}

    public static void main(String[] args) throws Exception {
        new NbApp().setArgs(args).setPrintProcDoc(true).run();
    }

}
