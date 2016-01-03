package company.pepisha.find_yours_pets.socialNetworksManagers;


import android.net.Uri;

import com.facebook.share.model.ShareLinkContent;

import company.pepisha.find_yours_pets.connection.ServerConnectionManager;

public class FacebookManager {


    public static ShareLinkContent share(String title, String description, String url, String imageUrl) {
        ShareLinkContent.Builder builder = new ShareLinkContent.Builder();
        builder.setContentTitle(title)
                .setContentDescription(description);

        if (url != null && !url.isEmpty() && !url.equals("null")) {
            builder.setContentUrl(Uri.parse(url));
        } else {
            builder.setContentUrl(Uri.parse(ServerConnectionManager.url));
        }

        if (imageUrl != null && !imageUrl.isEmpty()) {
            builder.setImageUrl(Uri.parse(imageUrl));
        }

        return builder.build();
    }
}
