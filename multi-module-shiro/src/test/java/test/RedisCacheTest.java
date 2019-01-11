package test;

import com.highcharts.ShiroApplication;
import com.highcharts.shiro.entity.BeautifulPictures;
import com.highcharts.shiro.service.BeautifulPicturesService;
import com.highcharts.shiro.test.RedisCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShiroApplication.class)
public class RedisCacheTest {
	@Autowired
	BeautifulPicturesService beautifulPicturesService;

	@Autowired
    StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	RedisCache redisCache;
	
	@Test
	public void redisTest() throws Exception {

		//保存字符串
		stringRedisTemplate.opsForValue().set("aaa", "111");
		//读取字符串
		String aaa = stringRedisTemplate.opsForValue().get("aaa");
		System.out.println(aaa);
	}
	/*
	 * 缓存测试
	 */
	@Test
	public void CacheTest() {
		String id = "1";
		try {//调用有缓存的方法
			BeautifulPictures beautifulPicture = redisCache.getBeautifulPicturesList(id);
			System.out.println("第一次查询结果：");
			System.out.println(beautifulPicture);
		} catch (RedisConnectionFailureException e) {//调用直接走db的方法
			BeautifulPictures beautifulPicture = redisCache.getBeautifulPicturesList1(id);
			System.out.println("第一次查询结果：");
			System.out.println(beautifulPicture);
		}
		
		BeautifulPictures beautifulPicture1 = redisCache.getBeautifulPicturesList(id);
		System.out.println("第二次查询结果：");
		System.out.println(beautifulPicture1);
		
		redisCache.updateBeautifulPicture(id);
		
		BeautifulPictures beautifulPicture2 = redisCache.getBeautifulPicturesList(id);
		System.out.println("第三次查询结果：");
		System.out.println(beautifulPicture2);
	}
}
