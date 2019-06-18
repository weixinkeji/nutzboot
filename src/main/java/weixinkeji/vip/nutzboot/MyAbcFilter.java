package weixinkeji.vip.nutzboot;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.nutz.boot.starter.WebFilterFace;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import weixinkeji.vip.jweb.power.JWPFilter;

@IocBean
public class MyAbcFilter implements WebFilterFace {

	@Inject
	PropertiesProxy conf; // 可以注入任意你需要的对象

	public String getName() {
		return "JWPowerFilter"; // 这个名字必须全局唯一
	}

	public String getPathSpec() {
		return "/*"; // 通常是/*, 按需要写
	}
	
	public EnumSet<DispatcherType> getDispatches() {
		return EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
	}
	
	public Filter getFilter() {
		// 最重要的一步了
		return new JWPFilter(); // 返回你需要注册的那个Filter实例
		// 并非强制要你new一个Filter,该filter从ioc注入,从静态变量获取,都没有问题的
	}
	
	public Map<String, String> getInitParameters() {
		// 如果你的Filter实例需要配置一些参数,这里填一下,否则返回个null就行
		Map<String, String> params = new HashMap<String, String>();
		params.put("abc", "123");
		return params;
	}
	
	public int getOrder() {
		// 数字越小, 放在的位置越靠前
		return 0;
	}

}
