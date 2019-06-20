package weixinkeji.vip.nutzboot.module;

import org.nutz.mvc.annotation.At;

public abstract class Fathor {
	 @At({"/now","/now1"})
	public  long now() {
        return System.currentTimeMillis();
    }
}
