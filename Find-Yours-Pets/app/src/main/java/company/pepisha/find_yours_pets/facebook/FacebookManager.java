package company.pepisha.find_yours_pets.facebook;


import android.net.Uri;

import com.facebook.share.model.ShareLinkContent;

public class FacebookManager {


    public static ShareLinkContent share(String title, String description, String url) {
        return new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentDescription(
                        description)
                .setContentUrl(Uri.parse(url))
                .build();
    }
}
