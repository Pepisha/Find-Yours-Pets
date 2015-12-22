package company.pepisha.find_yours_pets.socialNetworksManagers;


import android.content.Context;
import android.net.Uri;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

public class TwitterManager {

    public static void prepareTwitter(Context applicationContext) {
        TwitterAuthConfig authConfig =  new TwitterAuthConfig("9mKJiWsZFpZdU4XSErqVuWelv", "BAmIEmDNfSc6fEW0SadQC2jrH1kwfXHAvzPm5yaraXvPAMWsCH");
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
