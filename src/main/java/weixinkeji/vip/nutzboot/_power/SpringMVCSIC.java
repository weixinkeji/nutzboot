package weixinkeji.vip.nutzboot._power;

import java.lang.reflect.Method;

import org.nutz.mvc.annotation.At;

import weixinkeji.vip.jweb.power.config.JWPSystemInterfaceConfig;

/**
 * 示例
 * <p>
 * JWebPower框架 对接SrpingMVC框架 实例类
 * </p>
 * 
 * @author wangchunzi
 *
 */
public class SpringMVCSIC implements JWPSystemInterfaceConfig {

	@Override
	public String getURLByClass(Class<?> c) {
		At at=c.getAnnotation(At.class);
		if(null!=at) {
			return at.value().length==0?c.getSimpleName():at.value()[0];
		}
		return "";
	}

	@Override
	public String getURLByMethod(Method method) {
		At at=method.getAnnotation(At.class);
		if(null!=at) {
			return at.value().length==0?method.getName():at.value()[0];
		}
		return null;
	}

	@Override
	public String[] getRequestUrlSuffix() {
		return new String[] { ".json", ".xml" };
	}
	
	private String formatUrl(String url) {
		if (!url.startsWith("/")) {
			return "/" + url;
		}
		return url;
	}

}