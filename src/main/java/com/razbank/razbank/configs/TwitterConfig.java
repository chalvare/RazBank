package com.razbank.razbank.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class TwitterConfig {

    @Value(value = "${oauth.consumerKey}")
    private String consumerKey;
    @Value(value = "${oauth.consumerSecret}")
    private String consumerSecret;
    @Value(value = "${oauth.accessToken}")
    private String accessToken;
    @Value(value = "${oauth.accessTokenSecret}")
    private String accessTokenSecret;

    @Bean
    public TwitterStream configuration() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
       return new TwitterStreamFactory(cb.build()).getInstance();
    }
}
