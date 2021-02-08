package com.jobdiva.api.config;

import java.util.Arrays;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserCache;

@Configuration
@EnableCaching
public class CacheConfig {
	
	@Bean
	CacheManager cacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("userCache")));
		return cacheManager;
	}
	
	@Bean
	public UserCache userCache() throws Exception {
		Cache cache = (Cache) cacheManager().getCache("userCache");
		//
		// return new SpringCacheBasedUserCache(cache);
		// //
		return new JobdivaCacheBasedUserCache(cache);
	}
}