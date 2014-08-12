package com.infoklinik.rsvp.server.oauth;

import java.util.UUID;

import argo.format.JsonFormatter;
import argo.format.PrettyJsonFormatter;
import argo.jdom.JdomParser;
import argo.jdom.JsonRootNode;

public class OAuthServerUtil
{
    /**  Facebook starts Dec-16-2012 -- */
    public static final String FACEBOOK_APP_NAME    = "infoklinik.com";
    public static final String FACEBOOK_APP_ID      = "1442618809324907";
    public static final String FACEBOOK_APP_SECRET  = "ff9be3f5cfe2b84b50ae8f5d628903db";
    /** Facebook ends -- */
    
    /** Google starts Dec-16-2012 -- */
    public static final String GOOGLE_APP_NAME      = "OAuthLoginDemo";
    public final static String GOOGLE_APP_ID        = "246221969888.apps.googleusercontent.com";
    public final static String GOOGLE_APP_SECRET    = "upz9lh3Or-DKMfFGUwVs75fN";
    public final static String GOOGLE_SCOPE         = "https://www.googleapis.com/auth/userinfo.profile";
    /** Google ends -- */
    
    /** Twitter starts Dec-16-2012 -- */
    public static final String TWITTER_APP_NAME     = "OAuthLoginDemo";
    public final static String TWITTER_APP_ID       = "4LfvTXyLBUOaAHLFXpi6SQ";
    public final static String TWITTER_APP_SECRET   = "6HtTL32nBgn7BGXy7TPKzYcMFfxFk57HN4awTOp8GX4";
    /** Twitter ends -- */
    
    /** Yahoo! starts Dec-16-2012 -- */
    /** NOTE about stupid Yahoo: when creating the app, at least one api must be selected **/
    public static final String YAHOO_APP_NAME     = "OAuthLoginDemo";
    public final static String YAHOO_APP_ID       = "dj0yJmk9NkQ4NlViZmdzZ0NVJmQ9WVdrOWRrSldZblpFTkRJbWNHbzlNVGN3T0RZNE5qYzJNZy0tJnM9Y29uc3VtZXJzZWNyZXQmeD1mNg--";
    public final static String YAHOO_APP_SECRET   = "a2708e48cda6678e1b57116940935a7192ea700e";
    /** Yahoo! ends -- */
    
    /** Instagram starts Dec-16-2012 -- */
    public static final String INSTAGRAM_APP_NAME     = "OAuthLoginDemo";
    public final static String INSTAGRAM_APP_ID       = "a72e67f10c334edc95d21f5624490526";
    public final static String INSTAGRAM_APP_SECRET   = "4d29065ebb6542f592a7957f6d0686cb";
    /** Instagram ends -- */
 
    /** Linkedin starts -- */
    public static final String LINKEDIN_APP_NAME     = "OAuthLoginDemo";
    public final static String LINKEDIN_APP_ID       = "cpmiob5h1fsu";
    public final static String LINKEDIN_APP_SECRET   = "LgqI00Ckkf4xbagM";
    public final static String LINKEDIN_USER_TOKEN   = "XYZ";
    public final static String LINKEDIN_USER_SECRET  = "XYZ";
    /** Linkedin ends -- */
    
    /** github starts Dec-19-2012 --*/
    public static final String GITHUB_APP_NAME     = "OAuthLoginDemo";
    public final static String GITHUB_APP_ID       = "12c6d408af413f15394d";
    public final static String GITHUB_APP_SECRET   = "a690c32b8de7072fe8bef9c9fc64ca5b9ac564de";
    /** github starts --*/
    
    /** flickr starts Dec-21-2012 --*/
    public static final String FLICKR_APP_NAME     = "OAuthLoginDemo";
    public final static String FLICKR_APP_ID       = "4cda3073a98234ff91e1edf0f94054a1";
    public final static String FLICKR_APP_SECRET   = "825fa1fad4742353";
    /** flickr end Dec-21-2012 --*/
   
    /** Imagur starts Dec-08-2012 - their cert is invalid - */
    public static final String IMGUR_APP_NAME     = "OAuthLoginDemo";
    public final static String IMGUR_APP_ID       = "5de89977fe51b0497fd485f656f2215c050c3be63";
    public final static String IMGUR_APP_SECRET   = "105e221a68f6c7fd1a740b158e7c6ab3";
    /** Imagur ends Dec-08-2012 */
    
    /** vimeo starts Dec-22-2012 */
    public static final String VIMEO_APP_NAME     = "OAuthLoginDemo";
    public final static String VIMEO_APP_ID       = "bed24189fc325c111c0e3e58cbd403b6c5e19902";
    public final static String VIMEO_APP_SECRET   = "cd358f48b202dcf86124d900226c4d48fc734827";
    /** vimeo ends Dec-22-2012 */
    
    /** Evernote starts Dec-23-2012 Not supported due to their sandbox restriction */
    public static final String EVERNOTE_APP_NAME     = "OAuthLoginDemo";
    public final static String EVERNOTE_APP_ID       = "oauthdemo2012";
    public final static String EVERNOTE_APP_SECRET   = "05aae97837ab74ee";
    /** Evernote ends Dec-23-2012 */
    
    /** Windows Live starts Dec-23-2012 */
    /** Microsoft's implementation is the worst of all! It seems to cache  
     ** redirect URL, even after changing it, it keeps using the old one 
     ** I had to delete the app and create again to make it work         
     ** Also look at the doc in http://msdn.microsoft.com/en-us/library/live/hh826533.aspx#requesting_info_using_rest.
     ** A Slash is missing in quite a few URLs, So c&p without checking can be a big time waster!
     **/
    public static final String WINDOWS_LIVE_APP_NAME     = "OAuthLoginDemo";
    public final static String WINDOWS_LIVE_APP_ID       = "00000000480E7C9F";
    public final static String WINDOWS_LIVE_APP_SECRET   = "9W2yJx5ZbQm9fG6bKYeCO2td4GdJj6bT";
    /** Windows Live ends Dec-23-2012 */
    
    /** tumblr starts Dec-23-2012 */
    public static final String TUMBLR_LIVE_APP_NAME     = "OAuthLoginDemo";
    public final static String TUMBLR_LIVE_APP_ID       = "cmoxXXFqWm9wwkxF1MRSLW8IUseVjZndAj3Q0CkZJfJ5eZvZVa";
    public final static String TUMBLR_LIVE_APP_SECRET   = "ZB40SOPKLZbVLSxwpK7cqvt3QlTXJlkL2l7P52ayXhAuoNu5Xl";
    /** tumblr ends Dec-23-2012 */
    
    /* foursquare starts Dec-23-2012 */
    public static final String FOURSQUARE_APP_NAME     = "OAuthLoginDemo";
    public final static String FOURSQUARE_APP_ID       = "301QER4O2VJ2P35F4IX2VKAYPPE0UBF4GVDCSC1HCS3TW5FQ";
    public final static String FOURSQUARE_APP_SECRET   = "D5AALHCQ0NCMXQ5VOD2PEFPTCYWLKL0ZJG0K3X25Q1E0IN52";
    /* foursquare ends Dec-23-2012 */
    
    public static String makeRandomString()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    
    /**
     * Pretty print json string.
     * @param uglyJson
     * @return prettyfied json if possible, ugly one otherwise
     * I found Argo is the only one which can format a json string the way I want.
     * Argo: http://argo.sourceforge.net/documentation.html
     */
    public static String prettyPrintJsonString(String uglyJson)
    {
        try
        {
            JsonRootNode jsonRootNode = new JdomParser().parse(uglyJson);
            JsonFormatter jsonFormatter = new PrettyJsonFormatter();
            String prettyJson = jsonFormatter.format(jsonRootNode);
            return prettyJson;
        }
        catch(Exception e)
        {
            return uglyJson;
        }
    }
}
