package company.pepisha.find_yours_pets.socialNetworksManagers;


import android.content.Context;
import android.net.Uri;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

public class TwitterManager {

    private static final String CONSUMER_KEY = "9mKJiWsZFpZdU4XSErqVuWelv";
    private static final String CONSUMER_SECRET = "BAmIEmDNfSc6fEW0SadQC2jrH1kwfXHAvzPm5yaraXvPAMWsCH";

    public static void prepareTwitter(Context applicationContext) {
        TwitterAuthConfig authConfig =  new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET);
        Fabric.with(applicationContext, new Twitter(authConfig));
    }

    public static void tweetWithoutImage(String text, Context applicationContext ) {
        TweetComposer.Builder builder = new TweetComposer.Builder(applicationContext)
                .text(text);
        builder.show();
    }

    public static void tweetWithImage(String text, Uri imageUri ,Context applicationContext ) {
        TweetComposer.Builder builder = new TweetComposer.Builder(applicationContext)
                .text(text)
                .image(imageUri);
        builder.show();
    }


}
