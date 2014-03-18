The included code is not a complete project, it is simply a skelleton for creating an extension in muzei.

Use these code snippets as a helper for the official settings activity guide. A walkthrough of creating a settings activity is available on this guide from the official doc’s:  http://developer.android.com/guide/topics/ui/settings.html

Start by creating a new SettingsActivity that extends PreferenceActivity, then go from there.

Remember that Muzei requires 4.2+, so even if you're application targets 2.3 this will not be an issue. Users on > 4.2 will never be able to open this activity. So you can ignore having to use the support library for this part of your application.

I hope this is useful to you!
