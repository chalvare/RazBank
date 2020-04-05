package com.razbank.razbank.controllers.twitter;

import com.razbank.razbank.services.twitter.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/social")
public class TwitterController {

    private final TwitterService twitterService;

    @Autowired
    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }


    @PostMapping("/twitter")
    public void kafka(){
        this.twitterService.execute();
    }
}
