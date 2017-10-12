package ganea.alexandra.userdetailsapp.dummy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ganea.alexandra.userdetailsapp.utils.AppConstants;
import ganea.alexandra.userdetailsapp.entities.ItemUser;
import ganea.alexandra.userdetailsapp.entities.ItemUserId;
import ganea.alexandra.userdetailsapp.entities.ItemUserLocation;
import ganea.alexandra.userdetailsapp.entities.ItemUserLogin;
import ganea.alexandra.userdetailsapp.entities.ItemUserName;
import ganea.alexandra.userdetailsapp.entities.ItemUserPicture;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ItemUser> ITEMS = new ArrayList<ItemUser>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ItemUser> ITEM_MAP = new HashMap<String, ItemUser>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(ItemUser item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId().getValue(), item);
    }

    private static ItemUser createDummyItem(int position) {
        ItemUser user = new ItemUser();
        user.setGender(position % 2 == 0 ? AppConstants.GENDER_FEMALE : AppConstants.GENDER_MALE);

        ItemUserName name = new ItemUserName();
        name.setTitle(position % 2 == 0 ? AppConstants.TITLE_MISS : AppConstants.TITLE_MISTER);
        name.setFirst("First");
        name.setLast("Last " + position);
        user.setName(name);

        ItemUserLocation location = new ItemUserLocation();
        location.setStreet("Street " + position);
        location.setCity("City " + position);
        location.setState("State " + position);
        location.setPostcode(String.valueOf(position));
        user.setLocation(location);

        user.setEmail("email" + position + "@email.com");

        ItemUserLogin login = new ItemUserLogin();
        login.setUsername("username" + position);
        login.setPassword("password " + position);
        login.setSalt("Salt " + position);
        login.setSha1("SHA1 " + position);
        login.setSha256("SHA256 " + position);
        user.setLogin(login);

        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss", Locale.US);
        Date currentDateInMilis = Calendar.getInstance().getTime();
        user.setDob(dateFormat.format(currentDateInMilis) + " " + position);
        user.setRegistered(dateFormat.format(currentDateInMilis) + " " + position);
        user.setPhone("Some phone number" + position);
        user.setPhone("Some cell number " + position);

        ItemUserId id = new ItemUserId();
        id.setName("name " + position);
        id.setValue("" + position);
        user.setId(id);

        ItemUserPicture picture = new ItemUserPicture();
        picture.setLarge("https://i.ytimg.com/vi/4Zs0gUJ7eAM/maxresdefault.jpg");
        picture.setMedium("https://i.ytimg.com/vi/yt4FyeA6jcg/0.jpg");
        picture.setLarge("https://i.ytimg.com/vi/yt4FyeA6jcg/0.jpg");
        user.setPicture(picture);

        user.setNat("nat " + position);
        user.setFaved(position % 3 == 0);
        return user;
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
