/*package genhootest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.cqut.genhoo.annotation.RequestParam;
import com.cqut.recruit.UserAction;

public class AnnotationTest {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Class<?> t = UserAction.class;
		Method method = t.getMethod("login", String.class,String.class,int.class,HttpServletRequest.class);
		Annotation[][] anno = method.getParameterAnnotations();
		for(Annotation[] a:anno){
			for(Annotation an:a){
				if (an instanceof RequestParam) {
					RequestParam new_name = (RequestParam) an;
					System.out.println(new_name.value());
				}
				System.out.println(an.annotationType().getCanonicalName().equals("com.cqut.genhoo.annotation.RequestParam"));
			}
		}
		System.out.println(anno.length);
		Class<?>[] parameterTypes = method.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> c = parameterTypes[i];
			System.out.println(c.getCanonicalName());
			System.out.println(Arrays.deepToString(c.getAnnotations()));
			
			if(c.equals(HttpServletRequest.class)){
				System.out.println(11);
			}
		}
	}
}
*/