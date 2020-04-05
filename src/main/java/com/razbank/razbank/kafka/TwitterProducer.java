package com.razbank.razbank.kafka;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

@NoArgsConstructor
@Component
public class TwitterProducer {

    private Producer producer;

    @Value(value = "${oauth.consumerKey}")
    private String consumerKey;
    @Value(value = "${oauth.consumerSecret}")
    private String consumerSecret;
    @Value(value = "${oauth.accessToken}")
    private String accessToken;
    @Value(value = "${oauth.accessTokenSecret}")
    private String accessTokenSecret;

    @Autowired
    public TwitterProducer(Producer producer) {
        this.producer = producer;
    }

    public  Twitter config() throws TwitterException {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("BdbxxQ9xGbrG26sixLDeEP18E")
                .setOAuthConsumerSecret("990rLF2ohIrGVKU4O3SgAggEKdDYHZpJm2VNrXENYhA7xQ7Vkw")
                .setOAuthAccessToken("3306063281-tsVWtPKbd9BlLzoFY3HuLDkbUHSWCtbbF2IQ2LJ")
                .setOAuthAccessTokenSecret("jUfU8kwJIGXgVjAabzvVWaFuaFr3H6s4qBgGXpmdPH4NF");
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();

    }

    public void config2(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("BdbxxQ9xGbrG26sixLDeEP18E")
                .setOAuthConsumerSecret("990rLF2ohIrGVKU4O3SgAggEKdDYHZpJm2VNrXENYhA7xQ7Vkw")
                .setOAuthAccessToken("3306063281-tsVWtPKbd9BlLzoFY3HuLDkbUHSWCtbbF2IQ2LJ")
                .setOAuthAccessTokenSecret("jUfU8kwJIGXgVjAabzvVWaFuaFr3H6s4qBgGXpmdPH4NF");
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        twitterStream.addListener(new StatusListener () {

            @Override
            public void onException(Exception e) {

            }

            @Override
            public void onStatus(Status status) {
                producer.sendMessage(status.getText());
                System.out.println(status.getText());
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int i) {

            }

            @Override
            public void onScrubGeo(long l, long l1) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }
        });
        FilterQuery tweetFilterQuery = new FilterQuery(); // See
        tweetFilterQuery.track(new String[]{"coronavirus"}); // OR on keywords
        tweetFilterQuery.locations(new double[][]{
                new double[]{37.2663803, -6.9400401},
                new double[]{ 41.3818,2.1685}});
        tweetFilterQuery.language(new String[]{"es"});
        twitterStream.filter(tweetFilterQuery);
    }

    private void searchByLocation(Twitter twitter) throws TwitterException {
        List<Status> statuses = twitter.getUserTimeline("jax_vane");
        Query query = new Query().geoCode(new GeoLocation(40.4165000,-3.7025600), 20.0,Query.Unit.km);
        query.setLang("es");
        query.setCount(10);
        QueryResult result = twitter.search(query);
        for(Status status :result.getTweets())
        {
            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }
    }

    public static void main(String [] args) throws TwitterException {
        TwitterProducer tw = new TwitterProducer();
        Twitter t=tw.config();
        tw.searchByLocation(t);
        //tw.config2();

    }

}
