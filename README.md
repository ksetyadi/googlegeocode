Google Geocode for Android
==============================

Instruction:
------------

1. Create new instance of GoogleGeocode.
2. GoogleGeocode has two constructors. You can use either:
   - Lat and Lng; or
   - Address
3. Assign the result as JSONObject object.
4. If it's succeed, the result will be not null. Otherwise, null.

example:
--------

        String lat = "-6.234567";
        String lng = "106.23123";
        
        GoogleGeocode geocode = new GoogleGeocode(lat, lng);
        JSONObject result = geocode.getReverseGeo();
        
        // you can check for the result here
        if (result != null) {
            // Success. result contains the information needed.
        } else {
            // failed.
        }