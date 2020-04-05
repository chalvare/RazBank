package com.razbank.razbank.services.twitter;

import com.razbank.razbank.kafka.Producer;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;

@NoArgsConstructor
@Service
public class TwitterService {


    private Producer producer;
    private TwitterStream twitterStream;

    @Autowired
    public TwitterService(Producer producer, TwitterStream twitterStream) {
        this.producer = producer;
        this.twitterStream = twitterStream;
    }


    public void execute() {
        listener();

        final String[] track = {"coronavirus"};
        final String[] language = {"es"};

        FilterQuery tweetFilterQuery = new FilterQuery();
        tweetFilterQuery.track(track)
                .locations(Location.MADRID.getCo())
                .language(language);
        twitterStream.filter(tweetFilterQuery);
    }

    private void listener() {
        twitterStream.addListener(new StatusListener() {

            @Override
            public void onException(Exception e) {
                twitterStream.cleanUp();
            }

            @Override
            public void onStatus(Status status) {
                producer.sendMessage(status.getText());
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                // Do nothing
            }

            @Override
            public void onTrackLimitationNotice(int i) {
                // Do nothing
            }

            @Override
            public void onScrubGeo(long l, long l1) {
                // Do nothing
            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {
                // Do nothing
            }
        });
    }


}
