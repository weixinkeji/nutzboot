package weixinkeji.vip.nutzboot._power;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import weixinkeji.vip.jweb.power.config.JWPSystemInterfaceConfig;

/**
 * 示例
 * <p>
 * JWebPower框架 对接nutzboot.mvc框架 实例类
 * </p>
 * 
 * @author wangchunzi
 *
 */
public class NutzBootMVCSIC implements JWPSystemInterfaceConfig {

	@Override
	public String getURLByClass(Class<?> c) {
		At at = c.getAnnotation(At.class);
		if (null != at) {
			return at.value().length == 0 ? c.getSimpleName() : at.value()[0];
		}
		return "";
	}

	@Override
	public String getURLByMethod(Method method) {
		return null;
	}

	/**
	 * 获取方法绑定的URL地址(有些框架，一个方法上 绑定了多个路径。如果想要此方法生效，，必须getURLByMethod方法返回null;
	 * 
	 * <p>
	 * 此方法不是控制方法，请返回null
	 * 
	 * @param method 绑定了相关url的方法
	 * @return String[]
	 */
	@Override
	public String[] getURLByMethod2(Method method) {
		At at = method.getAnnotation(At.class);
		List<String> list = new ArrayList<>();
		if (null != at) {
			if (at.value().length == 0) {
				list.add(method.getName());// 方法名即是路径节点名
			} else {
				for (String url : at.value()) {
					if (!url.isEmpty()) {
						list.add(url);// 一个方法绑定多个路径
					}
				}
			}
		}
		return list.size() > 0 ? list.toArray(new String[list.size()]) : null;
	}
	
	// 入口方法 处理后的跳转。理应跟入口的权限一样
	//如果你的设计不一样，请自行注释掉这个方法的调用
	//如果你返回的视图路径不在Ok上，而是方法内，手动写。那么权限架构是否法管理到你的视图路径的。
	@Override
	public String[] getViewByMethod(Class<?> c,Method method) {
		Ok ok = method.getAnnotation(Ok.class);
		List<String> list = new ArrayList<>();
		this.findForWardUrlByOkAnnotation(ok,list);
		return list.size() > 0 ? list.toArray(new String[list.size()]) : null;
	}
	//专门找返回视图的路径。
	private void findForWardUrlByOkAnnotation(Ok ok,List<String> list) {
		String forwardUrl;
		if (null == ok || (forwardUrl = ok.value()).isEmpty()) {
			return;
		}
		// 如果返回的是内部跳转，权限应该跟入口一样
		if (forwardUrl.startsWith("->:")) {
			list.add(forwardUrl.substring(3));
		}
		// 如果是 内部跳转到 /user/login.jsp
		else if (forwardUrl.startsWith("jsp:")) {
			forwardUrl=forwardUrl.substring(4).trim();
			if(forwardUrl.startsWith("$")) {//表示返回的视图，是表达式（需要计算后，才知道）
				for(String jspUrl:this.getOkExpression_Urls(forwardUrl)) {
					list.add(this.formartJsp(jspUrl));
				}
			}
			else {
				list.add(this.formartJsp(forwardUrl));
			}
		}
	}

	private String formartJsp(String jspUrl) {
		return jspUrl.contains("/")
				?jspUrl
				:"/WEB-INF/jsp/" + jspUrl.replace(".", "/") + ".jsp";
	}
	
	//专治 jsp:${obj == null ? 'jsp.user.login' : 'jsp.user.home'}  。从中取出访问路径 
	private  char sdoc='\'';
	private  String[] getOkExpression_Urls(String url) {
		StringBuilder sb=new StringBuilder();
		String rs[]=new String[2];
		int index=0;
		int rsIndex=0;
		for(char c:url.toCharArray()) {
			if(c==sdoc) {
				index++;
			}
			if(index==1&&c!=sdoc) {
				sb.append(c);
			}
			if(index==2) {
				index=0;
				rs[rsIndex++]=sb.toString();
				sb.setLength(0);
			}
		}
		return rs;
	}
	
	
	@Override
	public String[] getRequestUrlSuffix() {
		return new String[] { ".json", ".xml" };
	}

//	private String formatUrl(String url) {
//		if (!url.startsWith("/")) {
//			return "/" + url;
//		}
//		return url;
//	}

}